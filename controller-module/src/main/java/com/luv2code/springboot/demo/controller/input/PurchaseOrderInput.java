package com.luv2code.springboot.demo.controller.input;

import java.time.LocalDateTime;
import java.util.List;

public class PurchaseOrderInput {

    private Integer id;
    private Long customerId;
    private Double totalAmount;
    private LocalDateTime orderDate;
    private List<OrderItemInput> orderItems;

    // Default constructor
    public PurchaseOrderInput() {
    }

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public List<OrderItemInput> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItemInput> orderItems) {
        this.orderItems = orderItems;
    }


    @Override
    public String toString() {
        return "PurchaseOrderDTO{" +
                "id=" + id +
                ", customerId=" + customerId +
                ", totalAmount=" + totalAmount +
                ", orderDate=" + orderDate +
                ", orderItems=" + orderItems +
                '}';
    }

}
