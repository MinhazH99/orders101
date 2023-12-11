package com.minhaz.orders101.endpoints;

import com.fasterxml.jackson.core.json.JsonWriteFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.minhaz.orders101.models.Product;
import com.minhaz.orders101.models.ResponseModel;
import com.minhaz.orders101.service.ProductService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductsEndpointIntegrationTest {

  private String buildUrlWithId(String id) {
    return "http://localhost:" + port + "/orders/" + id;
  }

  private String buildUrlWithoutId() {
    return "http://localhost:" + port + "/orders/";
  }

  @LocalServerPort
  private int port;

  @Autowired
  private ProductService productService;

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
  @Disabled("UnknownContentTypeException")
  public void testGETOrder() {
    ResponseEntity<?> response = restTemplate.exchange(buildUrlWithId("1"), HttpMethod.GET, null, ResponseModel.class);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    var product = objectMapper.convertValue(((ResponseModel<?>) Objects.requireNonNull(response.getBody())).getData(),
        Product.class);
    assertThat(product).isNotNull();
    assertThat(product.getId()).isEqualTo("1");
    assertThat(product.getUnitPrice()).isEqualTo(new BigDecimal("15.5"));
    assertThat(product.getStockLevel()).isEqualTo(10);
    assertThat(product.getDescription()).isEqualTo("tool");
    assertThat(product.getName()).isEqualTo("screwdriver");

  }

  @Test
  @Disabled
  public void testPOSTRequest() {

  }

  @Test
  @Disabled
  public void testFailedPOSTRequest() {

  }

  @Test
  @Disabled
  public void testPATCHRequest() {

  }


  @Test
  @Disabled
  public void testFailedPATCHRequest() {

  }

  @Test
  @Disabled
  public void testDELETERequest() {

  }

  @Test
  @Disabled("can not reach end point")
  public void testDELETEWithNullId() {

  }

  @Test
  @Disabled
  public void testOrderNotFound() {

  }
}
