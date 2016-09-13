package com.scottwseo.commons.logging;

import ch.qos.logback.contrib.jackson.JacksonJsonFormatter;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

@ServerEndpoint(value = "/logevent")
public class LogEndPoint {

    private static Queue<Session> queue = new ConcurrentLinkedQueue<>();

    @OnOpen
    public void onOpen(Session session) {
        queue.add(session);
    }

    @OnMessage
    public String onMessage(String message, Session session) {
        return message;
    }

    @OnClose
    public void onClose(Session session, CloseReason closeReason) {
    }

    @OnError
    public void onError(Throwable t) {

    }

    public static void sendAll(String msg) {
        try {
            List<Session> closedSessions = new ArrayList<>();
            for (Session session : queue) {
                if (!session.isOpen()) {
                    closedSessions.add(session);
                } else {
                    Map<String, Object> json = new HashMap<>();
                    json.put("event", "newLog");
                    json.put("data", msg);
                    JacksonJsonFormatter jsonFormatter = new JacksonJsonFormatter();
                    jsonFormatter.setPrettyPrint(true);
                    session.getAsyncRemote().sendText(jsonFormatter.toJsonString(json));
                }
            }
            queue.removeAll(closedSessions);
        } catch (Throwable e) {
            // do nothing since logging endpoint should not trigger another logging
        }
    }

}
