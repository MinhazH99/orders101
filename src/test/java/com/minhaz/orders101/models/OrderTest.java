package com.minhaz.orders101.models;

import com.minhaz.orders101.enums.OrderStatus;
import com.minhaz.orders101.enums.PaymentStatus;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
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
        List<ProductItem> items = new ArrayList<>();
        items.add(productItem);
        Address address = Address.builder().addressLine1("Test Street").postCode("T3ST").country("England").build();
        Date date = new Date();
        Order myOrder = Order.builder().id("myTestID").basket(items).deliveryAddress(address).totalPrice(new BigDecimal("125.12")).customerId("3").paymentStatus(PaymentStatus.AUTHROISED).orderStatus(OrderStatus.COMPLETED).createdDate(date).build();

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
//        assertTrue(localDateTime.isAfter(localDateTime.minusMonths(3)), "This test failed");
        Date testDate = Date.from(LocalDate.now().minusMonths(3).atStartOfDay(ZoneId.systemDefault()).toInstant());
        assertTrue(date.after(testDate),"This test has failed");


        // Changing items within basket and testing
        myOrder.setId("2");

        ProductItem newProductItem = ProductItem.builder().productId("5").quantity(10).build();
        List<ProductItem> newItems = new ArrayList<>();
        newItems.add(newProductItem);
        myOrder.setBasket(newItems);

        Address newAddress = Address.builder().addressLine1("New Test Street").postCode("N3W").country("Scotland").build();
        myOrder.setDeliveryAddress(newAddress);

        myOrder.setTotalPrice(BigDecimal.valueOf(150.12));
        myOrder.setCustomerId("15");
        myOrder.setPaymentStatus(PaymentStatus.CAPTURED);
        myOrder.setOrderStatus(OrderStatus.CREATED);
        myOrder.setCreatedDate(date);

        assertAll("address name",
                () -> assertEquals("New Test Street",newAddress.getAddressLine1()),
                () -> assertEquals("N3W",newAddress.getPostCode()),
                () -> assertEquals("Scotland",newAddress.getCountry()));

        assertAll("basket of goods",
                () -> assertEquals("5",newProductItem.getProductId()),
                () -> assertEquals(10,newProductItem.getQuantity()));

        assertEquals("2",myOrder.getId());
        assertEquals(new BigDecimal("150.12"),myOrder.getTotalPrice());
        assertEquals("15",myOrder.getCustomerId());
        assertEquals(PaymentStatus.CAPTURED,myOrder.getPaymentStatus());
        assertEquals(OrderStatus.CREATED,myOrder.getOrderStatus());


    }
}