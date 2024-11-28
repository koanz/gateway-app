package com.example.gateway.order.validations;

import com.example.gateway.commons.dtos.requests.ItemRequestDto;
import com.example.gateway.commons.entities.Item;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.DoubleStream;

@Component
public class ValidationOrder {

    public final Boolean isTotalValid(Double totalPrice, List<Item> items) {
        Double total = items.stream().mapToDouble(i -> {
            return i.getQuantity() * i.getProduct().getPrice();
        }).sum();

        return totalPrice.equals(total);
    }
}
