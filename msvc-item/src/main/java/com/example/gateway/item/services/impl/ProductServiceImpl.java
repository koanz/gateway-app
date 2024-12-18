package com.example.gateway.item.services.impl;

import com.example.gateway.commons.dtos.ProductDto;
import com.example.gateway.commons.entities.Product;
import com.example.gateway.item.feign.IProductFeignClient;
import com.example.gateway.item.repositories.IProductRepository;
import com.example.gateway.item.services.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductServiceImpl implements IProductService {

    @Autowired
    private IProductFeignClient feignClient;

    @Autowired
    private IProductRepository repository;

    @Override
    public ProductDto findById(Long id) {
        ProductDto response = feignClient.findById(id);

        if(response == null) {
            // throw new exception
        }

        return response;
    }

    @Override
    public Product findEntityById(Long id) {
        Optional<Product> product = repository.findById(id);

        if(product.isEmpty()) {
            // throw new exception
        }

        return product.get();
    }

}
