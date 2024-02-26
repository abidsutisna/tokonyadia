package com.enigma.tokonyadia.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.enigma.tokonyadia.dto.request.AuthRequest;
import com.enigma.tokonyadia.dto.response.ResponseDTO;
import com.enigma.tokonyadia.dto.response.UserResponse;
import com.enigma.tokonyadia.models.entity.Photo;
import com.enigma.tokonyadia.services.PhotoServiceClient;
import com.enigma.tokonyadia.utils.ApiUrlConstant;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("photos") 
@RequiredArgsConstructor
public class PhotoController {

    private final PhotoServiceClient photoServiceClient;
    

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN', 'CUSTOMER')")
    public ResponseEntity<?> getListPhoto (){

        List<Photo> listPhoto = photoServiceClient.getListPhoto();
        ResponseDTO<List<Photo>> responseDTO = new ResponseDTO<>();
        responseDTO.getMessage().add("succes");
        responseDTO.setStatus(HttpStatus.OK.getReasonPhrase());
        responseDTO.setPayload(listPhoto);

        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }

    @GetMapping(ApiUrlConstant.PATH_ID)
    public ResponseEntity<?> createNewPhoto(@RequestBody Photo photo){

        ResponseDTO<Photo> responseDTO = new ResponseDTO<>();
        Photo newPhoto = photoServiceClient.createPhoto(photo);
        responseDTO.getMessage().add("succes");
        responseDTO.setStatus(HttpStatus.OK.getReasonPhrase());
        responseDTO.setPayload(newPhoto);

        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }
}
