package com.minhaz.orders101.utils;



import com.minhaz.orders101.models.Product;
import com.minhaz.orders101.models.Product.ProductBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ProductUtils {
  public static ProductBuilder sampleProduct() {
    return Product.builder().id("1").stockLevel(10).unitPrice(new BigDecimal("15.50")).description("tool")
        .name("screwdriver");

  }
}
