package com.example.gateway.item.services;

import com.example.gateway.commons.dtos.ProductDto;
import com.example.gateway.commons.entities.Product;

public interface IProductService {
    ProductDto findById(Long id);
    Product findEntityById(Long id);

}
