package com.luv2code.springboot.demo.services;


import com.luv2code.springboot.demo.repository.entities.Customer;
import com.luv2code.springboot.demo.repository.CustomerRepository;
import com.luv2code.springboot.demo.services.vo.CustomerVO;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private ModelMapper modelMapper;

    @PostConstruct
    public void configureMapper() {
        modelMapper.typeMap(CustomerVO.class, Customer.class).addMappings(mapper -> mapper.map(CustomerVO::getUsername, Customer::setName));
        modelMapper.typeMap(Customer.class, CustomerVO.class).addMappings(mapper -> mapper.map(Customer::getName, CustomerVO::setUsername));
    }


    // Fetch all customers
    @Cacheable("customers")
    public List<CustomerVO> getAllCustomers() {
        List<Customer> entities = customerRepository.findAll();
        return entities.stream()
                .map(entity -> modelMapper.map(entity, CustomerVO.class))
                .collect(Collectors.toList());
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