package com.minhaz.orders101.endpoints;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.minhaz.orders101.models.Order;
import com.minhaz.orders101.models.ResponseModel;
import com.minhaz.orders101.service.OrderService;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.ServerErrorException;
import jakarta.ws.rs.core.Response;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Path("/orders")
@Consumes("application/json")
@Slf4j
public class OrdersEndpoint {

  private static final String ORDER_WITH_ORDER_ID_NOT_FOUND = "Order with id %s not found.";
  @Autowired
  OrderService orderService;

  @POST
  @Consumes({"application/json"})
  @Produces({"application/json"})
  public Response saveOrder(@Valid Order order) throws ServerErrorException {
    orderService.persist(order);
    return Response.ok().entity(order).build();
  }

  @GET
  @Produces({"application/json"})
  public Response getOrders() {
    List<Order> orders = orderService.retrieveAll();
    if (orders.isEmpty()) {
      return Response.status(Response.Status.NOT_FOUND).build();
    } else {
      return Response.ok().entity(ResponseModel.builder().data(orders).build()).build();
    }
  }


  @PATCH
  @Consumes({"application/json"})
  @Produces({"application/json"})
  public Response updateOrder(@Valid Order updatedOrder) throws JsonProcessingException {
    Optional<Order> existingOrder = orderService.retrieveById(updatedOrder.getId());
    if (existingOrder.isEmpty()) {
      return Response.status(Response.Status.NOT_FOUND).entity(notFoundError(updatedOrder.getId())).build();
    }
    if (orderService.orderRequiresUpdate(updatedOrder, existingOrder.get())) {
      var orderWithDiffs = orderService.applyDiff(updatedOrder, existingOrder.get());
      orderService.persist(orderWithDiffs);
      return Response.ok(ResponseModel.builder().data(orderWithDiffs).build()).build();
    }
    return Response.ok(ResponseModel.builder().data(existingOrder.get()).build()).build();
  }

  private Object notFoundError(String id) {
    return ResponseModel.builder().errors(Arrays.asList(new HashMap<>() {
      {
        put("error", String.format(ORDER_WITH_ORDER_ID_NOT_FOUND, id));
      }
    })).build();
  }

  @DELETE
  @Path("/{orderId}")
  public Response deleteOrder(@PathParam("orderId") String orderId) {
    orderService.delete(orderId);
    return Response.ok().build();
  }

  @GET
  @Path("/{orderId}")
  @Produces({"application/json"})
  public Response getOrder(@PathParam("orderId") String orderId) {
    Optional<Order> order = orderService.retrieveById(orderId);
    if (order.isPresent()) {
      return Response.ok().entity(ResponseModel.builder().data(order).build()).build();
    } else {
      return Response.status(Response.Status.NOT_FOUND).entity(notFoundError(orderId)).build();
    }
  }

}
