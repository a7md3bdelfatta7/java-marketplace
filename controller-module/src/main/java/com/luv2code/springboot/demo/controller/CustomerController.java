package com.luv2code.springboot.demo.controller;
import com.luv2code.springboot.demo.models.entities.Customer;
import com.luv2code.springboot.demo.models.services.CustomerService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {

    private static final Logger log = LoggerFactory.getLogger(MyCoolappApplication.class);


    @Autowired
    private CustomerService customerService;

    @GetMapping
    public List<Customer> getCustomers(){
        return customerService.findAll();
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
