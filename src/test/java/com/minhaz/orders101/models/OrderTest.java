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
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;


class OrderTest {
    private Order orderUnderTest;

    @BeforeEach
    void instantiateOrder() {
        ProductItem productItem = ProductItem.builder().productId("3").quantity(5).build();
        List<ProductItem> items = new ArrayList<>();
        items.add(productItem);
        Address address = Address.builder().addressLine1("Test Street").postCode("T3ST").country("England").build();
        Date date = new Date();
        orderUnderTest = Order.builder().id("myTestID").basket(items).deliveryAddress(address).totalPrice(new BigDecimal("125.12")).customerId("3").paymentStatus(PaymentStatus.AUTHORISED).orderStatus(OrderStatus.COMPLETED).createdDate(date).build();
    }

    @Test
    void testGetters() {
        assertAll("address name",
                () -> assertEquals("Test Street", orderUnderTest.getDeliveryAddress().getAddressLine1()),
                () -> assertEquals("T3ST", orderUnderTest.getDeliveryAddress().getPostCode()),
                () -> assertEquals("England", orderUnderTest.getDeliveryAddress().getCountry()));

        assertAll("basket of goods",
                () -> assertEquals("3", orderUnderTest.getBasket().get(0).getProductId()),
                () -> assertEquals(5, orderUnderTest.getBasket().get(0).getQuantity()));

        assertEquals("myTestID", orderUnderTest.getId());
        assertEquals(new BigDecimal("125.12"), orderUnderTest.getTotalPrice());
        assertEquals("3", orderUnderTest.getCustomerId());
        assertEquals(PaymentStatus.AUTHORISED, orderUnderTest.getPaymentStatus());
        assertEquals(OrderStatus.COMPLETED, orderUnderTest.getOrderStatus());
        Date testDate = Date.from(LocalDate.now().minusMonths(3).atStartOfDay(ZoneId.systemDefault()).toInstant());
        assertTrue(orderUnderTest.getCreatedDate().after(testDate), "This test has failed as order date falls before the (current date - 3 month ) range");

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

        assertThat(results.size()).isOne();
        var violation = results.stream().findFirst().get();
        assertThat(violation.getMessage()).isEqualTo("Primary key cannot be null");
    }
    
    @Test
    void testDateValidation() {
        orderUnderTest.setCreatedDate(Date.from(LocalDate.now().plusDays(1).atStartOfDay()
            .toInstant(ZoneOffset.UTC)));
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