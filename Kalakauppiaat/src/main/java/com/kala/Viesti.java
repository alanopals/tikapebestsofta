package com.kala;

public class Viesti {
    
    private final int id;
    private final int ketju_id;
    private final String nimimerkki;
    private final String sisalto;
    private final String aika;
    
    public Viesti(int ketju_id, String nimimerkki, String sisalto) {
        this.id = -1;
        this.ketju_id = ketju_id;
        this.nimimerkki = nimimerkki;
        this.sisalto = sisalto;
        this.aika = "-";
    }
    
    public Viesti(int id, int ketju_id, String nimimerkki, String sisalto, String aika) {
        this.id = id;
        this.ketju_id = ketju_id;
        this.nimimerkki = nimimerkki;
        this.sisalto = sisalto;
        this.aika = aika;
    }
    
    public int getId() {
        return id;
    }
    
    public int getKetju_id() {
        return ketju_id;
    }
    
    public String getNimimerkki() {
        return nimimerkki;
    }
    
    public String getSisalto() {
        return sisalto;
    }
    
    public String getAika() {
        return aika;
    }
}