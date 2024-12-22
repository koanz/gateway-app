package com.example.gateway.cliente.services;

import com.example.gateway.commons.dtos.requests.ClientRequestDto;
import com.example.gateway.commons.dtos.responses.ClientResponseDto;
import com.example.gateway.commons.dtos.MessageResponse;
import com.example.gateway.commons.models.PaginationRequest;
import org.springframework.data.domain.Page;

public interface IClientService {
    public MessageResponse create(ClientRequestDto request);
    public ClientResponseDto findById(Long id);
    public Page<ClientResponseDto> findAll(PaginationRequest pageRequest);
    public MessageResponse update(Long id, ClientRequestDto request);

}
