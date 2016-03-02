package com.kala;

import java.util.List;
import static spark.Spark.get;

public class Main {

    public static void main(String[] args) throws Exception {
        
        Database database = new Database("kalakauppiaat.db");

        ViestiDao vd = new ViestiDao(database);

        List<Viesti> viestit = vd.findAll();

        get("/viestit", (req, res) -> {
            StringBuilder sb = new StringBuilder();
            sb.append("<ul>\n");
            for (Viesti v : viestit) {
                String sisalto = v.getSisalto();
                sb.append("<li>").append(sisalto).append("</li>\n");
            }

            sb.append("</ul>\n");
            return sb.toString();
        });
    }
}
