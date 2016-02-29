package com.kala;

import java.util.*;
import java.sql.*;

public class ViestiDao implements Dao<Viesti, Integer> {

    private Database database;

    public ViestiDao(Database database) {
        this.database = database;
    }

    @Override
    public Viesti findOne(Integer key) throws SQLException {
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

        Viesti v = new Viesti(id, otsikko, genre_id);

        rs.close();
        stmt.close();
        connection.close();

        return v;
    }

    @Override
    public List<Viesti> findAll() throws SQLException {
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