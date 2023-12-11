package com.minhaz.orders101.interfaces;

import com.minhaz.orders101.models.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressDao extends JpaRepository<Address, String> {
}
