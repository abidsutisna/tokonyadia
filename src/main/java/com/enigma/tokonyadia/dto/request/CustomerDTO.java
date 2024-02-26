package com.enigma.tokonyadia.dto.request;

import java.sql.Date;
import com.enigma.tokonyadia.utils.GenderEnum;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerDTO {

    @NotEmpty(message = "Customer name is required")
    private String name;

    @JsonFormat(pattern="yyyy-MM-dd")
    private Date birthDate;

    @NotEmpty(message = "Addess is required")
    private String address;

    @NotNull
    private GenderEnum gender;
    
    @NotEmpty(message = "Phone Number is required")
    private String phoneNumber;
}
