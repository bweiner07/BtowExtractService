package com.botw.BotwExtractService.web;

import com.botw.BotwExtractService.business.BusinessService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class ExtractHandler {
    private final BusinessService service;
    public Mono<ServerResponse> extractMonsters(ServerRequest request){
        return service.extractMonsters()
                .then(ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(BodyInserters.fromValue("Ok")));
    }
}
