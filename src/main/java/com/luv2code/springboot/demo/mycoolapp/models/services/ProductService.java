package com.luv2code.springboot.demo.mycoolapp.models.services;

import com.luv2code.springboot.demo.mycoolapp.models.repositories.ProductRepository;
import com.luv2code.springboot.demo.mycoolapp.models.entities.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Optional<Product> findById(Integer id) {
        return productRepository.findById(id);
    }

    public Product save(Product product) {
        return productRepository.save(product);
    }

    public void deleteById(Integer id) {
        productRepository.deleteById(id);
    }
}