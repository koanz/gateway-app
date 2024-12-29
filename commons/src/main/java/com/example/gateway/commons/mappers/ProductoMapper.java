package com.example.gateway.commons.mappers;

import com.example.gateway.commons.dtos.ProductoDto;
import com.example.gateway.commons.entities.Product;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class ProductoMapper {
    public ProductoDto entityToResponseDto(Product entity) {
        ProductoDto response = new ProductoDto();

        response.setId(entity.getId());
        response.setName(entity.getName());
        response.setPrice(entity.getPrice());
        if(Objects.nonNull(entity.getDescription())) response.setDescription(entity.getDescription());
        response.setClientId(entity.getClient().getId());

        return response;
    }
}
