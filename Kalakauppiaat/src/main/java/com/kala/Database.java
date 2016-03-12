package com.kala;

import java.sql.*;
import java.util.*;
import java.net.*;

public class Database {

    private String databaseAddress;

    public Database(String databaseAddress) throws Exception {
        this.databaseAddress = databaseAddress;

        init();
    }

    private void init() {
        List<String> lauseet = null;
        if (this.databaseAddress.contains("postgres")) {
            lauseet = postgreLauseet();
        } else {
            lauseet = sqliteLauseet();
        }

        // "try with resources" sulkee resurssin automaattisesti lopuksi
        try (Connection conn = getConnection()) {
            Statement st = conn.createStatement();

            // suoritetaan komennot
            for (String lause : lauseet) {
                System.out.println("Running command >> " + lause);
                st.executeUpdate(lause);
            }

        } catch (Throwable t) {
            // jos tietokantataulu on jo olemassa, ei komentoja suoriteta
            System.out.println("Error >> " + t.getMessage());
        }
    }

    public Connection getConnection() throws SQLException {
        if (this.databaseAddress.contains("postgres")) {
            try {
                URI dbUri = new URI(databaseAddress);

                String username = dbUri.getUserInfo().split(":")[0];
                String password = dbUri.getUserInfo().split(":")[1];
                String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath();

                return DriverManager.getConnection(dbUrl, username, password);
            } catch (Throwable t) {
                System.out.println("Error: " + t.getMessage());
                t.printStackTrace();
            }
        }

        return DriverManager.getConnection(databaseAddress);
    }

    private List<String> postgreLauseet() {
        ArrayList<String> lista = new ArrayList<>();

        // tietokantataulujen luomiseen tarvittavat komennot suoritusjärjestyksessä
        lista.add("DROP TABLE Palsta;");
        lista.add("DROP TABLE Ketju;");
        lista.add("DROP TABLE Viesti;");
        // heroku käyttää SERIAL-avainsanaa uuden tunnuksen automaattiseen luomiseen
        lista.add("CREATE TABLE Palsta (id SERIAL PRIMARY KEY, kuvaus varchar(40) NOT NULL);");
        lista.add("INSERT INTO Palsta (kuvaus) VALUES ('postgresql-palsta');");
        lista.add("CREATE TABLE Ketju (id SERIAL PRIMARY KEY, palsta_id INTEGER NOT NULL, otsikko VARCHAR(40) NOT NULL);");
        lista.add("INSERT INTO Ketju (palsta_id, otsikko) VALUES ('postgresql-ketju');");
        lista.add("CREATE TABLE Viesti (id SERIAL PRIMARY KEY, ketju_id INTEGER NOT NULL, nimimerkki VARCHAR(20) NOT NULL, sisalto VARCHAR(140) NOT NULL, aika DATETIME NOT NULL);");
        lista.add("INSERT INTO Viesti (ketju_id, nimimerkki, sisalto, aika) VALUES ('postgresql-viesti');");

        return lista;
    }

    private List<String> sqliteLauseet() {
        ArrayList<String> lista = new ArrayList<>();

        // tietokantataulujen luomiseen tarvittavat komennot suoritusjärjestyksessä
        lista.add("CREATE TABLE Palsta (id SERIAL PRIMARY KEY, kuvaus varchar(40) NOT NULL);");
        lista.add("INSERT INTO Palsta (kuvaus) VALUES ('sqlite-palsta');");
        lista.add("CREATE TABLE Ketju (id SERIAL PRIMARY KEY, palsta_id INTEGER NOT NULL, otsikko VARCHAR(40) NOT NULL);");
        lista.add("INSERT INTO Ketju (palsta_id, otsikko) VALUES ('sqlite-ketju');");
        lista.add("CREATE TABLE Viesti (id SERIAL PRIMARY KEY, ketju_id INTEGER NOT NULL, nimimerkki VARCHAR(20) NOT NULL, sisalto VARCHAR(140) NOT NULL, aika DATETIME NOT NULL);");
        lista.add("INSERT INTO Viesti (ketju_id, nimimerkki, sisalto, aika) VALUES ('sqlite-viesti');");

        return lista;
    }
}