package com.minhaz.orders101.endpoints;


import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@Path("/orders")
//TODO Look up the return type for JAX-RS
public class OrdersEndpoint {
    @Autowired
    private OrderDao dao;
    @Autowired
    private ResourceLoader resourceLoader;
    private static Logger LOGGER = LoggerFactory.getLogger(OrdersEndpoint.class);

    private Order myOrder() {
        ProductItem productItem = ProductItem.builder().productId("3").quantity(5).build();
        List<ProductItem> items = new ArrayList<>();
        items.add(productItem);
        Address address = Address.builder().addressLine1("Test Street").postCode("T3ST").country("England").build();
        Date date = new Date();
        Order myOrder = Order.builder().id("myTestID").basket(items).deliveryAddress(address).totalPrice(new BigDecimal("125.12")).customerId("3").paymentStatus(PaymentStatus.AUTHROISED).orderStatus(OrderStatus.COMPLETED).createdDate(date).build();
        return myOrder;

    }

    @POST
    public Response saveOrder(Order myOrder) {
        LOGGER.info("Post method called with order details{}",myOrder());
        return Response.ok().build();
    }

    @GET
    @Produces({"application/json"})
    public Response getOrders() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        ObjectMapper objectMapper = new ObjectMapper();
        Resource resource = resourceLoader.getResource("classpath:orders.json");
        try {
            InputStream dbAsStream = resource.getInputStream();
            String InputString = new String(dbAsStream.readAllBytes(), StandardCharsets.UTF_8);
            objectMapper.setDateFormat(dateFormat);
            List<Order> ordersList = objectMapper.readValue(InputString,new TypeReference<List<Order>>(){});
            LOGGER.info("Get method called with list of all orders {}",ordersList);
            return Response.ok().entity(ordersList).build();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }



    }

    @PATCH
    public Response updateDate(Order myOrder){
        // TODO Do something with the order
        Optional<Order> foundOrder = dao.findById(myOrder.getId());
        if (foundOrder.isPresent()) {
            Order order = foundOrder.get();
            LOGGER.info("Found the order with total price {}",order.getTotalPrice());
            order.setOrderStatus(OrderStatus.COMPLETED);
        }

        return Response.ok().build();
    }

    @DELETE
    public Response deleteOrder(Order myOrder){
        LOGGER.info("Delete method called on order {}", myOrder);
        dao.deleteById(myOrder.getId());
        return Response.ok().build();
    }

    @GET
    //TODO What is the syntax to add the order ID as a path parameter. Need to add the order ID as a method parameter aswell
    @Path("/{orderId}")
    @Produces({"application/json"})
    public Response getOrder(@PathParam("orderId") String orderId) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        ObjectMapper objectMapper = new ObjectMapper();
        Resource resource = resourceLoader.getResource("classpath:orders.json");
        try {
            InputStream dbAsStream = resource.getInputStream();
            String InputString = new String(dbAsStream.readAllBytes(), StandardCharsets.UTF_8);
            objectMapper.setDateFormat(dateFormat);
            List<Order> ordersList = objectMapper.readValue(InputString,new TypeReference<List<Order>>(){});
            List<Order> orderStream = ordersList.stream().filter(item -> item.getId().equals(orderId)).toList();
            LOGGER.info("Get method called to retrieve order ID = {}. Order details {}",orderStream.get(0).getId(),orderStream);

            return Response.ok().entity(orderStream).build();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }



    }



}
