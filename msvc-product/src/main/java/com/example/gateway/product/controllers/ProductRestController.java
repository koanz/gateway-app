package com.example.gateway.product.controllers;

import com.example.gateway.commons.dtos.ProductDto;
import com.example.gateway.product.services.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1")
public class ProductRestController {

    @Autowired
    private IProductService service;

    @PostMapping("/create")
    @ResponseBody
    public ResponseEntity<?> create(@RequestBody @Validated ProductDto request) {
        return ResponseEntity.ok(service.create(request));
    }

    @GetMapping("/find/{id}")
    @ResponseBody
    public ResponseEntity<?> create(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }
}
