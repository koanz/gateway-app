package com.example.gateway.commons.dtos;

import com.example.gateway.commons.dtos.responses.ItemResponseDto;
import com.example.gateway.commons.dtos.responses.UserResponseDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderDto {
    private Long id;
    private UserResponseDto user;
    private Double total;
    private List<ItemResponseDto> items;
}
