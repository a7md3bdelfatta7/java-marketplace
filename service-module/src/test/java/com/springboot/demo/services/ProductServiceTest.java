package com.springboot.demo.services;

import com.springboot.demo.repository.ProductRepository;
import com.springboot.demo.repository.entities.Product;
import com.springboot.demo.services.ProductService;
import com.springboot.demo.services.vo.ProductVO;
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
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private ProductService productService;

    private Product productEntity;
    private ProductVO productVO;

    @BeforeEach
    void setUp() {
        productEntity = new Product();
        productEntity.setId(1);
        productEntity.setName("Test Product");
        productEntity.setPrice(100.0);
        productEntity.setDescription("Test Description");

        productVO = new ProductVO();
        productVO.setId(1L);
        productVO.setName("Test Product");
        productVO.setPrice(100.0);
        productVO.setDescription("Test Description");
    }

    @Test
    void testFindAll() {
        when(productRepository.findAll()).thenReturn(Collections.singletonList(productEntity));
        when(modelMapper.map(productEntity, ProductVO.class)).thenReturn(productVO);

        List<ProductVO> products = productService.findAll();

        assertNotNull(products);
        assertEquals(1, products.size());
        assertEquals(productVO.getId(), products.get(0).getId());
        assertEquals(productVO.getPrice(), products.get(0).getPrice());
        assertEquals(productVO.getDescription(), products.get(0).getDescription());

        verify(productRepository, times(1)).findAll();
        verify(modelMapper, times(1)).map(productEntity, ProductVO.class);
    }

    @Test
    void testFindById() {
        when(productRepository.findById(1)).thenReturn(Optional.of(productEntity));
        when(modelMapper.map(productEntity, ProductVO.class)).thenReturn(productVO);

        Optional<ProductVO> foundProduct = productService.findById(1);

        assertTrue(foundProduct.isPresent());
        assertEquals(productVO.getName(), foundProduct.get().getName());
        assertEquals(productVO.getPrice(), foundProduct.get().getPrice());
        assertEquals(productVO.getDescription(), foundProduct.get().getDescription());
        verify(productRepository, times(1)).findById(1);
        verify(modelMapper, times(1)).map(productEntity, ProductVO.class);
    }

    @Test
    void testSave() {
        when(productRepository.save(any(Product.class))).thenReturn(productEntity);
        when(modelMapper.map(productEntity, ProductVO.class)).thenReturn(productVO);
        when(modelMapper.map(productVO, Product.class)).thenReturn(productEntity);

        ProductVO savedProduct = productService.save(productVO);

        assertNotNull(savedProduct);
        assertEquals(productVO.getName(), savedProduct.getName());
        verify(productRepository, times(1)).save(any(Product.class));
        verify(modelMapper, times(1)).map(productVO, Product.class);
        verify(modelMapper, times(1)).map(productEntity, ProductVO.class);
    }

    @Test
    void testDeleteById() {
        doNothing().when(productRepository).deleteById(1);

        productService.deleteById(1);

        verify(productRepository, times(1)).deleteById(1);
    }
}