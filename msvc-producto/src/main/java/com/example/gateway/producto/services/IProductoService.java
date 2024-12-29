package com.example.gateway.producto.services;

import com.example.gateway.commons.dtos.MessageResponse;
import com.example.gateway.commons.dtos.ProductDto;
import com.example.gateway.commons.dtos.requests.ProductRequestDto;
import com.example.gateway.commons.entities.Product;

import java.util.List;

public interface IProductoService {
    public MessageResponse create(ProductRequestDto request);
    public ProductDto findById(Long id);
    public Product getById(Long id);
    public List<ProductDto> findAll();
    public MessageResponse update(Long id, ProductRequestDto request);
}
