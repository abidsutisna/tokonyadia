package com.enigma.tokonyadia.specification;

import org.springframework.data.jpa.domain.Specification;

import com.enigma.tokonyadia.models.entity.Customer;

public class SpesificationCustomer {
    public static Specification<Customer>  hasName(String name){
        return (root, query, cb) -> {
            return cb.like(root.get("name"), "%" + name + "%");
        };
    }
}
