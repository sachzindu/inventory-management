package com.example.InventoryManagement.dto;

import com.example.InventoryManagement.entity.Transaction;
import com.example.InventoryManagement.enums.UserRole;
import org.w3c.dom.stylesheets.LinkStyle;

import java.time.LocalDateTime;
import java.util.List;

public class Response {
    //generic
    private int status;
    private String message;
    //for login
    private String token;
    private UserRole role;
    private String expirationTime;

    //for pagination

    private Integer totalPages;
    private Long totalElement;

    private UserDTO user;
    private List<UserDTO> users;
    private SupplierDTO supplier;
    private List<SupplierDTO> suppliers;
    private CategoryDTO category;
    private List<CategoryDTO> categories;
    private ProductDTO product;
    private List<ProductDTO> products;
    private TransactionDTO  transaction;
    private List<TransactionDTO> transactions;
    private LocalDateTime timeStamp=LocalDateTime.now();




}
