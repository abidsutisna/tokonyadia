package com.enigma.tokonyadia.dto;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDTO {

    @NotEmpty(message = "Product name is required")
    private String name;

    @NotNull
    private int price;

    @NotNull
    private int stock;
}
