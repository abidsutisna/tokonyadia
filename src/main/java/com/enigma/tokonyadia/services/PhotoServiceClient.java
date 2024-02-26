package com.enigma.tokonyadia.services;

import java.util.List;

import com.enigma.tokonyadia.models.entity.Photo;

public interface PhotoServiceClient {
    Photo createPhoto(Photo photo);
    List<Photo> getListPhoto();
    Photo getPhotoById (Integer id);
    Photo updatePhoto (Photo photo);
    void deletePhotoById (Integer id);
}
