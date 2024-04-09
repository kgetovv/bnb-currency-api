package net.pb.currency.bnb.utils;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
@Component
public class MyWebSocketHandler extends TextWebSocketHandler {
    private final List<WebSocketSession> sessions = Collections.synchronizedList(new ArrayList<>());

    @Override
    public void afterConnectionEstablished(@NonNull WebSocketSession session) throws IOException {
        log.info("Connection established.");
        sessions.add(session);
        if (session.isOpen()) {
            session.sendMessage(new TextMessage("Success :)"));
        }
    }

    @Override
    public void afterConnectionClosed(@NonNull WebSocketSession session, @NonNull CloseStatus status) {
        log.info("Connection closed.");
        sessions.remove(session);
    }

    public void sendMessageToAll(String message) {
        log.info("Sessions list(at message sending): " + sessions);
        for (WebSocketSession session : sessions) {
            if (session.isOpen()) {
                try {
                    session.sendMessage(new TextMessage(message));
                    log.info("Sent to ws: " + message);
                } catch (IOException e) {
                    log.error("Error sending data through ws: ", e);
                }
            }
        }
    }
}