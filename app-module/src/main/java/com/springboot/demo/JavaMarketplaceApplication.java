package com.springboot.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@ComponentScan(basePackages = {
        "com.springboot.demo.controller",
        "com.springboot.demo.services",
        "com.springboot.demo.config"
})
@EnableJpaRepositories(basePackages = "com.springboot.demo.repository")
@EntityScan(basePackages = "com.springboot.demo.repository.entities")
public class JavaMarketplaceApplication {
    public static void main(String[] args) {
        SpringApplication.run(JavaMarketplaceApplication.class, args);
    }
}