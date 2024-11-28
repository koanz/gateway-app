package com.example.gateway.commons.mappers;

import com.example.gateway.commons.dtos.*;
import com.example.gateway.commons.dtos.responses.ClientResponseDto;
import com.example.gateway.commons.dtos.responses.ItemResponseDto;
import com.example.gateway.commons.dtos.responses.UserResponseDto;
import com.example.gateway.commons.entities.*;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class OrderMapper {

    public OrderDto entityToResponse(Order entity) {
        OrderDto response = new OrderDto();

        response.setId(entity.getId());
        response.setUser(this.toUserDto(entity.getUser()));
        response.setTotal(entity.getTotal());

        response.setItems(entity.getItems().stream().map(this::toItemDto).toList());

        return response;
    }

    public OrderDto entityWithoutItemsToResponse(Order entity) {
        OrderDto response = new OrderDto();

        response.setId(entity.getId());
        response.setUser(this.toUserDto(entity.getUser()));
        response.setTotal(entity.getTotal());

        return response;
    }

    public Order toOrderEntity(OrderDto dto) {
        Order entity = new Order();

        entity.setId(dto.getId());

        return entity;
    }

    public ItemResponseDto toItemDto(Item entity) {
        ItemResponseDto response = new ItemResponseDto();

        response.setId(entity.getId());
        response.setQuantity(entity.getQuantity());
        response.setProduct(this.toProductDto(entity.getProduct()));

        return response;
    }

    public ProductDto toProductDto(Product entity) {
        ProductDto response = new ProductDto();

        response.setId(entity.getId());
        response.setName(entity.getName());
        response.setPrice(entity.getPrice());

        return response;
    }

    public UserResponseDto toUserDto(User entity) {
        ClientResponseDto responseClientResponse= this.toClientDto(entity.getClient());

        UserResponseDto response = new UserResponseDto();

        if(Objects.nonNull(entity.getId())) response.setId(entity.getId());
        response.setUsername(entity.getUsername());
        response.setEmail(entity.getEmail());
        response.setClient(responseClientResponse);

        return response;
    }

    public ClientResponseDto toClientDto(Client entity) {
        ClientResponseDto response = new ClientResponseDto();

        response.setId(entity.getId());
        response.setCompanyName(entity.getCompanyName());
        if(Objects.nonNull(entity.getCuit())) response.setCuit(entity.getCuit());
        if(Objects.nonNull(entity.getAddress())) response.setAddress(entity.getAddress());

        return response;
    }

}
