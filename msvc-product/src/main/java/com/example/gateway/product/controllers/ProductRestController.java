package com.example.gateway.product.controllers;

import com.example.gateway.commons.dtos.ProductDto;
import com.example.gateway.product.services.IProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/user")
public class ProductRestController {
    private final Logger logger = LoggerFactory.getLogger(ProductRestController.class);

    @Autowired
    private IProductService service;

    @PostMapping("/create")
    @ResponseBody
    public ResponseEntity<?> create(@RequestBody @Validated ProductDto request) {
        logger.info("ProductRestController.create: {}", request);
        return ResponseEntity.ok(service.create(request));
    }

    @GetMapping("/find/{id}")
    @ResponseBody
    public ResponseEntity<?> findById(@PathVariable Long id) {
        logger.info("ProductoRestController.find: {}", id);
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/get/{id}")
    @ResponseBody
    public ResponseEntity<?> getById(@PathVariable Long id) {
        logger.info("ProductoRestController.get: {}", id);
        return ResponseEntity.ok(service.getById(id));
    }

    @GetMapping("/find-all")
    @ResponseBody
    public ResponseEntity<?> getAll() {
        // extraer el cliente id del token
        logger.info("ProductoRestController.find-all");
        return ResponseEntity.ok(service.findAll());
    }

    @PutMapping("/update/{id}")
    @ResponseBody
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody ProductDto request) {
        // extraer el cliente id del token
        logger.info("ProductoRestController.update: {} {}", id, request);
        return ResponseEntity.ok(service.update(id, request));
    }
}
