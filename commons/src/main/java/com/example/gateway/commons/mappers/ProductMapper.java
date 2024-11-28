package com.example.gateway.commons.mappers;

import com.example.gateway.commons.dtos.ProductDto;
import com.example.gateway.commons.entities.Product;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class ProductMapper {
    public ProductDto entityToResponseDto(Product entity) {
        ProductDto response = new ProductDto();

        response.setId(entity.getId());
        response.setName(entity.getName());
        response.setPrice(entity.getPrice());

        return response;
    }

}
