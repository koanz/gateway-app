package com.example.gateway.user.services;

import com.example.gateway.commons.entities.Role;

import java.util.List;

public interface IUserRoleService {
    public Role findByName(String name);
    public List<Role> findRolesById(List<Long> ids);
}
