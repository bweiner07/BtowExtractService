package com.botw.BotwExtractService.business;

import com.botw.BotwExtractService.MonsterDto;
import com.botw.BotwExtractService.data.BotwDataService;
import com.botw.BotwExtractService.messaging.Sender;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class BusinessService {

    private final Sender sender;

    private final BotwDataService dataService;

    public Flux<MonsterDto> extractMonsters() {
        return dataService.getMonsters()
                .doOnNext(sender::send);
    }
}
