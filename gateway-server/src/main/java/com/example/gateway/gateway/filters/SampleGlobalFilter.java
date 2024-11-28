package com.example.gateway.gateway.filters;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Component
public class SampleGlobalFilter implements GlobalFilter, Ordered {
    private final Logger log = LoggerFactory.getLogger(SampleGlobalFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("Filtro antes del Request PRE");
        exchange.getRequest().mutate().headers(h -> h.add("token", "abcdefg"));

        return chain.filter(exchange).then(Mono.fromRunnable(() -> {
            log.info("Filtro response POST");
            String token = exchange.getRequest().getHeaders().getFirst("token");
            if(token != null) {
                log.info("TOKEN: " + token);
                exchange.getResponse().getHeaders().add("token", token);
            }

            Optional.ofNullable(exchange.getRequest().getHeaders().getFirst("token")).ifPresent(t -> {
                log.info("TOKEN2: " + t);
                exchange.getResponse().getHeaders().add("token2", t);
            });

            exchange.getResponse().getCookies().add("colour", ResponseCookie.from("colour", "red").build());
        }));
    }

    @Override
    public int getOrder() {
        return 100;
    }
}
