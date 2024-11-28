package com.example.gateway.user.feign;

import com.example.gateway.commons.dtos.responses.ClientResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "msvc-cliente")
public interface IClientFeign {
    @GetMapping("/v1/find/{id}")
    public ClientResponseDto findById(@PathVariable Long id);
}
