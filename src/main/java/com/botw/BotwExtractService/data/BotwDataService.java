package com.botw.BotwExtractService.data;


import com.botw.BotwExtractService.MonsterDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Service
public class BotwDataService {

    private final WebClient webClient;

    private final String baseUrl;

    public BotwDataService(WebClient webClient, @Value("${botw.url.monster}") String baseUrl){
        this.webClient = webClient;
        this.baseUrl = baseUrl;
    }


    public Flux<MonsterDto> getMonsters() {
        return webClient.get()
                .uri(baseUrl)
                .retrieve()
                .bodyToMono(MonsterResponse.class)
                .flatMapIterable(MonsterResponse::getData)
                .map(BotwDataService::map);
    }

    private static MonsterDto map(MonsterModel model){
        return MonsterDto.builder()
                .category(model.getCategory())
                .commonLocations(model.getCommon_locations())
                .description(model.getDescription())
                .drops(model.getDrops())
                .id(model.getId())
                .image(model.getImage())
                .name(model.getName())
                .build();
    }
}
