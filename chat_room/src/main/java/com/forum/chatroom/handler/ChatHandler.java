package com.forum.chatroom.handler;

import cn.hutool.json.JSONUtil;
import com.forum.chatroom.config.WebSocketMapping;
import com.forum.chatroom.entity.ChatRoomComment;
import lombok.SneakyThrows;
import org.json.JSONObject;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.core.publisher.Mono;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author lituizi
 */
@Component
@WebSocketMapping("/chat/{id}")
public class ChatHandler implements WebSocketHandler {

    public static final Map<WebSocketSession, FluxSink<WebSocketMessage>> MY_CLIENTS = new ConcurrentHashMap<>();

    // 客户端数量
    private static int clientCount = 0;

    /**
     * 处理WebSocket请求
     */
    @Override
    @NonNull
    public Mono<Void> handle(WebSocketSession webSocketSession) {
        ChatRoomComment chatRoomComment = new ChatRoomComment();
        String path = webSocketSession.getHandshakeInfo().getUri().getPath();
        String id = path.substring(path.lastIndexOf("/") + 1);
        chatRoomComment.setId(id);
        // 创建一个变量来保存发送消息的WebSocketSession
        final WebSocketSession senderSession = webSocketSession;

        Flux<WebSocketMessage> receive = webSocketSession.receive();
        Mono<Void> mono1 = receive.map(message -> {
            String payloadAsText = message.getPayloadAsText();
            // 原始消息
            chatRoomComment.setComment(payloadAsText);
            return chatRoomComment;
        }).doOnNext(comment -> {
            // 广播的消息
            LocalTime now = LocalTime.now();
            String nowTime = now.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
            chatRoomComment.setTime(nowTime);
            chatRoomComment.setId("(" + id + ")");
            String broadcastMessage = JSONUtil.parse(chatRoomComment).toString();

            // 返回自定义的响应给发送者，例如加上"您说："的前缀
            chatRoomComment.setId(String.format("您说(%s)", id));
            chatRoomComment.setComment(chatRoomComment.getComment());
            String responseToSender = JSONUtil.parse(chatRoomComment).toString();
            // 向发送者发送定制的消息
            FluxSink<WebSocketMessage> senderSink = MY_CLIENTS.get(senderSession);
            if (senderSink != null) {
                senderSink.next(senderSession.textMessage(responseToSender));
            }
            // 广播给除了发送者之外的所有客户端
            for (Map.Entry<WebSocketSession, FluxSink<WebSocketMessage>> entry : MY_CLIENTS.entrySet()) {
                if (!entry.getKey().equals(senderSession)) {
                    entry.getValue().next(entry.getKey().textMessage(broadcastMessage));
                }
            }
        }).then();
        //创建要发送消息的 outFlux
        Flux<WebSocketMessage> outFlux = Flux.create(fluxSink ->
        {
            //Flux 真正发布消息用的是Flux 底层的 fluxSink
            MY_CLIENTS.put(webSocketSession, fluxSink);
        });
        // 客户端数量加1
        clientCount++;
        // Send initial message with current clientCount to all sessions
        sendClientCountToAllSessions(webSocketSession);
        //发送消息
        Mono<Void> mono2 = webSocketSession.send(outFlux);
        // Send messages and handle disconnection
        Mono<Void> sendMono = webSocketSession.send(outFlux)
                .doFinally(signalType -> {
                    // Remove the session from MY_CLIENTS map when the session ends
                    MY_CLIENTS.remove(webSocketSession);
                    // Decrement client count when the session ends
                    clientCount--;
                    sendClientCountToAllSessions(webSocketSession);
                    System.out.println("Client disconnected. Remaining clients: " + clientCount);
                });
        //把两个mono 的消息汇总起来 再返回
        return Mono.when(mono1, mono2)
                .then(sendMono);

    }

    // Method to send current clientCount to all connected sessions
    @SneakyThrows
    private void sendClientCountToAllSessions(WebSocketSession webSocketSession) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("clientCount", clientCount);
        String currentClientCount = jsonObject.toString();
        if (webSocketSession.isOpen()) {
            webSocketSession.send(Mono.just(webSocketSession.textMessage(currentClientCount)))
                    .subscribe();
        }
        // 遍历所有客户端并发送当前的客户端连接数
        for (Map.Entry<WebSocketSession, FluxSink<WebSocketMessage>> entry : MY_CLIENTS.entrySet()) {
            entry.getValue().next(entry.getKey().textMessage(currentClientCount));
        }
    }

}


