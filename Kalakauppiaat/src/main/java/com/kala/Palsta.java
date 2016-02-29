package com.kala;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class Palsta {

    private final int id;
    private final List<Ketju> ketjut;
    private final String kuvaus;
    private final String nimimerkki;
    private final Timestamp timestamp;

    public Palsta(int id, String kuvaus, String nimimerkki) {
        this.id = id;
        this.ketjut = new ArrayList();
        this.kuvaus = kuvaus;
        this.nimimerkki = nimimerkki;
        this.timestamp = new Timestamp(new java.util.Date().getTime());
    }

}
