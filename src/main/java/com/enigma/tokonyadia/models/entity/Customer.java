package com.enigma.tokonyadia.models.entity;

import java.sql.Date;
import java.util.List;

import com.enigma.tokonyadia.utils.GenderEnum;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    
    private String name;

    @JsonFormat(pattern="yyyy-MM-dd")
    @Column(name = "birth_date")
    private Date birthDate;

    private String address;

    private GenderEnum gender;
    
    @Column(name = "phone_number")
    private String phoneNumber;
    //sampai sini register

    @OneToMany(mappedBy = "customerId")
    @JsonBackReference
    private List<Purchase> purchases;

    @OneToOne
    private UserCredential userCredential;
}
