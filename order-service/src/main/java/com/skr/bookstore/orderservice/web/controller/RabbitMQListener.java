package com.skr.bookstore.orderservice.web.controller;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import com.skr.bookstore.orderservice.web.exception.domain.model.OrderCreatedEvent;

@Service
public class RabbitMQListener {

//	@RabbitListener(queues = "${orders.new-orders-queue}")
//	public void handleNewOrder(OrderCreatedEvent payload)
//	{
//		System.out.println("New Order : "+payload);
//	}
//	
//	@RabbitListener(queues = "${orders.delivered-orders-queue}")
//	public void handleDeliveredOrder(OrderCreatedEvent payload)
//	{
//		System.out.println("Delivered Order : "+payload);
//	}
//	
//	@RabbitListener(queues = "${orders.cancelled-orders-queue}")
//	public void handleCancelledOrder(OrderCreatedEvent payload)
//	{
//		System.out.println("Cancelled  Order : "+payload);
//	}
	
}
