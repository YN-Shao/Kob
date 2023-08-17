package com.kob.matchingsystem.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig { //用来在后端调用其他服务的配置，可以让两个服务之间互相通信
    @Bean
    public RestTemplate getRestTemplate(){

        return new RestTemplate();
    }
}
