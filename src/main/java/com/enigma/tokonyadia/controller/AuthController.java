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
import com.enigma.tokonyadia.utils.ApiUrlConstant;
import com.enigma.tokonyadia.utils.MessageResultConstant;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(ApiUrlConstant.BASE_PATH_AUTH)
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    
    @PostMapping(ApiUrlConstant.REGISTER)
    public ResponseEntity<?> register (@RequestBody AuthRequest authRequest){
        ResponseDTO<UserResponse> response = new ResponseDTO<>();

        UserResponse userResponse = authService.register(authRequest);

        response.setPayload(userResponse);
        response.getMessage().add(MessageResultConstant.SUCCESS_REGISTER);
        response.setStatus(HttpStatus.CREATED.getReasonPhrase());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }

    @PostMapping(ApiUrlConstant.REGISTER_ADMIN)
    public ResponseEntity<?> registerAdmin (@RequestBody AuthRequest authRequest){
        ResponseDTO<UserResponse> response = new ResponseDTO<>();

        UserResponse userResponse = authService.registerAdmin(authRequest);

        response.setPayload(userResponse);
        response.getMessage().add(MessageResultConstant.SUCCESS_REGISTER);
        response.setStatus(HttpStatus.CREATED.getReasonPhrase());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }

    @PostMapping(ApiUrlConstant.LOGIN)
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest){
        ResponseDTO<String> response = new ResponseDTO<>();
        response.setStatus(HttpStatus.CREATED.getReasonPhrase());
        response.setPayload(authService.login(authRequest.getEmail(), authRequest.getPassword()));
        response.getMessage().add(MessageResultConstant.SUCCESS_LOGIN);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    
}
