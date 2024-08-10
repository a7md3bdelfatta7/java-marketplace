package com.luv2code.springboot.demo.services.vo;

public class ProductVO {
    private Long id;
    private String name;
    private String description;
    private Double price;
    // Add other fields as needed

    public ProductVO() {
    }

    public ProductVO(Long id) {
        this.id = id;
    }
    // Getters and setters

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
