package com.enigma.tokonyadia.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

import com.enigma.tokonyadia.dto.ProductDTO;
import com.enigma.tokonyadia.dto.ResponseDTO;
import com.enigma.tokonyadia.models.entity.Product;
import com.enigma.tokonyadia.services.ProductService;

@RestController
@RequestMapping("/product")
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

            responseDTO.setStatus(false);
            responseDTO.setPayload(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDTO);
        }

        Product product = new Product();
        product.setName(productDTO.getName());
        product.setPrice(productDTO.getPrice());
        product.setStock(productDTO.getStock());

        responseDTO.setStatus(true);
        responseDTO.setPayload(productService.addProduct(product));
        responseDTO.getMessage().add("success add product");

        return ResponseEntity.ok().body(responseDTO);
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseDTO<Product>> updateProduct(@RequestBody @Valid Product product, Errors errors) {
        
        ResponseDTO<Product> responseDTO = new ResponseDTO<>();

        if(errors.hasErrors()) {
            for(ObjectError error : errors.getAllErrors()) {
                responseDTO.getMessage().add(error.getDefaultMessage());
            }

            responseDTO.setStatus(false);
            responseDTO.setPayload(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDTO);
        }

        productService.updateProduct(product);
        responseDTO.setStatus(true);
        responseDTO.setPayload(product);
        responseDTO.getMessage().add("success update product");

        return ResponseEntity.ok().body(responseDTO);
    }

    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProduct();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProductById (@PathVariable String id) {
        try {
            productService.deleteProductByid(id);
            return ResponseEntity.ok().body("success delete product");
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/searchKey")
    public List<Product> findProductByName(@RequestParam String value) {
            return productService.findProductByName(value);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO<Product>> findProductById(@PathVariable String id) {

        ResponseDTO<Product> responseDTO = new ResponseDTO<>();

        try {
            responseDTO.setStatus(true);
            responseDTO.setPayload(productService.findProductById(id));
            responseDTO.getMessage().add("Product found");
            return ResponseEntity.ok().body(responseDTO);
        
        } catch (Exception e) {
            responseDTO.getMessage().add("Product not found");      
            responseDTO.setStatus(false);
            responseDTO.setPayload(null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDTO);
        }

    }

    @GetMapping("/searchKey2")
    public List<Product> findProductByNameOrStock(@RequestParam(required = false) String name, @RequestParam(required = false) Integer stock) {
            return productService.findProductByNameOrStockOrPrice(name, stock);
    }


    @GetMapping("page")
    public Page<Product> getProductPerPage(@RequestParam Integer page, @RequestParam Integer size) {
        return productService.getProductPerPage(PageRequest.of(page, size));
        
    }

}
