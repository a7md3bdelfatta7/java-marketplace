# Order Management System

## Table of Contents
- [Introduction](#introduction)
- [Features](#features)
- [Technologies Used](#technologies-used)
- [Setup and Installation](#setup-and-installation)

## Introduction
The Order Management System is a Spring Boot application designed to manage customer orders. It includes functionalities to create customers, products, and orders while calculating the total amount for each order dynamically.

## Features
- Customer Management
- Product Management
- Order Management
- Dynamic Total Amount Calculation
- RESTful API

## Technologies Used
- Java
- Spring Boot
- Spring Data JPA
- PostgreSQL
- Maven

## Setup and Installation

### Prerequisites
- Java 17 
- Maven
- PostgreSQL

### Installation Steps

1. **Clone the repository:**
   ```bash
   git clone https://github.com/yourusername/order-management-system.git
   cd order-management-system


2. **Configure the database:**
    Update the application.yml file in src/main/resources with your PostgreSQL database credentials
    ```bash
    spring:
      datasource:
        url: jdbc:postgresql://localhost:5432/yourdatabase
        username: yourusername
        password: yourpassword
      jpa:
        hibernate:
          ddl-auto: update
        show-sql: true
3. **Build the Project**
   ```bash 
   mvn clean install
   
4. **run the application**
   ```bash 
   mvn spring-boot:run
   
