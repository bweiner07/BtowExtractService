package com.botw.BotwExtractService.data;

import com.botw.BotwExtractService.MonsterDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.test.StepVerifier;

import java.io.IOException;
import java.util.List;

public class BothDataServiceTest {

    public static MockWebServer mockWebServer;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeAll
    static void setup() throws IOException{
        mockWebServer = new MockWebServer();
        mockWebServer.start();
    }

    @AfterAll
    static void teardown() throws IOException{
        mockWebServer.shutdown();
    }

    @Test
    public void testGetMonsters() throws JsonProcessingException {
        WebClient mockWebClient = WebClient.builder().build();
        String baseUrl = String.format("http://localhost:%s",
                mockWebServer.getPort());
        BotwDataService service = new BotwDataService(mockWebClient, baseUrl);

        mockWebServer.enqueue(new MockResponse().setResponseCode(200).setBody(objectMapper.writeValueAsString(MonsterResponse
                .builder().data(List.of(MonsterModel.builder().build())).build()))
                        .setHeader("Content-Type", "application/json")
                );

        StepVerifier.create(service.getMonsters())//
                .expectNext(MonsterDto.builder().build())//
                .verifyComplete();
    }

    @Test
    public void testGetMonstersError() throws JsonProcessingException {
        WebClient mockWebClient = WebClient.builder().build();
        String baseUrl = String.format("http://localhost:%s",
                mockWebServer.getPort());
        BotwDataService service = new BotwDataService(mockWebClient, baseUrl);

        mockWebServer.enqueue(new MockResponse().setResponseCode(404).setBody(objectMapper.writeValueAsString(MonsterResponse
                        .builder().data(List.of(MonsterModel.builder().build())).build()))
                .setHeader("Content-Type", "application/json")
        );

        StepVerifier.create(service.getMonsters()).expectError().verify();
    }
}
