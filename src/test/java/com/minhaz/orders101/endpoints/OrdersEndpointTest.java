package com.minhaz.orders101.endpoints;

import static com.minhaz.orders101.utils.OrderUtils.sampleDeliveryAddress;
import static com.minhaz.orders101.utils.OrderUtils.sampleBasket;
import static com.minhaz.orders101.utils.OrderUtils.sampleCustomer;
import static com.minhaz.orders101.utils.OrderUtils.sampleOrder;
import static com.minhaz.orders101.utils.OrderUtils.sampleThreeLineItems;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import com.minhaz.orders101.enums.OrderStatus;
import com.minhaz.orders101.enums.PaymentStatus;
import com.minhaz.orders101.models.Order;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OrdersEndpointTest {

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
  public void testPOSTRequest() {
    // Retrieving sample order but changing the ID to prevent primary key violations with the existing order in database
    var order = sampleOrder().id("2").customer(sampleCustomer().id("2").build())
        .deliveryAddress(sampleDeliveryAddress().id("3").build())
        .basket(sampleBasket().id("2").lineItems(sampleThreeLineItems(new int[] {4, 5, 6})).build()).build();
    String url = "http://localhost:" + port + "/orders";
    HttpEntity<Order> request = new HttpEntity<>(order);
    var response = restTemplate.exchange(url, HttpMethod.POST, request, Order.class);
    assertEquals(response.getStatusCode(), HttpStatus.OK);

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
  public void testPATCHRequest() {
    String url = "http://localhost:" + port + "/orders";
    var order = sampleOrder().paymentStatus(PaymentStatus.CAPTURED).build();
    ResponseEntity response = restTemplate.exchange(url, HttpMethod.PATCH, new HttpEntity<>(order), Order.class);
    System.out.println(response);
    assertEquals(response.getStatusCode(), HttpStatus.OK);
  }


  @Test
  public void testFailedPATCHRequest() {
    String url = "http://localhost:" + port + "/orders";
    var order = sampleOrder().paymentStatus(null).build();
    ResponseEntity response = restTemplate.exchange(url, HttpMethod.PATCH, new HttpEntity<>(order), Order.class);
    assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
  }

  @Test
  public void testDeleteRequest() {

  }


  @Test
  public void testOrderNotFound() {
    var response = restTemplate.exchange("http://localhost:" + port + "/orders/2", HttpMethod.GET, null, String.class);
    assert (response.getStatusCode()).is4xxClientError();
  }

}
