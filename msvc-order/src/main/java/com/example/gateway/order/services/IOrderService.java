package com.example.gateway.order.services;

import com.example.gateway.commons.dtos.MessageResponse;
import com.example.gateway.commons.dtos.OrderDto;
import com.example.gateway.commons.dtos.requests.OrderRequestDto;

import java.util.List;

public interface IOrderService {
    public MessageResponse create(OrderRequestDto request);

    public OrderDto findById(Long id);

    public List<OrderDto> findAll();

}
