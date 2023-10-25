package org.sachith.choicemaker.model.dto;

import java.util.List;

public class SummaryRecord {

    private Long id;
    private List<String> participants;
    private String date;
    private String name;
    private String admin;
    private List<String> restaurants;
    private String winner;

    public SummaryRecord(Long id, List<String> participants, String date, String name, String admin, List<String> restaurants, String winner) {
        this.id = id;
        this.participants = participants;
        this.date = date;
        this.name = name;
        this.admin = admin;
        this.restaurants = restaurants;
        this.winner = winner;
    }

    public SummaryRecord() {
    }

    @Override
    public String toString() {
        return "SummaryRecord{" +
                "id=" + id +
                ", participants=" + participants +
                ", date='" + date + '\'' +
                ", name='" + name + '\'' +
                ", admin='" + admin + '\'' +
                ", restaurants=" + restaurants +
                ", winner='" + winner + '\'' +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<String> getParticipants() {
        return participants;
    }

    public void setParticipants(List<String> participants) {
        this.participants = participants;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }

    public List<String> getRestaurants() {
        return restaurants;
    }

    public void setRestaurants(List<String> restaurants) {
        this.restaurants = restaurants;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }
}
