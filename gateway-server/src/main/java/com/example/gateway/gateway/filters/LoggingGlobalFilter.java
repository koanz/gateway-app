package com.example.gateway.gateway.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class LoggingGlobalFilter implements GlobalFilter {
    private final Logger logger = LoggerFactory.getLogger(LoggingGlobalFilter.class);
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // Loguear detalles de la petición entrante
        logger.info("Request: {} {}", exchange.getRequest().getMethod(), exchange.getRequest().getURI());
        // Continuar con el siguiente filtro en la cadena
        return chain.filter(exchange).then(Mono.fromRunnable(() -> {
            // Loguear detalles de la respuesta saliente
            logger.info("Response: {}", exchange.getResponse().getStatusCode());
        }));
    }
}
