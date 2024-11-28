package com.example.gateway.order.services.impl;

import com.example.gateway.commons.dtos.responses.UserResponseDto;
import com.example.gateway.commons.entities.User;
import com.example.gateway.order.feign.IUserFeignClient;
import com.example.gateway.order.repositories.IUserRepository;
import com.example.gateway.order.services.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class UserServiceImpl implements IUserService {

    @Autowired
    private IUserFeignClient feignClient;

    @Autowired
    private IUserRepository repository;

    @Override
    public UserResponseDto findById(Long id) {
        return feignClient.findById(id);
    }

    @Override
    public User findEntityById(Long id) {
        Optional<User> user = repository.findById(id);

        if(user.isEmpty()) {
            // throw new exception
        }

        return user.get();
    }
}
