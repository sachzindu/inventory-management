package com.example.InventoryManagement.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.Positive;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TransactionRequest {
    @Positive(message = "Product id is is required")
    private Long productId;
    @Positive(message = "Quantity is is required")
    private Integer quantity;
    private Long supplierId;
    private String description;

    public TransactionRequest(Long productId, Integer quantity, Long supplierId, String description) {
        this.productId = productId;
        this.quantity = quantity;
        this.supplierId = supplierId;
        this.description = description;
    }

    public TransactionRequest() {
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "TransactionRequest{" +
                "productId=" + productId +
                ", quantity=" + quantity +
                ", supplierId=" + supplierId +
                ", description='" + description + '\'' +
                '}';
    }
}
