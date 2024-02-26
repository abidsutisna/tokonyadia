package com.enigma.tokonyadia.services.impl;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.enigma.tokonyadia.models.entity.UserCredential;
import com.enigma.tokonyadia.models.repos.UserCredentialRepository;
import com.enigma.tokonyadia.services.UserCredentialService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserCredentialServiceImpl implements UserCredentialService{

    private final UserCredentialRepository userCredentialRepository;

    @Override
    public UserCredential loadByUserCredentialId(String userId){
       return userCredentialRepository.findById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized"));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userCredentialRepository.findByEmail(username).orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized"));
    }
    
}
