package com.luv2code.springboot.demo.services;

import com.luv2code.springboot.demo.repository.ProductRepository;
import com.luv2code.springboot.demo.repository.entities.Customer;
import com.luv2code.springboot.demo.repository.entities.Product;
import com.luv2code.springboot.demo.services.vo.CustomerVO;
import com.luv2code.springboot.demo.services.vo.ProductVO;
import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<ProductVO> findAll() {
        List<Product> entities = productRepository.findAll();
        return entities.stream()
                .map(entity -> modelMapper.map(entity, ProductVO.class))
                .collect(Collectors.toList());
    }

    public Optional<ProductVO> findById(Integer id) {
        Optional<Product> product = productRepository.findById(id);
        return product.map(value -> modelMapper.map(value, ProductVO.class));
    }

    public ProductVO save(ProductVO productVO) {
        Product entity = modelMapper.map(productVO, Product.class);
        entity = productRepository.save(entity);
        return modelMapper.map(entity, ProductVO.class);
    }

    public void deleteById(Integer id) {
        productRepository.deleteById(id);
    }
}