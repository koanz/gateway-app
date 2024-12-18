package com.example.gateway.item.clients;

import com.example.gateway.commons.dtos.OrderDto;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class OrderWebClient {
    private final WebClient webClient;

    public OrderWebClient() {
        this.webClient = WebClient.create();
    }

    public Mono<OrderDto> findById(Long id) {
        return webClient.get()
                .uri("/api/orders/v1/find/{id}", id)
                .retrieve()
                .bodyToMono(OrderDto.class);
    }
}
