package com.minhaz.orders101.endpoints;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.minhaz.orders101.models.Order;
import com.minhaz.orders101.models.ResponseModel;
import com.minhaz.orders101.service.OrderService;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.GsonBuilderUtils;
import org.springframework.stereotype.Component;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

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
    return Response.ok().entity(ResponseModel.builder().data(order).build()).build();
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
  @Path("/{orderId}")
  @Consumes({"application/json"})
  @Produces({"application/json"})
  // TODO include the id in the URL path param
  public Response updateOrder(@Valid Order updatedOrder, @PathParam("orderId") String orderId)
      throws JsonProcessingException {
    Optional<Order> existingOrder = orderService.retrieveById(orderId);
    if (existingOrder.isEmpty()) {
      return Response.status(Response.Status.NOT_FOUND).entity(notFoundError(orderId)).build();
    }
    if (orderService.orderRequiresUpdate(existingOrder.get(), updatedOrder)) {
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
    if (orderId == null) {
      throw new IllegalArgumentException("Order id should not be null");
    }
    orderService.delete(orderId);
    return Response.ok().entity(ResponseModel.builder().build()).build();
  }

  @GET
  @Path("/{orderId}")
  @Produces({"application/json"})
  public Response getOrder(@PathParam("orderId") String orderId) {
    Optional<Order> order = orderService.retrieveById(orderId);
    if (order.isPresent()) {
      System.out.println(order.get().getBasket().getLineItems().get(0).getUnitPrice());
      return Response.ok().entity(ResponseModel.builder().data(order).build()).build();
    } else {
      return Response.status(Response.Status.NOT_FOUND).entity(notFoundError(orderId)).build();
    }
  }
}
