package com.example.gateway.order.services;

import com.example.gateway.commons.dtos.responses.UserResponseDto;
import com.example.gateway.commons.entities.User;

public interface IUserService {
    public UserResponseDto findById(Long id);
    public User findEntityById(Long id);

}
