package com.luv2code.springboot.demo.controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.luv2code.springboot.demo.controller", "com.luv2code.springboot.demo.models.services"})
@EnableJpaRepositories(basePackages = "com.luv2code.springboot.demo.models.repositories")
@EntityScan(basePackages = "com.luv2code.springboot.demo.models.entities")
public class MyCoolappApplication {
    public static void main(String[] args) {
        SpringApplication.run(MyCoolappApplication.class, args);
    }
}