package com.example.gateway.user.controllers;

import com.example.gateway.commons.dtos.requests.UserRequestDto;
import com.example.gateway.commons.dtos.requests.UserUpdateDto;
import com.example.gateway.commons.entities.User;
import com.example.gateway.commons.models.PaginationRequest;
import com.example.gateway.user.services.IUserService;
import com.example.gateway.user.services.IUserRoleService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RefreshScope
@RestController
@RequestMapping("/v1/user")
public class UserRestController {
    private final Logger logger = LoggerFactory.getLogger(UserRestController.class);

    private IUserService service;

    //private final IUserServiceCb serviceCb;

    @Autowired
    private IUserRoleService userRoleService;

    //private final CircuitBreakerFactory cBreakerFactory;

    @Autowired
    private Environment env;

    public UserRestController(/*@Qualifier("userServiceWebClient") IUserServiceCb serviceCb,
                              CircuitBreakerFactory cBreakerFactory,*/ IUserService service) {
        //this.cBreakerFactory = cBreakerFactory;
        //this.serviceCb = serviceCb;
        this.service = service;
    }

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
    public ResponseEntity<?> create(@RequestBody UserRequestDto request) {
        logger.info("Create User: {}", request);
        return ResponseEntity.ok(service.create(request));
    }

    @GetMapping("/find/{id}")
    @ResponseBody
    public ResponseEntity<?> getById(@PathVariable Long id) {
        logger.info("UserRestController.find: {}", id);
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/find-all")
    @ResponseBody
    public ResponseEntity<?> getAll(PaginationRequest pageRequest, HttpServletRequest request) {
        logger.info("UserRestController.find-all: {}", request);

        return ResponseEntity.ok(service.findAll(pageRequest));
    }

    @PutMapping("/update/{id}")
    @ResponseBody
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody @Valid UserUpdateDto request) {
        logger.info("UserRestController.update: {}", id);
        logger.info("update User: {}", request);
        return ResponseEntity.ok(service.update(id, request));
    }

    @GetMapping("/username/{username}")
    @ResponseBody
    public ResponseEntity<User> findByUsername(@PathVariable String username) {
        logger.info("UserRestController.username: {}", username);
        return ResponseEntity.ok(service.findByUsername(username));
    }

    /*@GetMapping("/find-cb/{id}")
    @ResponseBody
    public ResponseEntity<?> getByIdCb(@PathVariable Long id) {
        UserMapper mapper = new UserMapper();
        Role role = userRoleService.findByName(UserRole.ROLE_USER.name());

        List<Role> roles = new ArrayList<>();
        roles.add(role);

        UserResponseDto response = cBreakerFactory.create("user").run(() -> serviceCb.findByIdCb(id), e -> {
            System.out.println(e.getMessage());
            log.error(e.getMessage());

            Client client = new Client();
            client.setId(1L);
            client.setCreatedDate(new Date());
            client.setCompanyName("Circuit Breaker S.A.");
            client.setAddress("Apple St. 475");
            client.setCuit("20-16789789-0");

            return mapper.entityToResponseDto(new User(1L, "cb_user", "cb_user@email.com", "123456", true, roles, client, new Date(), new Date()));
        });

        if(response == null) {
            throw new EntityNotFoundException("User Not Found.");
        }

        return ResponseEntity.ok(response);
    }*/

}
