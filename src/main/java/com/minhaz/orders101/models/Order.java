package com.minhaz.orders101.models;

import com.minhaz.orders101.enums.OrderStatus;
import com.minhaz.orders101.enums.PaymentStatus;
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

    private String id;
    private ProductItem basket;
    private BigDecimal totalPrice;
    private String customerId;
    private PaymentStatus paymentStatus;
    private OrderStatus orderStatus;
    private Address deliveryAddress;
    private LocalDateTime createdDate;



}
