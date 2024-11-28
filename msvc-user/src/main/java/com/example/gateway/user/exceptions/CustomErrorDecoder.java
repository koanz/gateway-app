package com.example.gateway.user.exceptions;

import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class CustomErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String s, Response response) {
        if (response.status() == HttpStatus.NOT_FOUND.value()) {
            return new CustomException("Resource not found");
        }

        return new ResponseStatusException(HttpStatus.valueOf(response.status()), response.reason());
    }
}
