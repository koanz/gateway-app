package com.example.gateway.user.services.impl;

import com.example.gateway.commons.entities.Role;
import com.example.gateway.user.exceptions.EntityNotFoundException;
import com.example.gateway.user.repositories.IRoleRepository;
import com.example.gateway.user.services.IUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;

@Service
public class UserRoleServiceImpl implements IUserRoleService {
    @Autowired
    private IRoleRepository repository;

    @Autowired
    private MessageSource messageSource;

    @Override
    public Role findByName(String name) {
        Role role = repository.findByName(name);

        if(role == null) {

        }

        return role;
    }

    @Override
    public List<Role> findRolesById(List<Long> ids) {
        List<Role> roles = repository.findAllById(ids);

        if(roles.isEmpty()) {
            throw new EntityNotFoundException(messageSource.getMessage("user.role.notfound.list", null, Locale.getDefault()));
        }

        return roles;
    }

}
