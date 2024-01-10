package com.minhaz.orders101.model;

import com.minhaz.orders101.status.OrderStatus;
import com.minhaz.orders101.status.PaymentStatus;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;
import static com.minhaz.orders101.util.OrderUtils.sampleOrder;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


class OrderTest {
  private Order orderUnderTest;

  @BeforeEach
  void instantiateOrder() {
    // Address address = Address.builder().id("1").addressLine1("Test
    // Street").postCode("T3ST").country("England").build();
    // Customer customer =
    // Customer.builder().id("1").email("test@gmail.com").name("John").invoiceAddress(address).build();
    //
    // LineItem hammer = LineItem.builder().name("Hammer").description("hit screws").quantity(5).id("1")
    // .unitPrice(BigDecimal.valueOf(50.00)).build();
    // LineItem screwdriver = LineItem.builder().name("Screwdriver").description("test").quantity(6).id("2")
    // .unitPrice(BigDecimal.valueOf(100.12)).build();
    // List<LineItem> products = new ArrayList<>();
    // products.add(hammer);
    // products.add(screwdriver);
    //
    // Basket basket = Basket.builder().id("1").lineItems(products).build();
    //
    // LocalDate date = LocalDate.now().minusDays(1);
    //
    // orderUnderTest = Order.builder().id("1234").totalPrice(new BigDecimal("125.12")).customer(customer)
    // .paymentStatus(PaymentStatus.AUTHORISED).orderStatus(OrderStatus.COMPLETED).basket(basket).createdDate(date)
    // .deliveryAddress(address).build();
    orderUnderTest = sampleOrder().build();
  }

  @Test
  void testGetters() {
    assertAll("Customer details", () -> assertEquals("1", orderUnderTest.getCustomer().getId()),
        () -> assertEquals("cust1@gmail.com", orderUnderTest.getCustomer().getEmail()),
        () -> assertEquals("John", orderUnderTest.getCustomer().getName()));
    assertAll("Invoice address",
        () -> assertEquals("Test Invoice Street", orderUnderTest.getCustomer().getInvoiceAddress().getAddressLine1()),
        () -> assertEquals("SO24 8AH", orderUnderTest.getCustomer().getInvoiceAddress().getPostCode()),
        () -> assertEquals("England", orderUnderTest.getCustomer().getInvoiceAddress().getCountry()));

    assertAll("Delivery address",
        () -> assertEquals("Test Delivery Street", orderUnderTest.getDeliveryAddress().getAddressLine1()),
        () -> assertEquals("RG24 3KL", orderUnderTest.getDeliveryAddress().getPostCode()),
        () -> assertEquals("England", orderUnderTest.getDeliveryAddress().getCountry()));

    assertAll("basket of goods", () -> assertEquals("1", orderUnderTest.getBasket().getId()),
        () -> assertEquals("1", orderUnderTest.getBasket().getLineItems().get(0).getId()),
        () -> assertEquals("hammer", orderUnderTest.getBasket().getLineItems().get(0).getName()),
        () -> assertEquals("test", orderUnderTest.getBasket().getLineItems().get(0).getDescription()),
        () -> assertEquals(15, orderUnderTest.getBasket().getLineItems().get(0).getQuantity()),
        () -> assertEquals(0,
            new BigDecimal("125.1").compareTo(orderUnderTest.getBasket().getLineItems().get(0).getUnitPrice())),
        () -> assertEquals("2", orderUnderTest.getBasket().getLineItems().get(1).getId()),
        () -> assertEquals("screwdriver", orderUnderTest.getBasket().getLineItems().get(1).getName()),
        () -> assertEquals("test1", orderUnderTest.getBasket().getLineItems().get(1).getDescription()),
        () -> assertEquals(25, orderUnderTest.getBasket().getLineItems().get(1).getQuantity()),
        () -> assertEquals(new BigDecimal("100.0"), orderUnderTest.getBasket().getLineItems().get(1).getUnitPrice()),
        () -> assertEquals("3", orderUnderTest.getBasket().getLineItems().get(2).getId()),
        () -> assertEquals("drill", orderUnderTest.getBasket().getLineItems().get(2).getName()),
        () -> assertEquals("test2", orderUnderTest.getBasket().getLineItems().get(2).getDescription()),
        () -> assertEquals(40, orderUnderTest.getBasket().getLineItems().get(2).getQuantity()),
        () -> assertEquals(new BigDecimal("65.0"), orderUnderTest.getBasket().getLineItems().get(2).getUnitPrice()));

    assertEquals("1", orderUnderTest.getId());
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
  // Do we still need this? Would it make more sense to instead set the fields which have @NotNull to null?
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
