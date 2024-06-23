package com.forum.chatroom.handler;

import com.forum.chatroom.config.WebSocketMapping;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author lituizi
 */
@Component
@WebSocketMapping("/cha/{name}")
public class ChatHandler implements WebSocketHandler {

    public static final Map<WebSocketSession, FluxSink<WebSocketMessage>> MY_CLIENTS = new ConcurrentHashMap<>();

    @Override
    public Mono<Void> handle(WebSocketSession webSocketSession) {
        String path = webSocketSession.getHandshakeInfo().getUri().getPath();
        String name = path.substring(path.lastIndexOf("/") + 1);
        Flux<WebSocketMessage> receive = webSocketSession.receive();
        Mono<Void> mono1 = receive.map(message -> {
            String payloadAsText = message.getPayloadAsText();
            //返回 用户名+消息
            return name + " ： " + payloadAsText;
        }).doOnNext(message -> {
            //此处做消息广播,      keySet()用于遍历map中的所有key，存在一个set集合中
            for (WebSocketSession session : MY_CLIENTS.keySet()) {
                //通过session这个key ， 获取消息通道FluxSink
                FluxSink<WebSocketMessage> fluxSink = MY_CLIENTS.get(session);
                //调用 fluxSink 的 next() 方法向 Flux 发送消息
                //textMessage() 方法负责将 String 转换成 WebSocketMessage，把string类型的消息转回 WebSocketMessage
                fluxSink.next(session.textMessage(message));
            }
        }).then();
        //创建要发送消息的 outFlux
        Flux<WebSocketMessage> outFlux = Flux.create(fluxSink ->
        {
            //Flux 真正发布消息用的是Flux 底层的 fluxSink
            MY_CLIENTS.put(webSocketSession, fluxSink);
        });
        //发送消息
        Mono<Void> mono2 = webSocketSession.send(outFlux);
        //把两个mono 的消息汇总起来 再返回
        return Mono.zip(mono1, mono2).then();

    }
}
