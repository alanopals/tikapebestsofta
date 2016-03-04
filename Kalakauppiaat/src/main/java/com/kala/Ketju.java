package com.kala;

public class Ketju {
    
    private final int id;
    private final int palstaId;
    private final String otsikko;

    public Ketju(int id, int palstaId, String otsikko) {
        this.id = id;
        this.palstaId = palstaId;
        this.otsikko = otsikko;
    }

    public int getId() {
        return id;
    }

    public int getPalstaId() {
        return palstaId;
    }

    public String getOtsikko() {
        return otsikko;
    }
}
