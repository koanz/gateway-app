package com.example.gateway.order.services.impl;

import com.example.gateway.commons.dtos.requests.ItemRequestDto;
import com.example.gateway.commons.dtos.responses.ItemResponseDto;
import com.example.gateway.commons.entities.Item;
import com.example.gateway.order.feign.IItemFeignClient;
import com.example.gateway.order.repositories.IItemRepository;
import com.example.gateway.order.services.IItemService;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ItemServiceImpl implements IItemService {

    @Autowired
    private IItemFeignClient feignClient;

    @Autowired
    private IItemRepository repository;

    @Override
    @Transactional
    public ItemResponseDto create(ItemRequestDto request) {
        ItemResponseDto response = null;

        try {
            response = feignClient.create(request);
        } catch(FeignException.FeignClientException ex) {
            log.error("Error al intentar crear un Item: " + ex.getMessage());
        }

        return response;
    }

    @Override
    public ItemResponseDto findById(Long id) {
        return feignClient.findById(id);
    }

    @Override
    public Item findEntityById(Long id) {
        Optional<Item> item = repository.findById(id);

        if(item.isEmpty()) {
            // throw new exception
        }

        return item.get();
    }

    @Override
    public List<Item> getItemsEntity(List<ItemResponseDto> itemsResponse) {
        List<Item> items = new ArrayList<>();
        items = itemsResponse.stream().map(itemResponseDto -> {
            return this.findEntityById(itemResponseDto.getId());
        }).toList();

        if(items.isEmpty()) {
            // throw new exception
        }

        return items;
    }

}
