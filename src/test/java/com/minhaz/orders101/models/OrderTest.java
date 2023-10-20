package com.minhaz.orders101.models;

import com.minhaz.orders101.enums.OrderStatus;
import com.minhaz.orders101.enums.PaymentStatus;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

import static org.junit.jupiter.api.Assertions.*;

class OrderTest {

    @Test
    void builder() {
//        instantiate an order object
//        test validation
//        test the original values
//        amend the value and test the new values
        ProductItem productItem = ProductItem.builder().productId("3").quantity(5).build();
        Address address = Address.builder().addressLine1("Test Street").postCode("T3ST").country("England").build();
        LocalDateTime localDateTime = LocalDateTime.now();
        Order myOrder = Order.builder().id("myTestID").basket(productItem).deliveryAddress(address).totalPrice(new BigDecimal("125.12")).customerId("3").paymentStatus(PaymentStatus.AUTHROISED).orderStatus(OrderStatus.COMPLETED).createdDate(localDateTime).build();

        assertAll("address name",
                () -> assertEquals("Test Street",address.getAddressLine1()),
                () -> assertEquals("T3ST",address.getPostCode()),
                () -> assertEquals("England",address.getCountry()));

        assertAll("basket of goods",
                () -> assertEquals("3",productItem.getProductId()),
                () -> assertEquals(5,productItem.getQuantity()));

        assertEquals("myTestID",myOrder.getId());
        assertEquals(new BigDecimal("125.12"),myOrder.getTotalPrice());
        assertEquals("3",myOrder.getCustomerId());
        assertEquals(PaymentStatus.AUTHROISED,myOrder.getPaymentStatus());
        assertEquals(OrderStatus.COMPLETED,myOrder.getOrderStatus());
        assertTrue(localDateTime.isAfter(localDateTime.minusMonths(3)), "This test failed");


    }
}