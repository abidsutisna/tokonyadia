package com.enigma.tokonyadia.services.impl;

import java.util.List;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.enigma.tokonyadia.dto.request.AuthRequest;
import com.enigma.tokonyadia.dto.response.UserResponse;
import com.enigma.tokonyadia.models.entity.Role;
import com.enigma.tokonyadia.models.entity.UserCredential;
import com.enigma.tokonyadia.models.repos.UserCredentialRepository;
import com.enigma.tokonyadia.services.AuthService;
import com.enigma.tokonyadia.services.RoleService;
import com.enigma.tokonyadia.utils.RoleEnum;

import lombok.RequiredArgsConstructor;
import security.JwtUtils;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{

    private final PasswordEncoder passwordEncoder;

    private final UserCredentialRepository userCredentialRepository;

    private final RoleService roleService;

    // private final JwtUtils jwtUtils;

    // private final AuthenticationManager authenticationManager;

    @Override
    public String login(String email, String password) {
        return null;
    }

    @Override
    public UserResponse register(AuthRequest authRequest) {
        Role roleCustomer = roleService.getOrSave(RoleEnum.CUSTOMER);
        String hashPassword = passwordEncoder.encode(authRequest.getPassword());
        UserCredential userCredential = UserCredential.builder()
            .email(authRequest.getEmail())
            .password(hashPassword)
            .roles(List.of(roleCustomer))
            .build();
        
        userCredentialRepository.saveAndFlush(userCredential);
        List<String> roles = userCredential.getRoles().stream().map(role -> role.getRole().name()).toList();
        return UserResponse.builder()
            .email(userCredential.getEmail())
            .roles(roles)
            .build();
    }
    
}
