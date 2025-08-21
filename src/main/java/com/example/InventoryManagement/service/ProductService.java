package com.example.InventoryManagement.service;

import com.example.InventoryManagement.dto.ProductDTO;
import com.example.InventoryManagement.dto.Response;
import org.springframework.web.multipart.MultipartFile;

public interface ProductService {
    Response saveProduct(ProductDTO productDTO, MultipartFile imageFile);
    Response getAllProducts();
    Response getProductById(Long id);
    Response updateProduct(ProductDTO productDTO,MultipartFile imageFile);
    Response deleteProduct(Long id);
}
