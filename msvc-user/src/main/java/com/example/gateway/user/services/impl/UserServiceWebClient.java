package com.example.gateway.user.services.impl;

import com.example.gateway.commons.dtos.responses.ClientResponseDto;
import com.example.gateway.commons.dtos.responses.UserResponseDto;
import com.example.gateway.commons.entities.User;
import com.example.gateway.user.repositories.IUserRepository;
import com.example.gateway.user.services.IUserServiceCb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.Optional;

@Service
public class UserServiceWebClient implements IUserServiceCb {
    @Autowired
    private WebClient.Builder webClientBuilder;

    @Autowired
    private IUserRepository repository;

    @Qualifier
    private CircuitBreakerFactory cBreakerFactory;

    public UserServiceWebClient(CircuitBreakerFactory cBreakerFactory) {
        this.cBreakerFactory = cBreakerFactory;
    }

    @Override
    public UserResponseDto findByIdCb(Long id) {
        Optional<User> user = repository.findById(id);

        UserResponseDto userResponse = webClientBuilder.build().get().uri("/v1/admin/find/{id}", user.get().getClient().getId())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(ClientResponseDto.class)
                .map(client -> new UserResponseDto(user.get().getId(), user.get().getUsername(), user.get().getEmail(),
                        user.get().getEnabled(), client, user.get().getRoles().get(0).getName()))
                .block();
        // usar circuit breaker
        return userResponse;
    }
}
