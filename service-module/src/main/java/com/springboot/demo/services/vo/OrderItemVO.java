package com.springboot.demo.services.vo;

public class OrderItemVO {
    private Long id;
    private Integer quantity;
//    private Long productId;
    private ProductVO product;
    // Add other fields as needed


    // Getters and setters

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

//    public Long getProductId() {
//        return productId;
//    }
//
//    public void setProductId(Long productId) {
//        this.productId = productId;
//    }

    public ProductVO getProduct() {
        return product;
    }

    public void setProduct(ProductVO product) {
        this.product = product;
    }

    public void setProductById(Long productId) {
        this.product = new ProductVO(productId);
    }




}
