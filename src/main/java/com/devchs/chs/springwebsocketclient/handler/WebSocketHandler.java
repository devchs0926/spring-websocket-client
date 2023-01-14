package com.devchs.chs.springwebsocketclient.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;

@Log4j2
@RequiredArgsConstructor
@Component
public class WebSocketHandler implements StompSessionHandler {

    @Override
    public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
        log.info("isConnected : {}", session.isConnected());
        session.subscribe("/sub/notice", this);
    }

    @Override
    public void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] payload, Throwable exception) {
        log.error("exception : {}", session.isConnected());
    }

    @Override
    public void handleTransportError(StompSession session, Throwable exception) {
        log.error("transportError : {}", exception.getMessage());

    }

    @Override
    public Type getPayloadType(StompHeaders headers) {
        log.info("payloadType : {}", headers);
        return byte[].class;
    }

    @Override
    public void handleFrame(StompHeaders headers, Object payload) {
        if (payload != null) {
            String message = new String((byte[]) payload);
            log.info("message : {}", message);
        }
    }
}
