package com.devchs.chs.springwebsocketclient.client;

import com.devchs.chs.springwebsocketclient.handler.WebSocketHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.messaging.converter.SimpleMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.RestTemplateXhrTransport;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Log4j2
@RequiredArgsConstructor
@Component
public class WebSocketClient {

    private final WebSocketHandler handler;

    @PostConstruct
    private void client() {
        List<Transport> transports = new ArrayList<>();
        transports.add(new WebSocketTransport(new StandardWebSocketClient()));
        transports.add(new RestTemplateXhrTransport());

        SockJsClient sockjsClient = new SockJsClient(transports);
        WebSocketStompClient stompClient = new WebSocketStompClient(sockjsClient);
        stompClient.setMessageConverter(new SimpleMessageConverter());

        stompClient.connect("http://localhost:8080/ws", handler);
    }
}
