package com.minhaz.orders101.orderinterface;

import com.minhaz.orders101.model.LineItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LineItemDao extends JpaRepository<LineItem, String> {
}
