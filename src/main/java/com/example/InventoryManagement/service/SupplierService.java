package com.example.InventoryManagement.service;

import com.example.InventoryManagement.dto.CategoryDTO;
import com.example.InventoryManagement.dto.Response;
import com.example.InventoryManagement.dto.SupplierDTO;

public interface SupplierService {
    Response addSupplier(SupplierDTO supplierDTO);
    Response getAllSuppliers();
    Response getSupplierById(Long id);
    Response updateSupplier(Long id,SupplierDTO supplierDTO);
    Response deleteSupplier(Long id);

}
