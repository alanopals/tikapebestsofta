package com.kala;

public class Palsta {
    
    private final int id;
    private final String kuvaus;
    private int koko = 0;
    private String viimeisin = "-";
    
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
    
    public int getKoko() {
        return koko;
    }
    
    public void setKoko(int koko) {
        this.koko = koko;
    }
    
    public String getViimeisin() {
        return viimeisin;
    }
    
    public void setViimeisin(String viimeisin) {
        this.viimeisin = viimeisin;
    }
}