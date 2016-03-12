package com.kala;

public class Ketju {
    
    private final int id;
    private final int palsta_id;
    private final String otsikko;
    private int koko = 0;
    private String viimeisin = "-";
    
    public Ketju(int palsta_id, String otsikko) {
        this.id = -1;
        this.palsta_id = palsta_id;
        this.otsikko = otsikko;
    }
    
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