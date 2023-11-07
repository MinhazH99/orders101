package com.minhaz.orders101.models;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
  private String basketId;


  @OneToMany(targetEntity = LineItem.class)
  private List<LineItem> product;



}
