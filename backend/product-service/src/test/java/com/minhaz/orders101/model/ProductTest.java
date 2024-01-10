package com.minhaz.orders101.model;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.util.Collection;
import static com.minhaz.orders101.util.ProductUtils.sampleProduct;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;


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
  void testSetters() {
    assertEquals("tool", productUnderTest.getDescription());
    productUnderTest.setDescription("test");
    assertEquals("test", productUnderTest.getDescription());
  }

  @Test
  void testIdValidation() {
    productUnderTest.setId(null);
    var results = validate(productUnderTest);
    assertThat(results.size()).isZero();
  }

  Collection<ConstraintViolation<Product>> validate(Product product) {
    try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
      Validator validator = factory.getValidator();
      return validator.validate(product);
    }
  }
}
