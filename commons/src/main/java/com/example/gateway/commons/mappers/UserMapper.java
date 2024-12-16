package com.example.gateway.commons.mappers;

import com.example.gateway.commons.dtos.responses.ClientResponseDto;
import com.example.gateway.commons.dtos.requests.UserRequestDto;
import com.example.gateway.commons.dtos.responses.UserResponseDto;
import com.example.gateway.commons.entities.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class UserMapper {

    public UserResponseDto entityToResponseDto(User entity) {
        ClientMapper clientMapper = new ClientMapper();
        ClientResponseDto responseClientResponse= clientMapper.entityToResponseDto(entity.getClient());

        UserResponseDto response = new UserResponseDto();

        if(Objects.nonNull(entity.getId())) response.setId(entity.getId());
        response.setUsername(entity.getUsername());
        response.setEmail(entity.getEmail());
        response.setClient(responseClientResponse);
        response.setRole(entity.getRoles().get(0).getName());

        return response;
    }

    public User dtoToEntity(UserRequestDto request) {
        User entity = new User();

        entity.setUsername(request.getUsername().toUpperCase());
        entity.setEmail(request.getEmail());

        return entity;
    }

    public List<UserResponseDto> toDtoList(List<User> users) {
        return users.stream().map(this::entityToResponseDto).collect(Collectors.toList());
    }

}
