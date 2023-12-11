package com.minhaz.orders101.endpoints;

import com.fasterxml.jackson.core.json.JsonWriteFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.minhaz.orders101.models.ResponseModel;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

// @SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductsEndpointIntegrationTest {

  private String buildUrlWithId() {
    return "http://localhost:" + port + "/orders/" + "1";
  }

  private String buildUrlWithoutId() {
    return "http://localhost:" + port + "/orders/";
  }

  @LocalServerPort
  private int port;


  private static ObjectMapper objectMapper = null;

  @BeforeAll
  public static void init() {
    objectMapper = new ObjectMapper();
    objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    objectMapper.disable(JsonWriteFeature.QUOTE_FIELD_NAMES.mappedFeature());
    objectMapper.registerModule(new JavaTimeModule());
  }


  @Test
  @Disabled
  public void testGETOrder() {

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
