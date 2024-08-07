package com.luv2code.springboot.demo.controller;

import com.luv2code.springboot.demo.controller.dto.CustomerDTO;
import com.luv2code.springboot.demo.repository.entities.Customer;
import com.luv2code.springboot.demo.services.CustomerService;

import com.luv2code.springboot.demo.services.vo.CustomerVO;
import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {


    @Autowired
    private CustomerService customerService;
    @Autowired
    private ModelMapper modelMapper;

    @PostConstruct
    public void configureMapper() {
        modelMapper.typeMap(CustomerVO.class, CustomerDTO.class).addMappings(mapper -> mapper.map(CustomerVO::getUsername, CustomerDTO::setUserName));
        modelMapper.typeMap(CustomerDTO.class, CustomerVO.class).addMappings(mapper -> mapper.map(CustomerDTO::getUserName, CustomerVO::setUsername));
    }


    @GetMapping
    public List<CustomerDTO> getCustomers() {
        List<CustomerVO> customerVOs = customerService.getAllCustomers();
        return customerVOs.stream()
                .map(customerVO -> modelMapper.map(customerVO, CustomerDTO.class))
                .collect(Collectors.toList());
    }

    record NewCustomerRequest(String name, String email, Integer age) {
    }

    @PostMapping
    public ResponseEntity<CustomerDTO> addCustomer(@RequestBody NewCustomerRequest request) {
        CustomerVO customer = new CustomerVO();

        customer.setUsername(request.name());
        customer.setEmail(request.email());
        customer.setAge(request.age());

        return ResponseEntity.ok(modelMapper.map(customerService.save(customer), CustomerDTO.class));
    }

    @PutMapping("/{customerId}")
    public ResponseEntity<CustomerDTO> updateCustomer(@PathVariable("customerId") Integer id,
                                                      @RequestBody NewCustomerRequest request) {

        Optional<CustomerVO> customer = customerService.getCustomerById(id);

        if (customer.isPresent()) {
            CustomerVO updatedCustomer = customer.get();
            updatedCustomer.setUsername(request.name());
            updatedCustomer.setEmail(request.email());
            updatedCustomer.setAge(request.age());

            return ResponseEntity.ok(modelMapper.map(customerService.save(updatedCustomer), CustomerDTO.class));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("{customerId}")
    public ResponseEntity<CustomerDTO> deleteCustomer(@PathVariable("customerId") Integer id) {
        Optional<CustomerVO> customer = customerService.getCustomerById(id);
        if (customer.isPresent()) {
            customerService.deleteById(customer.get().getId());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @GetMapping("{customerId}")
    public CustomerDTO getCustomer(@PathVariable("customerId") Integer id) {
//        log.info(id.toString());
        try {
            Optional<CustomerVO> customer = customerService.getCustomerById(id);
            if (customer.isPresent()) {
                return modelMapper.map(customer.get(), CustomerDTO.class);
            } else {
                return new CustomerDTO(-1, "", "", -1);
            }
        } catch (Exception e) {
            return new CustomerDTO(-1, "", "", -1);
        }
    }

}
