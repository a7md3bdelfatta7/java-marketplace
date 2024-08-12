package com.springboot.demo.services.vo;

import java.io.Serializable;

public class CustomerVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String username;
    private String email;
    private Integer age;
    // Add other fields as needed

    // Default constructor
    public CustomerVO() {
    }

    public CustomerVO(Long id) {
        this.id = id;
    }

    // Parameterized constructor
    public CustomerVO(String username, String email, Integer age) {
        this.username = username;
        this.email = email;
        this.age = age;
    }


    // Getters and setters

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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