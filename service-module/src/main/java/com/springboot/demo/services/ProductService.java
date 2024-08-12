package com.springboot.demo.services;

import com.springboot.demo.repository.ProductRepository;
import com.springboot.demo.repository.entities.Product;
import com.springboot.demo.services.vo.ProductVO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
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

    @Cacheable("products")
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