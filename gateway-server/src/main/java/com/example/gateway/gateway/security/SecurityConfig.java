package com.example.gateway.gateway.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AbstractAuthenticationToken;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
//import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.stream.Collectors;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) throws Exception {

        return http.authorizeExchange(authz -> {
                    authz.pathMatchers("/authorized", "/logout").permitAll()
                            .pathMatchers(HttpMethod.GET,
                                    "/api/users/v1/user/username/**").permitAll()
                            .pathMatchers(HttpMethod.POST,
                                    "/api/users/v1/user/create").hasRole("USER")
                            .pathMatchers(HttpMethod.GET,
                                    "/api/users/v1/user/find/**", "/api/users/v1/user/find-all/**",
                                    "/api/productos/v1/user/**").hasRole("USER")
                            .pathMatchers(HttpMethod.PUT,"/api/users/v1/user/update/**", "/api/productos/v1/user/update/**").hasRole("USER")
                            .pathMatchers(HttpMethod.GET, "/api/items/v1/**", "/api/orders/v1/**").hasAnyRole("ADMIN", "USER")
                            .pathMatchers(HttpMethod.POST,
                                    "/api/products/v1/create", "/api/items/v1/create", "/api/orders/v1/create").hasAnyRole("ADMIN", "USER")
                            .pathMatchers(HttpMethod.POST,
                                    "/api/users/v1/admin/create").hasRole("ADMIN")
                            .pathMatchers(HttpMethod.GET,
                                    "/api/clients/v1/admin/**", "/api/users/v1/admin/**"/*,
                                    "/api/products/v1/admin/find/**", "/api/products/v1/admin/find-all/**"*/).hasRole("ADMIN")
                            .pathMatchers(HttpMethod.POST,
                                    "/api/clients/v1/admin/create", "/api/users/v1/admin/create"/*, "/api/products/v1/admin/create"*/).hasRole("ADMIN")
                            .anyExchange().authenticated();
                }).cors(csrf -> csrf.disable())
                .oauth2Login(withDefaults())
                .oauth2Client(withDefaults())
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(
                        jwt -> jwt.jwtAuthenticationConverter(new Converter<Jwt, Mono<AbstractAuthenticationToken>>() {
                            @Override
                            public Mono<AbstractAuthenticationToken> convert(Jwt source) {
                                Collection<String> roles = source.getClaimAsStringList("roles");
                                Collection<GrantedAuthority> authorities = roles.stream()
                                        .map(SimpleGrantedAuthority::new)
                                        .collect(Collectors.toList());

                                return Mono.just(new JwtAuthenticationToken(source, authorities));
                            }
                        })))
                .build();
    }

    /*@Bean
    SecurityFilterChain securityWebFilterChain(HttpSecurity http) throws Exception {

        return http.authorizeHttpRequests(authz -> {
                    authz.requestMatchers("/authorized", "/logout").permitAll()
                            .requestMatchers(HttpMethod.GET,
                                    "/api/users/v1/user/username/**").permitAll()
                            .requestMatchers(HttpMethod.POST,
                                    "/api/users/v1/user/create").hasRole("USER")
                            .requestMatchers(HttpMethod.GET,
                                    "/api/users/v1/user/find/**", "/api/users/v1/user/find-all/**").hasRole("USER")
                            .requestMatchers(HttpMethod.PUT,
                                    "/api/users/v1/user/update/**").hasRole("USER")
                            .requestMatchers(HttpMethod.GET, "/api/products/v1/**", "/api/items/v1/**", "/api/orders/v1/**").hasAnyRole("ADMIN", "USER")
                            .requestMatchers(HttpMethod.POST,
                                    "/api/users/v1/admin/create", "/api/products/v1/create", "/api/items/v1/create", "/api/orders/v1/create").hasRole("ADMIN")
                            .requestMatchers(HttpMethod.GET,
                                    "/api/clients/v1/admin/**", "/api/users/v1/admin/**", "/api/products/v1/admin/**").hasRole("ADMIN")
                            .requestMatchers(HttpMethod.POST,
                                    "/api/clients/v1/admin/create", "/api/users/v1/admin/create", "/api/products/v1/admin/create").hasRole("ADMIN")
                            .anyRequest().authenticated();
                }).cors(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .oauth2Login(login -> login.loginPage("/oauth2/authorization/client-app"))
                .oauth2Client(withDefaults())
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(
                        jwt -> jwt.jwtAuthenticationConverter(new Converter<Jwt, AbstractAuthenticationToken>() {

                            @Override
                            public AbstractAuthenticationToken convert(Jwt source) {
                                Collection<String> roles = source.getClaimAsStringList("roles");
                                Collection<GrantedAuthority> authorities = roles.stream()
                                        .map(SimpleGrantedAuthority::new)
                                        .collect(Collectors.toList());

                                return new JwtAuthenticationToken(source, authorities);
                            }
                        })))
                .build();
    }*/

}
