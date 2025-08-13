/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.shop.dao;

import com.mycompany.shop.model.loai_ao;
import com.mycompany.shop.db.db_connect;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author mailx
 */
public class loai_ao_dao {
    public db_connect db = new db_connect();
    public Connection connection = db.getConnection();
    
    public void addLoaiAo(String ma_loai, String ten_loai) {
        String sql = "INSERT INTO loai_ao (ma_loai, ten_loai) VALUES (?, ?)";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, ma_loai);
            ps.setString(2, ten_loai);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void updateLoaiAo(int id_loai_ao, String ma_loai, String ten_loai) {
        String sql = "UPDATE loai_ao SET ma_loai = ?, ten_loai = ? WHERE id_loai_ao = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, ma_loai);
            ps.setString(2, ten_loai);
            ps.setInt(3, id_loai_ao);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void deleteLoaiAo(int id_loai_ao) {
        String sql = "DELETE FROM loai_ao WHERE id_loai_ao = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id_loai_ao);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public loai_ao getLoaiAoById(int id_loai_ao) {
        String sql = "SELECT * FROM loai_ao WHERE id_loai_ao = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id_loai_ao);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                loai_ao la = new loai_ao(
                    rs.getInt("id_loai_ao"),
                    rs.getString("ma_loai"),
                    rs.getString("ten_loai")
                );
                return la;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public List<loai_ao> getAllLoaiAo() {
        List<loai_ao> list = new ArrayList<>();
        String sql = "SELECT * FROM loai_ao";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                loai_ao la = new loai_ao(
                    rs.getInt("id_loai_ao"),
                    rs.getString("ma_loai"),
                    rs.getString("ten_loai")
                );
                list.add(la);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public loai_ao getLoaiAoByMa(String ma_loai) {
        String sql = "SELECT * FROM loai_ao WHERE ma_loai = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, ma_loai);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                loai_ao la = new loai_ao(
                    rs.getInt("id_loai_ao"),
                    rs.getString("ma_loai"),
                    rs.getString("ten_loai")
                );
                return la;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
