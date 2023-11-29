package com.minhaz.orders101.models;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.minhaz.orders101.enums.OrderStatus;
import com.minhaz.orders101.enums.PaymentStatus;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class OrderTest {
  private Order orderUnderTest;

  @BeforeEach
  void instantiateOrder() {
    Address address = Address.builder().id("1").addressLine1("Test Street").postCode("T3ST").country("England").build();
    Customer customer = Customer.builder().id("1").email("test@gmail.com").name("John").invoiceAddress(address).build();

    LineItem hammer = LineItem.builder().name("Hammer").description("hit screws").quantity(5).id("1")
        .unitPrice(BigDecimal.valueOf(50.00)).build();
    LineItem screwdriver = LineItem.builder().name("Screwdriver").description("test").quantity(6).id("2")
        .unitPrice(BigDecimal.valueOf(100.12)).build();
    List<LineItem> products = new ArrayList<>();
    products.add(hammer);
    products.add(screwdriver);

    Basket basket = Basket.builder().id("1").lineItems(products).build();

    LocalDate date = LocalDate.now().minusDays(1);

    orderUnderTest = Order.builder().id("1234").totalPrice(new BigDecimal("125.12")).customer(customer)
        .paymentStatus(PaymentStatus.AUTHORISED).orderStatus(OrderStatus.COMPLETED).basket(basket).createdDate(date)
        .deliveryAddress(address).build();
  }

  @Test
  void testGetters() {
    assertAll("address name",
        () -> assertEquals("Test Street", orderUnderTest.getCustomer().getInvoiceAddress().getAddressLine1()),
        () -> assertEquals("T3ST", orderUnderTest.getCustomer().getInvoiceAddress().getPostCode()),
        () -> assertEquals("England", orderUnderTest.getCustomer().getInvoiceAddress().getCountry()));

    assertAll("basket of goods", () -> assertEquals("1", orderUnderTest.getBasket().getId()),
        () -> assertEquals("1", orderUnderTest.getBasket().getLineItems().get(0).getId()));

    assertEquals("1234", orderUnderTest.getId());
    assertEquals(new BigDecimal("125.12"), orderUnderTest.getTotalPrice());
    assertEquals("1", orderUnderTest.getCustomer().getId());
    assertEquals(PaymentStatus.AUTHORISED, orderUnderTest.getPaymentStatus());
    assertEquals(OrderStatus.COMPLETED, orderUnderTest.getOrderStatus());
    LocalDate testDate = LocalDate.now().minusMonths(3);
    assertTrue(orderUnderTest.getCreatedDate().isAfter(testDate),
        "This test has failed as order date falls before the (current date - 3 month ) range");

  }

  @Test
  void testSetters() {
    assertEquals(PaymentStatus.AUTHORISED, orderUnderTest.getPaymentStatus());
    orderUnderTest.setPaymentStatus(PaymentStatus.CAPTURED);
    assertEquals(PaymentStatus.CAPTURED, orderUnderTest.getPaymentStatus());


  }

  @Test
  void testIdValidation() {
    orderUnderTest.setId(null);
    var results = validate(orderUnderTest);

    assertThat(results.size()).isZero();
  }

  @Test
  void testDateValidation() {
    orderUnderTest.setCreatedDate(LocalDate.now().plusDays(1));
    var results = validate(orderUnderTest);
    assertThat(results.size()).isOne();
    results.stream().findFirst()
        .ifPresent(violation -> assertThat(violation.getMessage()).isEqualTo("must be a past date"));
  }

  Collection<ConstraintViolation<Order>> validate(Order order) {
    try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
      Validator validator = factory.getValidator();
      return validator.validate(order);
    }
  }

}
