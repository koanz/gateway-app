package com.example.gateway.order.services;

import com.example.gateway.commons.dtos.requests.ItemRequestDto;
import com.example.gateway.commons.dtos.responses.ItemResponseDto;
import com.example.gateway.commons.entities.Item;

import java.util.List;

public interface IItemService {
    public ItemResponseDto create(ItemRequestDto request);

    public ItemResponseDto findById(Long id);

    public Item findEntityById(Long id);

    public List<Item> getItemsEntity(List<ItemResponseDto> itemsResponse);
}
