package com.luv2code.springboot.demo.models.services;

import com.luv2code.springboot.demo.models.entities.Customer;
import com.luv2code.springboot.demo.models.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    // Fetch all customers
    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    // Fetch a customer by ID
    public Optional<Customer> findById(Integer id) {
        return customerRepository.findById(id);
    }

    // Save a new customer or update an existing one
    public Customer save(Customer customer) {
        return customerRepository.save(customer);
    }

    // Delete a customer by ID
    public void deleteById(Integer id) {
        customerRepository.deleteById(id);
    }
}