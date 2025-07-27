package com.skr.bookstore.orderservice.web.controller;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;

import com.skr.bookstore.orderservice.AbstractIT;
import com.skr.bookstore.orderservice.web.exception.domain.model.OrderSummary;

import io.restassured.common.mapper.TypeRef;
import io.restassured.http.ContentType;

@Sql("/test-orders.sql")
public class OrderControllerTest extends AbstractIT {
//@WebMvcTest -- for unit test
//@DataJpaTest-- for repo test
//@SpringBootTest -- for integration test
	@Nested
	class CreateOrderTest {

		@Test
		void shouldCreatedOrderSuccessfully() {
			mockGetProductByCode("P100", "Product 1", new BigDecimal("25.50"));
			var payload = """
					{
					   "customer":{
					      "name":"Shivkaran Ravidas",
					      "email":"s.ravidas01@gmail.com",
					      "phone":"9860214064"
					   },
					   "deliveryAddress":{
					      "addressLine1":"Richhalalmuha",
					      "addressLine2":"Near Imali Tree, Lalakheda road",
					      "city":"Mandsaur",
					      "state":"MP",
					      "zipCode":"458669",
					      "country":"India"
					   },
					   "items":[
					       {
					       "code":"P100",
					       "name":"Product-1",
					       "price":"25.50",
					       "quantity":"1"
					       }
					   ]
					              }
					""";
			given().contentType(ContentType.JSON).body(payload).when().post("/api/orders").then()
					.statusCode(HttpStatus.CREATED.value()).body("orderNumber", notNullValue());
		}

		@Test
		void shouldReturnBadRequestWhenMandatoryDataIsMissing() {
			var payload = """
					{
					   "customer":{
					      "name":"Shivkaran Ravidas",
					      "email":"s.ravidas01@gmail.com",
					      "phone":"9860214064"
					   },
					   "deliveryAddress":{
					      "addressLine1":"Richhalalmuha",
					      "addressLine2":"Near Imali Tree, Lalakheda road",
					      "city":"Mandsaur",
					      "state":"MP",
					      "zipCode":"458669",
					      "country":""
					   },
					   "items":[
					       {
					       "code":"P100",
					       "name":"Product-1",
					       "price":"25.50",
					       "quantity":"1"
					       }
					   ]
					              }
					""";
			given().contentType(ContentType.JSON).body(payload).when().post("/api/orders").then()
					.statusCode(HttpStatus.BAD_REQUEST.value());
		}
	}
//
//	@Nested
//	class GetOrdersTests {
//		@Test
//		void shouldGetOrdersSuccessfully() {
//			List<OrderSummary> orderSummaries = given().when()
//					.get("/api/orders")
//					.then()
//					.statusCode(200)
//					.extract()
//					.body()
//					.as(new TypeRef<>() {
//					});
//
//			assertThat(orderSummaries).hasSize(2);
//		}
//	}
//
//	@Nested
//	class GetOrderByOrderNumberTests {
//		String orderNumber = "order-123";
//
//		@Test
//		void shouldGetOrderSuccessfully() {
//			given().when()
//					.get("/api/orders/{orderNumber}", orderNumber)
//					.then()
//					.statusCode(200)
//					.body("orderNumber", is(orderNumber))
//					.body("items.size()", is(2));
//		}
//	}
}
