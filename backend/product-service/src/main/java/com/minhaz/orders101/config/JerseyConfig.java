package com.minhaz.orders101.config;

import com.minhaz.orders101.cors.CORSResponseFilter;
import com.minhaz.orders101.endpoint.ProductsEndpoint;
import com.minhaz.orders101.exception.ValidationConstraintExceptionMapper;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

@Component
public class JerseyConfig extends ResourceConfig {
  public JerseyConfig() {
    register(CORSResponseFilter.class);
    register(ProductsEndpoint.class);
    register(ValidationConstraintExceptionMapper.class);
  }
}
