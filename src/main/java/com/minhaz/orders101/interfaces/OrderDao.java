package com.minhaz.orders101.interfaces;

import com.minhaz.orders101.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface OrderDao extends JpaRepository<Order, String> {
}
