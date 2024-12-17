package com.example.gateway.user.controllers;

import com.example.gateway.commons.dtos.requests.UserRequestDto;
import com.example.gateway.commons.models.PaginationRequest;
import com.example.gateway.user.services.IUserService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/v1/admin")
public class UserAdminRestController {
    private final Logger logger = LoggerFactory.getLogger(UserAdminRestController.class);
    @Autowired
    private IUserService service;

    @Autowired
    private Environment env;

    @GetMapping("/fetch-config")
    public ResponseEntity<?> fetchConfig(@Value("${server.port}") String port) {
        Map<String, String> config = new HashMap<>();

        config.put("port", port);
        logger.info(port);

        if(env.getActiveProfiles().length > 0 && env.getActiveProfiles()[0].equals("dev")) {
            logger.info(env.getProperty("configuracion.texto"));
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
    public ResponseEntity<?> create(@RequestBody @Valid UserRequestDto request) {
        logger.info("UserAdminRestController.create: {}", request);
        return ResponseEntity.ok(service.create(request));
    }

    @GetMapping("/find/{id}")
    @ResponseBody
    public ResponseEntity<?> findById(@PathVariable Long id) {
        logger.info("UserAdminRestController.find: {}", id);
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/find-all")
    @ResponseBody
    public ResponseEntity<?> findAll(PaginationRequest request) {
        logger.info("UserAdminRestController.find-all: {}", request);
        return ResponseEntity.ok(service.findAll(request));
    }

}
