package com.minhaz.orders101.endpoints;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.minhaz.orders101.models.Product;
import com.minhaz.orders101.models.ResponseModel;
import com.minhaz.orders101.service.ProductService;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Component
@Path("/")
@Consumes("application/json")
@Slf4j
public class ProductsEndpoint {

  private static final String ORDER_WITH_ORDER_ID_NOT_FOUND = "Order with id %s not found.";
  @Autowired
  ProductService productService;


  @POST
  @Consumes({"application/json"})
  @Produces({"application/json"})
  public Response saveOrder(@Valid Product order) throws ServerErrorException {
    productService.persist(order);
    return Response.ok().entity(ResponseModel.builder().data(order).build()).build();
  }

  @GET
  @Path("/products")
  @Produces({"application/json"})
  public Response getProducts() {
    List<Product> orders = productService.retrieveAll();
    if (orders.isEmpty()) {
      return Response.status(Response.Status.NOT_FOUND).build();
    } else {
      return Response.ok().entity(ResponseModel.builder().data(orders).build()).build();
    }
  }

  @GET
  @Path("products/{productId}")
  @Produces({"application/json"})
  public Response getProduct(@PathParam("productId") String productId) {
    Optional<Product> product = productService.retrieveById(productId);
    if (product.isPresent()) {
      return Response.ok().entity(ResponseModel.builder().data(product).build()).build();
    } else {
      return Response.status(Response.Status.NOT_FOUND).entity(notFoundError(productId)).build();
    }
  }

  @GET
  @Path("available-stock/{productId}")
  @Produces({"application/json"})
  public Response getProductStock(@PathParam("productId") String productId) {
    Optional<Product> product = productService.retrieveById(productId);
    if (product.isPresent()) {
      int stockLevel = product.get().getStockLevel();
      return Response.ok().entity(ResponseModel.builder().data(stockLevel).build()).build();
    } else {
      return Response.status(Response.Status.NOT_FOUND).entity(notFoundError(productId)).build();
    }
  }



  @PATCH
  @Path("/{orderId}")
  @Consumes({"application/json"})
  @Produces({"application/json"})
  public Response updateOrder(@Valid Product updatedOrder, @PathParam("orderId") String orderId)
      throws JsonProcessingException {
    Optional<Product> existingOrder = productService.retrieveById(orderId);
    if (existingOrder.isEmpty()) {
      return Response.status(Response.Status.NOT_FOUND).entity(notFoundError(orderId)).build();
    }
    if (productService.orderRequiresUpdate(existingOrder.get(), updatedOrder)) {
      var orderWithDiffs = productService.applyDiff(updatedOrder, existingOrder.get());
      productService.persist(orderWithDiffs);
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
    // orderService.delete(orderId);
    return Response.ok().entity(ResponseModel.builder().build()).build();
  }


}
