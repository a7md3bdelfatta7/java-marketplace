package com.springboot.demo.controller.input;

public class OrderItemInput{
    private Long productId;
    private Integer quantity;

    // Default constructor
    public OrderItemInput() {
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