package com.example.gateway.product.services;

import com.example.gateway.commons.dtos.MessageResponse;
import com.example.gateway.commons.dtos.ProductDto;
import com.example.gateway.commons.entities.Product;

import java.util.List;

public interface IProductService {
    public MessageResponse create(ProductDto request);
    public ProductDto findById(Long id);

    public Product getById(Long id);

    public List<ProductDto> findAll();
}
