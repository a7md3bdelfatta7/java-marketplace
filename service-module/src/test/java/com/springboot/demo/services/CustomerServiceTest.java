package com.springboot.demo.services;

import com.springboot.demo.repository.CustomerRepository;
import com.springboot.demo.repository.entities.Customer;
import com.springboot.demo.services.CustomerService;
import com.springboot.demo.services.vo.CustomerVO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private CustomerService customerService;

    private Customer Customer;
    private CustomerVO customerVO;

    @BeforeEach
    void setUp() {
        Customer = new Customer();
        Customer.setId(1L);
        Customer.setName("John Doe");
        Customer.setEmail("john.doe@example.com");
        Customer.setAge(30);

        customerVO = new CustomerVO();
        customerVO.setId(1L);
        customerVO.setUsername("John Doe");
        customerVO.setEmail("john.doe@example.com");
        customerVO.setAge(30);
    }

    @Test
    void testGetAllCustomers() {
        when(customerRepository.findAll()).thenReturn(Collections.singletonList(Customer));
        when(modelMapper.map(Customer, CustomerVO.class)).thenReturn(customerVO);

        List<CustomerVO> customers = customerService.getAllCustomers();

        assertNotNull(customers);
        assertEquals(1, customers.size());
        assertEquals(customerVO.getId(), customers.get(0).getId());
        assertEquals(customerVO.getUsername(), customers.get(0).getUsername());
        assertEquals(customerVO.getEmail(), customers.get(0).getEmail());

        verify(customerRepository, times(1)).findAll();
        verify(modelMapper, times(1)).map(Customer, CustomerVO.class);
    }

    @Test
    void testGetCustomerById() {
        when(customerRepository.findById(1)).thenReturn(Optional.of(Customer));
        when(modelMapper.map(Customer, CustomerVO.class)).thenReturn(customerVO);

        Optional<CustomerVO> foundCustomer = customerService.getCustomerById(1);
        if(foundCustomer.isPresent()) {
            assertNotNull(foundCustomer);
            assertEquals(customerVO.getId(), foundCustomer.get().getId());
            assertEquals(customerVO.getUsername(), foundCustomer.get().getUsername());
            assertEquals(customerVO.getEmail(), foundCustomer.get().getEmail());
            assertEquals(customerVO.getAge(), foundCustomer.get().getAge());

            verify(customerRepository, times(1)).findById(1);
            verify(modelMapper, times(1)).map(Customer, CustomerVO.class);
        }
    }

    @Test
    void testGetCustomerByIdNotFound() {
        when(customerRepository.findById(1)).thenReturn(Optional.empty());

        Optional<CustomerVO> foundCustomer = customerService.getCustomerById(1);

        assertFalse(foundCustomer.isPresent());

        verify(customerRepository, times(1)).findById(1);
        verify(modelMapper, times(0)).map(any(), any());
    }

    @Test
    void testSaveCustomer() {
        when(modelMapper.map(customerVO, Customer.class)).thenReturn(Customer);
        when(customerRepository.save(Customer)).thenReturn(Customer);
        when(modelMapper.map(Customer, CustomerVO.class)).thenReturn(customerVO);

        CustomerVO savedCustomer = customerService.save(customerVO);

        assertNotNull(savedCustomer);
        assertEquals(customerVO.getId(), savedCustomer.getId());
        assertEquals(customerVO.getUsername(), savedCustomer.getUsername());
        assertEquals(customerVO.getEmail(), savedCustomer.getEmail());
        assertEquals(customerVO.getAge(), savedCustomer.getAge());

        verify(modelMapper, times(1)).map(customerVO, Customer.class);
        verify(customerRepository, times(1)).save(Customer);
        verify(modelMapper, times(1)).map(Customer, CustomerVO.class);
    }

}