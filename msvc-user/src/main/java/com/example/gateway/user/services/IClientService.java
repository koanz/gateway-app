package com.example.gateway.user.services;

import com.example.gateway.commons.dtos.responses.ClientResponseDto;

public interface IClientService {
    public ClientResponseDto findById(Long id);

}
