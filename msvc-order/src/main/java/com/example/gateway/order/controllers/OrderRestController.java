package com.example.gateway.order.controllers;

import com.example.gateway.commons.dtos.requests.OrderRequestDto;
import com.example.gateway.order.services.IOrderService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1")
public class OrderRestController {
    private final Logger logger = LoggerFactory.getLogger(OrderRestController.class);
    @Autowired
    private IOrderService service;

    @PostMapping("/create")
    @ResponseBody
    public ResponseEntity<?> create(@RequestBody @Valid OrderRequestDto request) {
        logger.info("OrderRestController.create: {}", request);
        return ResponseEntity.ok(service.create(request));
    }

    @GetMapping("/find/{id}")
    @ResponseBody
    public ResponseEntity<?> findById(@PathVariable Long id) {
        logger.info("OrderRestController.find: {}", id);
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/find-all")
    @ResponseBody
    public ResponseEntity<?> findAll() {
        logger.info("OrderRestController.find-all");
        return ResponseEntity.ok(service.findAll());
    }

    @DeleteMapping("/delete/{id}")
    @ResponseBody
    public ResponseEntity<?> delete(@PathVariable Long id) {
        logger.info("OrderRestController.delete: {}", id);
        return ResponseEntity.ok(service.delete(id));
    }

}
