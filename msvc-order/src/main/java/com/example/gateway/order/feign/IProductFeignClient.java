package com.example.gateway.order.feign;

import com.example.gateway.commons.entities.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "msvc-product")
public interface IProductFeignClient {

    @GetMapping("/v1/get/{id}")
    public Product getById(@PathVariable Long id);
}
