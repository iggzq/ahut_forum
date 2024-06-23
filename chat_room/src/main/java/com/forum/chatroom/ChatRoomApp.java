package com.forum.chatroom;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.reactive.config.EnableWebFlux;

/**
 * Hello world!
 *
 * @author lituizi
 */
@SpringBootApplication
@AutoConfiguration
@EnableWebFlux
public class ChatRoomApp
{
    public static void main( String[] args )
    {
        SpringApplication.run(ChatRoomApp.class, args);
    }
}
