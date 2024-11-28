package com.example.gateway.item.feign;

import com.example.gateway.commons.dtos.OrderDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "msvc-order")
public interface IOrderFeignClient {
    @GetMapping("/v1/find/{id}")
    public OrderDto findById(@PathVariable Long id);
}
