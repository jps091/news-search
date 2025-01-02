package com.news;

import com.news.feign.NaverClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients(clients = NaverClient.class)
@SpringBootApplication
public class WebSearchApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(WebSearchApiApplication.class, args);
    }
}
