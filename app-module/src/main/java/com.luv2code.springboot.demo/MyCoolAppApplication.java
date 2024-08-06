package com.luv2code.springboot.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@ComponentScan(basePackages = {
        "com.luv2code.springboot.demo.controller",
        "com.luv2code.springboot.demo.services",
        "com.luv2code.springboot.demo.config"
})
@EnableJpaRepositories(basePackages = "com.luv2code.springboot.demo.repository")
@EntityScan(basePackages = "com.luv2code.springboot.demo.repository.entities")
public class MyCoolAppApplication {
    public static void main(String[] args) {
        SpringApplication.run(MyCoolAppApplication.class, args);
    }
}