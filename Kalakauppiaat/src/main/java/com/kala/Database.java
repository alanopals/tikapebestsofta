package com.kala;

import java.sql.Connection;
import java.sql.DriverManager;

public class Database {

    private Connection connection;

    public Database(String name) {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:" + name);
            System.out.println("Opened database successfully");
            System.out.println("****************************");
        } catch (Exception e) {
        }
    }

    public Connection getConnection() {
        return connection;
    }
}
