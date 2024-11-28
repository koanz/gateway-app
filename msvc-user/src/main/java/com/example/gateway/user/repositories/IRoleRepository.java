package com.example.gateway.user.repositories;

import com.example.gateway.commons.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IRoleRepository extends JpaRepository<Role, Long> {
    @Query(value = "SELECT r FROM roles r WHERE r.name = :name", nativeQuery = true)
    public Role findByName(@Param("name") String name);

}
