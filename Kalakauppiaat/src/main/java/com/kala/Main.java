package com.kala;

import java.util.List;
import static spark.Spark.get;
import static spark.Spark.port;

public class Main {

    public static void main(String[] args) throws Exception {

        if (System.getenv("PORT") != null) {
            port(Integer.valueOf(System.getenv("PORT")));
        }

        String jdbcOsoite = "jdbc:sqlite:kalakauppiaat.db";
        if (System.getenv("DATABASE_URL") != null) {
            jdbcOsoite = System.getenv("DATABASE_URL");
        }

        Database database = new Database(jdbcOsoite);

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
