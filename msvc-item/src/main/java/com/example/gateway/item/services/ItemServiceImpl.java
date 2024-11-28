package com.example.gateway.item.services;

import com.example.gateway.commons.dtos.*;
import com.example.gateway.commons.dtos.requests.ItemRequestDto;
import com.example.gateway.commons.dtos.responses.ItemResponseDto;
import com.example.gateway.commons.entities.Item;
import com.example.gateway.commons.entities.Product;
import com.example.gateway.commons.mappers.ItemMapper;
import com.example.gateway.item.exceptions.EntityNotFoundException;
import com.example.gateway.item.repositories.IItemRepository;
import jakarta.persistence.PersistenceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
@Slf4j
public class ItemServiceImpl implements IItemService {
    @Autowired
    private IItemRepository repository;

    @Autowired
    private ItemMapper mapper;

    @Autowired
    private IOrderService orderService;

    @Autowired
    private IProductService productService;

    @Autowired
    private MessageSource messageSource;

    @Override
    @Transactional
    public ItemResponseDto create(ItemRequestDto request) {
        ProductDto productDto = productService.findById(request.getProductId());

        Product product = productService.findEntityById(productDto.getId());

        Item item = new Item();
        item.setQuantity(request.getQuantity());
        item.setProduct(product);

        try {
            item = repository.save(item);
        } catch(PersistenceException ex) {
            log.error("Error al intentar registrar el Item: " + ex.getMessage());
            throw new PersistenceException(ex.getMessage());
        }

        return mapper.entityToDto(item);
    }

    @Override
    @Transactional(readOnly = true)
    public ItemResponseDto findById(Long id) {
        Optional<Item> item = repository.findById(id);

        if(item.isEmpty()) {
            log.error("Item Not Found: " + id);
            throw new EntityNotFoundException(messageSource.getMessage("item.notfound", null, Locale.getDefault()) + " " + id);
        }

        return mapper.entityToDto(item.get());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ItemResponseDto> findAll() {
        List<Item> items = repository.findAll();

        if(items.isEmpty()) {
            throw new EntityNotFoundException(messageSource.getMessage("item.notfound.list", null, Locale.getDefault()));
        }

        return items.stream().map(mapper::entityToDto).toList();
    }

}
