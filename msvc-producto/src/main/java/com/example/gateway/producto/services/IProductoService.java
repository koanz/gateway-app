package com.example.gateway.producto.services;

import com.example.gateway.commons.dtos.MessageResponse;
import com.example.gateway.commons.dtos.ProductoDto;
import com.example.gateway.commons.entities.Product;

import java.util.List;

public interface IProductoService {
    public MessageResponse create(ProductoDto request);
    public ProductoDto findById(Long id);
    public Product getById(Long id);
    public List<ProductoDto> findAll();
    public MessageResponse update(Long id, ProductoDto request);
}
