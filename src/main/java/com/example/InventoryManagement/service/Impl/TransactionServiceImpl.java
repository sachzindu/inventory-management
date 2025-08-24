package com.example.InventoryManagement.service.Impl;

import com.example.InventoryManagement.dto.Response;
import com.example.InventoryManagement.dto.TransactionDTO;
import com.example.InventoryManagement.dto.TransactionRequest;
import com.example.InventoryManagement.entity.Product;
import com.example.InventoryManagement.entity.Supplier;
import com.example.InventoryManagement.entity.Transaction;
import com.example.InventoryManagement.entity.User;
import com.example.InventoryManagement.enums.TransactionStatus;
import com.example.InventoryManagement.enums.TransactionType;
import com.example.InventoryManagement.exceptions.NameValueRequiredException;
import com.example.InventoryManagement.exceptions.NotFoundException;
import com.example.InventoryManagement.repository.ProductRepository;
import com.example.InventoryManagement.repository.SupplierRepository;
import com.example.InventoryManagement.repository.TransactionRepository;
import com.example.InventoryManagement.service.TransactionService;
import com.example.InventoryManagement.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final ModelMapper modelMapper;
    private final SupplierRepository supplierRepository;
    private final UserService userService;
    private final ProductRepository productRepository;

    @Override
    public Response RestockInventory(TransactionRequest transactionRequest) {
        Long productId=transactionRequest.getProductId();
        Long supplierId=transactionRequest.getSupplierId();
        Integer quantity=transactionRequest.getQuantity();

        if (supplierId==null) throw new NameValueRequiredException("Supplier id is required");
        Product product=productRepository.findById(productId).orElseThrow(()->new NotFoundException("Product not found in transaction"));
        Supplier supplier =supplierRepository.findById(supplierId).orElseThrow(()->new NotFoundException("Supplier not found in transaction"));
        User user=userService.getCurrentLoggedInUser();

        product.setStockQuantity(product.getStockQuantity()+quantity);
        productRepository.save(product);

        Transaction transaction = Transaction.builder()
                .transactionType(TransactionType.PURCHASE)
                .status(TransactionStatus.COMPLETED)
                .product(product)
                .user(user)
                .supplier(supplier)
                .totalProducts(quantity)
                .totalPrice(product.getPrice().multiply(BigDecimal.valueOf(quantity)))
                .description(transactionRequest.getDescription())
                .build();

        transactionRepository.save(transaction);
        return Response.builder()
                .status(200)
                .message("Purchase Made successfully")
                .build();


    }

    @Override
    public Response sell(TransactionRequest transactionRequest) {
        Long productId = transactionRequest.getProductId();
        Integer quantity = transactionRequest.getQuantity();

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("Product Not Found"));

        User user = userService.getCurrentLoggedInUser();

        //update the stock quantity and re-save
        product.setStockQuantity(product.getStockQuantity() - quantity);
        productRepository.save(product);


        //create a transaction
        Transaction transaction = Transaction.builder()
                .transactionType(TransactionType.SALE)
                .status(TransactionStatus.COMPLETED)
                .product(product)
                .user(user)
                .totalProducts(quantity)
                .totalPrice(product.getPrice().multiply(BigDecimal.valueOf(quantity)))
                .description(transactionRequest.getDescription())
                .build();

        transactionRepository.save(transaction);
        return Response.builder()
                .status(200)
                .message("Product Sale successfully made")
                .build();
    }

    @Override
    public Response returnToSupplier(TransactionRequest transactionRequest) {
        Long productId = transactionRequest.getProductId();
        Long supplierId = transactionRequest.getSupplierId();
        Integer quantity = transactionRequest.getQuantity();

        if (supplierId == null) throw new NameValueRequiredException("Supplier Id is Required");

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("Product Not Found"));

        Supplier supplier = supplierRepository.findById(supplierId)
                .orElseThrow(() -> new NotFoundException("Supplier Not Found"));

        User user = userService.getCurrentLoggedInUser();

        //update the stock quantity and re-save
        product.setStockQuantity(product.getStockQuantity() - quantity);
        productRepository.save(product);


        //create a transaction
        Transaction transaction = Transaction.builder()
                .transactionType(TransactionType.RETURN_TO_SUPPLIER)
                .status(TransactionStatus.PROCESSING)
                .product(product)
                .user(user)
                .totalProducts(quantity)
                .totalPrice(BigDecimal.ZERO)
                .description(transactionRequest.getDescription())
                .build();

        transactionRepository.save(transaction);

        return Response.builder()
                .status(200)
                .message("Product Returned in progress")
                .build();
    }

    @Override
    public Response getAllTransactions(int page, int size, String searchText) {

     Pageable pageable=PageRequest.of(page,size,Sort.by(Sort.Direction.DESC,"id"));
     Page<Transaction> transactionPage=transactionRepository.searchTransactions(searchText,pageable);
        List<TransactionDTO> transactionDTOS=modelMapper.map(transactionPage.getContent(),new TypeToken<List<TransactionDTO>>(){}.getType());
        transactionDTOS.forEach(transactionDTOItem -> {
            transactionDTOItem.setUser(null);
            transactionDTOItem.setProduct(null);
            transactionDTOItem.setSupplierDTO(null);



        });

        return Response.builder()
                .status(200)
                .message("Transactions returned")
                .transactions(transactionDTOS)
                .build();



    }

    @Override
    public Response getTransactionById(Long id) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Transaction Not Found"));

        TransactionDTO transactionDTO = modelMapper.map(transaction, TransactionDTO.class);

        transactionDTO.getUser().setTransactions(null);

        return Response.builder()
                .status(200)
                .message("success")
                .transaction(transactionDTO)
                .build();
    }

    @Override
    public Response getAllTransactionsByMonthAndYear(int month, int year) {
        List<Transaction> transactions = transactionRepository.findByAllByMonthAndYear(month,year);

        List<TransactionDTO> transactionDTOS = modelMapper.map(transactions, new TypeToken<List<TransactionDTO>>() {
        }.getType());

        transactionDTOS.forEach(transactionDTO -> {
            transactionDTO.setUser(null);
            transactionDTO.setProduct(null);
            transactionDTO.setSupplierDTO(null);
        });

        return Response.builder()
                .status(200)
                .message("success")
                .transactions(transactionDTOS)
                .build();
    }

    @Override
    public Response UpdateTransactionStatus(Long transactionId, TransactionStatus transactionStatus)
    {
        Transaction existingTransaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new NotFoundException("Transaction Not Found"));

        existingTransaction.setStatus(transactionStatus);
        existingTransaction.setUpdatedAt(LocalDateTime.now());

        transactionRepository.save(existingTransaction);

        return Response.builder()
                .status(200)
                .message("Transaction Status Successfully Updated")
                .build();

    }
}
