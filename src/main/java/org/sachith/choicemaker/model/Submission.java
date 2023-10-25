package org.sachith.choicemaker.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "submissions")
public class Submission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String sessionId;
    private String username;
    private String text;
    private boolean isSelected;

    public Submission(String sessionId, String username, String text, boolean isSelected) {
        this.sessionId = sessionId;
        this.username = username;
        this.text = text;
        this.isSelected = isSelected;
    }

    public Submission() {}

    @Override
    public String toString() {
        return "Submission{" +
                "id=" + id +
                ", sessionId='" + sessionId + '\'' +
                ", username='" + username + '\'' +
                ", text='" + text + '\'' +
                ", isSelected=" + isSelected +
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

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
