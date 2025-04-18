package com.prasanth.ragdemo.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.ResponseEntity;
import org.springframework.ai.chat.client.advisor.QuestionAnswerAdvisor;
import org.springframework.ai.vectorstore.pgvector.PgVectorStore;
import org.springframework.web.bind.annotation.RestController;

import com.pgvector.PGvector;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
public class ChatController {

	
	private final ChatClient chatClient;

	public ChatController(ChatClient.Builder builder, PgVectorStore vectorStore) {
		this.chatClient = builder.defaultAdvisors(new QuestionAnswerAdvisor(vectorStore)).build();
	}
	
	@GetMapping("/")
	public String index() {
		return "success";
	}
	
	@GetMapping("/chat")
	public String getMethodName(@RequestParam String query) {
		return chatClient.prompt( query).call().content();
	}
}
