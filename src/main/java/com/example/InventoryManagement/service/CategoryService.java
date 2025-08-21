package com.example.InventoryManagement.service;

import com.example.InventoryManagement.dto.CategoryDTO;
import com.example.InventoryManagement.dto.Response;

public interface CategoryService {
    Response createCategory(CategoryDTO categoryDTO);
    Response getAllCategories();
    Response getCategoryById(Long id);
    Response updateCategory(Long id,CategoryDTO categoryDTO);
    Response deleteCategory(Long id);

}
