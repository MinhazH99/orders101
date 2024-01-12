package com.minhaz.orders101.util;



import com.minhaz.orders101.model.Product;
import com.minhaz.orders101.model.Product.ProductBuilder;

import java.math.BigDecimal;

public class ProductUtils {
  public static ProductBuilder sampleProduct() {
    return Product.builder().id("1").stockLevel(10).unitPrice(new BigDecimal("15.50")).description("tool")
        .name("screwdriver");

  }
}
