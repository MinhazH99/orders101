package com.minhaz.orders101.dao;

import com.minhaz.orders101.model.Basket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BasketDao extends JpaRepository<Basket, String> {
}
