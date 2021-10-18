package com.botw.BotwExtractService.business;

import com.botw.BotwExtractService.MonsterDto;
import com.botw.BotwExtractService.data.BotwDataService;
import com.botw.BotwExtractService.messaging.Sender;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.when;

public class BusinessServiceTest {

    @Test
    public void testGetMonster(){
        Sender mockSender = Mockito.mock(Sender.class);
        BotwDataService mockDataService = Mockito.mock(BotwDataService.class);
        MonsterDto testMonster = MonsterDto.builder().name("Gimp").build();
        given(mockDataService.getMonsters()).willReturn(Flux.just(testMonster));
//        when(mockDataService.getMonsters()).thenReturn(Flux.just(testMonster));
        BusinessService service = new BusinessService(mockSender, mockDataService);

        StepVerifier.create(service.extractMonsters()).expectNext(testMonster).verifyComplete();
        then(mockDataService).should().getMonsters();
        then(mockSender).should().send(any());
    }

    @Test
    public void testGetMonstersError(){
        Sender mockSender = Mockito.mock(Sender.class);
        BotwDataService mockDataService = Mockito.mock(BotwDataService.class);
        given(mockDataService.getMonsters()).willReturn(Flux.error(new RuntimeException()));

        BusinessService service = new BusinessService(mockSender, mockDataService);

        StepVerifier.create(service.extractMonsters())
                .expectError()
                .verify();
        then(mockDataService).should().getMonsters();
        then(mockSender).shouldHaveNoInteractions();
    }

}
