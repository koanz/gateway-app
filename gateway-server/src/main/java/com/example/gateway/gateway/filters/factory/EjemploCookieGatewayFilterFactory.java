package com.example.gateway.gateway.filters.factory;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;

@Component
public class EjemploCookieGatewayFilterFactory extends AbstractGatewayFilterFactory<EjemploCookieGatewayFilterFactory.Config> {
    public static class Config {
        // Define aquí cualquier configuración que necesites
    }

    public EjemploCookieGatewayFilterFactory() {
        super(Config.class);
    }
    @Override public GatewayFilter apply(Config config) {
        return (exchange, chain) -> { // Lógica de tu filtro personalizado
            return chain.filter(exchange);
        };
    }
}
