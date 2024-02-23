package com.enigma.tokonyadia.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.enigma.tokonyadia.dto.request.AuthRequest;
import com.enigma.tokonyadia.dto.response.UserResponse;
import com.enigma.tokonyadia.models.entity.Role;
import com.enigma.tokonyadia.models.entity.UserCredential;
import com.enigma.tokonyadia.models.repos.UserCredentialRepository;
import com.enigma.tokonyadia.securiry.JwtUtils;
import com.enigma.tokonyadia.services.AuthService;
import com.enigma.tokonyadia.services.RoleService;
import com.enigma.tokonyadia.utils.RoleEnum;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{

    private final PasswordEncoder passwordEncoder;

    private final UserCredentialRepository userCredentialRepository;

    private final RoleService roleService;

    private final JwtUtils jwtUtils;

    private final AuthenticationManager authenticationManager;

    @PostConstruct
    public void initSuperAdmin(){
        String email = "superadmin@gmail.com";
        String password = "superadmin";

        Optional<UserCredential> OptionalUserCredential = userCredentialRepository.findByEmail(email);
        if(OptionalUserCredential.isPresent()){
            return;
        }

        Role roleSuperAdmin = roleService.getOrSave(RoleEnum.ROLE_SUPER_ADMIN);
        Role roleAdmin = roleService.getOrSave(RoleEnum.ROLE_ADMIN);
        Role roleCustomer = roleService.getOrSave(RoleEnum.ROLE_CUSTOMER);

        String hashPassword = passwordEncoder.encode(password);
        
        UserCredential userCredential = UserCredential.builder()
            .email(email)
            .password(hashPassword)
            .roles(List.of(roleSuperAdmin, roleAdmin, roleCustomer))
            .build();
            
        userCredentialRepository.saveAndFlush(userCredential);
    }

    @Override
    public String login(String email, String password) {
        Authentication authenticationToken = new UsernamePasswordAuthenticationToken(email, password);
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        
        //save session
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserCredential userCredential = (UserCredential) authentication.getPrincipal();
        return jwtUtils.generateToken(userCredential);
    }

    @Override
    public UserResponse register(AuthRequest authRequest) {
        Role roleCustomer = roleService.getOrSave(RoleEnum.ROLE_CUSTOMER);
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

    @Override
    public UserResponse registerAdmin(AuthRequest authRequest) {
        Role roleCustomer = roleService.getOrSave(RoleEnum.ROLE_CUSTOMER);
        Role roleAdmin = roleService.getOrSave(RoleEnum.ROLE_ADMIN);

        String hashPassword = passwordEncoder.encode(authRequest.getPassword());

        UserCredential userCredential = UserCredential.builder()
            .email(authRequest.getEmail())
            .password(hashPassword)
            .roles(List.of(roleCustomer, roleAdmin))
            .build();
        
        userCredentialRepository.saveAndFlush(userCredential);
        List<String> roles = userCredential.getRoles().stream().map(role -> role.getRole().name()).toList();
        return UserResponse.builder()
            .email(userCredential.getEmail())
            .roles(roles)
            .build();
    }
    
}
