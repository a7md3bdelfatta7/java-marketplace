package com.luv2code.springboot.demo.controller.graphql;

import com.luv2code.springboot.demo.controller.dto.ProductDTO;
import com.luv2code.springboot.demo.controller.input.ProductInput;
import com.luv2code.springboot.demo.services.ProductService;
import com.luv2code.springboot.demo.services.vo.ProductVO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class ProductGraphQLController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ModelMapper modelMapper;

    @QueryMapping
    public List<ProductDTO> getAllProducts() {
        List<ProductVO> products = productService.findAll();
        return products.stream()
                .map(productVO -> modelMapper.map(productVO, ProductDTO.class))
                .collect(Collectors.toList());
    }

    @QueryMapping
    public ProductDTO getProductById(@Argument Integer id) {
        Optional<ProductVO> product = productService.findById(id);
        return product.map(productVO -> modelMapper.map(productVO, ProductDTO.class)).orElse(null);
    }

    @MutationMapping
    public ProductDTO createProduct(@Argument ProductInput input) {
        ProductVO productVO = modelMapper.map(input, ProductVO.class);
        return modelMapper.map(productService.save(productVO), ProductDTO.class);
    }

    @MutationMapping
    public ProductDTO updateProduct(@Argument Integer id, @Argument ProductInput input) {
        Optional<ProductVO> product = productService.findById(id);
        if (product.isPresent()) {
            ProductVO updatedProduct = product.get();
            updatedProduct.setName(input.getName());
            updatedProduct.setDescription(input.getDescription());
            updatedProduct.setPrice(input.getPrice());
            productService.save(updatedProduct);
            return modelMapper.map(updatedProduct, ProductDTO.class);
        } else {
            return null;
        }
    }

    @MutationMapping
    public Boolean deleteProduct(@Argument Integer id) {
        Optional<ProductVO> productVo = productService.findById(id);
        if (productVo.isPresent()) {
            productService.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}