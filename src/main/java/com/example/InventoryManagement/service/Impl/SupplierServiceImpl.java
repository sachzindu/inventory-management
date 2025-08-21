package com.example.InventoryManagement.service.Impl;


import com.example.InventoryManagement.dto.Response;
import com.example.InventoryManagement.dto.SupplierDTO;
import com.example.InventoryManagement.entity.Category;
import com.example.InventoryManagement.entity.Supplier;
import com.example.InventoryManagement.exceptions.NotFoundException;
import com.example.InventoryManagement.repository.SupplierRepository;
import com.example.InventoryManagement.service.SupplierService;
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
public class SupplierServiceImpl implements SupplierService {

    private final SupplierRepository supplierRepository;
    private final ModelMapper modelMapper;

    @Override
    public Response addSupplier(SupplierDTO supplierDTO) {
        Supplier supplierToSave=modelMapper.map(supplierDTO,Supplier.class);
        supplierRepository.save(supplierToSave);

        return Response.builder()
                .status(200)
                .message("Supplier added successfully")
                .build();
    }

    @Override
    public Response getAllSuppliers() {
        List<Supplier> suppliers=supplierRepository.findAll((Sort.by(Sort.Direction.DESC,"id")));
        List<SupplierDTO> SupplierDTOS=modelMapper.map(suppliers,new TypeToken<List<SupplierDTO>>(){}.getType());

        return Response.builder()
                .status(200)
                .message("Fetched all suppliers successfully")
                .suppliers(SupplierDTOS)
                .build();


    }

    @Override
    public Response getSupplierById(Long id) {
        Supplier supplier=  supplierRepository.findById(id).orElseThrow(()->new NotFoundException("Supplier not found"));
        SupplierDTO supplierDTO=modelMapper.map(supplier,SupplierDTO.class);

        return Response.builder()
                .status(200)
                .message("Fetched the supplier successfully")
                .supplier(supplierDTO)
                .build();
    }

    @Override
    public Response updateSupplier(Long id, SupplierDTO supplierDTO) {

        Supplier existingSupplier=  supplierRepository.findById(id).orElseThrow(()->new NotFoundException("Supplier not found"));
       if (existingSupplier.getName()!=null) existingSupplier.setName(supplierDTO.getName());
        if (existingSupplier.getAddress()!=null) existingSupplier.setAddress(supplierDTO.getAddress());
        supplierRepository.save(existingSupplier);

        return Response.builder()
                .status(200)
                .message("Supplier updated successfully")
                .build();
    }

    @Override
    public Response deleteSupplier(Long id)

    {
        Supplier existingsupplier=  supplierRepository.findById(id).orElseThrow(()->new NotFoundException("Supplier not found"));
        supplierRepository.deleteById(id);

        return Response.builder()
                .status(200)
                .message("Supplier deleted successfully")
                .build();
    }
}
