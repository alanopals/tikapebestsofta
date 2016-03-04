package com.kala;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class KetjuDao implements Dao<Ketju, Integer> {

    private Database database;

    public KetjuDao(Database database) {
        this.database = database;
    }

    @Override 
    public Ketju findOne(Integer key) throws SQLException {
        Connection connection = database.getConnection();

        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Ketju WHERE id = ?");
        stmt.setObject(1, key);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();

        if (!hasOne) {
            return null;
        }

        int id = rs.getInt("id");
        String nimimerkki = rs.getString("nimimerkki");
        String otsikko = rs.getString("otsikko");
        String sisalto = rs.getString("sisalto");
        Timestamp aika = rs.getTimestamp("aika");

        Ketju k = new Ketju(id, nimimerkki, otsikko, sisalto);

        rs.close();
        stmt.close();
        connection.close();

        return k;
    }

    @Override
    public List<Ketju> findAll() throws SQLException {
        Connection connection = database.getConnection();

        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Ketju");

        ResultSet rs = stmt.executeQuery();

        List<Ketju> ketjut = new ArrayList<>();

        while (rs.next()) {
            int id = rs.getInt("id");
            String nimimerkki = rs.getString("nimimerkki");
            String sisalto = rs.getString("sisalto");
            String otsikko = rs.getString("otsikko");
            Timestamp aika = rs.getTimestamp("aika");

            Ketju k = new Ketju(id, nimimerkki, otsikko, sisalto);
            ketjut.add(k);

        }
        rs.close();
        stmt.close();
        connection.close();

        return ketjut;
    }

    @Override
    public void delete(Integer key) throws SQLException {
        // ei toteutettu
    }

}
