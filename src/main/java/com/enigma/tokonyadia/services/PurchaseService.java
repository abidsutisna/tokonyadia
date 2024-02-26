package com.enigma.tokonyadia.services;

import com.enigma.tokonyadia.models.entity.Purchase;

public interface PurchaseService {

    public Purchase addPurchase(Purchase purchase);
    public Purchase findPurchaseById(String id);
    public Purchase saveTransaction(Purchase purchase);
}
