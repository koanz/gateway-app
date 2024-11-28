package com.example.gateway.user.services.impl;

import com.example.gateway.commons.dtos.responses.ClientResponseDto;
import com.example.gateway.user.exceptions.CustomException;
import com.example.gateway.user.feign.IClientFeign;
import com.example.gateway.user.services.IClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientServiceImpl implements IClientService {
    @Autowired
    private IClientFeign feignClient;

    @Override
    public ClientResponseDto findById(Long id) {
        try {
            return feignClient.findById(id);
        } catch (CustomException e) { // Manejo específico de la excepción personalizada
            throw new RuntimeException("Custom error occurred: " + e.getMessage());
        } catch (Exception e) { // Manejo general de otras excepciones
            throw new RuntimeException("An error occurred: " + e.getMessage());
        }

        //return feignClient.findById(id);
    }

}
