package com.enigma.tokonyadia.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PurchaseDetailDTO {

    private String purchaseId;

    private String productId;

    private Integer priceSell;

    private Integer quantity;
}
