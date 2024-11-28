package com.example.gateway.order.controllers;

import com.example.gateway.commons.dtos.OrderDto;
import com.example.gateway.commons.dtos.requests.OrderRequestDto;
import com.example.gateway.order.services.IOrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1")
public class OrderRestController {

    @Autowired
    private IOrderService service;

    @PostMapping("/create")
    @ResponseBody
    public ResponseEntity<?> create(@RequestBody @Valid OrderRequestDto request) {
        return ResponseEntity.ok(service.create(request));
    }

    @GetMapping("/find/{id}")
    @ResponseBody
    public ResponseEntity<?> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/find-all")
    @ResponseBody
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

}
