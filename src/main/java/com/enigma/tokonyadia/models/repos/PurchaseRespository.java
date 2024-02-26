package com.enigma.tokonyadia.models.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.enigma.tokonyadia.models.entity.Purchase;

@Repository
public interface PurchaseRespository extends JpaRepository<Purchase, String> {
    
}
