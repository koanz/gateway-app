package com.example.gateway.commons.dtos.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequestDto {
    @NotNull(message = "Total is required.")
    @JsonProperty("total")
    private Double totalPrice;

    @NotNull(message = "User ID is required.")
    @JsonProperty("user_id")
    private Long userId;

    private String description;

    @NotNull(message = "The items are required.")
    private List<ItemRequestDto> items;
}
