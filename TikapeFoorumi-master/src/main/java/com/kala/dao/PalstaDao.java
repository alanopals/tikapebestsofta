package com.kala.dao;

import com.kala.Database;
import com.kala.Palsta;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PalstaDao implements Dao<Palsta, Integer> {
    
    private Database database;
    
    public PalstaDao(Database database) throws Exception {
        this.database = database;
    }
    
    private List<Palsta> collect(ResultSet rs) throws Exception {
        ArrayList<Palsta> collection = new ArrayList<>();
        
        while (rs.next()) {
            collection.add(new Palsta(rs.getInt("id"), rs.getString("kuvaus")));
        }
        
        return collection;
    }
    
    @Override
    public Palsta findOne(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Palsta WHERE id = ?");
        stmt.setInt(1, key);
        
        ResultSet rs = stmt.executeQuery();
        
        if (!rs.next()) {
            return null;
        }
        
        Palsta p = new Palsta(rs.getInt("id"), rs.getString("kuvaus"));
        
        rs.close();
        stmt.close();
        connection.close();
        
        return p;
    }
    
    @Override
    public List<Palsta> findAll() throws SQLException {
        Connection connection = database.getConnection();
        
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Palsta ORDER BY kuvaus ASC");
        ResultSet rs = stmt.executeQuery();
        
        List<Palsta> collection = null;
        
        collect: try {
            collection = collect(rs);
        } catch (Exception e) {
            break collect;
        }
        
        rs.close();
        stmt.close();
        connection.close();
        
        return collection;
    }
    
    @Override
    public void add(Palsta p) throws SQLException {
        Connection connection = database.getConnection();
        
        PreparedStatement stmt = connection.prepareStatement("INSERT INTO Palsta (kuvaus) VALUES (?)");
        stmt.setString(1, p.getKuvaus());
        
        stmt.executeUpdate();
        
        stmt.close();
        connection.close();
    }
    
    @Override
    public void delete(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        
        PreparedStatement stmt = connection.prepareStatement("DELETE FROM Palsta WHERE id = ?");
        stmt.setInt(1, key);
        
        stmt.executeUpdate();
        
        stmt.close();
        connection.close();
    }
    
    public int countViestit(Integer palsta_id) throws SQLException {
        Connection connection1 = database.getConnection();
        
        PreparedStatement stmt1 = connection1.prepareStatement("SELECT COUNT(*) AS lkm FROM Ketju, Viesti "
                + "WHERE Viesti.ketju_id = Ketju.id AND Ketju.palsta_id = ?");
        stmt1.setInt(1, palsta_id);
        
        ResultSet rs1 = stmt1.executeQuery();
        
        if (!rs1.next()) {
            return 0;
        }
        
        int count = rs1.getInt("lkm");
        
        rs1.close();
        stmt1.close();
        connection1.close();
        
        return count;
    }
    
    public String lastViesti(int palsta_id) throws SQLException {
        Connection connection2 = database.getConnection();
        
        PreparedStatement stmt2 = connection2.prepareStatement("SELECT Viesti.aika AS viimeisin FROM Ketju, Viesti WHERE "
                + "Viesti.ketju_id = Ketju.id AND Ketju.palsta_id = ? ORDER BY Viesti.aika DESC LIMIT 1");
        stmt2.setInt(1, palsta_id);
        
        ResultSet rs2 = stmt2.executeQuery();
        
        if (!rs2.next()) {
            return "ei viestejä";
        }
        
        String last = rs2.getString("viimeisin");
        
        rs2.close();
        stmt2.close();
        connection2.close();
        
        return last;
    }
}