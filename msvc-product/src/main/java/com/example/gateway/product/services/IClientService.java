package com.example.gateway.product.services;

import com.example.gateway.commons.entities.Client;

public interface IClientService {

    public Client findById(Long id);
}
