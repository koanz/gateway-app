package com.example.gateway.order.services;

import com.example.gateway.commons.dtos.ProductDto;
import com.example.gateway.commons.entities.Product;

public interface IProductService {
    public ProductDto findById(Long id);
    public Product findEntityById(Long id);
}
