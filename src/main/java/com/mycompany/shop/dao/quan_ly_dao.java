/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.shop.dao;

import com.mycompany.shop.model.quan_ly;
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
public class quan_ly_dao {
    public db_connect db = new db_connect();
    public Connection connection = db.getConnection();

    public void addQuanLy(String ma_quan_ly, String ten_quan_ly, String tai_khoan, String mat_khau) {
        String sql = "INSERT INTO quan_ly (ma_quan_ly, ten_quan_ly, tai_khoan, mat_khau) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, ma_quan_ly);
            ps.setString(2, ten_quan_ly);
            ps.setString(3, tai_khoan);
            ps.setString(4, mat_khau);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateQuanLy(int id_quan_ly, String ma_quan_ly, String ten_quan_ly, String tai_khoan, String mat_khau) {
        String sql = "UPDATE quan_ly SET ma_quan_ly = ?, ten_quan_ly = ?, tai_khoan = ?, mat_khau = ? WHERE id_quan_ly = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, ma_quan_ly);
            ps.setString(2, ten_quan_ly);
            ps.setString(3, tai_khoan);
            ps.setString(4, mat_khau);
            ps.setInt(5, id_quan_ly);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteQuanLy(int id_quan_ly) {
        String sql = "DELETE FROM quan_ly WHERE id_quan_ly = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id_quan_ly);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public quan_ly getQuanLyById(int id_quan_ly) {
        String sql = "SELECT * FROM quan_ly WHERE id_quan_ly = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id_quan_ly);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                quan_ly ql = new quan_ly(
                        rs.getInt("id_quan_ly"),
                        rs.getString("ma_quan_ly"),
                        rs.getString("ten_quan_ly"),
                        rs.getString("tai_khoan"),
                        rs.getString("mat_khau"));
                return ql;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<quan_ly> getAllQuanLy() {
        List<quan_ly> list = new ArrayList<>();
        String sql = "SELECT * FROM quan_ly";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                quan_ly ql = new quan_ly(
                        rs.getInt("id_quan_ly"),
                        rs.getString("ma_quan_ly"),
                        rs.getString("ten_quan_ly"),
                        rs.getString("tai_khoan"),
                        rs.getString("mat_khau"));
                list.add(ql);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public quan_ly getQuanLyByTaiKhoan(String tai_khoan) {
        String sql = "SELECT * FROM quan_ly WHERE tai_khoan = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, tai_khoan);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                quan_ly ql = new quan_ly(
                        rs.getInt("id_quan_ly"),
                        rs.getString("ma_quan_ly"),
                        rs.getString("ten_quan_ly"),
                        rs.getString("tai_khoan"),
                        rs.getString("mat_khau"));
                return ql;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public quan_ly checkLogin(String tai_khoan, String mat_khau) {
        String sql = "SELECT * FROM quan_ly WHERE tai_khoan = ? AND mat_khau = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, tai_khoan);
            ps.setString(2, mat_khau);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                quan_ly ql = new quan_ly(
                        rs.getInt("id_quan_ly"),
                        rs.getString("ma_quan_ly"),
                        rs.getString("ten_quan_ly"),
                        rs.getString("tai_khoan"),
                        rs.getString("mat_khau"));
                return ql;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Method kiểm tra tài khoản đã tồn tại
    public boolean checkTaiKhoanExists(String tai_khoan) {
        String sql = "SELECT COUNT(*) FROM quan_ly WHERE tai_khoan = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, tai_khoan);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Method kiểm tra mã quản lý đã tồn tại
    public boolean checkMaQuanLyExists(String ma_quan_ly) {
        String sql = "SELECT COUNT(*) FROM quan_ly WHERE ma_quan_ly = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, ma_quan_ly);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Method tìm kiếm quản lý theo tên
    public List<quan_ly> searchQuanLyByName(String ten_quan_ly) {
        List<quan_ly> list = new ArrayList<>();
        String sql = "SELECT * FROM quan_ly WHERE ten_quan_ly LIKE ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, "%" + ten_quan_ly + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                quan_ly ql = new quan_ly(
                        rs.getInt("id_quan_ly"),
                        rs.getString("ma_quan_ly"),
                        rs.getString("ten_quan_ly"),
                        rs.getString("tai_khoan"),
                        rs.getString("mat_khau"));
                list.add(ql);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Method đổi mật khẩu
    public boolean changePassword(int id_quan_ly, String mat_khau_moi) {
        String sql = "UPDATE quan_ly SET mat_khau = ? WHERE id_quan_ly = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, mat_khau_moi);
            ps.setInt(2, id_quan_ly);
            int result = ps.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Method lấy quản lý theo mã
    public quan_ly getQuanLyByMa(String ma_quan_ly) {
        String sql = "SELECT * FROM quan_ly WHERE ma_quan_ly = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, ma_quan_ly);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                quan_ly ql = new quan_ly(
                        rs.getInt("id_quan_ly"),
                        rs.getString("ma_quan_ly"),
                        rs.getString("ten_quan_ly"),
                        rs.getString("tai_khoan"),
                        rs.getString("mat_khau"));
                return ql;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
