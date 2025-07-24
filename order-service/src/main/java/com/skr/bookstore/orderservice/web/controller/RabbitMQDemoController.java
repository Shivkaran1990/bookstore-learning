package com.skr.bookstore.orderservice.web.controller;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.skr.bookstore.orderservice.ApplicationProperties;

@RestController
public class RabbitMQDemoController {

	private final RabbitTemplate rabbitTemplate;
	private final ApplicationProperties applicationProperties;
	RabbitMQDemoController(RabbitTemplate rabbitTemplate,ApplicationProperties applicationProperties){
		this.rabbitTemplate=rabbitTemplate;
		this.applicationProperties=applicationProperties;
	}
	
	@PostMapping("/send")
	public void sendMsg(@RequestBody MyMessage message)
	{
		rabbitTemplate.convertAndSend(applicationProperties.orderEventsExchange(),message.rountingKey(),message.payload());
	}
	
}

record MyMessage(String rountingKey,MyPayload payload) {}
record MyPayload(String content) {
	
}