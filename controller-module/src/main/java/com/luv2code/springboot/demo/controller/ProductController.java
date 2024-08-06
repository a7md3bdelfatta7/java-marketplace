package com.luv2code.springboot.demo.controller;

import com.luv2code.springboot.demo.controller.dto.ProductDTO;
import com.luv2code.springboot.demo.services.ProductService;
import com.luv2code.springboot.demo.services.vo.ProductVO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    @Autowired
    private ProductService productService;
    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    public List<ProductDTO> getAllProducts() {
        List<ProductVO> products = productService.findAll();
        return products.stream()
                .map(productVO -> modelMapper.map(productVO, ProductDTO.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Integer id) {
        Optional<ProductVO> product = productService.findById(id);
        return product.map(productVO -> ResponseEntity.ok(
                modelMapper.map(productVO, ProductDTO.class)
        )).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ProductDTO createProduct(@RequestBody ProductVO productVO) {
        return modelMapper.map(productService.save(productVO), ProductDTO.class);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Integer id, @RequestBody ProductDTO productDetails) {
        Optional<ProductVO> product = productService.findById(id);
        if (product.isPresent()) {
            ProductVO updatedProduct = product.get();
            updatedProduct.setName(productDetails.getName());
            updatedProduct.setDescription(productDetails.getDescription());
            updatedProduct.setPrice(productDetails.getPrice());
            productService.save(updatedProduct);
            return ResponseEntity.ok(modelMapper.map(updatedProduct, ProductDTO.class));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Integer id) {
        Optional<ProductVO> productVo = productService.findById(id);
        if (productVo.isPresent()) {
            productService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}