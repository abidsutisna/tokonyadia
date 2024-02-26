package com.enigma.tokonyadia.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.enigma.tokonyadia.models.entity.Customer;
import com.enigma.tokonyadia.models.repos.CustomerRepository;
import com.enigma.tokonyadia.services.CustomerService;
import com.enigma.tokonyadia.specification.SpesificationCustomer;

@Service
public class CustomerServiceImpl implements CustomerService{

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public Customer createCustomer(Customer customer) {
        return this.customerRepository.save(customer); 
    }

    @Override
    public Customer findCustomerById(String id) {
       return this.customerRepository.findById(id).get();
    }

    @Override
    public List<Customer> findCustomerByName(String name) {
       return this.customerRepository.findCustomerByName("%"+name+"%");
    }

    @Override
    public List<Customer> getAllCustomer() {
        return this.customerRepository.findAll();
    }

    @Override
    public void updateCustomer(Customer customer) {
        this.customerRepository.findById(customer.getId()).get();
        this.customerRepository.save(customer);
    }

    @Override
    public void deleteCustomerByid(String id) {
        this.customerRepository.deleteById(id);
    }

    @Override
    public Page<Customer> getCustomerPerPage(String name,Pageable pageable) {
        Specification<Customer> spec = SpesificationCustomer.hasName(null);
        if(name != null){
            spec = SpesificationCustomer.hasName(name);
        }
        return this.customerRepository.findAll(spec,pageable);
    }
    
}
