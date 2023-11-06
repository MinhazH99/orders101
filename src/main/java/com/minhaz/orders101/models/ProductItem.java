package com.minhaz.orders101.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class ProductItem {
  @Id
  private String productId;
  private int quantity;

  @ManyToOne
  private Order order;
}
