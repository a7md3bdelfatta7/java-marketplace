package com.luv2code.springboot.demo.controller.dto;

public class CustomerDTO {
    private Integer id;
    private String userName;
    private String email;
    private Integer age;

    // Default constructor
    public CustomerDTO() {
    }

    // Parameterized constructor
    public CustomerDTO(Integer id, String userName, String email, Integer age) {
        this.id = id;
        this.userName = userName;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String username) {
        this.userName = username;
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