package com.minhaz.orders101.models;


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
  private String basketId;


  @OneToMany(targetEntity = LineItem.class, fetch = FetchType.EAGER)
  private List<LineItem> lineItems;



}
