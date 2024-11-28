package com.example.gateway.cliente.repositories;

import com.example.gateway.commons.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IClientRepository extends JpaRepository<Client, Long> {
//public interface IClientRepository extends CrudRepository<Client, Long> {
}
