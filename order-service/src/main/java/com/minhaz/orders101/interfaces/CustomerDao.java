package com.minhaz.orders101.interfaces;

import com.minhaz.orders101.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerDao extends JpaRepository<Customer, String> {
}
