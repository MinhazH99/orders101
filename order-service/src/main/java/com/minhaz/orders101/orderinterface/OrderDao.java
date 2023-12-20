package com.minhaz.orders101.orderinterface;

import com.minhaz.orders101.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface OrderDao extends JpaRepository<Order, String> {
}
