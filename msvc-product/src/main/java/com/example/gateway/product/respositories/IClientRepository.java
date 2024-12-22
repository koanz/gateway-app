package com.example.gateway.product.respositories;

import com.example.gateway.commons.entities.Client;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IClientRepository extends CrudRepository<Client, Long> {
}
