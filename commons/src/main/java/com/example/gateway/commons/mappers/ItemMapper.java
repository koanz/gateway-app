package com.example.gateway.commons.mappers;

import com.example.gateway.commons.dtos.responses.ItemResponseDto;
import com.example.gateway.commons.entities.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class ItemMapper {

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private OrderMapper orderMapper;

    public ItemResponseDto entityToDto(Item entity) {
        ItemResponseDto response = new ItemResponseDto();

        response.setId(entity.getId());
        response.setQuantity(entity.getQuantity());
        response.setProduct(productMapper.entityToResponseDto(entity.getProduct()));
        if(Objects.nonNull(entity.getOrder())) response.setOrder(orderMapper.entityWithoutItemsToResponse(entity.getOrder()));

        return response;
    }

}
