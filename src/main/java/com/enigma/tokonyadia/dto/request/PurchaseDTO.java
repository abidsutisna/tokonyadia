package com.enigma.tokonyadia.dto.request;

import java.util.ArrayList;
import java.util.List;

import com.enigma.tokonyadia.models.entity.PurchaseDetail;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PurchaseDTO {
    private String customerId;

    private List<PurchaseDetail> purchaseDetailsId;
}
