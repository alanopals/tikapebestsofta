package com.kala;

import java.sql.Timestamp;

public class Ketju {

    private final int id;
    private final int palsta_id;
    private final String nimimerkki;
    private final String otsikko;
    private final Timestamp aika;

    public Ketju(int id, int palsta_id, String nimimerkki, String otsikko) {
        this.id = id;
        this.palsta_id = palsta_id;
        this.nimimerkki = nimimerkki;
        this.otsikko = otsikko;
        this.aika = new Timestamp(new java.util.Date().getTime());
    }

    public int getId() {
        return id;
    }

    public int getPalsta_id() {
        return palsta_id;
    }

    public String getNimimerkki() {
        return nimimerkki;
    }

    public String getOtsikko() {
        return otsikko;
    }

    public Timestamp getAika() {
        return aika;
    }

}
