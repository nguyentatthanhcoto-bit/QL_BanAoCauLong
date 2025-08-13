/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.shop.dao;
import com.mycompany.shop.model.size;
import com.mycompany.shop.db.db_connect;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;
/**
 *
 * @author mailx
 */
public class size_dao {
    public db_connect db = new db_connect();
    public Connection connection = db.getConnection();
    
    public void addSize(String ma_size, String ten_size){
        String sql = "INSERT INTO size (ma_size, ten_size) VALUES (?, ?)";
        try{
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, ma_size);
            ps.setString(2, ten_size);
            ps.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    public void updateSize(int id_size, String ma_size, String ten_size){
        String sql = "UPDATE size SET ma_size = ?, ten_size = ? WHERE id_size = ?";
        try{
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, ma_size);
            ps.setString(2, ten_size);
            ps.setInt(3, id_size);
            ps.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    public void deleteSize(int id_size){
        String sql = "DELETE FROM size WHERE id_size = ?";
        try{
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id_size);
            ps.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    public size getSizeById(int id_size){
        String sql = "SELECT * FROM size WHERE id_size = ?";
        try{
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id_size);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                size s = new size();
                s.setId_size(rs.getInt("id_size"));
                s.setMa_size(rs.getString("ma_size"));
                s.setTen_size(rs.getString("ten_size"));
                return s;
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }
    public List<size> getAllSize(){
        List<size> list = new ArrayList<>();
        String sql = "SELECT * FROM size";
        try{
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                size s = new size();
                s.setId_size(rs.getInt("id_size"));
                s.setMa_size(rs.getString("ma_size"));
                s.setTen_size(rs.getString("ten_size"));
                list.add(s);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return list;
    }
}
