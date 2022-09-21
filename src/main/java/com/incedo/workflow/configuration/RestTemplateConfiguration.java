package com.incedo.workflow.configuration;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfiguration {
    @Bean("commonRestTemplate")
    public RestTemplate commonRestTemplate(){
        return new RestTemplate();
    }
}
