package com.enigma.tokonyadia.models.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.enigma.tokonyadia.models.entity.Product;

import jakarta.websocket.server.PathParam;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> ,   JpaSpecificationExecutor<Product>{
    @Query("SELECT p FROM Product p WHERE p.name LIKE :produkname")
    public List<Product> findProdukByName(@PathParam("produkname") String produkname);

    public List<Product> findProductByStock(Integer stock);

}
