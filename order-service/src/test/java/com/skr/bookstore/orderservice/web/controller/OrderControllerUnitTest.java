package com.skr.bookstore.orderservice.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.BDDMockito.given;

import com.skr.bookstore.orderservice.testdata.OrderTestDataUtils;
import com.skr.bookstore.orderservice.web.exception.domain.OrderService;
import com.skr.bookstore.orderservice.web.exception.domain.SecurityService;
import com.skr.bookstore.orderservice.web.exception.domain.model.CreateOrderRequest;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.jupiter.api.Named.named;
import static org.junit.jupiter.params.provider.Arguments.arguments;

@WebMvcTest(OrderController.class)
public class OrderControllerUnitTest {

	
	@MockitoBean
	private OrderService orderService;
	
	@MockitoBean
	private SecurityService securityService;
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@BeforeEach
	void setUp()
	{
		given(securityService.getLoginUserName()).willReturn("karan	");
	}
	
	@ParameterizedTest(name = "[{index}]-{0}")
    @MethodSource("createOrderRequestProvider")
    void shouldReturnBadRequestWhenOrderPayloadIsInvalid(CreateOrderRequest request) throws Exception {
		System.out.println("request Object created :"+request);
        given(orderService.createOrder(eq("siva"), any(CreateOrderRequest.class)))
                .willReturn(null);

        mockMvc.perform(post("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    static Stream<Arguments> createOrderRequestProvider() {
        return Stream.of(
                arguments(named("Order with Invalid Customer", OrderTestDataUtils.createOrderRequestWithInvalidCustomer())),
                arguments(named("Order with Invalid Delivery Address", OrderTestDataUtils.createOrderRequestWithInvalidDeliveryAddress())),
                arguments(named("Order with No Items", OrderTestDataUtils.createOrderRequestWithNoItems())));
    }
}
