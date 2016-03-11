package com.kala.dao;

import com.kala.Database;
import com.kala.Viesti;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ViestiDao implements Dao<Viesti, Integer> {
    
    private Database database;
    
    public ViestiDao(Database database) throws Exception {
        this.database = database;
    }
    
    private List<Viesti> collect(ResultSet rs) throws Exception {
        ArrayList<Viesti> collection = new ArrayList<>();
        
        while (rs.next()) {
            collection.add(new Viesti(rs.getInt("id"), rs.getInt("ketju_id"), rs.getString("nimimerkki"), 
            rs.getString("sisalto"), rs.getString("aika")));
        }
        
        return collection;
    }
    
    @Override
    public Viesti findOne(Integer key) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public List<Viesti> findAll() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public List<Viesti> findAll(int ketju_id, int sivu) throws SQLException {
        Connection connection = database.getConnection();
        
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Viesti WHERE ketju_id = ? "
                + "DESC LIMIT 10 OFFSET ?");
        stmt.setInt(1, ketju_id);
        stmt.setInt(2, (sivu - 1) * 10);
        
        ResultSet rs = stmt.executeQuery();
        
        List<Viesti> collection = null;
        
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
    public void add(Viesti v) throws SQLException {
        Connection connection = database.getConnection();
        
        PreparedStatement stmt = connection.prepareStatement("INSERT INTO Viesti "
                + "(ketju_id, nimimerkki, sisalto, aika) VALUES (?, ?, ?, DATETIME('now'))");
        stmt.setInt(1, v.getKetju_id());
        stmt.setString(2, v.getNimimerkki());
        stmt.setString(3, v.getSisalto());
        
        stmt.executeUpdate();
        
        stmt.close();
        connection.close();
    }
    
    @Override
    public void delete(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        
        PreparedStatement stmt = connection.prepareStatement("DELETE FROM Viesti WHERE id = ?");
        stmt.setInt(1, key);
        
        stmt.executeUpdate();
        
        stmt.close();
        connection.close();
    }
}