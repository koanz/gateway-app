package com.example.gateway.producto.repositories;

import com.example.gateway.commons.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IProductoRepository extends JpaRepository<Product, Long> {
}
