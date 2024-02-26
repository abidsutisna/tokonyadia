package com.enigma.tokonyadia.models.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Photo {

    private Integer id;

    private Integer albumId;

    private String title;

    private String url;

    private String thumbnailUrl;
}
