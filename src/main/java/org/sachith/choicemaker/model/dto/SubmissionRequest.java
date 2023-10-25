package org.sachith.choicemaker.model.dto;

public class SubmissionRequest {

    private String sessionId;
    private String username;
    private String text;

    public SubmissionRequest(String sessionId, String username, String text) {
        this.sessionId = sessionId;
        this.username = username;
        this.text = text;
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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
