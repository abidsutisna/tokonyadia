package com.enigma.tokonyadia.services;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.enigma.tokonyadia.models.entity.UserCredential;

public interface UserCredentialService extends UserDetailsService{
    UserCredential loadByUserCredentialId(String userId);

}
