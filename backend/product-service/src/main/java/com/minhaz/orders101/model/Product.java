package com.minhaz.orders101.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;

@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Data
@Entity(name = "PRODUCTS")
@Validated
public class Product {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  @Digits(integer = 6, fraction = 2)
  private BigDecimal unitPrice;

  @PositiveOrZero
  @NotNull
  private Integer stockLevel;

  private String description;

  @NotNull
  private String name;

}
