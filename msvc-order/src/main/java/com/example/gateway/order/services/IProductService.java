package com.example.gateway.order.services;

import com.example.gateway.commons.entities.Product;

public interface IProductService {
    public Product getById(Long id);

    public Product findById(Long id);
}
