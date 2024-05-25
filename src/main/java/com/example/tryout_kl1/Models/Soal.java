package com.example.tryout_kl1.Models;

public class Soal {
    private String idSoal;
    private String id_mapel;
    private String pertanyaan;
    private String opsi1;
    private String opsi2;
    private String opsi3;
    private String opsi4;
    private String opsi5;
    private String jawabanBenar;
    private double skor;

    public Soal(){
        

    }

    public String getIdSoal() {
        return idSoal;
    }

    public void setIdSoal(String idSoal) {
        this.idSoal = idSoal;
    }

    public String getId_mapel() {
        return id_mapel;
    }

    public void setId_mapel(String id_mapel) {
        this.id_mapel = id_mapel;
    }

    public String getPertanyaan() {
        return pertanyaan;
    }

    public void setPertanyaan(String pertanyaan) {
        this.pertanyaan = pertanyaan;
    }

    public String getOpsi1() {
        return opsi1;
    }

    public void setOpsi1(String opsi1) {
        this.opsi1 = opsi1;
    }

    public String getOpsi2() {
        return opsi2;
    }

    public void setOpsi2(String opsi2) {
        this.opsi2 = opsi2;
    }

    public String getOpsi3() {
        return opsi3;
    }

    public void setOpsi3(String opsi3) {
        this.opsi3 = opsi3;
    }

    public String getOpsi4() {
        return opsi4;
    }

    public void setOpsi4(String opsi4) {
        this.opsi4 = opsi4;
    }

    public String getOpsi5() {
        return opsi5;
    }

    public void setOpsi5(String opsi5) {
        this.opsi5 = opsi5;
    }

    public String getJawabanBenar() {
        return jawabanBenar;
    }

    public void setJawabanBenar(String jawabanBenar) {
        this.jawabanBenar = jawabanBenar;
    }

    public double getSkor() {
        return skor;
    }

    public void setSkor(double skor) {
        this.skor = skor;
    }
}