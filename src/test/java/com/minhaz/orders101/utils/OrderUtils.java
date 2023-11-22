package com.minhaz.orders101.utils;

import com.minhaz.orders101.enums.OrderStatus;
import com.minhaz.orders101.enums.PaymentStatus;
import com.minhaz.orders101.models.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OrderUtils {
  public static Order sampleOrder() {

    Address address = Address.builder().id("1").addressLine1("Test Street").postCode("T3ST").country("England").build();
    Customer customer =
        Customer.builder().id("1").email("cust1@gmail.com").name("John").invoiceAddress(address).build();

    LineItem hammer = LineItem.builder().name("hammer").description("test").quantity(15).id("1")
        .unitPrice(BigDecimal.valueOf(125.10)).build();
    LineItem screwdriver = LineItem.builder().name("screwdriver").description("test1").quantity(25).id("2")
        .unitPrice(BigDecimal.valueOf(100.00)).build();
    LineItem drill = LineItem.builder().name("drill").description("test2").quantity(40).id("3")
        .unitPrice(BigDecimal.valueOf(65.00)).build();
    List<LineItem> products = new ArrayList<>();
    products.add(hammer);
    products.add(screwdriver);
    products.add(drill);

    Basket basket = Basket.builder().id("1").lineItems(products).build();

    LocalDate date = LocalDate.now().minusDays(1);

    return Order.builder().id("1").totalPrice(new BigDecimal("125.12")).customer(customer)
        .paymentStatus(PaymentStatus.AUTHORISED).orderStatus(OrderStatus.COMPLETED).basket(basket).createdDate(date)
        .deliveryAddress(address).build();


  }
}
