package com.minhaz.orders101.models;

import com.minhaz.orders101.enums.OrderStatus;
import com.minhaz.orders101.enums.PaymentStatus;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Data
public class Order {

    @NotNull(message = "Primary key can not be null")
    private String id;

    @NotNull
    private ProductItem basket;

    @Digits(integer=6, fraction=2)
    private BigDecimal totalPrice;

    @NotNull
    private String customerId;

    @NotNull
    private PaymentStatus paymentStatus;

    @NotNull
    private OrderStatus orderStatus;

    @NotNull
    private Address deliveryAddress;
    
    @Past
    private LocalDateTime createdDate;



}
