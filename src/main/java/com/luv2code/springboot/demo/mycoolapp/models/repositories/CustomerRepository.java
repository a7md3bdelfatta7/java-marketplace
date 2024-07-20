package com.luv2code.springboot.demo.mycoolapp.models.repositories;

import com.luv2code.springboot.demo.mycoolapp.models.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository
        extends JpaRepository<Customer, Integer> {
}
