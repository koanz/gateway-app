package com.example.gateway.commons.mappers;

import com.example.gateway.commons.dtos.requests.ClientRequestDto;
import com.example.gateway.commons.dtos.responses.ClientResponseDto;
import com.example.gateway.commons.entities.Client;
import org.springframework.stereotype.Component;
import org.springframework.data.domain.Page;


import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class ClientMapper {
    public ClientResponseDto entityToResponseDto(Client entity) {
        ClientResponseDto response = new ClientResponseDto();

        response.setId(entity.getId());
        response.setCompanyName(entity.getCompanyName());
        if(Objects.nonNull(entity.getCuit())) response.setCuit(entity.getCuit());
        if(Objects.nonNull(entity.getAddress())) response.setAddress(entity.getAddress());

        return response;
    }

    public Client dtoToEntity(ClientRequestDto request) {
        Client entity = new Client();

        entity.setCompanyName(request.getCompanyName().toUpperCase());
        if(Objects.nonNull(request.getCuit())) entity.setCuit(request.getCuit());
        if(Objects.nonNull(request.getAddress())) entity.setAddress(request.getAddress().toUpperCase());

        return entity;
    }

    public Client dtoToEntityCreate(ClientResponseDto dto) {
        Client entity = new Client();

        entity.setId(dto.getId());
        entity.setCompanyName(dto.getCompanyName().toUpperCase());
        if(Objects.nonNull(dto.getCuit())) entity.setCuit(dto.getCuit());
        if(Objects.nonNull(dto.getAddress())) entity.setAddress(dto.getAddress().toUpperCase());

        return entity;
    }

    public List<ClientResponseDto> toDtoList(Page<Client> clients) {
        return clients.stream().map(this::entityToResponseDto).collect(Collectors.toList());
    }
}
