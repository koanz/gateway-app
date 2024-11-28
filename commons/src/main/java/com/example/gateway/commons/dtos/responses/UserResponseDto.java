package com.example.gateway.commons.dtos.responses;

import com.example.gateway.commons.dtos.responses.ClientResponseDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResponseDto {
    private Long id;
    private String username;
    private String email;
    private Boolean enabled;
    private ClientResponseDto client;
    private String role;
}