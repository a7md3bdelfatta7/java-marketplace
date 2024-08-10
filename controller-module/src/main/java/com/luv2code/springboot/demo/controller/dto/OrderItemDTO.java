package com.luv2code.springboot.demo.controller.dto;


public class OrderItemDTO {
    private Integer quantity;
    private ProductDTO product;

    // Default constructor
    public OrderItemDTO() {
    }

    public ProductDTO getProduct() {
        return product;
    }

    public void setProduct(ProductDTO product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
