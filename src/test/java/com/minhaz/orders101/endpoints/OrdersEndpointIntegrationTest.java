package com.minhaz.orders101.endpoints;

import com.fasterxml.jackson.core.json.JsonWriteFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.minhaz.orders101.enums.OrderStatus;
import com.minhaz.orders101.enums.PaymentStatus;
import com.minhaz.orders101.models.Order;
import com.minhaz.orders101.models.ResponseModel;
import com.minhaz.orders101.service.OrderService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Objects;

import static com.minhaz.orders101.utils.OrderUtils.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OrdersEndpointIntegrationTest {

  private String buildUrlWithId(String id) {
    return "http://localhost:" + port + "/orders/" + id;
  }

  private String buildUrlWithoutId() {
    return "http://localhost:" + port + "/orders/";
  }

  @LocalServerPort
  private int port;

  @Autowired
  private OrderService orderService;


  @Autowired
  private TestRestTemplate restTemplate;

  private static ObjectMapper objectMapper = null;

  @BeforeAll
  public static void init() {
    objectMapper = new ObjectMapper();
    objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    objectMapper.disable(JsonWriteFeature.QUOTE_FIELD_NAMES.mappedFeature());
    objectMapper.registerModule(new JavaTimeModule());
  }


  @Test
  public void testGETOrder() {
    ResponseEntity<?> response = restTemplate.exchange(buildUrlWithId("1"), HttpMethod.GET, null, ResponseModel.class);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    var order = objectMapper.convertValue(((ResponseModel<?>) Objects.requireNonNull(response.getBody())).getData(),
        Order.class);
    assertThat(order).isNotNull();
    assertThat(order.getId()).isEqualTo("1");
    assertThat(order.getBasket().getId()).isEqualTo("1");
    assertThat(order.getPaymentStatus()).isEqualTo(PaymentStatus.AUTHORISED);
    assertThat(order.getOrderStatus()).isEqualTo(OrderStatus.COMPLETED);
    assertAll("address name",
        () -> assertEquals("Test Street", order.getCustomer().getInvoiceAddress().getAddressLine1()),
        () -> assertEquals("T3ST", order.getCustomer().getInvoiceAddress().getPostCode()),
        () -> assertEquals("England", order.getCustomer().getInvoiceAddress().getCountry()));
    assertAll("customer details", () -> assertEquals("1", order.getCustomer().getId()),
        () -> assertEquals("John", order.getCustomer().getName()),
        () -> assertEquals("cust1@gmail.com", order.getCustomer().getEmail()));
    assertAll("line items", () -> assertEquals("1", order.getBasket().getLineItems().get(0).getId()),
        () -> assertEquals("hammer", order.getBasket().getLineItems().get(0).getName()),
        () -> assertEquals("test", order.getBasket().getLineItems().get(0).getDescription()),
        () -> assertEquals(new BigDecimal("125.1"), order.getBasket().getLineItems().get(0).getUnitPrice()),
        () -> assertEquals(15, order.getBasket().getLineItems().get(0).getQuantity()));
  }

  @Test
  public void testPOSTRequest() {
    var order = sampleOrder().id("3")
        .customer(sampleCustomer().id("3").invoiceAddress(sampleInvoiceAddress().id("3").build()).build())
        .deliveryAddress(sampleDeliveryAddress().id("4").build())
        .basket(sampleBasket().id("3").lineItems(sampleThreeLineItems(new int[] {7, 8, 9})).build()).build();
    var response =
        restTemplate.exchange(buildUrlWithoutId(), HttpMethod.POST, new HttpEntity<>(order), ResponseModel.class);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    var orderFromResponse = objectMapper
        .convertValue(((ResponseModel<?>) Objects.requireNonNull(response.getBody())).getData(), Order.class);
    assertThat(orderFromResponse).isNotNull();
    assertThat(orderFromResponse.getPaymentStatus()).isEqualTo(PaymentStatus.AUTHORISED);
    assertThat(orderFromResponse.getOrderStatus()).isEqualTo(OrderStatus.COMPLETED);
    assertAll("address name",
        () -> assertEquals("Test Invoice Street",
            orderFromResponse.getCustomer().getInvoiceAddress().getAddressLine1()),
        () -> assertEquals("SO24 8AH", orderFromResponse.getCustomer().getInvoiceAddress().getPostCode()),
        () -> assertEquals("England", orderFromResponse.getCustomer().getInvoiceAddress().getCountry()));
    assertAll("customer details", () -> assertEquals("John", orderFromResponse.getCustomer().getName()),
        () -> assertEquals("cust1@gmail.com", orderFromResponse.getCustomer().getEmail()));
    assertAll("line items", () -> assertEquals("hammer", orderFromResponse.getBasket().getLineItems().get(0).getName()),
        () -> assertEquals("test", orderFromResponse.getBasket().getLineItems().get(0).getDescription()),
        () -> assertEquals(new BigDecimal("125.1"), orderFromResponse.getBasket().getLineItems().get(0).getUnitPrice()),
        () -> assertEquals(15, orderFromResponse.getBasket().getLineItems().get(0).getQuantity()));
  }

  @Test
  public void testFailedPOSTRequest() {
    Order failedPostOrder = sampleOrder().paymentStatus(null).orderStatus(null).deliveryAddress(null).build();
    ResponseEntity<?> response = restTemplate.exchange(buildUrlWithoutId(), HttpMethod.POST,
        new HttpEntity<>(failedPostOrder), ResponseModel.class);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    var errors = ((ResponseModel<?>) Objects.requireNonNull(response.getBody())).getErrors();
    assertThat(errors.size()).isEqualTo(3);
    assertThat(errors).contains(Collections.singletonMap("saveOrder.order.paymentStatus", "must not be null"));
    assertThat(errors).contains(Collections.singletonMap("saveOrder.order.orderStatus", "must not be null"));
    assertThat(errors).contains(Collections.singletonMap("saveOrder.order.deliveryAddress", "must not be null"));
  }

  @Test
  public void testPATCHRequest() {
    var order = sampleOrder().paymentStatus(PaymentStatus.CAPTURED).build();
    ResponseEntity<?> response =
        restTemplate.exchange(buildUrlWithId("1"), HttpMethod.PATCH, new HttpEntity<>(order), ResponseModel.class);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    var orderFromResponse = objectMapper
        .convertValue(((ResponseModel<?>) Objects.requireNonNull(response.getBody())).getData(), Order.class);
    assertThat(orderFromResponse.getPaymentStatus()).isEqualTo(PaymentStatus.CAPTURED);
  }


  @Test
  public void testFailedPATCHRequest() {
    var order = sampleOrder().paymentStatus(null).build();
    ResponseEntity<?> response =
        restTemplate.exchange(buildUrlWithId("1"), HttpMethod.PATCH, new HttpEntity<>(order), ResponseModel.class);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    var errors = ((ResponseModel<?>) Objects.requireNonNull(response.getBody())).getErrors();
    assertThat(errors.size()).isOne();
    assertThat(errors).contains(Collections.singletonMap("updateOrder.updatedOrder.paymentStatus", "must not be null"));
  }

  @Test
  public void testDELETERequest() {
    var response = restTemplate.exchange(buildUrlWithId("2"), HttpMethod.DELETE, null, String.class);
    assertThat(orderService.retrieveById("2")).isEmpty();
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
  }

  @Test
  public void testDELETEWithNullId() {
    // TODO throws IllegalArgumentException
  }

  @Test
  public void testOrderNotFound() {
    var response = restTemplate.exchange(buildUrlWithId("3"), HttpMethod.GET, null, ResponseModel.class);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    assertThat(((ResponseModel<?>) Objects.requireNonNull(response.getBody())).getErrors())
        .contains(Collections.singletonMap("error", "Order with id 3 not found."));
  }
}
