package com.example.InventoryManagement.service;

import com.example.InventoryManagement.dto.Response;
import com.example.InventoryManagement.dto.TransactionRequest;
import com.example.InventoryManagement.enums.TransactionStatus;

public interface TransactionService {
    Response RestockInventory(TransactionRequest transactionRequest);
    Response sell(TransactionRequest transactionRequest);
    Response returnToSupplier(TransactionRequest transactionRequest);
    Response getAllTransactions(int page,int size, String searchText);
    Response getTransactionById(Long id);
    Response getAllTransactionsByMonthAndYear(int month, int year);
    Response UpdateTransactionStatus(Long transactionId, TransactionStatus transactionStatus);

}
