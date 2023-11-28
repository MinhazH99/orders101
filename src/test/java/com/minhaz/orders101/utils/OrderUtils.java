package com.minhaz.orders101.utils;

import com.minhaz.orders101.enums.OrderStatus;
import com.minhaz.orders101.enums.PaymentStatus;
import com.minhaz.orders101.models.*;

import com.minhaz.orders101.models.Order.OrderBuilder;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OrderUtils {
  public static OrderBuilder sampleOrder() {
    return Order.builder().id("3").totalPrice(new BigDecimal("125.12")).customer(sampleCustomer().build())
        .paymentStatus(PaymentStatus.AUTHORISED).orderStatus(OrderStatus.COMPLETED).basket(sampleBasket().build())
        .createdDate(LocalDate.now().minusDays(1)).deliveryAddress(sampleDeliveryAddress().build());
  }

  public static Customer.CustomerBuilder sampleCustomer() {
    return Customer.builder().id("3").email("cust1@gmail.com").name("John")
        .invoiceAddress(sampleInvoiceAddress().build());
  }

  public static Address.AddressBuilder sampleDeliveryAddress() {
    return Address.builder().id("3").addressLine1("Test Delivery Street").postCode("RG24 3KL").country("England");
  }

  public static Address.AddressBuilder sampleInvoiceAddress() {
    return Address.builder().id("3").addressLine1("Test Invoice Street").postCode("SO24 8AH").country("England");
  }

  public static Basket.BasketBuilder sampleBasket() {
    return Basket.builder().id("3").lineItems(sampleThreeLineItems(new int[] {1, 2, 3}));
  }

  public static List<LineItem> sampleThreeLineItems(int[] ids) {
    List<LineItem> products = new ArrayList<>();
    products.add(LineItem.builder().name("hammer").description("test").quantity(15).id(Integer.toString(ids[0]))
        .unitPrice(BigDecimal.valueOf(125.10)).build());
    products.add(LineItem.builder().name("screwdriver").description("test1").quantity(25).id(Integer.toString(ids[1]))
        .unitPrice(BigDecimal.valueOf(100.00)).build());
    products.add(LineItem.builder().name("drill").description("test2").quantity(40).id(Integer.toString(ids[2]))
        .unitPrice(BigDecimal.valueOf(65.00)).build());
    return products;
  }

}
