package com.minhaz.orders101.endpoints;

import com.minhaz.orders101.enums.OrderStatus;
import com.minhaz.orders101.enums.PaymentStatus;
import com.minhaz.orders101.interfaces.OrderDao;
import com.minhaz.orders101.models.Address;
import com.minhaz.orders101.models.Order;
import com.minhaz.orders101.models.ProductItem;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@Path("/orders")
//TODO Look up the return type for JAX-RS
public class OrdersEndpoint {
    @Autowired
    private OrderDao dao;
    private static Logger LOGGER = LoggerFactory.getLogger(OrdersEndpoint.class);

    private Order myOrder() {
        ProductItem productItem = ProductItem.builder().productId("3").quantity(5).build();
        List<ProductItem> items = new ArrayList<>();
        items.add(productItem);
        Address address = Address.builder().addressLine1("Test Street").postCode("T3ST").country("England").build();
        LocalDateTime localDateTime = LocalDateTime.now();
        Order myOrder = Order.builder().id("myTestID").basket(items).deliveryAddress(address).totalPrice(new BigDecimal("125.12")).customerId("3").paymentStatus(PaymentStatus.AUTHROISED).orderStatus(OrderStatus.COMPLETED).createdDate(localDateTime).build();
        return myOrder;

    }

    @POST
    public Response saveOrder(Order myOrder) {
        ProductItem productItem = ProductItem.builder().productId("3").quantity(5).build();
        List<ProductItem> items = new ArrayList<>();
        items.add(productItem);
        Address address = Address.builder().addressLine1("Test Street").postCode("T3ST").country("England").build();
        LocalDateTime localDateTime = LocalDateTime.now();
        myOrder = Order.builder().id("myTestID").basket(items).deliveryAddress(address).totalPrice(new BigDecimal("125.12")).customerId("3").paymentStatus(PaymentStatus.AUTHROISED).orderStatus(OrderStatus.COMPLETED).createdDate(localDateTime).build();
        LOGGER.info("{}",myOrder);
//        return dao.save(myOrder);
        return Response.ok().build();
    }

    @GET
    @Produces({"application/json"})
    public Response getOrders() {
//        LOGGER.info("{}",dao.findAll());
//        return dao.findAll();

        return Response.ok().entity(myOrder()).build();
    }

    @PATCH
    public Response updateDate(Order myOrder){
//        Optional<Order> foundOrder = dao.findById(Integer.valueOf(myOrder.getId()));
//        if (foundOrder.isPresent()) {
//            Order order = foundOrder.get();
//            LOGGER.info("Found the order with total price {}",order.getTotalPrice());
////            dao.save(order);
//        }
        // TODO Do something with the order
        return Response.ok().build();
    }

    @DELETE
    @Path("/delete-orders")
    public Response deleteOrder(Order myOrder){
//        dao.deleteById(Integer.valueOf(myOrder.getId()));
        return Response.ok().build();
    }

    @GET

//    //TODO What is the syntax to add the order ID as a path parameter. Need to add the order ID as a method parameter aswell
    @Path("/{orderId}")
    public Response getOrder(@PathVariable("orderID") String orderId) {
//        LOGGER.info("{}",dao.findAll());
        Optional<Order> foundOrder = dao.findById(orderId);
       if (foundOrder.isPresent()) {
           Order order = foundOrder.get();
           return Response.ok().entity(order).build();
       } else {
          return Response.status(Response.Status.NOT_FOUND).build();
       }

    }



}
