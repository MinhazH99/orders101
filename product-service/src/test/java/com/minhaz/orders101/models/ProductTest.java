package com.minhaz.orders101.models;


import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static com.minhaz.orders101.utils.ProductUtils.sampleProduct;
import static org.junit.jupiter.api.Assertions.*;


class ProductTest {

  private Product productUnderTest;

  @BeforeEach
  void instantiateProduct() {
    productUnderTest = sampleProduct().build();
  }



  @Test
  void testGetters() {
    assertAll("Product details", () -> assertEquals("1", productUnderTest.getId()),
        () -> assertEquals(10, productUnderTest.getStockLevel()),
        () -> assertEquals(new BigDecimal("15.50"), productUnderTest.getUnitPrice()),
        () -> assertEquals("tool", productUnderTest.getDescription()),
        () -> assertEquals("screwdriver", productUnderTest.getName()));

  }

  @Test
  @Disabled
  void testSetters() {

  }

  @Test
  @Disabled
  void testIdValidation() {

  }

  @Test
  @Disabled
  void testDateValidation() {}

}
