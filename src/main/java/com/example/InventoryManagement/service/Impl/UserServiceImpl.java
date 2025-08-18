package com.example.InventoryManagement.service.Impl;

import com.example.InventoryManagement.dto.LoginRequest;
import com.example.InventoryManagement.dto.RegisterRequest;
import com.example.InventoryManagement.dto.Response;
import com.example.InventoryManagement.dto.UserDTO;
import com.example.InventoryManagement.entity.User;
import com.example.InventoryManagement.enums.UserRole;
import com.example.InventoryManagement.exceptions.InvalidCredentialsException;
import com.example.InventoryManagement.exceptions.NotFoundException;
import com.example.InventoryManagement.repository.UserRepository;
import com.example.InventoryManagement.security.JwtUtils;
import com.example.InventoryManagement.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final JwtUtils jwtUtils;

    @Override
    public Response registerUser(RegisterRequest registerRequest) {
        UserRole role=UserRole.MANAGER;
        if(registerRequest.getRole()!=null){
          role=registerRequest.getRole();
        }
        User userToSave=User.builder()
                .name(registerRequest.getName())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .phoneNumber(registerRequest.getPhoneNumber())
                .role(role)
                .build();

        userRepository.save(userToSave);

        return Response.builder()
                .status(200)
                .message("User created successfully")
                .build();
    }

    @Override
    public Response loginUser(LoginRequest loginRequest) {
        User user=userRepository.findByEmail(loginRequest.getEmail()).orElseThrow(()->new NotFoundException("User not found"));
        if(!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())){
            throw new InvalidCredentialsException("Password is not valid");
        }

        String token= jwtUtils.generateToken(user.getEmail());
        return Response.builder()
                .status(200)
                .message("Login success")
                .role(user.getRole())
                .token(token)
                .expirationTime("6 months")
                .build();
    }

    @Override
    public User getCurrentLoggedInUser() {
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();;
        String email=authentication.getName();
        User user=userRepository.findByEmail(email).orElseThrow(()->new NotFoundException("Email not found"));
        user.setTransactions(null);
        return user;
    }

    @Override
    public Response updateUser(Long id, UserDTO userDTO) {
        User existinuser=userRepository.findById(id).orElseThrow(()->new NotFoundException("User  not found"));
        if (userDTO.getEmail()!=null) existinuser.setEmail(userDTO.getEmail());
        if (userDTO.getPhoneNumber()!=null) existinuser.setPhoneNumber(userDTO.getPhoneNumber());
        if (userDTO.getName()!=null) existinuser.setName(userDTO.getName());
        if (userDTO.getRole()!=null) existinuser.setRole(userDTO.getRole());

        if(userDTO.getPassword() !=null && !userDTO.getPassword().isEmpty()){
            existinuser.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        }
        userRepository.save(existinuser);

        return Response.builder()
                .status(200)
                .message("User updated successfully")
                .build();


    }

    @Override
    public Response deleteUser(Long id) {
        userRepository.findById(id).orElseThrow(()->new NotFoundException("User not found"));
        userRepository.deleteById(id);

        return Response.builder()
                .status(200)
                .message("User deleted successfully")
                .build();
    }

    @Override
    public Response getUserTransactions(Long id) {
      User user=  userRepository.findById(id).orElseThrow(()->new NotFoundException("User not found"));
        UserDTO userDTO=modelMapper.map(user, UserDTO.class);
        userDTO.getTransactions().forEach(transactionDTO->{
            transactionDTO.setUser(null);
            transactionDTO.setSupplierDTO(null);
        });

        return Response.builder()
                .status(200)
                .message("Success")
                .user(userDTO)
                .build();
    }

    @Override
    public Response getAllUsers() {
        List<User> users=userRepository.findAll((Sort.by(Sort.Direction.DESC,"id")));
        users.forEach(user ->user.setTransactions(null));
        List<UserDTO> UserDTOS=modelMapper.map(users,new TypeToken<List<UserDTO>>(){}.getType());

        UserDTOS.forEach(userDTO -> userDTO.setTransactions(null));

        return Response.builder()
                .status(200)
                .message("Fetched all users successfully")
                .users(UserDTOS)
                .build();

    }
}
