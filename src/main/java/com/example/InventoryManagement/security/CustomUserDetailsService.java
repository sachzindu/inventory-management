package com.example.InventoryManagement.security;

import com.example.InventoryManagement.entity.User;
import com.example.InventoryManagement.exceptions.NotFoundException;
import com.example.InventoryManagement.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user=userRepository.findByEmail(username).orElseThrow(()->new NotFoundException("User not found"));
        return AuthUser.builder()
                .user(user)
                .build();
    }
}
