package org.sachith.choicemaker.model.dto;

import java.util.List;

public class SessionRequest {

    private String sessionAdmin;
    private List<String> sessionParticipants;

    public SessionRequest(String sessionAdmin, List<String> sessionParticipants) {
        this.sessionAdmin = sessionAdmin;
        this.sessionParticipants = sessionParticipants;
    }

    public String getSessionAdmin() {
        return sessionAdmin;
    }

    public void setSessionAdmin(String sessionAdmin) {
        this.sessionAdmin = sessionAdmin;
    }

    public List<String> getSessionParticipants() {
        return sessionParticipants;
    }

    public void setSessionParticipants(List<String> sessionParticipants) {
        this.sessionParticipants = sessionParticipants;
    }
}
