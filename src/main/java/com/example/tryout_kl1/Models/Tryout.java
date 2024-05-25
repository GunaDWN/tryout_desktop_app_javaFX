package com.example.tryout_kl1.Models;

public class Tryout {
    private String id;
    private String name;
    private int duration;
    private double score;
    private String gambarTO;

    public Tryout (String id,String name,int duration, double score){
        this.id = id;
        this.name = name;
        this.duration = duration;
        this.score = score;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public String getGambarTO() {
        return gambarTO;
    }

    public void setGambarTO(String gambarTO) {
        this.gambarTO = gambarTO;
    }
}
