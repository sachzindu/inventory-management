package com.example.InventoryManagement.service;

import com.example.InventoryManagement.dto.LoginRequest;
import com.example.InventoryManagement.dto.RegisterRequest;
import com.example.InventoryManagement.dto.Response;
import com.example.InventoryManagement.dto.UserDTO;
import com.example.InventoryManagement.entity.User;

public interface UserService {
    Response registerUser(RegisterRequest registerRequest);
    Response loginUser(LoginRequest loginRequest);
    User getCurrentLoggedInUser();
    Response updateUser(Long id, UserDTO userDTO);
    Response deleteUser(Long id);
    Response getUserTransactions(Long id);
    Response getAllUsers();

}
