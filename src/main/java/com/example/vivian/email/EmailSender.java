package com.example.vivian.email;

import org.springframework.scheduling.annotation.Async;

public interface EmailSender {
	
	@Async
	void send(String para,String email);
}
