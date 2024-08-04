package com.luv2code.springboot.demo.models.repositories;

import com.luv2code.springboot.demo.models.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository
        extends JpaRepository<Customer, Integer> {
}
