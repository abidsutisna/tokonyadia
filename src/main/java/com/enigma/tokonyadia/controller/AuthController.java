package com.enigma.tokonyadia.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.enigma.tokonyadia.dto.request.AuthRequest;
import com.enigma.tokonyadia.dto.response.ResponseDTO;
import com.enigma.tokonyadia.dto.response.UserResponse;
import com.enigma.tokonyadia.services.AuthService;
import com.enigma.tokonyadia.utils.Message;
import com.enigma.tokonyadia.utils.MessageResultConstant;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    
    @PostMapping("/register")
    public ResponseEntity<?> register (@RequestBody AuthRequest authRequest){
        ResponseDTO<UserResponse> response = new ResponseDTO<>();

        UserResponse userResponse = authService.register(authRequest);

        response.setPayload(userResponse);
        response.getMessage().add(MessageResultConstant.SUCCESS_REGISTER);
        response.setStatus(HttpStatus.CREATED.getReasonPhrase());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }
}
