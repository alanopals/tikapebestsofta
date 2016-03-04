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

        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Viesti WHERE id = ?");
        stmt.setObject(1, key);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();

        if (!hasOne) {
            return null;
        }

        int id = rs.getInt("id");
        int ketju_id = rs.getInt("ketju_id");
        String nimimerkki = rs.getString("nimimerkki");
        String sisalto = rs.getString("sisalto");
        Timestamp aika = rs.getTimestamp("aika");

        Viesti v = new Viesti(id, ketju_id, nimimerkki, sisalto);

        rs.close();
        stmt.close();
        connection.close();

        return v;
    }

    @Override
    public List<Viesti> findAll() throws SQLException {
        Connection connection = database.getConnection();

        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Viesti");

        ResultSet rs = stmt.executeQuery();

        List<Viesti> viestit = new ArrayList<>();

        while (rs.next()) {

            int id = rs.getInt("id");
            int ketju_id = rs.getInt("ketju_id");
            String nimimerkki = rs.getString("nimimerkki");
            String sisalto = rs.getString("sisalto");
            Timestamp aika = rs.getTimestamp("aika");

            Viesti v = new Viesti(id, ketju_id, nimimerkki, sisalto);

        }
        
        rs.close();
        stmt.close();
        connection.close();

        return viestit;
    }

    @Override
    public void delete(Integer key) throws SQLException {
        // ei toteutettu
    }

}
