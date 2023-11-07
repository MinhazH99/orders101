package com.minhaz.orders101.endpoints;


import com.minhaz.orders101.enums.OrderStatus;
import com.minhaz.orders101.interfaces.OrderDao;
import com.minhaz.orders101.models.Order;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@Path("/orders")
@Slf4j
public class OrdersEndpoint {

  @Autowired
  private OrderDao dao;



  @POST
  public Response saveOrder(Order myOrder) {
    log.info("Post method called with order details{}", myOrder);
    return Response.ok().build();
  }

  @GET
  @Produces({"application/json"})
  public Response getOrders() {
    List<Order> orders = dao.findAll();
    if (orders.isEmpty()) {
      return Response.status(Response.Status.NOT_FOUND).build();
    } else {
      return Response.ok().entity(orders).build();
    }

  }

  @PATCH
  public Response updateDate(Order myOrder) {
    // TODO Do something with the order
    Optional<Order> foundOrder = dao.findById("1");
    if (foundOrder.isPresent()) {
      Order order = foundOrder.get();
      log.info("Found the order with total price {}", order.getTotalPrice());
      order.setOrderStatus(OrderStatus.COMPLETED);
    }
    return Response.ok().build();
  }

  @DELETE
  public Response deleteOrder(Order myOrder) {
    log.info("Delete method called on order {}", myOrder);
    dao.deleteById(myOrder.getOrderId());
    return Response.ok().build();
  }

  @GET
  @Path("/{orderId}")
  @Produces({"application/json"})
  public Response getOrder(@PathParam("orderId") String orderId) {
    Optional<Order> order;
    order = dao.findById(orderId);
    if (order.isPresent()) {
      log.info("Get method called to retrieve order with ID = {}. Order details {}", order.get().getOrderId(), order);
      return Response.ok().entity(order).build();
    } else {
      return Response.status(Response.Status.NOT_FOUND).build();
    }
  }

}
