package com.example.InventoryManagement.dto;

import com.example.InventoryManagement.enums.UserRole;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;
import java.util.List;

public class UserDTO {
    private long id;
    private String name;
    private String email;

@JsonIgnore
    private String password;

    private String phoneNumber;

    private UserRole role;

    private List<TransactionDTO> transactionDTOS;
    private final LocalDateTime createdAt=LocalDateTime.now();

    public UserDTO(long id, String name, String email, String password, String phoneNumber, UserRole role, List<TransactionDTO> transactionDTOS) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.transactionDTOS = transactionDTOS;
    }

    public UserDTO() {
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", role=" + role +
                ", createdAt=" + createdAt +
                '}';
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public List<TransactionDTO> getTransactions() {
        return transactionDTOS;
    }

    public void setTransactions(List<TransactionDTO> transactionDTOS) {
        this.transactionDTOS = transactionDTOS;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
