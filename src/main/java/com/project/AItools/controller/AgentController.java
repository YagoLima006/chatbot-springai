package com.project.AItools.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AgentController 
{
	private final ChatClient chatClient;
	
	public AgentController(ChatClient chatClient)
	{
		this.chatClient = chatClient;
	}
	
	@PostMapping("/chat")
    public String generateCode(@RequestBody String message)
	{
		return chatClient
				.prompt()
				.user(message)
				.call()
				.content();
	}
}
