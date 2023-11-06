package com.minhaz.orders101.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Address {

  @NotNull
  private String addressLine1;

  @Id
  @NotNull
  private String postCode;

  @NotNull
  private String country;

}
