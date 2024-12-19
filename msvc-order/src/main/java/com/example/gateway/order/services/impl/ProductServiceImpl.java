package com.example.gateway.order.services.impl;

import com.example.gateway.commons.entities.Product;
import com.example.gateway.order.exceptions.EntityNotFoundException;
import com.example.gateway.order.feign.IProductFeignClient;
import com.example.gateway.order.repositories.IProductRepository;
import com.example.gateway.order.services.IProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.Optional;

@Service
public class ProductServiceImpl implements IProductService {
    private final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Autowired
    private IProductFeignClient feignClient;

    @Autowired
    private IProductRepository repository;

    @Autowired
    private MessageSource messageSource;

    @Override
    public Product getById(Long id) {
        return feignClient.getById(id);
    }

    @Override
    public Product findById(Long id) {
        Optional<Product> product = repository.findById(id);

        if(product.isEmpty()) {
            logger.error("Product Not Found: " + id);
            throw new EntityNotFoundException("Product Not Found with id " + id);
        }

        return product.get();
    }
}
