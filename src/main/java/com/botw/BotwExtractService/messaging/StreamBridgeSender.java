package com.botw.BotwExtractService.messaging;

import com.botw.BotwExtractService.MonsterDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(prefix = "sender", name = "stream", havingValue = "stream-bridge", matchIfMissing = true)
public class StreamBridgeSender   implements Sender {

    private final StreamBridge streamBridge;

    private final String destination;

    public StreamBridgeSender(StreamBridge streamBridge, @Value("${stream-bridge.destination}") String destination){
        this.streamBridge = streamBridge;
        this.destination = destination;
    }

    @Override
    public void send(MonsterDto monster) {
        if(!streamBridge.send(destination, MessageBuilder.withPayload(monster).build())){
            throw new UnableToPublishMessageException("Failed to publish message to " + destination);
        }
    }
}
