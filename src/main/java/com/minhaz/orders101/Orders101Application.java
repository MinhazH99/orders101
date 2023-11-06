package com.minhaz.orders101;

import com.minhaz.orders101.enums.OrderStatus;
import com.minhaz.orders101.enums.PaymentStatus;
import com.minhaz.orders101.interfaces.OrderDao;
import com.minhaz.orders101.models.Address;
import com.minhaz.orders101.models.Order;
import com.minhaz.orders101.models.ProductItem;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.Id;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.stream.Stream;

@SpringBootApplication()

public class Orders101Application {

  public static void main(String[] args) {
    SpringApplication.run(Orders101Application.class, args);
  }
}
