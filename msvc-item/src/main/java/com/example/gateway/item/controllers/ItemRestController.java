package com.example.gateway.item.controllers;

import com.example.gateway.commons.dtos.requests.ItemRequestDto;
import com.example.gateway.item.services.IItemService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/v1")
@Slf4j
public class ItemRestController {

    private final IItemService service;
    private final CircuitBreakerFactory circuitBreakerFactory;

    @Autowired
    private Environment env;

    public ItemRestController(IItemService service, CircuitBreakerFactory circuitBreakerFactory) {
        this.service = service;
        this.circuitBreakerFactory = circuitBreakerFactory;
    }

    @GetMapping("/fetch-config")
    public ResponseEntity<?> fetchConfig(@Value("${server.port}") String port) {
        Map<String, String> config = new HashMap<>();

        config.put("port", port);
        log.info(port);

        if(env.getActiveProfiles().length > 0 && env.getActiveProfiles()[0].equals("dev")) {
            log.info(env.getProperty("configuracion.texto"));
            config.put("env", env.getProperty("configuracion.texto"));

            if(env.containsProperty("configuracion.autor.name")) {
                config.put("author_name", env.getProperty("configuracion.autor.name"));
            }

            if(env.containsProperty("configuracion.autor.email")) {
                config.put("author_email", env.getProperty("configuracion.autor.email"));
            }
        }

        return ResponseEntity.ok(config);
    }

    @PostMapping("/create")
    @ResponseBody
    public ResponseEntity<?> create(@RequestBody @Valid ItemRequestDto request) {
        return ResponseEntity.ok(service.create(request));
    }

    @GetMapping("/find/{id}")
    @ResponseBody
    public ResponseEntity<?> create(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/find-all")
    @ResponseBody
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

}
