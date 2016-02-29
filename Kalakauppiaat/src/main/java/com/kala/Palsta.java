package com.kala;

import java.sql.Timestamp;

public class Palsta {

    private final int id;
    private final String kuvaus;
    private final String nimimerkki;
    private final Timestamp aika;

    public Palsta(int id, String kuvaus, String nimimerkki) {
        this.id = id;
        this.kuvaus = kuvaus;
        this.nimimerkki = nimimerkki;
        this.aika = new Timestamp(new java.util.Date().getTime());
    }

}
