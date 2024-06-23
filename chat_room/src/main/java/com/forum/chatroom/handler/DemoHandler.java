package com.forum.chatroom.handler;

import com.forum.chatroom.config.WebSocketMapping;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Mono;

/**
 * @author lituizi
 */
@Component
@WebSocketMapping("/hello")
public class DemoHandler implements WebSocketHandler {

    @Override
    public Mono<Void> handle(WebSocketSession session) {
        System.out.println("handle");
        return session.send(
                session.receive().map(
                        msg -> session.textMessage("推送消息: " + msg.getPayloadAsText())
                )
        );
    }
}
