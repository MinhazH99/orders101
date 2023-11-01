package com.minhaz.orders101.interfaces;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.minhaz.orders101.models.Order;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;


import java.io.File;
import java.io.FileReader;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

public class TextFileOrderDao{
//    @Autowired
//    private ResourceLoader resourceLoader;
//    @Override
//    public Optional<Order> findbyId(String orderID) {
//       Resource resource = resourceLoader.getResource("/Users/minhaz.hassan/Documents/orders101/src/main/resources/orders.json");
//        InputStream dbAsStream = resource.getInputStream();
//        System.out.println(InputStream);
//        return Optional.empty();
//    }
//
//    @Override
//    public List<Order> findAll() {
//        return null;
//    }
}
