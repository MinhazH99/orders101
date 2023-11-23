package com.minhaz.orders101.endpoints;

import static com.minhaz.orders101.utils.OrderUtils.sampleBasket;
import static com.minhaz.orders101.utils.OrderUtils.sampleCustomer;
import static com.minhaz.orders101.utils.OrderUtils.sampleDeliveryAddress;
import static com.minhaz.orders101.utils.OrderUtils.sampleOrder;
import static com.minhaz.orders101.utils.OrderUtils.sampleThreeLineItems;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import com.fasterxml.jackson.core.json.JsonWriteFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.minhaz.orders101.enums.OrderStatus;
import com.minhaz.orders101.enums.PaymentStatus;
import com.minhaz.orders101.models.Order;
import com.minhaz.orders101.models.ResponseModel;
import java.math.BigDecimal;
import java.util.Collections;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OrdersEndpointIntegrationTest {

  @LocalServerPort
  private int port;

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
  void testGETOrder() throws Exception {
    String url = "http://localhost:" + port + "/orders/1";
    ResponseEntity response = restTemplate.exchange(url, HttpMethod.GET, null, ResponseModel.class);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    var order = objectMapper.convertValue(((ResponseModel) response.getBody()).getData(), Order.class);
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
    // Retrieving sample order but changing the ID to prevent primary key violations with the existing order in database
    var order = sampleOrder().id("2").customer(sampleCustomer().id("2").build())
        .deliveryAddress(sampleDeliveryAddress().id("3").build())
        .basket(sampleBasket().id("2").lineItems(sampleThreeLineItems(new int[] {4, 5, 6})).build()).build();
    String url = "http://localhost:" + port + "/orders";
    HttpEntity<Order> request = new HttpEntity<>(order);
    var response = restTemplate.exchange(url, HttpMethod.POST, request, ResponseModel.class);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    // TODO - test response
  }

  @Test
  public void testFailedPOSTRequest() {
    Order failedPostOrder = Order.builder().build();
    String url = "http://localhost:" + port + "/orders";
    HttpEntity<Order> request = new HttpEntity<>(failedPostOrder);
    ResponseEntity response = restTemplate.exchange(url, HttpMethod.POST, request, ResponseModel.class);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    var errors = ((ResponseModel) response.getBody()).getErrors();
    assertThat(errors.size()).isEqualTo(3);
    assertThat(errors).contains(Collections.singletonMap("saveOrder.order.paymentStatus", "must not be null"));
    assertThat(errors).contains(Collections.singletonMap("saveOrder.order.orderStatus", "must not be null"));
    assertThat(errors).contains(Collections.singletonMap("saveOrder.order.deliveryAddress", "must not be null"));
  }

  @Test
  public void testPATCHRequest() {
    String url = "http://localhost:" + port + "/orders";
    var order = sampleOrder().paymentStatus(PaymentStatus.CAPTURED).build();
    ResponseEntity response =
        restTemplate.exchange(url, HttpMethod.PATCH, new HttpEntity<>(order), ResponseModel.class);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    // TODO - test order data in response
  }


  @Test
  public void testFailedPATCHRequest() {
    String url = "http://localhost:" + port + "/orders";
    var order = sampleOrder().paymentStatus(null).build();
    ResponseEntity response =
        restTemplate.exchange(url, HttpMethod.PATCH, new HttpEntity<>(order), ResponseModel.class);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    var errors = ((ResponseModel) response.getBody()).getErrors();
    assertThat(errors.size()).isOne();
    assertThat(errors).contains(Collections.singletonMap("updateOrder.updatedOrder.paymentStatus", "must not be null"));
  }

  @Test
  @Disabled("this test will delete the record with id 1 used by the other tests!")
  public void testDeleteRequest() {
    var response =
        restTemplate.exchange("http://localhost:" + port + "/orders/1", HttpMethod.DELETE, null, String.class);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
  }

  @Test
  public void testOrderNotFound() {
    var response =
        restTemplate.exchange("http://localhost:" + port + "/orders/2", HttpMethod.GET, null, ResponseModel.class);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    assertThat(((ResponseModel) response.getBody()).getErrors())
        .contains(Collections.singletonMap("error", "Order with id 2 not found."));
  }

}
