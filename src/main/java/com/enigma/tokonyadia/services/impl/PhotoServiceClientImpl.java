package com.enigma.tokonyadia.services.impl;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.enigma.tokonyadia.models.entity.Photo;
import com.enigma.tokonyadia.services.PhotoServiceClient;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PhotoServiceClientImpl implements PhotoServiceClient {

    private final RestTemplate restTemplate;

    private String baseURl = "https://jsonplaceholder.typicode.com/photos";

    @Override
    public Photo createPhoto(Photo photo) {
        return restTemplate.postForObject(baseURl, photo, Photo.class);
    }

    @Override
    public List<Photo> getListPhoto() {
        ResponseEntity<Photo[]> res = restTemplate.getForEntity("https://jsonplaceholder.typicode.com/photos", Photo[].class); ;
        return Arrays.asList(res.getBody());
    }

    @Override
    public Photo getPhotoById(Integer id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getPhotoById'");
    }

    @Override
    public Photo updatePhoto(Photo photo) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updatePhoto'");
    }

    @Override
    public void deletePhotoById(Integer id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deletePhotoById'");
    }
}
