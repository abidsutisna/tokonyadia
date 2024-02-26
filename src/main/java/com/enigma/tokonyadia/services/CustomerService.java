package com.enigma.tokonyadia.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.enigma.tokonyadia.models.entity.Customer;

public interface CustomerService {
    public Customer createCustomer(Customer customer);
    Customer findCustomerById(String id);
    List<Customer> findCustomerByName(String name);
    List<Customer> getAllCustomer();
    void updateCustomer(Customer customer);
    void deleteCustomerByid(String id);
    Page<Customer> getCustomerPerPage(String name,Pageable pageable);
}
