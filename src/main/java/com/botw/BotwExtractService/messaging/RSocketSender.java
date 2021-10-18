package com.botw.BotwExtractService.messaging;

import com.botw.BotwExtractService.MonsterDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(prefix = "sender", name = "rsocket", havingValue = "rsocket", matchIfMissing = false)
public class RSocketSender implements Sender {

    private final String route;

    private final RSocketRequester requester;

    public RSocketSender(@Value("${RSocket.host}") String host, @Value("${RSSocket.port}") Integer port,
                         @Value("${RSocket.route}") String route){

        this.route = route;
        this.requester = RSocketRequester.builder().tcp(host, port);
    }

    @Override
    public void send(MonsterDto monster) {
        requester.route(route)
                .data(monster)
                .send()
                .subscribe();
    }
}
