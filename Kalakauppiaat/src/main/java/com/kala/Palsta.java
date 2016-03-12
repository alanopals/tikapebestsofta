package com.kala;

public class Palsta {

    private final int id;
    private final String kuvaus;
    private int lkm = 0;
    private String aika = "-";

    public Palsta(String kuvaus) {
        this.id = -1;
        this.kuvaus = kuvaus;
    }

    public Palsta(int id, String kuvaus) {
        this.id = id;
        this.kuvaus = kuvaus;
    }

    public int getId() {
        return id;
    }

    public String getKuvaus() {
        return kuvaus;
    }

    public int getLkm() {
        return lkm;
    }

    public void setLkm(int lkm) {
        this.lkm = lkm;
    }

    public String getAika() {
        return aika;
    }

    public void setAika(String aika) {
        this.aika = aika;
    }
}
