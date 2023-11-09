package com.minhaz.orders101.interfaces;

import com.minhaz.orders101.models.Address;
import com.minhaz.orders101.models.Basket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BasketDao extends JpaRepository<Basket, String> {
}
