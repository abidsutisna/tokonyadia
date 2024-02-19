package com.enigma.tokonyadia.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.enigma.tokonyadia.models.entity.Purchase;
import com.enigma.tokonyadia.models.entity.PurchaseDetail;
import com.enigma.tokonyadia.models.repos.PurchaseDetailRepository;
import com.enigma.tokonyadia.models.repos.PurchaseRespository;
import com.enigma.tokonyadia.services.PurchaseDetailService;
import com.enigma.tokonyadia.services.PurchaseService;

import jakarta.transaction.Transactional;

@Service
public class PurchaseServiceImpl implements PurchaseService{

    @Autowired
    private PurchaseRespository purchaseRespository;

    @Autowired
    private PurchaseDetailService purchaseDetailService;

    @Override
    public Purchase addPurchase(Purchase purchase) {
        return this.purchaseRespository.save(purchase);
    }

    @Override
    public Purchase findPurchaseById(String id) {
       return this.purchaseRespository.findById(id).get();
    }

    @Override
    @Transactional
    public Purchase saveTransaction(Purchase purchase) {
        this.purchaseRespository.save(purchase);
        for(PurchaseDetail pd : purchase.getPurchaseDetails()) {
            pd.setPurchaseId(findPurchaseById(purchase.getId()));
            this.purchaseDetailService.addPurchaseDetail(pd);
        }

        return purchase;
    }
    
}
