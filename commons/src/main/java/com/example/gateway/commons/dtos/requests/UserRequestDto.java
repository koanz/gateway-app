package com.example.gateway.commons.dtos.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDto {
    @NotEmpty(message = "Username is required.")
    private String username;

    @NotEmpty(message = "Email is required.")
    private String email;

    @NotEmpty(message = "Password is required.")
    private String password;

    @NotNull(message = "Enabled is required.")
    private Boolean enabled;

    @JsonProperty("client_id")
    private Long clientId;

    @JsonProperty("roles_id")
    @NotEmpty(message = "At least one role ID is required.")
    @Size(min = 1, message = "At least one role ID is required.")
    private List<Long> rolesId;
}
