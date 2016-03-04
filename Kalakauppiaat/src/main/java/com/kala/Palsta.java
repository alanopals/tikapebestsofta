package com.kala;

public class Palsta {

    private final int id;
    private final String kuvaus;

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
}
