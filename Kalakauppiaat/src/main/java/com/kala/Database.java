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
        List<String> queries = null;
        
        if (this.databaseAddress.contains("postgres")) {
            queries = postgreInit();
        } else {
            queries = sqliteInit();
        }
        
        try (Connection connection = getConnection()) {
            Statement stmt = connection.createStatement();
            
            for (String query : queries) {
                stmt.executeUpdate(query);
            }
        } catch (Throwable t) {
            System.out.println(t.getMessage());
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
                System.out.println(t.getMessage());
                t.printStackTrace();
            }
        }
        
        return DriverManager.getConnection(databaseAddress);
    }
    
    private List<String> postgreInit() {
        ArrayList<String> queries = new ArrayList<>();
        
        queries.add("DROP TABLE Palsta;");
        queries.add("DROP TABLE Ketju;");
        queries.add("DROP TABLE Viesti;");
        
        queries.add("CREATE TABLE Palsta (id SERIAL PRIMARY KEY, kuvaus varchar(40) NOT NULL);");
        queries.add("INSERT INTO Palsta (kuvaus) VALUES ('postgresql-palsta');");
        queries.add("CREATE TABLE Ketju (id SERIAL PRIMARY KEY, palsta_id INTEGER NOT NULL, otsikko VARCHAR(40) NOT NULL);");
        queries.add("INSERT INTO Ketju (palsta_id, otsikko) VALUES ('postgresql-ketju');");
        queries.add("CREATE TABLE Viesti (id SERIAL PRIMARY KEY, ketju_id INTEGER NOT NULL, nimimerkki VARCHAR(20) NOT NULL, sisalto VARCHAR(140) NOT NULL, aika DATETIME NOT NULL);");
        queries.add("INSERT INTO Viesti (ketju_id, nimimerkki, sisalto, aika) VALUES ('postgresql-viesti');");
        
        return queries;
    }
    
    private List<String> sqliteInit() {
        ArrayList<String> queries = new ArrayList<>();
        
        queries.add("CREATE TABLE Palsta (id SERIAL PRIMARY KEY, kuvaus varchar(40) NOT NULL);");
        queries.add("INSERT INTO Palsta (kuvaus) VALUES ('sqlite-palsta');");
        queries.add("CREATE TABLE Ketju (id SERIAL PRIMARY KEY, palsta_id INTEGER NOT NULL, otsikko VARCHAR(40) NOT NULL);");
        queries.add("INSERT INTO Ketju (palsta_id, otsikko) VALUES ('sqlite-ketju');");
        queries.add("CREATE TABLE Viesti (id SERIAL PRIMARY KEY, ketju_id INTEGER NOT NULL, nimimerkki VARCHAR(20) NOT NULL, sisalto VARCHAR(140) NOT NULL, aika DATETIME NOT NULL);");
        queries.add("INSERT INTO Viesti (ketju_id, nimimerkki, sisalto, aika) VALUES ('sqlite-viesti');");
        
        return queries;
    }
}