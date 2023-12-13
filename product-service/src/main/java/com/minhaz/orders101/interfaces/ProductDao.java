package com.minhaz.orders101.interfaces;

import com.minhaz.orders101.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductDao extends JpaRepository<Product, String> {
}
