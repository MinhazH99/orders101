package com.minhaz.orders101.endpoints;

import com.minhaz.orders101.interfaces.DataAccess;
import jakarta.ws.rs.*;
import org.springframework.stereotype.Component;

@Component
@Path("/orders")
public class OrdersEndpoint implements DataAccess {

    @Override
    @GET
    public String get_orders() {
        return new String("Get orders");
    }

    @Override
    @PATCH
    public String update_orders() {
        return null;
    }

    @Override
    @POST
    public void create_orders() {

    }

    @Override
    @DELETE
    public void delet_orders() {

    }
}
