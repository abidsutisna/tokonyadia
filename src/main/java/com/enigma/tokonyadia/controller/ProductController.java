package com.enigma.tokonyadia.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.enigma.tokonyadia.dto.request.ProductDTO;
import com.enigma.tokonyadia.dto.response.PageResponseWrapper;
import com.enigma.tokonyadia.dto.response.ResponseDTO;
import com.enigma.tokonyadia.models.entity.Product;
import com.enigma.tokonyadia.services.ProductService;
import com.enigma.tokonyadia.utils.ApiUrlConstant;
import com.enigma.tokonyadia.utils.MessageResultConstant;

@RestController
@RequestMapping(ApiUrlConstant.BASE_PATH_PRODUCT)
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping
    public ResponseEntity<ResponseDTO<Product>> addProduct(@RequestBody @Valid ProductDTO productDTO, Errors errors) {
        
        ResponseDTO<Product> responseDTO = new ResponseDTO<>();

        if(errors.hasErrors()) {
            for(ObjectError error : errors.getAllErrors()) {
                responseDTO.getMessage().add(error.getDefaultMessage());
            }

            responseDTO.setStatus(HttpStatus.BAD_REQUEST.getReasonPhrase());
            responseDTO.setPayload(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDTO);
        }

        Product product = new Product();
        product.setName(productDTO.getName());
        product.setPrice(productDTO.getPrice());
        product.setStock(productDTO.getStock());

        responseDTO.setStatus(HttpStatus.CREATED.getReasonPhrase());
        responseDTO.setPayload(productService.addProduct(product));
        responseDTO.getMessage().add(MessageResultConstant.CREATE_SUCCESS_PRODUCT);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @PutMapping(ApiUrlConstant.PATH_UPDATE)
    public ResponseEntity<ResponseDTO<Product>> updateProduct(@RequestBody @Valid Product product, Errors errors) {
        
        ResponseDTO<Product> responseDTO = new ResponseDTO<>();

        if(errors.hasErrors()) {
            for(ObjectError error : errors.getAllErrors()) {
                responseDTO.getMessage().add(error.getDefaultMessage());
            }

            responseDTO.setStatus(HttpStatus.BAD_REQUEST.getReasonPhrase());
            responseDTO.setPayload(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDTO);
        }

        productService.updateProduct(product);
        responseDTO.setStatus(HttpStatus.OK.getReasonPhrase());
        responseDTO.setPayload(product);
        responseDTO.getMessage().add(MessageResultConstant.UPDATE_SUCCESS_PRODUCT);

        return ResponseEntity.ok().body(responseDTO);
    }

    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProduct();
    }

    @DeleteMapping(ApiUrlConstant.PATH_ID)
    public ResponseEntity<String> deleteProductById (@PathVariable String id) {
        try {
            productService.deleteProductByid(id);
            return ResponseEntity.ok().body(MessageResultConstant.DELETE_SUCCESS_PRODUCT);
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping(ApiUrlConstant.SEARCH_KEY)
    public List<Product> findProductByName(@RequestParam String value) {
            return productService.findProductByName(value);
    }
    
    @GetMapping(ApiUrlConstant.PATH_ID)
    public ResponseEntity<?> findProductById(@PathVariable String id) {

        ResponseDTO<Product> responseDTO = new ResponseDTO<>();
        
        try {
            return ResponseEntity.ok().body(productService.findProductById(id));
        } catch (Exception e) {
            responseDTO.getMessage().add(MessageResultConstant.NOT_FOUND_PRODUCT);      
            responseDTO.setStatus(HttpStatus.NOT_FOUND.getReasonPhrase());
            responseDTO.setPayload(null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDTO);
        }

    }

    @GetMapping(ApiUrlConstant.SEARCH_KEY2)
    public List<Product> findProductByNameOrStock(@RequestParam(required = false) String name, @RequestParam(required = false) Integer stock) {
            return productService.findProductByNameOrStockOrPrice(name, stock);
    }

    @GetMapping(ApiUrlConstant.PATH_PAGE)
    public PageResponseWrapper<Product> getProductPerPage(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Integer stock,
            @RequestParam(required = false) Integer price,
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "5") Integer size, 
            @RequestParam(defaultValue = "name") String sortBy, 
            @RequestParam(defaultValue = "ASC") String direction) 
            {

        Sort sort = Sort.by(Sort.Direction.fromString(direction), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Product> pageProduct = productService.getProductPerPage(name, stock, price ,pageable);
        PageResponseWrapper<Product> response = new PageResponseWrapper<>(pageProduct);
        return response;
   
    }

}
