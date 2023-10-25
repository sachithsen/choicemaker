package org.sachith.choicemaker.model.dto;

import org.sachith.choicemaker.model.EventType;

public class SocketEvent {

    private EventType eventType;
    private String data;
    private String sessionId;
    private String username;

    public SocketEvent(EventType eventType, String data, String sessionId, String username) {
        this.eventType = eventType;
        this.data = data;
        this.sessionId = sessionId;
        this.username = username;
    }

    @Override
    public String toString() {
        return "SocketEvent{" +
                "eventType=" + eventType +
                ", message='" + data + '\'' +
                ", sessionId='" + sessionId + '\'' +
                ", username='" + username + '\'' +
                '}';
    }

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
