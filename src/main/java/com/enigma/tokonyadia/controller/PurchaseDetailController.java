package com.enigma.tokonyadia.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.enigma.tokonyadia.dto.request.PurchaseDetailDTO;
import com.enigma.tokonyadia.dto.response.ResponseDTO;
import com.enigma.tokonyadia.models.entity.Customer;
import com.enigma.tokonyadia.models.entity.PurchaseDetail;
import com.enigma.tokonyadia.services.ProductService;
import com.enigma.tokonyadia.services.PurchaseDetailService;
import com.enigma.tokonyadia.services.PurchaseService;
import com.enigma.tokonyadia.utils.MessageResultConstant;
import com.enigma.tokonyadia.utils.ApiUrlConstant;
import jakarta.validation.Valid;

@RestController
@RequestMapping(ApiUrlConstant.BASE_PATH_PURCHASE_DETAIL)
public class PurchaseDetailController {

    @Autowired
    private PurchaseDetailService purchaseDetailService;

    @Autowired
    private ProductService productService;

    @Autowired
    private PurchaseService purchaseService;

    @PostMapping
    public ResponseEntity<ResponseDTO<PurchaseDetail>> addPurchaseDetail(@RequestBody @Valid PurchaseDetailDTO purchaseDetailDTO, Errors errors) {

        ResponseDTO<PurchaseDetail> responseDTO = new ResponseDTO<>();

        if(errors.hasErrors()){
            for(ObjectError err : errors.getAllErrors()){
                responseDTO.getMessage().add(err.getDefaultMessage());
            }
            responseDTO.setStatus(HttpStatus.BAD_REQUEST.getReasonPhrase());
            responseDTO.setPayload(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDTO);
        }

        PurchaseDetail purchaseDetail = new PurchaseDetail();
        purchaseDetail.setQuantity(purchaseDetailDTO.getQuantity());
        purchaseDetail.setPriceSell(purchaseDetailDTO.getPriceSell());
        purchaseDetail.setProductId(productService.findProductById(purchaseDetailDTO.getProductId()));
        purchaseDetail.setPurchaseId(purchaseService.findPurchaseById(purchaseDetailDTO.getPurchaseId()));

        responseDTO.setStatus(HttpStatus.OK.getReasonPhrase());
        responseDTO.setPayload(purchaseDetailService.addPurchaseDetail(purchaseDetail));
        responseDTO.getMessage().add(MessageResultConstant.UPDATE_SUCCESS_CUSTOMER);

        return ResponseEntity.ok().body(responseDTO);
    }

    @GetMapping(ApiUrlConstant.PATH_ID)
    public ResponseEntity<?> findPurchaseDetailById(@PathVariable String id) {

        ResponseDTO<PurchaseDetail> responseDTO = new ResponseDTO<>();

        try {
            return ResponseEntity.ok().body(purchaseDetailService.findPurchaseDetailById(id));
        } catch (Exception e) {
            responseDTO.getMessage().add(MessageResultConstant.NOT_FOUND_CUSTOMER);      
            responseDTO.setStatus(HttpStatus.NOT_FOUND.getReasonPhrase());
            responseDTO.setPayload(null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDTO);
        }

    }
}
