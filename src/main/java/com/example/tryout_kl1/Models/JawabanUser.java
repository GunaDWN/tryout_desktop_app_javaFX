package com.example.tryout_kl1.Models;

public class JawabanUser {
    private String idJawabanUser;
    private String idTryoutUser;
    private String idSoal;
    private String jawabanUser;
    private double skorSementara;

    public JawabanUser() {
    }

    public String getIdJawabanUser() {
        return idJawabanUser;
    }

    public void setIdJawabanUser(String idJawabanUser) {
        this.idJawabanUser = idJawabanUser;
    }

    public String getIdTryoutUser() {
        return idTryoutUser;
    }

    public void setIdTryoutUser(String idTryoutUser) {
        this.idTryoutUser = idTryoutUser;
    }

    public String getIdSoal() {
        return idSoal;
    }

    public void setIdSoal(String idSoal) {
        this.idSoal = idSoal;
    }

    public String getJawabanUser() {
        return jawabanUser;
    }

    public void setJawabanUser(String jawabanUser) {
        this.jawabanUser = jawabanUser;
    }

    public double getSkorSementara() {
        return skorSementara;
    }

    public void setSkorSementara(double skorSementara) {
        this.skorSementara = skorSementara;
    }
}