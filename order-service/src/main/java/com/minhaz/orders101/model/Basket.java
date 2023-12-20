package com.minhaz.orders101.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Basket {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  @OneToMany(targetEntity = LineItem.class, fetch = FetchType.EAGER)
  private List<LineItem> lineItems;
}
