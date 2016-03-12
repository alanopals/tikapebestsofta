package com.kala.dao;

import com.kala.Database;
import com.kala.Ketju;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class KetjuDao implements Dao<Ketju, Integer> {
    
    private Database database;
    
    public KetjuDao(Database database) throws Exception {
        this.database = database;
    }
    
    private List<Ketju> collect(ResultSet rs) throws Exception {
        ArrayList<Ketju> collection = new ArrayList<>();
        
        while (rs.next()) {
            Ketju k = new Ketju(rs.getInt("id"), rs.getInt("palsta_id"), rs.getString("otsikko"));
            k.setKoko(getKoko(k.getId()));
            k.setViimeisin(getViimeisin(k.getId()));
            
            collection.add(k);
        }
        
        return collection;
    }
    
    @Override
    public Ketju findOne(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt;
        
        if (key == -1) {
            stmt = connection.prepareStatement("SELECT * FROM Ketju ORDER BY id DESC LIMIT 1");
        } else {
            stmt = connection.prepareStatement("SELECT * FROM Ketju WHERE id = ?");
            stmt.setInt(1, key);
        }
        
        ResultSet rs = stmt.executeQuery();
        
        if (!rs.next()) {
            return null;
        }
        
        Ketju k = new Ketju(rs.getInt("id"), rs.getInt("palsta_id"), rs.getString("otsikko"));
        k.setKoko(getKoko(k.getId()));
        k.setViimeisin(getViimeisin(k.getId()));
        
        rs.close();
        stmt.close();
        connection.close();
        
        return k;
    }
    
    @Override
    public List<Ketju> findAll() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public List<Ketju> findAll(int palsta_id, int sivu) throws SQLException {
        Connection connection = database.getConnection();
        
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Ketju WHERE "
                + "Ketju.palsta_id = ? ORDER BY (SELECT Viesti.aika FROM Viesti WHERE Viesti.ketju_id = Ketju.id "
                + "ORDER BY Viesti.aika DESC LIMIT 1) DESC LIMIT 10 OFFSET ?");
        stmt.setInt(1, palsta_id);
        stmt.setInt(2, (sivu - 1) * 10);
        
        ResultSet rs = stmt.executeQuery();
        
        List<Ketju> collection = null;
        
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
    public void add(Ketju k) throws SQLException {
        Connection connection = database.getConnection();
        
        PreparedStatement stmt = connection.prepareStatement("INSERT INTO Ketju (palsta_id, otsikko) VALUES (?, ?)");
        stmt.setInt(1, k.getPalsta_id());
        stmt.setString(2, k.getOtsikko());
        
        stmt.executeUpdate();
        
        stmt.close();
        connection.close();
    }
    
    @Override
    public void delete(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        
        PreparedStatement stmt = connection.prepareStatement("DELETE FROM Ketju WHERE id = ?");
        stmt.setInt(1, key);
        
        stmt.executeUpdate();
        
        stmt.close();
        connection.close();
    }
    
    public int getKoko(Integer palsta_id) throws SQLException {
        Connection connection1 = database.getConnection();
        
        PreparedStatement stmt1 = connection1.prepareStatement("SELECT COUNT(*) AS koko FROM Viesti "
                + "WHERE Viesti.ketju_id = ?");
        stmt1.setInt(1, palsta_id);
        
        ResultSet rs1 = stmt1.executeQuery();
        
        if (!rs1.next()) {
            return 0;
        }
        
        int size = rs1.getInt("koko");
        
        rs1.close();
        stmt1.close();
        connection1.close();
        
        return size;
    }
    
    public String getViimeisin(int palsta_id) throws SQLException {
        Connection connection2 = database.getConnection();
        
        PreparedStatement stmt2 = connection2.prepareStatement("SELECT Viesti.aika AS viimeisin FROM Viesti WHERE "
                + "Viesti.ketju_id = ? ORDER BY Viesti.aika DESC LIMIT 1");
        stmt2.setInt(1, palsta_id);
        
        ResultSet rs2 = stmt2.executeQuery();
        
        if (!rs2.next()) {
            return "-";
        }
        
        String latest = rs2.getString("viimeisin");
        
        rs2.close();
        stmt2.close();
        connection2.close();
        
        return latest;
    }
}