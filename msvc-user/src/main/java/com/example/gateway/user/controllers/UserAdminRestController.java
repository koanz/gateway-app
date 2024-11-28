package com.example.gateway.user.controllers;

import com.example.gateway.commons.dtos.requests.UserRequestDto;
import com.example.gateway.commons.models.PaginationRequest;
import com.example.gateway.user.services.IUserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/admin")
public class UserAdminRestController {
    @Autowired
    private IUserService service;

    @PostMapping("/create")
    @ResponseBody
    public ResponseEntity<?> create(@RequestBody @Valid UserRequestDto request) {
        return ResponseEntity.ok(service.create(request));
    }

    @GetMapping("/find/{id}")
    @ResponseBody
    public ResponseEntity<?> findById(Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/find-all")
    @ResponseBody
    public ResponseEntity<?> findAll(PaginationRequest request) {
        return ResponseEntity.ok(service.findAll(request));
    }

}
