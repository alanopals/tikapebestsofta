/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kala;

import java.util.*;
import java.sql.*;

public class PeliDao implements Dao<Peli, Integer> {

    private Database database;

    public PeliDao(Database database) {
        this.database = database;
    }

    @Override
    public Peli findOne(Integer key) throws SQLException {
        Connection connection = database.getConnection();

        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Peli WHERE id = ?");
        stmt.setObject(1, key);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();

        if (!hasOne) {
            return null;
        }

        int id = rs.getInt("id");
        String otsikko = rs.getString("otsikko");
        int genre_id = rs.getInt("genre_id");

        Peli p = new Peli(id, otsikko, genre_id);

        rs.close();
        stmt.close();
        connection.close();

        return p;
    }

    @Override
    public List<Peli> findAll() throws SQLException {
        Connection connection = database.getConnection();

        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Peli");

        ResultSet rs = stmt.executeQuery();

        List<Peli> pelit = new ArrayList<>();

        while (rs.next()) {
            int id = rs.getInt("id");
            String otsikko = rs.getString("otsikko");
            int genre_id = rs.getInt("genre_id");

            Peli p = new Peli(id, otsikko, genre_id);
            pelit.add(p);

        }

        rs.close();
        stmt.close();
        connection.close();

        return pelit;
    }

    @Override
    public void delete(Integer key) throws SQLException {
        // ei toteutettu
    }
}