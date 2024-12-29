package com.example.gateway.commons.dtos.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductReqDto {
    private String name;

    private Double price;

    @JsonProperty("client_id")
    private Long clientId;
}
