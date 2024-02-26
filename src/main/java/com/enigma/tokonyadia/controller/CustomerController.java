package com.enigma.tokonyadia.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

import com.enigma.tokonyadia.dto.request.CustomerDTO;
import com.enigma.tokonyadia.dto.response.PageResponseWrapper;
import com.enigma.tokonyadia.dto.response.ResponseDTO;
import com.enigma.tokonyadia.models.entity.Customer;
import com.enigma.tokonyadia.services.CustomerService;
import com.enigma.tokonyadia.utils.ApiUrlConstant;
import com.enigma.tokonyadia.utils.MessageResultConstant;

import jakarta.validation.Valid;

@RestController
@RequestMapping(ApiUrlConstant.BASE_PATH_CUSTOMER)
public class CustomerController {

    @Autowired
    private CustomerService customerService;
    
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<ResponseDTO<Customer>> addCustomer(@RequestBody @Valid CustomerDTO customerDTO, Errors errors){
        ResponseDTO<Customer> responseDTO = new ResponseDTO<>();

        if(errors.hasErrors()){
            for(ObjectError err : errors.getAllErrors()){
                responseDTO.getMessage().add(err.getDefaultMessage());
            }
            responseDTO.setStatus(HttpStatus.BAD_REQUEST.getReasonPhrase());
            responseDTO.setPayload(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDTO);
        }

        Customer customer = new Customer();
        customer.setName(customerDTO.getName());
        customer.setBirthDate(customerDTO.getBirthDate());
        customer.setAddress(customerDTO.getAddress());
        customer.setGender(customerDTO.getGender());
        customer.setPhoneNumber(customerDTO.getPhoneNumber());

        responseDTO.setStatus(HttpStatus.CREATED.getReasonPhrase());
        responseDTO.setPayload(customerService.createCustomer(customer));
        responseDTO.getMessage().add(MessageResultConstant.CREATE_SUCCESS_CUSTOMER);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @PutMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<ResponseDTO<Customer>> updateCustomer(@RequestBody Customer customer, Errors errors){
        ResponseDTO<Customer> responseDTO = new ResponseDTO<>();

        if(errors.hasErrors()){
            for(ObjectError err : errors.getAllErrors()){
                responseDTO.getMessage().add(err.getDefaultMessage());
            }
            responseDTO.setStatus(HttpStatus.BAD_REQUEST.getReasonPhrase());
            responseDTO.setPayload(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDTO);
        }

        customerService.updateCustomer(customer);

        responseDTO.setStatus(HttpStatus.OK.getReasonPhrase());
        responseDTO.setPayload(customer);
        responseDTO.getMessage().add(MessageResultConstant.UPDATE_SUCCESS_CUSTOMER);

        return ResponseEntity.ok().body(responseDTO);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public List<Customer> getAllCustomer(){
        return customerService.getAllCustomer();
    }

    @DeleteMapping(ApiUrlConstant.PATH_ID)
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<String> deleteCustomerById (@PathVariable String id) {
        try {
            customerService.deleteCustomerByid(id);
            return ResponseEntity.ok().body(MessageResultConstant.DELETE_SUCCESS_CUSTOMER);
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping(ApiUrlConstant.SEARCH_KEY)
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public List<Customer> findCustomerByName(@RequestParam String name) {
            return customerService.findCustomerByName(name);
    }

    @GetMapping(ApiUrlConstant.PATH_ID)
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<?> findCustomerById(@PathVariable String id) {

        ResponseDTO<Customer> responseDTO = new ResponseDTO<>();

        try {
            return ResponseEntity.ok().body(customerService.findCustomerById(id));
        } catch (Exception e) {
            responseDTO.getMessage().add(MessageResultConstant.NOT_FOUND_CUSTOMER);      
            responseDTO.setStatus(HttpStatus.NOT_FOUND.getReasonPhrase());
            responseDTO.setPayload(null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDTO);
        }

    }

    @GetMapping(ApiUrlConstant.PATH_PAGE)
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public PageResponseWrapper<Customer> getCustomerPerPage(@RequestParam(required = false, defaultValue = "0") Integer page,@RequestParam(required = false) String name, @RequestParam(required = false, defaultValue = "5") Integer size, @RequestParam(defaultValue = "name") String sortBy, @RequestParam(defaultValue = "ASC") String direction) {
        Sort sort = Sort.by(Sort.Direction.fromString(direction), sortBy);
        PageResponseWrapper response = new PageResponseWrapper<>(customerService.getCustomerPerPage(name, PageRequest.of(page, size, sort))); 
        return response;
    }
}
