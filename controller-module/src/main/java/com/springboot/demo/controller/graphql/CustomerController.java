package com.springboot.demo.controller.graphql;

import com.springboot.demo.controller.dto.CustomerDTO;
import com.springboot.demo.controller.input.CustomerInput;
import com.springboot.demo.services.vo.CustomerVO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;
import com.springboot.demo.services.CustomerService;
import org.springframework.graphql.data.method.annotation.QueryMapping;

import java.util.List;
import java.util.Optional;

@Controller
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private ModelMapper modelMapper;

    @QueryMapping
    public List<CustomerDTO> getAllCustomers() {
        List<CustomerDTO> customers = customerService.getAllCustomers().stream().map(customerVO -> modelMapper.map(customerVO, CustomerDTO.class)).toList();
        return customers;
    }

    @QueryMapping
    public CustomerVO getCustomerById(@Argument Integer id) {
        Optional<CustomerVO> customer = customerService.getCustomerById(id);
        return customer.orElse(null);
    }

    @MutationMapping
    public CustomerDTO createUser(@Argument CustomerInput input) {
        CustomerVO customer = new CustomerVO(input.getUsername(),input.getEmail(),input.getAge());
        return modelMapper.map(customerService.save(customer), CustomerDTO.class);
    }

    @MutationMapping
    public CustomerDTO updateUser(@Argument CustomerInput input) {
        CustomerVO customerVO = customerService.updateUser(input.getId(), input.getUsername(), input.getEmail(), input.getAge());
        return modelMapper.map(customerVO, CustomerDTO.class);
    }

    @MutationMapping
    public Boolean deleteCustomer(@Argument Integer id) {
        return customerService.deleteCustomer(id);
    }




}
