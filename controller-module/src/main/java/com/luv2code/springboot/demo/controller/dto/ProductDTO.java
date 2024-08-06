package com.luv2code.springboot.demo.controller.dto;

public class ProductDTO {
    private Integer id;
    private String name;
    private String description;
    private Double price;

    // Default constructor
    public ProductDTO() {
    }

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String productName) {
        this.name = productName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String productDescription) {
        this.description = productDescription;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double productPrice) {
        this.price = productPrice;
    }
}
