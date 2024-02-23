package com.enigma.tokonyadia.services;

import com.enigma.tokonyadia.dto.request.AuthRequest;
import com.enigma.tokonyadia.dto.response.UserResponse;

public interface AuthService {
    public String login(String email, String password);
    UserResponse register(AuthRequest authRequest);
    UserResponse registerAdmin(AuthRequest authRequest);
}
