package com.example.gateway.item.services;

import com.example.gateway.commons.dtos.OrderDto;
import reactor.core.publisher.Mono;

public interface IOrderWebClientService {
    public Mono<OrderDto> findById(Long id);
}
