package com.minhaz.orders101.service;

import com.minhaz.orders101.enums.OrderStatus;
import com.minhaz.orders101.enums.PaymentStatus;
import com.minhaz.orders101.exceptions.ValidationConstraintExceptionMapper;
import com.minhaz.orders101.interfaces.*;
import com.minhaz.orders101.models.Basket;
import com.minhaz.orders101.models.Customer;
import com.minhaz.orders101.models.Order;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static com.minhaz.orders101.utils.OrderUtils.*;
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

  @Mock
  OrderDao orderDao;

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

  @Test
  public void testRetrieveId() {
    var order = sampleOrder().build();

    when(dao.findById(order.getId())).thenReturn(Optional.of(order));
    var orderById = orderService.retrieveById("1");
    assertNotEquals(orderById, null);
    assertEquals(orderById.get().getCustomer().getName(), "John");
    assertEquals(orderById.get().getBasket().getId(), "1");
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


}
