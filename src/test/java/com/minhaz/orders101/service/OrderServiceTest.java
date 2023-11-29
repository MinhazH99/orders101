package com.minhaz.orders101.service;

import com.minhaz.orders101.enums.OrderStatus;
import com.minhaz.orders101.enums.PaymentStatus;
import com.minhaz.orders101.interfaces.*;
import com.minhaz.orders101.models.Order;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static com.minhaz.orders101.utils.OrderUtils.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class) // require this for dependency injection (double check with Joe). Without this, we
                                   // get NullPointerException
class OrderServiceTest {
  @Mock
  OrderDao dao;
  @Mock
  AddressDao addressDao;

  @Mock
  BasketDao basketDao;

  @Mock
  CustomerDao customerDao;

  @Mock
  LineItemDao lineItemDao;

  @InjectMocks
  OrderService orderService;

  @Test
  public void testRetrieveAll() {
    var order1 = sampleOrder().build();
    var order2 = sampleOrder().id("2").customer(sampleCustomer().id("2").build())
        .deliveryAddress(sampleDeliveryAddress().id("3").build())
        .basket(sampleBasket().id("2").lineItems(sampleThreeLineItems(new int[] {4, 5, 6})).build()).build();
    List<Order> orders = Arrays.asList(order1, order2);
    when(dao.findAll()).thenReturn(orders);
    List<Order> orderList = orderService.retrieveAll();
    verify(dao, times(1)).findAll();
    assertEquals(orderList.get(0), order1);
    assertEquals(orderList.get(1), order2);
    assertEquals(orderList.size(), 2);
  }

  @Test
  public void testRetrieveId() {
    var order = sampleOrder().build();

    when(dao.findById(order.getId())).thenReturn(Optional.of(order));
    var orderById = orderService.retrieveById("1");
    verify(dao, times(1)).findById(anyString());
    assertNotNull(orderById);
    assertEquals(orderById.get(), order);
  }

  @Test
  public void testPersist() {
    var order = sampleOrder().build();
    orderService.persist(order);
    verify(addressDao, times(1)).save(order.getCustomer().getInvoiceAddress());
    verify(addressDao, times(1)).save(order.getDeliveryAddress());
    verify(customerDao, times(1)).save(order.getCustomer());
    verify(lineItemDao, times(1)).saveAll(order.getBasket().getLineItems());
    verify(basketDao, times(1)).save(order.getBasket());
    verify(dao, times(1)).save(order);
  }

  @Test
  public void testDeleteOrder() {
    orderService.delete("4");
    verify(dao, times(1)).deleteById(anyString());
    assertThat(dao.findById("4")).isEmpty(); // Ask if this line is necessary
  }

  // what happens if you insert null in apply


}
