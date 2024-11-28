package com.example.gateway.item.feign;

import com.example.gateway.commons.dtos.ProductDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "msvc-product")
public interface IProductFeignClient {

    @GetMapping("/v1/find/{id}")
    public ProductDto findById(@PathVariable Long id);
}
