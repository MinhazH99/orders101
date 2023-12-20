package com.minhaz.orders101.dao;

import com.minhaz.orders101.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerDao extends JpaRepository<Customer, String> {
}
