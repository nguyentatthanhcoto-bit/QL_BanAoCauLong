/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.shop.dao;

import com.mycompany.shop.model.voucher;
import com.mycompany.shop.db.db_connect;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date;

/**
 *
 * @author mailx
 */
public class voucher_dao {
    public db_connect db = new db_connect();
    public Connection connection = db.getConnection();

    public void addVoucher(String ma_voucher, String ten_voucher, int gia_tri_giam, Date ngay_bat_dau,
            Date ngay_ket_thuc, int so_luong, String trang_thai, int id_quan_ly) {
        String sql = "INSERT INTO voucher (ma_voucher, ten_voucher, gia_tri_giam, ngay_bat_dau, ngay_ket_thuc, so_luong, trang_thai, id_quan_ly) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, ma_voucher);
            ps.setString(2, ten_voucher);
            ps.setInt(3, gia_tri_giam);
            ps.setDate(4, ngay_bat_dau);
            ps.setDate(5, ngay_ket_thuc);
            ps.setInt(6, so_luong);
            ps.setString(7, trang_thai);
            ps.setInt(8, id_quan_ly);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateVoucher(int id_voucher, String ma_voucher, String ten_voucher, int gia_tri_giam,
            Date ngay_bat_dau, Date ngay_ket_thuc, int so_luong, String trang_thai, int id_quan_ly) {
        String sql = "UPDATE voucher SET ma_voucher = ?, ten_voucher = ?, gia_tri_giam = ?, ngay_bat_dau = ?, ngay_ket_thuc = ?, so_luong = ?, trang_thai = ?, id_quan_ly = ? WHERE id_voucher = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, ma_voucher);
            ps.setString(2, ten_voucher);
            ps.setInt(3, gia_tri_giam);
            ps.setDate(4, ngay_bat_dau);
            ps.setDate(5, ngay_ket_thuc);
            ps.setInt(6, so_luong);
            ps.setString(7, trang_thai);
            ps.setInt(8, id_quan_ly);
            ps.setInt(9, id_voucher);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteVoucher(int id_voucher) {
        String sql = "DELETE FROM voucher WHERE id_voucher = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id_voucher);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public voucher getVoucherById(int id_voucher) {
        String sql = "SELECT * FROM voucher WHERE id_voucher = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id_voucher);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                voucher v = new voucher(
                        rs.getInt("id_voucher"),
                        rs.getString("ma_voucher"),
                        rs.getString("ten_voucher"),
                        rs.getInt("gia_tri_giam"),
                        rs.getDate("ngay_bat_dau"),
                        rs.getDate("ngay_ket_thuc"),
                        rs.getInt("so_luong"),
                        rs.getString("trang_thai"),
                        rs.getInt("id_quan_ly"));
                return v;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<voucher> getAllVoucher() {
        List<voucher> list = new ArrayList<>();
        String sql = "SELECT * FROM voucher";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                voucher v = new voucher(
                        rs.getInt("id_voucher"),
                        rs.getString("ma_voucher"),
                        rs.getString("ten_voucher"),
                        rs.getInt("gia_tri_giam"),
                        rs.getDate("ngay_bat_dau"),
                        rs.getDate("ngay_ket_thuc"),
                        rs.getInt("so_luong"),
                        rs.getString("trang_thai"),
                        rs.getInt("id_quan_ly"));
                list.add(v);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public voucher getVoucherByMa(String ma_voucher) {
        String sql = "SELECT * FROM voucher WHERE ma_voucher = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, ma_voucher);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                voucher v = new voucher(
                        rs.getInt("id_voucher"),
                        rs.getString("ma_voucher"),
                        rs.getString("ten_voucher"),
                        rs.getInt("gia_tri_giam"),
                        rs.getDate("ngay_bat_dau"),
                        rs.getDate("ngay_ket_thuc"),
                        rs.getInt("so_luong"),
                        rs.getString("trang_thai"),
                        rs.getInt("id_quan_ly"));
                return v;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<voucher> getVoucherByTrangThai(String trang_thai) {
        List<voucher> list = new ArrayList<>();
        String sql = "SELECT * FROM voucher WHERE trang_thai = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, trang_thai);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                voucher v = new voucher(
                        rs.getInt("id_voucher"),
                        rs.getString("ma_voucher"),
                        rs.getString("ten_voucher"),
                        rs.getInt("gia_tri_giam"),
                        rs.getDate("ngay_bat_dau"),
                        rs.getDate("ngay_ket_thuc"),
                        rs.getInt("so_luong"),
                        rs.getString("trang_thai"),
                        rs.getInt("id_quan_ly"));
                list.add(v);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Method kiểm tra mã voucher đã tồn tại
    public boolean checkMaVoucherExists(String ma_voucher) {
        String sql = "SELECT COUNT(*) FROM voucher WHERE ma_voucher = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, ma_voucher);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Method tìm kiếm voucher theo tên
    public List<voucher> searchVoucherByName(String ten_voucher) {
        List<voucher> list = new ArrayList<>();
        String sql = "SELECT * FROM voucher WHERE ten_voucher LIKE ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, "%" + ten_voucher + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                voucher v = new voucher(
                        rs.getInt("id_voucher"),
                        rs.getString("ma_voucher"),
                        rs.getString("ten_voucher"),
                        rs.getInt("gia_tri_giam"),
                        rs.getDate("ngay_bat_dau"),
                        rs.getDate("ngay_ket_thuc"),
                        rs.getInt("so_luong"),
                        rs.getString("trang_thai"),
                        rs.getInt("id_quan_ly"));
                list.add(v);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Method lấy voucher còn hiệu lực (cho hóa đơn)
    public List<voucher> getActiveVouchers() {
        List<voucher> list = new ArrayList<>();
        String sql = "SELECT * FROM voucher WHERE trang_thai = 'Hoạt động' AND so_luong > 0 AND ngay_ket_thuc >= CURDATE()";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                voucher v = new voucher(
                        rs.getInt("id_voucher"),
                        rs.getString("ma_voucher"),
                        rs.getString("ten_voucher"),
                        rs.getInt("gia_tri_giam"),
                        rs.getDate("ngay_bat_dau"),
                        rs.getDate("ngay_ket_thuc"),
                        rs.getInt("so_luong"),
                        rs.getString("trang_thai"),
                        rs.getInt("id_quan_ly"));
                list.add(v);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Method giảm số lượng voucher khi sử dụng
    public boolean useVoucher(String ma_voucher) {
        String sql = "UPDATE voucher SET so_luong = so_luong - 1 WHERE ma_voucher = ? AND so_luong > 0";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, ma_voucher);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Method tính giá trị giảm giá
    public double calculateDiscount(String ma_voucher, double totalAmount) {
        voucher v = getVoucherByMa(ma_voucher);
        if (v != null && v.getTrang_thai().equals("Hoạt động") && v.getSo_luong() > 0) {
            // Kiểm tra ngày hiệu lực
            java.util.Date currentDate = new java.util.Date();
            if (currentDate.after(v.getNgay_bat_dau()) && currentDate.before(v.getNgay_ket_thuc())) {
                // Tính phần trăm giảm giá
                return totalAmount * v.getGia_tri_giam() / 100.0;
            }
        }
        return 0;
    }
}
