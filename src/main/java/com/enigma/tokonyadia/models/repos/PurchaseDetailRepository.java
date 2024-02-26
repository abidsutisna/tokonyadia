package com.enigma.tokonyadia.models.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.enigma.tokonyadia.models.entity.PurchaseDetail;

public interface PurchaseDetailRepository extends JpaRepository<PurchaseDetail, String> {
    
}
