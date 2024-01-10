package com.minhaz.orders101.dao;

import com.minhaz.orders101.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductDao extends JpaRepository<Product, String> {
}
