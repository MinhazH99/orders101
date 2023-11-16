package com.minhaz.orders101.models;

import com.minhaz.orders101.enums.OrderStatus;
import com.minhaz.orders101.enums.PaymentStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Data
@Entity(name = "ORDERS")
@Validated
public class Order {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  @OneToOne
  @JoinColumn(name = "BASKET_FK_ID")
  private Basket basket;

  @Transient
  @Digits(integer = 6, fraction = 2)
  private BigDecimal totalPrice;

  @OneToOne
  @JoinColumn(name = "CUSTOMER_FK_ID")
  private Customer customer;

  @Enumerated(EnumType.STRING)
  @NotNull
  private PaymentStatus paymentStatus;

  @Enumerated(EnumType.STRING)
  @NotNull
  private OrderStatus orderStatus;

  @OneToOne
  @JoinColumn(name = "DELIVERY_ADDRESS_FK_ID")
  @NotNull
  private Address deliveryAddress;

  @Past
  private LocalDate createdDate;



}
