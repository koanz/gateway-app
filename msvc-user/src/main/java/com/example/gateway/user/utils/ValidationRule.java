package com.example.gateway.user.utils;

import org.springframework.stereotype.Component;

@Component
public class ValidationRule {
    public boolean isUserNameValid(String username) {
        return username != null && !username.trim().isEmpty();
    }
}
