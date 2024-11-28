package com.example.gateway.cliente.controllers;

import com.example.gateway.cliente.services.IClientService;
import com.example.gateway.commons.dtos.requests.ClientRequestDto;
import com.example.gateway.commons.models.PaginationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/v1/admin")
public class ClientAdminRestController {
    @Autowired
    private IClientService service;

    @Value("${configuracion.texto}")
    private String env;

    @GetMapping("/fetch-config")
    public ResponseEntity<?> fetchConfig(@Value("${server.port}") String port) {
        Map<String, String> config = new HashMap<>();

        config.put("port", port);
        config.put("env", env);

        return ResponseEntity.ok(config);
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody @Validated ClientRequestDto request) {
        return ResponseEntity.ok(service.create(request));
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/find-all")
    public ResponseEntity<?> findAll(PaginationRequest pageRequest, @RequestParam(name = "name", required = false) String name) {
        System.out.println("RequestParam: " + name);
        return ResponseEntity.ok(service.findAll(pageRequest));
    }

}
