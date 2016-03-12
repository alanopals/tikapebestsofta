package com.kala;

public class Ketju {

    private final int id;
    private final int palsta_id;
    private final String otsikko;

    public Ketju(int id, int palsta_id, String otsikko) {
        this.id = id;
        this.palsta_id = palsta_id;
        this.otsikko = otsikko;
    }

    public int getId() {
        return id;
    }

    public int getPalsta_id() {
        return palsta_id;
    }

    public String getOtsikko() {
        return otsikko;
    }
}
