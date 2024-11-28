package com.example.gateway.order.services.impl;

import com.example.gateway.commons.dtos.ProductDto;
import com.example.gateway.commons.entities.Product;
import com.example.gateway.order.feign.IProductFeignClient;
import com.example.gateway.order.repositories.IProductRepository;
import com.example.gateway.order.services.IProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class ProductServiceImpl implements IProductService {

    @Autowired
    private IProductFeignClient feignClient;

    @Autowired
    private IProductRepository repository;

    @Override
    public ProductDto findById(Long id) {
        return feignClient.findById(id);
    }

    @Override
    public Product findEntityById(Long id) {
        Optional<Product> product = repository.findById(id);

        if(product.isEmpty()) {
            // throw new exception
            throw new IllegalArgumentException("Invalid product ID");
        }

        return product.get();
    }
}
