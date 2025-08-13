/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.shop.dao;

import com.mycompany.shop.model.nhan_vien;
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
public class nhan_vien_dao {
    public db_connect db = new db_connect();
    public Connection connection = db.getConnection();

    public void addNhanVien(String ma_nhan_vien, String ten_nhan_vien, String tai_khoan, String mat_khau,
            String so_dien_thoai, int id_quan_ly) {
        String sql = "INSERT INTO nhan_vien (ma_nhan_vien, ten_nhan_vien, tai_khoan, mat_khau, so_dien_thoai, id_quan_ly) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, ma_nhan_vien);
            ps.setString(2, ten_nhan_vien);
            ps.setString(3, tai_khoan);
            ps.setString(4, mat_khau);
            ps.setString(5, so_dien_thoai);
            ps.setInt(6, id_quan_ly);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateNhanVien(int id_nhan_vien, String ma_nhan_vien, String ten_nhan_vien, String tai_khoan,
            String mat_khau, String so_dien_thoai, int id_quan_ly) {
        String sql = "UPDATE nhan_vien SET ma_nhan_vien = ?, ten_nhan_vien = ?, tai_khoan = ?, mat_khau = ?, so_dien_thoai = ?, id_quan_ly = ? WHERE id_nhan_vien = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, ma_nhan_vien);
            ps.setString(2, ten_nhan_vien);
            ps.setString(3, tai_khoan);
            ps.setString(4, mat_khau);
            ps.setString(5, so_dien_thoai);
            ps.setInt(6, id_quan_ly);
            ps.setInt(7, id_nhan_vien);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteNhanVien(int id_nhan_vien) {
        String sql = "DELETE FROM nhan_vien WHERE id_nhan_vien = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id_nhan_vien);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public nhan_vien getNhanVienById(int id_nhan_vien) {
        String sql = "SELECT * FROM nhan_vien WHERE id_nhan_vien = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id_nhan_vien);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                nhan_vien nv = new nhan_vien(
                        rs.getInt("id_nhan_vien"),
                        rs.getString("ma_nhan_vien"),
                        rs.getString("ten_nhan_vien"),
                        rs.getString("tai_khoan"),
                        rs.getString("mat_khau"),
                        rs.getString("so_dien_thoai"),
                        rs.getInt("id_quan_ly"));
                return nv;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<nhan_vien> getAllNhanVien() {
        List<nhan_vien> list = new ArrayList<>();
        String sql = "SELECT * FROM nhan_vien";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                nhan_vien nv = new nhan_vien(
                        rs.getInt("id_nhan_vien"),
                        rs.getString("ma_nhan_vien"),
                        rs.getString("ten_nhan_vien"),
                        rs.getString("tai_khoan"),
                        rs.getString("mat_khau"),
                        rs.getString("so_dien_thoai"),
                        rs.getInt("id_quan_ly"));
                list.add(nv);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public nhan_vien getNhanVienByMa(String ma_nhan_vien) {
        String sql = "SELECT * FROM nhan_vien WHERE ma_nhan_vien = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, ma_nhan_vien);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                nhan_vien nv = new nhan_vien(
                        rs.getInt("id_nhan_vien"),
                        rs.getString("ma_nhan_vien"),
                        rs.getString("ten_nhan_vien"),
                        rs.getString("tai_khoan"),
                        rs.getString("mat_khau"),
                        rs.getString("so_dien_thoai"),
                        rs.getInt("id_quan_ly"));
                return nv;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public nhan_vien checkLogin(String tai_khoan, String mat_khau) {
        String sql = "SELECT * FROM nhan_vien WHERE tai_khoan = ? AND mat_khau = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, tai_khoan);
            ps.setString(2, mat_khau);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                nhan_vien nv = new nhan_vien(
                        rs.getInt("id_nhan_vien"),
                        rs.getString("ma_nhan_vien"),
                        rs.getString("ten_nhan_vien"),
                        rs.getString("tai_khoan"),
                        rs.getString("mat_khau"),
                        rs.getString("so_dien_thoai"),
                        rs.getInt("id_quan_ly"));
                return nv;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<nhan_vien> getNhanVienByQuanLy(int id_quan_ly) {
        List<nhan_vien> list = new ArrayList<>();
        String sql = "SELECT * FROM nhan_vien WHERE id_quan_ly = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id_quan_ly);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                nhan_vien nv = new nhan_vien(
                        rs.getInt("id_nhan_vien"),
                        rs.getString("ma_nhan_vien"),
                        rs.getString("ten_nhan_vien"),
                        rs.getString("tai_khoan"),
                        rs.getString("mat_khau"),
                        rs.getString("so_dien_thoai"),
                        rs.getInt("id_quan_ly"));
                list.add(nv);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Method kiểm tra tài khoản đã tồn tại
    public boolean checkTaiKhoanExists(String tai_khoan) {
        String sql = "SELECT COUNT(*) FROM nhan_vien WHERE tai_khoan = ?";
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

    // Method kiểm tra mã nhân viên đã tồn tại
    public boolean checkMaNhanVienExists(String ma_nhan_vien) {
        String sql = "SELECT COUNT(*) FROM nhan_vien WHERE ma_nhan_vien = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, ma_nhan_vien);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Method kiểm tra số điện thoại đã tồn tại
    public boolean checkSoDienThoaiExists(String so_dien_thoai) {
        String sql = "SELECT COUNT(*) FROM nhan_vien WHERE so_dien_thoai = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, so_dien_thoai);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Method tìm kiếm nhân viên theo tên
    public List<nhan_vien> searchNhanVienByName(String ten_nhan_vien) {
        List<nhan_vien> list = new ArrayList<>();
        String sql = "SELECT * FROM nhan_vien WHERE ten_nhan_vien LIKE ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, "%" + ten_nhan_vien + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                nhan_vien nv = new nhan_vien(
                        rs.getInt("id_nhan_vien"),
                        rs.getString("ma_nhan_vien"),
                        rs.getString("ten_nhan_vien"),
                        rs.getString("tai_khoan"),
                        rs.getString("mat_khau"),
                        rs.getString("so_dien_thoai"),
                        rs.getInt("id_quan_ly"));
                list.add(nv);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Method đăng nhập cho nhân viên
    public nhan_vien checkLoginNhanVien(String tai_khoan, String mat_khau) {
        String sql = "SELECT * FROM nhan_vien WHERE tai_khoan = ? AND mat_khau = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, tai_khoan);
            ps.setString(2, mat_khau);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                nhan_vien nv = new nhan_vien(
                        rs.getInt("id_nhan_vien"),
                        rs.getString("ma_nhan_vien"),
                        rs.getString("ten_nhan_vien"),
                        rs.getString("tai_khoan"),
                        rs.getString("mat_khau"),
                        rs.getString("so_dien_thoai"),
                        rs.getInt("id_quan_ly"));
                return nv;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Method đổi mật khẩu
    public boolean changePassword(int id_nhan_vien, String mat_khau_moi) {
        String sql = "UPDATE nhan_vien SET mat_khau = ? WHERE id_nhan_vien = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, mat_khau_moi);
            ps.setInt(2, id_nhan_vien);
            int result = ps.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
