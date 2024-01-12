package com.minhaz.orders101.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;


@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class StockAvailability {

  private String id;

  private Integer requestQuantity;

  private boolean isAvailable;

}
