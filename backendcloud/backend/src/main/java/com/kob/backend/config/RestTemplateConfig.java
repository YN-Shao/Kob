package com.kob.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig { //allowing two services to communicate with each other
    @Bean
    public RestTemplate getRestTemplate(){

        return new RestTemplate();
    }
}
