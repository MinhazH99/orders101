package com.minhaz.orders101.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.minhaz.orders101.enums.OrderStatus;
import com.minhaz.orders101.enums.PaymentStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.jackson.Jacksonized;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Data
@Jacksonized
@Entity
public class Order {

    @Id
    @NotNull(message = "Primary key can not be null")
    private String id;

    @OneToMany(mappedBy = "order")
    @NotNull
    private List<ProductItem> basket;

    @Digits(integer=6, fraction=2)
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
    private Date createdDate;



}
