package com.minhaz.orders101.models;

import com.minhaz.orders101.enums.OrderStatus;
import com.minhaz.orders101.enums.PaymentStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;

@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Data
@Entity(name = "ORDERS")
@Validated
public class Order {

  @Id
  @NotNull(message = "Primary key cannot be null")
  private String id;

  @OneToMany(mappedBy = "order")
  @NotNull
  private List<ProductItem> basket;

  @Digits(integer = 6, fraction = 2)
  private BigDecimal totalPrice;

  @NotNull
  private String customerId;

  @NotNull
  private PaymentStatus paymentStatus;

  @NotNull
  private OrderStatus orderStatus;

  @OneToOne
  @NotNull
  private Address deliveryAddress;

  @Past
  private LocalDate createdDate;



}
