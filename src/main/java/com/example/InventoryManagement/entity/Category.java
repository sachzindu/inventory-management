package com.example.InventoryManagement.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

import java.util.List;

@Entity
@Table(name="categories")
@Builder
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotBlank(message ="name is required")
    @Column(unique = true)
    private String name;
    @NotBlank(message ="email is required")
    @Column(unique = true)
    private String email;
    @OneToMany(mappedBy = "category")
    private List<Product> products;


    public Category(long id, String name, String email, List<Product> products) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.products = products;
    }

    public Category() {
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

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
