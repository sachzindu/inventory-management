package com.example.InventoryManagement.service.Impl;

import com.example.InventoryManagement.dto.CategoryDTO;
import com.example.InventoryManagement.dto.ProductDTO;
import com.example.InventoryManagement.dto.Response;
import com.example.InventoryManagement.entity.Category;
import com.example.InventoryManagement.entity.Product;
import com.example.InventoryManagement.exceptions.NotFoundException;
import com.example.InventoryManagement.repository.CategoryRepository;
import com.example.InventoryManagement.repository.ProductRepository;
import com.example.InventoryManagement.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {

    private  final ProductRepository productRepository;
    private final ModelMapper modelMapper;
    private final CategoryRepository categoryRepository;
    private static final String  IMAGEDIRECTORY=System.getProperty("user.dir")+"/product-image/";

    @Override
    public Response saveProduct(ProductDTO productDTO, MultipartFile imageFile)

    {
        Category category=categoryRepository.findById(productDTO.getProductId()).orElseThrow(()->new NotFoundException("Category not found of product"));
        Product productToSave=Product.builder()
                .name(productDTO.getName())
                .sku(productDTO.getSku())
                .price(productDTO.getPrice())
                .stockQuantity(productDTO.getStockQuantity())
                .description(productDTO.getDescription())
                .category(category)
                .build();

        if(imageFile!=null){
            String imagePath=saveImage(imageFile);
            productToSave.setImageUrl(imagePath);
        }

        productRepository.save(productToSave);

        return Response.builder()
                .status(200)
                .message("Product created successfully")
                .build();


    }

    @Override
    public Response getAllProducts()
    {
        List<Product> products=productRepository.findAll((Sort.by(Sort.Direction.DESC,"id")));
        List<ProductDTO> ProductDTOS=modelMapper.map(products,new TypeToken<List<ProductDTO>>(){}.getType());



        return Response.builder()
                .status(200)
                .message("Fetched all products successfully")
                .products(ProductDTOS)
                .build();
    }

    @Override
    public Response getProductById(Long id) {
        Product product=  productRepository.findById(id).orElseThrow(()->new NotFoundException("Product not found"));
        ProductDTO productDTO=modelMapper.map(product,ProductDTO.class);

        return Response.builder()
                .status(200)
                .message("Fetched the product successfully")
                .product(productDTO)
                .build();
    }

    @Override
    public Response updateProduct(ProductDTO productDTO, MultipartFile imageFile)

    {
        Product existingProduct=productRepository.findById(productDTO.getProductId()).orElseThrow(()->new NotFoundException("Product not found"));
        if(!imageFile.isEmpty()&&imageFile!=null){

            String imagePath=saveImage(imageFile);
            existingProduct.setImageUrl(imagePath);


        }

        if(productDTO.getCategoryId()!=null &&productDTO.getCategoryId()>0){
            Category category=categoryRepository.findById(productDTO.getProductId()).orElseThrow(()->new NotFoundException("Category not found of product"));
            existingProduct.setCategory(category);

        }

        if(productDTO.getName() !=null && !productDTO.getName().isBlank()){
            existingProduct.setName(productDTO.getName());
        }

        if(productDTO.getSku() !=null && !productDTO.getSku().isBlank()){
            existingProduct.setSku(productDTO.getSku());
        }

        if(productDTO.getPrice() !=null && !(productDTO.getPrice().compareTo(BigDecimal.ZERO) >=0)){
            existingProduct.setPrice(productDTO.getPrice());
        }

        if(productDTO.getStockQuantity()!=null && productDTO.getStockQuantity()>=0){
            existingProduct.setStockQuantity(productDTO.getStockQuantity());
        }

        productRepository.save(existingProduct);

        return Response.builder()
                .status(200)
                .message("Product updated  successfully")
                .build();
    }

    @Override
    public Response deleteProduct(Long id) {
        Product  existingproduct=  productRepository.findById(id).orElseThrow(()->new NotFoundException("Product not found"));
        productRepository.deleteById(id);

        return Response.builder()
                .status(200)
                .message("Product deleted successfully")
                .build();
    }

    private String saveImage(MultipartFile imageFile){
        //validate image check
        if(!imageFile.getContentType().startsWith("image/")){
            throw new IllegalArgumentException("Only image files are allowed");
        }

        File directory=new File(IMAGEDIRECTORY);
        if(!directory.exists()){
            directory.mkdir();
            log.info("Directory was created");
        }

        String uniqueFileName= UUID.randomUUID()+"_"+imageFile.getOriginalFilename();
        String imagePath=IMAGEDIRECTORY+uniqueFileName;

        try{
            File destinationFile=new File(imagePath);
            imageFile.transferTo(destinationFile);

        }catch(Exception e){
            throw new IllegalArgumentException("Error in imagePartFile"+e.getMessage());

        }

        return imagePath;



    }
}
