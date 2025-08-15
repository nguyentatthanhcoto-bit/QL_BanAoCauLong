/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.shop.dao;

import com.mycompany.shop.model.ao;
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
public class ao_dao {
    public db_connect db = new db_connect();
    public Connection connection = db.getConnection();

    public void addAo(String ma_ao, String ten_ao, int gia, int so_luong, String mo_ta, String image, int id_loai,
            int id_mau_sac,
            int id_size) {
        String sql = "INSERT INTO ao (ma_ao, ten_ao, gia, so_luong, mo_ta, image, id_loai, id_mau_sac, id_size) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, ma_ao);
            ps.setString(2, ten_ao);
            ps.setInt(3, gia);
            ps.setInt(4, so_luong);
            ps.setString(5, mo_ta);
            ps.setString(6, image);
            ps.setInt(7, id_loai);
            ps.setInt(8, id_mau_sac);
            ps.setInt(9, id_size);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateAo(int id_ao, String ma_ao, String ten_ao, int gia, int so_luong, String mo_ta, String image,
            int id_loai,
            int id_mau_sac, int id_size) {
        String sql = "UPDATE ao SET ma_ao = ?, ten_ao = ?, gia = ?, so_luong = ?, mo_ta = ?, image = ?, id_loai = ?, id_mau_sac = ?, id_size = ? WHERE id_ao = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, ma_ao);
            ps.setString(2, ten_ao);
            ps.setInt(3, gia);
            ps.setInt(4, so_luong);
            ps.setString(5, mo_ta);
            ps.setString(6, image);
            ps.setInt(7, id_loai);
            ps.setInt(8, id_mau_sac);
            ps.setInt(9, id_size);
            ps.setInt(10, id_ao);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteAo(int id_ao) {
        String sql = "DELETE FROM ao WHERE id_ao = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id_ao);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ao getAoById(int id_ao) {
        String sql = "SELECT * FROM ao WHERE id_ao = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id_ao);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                ao a = new ao(
                        rs.getInt("id_ao"),
                        rs.getString("ma_ao"),
                        rs.getString("ten_ao"),
                        rs.getInt("gia"),
                        rs.getInt("so_luong"),
                        rs.getString("mo_ta"),
                        rs.getString("image"),
                        rs.getInt("id_loai"),
                        rs.getInt("id_mau_sac"),
                        rs.getInt("id_size"));
                return a;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<ao> getAllAo() {
        List<ao> list = new ArrayList<>();
        String sql = "SELECT * FROM ao";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ao a = new ao(
                        rs.getInt("id_ao"),
                        rs.getString("ma_ao"),
                        rs.getString("ten_ao"),
                        rs.getInt("gia"),
                        rs.getInt("so_luong"),
                        rs.getString("mo_ta"),
                        rs.getString("image"),
                        rs.getInt("id_loai"),
                        rs.getInt("id_mau_sac"),
                        rs.getInt("id_size"));
                list.add(a);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public ao getAoByMa(String ma_ao) {
        String sql = "SELECT * FROM ao WHERE ma_ao = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, ma_ao);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                ao a = new ao(
                        rs.getInt("id_ao"),
                        rs.getString("ma_ao"),
                        rs.getString("ten_ao"),
                        rs.getInt("gia"),
                        rs.getInt("so_luong"),
                        rs.getString("mo_ta"),
                        rs.getString("image"),
                        rs.getInt("id_loai"),
                        rs.getInt("id_mau_sac"),
                        rs.getInt("id_size"));
                return a;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<ao> getAoByLoai(int id_loai) {
        List<ao> list = new ArrayList<>();
        String sql = "SELECT * FROM ao WHERE id_loai = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id_loai);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ao a = new ao(
                        rs.getInt("id_ao"),
                        rs.getString("ma_ao"),
                        rs.getString("ten_ao"),
                        rs.getInt("gia"),
                        rs.getInt("so_luong"),
                        rs.getString("mo_ta"),
                        rs.getString("image"),
                        rs.getInt("id_loai"),
                        rs.getInt("id_mau_sac"),
                        rs.getInt("id_size"));
                list.add(a);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<ao> getAoByMauSac(int id_mau_sac) {
        List<ao> list = new ArrayList<>();
        String sql = "SELECT * FROM ao WHERE id_mau_sac = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id_mau_sac);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ao a = new ao(
                        rs.getInt("id_ao"),
                        rs.getString("ma_ao"),
                        rs.getString("ten_ao"),
                        rs.getInt("gia"),
                        rs.getInt("so_luong"),
                        rs.getString("mo_ta"),
                        rs.getString("image"),
                        rs.getInt("id_loai"),
                        rs.getInt("id_mau_sac"),
                        rs.getInt("id_size"));
                list.add(a);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<ao> getAoBySize(int id_size) {
        List<ao> list = new ArrayList<>();
        String sql = "SELECT * FROM ao WHERE id_size = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id_size);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ao a = new ao(
                        rs.getInt("id_ao"),
                        rs.getString("ma_ao"),
                        rs.getString("ten_ao"),
                        rs.getInt("gia"),
                        rs.getInt("so_luong"),
                        rs.getString("mo_ta"),
                        rs.getString("image"),
                        rs.getInt("id_loai"),
                        rs.getInt("id_mau_sac"),
                        rs.getInt("id_size"));
                list.add(a);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Method tìm kiếm sản phẩm theo tên
    public List<ao> searchAoByName(String ten_ao) {
        List<ao> list = new ArrayList<>();
        String sql = "SELECT * FROM ao WHERE ten_ao LIKE ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, "%" + ten_ao + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ao a = new ao(
                        rs.getInt("id_ao"),
                        rs.getString("ma_ao"),
                        rs.getString("ten_ao"),
                        rs.getInt("gia"),
                        rs.getInt("so_luong"),
                        rs.getString("mo_ta"),
                        rs.getString("image"),
                        rs.getInt("id_loai"),
                        rs.getInt("id_mau_sac"),
                        rs.getInt("id_size"));
                list.add(a);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Method lấy sản phẩm còn hàng (số lượng > 0)
    public List<ao> getAoConHang() {
        List<ao> list = new ArrayList<>();
        String sql = "SELECT * FROM ao WHERE so_luong > 0";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ao a = new ao(
                        rs.getInt("id_ao"),
                        rs.getString("ma_ao"),
                        rs.getString("ten_ao"),
                        rs.getInt("gia"),
                        rs.getInt("so_luong"),
                        rs.getString("mo_ta"),
                        rs.getString("image"),
                        rs.getInt("id_loai"),
                        rs.getInt("id_mau_sac"),
                        rs.getInt("id_size"));
                list.add(a);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Method cập nhật số lượng sản phẩm (giảm khi bán)
    public boolean updateSoLuong(int id_ao, int so_luong_moi) {
        String sql = "UPDATE ao SET so_luong = ? WHERE id_ao = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, so_luong_moi);
            ps.setInt(2, id_ao);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Method giảm số lượng sản phẩm khi bán
    public boolean giamSoLuong(int id_ao, int so_luong_ban) {
        String sql = "UPDATE ao SET so_luong = so_luong - ? WHERE id_ao = ? AND so_luong >= ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, so_luong_ban);
            ps.setInt(2, id_ao);
            ps.setInt(3, so_luong_ban);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Method kiểm tra số lượng có đủ để bán không
    public boolean checkSoLuongDuBan(int id_ao, int so_luong_can_ban) {
        String sql = "SELECT so_luong FROM ao WHERE id_ao = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id_ao);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int so_luong_hien_tai = rs.getInt("so_luong");
                return so_luong_hien_tai >= so_luong_can_ban;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
