package com.example.gateway.user.services;

import com.example.gateway.commons.dtos.responses.UserResponseDto;

public interface IUserServiceCb {
    public UserResponseDto findByIdCb(Long id);
}
