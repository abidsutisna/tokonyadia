package com.enigma.tokonyadia.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.enigma.tokonyadia.models.entity.PurchaseDetail;
import com.enigma.tokonyadia.models.repos.PurchaseDetailRepository;
import com.enigma.tokonyadia.services.PurchaseDetailService;

@Service
public class PurchaseDetailServiceImpl implements PurchaseDetailService{

    @Autowired
    private PurchaseDetailRepository purchaseDetailRepository;

    @Override
    public PurchaseDetail addPurchaseDetail(PurchaseDetail purchaseDetail) {
       return this.purchaseDetailRepository.save(purchaseDetail);
    }

    @Override
    public PurchaseDetail findPurchaseDetailById(String id) {
        return this.purchaseDetailRepository.findById(id).get();
    }
    
}
