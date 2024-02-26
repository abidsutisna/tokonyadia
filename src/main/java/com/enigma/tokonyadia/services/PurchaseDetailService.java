package com.enigma.tokonyadia.services;

import com.enigma.tokonyadia.models.entity.PurchaseDetail;

public interface PurchaseDetailService {
    public PurchaseDetail addPurchaseDetail(PurchaseDetail purchaseDetail);
    public PurchaseDetail findPurchaseDetailById(String id);
}
