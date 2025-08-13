/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.shop.dao;

import com.mycompany.shop.model.mau_sac;
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
public class mau_sac_dao {
    public db_connect db = new db_connect();
    public Connection connection = db.getConnection();
    
    public void addMauSac(String ma_mau_sac, String ten_mau_sac) {
        String sql = "INSERT INTO mau_sac (ma_mau_sac, ten_mau_sac) VALUES (?, ?)";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, ma_mau_sac);
            ps.setString(2, ten_mau_sac);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void updateMauSac(int id_mau_sac, String ma_mau_sac, String ten_mau_sac) {
        String sql = "UPDATE mau_sac SET ma_mau_sac = ?, ten_mau_sac = ? WHERE id_mau_sac = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, ma_mau_sac);
            ps.setString(2, ten_mau_sac);
            ps.setInt(3, id_mau_sac);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void deleteMauSac(int id_mau_sac) {
        String sql = "DELETE FROM mau_sac WHERE id_mau_sac = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id_mau_sac);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public mau_sac getMauSacById(int id_mau_sac) {
        String sql = "SELECT * FROM mau_sac WHERE id_mau_sac = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id_mau_sac);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                mau_sac ms = new mau_sac(
                    rs.getInt("id_mau_sac"),
                    rs.getString("ma_mau_sac"),
                    rs.getString("ten_mau_sac")
                );
                return ms;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public List<mau_sac> getAllMauSac() {
        List<mau_sac> list = new ArrayList<>();
        String sql = "SELECT * FROM mau_sac";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                mau_sac ms = new mau_sac(
                    rs.getInt("id_mau_sac"),
                    rs.getString("ma_mau_sac"),
                    rs.getString("ten_mau_sac")
                );
                list.add(ms);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public mau_sac getMauSacByMa(String ma_mau_sac) {
        String sql = "SELECT * FROM mau_sac WHERE ma_mau_sac = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, ma_mau_sac);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                mau_sac ms = new mau_sac(
                    rs.getInt("id_mau_sac"),
                    rs.getString("ma_mau_sac"),
                    rs.getString("ten_mau_sac")
                );
                return ms;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
