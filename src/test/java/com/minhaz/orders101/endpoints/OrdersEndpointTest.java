package com.minhaz.orders101.endpoints;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.*;


class OrdersEndpointTest {
    @Test

    public void getOrdersTest() {
        OrdersEndpoint ordersEndpoint = new OrdersEndpoint();
        assertEquals("success",ordersEndpoint.getOrders());
    }

    @Test
    public void testGetRequest() {

    }

}