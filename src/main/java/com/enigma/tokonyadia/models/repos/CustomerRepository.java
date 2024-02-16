package com.enigma.tokonyadia.models.repos;

import org.springframework.stereotype.Repository;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import com.enigma.tokonyadia.models.entity.Customer;

import jakarta.websocket.server.PathParam;


@Repository
public interface CustomerRepository extends JpaRepository<Customer, String>,  JpaSpecificationExecutor<Customer>{
    @Query("SELECT c FROM Customer c WHERE c.name LIKE :name")
    public List<Customer> findCustomerByName(@PathParam("name") String name);
}
