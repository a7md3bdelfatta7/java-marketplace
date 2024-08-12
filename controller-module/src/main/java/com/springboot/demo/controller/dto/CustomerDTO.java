package com.springboot.demo.controller.dto;

public class CustomerDTO {
    private Integer id;
    private String username;
    private String email;
    private Integer age;

    // Default constructor
    public CustomerDTO() {
    }

    // Parameterized constructor
    public CustomerDTO(Integer id, String userName, String email, Integer age) {
        this.id = id;
        this.username = userName;
        this.email = email;
        this.age = age;
    }

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}