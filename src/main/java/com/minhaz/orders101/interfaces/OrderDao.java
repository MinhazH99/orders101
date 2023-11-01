package com.minhaz.orders101.interfaces;

import com.minhaz.orders101.models.Order;
import jakarta.ws.rs.core.Response;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

//@Component
public interface OrderDao extends JpaRepository<Order,String> {
//    Optional<Order> findbyId(String orderID);
//    List<Order> findAll();
}
