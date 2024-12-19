package com.example.gateway.product.services.impl;

import com.example.gateway.commons.dtos.MessageResponse;
import com.example.gateway.commons.dtos.ProductDto;
import com.example.gateway.commons.entities.Product;
import com.example.gateway.commons.mappers.ProductMapper;
import com.example.gateway.product.exceptions.EntityNotFoundException;
import com.example.gateway.product.respositories.IProductRepository;
import com.example.gateway.product.services.IProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ProductServiceImpl implements IProductService {

    @Autowired
    private IProductRepository repository;

    @Autowired
    private ProductMapper mapper;

    @Autowired
    private MessageSource messageSource;

    @Override
    @Transactional
    public MessageResponse create(ProductDto request) {
        Product product = new Product();

        product.setName(request.getName());
        product.setPrice(request.getPrice());

        try {
            repository.save(product);
        } catch(Exception ex) {
            log.error("Error: " + ex.getMessage());
        }

        return new MessageResponse(messageSource.getMessage("product.created", null, Locale.getDefault()));
    }

    @Override
    @Transactional(readOnly = true)
    public ProductDto findById(Long id) {
        Optional<Product> product = repository.findById(id);

        if(product.isEmpty()) {
            log.error("Product Not Found: " + id);
            throw new EntityNotFoundException(messageSource.getMessage("product.notfound", null, Locale.getDefault()) + " " + id);
        }

        return mapper.entityToResponseDto(product.get());
    }

    @Override
    @Transactional(readOnly = true)
    public Product getById(Long id) {
        Optional<Product> product = repository.findById(id);

        if(product.isEmpty()) {
            log.error("Product Not Found: " + id);
            throw new EntityNotFoundException(messageSource.getMessage("product.notfound", null, Locale.getDefault()) + " " + id);
        }

        return product.get();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductDto> findAll() {
        List<Product> products = repository.findAll();

        if(products.isEmpty()) {
            throw new EntityNotFoundException(messageSource.getMessage("product.notfound.list", null, Locale.getDefault()));
        }

        return products.stream().map(mapper::entityToResponseDto).collect(Collectors.toList());
    }

}
