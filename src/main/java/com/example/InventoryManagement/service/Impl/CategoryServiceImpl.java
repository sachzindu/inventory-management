package com.example.InventoryManagement.service.Impl;

import com.example.InventoryManagement.dto.CategoryDTO;
import com.example.InventoryManagement.dto.Response;
import com.example.InventoryManagement.dto.UserDTO;
import com.example.InventoryManagement.entity.Category;
import com.example.InventoryManagement.entity.User;
import com.example.InventoryManagement.exceptions.NotFoundException;
import com.example.InventoryManagement.repository.CategoryRepository;
import com.example.InventoryManagement.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;


    @Override
    public Response createCategory(CategoryDTO categoryDTO) {
        Category categoryToSave=modelMapper.map(categoryDTO,Category.class);
        categoryRepository.save(categoryToSave);

        return Response.builder()
                .status(200)
                .message("Category created successfully")
                .build();
    }

    @Override
    public Response getAllCategories() {
        List<Category> categories=categoryRepository.findAll((Sort.by(Sort.Direction.DESC,"id")));
        List<CategoryDTO> CategoryDTOS=modelMapper.map(categories,new TypeToken<List<CategoryDTO>>(){}.getType());



        return Response.builder()
                .status(200)
                .message("Fetched all users successfully")
                .categories(CategoryDTOS)
                .build();


    }

    @Override
    public Response getCategoryById(Long id) {
        Category category=  categoryRepository.findById(id).orElseThrow(()->new NotFoundException("Category not found"));
        CategoryDTO categoryDTO=modelMapper.map(category,CategoryDTO.class);

        return Response.builder()
                .status(200)
                .message("Fetched the category successfully")
                .category(categoryDTO)
                .build();


    }

    @Override
    public Response updateCategory(Long id, CategoryDTO categoryDTO) {
        Category existingcategory=  categoryRepository.findById(id).orElseThrow(()->new NotFoundException("Category not found"));
        existingcategory.setName(categoryDTO.getName());
        categoryRepository.save(existingcategory);

        return Response.builder()
                .status(200)
                .message("Category updated successfully")
                .build();

    }

    @Override
    public Response deleteCategory(Long id) {
        Category existingcategory=  categoryRepository.findById(id).orElseThrow(()->new NotFoundException("Category not found"));
        categoryRepository.deleteById(id);

        return Response.builder()
                .status(200)
                .message("Category deleted successfully")
                .build();
    }
}
