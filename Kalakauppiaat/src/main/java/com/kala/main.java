package com.kala;

import com.kala.dao.ViestiDao;
import com.kala.dao.KetjuDao;
import com.kala.dao.PalstaDao;
import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;
import spark.ModelAndView;

import static spark.Spark.*;
import spark.template.thymeleaf.ThymeleafTemplateEngine;

public class main {
    
    public static void main(String[] args) throws Exception {
        
        if (System.getenv("PORT") != null) {
            port(Integer.valueOf(System.getenv("PORT")));
        }
        
        Database database = new Database("jdbc:sqlite:kalakauppiaat.db");
        
        PalstaDao pDao = new PalstaDao(database);
        KetjuDao kDao = new KetjuDao(database);
        ViestiDao vDao = new ViestiDao(database);
        
        get("/", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("palstat", pDao.findAll());
            
            return new ModelAndView(map, "palstat");
        }, new ThymeleafTemplateEngine());
        
        post("/", (req, res) -> {
            String kuvaus = req.queryParams("kuvaus");
            
            if (kuvaus.isEmpty()) {
                res.redirect("./");
                return null;
            }
            
            pDao.add(new Palsta(kuvaus));
            res.redirect("./");
            
            return null;
        });
        
        get("/palsta", (req, res) -> {
            HashMap map = new HashMap<>();
            
            int palsta_id = Integer.parseInt(req.queryParams("id"));
            int sivu = Integer.parseInt(req.queryParams("sivu"));
            
            List<Integer> sivut = new ArrayList<>();
            
            for (int i = 0; i <= (pDao.getKoko2(palsta_id) - 1) / 10; i++) {
                sivut.add(i + 1);
            }
            
            map.put("palsta", pDao.findOne(palsta_id));
            map.put("ketjut", kDao.findAll(palsta_id, sivu));
            map.put("sivut", sivut);
            
            return new ModelAndView(map, "ketjut");
        }, new ThymeleafTemplateEngine());
        
        post("/palsta", (req, res) -> {
            int palsta_id = Integer.parseInt(req.queryParams("id"));
            int sivu = Integer.parseInt(req.queryParams("sivu"));
            
            String nimimerkki = req.queryParams("nimimerkki");
            String otsikko = req.queryParams("otsikko");
            String sisalto = req.queryParams("sisalto");
            
            if (nimimerkki.isEmpty() || otsikko.isEmpty() || sisalto.isEmpty()) {
                res.redirect("./palsta?id=" + palsta_id + "&sivu=" + String.valueOf(sivu));
                return null;
            }
            
            kDao.add(new Ketju(palsta_id, otsikko));
            vDao.add(new Viesti(kDao.findOne(-1).getId(), nimimerkki, sisalto));
            
            res.redirect("./palsta?id=" + palsta_id + "&sivu=1");
            
            return null;
        });
        
        get("/ketju", (req, res) -> {
            HashMap map = new HashMap<>();
            
            int ketju_id = Integer.parseInt(req.queryParams("id"));
            int sivu = Integer.parseInt(req.queryParams("sivu"));
            
            List<Integer> sivut = new ArrayList<>();
            
            for (int i = 0; i <= (kDao.getKoko(ketju_id) - 1) / 10; i++) {
                sivut.add(i + 1);
            }
            
            Ketju k = kDao.findOne(ketju_id);
            
            map.put("palsta", pDao.findOne(k.getPalsta_id()));
            map.put("ketju", k);
            map.put("viestit", vDao.findAll(ketju_id, sivu));
            map.put("sivut", sivut);
            
            return new ModelAndView(map, "viestit");
        }, new ThymeleafTemplateEngine());
        
        post("/ketju", (req, res) -> {
            int ketju_id = Integer.parseInt(req.queryParams("id"));
            int sivu = Integer.parseInt(req.queryParams("sivu"));
            
            String nimimerkki = req.queryParams("nimimerkki");
            String sisalto = req.queryParams("sisalto");
            
            if (nimimerkki.isEmpty() || sisalto.isEmpty()) {
                res.redirect("./ketju?id=" + ketju_id + "&sivu=" + String.valueOf(sivu));
                return null;
            }
            
            int viimeisin = kDao.getKoko(ketju_id) / 10 + 1;
            
            vDao.add(new Viesti(ketju_id, nimimerkki, sisalto));
            res.redirect("./ketju?id=" + ketju_id + "&sivu=" + String.valueOf(viimeisin));
            
            return null;
        });
    }
}