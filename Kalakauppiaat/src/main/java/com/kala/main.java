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
        //maini
        // heroku
        /*if (System.getenv("PORT") != null) {
            port(Integer.valueOf(System.getenv("PORT")));
        }*/
        
        Database database = new Database("jdbc:sqlite:kalakauppiaat.db");
        PalstaDao pDao = new PalstaDao(database);
        ViestiDao vDao = new ViestiDao(database);
        KetjuDao kDao = new KetjuDao(database);
        
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
        
        /*get("/alue", (req, res) -> {
            HashMap map = new HashMap<>();
            int id = Integer.parseInt(req.queryParams("id"));
            int sivu = Integer.parseInt(req.queryParams("sivu"));
            ArrayList<Integer> sivut = new ArrayList<>();
            for (int i = 1; i <= ketjudao.countAllIn(id) / 10 + 1; i++) {
                sivut.add(i);
            }
            map.put("sivut", sivut);
            map.put("ketjut", ketjudao.findAllIn(id, sivu));
            map.put("alue", aluedao.findOne(id));

            return new ModelAndView(map, "ketjut");
        }, new ThymeleafTemplateEngine());

        post("/alue", (req, res) -> {
            String otsikko = req.queryParams("otsikko");
            int alueId = Integer.parseInt(req.queryParams("alueId"));
            if (otsikko.isEmpty()) {
                res.redirect("./alue?id=" + alueId + "&sivu=1");
                return null;
            }
            ketjudao.add(new Ketju(alueId, otsikko));
            res.redirect("./alue?id=" + alueId + "&sivu=1");
            return null;
        });

        get("/ketju", (req, res) -> {
            HashMap map = new HashMap<>();
            int ketjuId = Integer.parseInt(req.queryParams("id"));
            Ketju k = ketjudao.findOne(ketjuId);
            int alueId = k.getAlueId();
            ArrayList<Integer> sivut = new ArrayList<>();
            for (int i = 1; i <= viestidao.countAllIn(ketjuId) / 10 + 1; i++) {
                sivut.add(i);
            }
            map.put("sivut", sivut);
            map.put("viestit", viestidao.findAllIn(ketjuId));
            map.put("ketju", k);
            map.put("alue", aluedao.findOne(alueId));

            return new ModelAndView(map, "viestit");
        }, new ThymeleafTemplateEngine());

        post("/ketju", (req, res) -> {
            int ketjuId = Integer.parseInt(req.queryParams("ketjuId"));
            String viesti = req.queryParams("viesti");
            String nimim = req.queryParams("nimim");
            if (viesti.isEmpty()) {
                res.redirect("./ketju?id=" + ketjuId);
                return null;
            }
            if (nimim.isEmpty()) {
                nimim = "Anonyymi";
            }
            viestidao.add(new Viesti(ketjuId, viesti, nimim));
            res.redirect("./ketju?id=" + ketjuId);
            return null;
        });*/
    }
}