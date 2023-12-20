package com.minhaz.orders101.endpoints;

import com.fasterxml.jackson.core.json.JsonWriteFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.minhaz.orders101.models.Product;
import com.minhaz.orders101.models.ResponseModel;
import com.minhaz.orders101.models.StockAvailability;
import com.minhaz.orders101.service.ProductService;
import org.junit.jupiter.api.*;
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

import static com.minhaz.orders101.utils.ProductUtils.sampleProduct;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProductsEndpointIntegrationTest {

  private String buildUrlWithId(String id) {
    return "http://localhost:" + port + "/products/" + id;
  }

  private String buildUrlWithoutId() {
    return "http://localhost:" + port + "/products/";
  }


  private String buildStockAvailabilityUrl(String id, Integer qty) {
    return "http://localhost:" + port + "/products/stock-availability/" + id + "?qty=" + qty;
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
  @Order(1)
  public void testGETProduct() {
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
  @Order(2)
  public void testGETStockAvailabilityWhereQuantityIsAvailable() {
    ResponseEntity<?> response =
        restTemplate.exchange(buildStockAvailabilityUrl("1", 6), HttpMethod.GET, null, ResponseModel.class);

    var stockAvailabilityResponse = objectMapper.convertValue(
        ((ResponseModel<?>) Objects.requireNonNull(response.getBody())).getData(), StockAvailability.class);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertAll("stock availability model", () -> assertEquals(6, stockAvailabilityResponse.getRequestQuantity()),
        () -> assertTrue(stockAvailabilityResponse.isAvailable()));
  }

  @Test
  @Order(3)
  public void testGETStockAvailabilityWhereQuantityIsNotAvailable() {
    ResponseEntity<?> response =
        restTemplate.exchange(buildStockAvailabilityUrl("1", 11), HttpMethod.GET, null, ResponseModel.class);

    var stockAvailabilityResponse = objectMapper.convertValue(
        ((ResponseModel<?>) Objects.requireNonNull(response.getBody())).getData(), StockAvailability.class);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertAll("stock availability model", () -> assertEquals(11, stockAvailabilityResponse.getRequestQuantity()),
        () -> assertFalse(stockAvailabilityResponse.isAvailable()));
  }



  @Test
  @Order(4)
  public void testPOSTRequest() {
    var product = sampleProduct().id("3").unitPrice(new BigDecimal("56.25")).stockLevel(24).description("test")
        .name("test").build();
    var response =
        restTemplate.exchange(buildUrlWithoutId(), HttpMethod.POST, new HttpEntity<>(product), ResponseModel.class);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    var productFromResponse = objectMapper
        .convertValue(((ResponseModel<?>) Objects.requireNonNull(response.getBody())).getData(), Product.class);
    assertThat(productFromResponse).isNotNull();
    assertThat(productFromResponse.getUnitPrice()).isEqualTo(new BigDecimal("56.25"));
    assertThat(productFromResponse.getStockLevel()).isEqualTo(24);
    assertThat(productFromResponse.getDescription()).isEqualTo("test");
    assertThat(productFromResponse.getName()).isEqualTo("test");
  }

  @Test
  @Order(5)
  public void testFailedPOSTRequest() {
    var failedPostProduct = sampleProduct().id("3").stockLevel(null).name(null).build();
    ResponseEntity<?> response = restTemplate.exchange(buildUrlWithoutId(), HttpMethod.POST,
        new HttpEntity<>(failedPostProduct), ResponseModel.class);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    var errors = ((ResponseModel<?>) Objects.requireNonNull(response.getBody())).getErrors();
    assertThat(errors.size()).isEqualTo(2);
    assertThat(errors).contains(Collections.singletonMap("saveProduct.product.name", "must not be null"));
    assertThat(errors).contains(Collections.singletonMap("saveProduct.product.stockLevel", "must not be null"));
  }

  @Test
  @Order(6)
  public void testPATCHRequest() {
    var product = sampleProduct().build();
    ResponseEntity<?> response = restTemplate.exchange(buildStockAvailabilityUrl("1", 5), HttpMethod.PATCH,
        new HttpEntity<>(product), ResponseModel.class);
    var stockAvailabilityResponse = objectMapper.convertValue(
        ((ResponseModel<?>) Objects.requireNonNull(response.getBody())).getData(), StockAvailability.class);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertAll("stock availability model", () -> assertEquals(5, stockAvailabilityResponse.getRequestQuantity()),
        () -> assertTrue(stockAvailabilityResponse.isAvailable()));
  }


  @Test
  @Order(7)
  public void testFailedPATCHRequestWithNegativeQuantity() {
    var product = sampleProduct().build();
    ResponseEntity<?> response = restTemplate.exchange(buildStockAvailabilityUrl("1", -5), HttpMethod.PATCH,
        new HttpEntity<>(product), ResponseModel.class);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    var errors = ((ResponseModel<?>) Objects.requireNonNull(response.getBody())).getErrors();
    assertThat(errors.size()).isEqualTo(1);
    assertThat(errors).contains(Collections.singletonMap("updateStock.qty", "The value must be 0 or greater"));
  }

  @Test
  @Order(6)
  public void testFailedPATCHRequestWithNullQuantityParam() {
    var product = sampleProduct().build();
    String failedPatchUrl = "http://localhost:" + port + "/products/stock-availability/" + product.getId() + "?qty=";
    ResponseEntity<?> response =
        restTemplate.exchange(failedPatchUrl, HttpMethod.PATCH, new HttpEntity<>(product), ResponseModel.class);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    var errors = ((ResponseModel<?>) Objects.requireNonNull(response.getBody())).getErrors();
    assertThat(errors.size()).isEqualTo(1);
    assertThat(errors).contains(Collections.singletonMap("updateStock.qty", "must not be null"));
  }

  @Test
  @Order(8)
  public void testDELETERequest() {
    System.out.println(buildUrlWithId("2"));
    var response = restTemplate.exchange(buildUrlWithId("2"), HttpMethod.DELETE, null, ResponseModel.class);
    assertThat(productService.retrieveById("2")).isEmpty();
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
  }
}
