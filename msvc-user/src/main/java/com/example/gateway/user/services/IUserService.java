package com.example.gateway.user.services;

import com.example.gateway.commons.dtos.MessageResponse;
import com.example.gateway.commons.dtos.requests.UserRequestDto;
import com.example.gateway.commons.dtos.requests.UserUpdateDto;
import com.example.gateway.commons.dtos.responses.UserResponseDto;
import com.example.gateway.commons.entities.User;
import com.example.gateway.commons.models.PaginationRequest;
import org.springframework.data.domain.Page;

public interface IUserService {
    public MessageResponse create(UserRequestDto request);
    public UserResponseDto findById(Long id);
    public User findByUsername(String username);


    public Page<UserResponseDto> findAll(PaginationRequest pageRequest);

    public MessageResponse update(Long id, UserUpdateDto request);
}
