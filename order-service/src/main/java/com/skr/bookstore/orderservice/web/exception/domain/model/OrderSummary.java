package com.skr.bookstore.orderservice.web.exception.domain.model;

public record OrderSummary(String orderNumber, OrderStatus status) {}