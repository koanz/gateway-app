package com.example.gateway.order.feign;

import com.example.gateway.commons.dtos.responses.UserResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "msvc-user")
public interface IUserFeignClient {

    @GetMapping("/v1/user/find/{id}")
    public UserResponseDto findById(@PathVariable Long id);

}
