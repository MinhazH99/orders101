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
    Address address =
        Address.builder().address_id("1").addressLine1("Test Street").postCode("T3ST").country("England").build();
    List<Address> addressList = new ArrayList<>();
    addressList.add(address);
    Customer customer =
        Customer.builder().customerId("1").email("test@gmail.com").name("John").addressList(addressList).build();

    LineItem hammer = LineItem.builder().name("Hammer").description("hit screws").quantity(5).productId("1")
        .unitPrice(BigDecimal.valueOf(50.00)).build();
    LineItem screwdriver = LineItem.builder().name("Screwdriver").description("test").quantity(6).productId("2")
        .unitPrice(BigDecimal.valueOf(100.12)).build();
    List<LineItem> products = new ArrayList<>();
    products.add(hammer);
    products.add(screwdriver);

    Basket basket = Basket.builder().basketId("1").product(products).build();

    LocalDate date = LocalDate.now().minusDays(1);

    orderUnderTest = Order.builder().orderId("1234").totalPrice(new BigDecimal("125.12")).customer(customer)
        .paymentStatus(PaymentStatus.AUTHORISED).orderStatus(OrderStatus.COMPLETED).basket(basket).createdDate(date)
        .build();
  }

  @Test
  void testGetters() {
    assertAll("address name",
        () -> assertEquals("Test Street", orderUnderTest.getCustomer().getAddressList().get(0).getAddressLine1()),
        () -> assertEquals("T3ST", orderUnderTest.getCustomer().getAddressList().get(0).getPostCode()),
        () -> assertEquals("England", orderUnderTest.getCustomer().getAddressList().get(0).getCountry()));

    assertAll("basket of goods", () -> assertEquals("1", orderUnderTest.getBasket().getBasketId()),
        () -> assertEquals("1", orderUnderTest.getBasket().getProduct().get(0).getProductId()));

    assertEquals("1234", orderUnderTest.getOrderId());
    assertEquals(new BigDecimal("125.12"), orderUnderTest.getTotalPrice());
    assertEquals("1", orderUnderTest.getCustomer().getCustomerId());
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
    orderUnderTest.setOrderId(null);
    var results = validate(orderUnderTest);

    assertThat(results.size()).isOne();
    var violation = results.stream().findFirst().get();
    assertThat(violation.getMessage()).isEqualTo("Primary key cannot be null");
  }

  @Test
  void testDateValidation() {
    orderUnderTest.setCreatedDate(LocalDate.now().plusDays(1));
    var results = validate(orderUnderTest);

    assertThat(results.size()).isOne();
    var violation = results.stream().findFirst().get();
    assertThat(violation.getMessage()).isEqualTo("must be a past date");
  }

  Collection<ConstraintViolation<Order>> validate(Order order) {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();

    return validator.validate(order);
  }

}
