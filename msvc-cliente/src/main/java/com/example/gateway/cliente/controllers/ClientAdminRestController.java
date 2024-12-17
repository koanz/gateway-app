package com.example.gateway.cliente.controllers;

import com.example.gateway.cliente.services.IClientService;
import com.example.gateway.commons.dtos.requests.ClientRequestDto;
import com.example.gateway.commons.models.PaginationRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/v1/admin")
public class ClientAdminRestController {
    private final Logger logger = LoggerFactory.getLogger(ClientAdminRestController.class);
    private final IClientService service;

    @Value("${configuracion.texto}")
    private String env;

    public ClientAdminRestController(IClientService service) {
        this.service = service;
    }

    @GetMapping("/fetch-config")
    public ResponseEntity<?> fetchConfig(@Value("${server.port}") String port) {
        Map<String, String> config = new HashMap<>();

        config.put("port", port);
        config.put("env", env);
        logger.info(port);

        return ResponseEntity.ok(config);
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody @Validated ClientRequestDto request) {
        logger.info("ClienteAdminRestController.create: {}", request);
        return ResponseEntity.ok(service.create(request));
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        logger.info("ClienteAdminRestControlller.find: {}", id);
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/find-all")
    public ResponseEntity<?> findAll(PaginationRequest pageRequest, @RequestParam(name = "name", required = false) String name) {
        logger.info("ClienteAdminRestController.findAll()");
        logger.info("Request Parameter: {}", name);
        return ResponseEntity.ok(service.findAll(pageRequest));
    }

}
