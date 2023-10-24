package com.minhaz.orders101.interfaces;

import jakarta.ws.rs.GET;

public interface DataAccess {
    String get_orders();
    String update_orders();
    void create_orders();
    void delet_orders();


}
