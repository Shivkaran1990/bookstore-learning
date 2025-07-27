package com.skr.bookstore.orderservice;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.configureFor;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlMatching;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.wiremock.integrations.testcontainers.WireMockContainer;

import com.github.tomakehurst.wiremock.client.WireMock;

import io.restassured.RestAssured;;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@Import(TestcontainersConfiguration.class)
public abstract class AbstractIT {
    @LocalServerPort
    int port;
    
    static WireMockContainer wireMockContainer=new WireMockContainer("wiremock/wiremock:3.5.2-alpine");
    
    @BeforeAll
    static void beforeAllUp() {
      wireMockContainer.start();
      configureFor(wireMockContainer.getHost(),wireMockContainer.getPort());
    }
    
    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry dynamicPropertyRegistry)
    {
    	dynamicPropertyRegistry.add("orders.catalog-service-url", wireMockContainer::getBaseUrl);
    }
    protected static void mockGetProductByCode(String code, String name, BigDecimal price) {
        stubFor(WireMock.get(urlMatching("/api/products/" + code))
                .willReturn(aResponse()
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withStatus(200)
                        .withBody(
                                """
                    {
                        "code": "%s",
                        "name": "%s",
                        "price": %f
                    }
                """
               .formatted(code, name, price.doubleValue()))));
    }

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }
}