package com.skr.bookstore.orderservice.web.exception.domain;

import org.springframework.stereotype.Service;

@Service
public class SecurityService {

	public String getLoginUserName()
	{
		return "user";
	}
}
