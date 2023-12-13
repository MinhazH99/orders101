package com.minhaz.orders101.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;


@Data
@AllArgsConstructor
@Builder
public class StockAvailability {

  private String id;

  private Integer requestQuantity;

  private boolean isAvailable;

}
