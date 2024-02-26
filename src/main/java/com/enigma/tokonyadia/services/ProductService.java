package com.enigma.tokonyadia.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.enigma.tokonyadia.models.entity.Product;

public interface ProductService {
    public Product addProduct(Product product);
    Product findProductById(String id);
    List<Product> findProductByName(String name);
    List<Product> getAllProduct();
    public void updateProduct(Product product);
    void deleteProductByid(String id);
    List<Product> findProductByNameOrStockOrPrice(String name, Integer stock);
    Page<Product> getProductPerPage(String name, Integer stock, Integer price, Pageable pageable);
}
