package com.botw.BotwExtractService.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

@Configuration
public class Router {

    @Bean
    public RouterFunction<ServerResponse> routeMonsterExtract(ExtractHandler handler){
        return RouterFunctions
                .route(GET("extract/monster").and(accept(MediaType.APPLICATION_JSON)),
                        handler::extractMonsters);
    }
}
