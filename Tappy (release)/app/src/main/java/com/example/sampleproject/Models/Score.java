package com.example.sampleproject.Models;

public class Score {
    private String username;
    private String game;
    private long score;
    private String additionalInfo;

    public Score(String username, String game, long score, String additionalInfo) {
        this.username = username;
        this.game = game;
        this.score = score;
        this.additionalInfo = additionalInfo;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getGame() {
        return game;
    }

    public void setGame(String game) {
        this.game = game;
    }

    public long getScore() {
        return score;
    }

    public void setScore(long score) {
        this.score = score;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }
}
