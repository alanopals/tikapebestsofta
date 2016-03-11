package com.kala;

import java.sql.*;
import java.util.*;

public class PalstaDao implements Dao<Viesti, Integer> {
    
    private Database database;
    
    public PalstaDao(Database database) throws Exception {
        this.database = database;
    }
    
    private List<Viesti> collect(ResultSet rs) throws Exception {
        ArrayList<Viesti> viestit = new ArrayList<>();
        
        while (rs.next()) {
            viestit.add(new Viesti(rs.getInt("id"), rs.getInt("ketju_id"), rs.getString("nimimerkki"), 
            rs.getString("sisalto"), rs.getString("aika")));
        }
        
        return viestit;
    }

    @Override
    public Viesti findOne(Integer key) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public List<Viesti> findAll() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public List<Viesti> findAllIn(int ketju_id) throws SQLException {
        Connection connection = database.getConnection();
        
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Viesti WHERE ketju_id = ?");
        stmt.setInt(1, ketju_id);
        
        ResultSet rs = stmt.executeQuery();
        
        List<Viesti> lista = null;
        
        collect: try {
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
        
        PreparedStatement stmt = connection.prepareStatement("DELETE FROM Viesti WHERE id = ?");
        stmt.setObject(1, key);
        
        stmt.executeUpdate();

        stmt.close();
        connection.close();
    }
    
    @Override
    public void add(Viesti v) throws SQLException {
        Connection connection = database.getConnection();
        
        PreparedStatement stmt = connection.prepareStatement("INSERT INTO Viesti (ketju_id, nimimerkki, sisalto) VALUES(?, ?, ?)");
        stmt.setInt(1, v.getKetju_id());
        stmt.setString(2, v.getNimimerkki());
        stmt.setString(3, v.getSisalto());
        
        stmt.executeUpdate();
        
        stmt.close();
        connection.close();
    }
    
    public int countAllIn(int ketju_id) throws SQLException{
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
}
