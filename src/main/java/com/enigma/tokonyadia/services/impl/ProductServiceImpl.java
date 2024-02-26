package com.enigma.tokonyadia.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.enigma.tokonyadia.dto.request.ProductDTO;
import com.enigma.tokonyadia.models.entity.Product;
import com.enigma.tokonyadia.models.repos.ProductRepository;
import com.enigma.tokonyadia.services.ProductService;
import com.enigma.tokonyadia.specification.SpesificationProduct;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Product addProduct(Product product) {
       return this.productRepository.save(product);
    }

    @Override
    public Product findProductById(String id) {
        return this.productRepository.findById(id).get();
    }

    @Override
    public List<Product> findProductByName(String name) {
        return this.productRepository.findProdukByName("%"+name+"%");
    }

    @Override
    public List<Product> getAllProduct() {
        return this.productRepository.findAll();
    }

    @Override
    public void updateProduct(Product product) {
        this.productRepository.findById(product.getId()).get();
        this.productRepository.save(product);
    }

    @Override
    public void deleteProductByid(String id) {
        this.productRepository.deleteById(id);
    }

    @Override
    public List<Product> findProductByNameOrStockOrPrice(String name, Integer stock) {
        if(name == null ) {
            return this.productRepository.findProductByStock(stock);
            
        } else {
            return this.productRepository.findAll();
        }

    }

    @Override
    public Page<Product> getProductPerPage(String name, Integer stock, Integer price, Pageable pageable) {

        Specification<Product> spec = Specification.where(null);

        if(name != null && !name.isEmpty()) {
            spec = spec.and(SpesificationProduct.hasName(name));
        }

        if(stock != null) {
            spec = spec.and(SpesificationProduct.hasStock(stock));
        }
        
        if(price != null){
            spec = spec.and(SpesificationProduct.hasPrice(price));
        }

        return this.productRepository.findAll(spec, pageable);
    }
}
