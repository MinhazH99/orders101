package com.minhaz.orders101.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Customer {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  @OneToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "INVOICE_ADDRESS_FK_ID")
  private Address invoiceAddress;

  private String name;

  private String email;


}
