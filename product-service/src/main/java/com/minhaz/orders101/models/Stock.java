package com.minhaz.orders101.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;

@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Data
@Entity(name = "STOCK")
@Validated
public class Stock {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  private Integer requestQuantity;

  private boolean isAvailable;

}
