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

    record NewCustomerRequest(String name,String email, Integer age){
    }

    @PostMapping
    public ResponseEntity<Customer> addCustomer (@RequestBody NewCustomerRequest request){
        Customer customer = new Customer();

        customer.setName(request.name());
        customer.setEmail(request.email());
        customer.setAge(request.age());
        return ResponseEntity.ok(customerService.save(customer));
    }

    @PutMapping("/{customerId}")
    public ResponseEntity<Customer> updateCustomer (@PathVariable("customerId") Integer id,
                                          @RequestBody NewCustomerRequest request){

        Optional<Customer> customer = customerService.findById(id);

        if(customer.isPresent()) {
            Customer updatedCustomer = customer.get();
            updatedCustomer.setName(request.name());
            updatedCustomer.setEmail(request.email());
            updatedCustomer.setAge(request.age());

            return ResponseEntity.ok(customerService.save(updatedCustomer));
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("{customerId}")
    public ResponseEntity<Customer> deleteCustomer(@PathVariable("customerId") Integer id){
        Optional<Customer> customer = customerService.findById(id);
        if(customer.isPresent()) {
            customerService.deleteById(customer.get().getId());
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.notFound().build();
        }
    }


    @GetMapping("{customerId}")
    public Customer getCustomer(@PathVariable("customerId") Integer id){
//        log.info(id.toString());
        try{
            return customerService.findById(id).orElseThrow(() -> new RuntimeException("Customer not found"));
        }catch (Exception e){
//            log.error(e.getMessage());
            return new Customer(-1,"","",-1);
        }
    }

}
