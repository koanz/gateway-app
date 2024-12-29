package com.example.gateway.commons.dtos.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ItemRequestDto {
    @NotNull(message = "Quantity is required.")
    @Min(value = 1, message = "The minimum value at least has to be 1")
    private int quantity;

    @JsonProperty("product_id")
    @NotNull(message = "Product ID is required.")
    private Long productId;

    @JsonProperty("order_id")
    private Long orderId;
}
