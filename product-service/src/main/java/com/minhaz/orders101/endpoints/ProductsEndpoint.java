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
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Component
@Path("/")
@Consumes("application/json")
@Slf4j
public class ProductsEndpoint {

  private static final String PRODUCT_WITH_ID_NOT_FOUND = "Product with id %s not found.";
  @Autowired
  ProductService productService;


  @POST
  @Path("/products")
  @Consumes({"application/json"})
  @Produces({"application/json"})
  public Response saveOrder(@Valid Product product) throws ServerErrorException {
    productService.persist(product);
    return Response.ok().entity(ResponseModel.builder().data(product).build()).build();
  }

  @GET
  @Path("/products")
  @Produces({"application/json"})
  public Response getProducts() {
    List<Product> products = productService.retrieveAll();
    if (products.isEmpty()) {
      return Response.status(Response.Status.NOT_FOUND).build();
    } else {
      return Response.ok().entity(ResponseModel.builder().data(products).build()).build();
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
  @Path("products/{productId}")
  @Consumes({"application/json"})
  @Produces({"application/json"})
  public Response updateOrder(@Valid Product updatedOrder, @PathParam("productId") String orderId)
      throws JsonProcessingException {
    Optional<Product> existingProduct = productService.retrieveById(orderId);
    if (existingProduct.isEmpty()) {
      return Response.status(Response.Status.NOT_FOUND).entity(notFoundError(orderId)).build();
    }
    if (productService.productRequiresUpdate(existingProduct.get(), updatedOrder)) {
      var productWithDiffs = productService.applyDiff(updatedOrder, existingProduct.get());
      productService.persist(productWithDiffs);
      return Response.ok(ResponseModel.builder().data(productWithDiffs).build()).build();
    }
    return Response.ok(ResponseModel.builder().data(existingProduct.get()).build()).build();
  }

  private Object notFoundError(String id) {
    return ResponseModel.builder().errors(List.of(new HashMap<>() {
      {
        put("error", String.format(PRODUCT_WITH_ID_NOT_FOUND, id));
      }
    })).build();
  }

  @DELETE
  @Path("products/{productId}")
  public Response deleteOrder(@PathParam("productId") String productId) {
    if (productId == null) {
      throw new IllegalArgumentException("Product id should not be null");
    }
    productService.delete(productId);
    return Response.ok().entity(ResponseModel.builder().build()).build();
  }


}
