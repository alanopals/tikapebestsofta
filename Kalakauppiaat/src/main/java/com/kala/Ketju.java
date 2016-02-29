package com.kala;

import java.sql.Timestamp;

public class Ketju {

    private final int id;
    private final String nimimerkki;
    private final String otsikko;
    private final String sisalto;
    private final Timestamp aika;

    public Ketju(int id, String nimimerkki, String otsikko, String sisalto) {
        this.id = id;
        this.nimimerkki = nimimerkki;
        this.otsikko = otsikko;
        this.sisalto = sisalto;
        this.aika = new Timestamp(new java.util.Date().getTime());
    }

    public int getId() {
        return id;
    }

    public String getNimimerkki() {
        return nimimerkki;
    }

    public String getOtsikko() {
        return otsikko;
    }

    public String getSisalto() {
        return sisalto;
    }

    public Timestamp getAika() {
        return aika;
    }

}
