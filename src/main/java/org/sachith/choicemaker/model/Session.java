package org.sachith.choicemaker.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "sessions")
public class Session {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String sessionId;
    private String sessionName;
    private Date sessionDate;
    private String sessionAdmin;
    private String participants;
    private boolean isActive;

    public Session(String sessionId, String sessionName, Date sessionDate, String sessionAdmin, String participants, boolean isActive) {
        this.sessionId = sessionId;
        this.sessionName = sessionName;
        this.sessionDate = sessionDate;
        this.sessionAdmin = sessionAdmin;
        this.participants = participants;
        this.isActive = isActive;
    }

    public Session() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Session session = (Session) o;

        if (isActive != session.isActive) return false;
        if (!id.equals(session.id)) return false;
        if (!sessionId.equals(session.sessionId)) return false;
        if (!Objects.equals(sessionName, session.sessionName)) return false;
        if (!Objects.equals(sessionDate, session.sessionDate)) return false;
        if (!Objects.equals(sessionAdmin, session.sessionAdmin))
            return false;
        return Objects.equals(participants, session.participants);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + sessionId.hashCode();
        result = 31 * result + (sessionName != null ? sessionName.hashCode() : 0);
        result = 31 * result + (sessionDate != null ? sessionDate.hashCode() : 0);
        result = 31 * result + (sessionAdmin != null ? sessionAdmin.hashCode() : 0);
        result = 31 * result + (participants != null ? participants.hashCode() : 0);
        result = 31 * result + (isActive ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Session{" +
                "id=" + id +
                ", sessionId='" + sessionId + '\'' +
                ", sessionName='" + sessionName + '\'' +
                ", sessionDate=" + sessionDate +
                ", sessionAdmin='" + sessionAdmin + '\'' +
                ", participants='" + participants + '\'' +
                ", isActive='" + isActive + '\'' +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getSessionName() {
        return sessionName;
    }

    public void setSessionName(String sessionName) {
        this.sessionName = sessionName;
    }

    public Date getSessionDate() {
        return sessionDate;
    }

    public void setSessionDate(Date sessionDate) {
        this.sessionDate = sessionDate;
    }

    public String getSessionAdmin() {
        return sessionAdmin;
    }

    public void setSessionAdmin(String sessionAdmin) {
        this.sessionAdmin = sessionAdmin;
    }

    public String getParticipants() {
        return participants;
    }

    public void setParticipants(String participants) {
        this.participants = participants;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }
}
