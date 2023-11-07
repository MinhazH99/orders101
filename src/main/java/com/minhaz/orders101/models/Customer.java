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
public class Customer {
  @Id
  private String customerId;

  @OneToMany
  private List<Address> addressList;

  private String name;

  private String email;


}
