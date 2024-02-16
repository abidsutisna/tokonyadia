package com.enigma.tokonyadia.specification;

import org.springframework.data.jpa.domain.Specification;

import com.enigma.tokonyadia.models.entity.Product;

public class SpesificationProduct {
    public static Specification<Product>  hasName(String name){
        return (root, query, cb) -> {
            return cb.like(root.get("name"),"%" + name + "%");
        };
    }

    public static Specification<Product>  hasStock(Integer stock){
        return (root, query, cb) -> {
            return cb.equal(root.get("stock"),stock);
        };
    }

    public static Specification<Product>  hasPrice(Integer price){
        return (root, query, cb) -> {
            return cb.equal(root.get("price"), price);
        };
    }
}
