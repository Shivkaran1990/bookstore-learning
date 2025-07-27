package com.skr.bookstore.orderservice.web.exception.domain;


import org.springframework.data.jpa.repository.JpaRepository;

interface OrderEventRepository extends JpaRepository<OrderEventEntity, Long> {}
