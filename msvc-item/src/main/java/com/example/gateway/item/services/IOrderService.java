package com.example.gateway.item.services;

import com.example.gateway.commons.dtos.OrderDto;
import com.example.gateway.commons.entities.Order;

public interface IOrderService {
    public OrderDto findById(Long id);
    public Order findEntityById(Long id);

}
