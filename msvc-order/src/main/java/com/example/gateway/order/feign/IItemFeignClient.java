package com.example.gateway.order.feign;

import com.example.gateway.commons.dtos.requests.ItemRequestDto;
import com.example.gateway.commons.dtos.responses.ItemResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "msvc-item")
public interface IItemFeignClient {

    @PostMapping("/v1/create")
    public ItemResponseDto create(@RequestBody ItemRequestDto request);
}
