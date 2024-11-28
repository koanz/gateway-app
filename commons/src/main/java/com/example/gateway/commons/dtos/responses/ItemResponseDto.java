package com.example.gateway.commons.dtos.responses;

import com.example.gateway.commons.dtos.OrderDto;
import com.example.gateway.commons.dtos.ProductDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ItemResponseDto {
    private Long id;
    private int quantity;
    private ProductDto product;
    private OrderDto order;
}
