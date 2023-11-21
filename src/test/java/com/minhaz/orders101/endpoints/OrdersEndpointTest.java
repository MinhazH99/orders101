package com.minhaz.orders101.endpoints;

import com.minhaz.orders101.enums.OrderStatus;
import com.minhaz.orders101.enums.PaymentStatus;
import com.minhaz.orders101.models.Order;
import jakarta.ws.rs.core.Application;
import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.aspectj.weaver.ast.Or;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OrdersEndpointTest {

  // @Override
  // protected Application configure() {
  // return new ResourceConfig(OrdersEndpoint.class);
  // }

  @LocalServerPort
  private int port;


  @Autowired
  private TestRestTemplate restTemplate;


  @Test
  void testGETOrder() throws Exception {
    String url = "http://localhost:" + port + "/orders/1";
    ParameterizedTypeReference<Order> paramType = new ParameterizedTypeReference<Order>() {};
    ResponseEntity<Order> orderResponse = restTemplate.exchange(url, HttpMethod.GET, null, paramType);

    assertEquals(orderResponse.getStatusCode(), HttpStatus.OK);
    assertEquals("1", orderResponse.getBody().getId());
    assertEquals("1", orderResponse.getBody().getBasket().getId());
    assertEquals(PaymentStatus.AUTHORISED, orderResponse.getBody().getPaymentStatus());
    assertEquals(OrderStatus.COMPLETED, orderResponse.getBody().getOrderStatus());

    assertAll("address name",
        () -> assertEquals("Test Street", orderResponse.getBody().getCustomer().getInvoiceAddress().getAddressLine1()),
        () -> assertEquals("T3ST", orderResponse.getBody().getCustomer().getInvoiceAddress().getPostCode()),
        () -> assertEquals("England", orderResponse.getBody().getCustomer().getInvoiceAddress().getCountry()));

    assertAll("customer details", () -> assertEquals("1", orderResponse.getBody().getCustomer().getId()),
        () -> assertEquals("John", orderResponse.getBody().getCustomer().getName()),
        () -> assertEquals("cust1@gmail.com", orderResponse.getBody().getCustomer().getEmail()));

    assertAll("line items", () -> assertEquals("1", orderResponse.getBody().getBasket().getLineItems().get(0).getId()),
        () -> assertEquals("hammer", orderResponse.getBody().getBasket().getLineItems().get(0).getName()),
        () -> assertEquals("test", orderResponse.getBody().getBasket().getLineItems().get(0).getDescription()),
        () -> assertEquals(new BigDecimal("125.10"),
            orderResponse.getBody().getBasket().getLineItems().get(0).getUnitPrice()),
        () -> assertEquals(15, orderResponse.getBody().getBasket().getLineItems().get(0).getQuantity()));

  }


  @Test
  public void testFailedPOSTRequest() {
    Order failedPostOrder = Order.builder().build();
    String url = "http://localhost:" + port + "/orders";
    HttpEntity<Order> request = new HttpEntity<>(failedPostOrder);

    ResponseEntity<Order> response = restTemplate.exchange(url, HttpMethod.POST, request, Order.class);
    System.out.println(response);
    assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);

  }

  @Test
  public void testPATCHRequest() throws JSONException {
    // String url = "http://localhost:" + port + "/orders/1";
    //
    // JSONObject updateBody = new JSONObject();
    // updateBody.put("paymentStatus", "CAPTURED");
    //
    // ResponseEntity<Order> response = restTemplate.exchange(url, HttpMethod.PATCH,
    // buildRequest(updateBody.toString()), Order.class);
    //
    // assertEquals(response.getStatusCode(),HttpStatus.OK);
    //
    //// Order updatedOrder = response.getBody();
    //// assertEquals(PaymentStatus.CAPTURED.toString(),updatedOrder.getPaymentStatus());
    //
    //
    // }
    //
    // private HttpEntity buildRequest(String jsonPatchBody) {
    // List acceptTypes = new ArrayList();
    // acceptTypes.add(MediaType.APPLICATION_JSON);
    //
    // HttpHeaders reqHeaders = new HttpHeaders();
    // reqHeaders.setContentType(MediaType.APPLICATION_JSON);
    // reqHeaders.setAccept(acceptTypes);
    //
    //
    // return new HttpEntity(jsonPatchBody, reqHeaders);

  }
}
