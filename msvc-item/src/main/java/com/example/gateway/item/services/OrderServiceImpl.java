package com.example.gateway.item.services;

import com.example.gateway.commons.dtos.OrderDto;
import com.example.gateway.commons.entities.Order;
import com.example.gateway.commons.mappers.OrderMapper;
import com.example.gateway.item.feign.IOrderFeignClient;
import com.example.gateway.item.repositories.IOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrderServiceImpl implements IOrderService {

    @Autowired
    private IOrderFeignClient feignClient;

    @Autowired
    private IOrderRepository repository;

    @Autowired
    private OrderMapper mapper;

    @Override
    public OrderDto findById(Long id) {
        OrderDto response = feignClient.findById(id);

        if(response == null) {
            // throw new exception
        }

        return response;
    }

    @Override
    public Order findEntityById(Long id) {
        Optional<Order> order = repository.findById(id);

        if(order.isEmpty()) {
            // throw new exception
        }

        return order.get();
    }

}
