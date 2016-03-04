package com.kala;

import java.sql.Timestamp;

public class Viesti {

    private final int id;
    private final int ketju_id;
    private final String nimimerkki;
    private final String sisalto;
    private Timestamp aika;

    public Viesti(int id, int ketju_id, String nimimerkki, String sisalto) {
        this.id = id;
        this.ketju_id = ketju_id;
        this.nimimerkki = nimimerkki;
        this.sisalto = sisalto;        
    }
    
    public void setAika() {
        this.aika = new Timestamp(new java.util.Date().getTime());
    }

    public int getId() {
        return id;
    }

    public String getNimimerkki() {
        return nimimerkki;
    }

    public String getSisalto() {
        return sisalto;
    }

    public Timestamp getAika() {
        return aika;
    }

}
