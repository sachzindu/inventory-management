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

}
