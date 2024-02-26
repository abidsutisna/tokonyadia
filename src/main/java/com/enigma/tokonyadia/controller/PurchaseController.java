package com.enigma.tokonyadia.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.enigma.tokonyadia.dto.request.PurchaseDTO;
import com.enigma.tokonyadia.dto.response.ResponseDTO;
import com.enigma.tokonyadia.models.entity.Customer;
import com.enigma.tokonyadia.models.entity.Product;
import com.enigma.tokonyadia.models.entity.Purchase;
import com.enigma.tokonyadia.models.entity.PurchaseDetail;
import com.enigma.tokonyadia.models.repos.ProductRepository;
import com.enigma.tokonyadia.services.CustomerService;
import com.enigma.tokonyadia.services.PurchaseService;
import com.enigma.tokonyadia.utils.ApiUrlConstant;
import com.enigma.tokonyadia.utils.MessageResultConstant;

import jakarta.validation.Valid;

@RestController
@RequestMapping(ApiUrlConstant.BASE_PATH_PURCHASE)
public class PurchaseController {

    @Autowired
    private PurchaseService purchaseService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private ProductRepository productRepository;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN', 'CUSTOMER')")
    public ResponseEntity<ResponseDTO<Purchase>> addPurchase(@RequestBody @Valid PurchaseDTO purchaseDTO, Errors errors) {

        ResponseDTO<Purchase> responseDTO = new ResponseDTO<>();

        if(errors.hasErrors()){
            for(ObjectError err : errors.getAllErrors()){
                responseDTO.getMessage().add(err.getDefaultMessage());
            }
            responseDTO.setStatus(HttpStatus.BAD_REQUEST.getReasonPhrase());
            responseDTO.setPayload(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDTO);
        }

        for(PurchaseDetail purchaseDetail : purchaseDTO.getPurchaseDetailsId()){
            if(productRepository.findById(purchaseDetail.getProductId().getId()).get().getStock() < purchaseDetail.getQuantity()){
                responseDTO.setStatus(HttpStatus.BAD_REQUEST.getReasonPhrase());
                responseDTO.setPayload(null);
                responseDTO.getMessage().add(MessageResultConstant.STOCK_NOT_ENOUGH);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDTO);
            }
        }

        Purchase purchase = new Purchase();
        purchase.setCustomerId(customerService.findCustomerById(purchaseDTO.getCustomerId()));
        purchase.setPurchaseDetails(purchaseDTO.getPurchaseDetailsId());


        for(PurchaseDetail purchaseDetail : purchaseDTO.getPurchaseDetailsId()){
            Product product = productRepository.findById(purchaseDetail.getProductId().getId()).get();
            product.setStock(product.getStock() - purchaseDetail.getQuantity());
        }

        responseDTO.setStatus(HttpStatus.OK.getReasonPhrase());
        responseDTO.setPayload(purchaseService.saveTransaction(purchase));
        responseDTO.getMessage().add(MessageResultConstant.CREATE_SUCCES_PURCHASE);

        return ResponseEntity.ok().body(responseDTO);
    }

    @GetMapping(ApiUrlConstant.PATH_ID)
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<?> findPurchaseById(@PathVariable String id) {

        ResponseDTO<Purchase> responseDTO = new ResponseDTO<>();

        try {
            return ResponseEntity.ok().body(purchaseService.findPurchaseById(id));
        } catch (Exception e) {
            responseDTO.getMessage().add(MessageResultConstant.NOT_FOUND_CUSTOMER);      
            responseDTO.setStatus(HttpStatus.NOT_FOUND.getReasonPhrase());
            responseDTO.setPayload(null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDTO);
        }

    }
}
