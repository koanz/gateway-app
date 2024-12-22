package com.example.gateway.item.services.impl;

import com.example.gateway.commons.dtos.requests.ItemRequestDto;
import com.example.gateway.commons.dtos.responses.ItemResponseDto;
import com.example.gateway.commons.entities.Item;
import com.example.gateway.commons.entities.Product;
import com.example.gateway.commons.mappers.ItemMapper;
import com.example.gateway.item.exceptions.EntityNotFoundException;
import com.example.gateway.item.repositories.IItemRepository;
import com.example.gateway.item.repositories.IProductRepository;
import com.example.gateway.item.services.IItemService;
import jakarta.persistence.PersistenceException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
public class ItemServiceImpl implements IItemService {
    private final Logger logger = LoggerFactory.getLogger(ItemServiceImpl.class);
    @Autowired
    private IItemRepository repository;

    @Autowired
    private ItemMapper mapper;

    @Autowired
    private IProductRepository productRepository;

    @Autowired
    private MessageSource messageSource;

    @Override
    @Transactional
    public ItemResponseDto create(ItemRequestDto request) {
        logger.info("ItemServiceImpl.create(): {}", request);

        Optional<Product> product = productRepository.findById(request.getProductId());
        if(!product.isPresent()) {
            logger.error("Product Not Found: " + request.getProductId());
            throw new EntityNotFoundException("Cannot register an Item. Cause: The Product with ID: " + request.getProductId() + " doesn't exist.");
        }

        Item item = new Item();
        item.setQuantity(request.getQuantity());
        item.setProduct(product.get());

        try {
            item = repository.save(item);
        } catch(PersistenceException ex) {
            logger.error("Cannot register an Item: " + ex.getMessage());
            throw new PersistenceException(ex.getMessage());
        }

        return mapper.entityToDto(item);
    }

    @Override
    @Transactional(readOnly = true)
    public ItemResponseDto findById(Long id) {
        logger.info("ItemServiceImpl.findById(): {}", id);
        Optional<Item> item = repository.findById(id);

        if(item.isEmpty()) {
            logger.error("Item Not Found: " + id);
            throw new EntityNotFoundException(messageSource.getMessage("item.notfound", null, Locale.getDefault()) + " " + id);
        }

        return mapper.entityToDto(item.get());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ItemResponseDto> findAll() {
        logger.info("ItemServiceImpl.findAll().");
        List<Item> items = repository.findAll();

        if(items.isEmpty()) {
            logger.error("The Items list is empty.");
            throw new EntityNotFoundException(messageSource.getMessage("item.notfound.list", null, Locale.getDefault()));
        }

        return items.stream().map(mapper::entityToDto).toList();
    }

}
