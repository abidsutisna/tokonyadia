package com.enigma.tokonyadia.models.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "t_purchase_detail")
public class PurchaseDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    @JoinColumn(name = "purchase_id", referencedColumnName = "id")
    @JsonBackReference
    private Purchase purchaseId;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product productId;

    private Integer priceSell;

    private Integer quantity;
}
