package com.enigma.tokonyadia.models.entity;

import java.sql.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
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
@Table(name = "t_purchase")
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private Date purchaseDate;

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Customer customerId;

    @OneToMany(mappedBy = "purchaseId")
    private List<PurchaseDetail> purchaseDetails;

    @PrePersist
    public void onPrePersist(){
        this.purchaseDate = Date.valueOf(java.time.LocalDate.now());
    }
}
