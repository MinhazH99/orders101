package com.minhaz.orders101.dao;

import com.minhaz.orders101.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressDao extends JpaRepository<Address, String> {
}
