package com.springboot.demo.services.vo;


import java.time.LocalDateTime;
import java.util.List;

public class PurchaseOrderVO {
    private Long id;
    private Double totalAmount;
    private LocalDateTime orderDate;
    private List<OrderItemVO> orderItems;
    private CustomerVO customer;
    // Add other fields as needed

    // Getters and setters

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

//    public Long getCustomerId() {
//        return customerId;
//    }
//

    public void setCustomerById(Long customerId) {
        this.customer = new CustomerVO(customerId);
    }

    public CustomerVO getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerVO customer) {
        this.customer = customer;
    }


    public List<OrderItemVO> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItemVO> orderItems) {
        this.orderItems = orderItems;
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

    @Override
    public String toString() {
        return "PurchaseOrderVO{" +
                "id=" + id +
                ", totalAmount=" + totalAmount +
                ", orderDate=" + orderDate +
                ", orderItems=" + orderItems +
                '}';
    }

}

