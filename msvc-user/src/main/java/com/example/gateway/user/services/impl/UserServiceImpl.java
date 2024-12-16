package com.example.gateway.user.services.impl;

import com.example.gateway.commons.dtos.requests.UserUpdateDto;
import com.example.gateway.commons.dtos.responses.ClientResponseDto;
import com.example.gateway.commons.dtos.MessageResponse;
import com.example.gateway.commons.dtos.requests.UserRequestDto;
import com.example.gateway.commons.dtos.responses.UserResponseDto;
import com.example.gateway.commons.entities.Client;
import com.example.gateway.commons.entities.Role;
import com.example.gateway.commons.entities.User;
import com.example.gateway.commons.mappers.ClientMapper;
import com.example.gateway.commons.mappers.UserMapper;
import com.example.gateway.commons.models.PaginationRequest;
import com.example.gateway.user.exceptions.CustomException;
import com.example.gateway.user.exceptions.EntityNotFoundException;
import com.example.gateway.user.repositories.IClientRepository;
import com.example.gateway.user.repositories.IUserRepository;
import com.example.gateway.user.services.IClientService;
import com.example.gateway.user.services.IUserRoleService;
import com.example.gateway.user.services.IUserService;
import jakarta.persistence.PersistenceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
public class UserServiceImpl implements IUserService {
    @Autowired
    private IUserRepository repository;

    @Autowired
    private IClientRepository clientRepository;

    @Autowired
    private UserMapper mapper;

    @Autowired
    private IClientService clientService;

    @Autowired
    private IUserRoleService userRoleService;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public MessageResponse create(UserRequestDto request) {
        ClientResponseDto clientResponse = clientService.findById(request.getClientId());
        List<Role> roles = userRoleService.findRolesById(request.getRolesId());

        if(clientResponse == null) {
            log.error("Client Not Found with id: " + request.getClientId());
            throw new EntityNotFoundException(messageSource.getMessage("client.notfound", null, Locale.getDefault()) + " " + request.getClientId());
        }

        ClientMapper clientMapper = new ClientMapper();
        Optional<Client> client = clientRepository.findById(request.getClientId());

        User user = mapper.dtoToEntity(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEnabled(request.getEnabled());
        user.setRoles(roles);
        user.setClient(client.get());

        try {
            user = repository.save(user);
        } catch(PersistenceException ex) {
            throw new PersistenceException(ex.getMessage());
        }

        return new MessageResponse(messageSource.getMessage("user.created", null, Locale.getDefault()));
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponseDto findById(Long id) {
        Optional<User> user = repository.findById(id);
        if(user.isEmpty()) {
            log.error("User Not Found: " + id);
            throw new EntityNotFoundException(messageSource.getMessage("user.notfound", null, Locale.getDefault()) + " " + id);
        }

        return mapper.entityToResponseDto(user.get());
    }

    @Override
    public User findByUsername(String username) {
        if(username == null) {
            throw new CustomException("Username is required.");
        }

        User user = repository.findByUsername(username.toUpperCase());

        if(user == null) {
            throw new EntityNotFoundException("Username not found with Username: " + username);
        }

        return user;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserResponseDto> findAll(PaginationRequest pageRequest) {
        Pageable pageable = PageRequest.of(pageRequest.getPageNumber(), pageRequest.getPageSize());
        Page<User> users = repository.findAll(pageable);

        if(Objects.isNull(users) || users.isEmpty()) {
            throw new EntityNotFoundException(messageSource.getMessage("user.notfound.list", null, Locale.getDefault()));
        }

        return users.map(client -> mapper.entityToResponseDto(client));
    }

    @Override
    @Transactional
    public MessageResponse update(Long id, UserUpdateDto request) {
        Optional<User> user = repository.findById(id);
        if(user.isEmpty()) {
            // throw not found exception
            throw new EntityNotFoundException(messageSource.getMessage("user.notfound", null, Locale.getDefault()));
        }

        User userRetrieve = user.get();
        if(Objects.nonNull(request.getUsername())) userRetrieve.setUsername(request.getUsername().toUpperCase());
        if(Objects.nonNull(request.getEmail())) userRetrieve.setEmail(request.getEmail());
        if(Objects.nonNull(request.getPassword())) userRetrieve.setPassword(request.getPassword());

        try {
            userRetrieve = repository.save(userRetrieve);
        } catch(PersistenceException ex) {
            throw new PersistenceException(ex.getMessage());
        }

        return new MessageResponse(messageSource.getMessage("user.updated", null, Locale.getDefault()));
    }

}
