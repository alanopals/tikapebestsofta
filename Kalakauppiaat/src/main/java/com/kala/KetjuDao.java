package com.kala;

import java.sql.*;
import java.util.*;

public class KetjuDao implements Dao<Ketju, Integer> {

    private Database database;

    public KetjuDao(Database database) throws Exception {
        this.database = database;
    }

    private List<Ketju> collect(ResultSet rs) throws Exception {
        ArrayList<Ketju> ketjut = new ArrayList<>();

        while (rs.next()) {
            ketjut.add(new Ketju(rs.getInt("id"), rs.getInt("palsta_id"), rs.getString("otsikko")));
        }

        return ketjut;
    }

    public List<Ketju> findAllIn(int palsta_id) throws SQLException {
        Connection connection = database.getConnection();

        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Palsta WHERE palsta_id = ?");
        stmt.setInt(1, palsta_id);

        ResultSet rs = stmt.executeQuery();

        List<Ketju> lista = null;

        collect:
        try {
            lista = collect(rs);
        } catch (Exception ex) {
            break collect;
        }

        rs.close();
        stmt.close();
        connection.close();

        return lista;
    }

    @Override
    public void delete(Integer key) throws SQLException {
        Connection connection = database.getConnection();

        PreparedStatement stmt = connection.prepareStatement("DELETE FROM Ketju WHERE id = ?");
        stmt.setObject(1, key);

        stmt.executeUpdate();

        stmt.close();
        connection.close();
    }

    public int countAllIn(int ketju_id) throws SQLException {
        Connection connection = database.getConnection();

        PreparedStatement stmt = connection.prepareStatement("SELECT COUNT(*) FROM Viesti WHERE ketju_id = ?");
        stmt.setInt(1, ketju_id);

        ResultSet rs = stmt.executeQuery();
        int viestit = rs.getInt("COUNT(*)");

        rs.close();
        stmt.close();
        connection.close();

        return viestit;
    }

    @Override
    public Ketju findOne(Integer key) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Ketju> findAll() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void add(Ketju type) throws SQLException {
        Connection connection = database.getConnection();

        PreparedStatement stmt = connection.prepareStatement("INSERT INTO Ketju (palsta_id, otsikko) VALUES(?, ?)");
        stmt.setInt(1, k.getPalsta_id());
        stmt.setString(2, k.getOtsikko());

        stmt.executeUpdate();

        stmt.close();
        connection.close();
    }
}
