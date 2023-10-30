package com.minhaz.orders101.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
    private String addressLine1;
    @Id
    private String postCode;
    private String country;
}
