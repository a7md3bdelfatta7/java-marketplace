package com.luv2code.springboot.demo.mycoolapp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class MyCoolappApplication {

	private static final Logger log = LoggerFactory.getLogger(MyCoolappApplication.class);

    public MyCoolappApplication() {
	}

    public static void main(String[] args) {
		SpringApplication.run(MyCoolappApplication.class, args);
	}






}
