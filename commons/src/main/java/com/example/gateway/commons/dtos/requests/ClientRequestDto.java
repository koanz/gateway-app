package com.example.gateway.commons.dtos.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClientRequestDto {
    @NotEmpty(message = "Company name is required.")
    @JsonProperty("company_name")
    private String companyName;

    @NotEmpty(message = "CUIT is required")
    private String cuit;

    @Size(max = 120, message = "Address must not exceed 120 characters.")
    private String address;
}
