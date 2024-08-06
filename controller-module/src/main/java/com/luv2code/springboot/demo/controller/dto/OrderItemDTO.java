package com.luv2code.springboot.demo.controller.dto;

public class OrderItemDTO {
    private Long productId;
    private Integer quantity;

    // Default constructor
    public OrderItemDTO() {
    }

    // Getters and Setters
    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
