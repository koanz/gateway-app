package com.example.gateway.producto.services.impl;

import com.example.gateway.commons.dtos.MessageResponse;
import com.example.gateway.commons.dtos.ProductoDto;
import com.example.gateway.commons.entities.Client;
import com.example.gateway.commons.entities.Product;
import com.example.gateway.commons.mappers.ProductoMapper;
import com.example.gateway.producto.exceptions.EntityNotFoundException;
import com.example.gateway.producto.repositories.IClientRepository;
import com.example.gateway.producto.repositories.IProductoRepository;
import com.example.gateway.producto.services.IProductoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductoServiceImpl implements IProductoService {

    private final Logger logger = LoggerFactory.getLogger(ProductoServiceImpl.class);

    @Autowired
    private IProductoRepository repository;

    @Autowired
    private ProductoMapper mapper;

    @Autowired
    private IClientRepository clientRepository;

    @Autowired
    private MessageSource messageSource;

    @Override
    @Transactional
    public MessageResponse create(ProductoDto request) {
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
    public ProductoDto findById(Long id) {
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
    public List<ProductoDto> findAll() {
        List<Product> products = repository.findAll();

        if(products.isEmpty()) {
            throw new EntityNotFoundException(messageSource.getMessage("product.notfound.list", null, Locale.getDefault()));
        }

        return products.stream().map(mapper::entityToResponseDto).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public MessageResponse update(Long id, ProductoDto request) {
        Optional<Product> registered = repository.findById(id);

        if(registered.isEmpty()) {
            logger.error("Product Not Found: " + id);
            throw new EntityNotFoundException(messageSource.getMessage("product.notfound", null, Locale.getDefault()) + " " + id);
        }

        Product product = registered.get();
        if(Objects.nonNull(request.getName())) {
            if(!request.getName().equalsIgnoreCase(product.getName())) {
                product.setName(request.getName().toUpperCase());
            }
        }

        if(Objects.nonNull(request.getPrice())) {
            if(!request.getPrice().equals(product.getPrice())) {
                product.setPrice(request.getPrice());
            }
        }

        repository.save(product);

        return new MessageResponse(messageSource.getMessage("product.updated", null, Locale.getDefault()));
    }
}
