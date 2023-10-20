package com.minhaz.orders101.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Data
public class Address {
    private String addressLine1;
    private String postCode;
    private String country;
}
