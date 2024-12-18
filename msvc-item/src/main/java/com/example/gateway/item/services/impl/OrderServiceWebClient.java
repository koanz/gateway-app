package com.example.gateway.item.services.impl;

import com.example.gateway.commons.dtos.OrderDto;
import com.example.gateway.commons.entities.Order;
import com.example.gateway.item.services.IOrderService;
import com.example.gateway.item.services.IOrderWebClientService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class OrderServiceWebClient implements IOrderWebClientService {

    private final WebClient client;

    public OrderServiceWebClient(WebClient client) {
        this.client = client;
    }

    @Override
    public Mono<OrderDto> findById(Long id) {
        return this.client
                .get()
                .uri("/api/orders/v1/find/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(OrderDto.class);
    }

}
