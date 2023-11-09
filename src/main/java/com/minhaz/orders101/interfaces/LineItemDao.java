package com.minhaz.orders101.interfaces;

import com.minhaz.orders101.models.Address;
import com.minhaz.orders101.models.LineItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LineItemDao extends JpaRepository<LineItem, String> {
}
