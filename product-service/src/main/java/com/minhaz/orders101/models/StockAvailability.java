package com.minhaz.orders101.models;

import lombok.Builder;


@Builder
public class StockAvailability {

  private String id;

  private Integer requestQuantity;

  private boolean isAvailable;

}
