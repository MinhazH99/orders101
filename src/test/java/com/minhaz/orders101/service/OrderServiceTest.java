package com.minhaz.orders101.service;

import com.minhaz.orders101.enums.OrderStatus;
import com.minhaz.orders101.enums.PaymentStatus;
import com.minhaz.orders101.interfaces.OrderDao;
import com.minhaz.orders101.models.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static com.minhaz.orders101.utils.OrderUtils.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class) // require this for dependency injection (double check with Joe). Without this, we
                                   // get NullPointerException
class OrderServiceTest {
  @Mock
  OrderDao dao;

  @InjectMocks
  OrderService orderService;

  @Test
  public void testRetrieveAll() {
    var order1 = sampleOrder().build();
    var order2 = sampleOrder().id("2").customer(sampleCustomer().id("2").build())
        .deliveryAddress(sampleDeliveryAddress().id("3").build())
        .basket(sampleBasket().id("2").lineItems(sampleThreeLineItems(new int[] {4, 5, 6})).build()).build();
    List<Order> orders = new ArrayList<>();
    orders.add(order1);
    orders.add(order2);
    when(dao.findAll()).thenReturn(orders);

    List<Order> orderList = orderService.retrieveAll();
    assertEquals(orderList.size(), 2);
    assertEquals(orderList.get(0).getId(), "1");
    assertEquals(orderList.get(0).getPaymentStatus(), PaymentStatus.AUTHORISED);
    assertEquals(orderList.get(0).getOrderStatus(), OrderStatus.COMPLETED);
    assertEquals(orderList.get(0).getCustomer().getId(), "1");
    assertEquals(orderList.get(0).getDeliveryAddress().getId(), "1");
    assertEquals(orderList.get(0).getBasket().getId(), "1");

    assertEquals(orderList.get(1).getId(), "2");
    assertEquals(orderList.get(1).getPaymentStatus(), PaymentStatus.AUTHORISED);
    assertEquals(orderList.get(1).getOrderStatus(), OrderStatus.COMPLETED);
    assertEquals(orderList.get(1).getCustomer().getId(), "2");
    assertEquals(orderList.get(1).getDeliveryAddress().getId(), "3");
    assertEquals(orderList.get(1).getBasket().getId(), "2");



  }


}
