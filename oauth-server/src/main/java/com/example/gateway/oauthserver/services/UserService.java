package com.example.gateway.oauthserver.services;

import com.example.gateway.oauthserver.models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {
    private final Logger logger = LoggerFactory.getLogger(UserService.class);
    @Autowired
    private WebClient.Builder client;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("Ejecutando proceso de login UserService.loadUserByUsername con {}", username);
        Map<String, String> params = new HashMap<>();
        params.put("username", username);

        try {
            User user = client.build().get().uri("/v1/user/username/{username}", params)
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(User.class)
                    .block();

            List<GrantedAuthority> roles = user.getRoles()
                    .stream()
                    .map(role -> new SimpleGrantedAuthority(role.getName()))
                    .collect(Collectors.toList());

            logger.info("Se ha realizado con éxito el login por username: {}", user);

            return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                    user.getEnabled(), true, true, true, roles);
        } catch (WebClientResponseException e) {
            String errorDetails = e.getResponseBodyAsString();
            logger.error("User Not Found with username: " + username + ". Error details: " + errorDetails);
            throw new UsernameNotFoundException("User Not Found with username: " + username + ". Error details: " + errorDetails, e);
        } catch (WebClientRequestException e) {
            logger.error("Request error while fetching user: " + username);
            throw new UsernameNotFoundException("Request error while fetching user: " + username, e);
        }
    }
}
