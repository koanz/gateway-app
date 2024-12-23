package com.example.gateway.product.services.impl;

import com.example.gateway.commons.dtos.MessageResponse;
import com.example.gateway.commons.dtos.ProductDto;
import com.example.gateway.commons.entities.Client;
import com.example.gateway.commons.entities.Product;
import com.example.gateway.commons.mappers.ProductMapper;
import com.example.gateway.product.exceptions.EntityNotFoundException;
import com.example.gateway.product.respositories.IClientRepository;
import com.example.gateway.product.respositories.IProductRepository;
import com.example.gateway.product.services.IProductService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements IProductService {

    private final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Autowired
    private IProductRepository repository;

    @Autowired
    private ProductMapper mapper;

    @Autowired
    private IClientRepository clientRepository;

    @Autowired
    private MessageSource messageSource;

    @Override
    @Transactional
    public MessageResponse create(ProductDto request) {
        Optional<Client> client = clientRepository.findById(request.getClientId());

        if(client.isEmpty()) {
            logger.error("Client Not Found: " + request.getClientId());
            throw new EntityNotFoundException("Cannot create this Product. Cause: Client with ID " + request.getClientId() + " doesn't exist.");
        }

        Product product = new Product();

        product.setName(request.getName());
        product.setPrice(request.getPrice());
        product.setClient(client.get());

        try {
            repository.save(product);
        } catch(Exception ex) {
            logger.error("Error: " + ex.getMessage());
        }

        return new MessageResponse(messageSource.getMessage("product.created", null, Locale.getDefault()));
    }

    @Override
    @Transactional(readOnly = true)
    public ProductDto findById(Long id) {
        Optional<Product> product = repository.findById(id);

        if(product.isEmpty()) {
            logger.error("Product Not Found: " + id);
            throw new EntityNotFoundException(messageSource.getMessage("product.notfound", null, Locale.getDefault()) + " " + id);
        }

        return mapper.entityToResponseDto(product.get());
    }

    @Override
    @Transactional(readOnly = true)
    public Product getById(Long id) {
        Optional<Product> product = repository.findById(id);

        if(product.isEmpty()) {
            logger.error("Product Not Found: " + id);
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

    @Override
    public MessageResponse update(Long id, ProductDto request) {
        Optional<Product> registered = repository.findById(id);

        if(registered.isEmpty()) {
            logger.error("Product Not Found: " + id);
            throw new EntityNotFoundException(messageSource.getMessage("product.notfound", null, Locale.getDefault()) + " " + id);
        }

        Product product = registered.get();
        if(!request.getName().equalsIgnoreCase(product.getName())) {
            product.setName(request.getName().toUpperCase());
        }

        if(!request.getPrice().equals(product.getPrice())) {
            product.setPrice(request.getPrice());
        }

        repository.save(product);

        return new MessageResponse(messageSource.getMessage("product.updated", null, Locale.getDefault()));
    }

}
