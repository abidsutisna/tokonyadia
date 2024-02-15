package com.enigma.tokonyadia.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.enigma.tokonyadia.models.entity.Product;
import com.enigma.tokonyadia.models.repos.ProductRepository;

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
}
