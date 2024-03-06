package com.example.Upbeat_websocket;

import com.example.Upbeat_websocket.Model.UPBEAT.UpbeatGame;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class UpbeatWebsocketApplication {

	public static void main(String[] args) {
		UpbeatGame.getInstance();
		SpringApplication.run(UpbeatWebsocketApplication.class, args);
	}

}
