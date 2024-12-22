package com.example.gateway.cliente.services;

import com.example.gateway.cliente.exceptions.EntityNotFoundException;
import com.example.gateway.cliente.repositories.IClientRepository;
import com.example.gateway.commons.dtos.requests.ClientRequestDto;
import com.example.gateway.commons.dtos.responses.ClientResponseDto;
import com.example.gateway.commons.dtos.MessageResponse;
import com.example.gateway.commons.entities.Client;
import com.example.gateway.commons.mappers.ClientMapper;

import com.example.gateway.commons.models.PaginationRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
//import java.util.concurrent.TimeUnit;

@Service
public class ClientServiceImpl implements IClientService {
    private final Logger logger = LoggerFactory.getLogger(ClientServiceImpl.class);

    @Autowired
    private IClientRepository repository;

    @Autowired
    private ClientMapper mapper;

    @Autowired
    private MessageSource messageSource;

    @Override
    @Transactional
    public MessageResponse create(ClientRequestDto request) {
        logger.info("ClientServiceImpl.create: {}", request);

        Client client = mapper.dtoToEntity(request);
        repository.save(client);
        logger.info("Client successfully saved: {}", client.getCompanyName());

        return new MessageResponse(messageSource.getMessage("client.created", null, Locale.getDefault()));
    }

    @Override
    @Transactional(readOnly = true)
    public ClientResponseDto findById(Long id) {
        logger.info("ClientServiceImpl.findById: {}", id);
        Optional<Client> client = repository.findById(id);

       /* if(id.equals(9L)) {
            throw new IllegalStateException(messageSource.getMessage("client.notfound", null, Locale.getDefault()) + " " + id);
        }

        try {
            if(id.equals(10L)) {
                TimeUnit.SECONDS.sleep(5L);
            }
        } catch(InterruptedException ex) {
            log.error("Error InterruptedException");
        }*/

        if(!client.isPresent()) {
            logger.error("Client Not Found: " + id);
            throw new EntityNotFoundException(messageSource.getMessage("client.notfound", null, Locale.getDefault()) + " " + id);
        }

        return mapper.entityToResponseDto(client.get());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ClientResponseDto> findAll(PaginationRequest pageRequest) {
        logger.info("ClientServiceImpl.findAll: {}", pageRequest);
        Pageable pageable = PageRequest.of(pageRequest.getPageNumber(), pageRequest.getPageSize());
        Page<Client> clients = repository.findAll(pageable);

        if(Objects.isNull(clients) || clients.isEmpty()) {
            logger.error("The list of Client is empty.");
            throw new EntityNotFoundException(messageSource.getMessage("client.notfound.list", null, Locale.getDefault()));
        }

        return clients.map(client -> mapper.entityToResponseDto(client));
    }

    @Override
    public MessageResponse update(Long id, ClientRequestDto request) {
        logger.info("ClientServiceImpl.update: {}", request);
        Optional<Client> client = repository.findById(id);

        if(client.isEmpty()) {
            logger.error("Client Not Found: " + id);
            throw new EntityNotFoundException(messageSource.getMessage("client.notfound", null, Locale.getDefault()) + " " + id);
        }

        Client registered = client.get();
        if(!registered.getCompanyName().equalsIgnoreCase(request.getCompanyName())) {
            registered.setCompanyName(request.getCompanyName().toUpperCase());
        }

        if(!registered.getCuit().equals(request.getCuit())) {
            registered.setCuit(request.getCuit());
        }

        if(!registered.getAddress().equalsIgnoreCase(request.getAddress())) {
            registered.setAddress(request.getAddress().toUpperCase());
        }

        repository.save(registered);
        logger.info("Client successfully updated: {}", registered.getCompanyName());


        return new MessageResponse(messageSource.getMessage("client.updated", null, Locale.getDefault()));
    }

}
