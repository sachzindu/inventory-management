package com.example.InventoryManagement.dto;

import com.example.InventoryManagement.entity.Transaction;
import com.example.InventoryManagement.enums.UserRole;
import lombok.Builder;
import org.w3c.dom.stylesheets.LinkStyle;

import java.time.LocalDateTime;
import java.util.List;
@Builder
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

    public Response(int status, String message, String token, UserRole role, String expirationTime, Integer totalPages, Long totalElement, UserDTO user, List<UserDTO> users, SupplierDTO supplier, List<SupplierDTO> suppliers, CategoryDTO category, List<CategoryDTO> categories, ProductDTO product, List<ProductDTO> products, TransactionDTO transaction, List<TransactionDTO> transactions, LocalDateTime timeStamp) {
        this.status = status;
        this.message = message;
        this.token = token;
        this.role = role;
        this.expirationTime = expirationTime;
        this.totalPages = totalPages;
        this.totalElement = totalElement;
        this.user = user;
        this.users = users;
        this.supplier = supplier;
        this.suppliers = suppliers;
        this.category = category;
        this.categories = categories;
        this.product = product;
        this.products = products;
        this.transaction = transaction;
        this.transactions = transactions;
        this.timeStamp = timeStamp;
    }

    public Response() {
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public String getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(String expirationTime) {
        this.expirationTime = expirationTime;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public Long getTotalElement() {
        return totalElement;
    }

    public void setTotalElement(Long totalElement) {
        this.totalElement = totalElement;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public List<UserDTO> getUsers() {
        return users;
    }

    public void setUsers(List<UserDTO> users) {
        this.users = users;
    }

    public SupplierDTO getSupplier() {
        return supplier;
    }

    public void setSupplier(SupplierDTO supplier) {
        this.supplier = supplier;
    }

    public List<SupplierDTO> getSuppliers() {
        return suppliers;
    }

    public void setSuppliers(List<SupplierDTO> suppliers) {
        this.suppliers = suppliers;
    }

    public CategoryDTO getCategory() {
        return category;
    }

    public void setCategory(CategoryDTO category) {
        this.category = category;
    }

    public List<CategoryDTO> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoryDTO> categories) {
        this.categories = categories;
    }

    public ProductDTO getProduct() {
        return product;
    }

    public void setProduct(ProductDTO product) {
        this.product = product;
    }

    public List<ProductDTO> getProducts() {
        return products;
    }

    public void setProducts(List<ProductDTO> products) {
        this.products = products;
    }

    public TransactionDTO getTransaction() {
        return transaction;
    }

    public void setTransaction(TransactionDTO transaction) {
        this.transaction = transaction;
    }

    public List<TransactionDTO> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<TransactionDTO> transactions) {
        this.transactions = transactions;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }
}
