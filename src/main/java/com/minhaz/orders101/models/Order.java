package com.minhaz.orders101.models;

import com.minhaz.orders101.enums.OrderStatus;
import com.minhaz.orders101.enums.PaymentStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Transient;
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
  @NotNull(message = "Primary key cannot be null")
  private String orderId;

  @OneToOne
  private Basket basket;

  @Transient
  @Digits(integer = 6, fraction = 2)
  private BigDecimal totalPrice;

  @OneToOne
  private Customer customer;

  @NotNull
  private PaymentStatus paymentStatus;

  @NotNull
  private OrderStatus orderStatus;

  // @OneToOne
  // @NotNull
  // private Address deliveryAddress;



  @Past
  private LocalDate createdDate;



}
