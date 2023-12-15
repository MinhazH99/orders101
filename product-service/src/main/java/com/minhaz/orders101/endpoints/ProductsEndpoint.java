package com.minhaz.orders101.endpoints;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.minhaz.orders101.models.Product;
import com.minhaz.orders101.models.ResponseModel;
import com.minhaz.orders101.models.StockAvailability;
import com.minhaz.orders101.service.ProductService;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.rmi.server.RemoteRef;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Component
@Path("/products")
@Consumes("application/json")
@Slf4j
public class ProductsEndpoint {

  private static final String PRODUCT_WITH_ID_NOT_FOUND = "Product with id %s not found.";
  private static final String QTY_CAN_NOT_BE_A_NEGATIVE_NULL_VALUE =
      "Quantity provided can not be a negative/null value";
  @Autowired
  ProductService productService;


  @POST
  @Consumes({"application/json"})
  @Produces({"application/json"})
  public Response saveProduct(@Valid Product product) throws ServerErrorException {
    productService.persist(product);
    return Response.ok().entity(ResponseModel.builder().data(product).build()).build();
  }

  @GET
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
  @Path("{productId}")
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
  @Path("stock-availability/{productId}")
  @Produces({"application/json"})
  public Response getProductStock(@PathParam("productId") String productId, @QueryParam("qty") Integer qty) {
    Optional<Product> product = productService.retrieveById(productId);
    if (product.isPresent()) {
      int stockLevel = product.get().getStockLevel();
      StockAvailability stockAvailability = StockAvailability.builder().id(productId).requestQuantity(qty)
          .isAvailable(checkAvailability(stockLevel, qty)).build();
      return Response.ok().entity(ResponseModel.builder().data(stockAvailability).build()).build();
    } else {
      return Response.status(Response.Status.NOT_FOUND).entity(notFoundError(productId)).build();
    }
  }

  private boolean checkAvailability(int stockLevel, Integer qty) {
    return stockLevel >= qty;
  }


  @PATCH
  @Path("{productId}")
  @Consumes({"application/json"})
  @Produces({"application/json"})
  public Response updateProduct(@Valid Product updatedProduct, @PathParam("productId") String productId)
      throws JsonProcessingException {
    Optional<Product> existingProduct = productService.retrieveById(productId);
    if (existingProduct.isEmpty()) {
      return Response.status(Response.Status.NOT_FOUND).entity(notFoundError(productId)).build();
    }
    if (productService.productRequiresUpdate(existingProduct.get(), updatedProduct)) {
      var productWithDiffs = productService.applyDiff(updatedProduct, existingProduct.get());
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

  @PATCH
  @Path("stock-availability/{productId}")
  @Consumes({"application/json"})
  @Produces({"application/json"})
  public Response updateStock(@PathParam("productId") String productId, @QueryParam("inc") boolean inc,
      @QueryParam("qty") Integer qty) {
    Optional<Product> product = productService.retrieveById(productId);
    if (qty == null) {
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(quantityNegativeNullError(null)).build();
    }
    if (product.isEmpty()) {
      return Response.status(Response.Status.NOT_FOUND).entity(notFoundError(productId)).build();

    }
    if (product.isPresent() & qty > 0) {
      int stockLevel = product.get().getStockLevel();
      StockAvailability stockAvailability = StockAvailability.builder().id(productId).requestQuantity(qty)
          .isAvailable(checkAvailability(stockLevel, qty)).build();
      if (!inc && stockAvailability.isAvailable()) {
        product.get().setStockLevel(stockLevel - qty);
        productService.persist(product.get());
      } else if (inc) {
        product.get().setStockLevel(stockLevel + qty);
        productService.persist(product.get());
      }
      StockAvailability stockAvailabilityLatest = StockAvailability.builder().id(productId).requestQuantity(qty)
          .isAvailable(checkAvailability(stockLevel, qty)).build();
      return Response.ok().entity(ResponseModel.builder().data(stockAvailabilityLatest).build()).build();
    }
    return Response.status(Response.Status.BAD_REQUEST).entity(quantityNegativeNullError(qty)).build();
  }

  private Object quantityNegativeNullError(Integer qty) {
    return ResponseModel.builder().errors(List.of(new HashMap<>() {
      {
        put("error", String.format(QTY_CAN_NOT_BE_A_NEGATIVE_NULL_VALUE, qty));
      }
    })).build();
  }

  @DELETE
  @Path("{productId}")
  public Response deleteProduct(@PathParam("productId") String productId) {
    if (productId == null) {
      throw new IllegalArgumentException("Product id should not be null");
    }
    productService.delete(productId);
    return Response.ok().build();
  }


}
