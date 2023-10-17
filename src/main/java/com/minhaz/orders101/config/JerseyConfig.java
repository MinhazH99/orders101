package com.minhaz.orders101.config;

import com.minhaz.orders101.endpoints.HelloWorldEndpoint;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

@Component
public class JerseyConfig extends ResourceConfig {

    public JerseyConfig() {
        register(HelloWorldEndpoint.class);
    }

}