package com.example.gateway.item.services.impl;

import com.example.gateway.commons.dtos.OrderDto;
import com.example.gateway.commons.entities.Order;
import com.example.gateway.commons.mappers.OrderMapper;
import com.example.gateway.item.exceptions.EntityNotFoundException;
import com.example.gateway.item.feign.IOrderFeignClient;
import com.example.gateway.item.repositories.IOrderRepository;
import com.example.gateway.item.services.IOrderService;
import com.example.gateway.item.services.IOrderWebClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;
import java.util.Optional;

@Service
public class OrderServiceImpl implements IOrderService {

    private final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    private IOrderFeignClient feignClient;

    @Autowired
    private IOrderRepository repository;

    @Autowired
    private IOrderWebClientService webClient;

    @Autowired
    private OrderMapper mapper;

    @Autowired
    private MessageSource messageSource;

    @Override
    @Transactional(readOnly = true)
    public OrderDto findById(Long id) {
        //OrderDto response = feignClient.findById(id);
        OrderDto response = webClient.findById(id).block();

        if(response == null) {
            logger.error("Item Not Found: " + id);
            throw new EntityNotFoundException(messageSource.getMessage("item.notfound", null, Locale.getDefault()) + " " + id);
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
