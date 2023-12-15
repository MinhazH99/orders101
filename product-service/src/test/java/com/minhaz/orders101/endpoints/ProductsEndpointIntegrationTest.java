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

  private String buildPatchUrl(String id, Integer qty) {
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
  @Disabled
  @Order(3)
  public void testFailedPOSTRequest() {
    var failedPostProduct = sampleProduct().id("3").stockLevel(null).name(null);
    System.out.println(buildUrlWithoutId());
    ResponseEntity<?> response = restTemplate.exchange(buildUrlWithoutId(), HttpMethod.POST,
        new HttpEntity<>(failedPostProduct), ResponseModel.class);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    var errors = ((ResponseModel<?>) Objects.requireNonNull(response.getBody())).getErrors();
    assertThat(errors.size()).isEqualTo(2);
  }

  @Test
  @Order(4)
  public void testPATCHRequest() {
    var product = sampleProduct().build();
    ResponseEntity<?> response =
        restTemplate.exchange(buildPatchUrl("1", 5), HttpMethod.PATCH, new HttpEntity<>(product), ResponseModel.class);
    var stockAvailabilityResponse = objectMapper.convertValue(
        ((ResponseModel<?>) Objects.requireNonNull(response.getBody())).getData(), StockAvailability.class);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertAll("stock availability model", () -> assertEquals(5, stockAvailabilityResponse.getRequestQuantity()),
        () -> assertTrue(stockAvailabilityResponse.isAvailable()));
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
