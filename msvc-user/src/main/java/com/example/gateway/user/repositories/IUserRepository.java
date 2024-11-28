package com.example.gateway.user.repositories;

import com.example.gateway.commons.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {
    @Query(value = "SELECT u.* FROM users u WHERE u.client_id = :client_id", nativeQuery = true)
    public List<User> findAllByClientId(@Param("client_id") Long clientId);
}
