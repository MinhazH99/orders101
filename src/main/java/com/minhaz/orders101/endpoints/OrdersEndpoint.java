package com.minhaz.orders101.endpoints;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.minhaz.orders101.enums.OrderStatus;
import com.minhaz.orders101.enums.PaymentStatus;
import com.minhaz.orders101.interfaces.OrderDao;
import com.minhaz.orders101.models.Address;
import com.minhaz.orders101.models.Order;
import com.minhaz.orders101.models.ProductItem;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

@Component
@Path("/orders")
public class OrdersEndpoint {
    @Autowired
    private OrderDao dao;
    @Autowired
    private ResourceLoader resourceLoader;
    private static Logger LOGGER = LoggerFactory.getLogger(OrdersEndpoint.class);
    private ObjectMapper objectMapper = new ObjectMapper();

    private Order myOrder() {
        ProductItem productItem = ProductItem.builder().productId("3").quantity(5).build();
        List<ProductItem> items = new ArrayList<>();
        items.add(productItem);
        Address address = Address.builder().addressLine1("Test Street").postCode("T3ST").country("England").build();
        LocalDate date = LocalDate.now();
        System.out.println("Testing pre-commit hook");
        return Order.builder().id("myTestID").basket(items).deliveryAddress(address).totalPrice(new BigDecimal("125.12")).customerId("3").paymentStatus(PaymentStatus.AUTHORISED).orderStatus(OrderStatus.COMPLETED).createdDate(date).build();
    }

    @POST
    public Response saveOrder(Order myOrder) {
        LOGGER.info("Post method called with order details{}",myOrder());
        return Response.ok().build();
    }

    @GET
    @Produces({"application/json"})
    public Response getOrders() {
        Resource resource = resourceLoader.getResource("classpath:orders.json");
        //            InputStream dbAsStream = resource.getInputStream();
//            String InputString = new String(dbAsStream.readAllBytes(), StandardCharsets.UTF_8);
//            objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
//            objectMapper.registerModule(new JavaTimeModule());
//            List<Order> ordersList = objectMapper.readValue(InputString,new TypeReference<List<Order>>(){});
//            LOGGER.info("Get method called with list of all orders {}",ordersList);
        List<Order> orders = dao.findAll();
        if (orders.isEmpty()) {
            return Response.status(Status.NOT_FOUND).build();
        } else {
            return Response.ok().entity(orders).build();
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
        System.out.println("Testing precommit");
        dao.deleteById(myOrder.getId());
        return Response.ok().build();
    }

    @GET
    @Path("/{orderId}")
    @Produces({"application/json"})
    public Response getOrder(@PathParam("orderId") String orderId) {
        Resource resource = resourceLoader.getResource("classpath:orders.json");
        try {
            InputStream dbAsStream = resource.getInputStream();
            String InputString = new String(dbAsStream.readAllBytes(), StandardCharsets.UTF_8);
            objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            objectMapper.registerModule(new JavaTimeModule());
            List<Order> ordersList = objectMapper.readValue(InputString,new TypeReference<List<Order>>(){});
            Optional<Order> order = ordersList.stream().filter(item -> item.getId().equals(orderId)).findFirst();
            if(order.isPresent()) {
                LOGGER.info("Get method called to retrieve order with ID = {}. Order details {}", order.get().getId(), order);
                return Response.ok().entity(order).build();
            } else {
                return Response.status(Status.NOT_FOUND).build();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
