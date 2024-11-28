package com.example.gateway.item.services;

import com.example.gateway.commons.dtos.requests.ItemRequestDto;
import com.example.gateway.commons.dtos.responses.ItemResponseDto;
import com.example.gateway.commons.dtos.MessageResponse;

import java.util.List;

public interface IItemService {
    public ItemResponseDto create(ItemRequestDto request);
    public ItemResponseDto findById(Long id);

    public List<ItemResponseDto> findAll();
}
