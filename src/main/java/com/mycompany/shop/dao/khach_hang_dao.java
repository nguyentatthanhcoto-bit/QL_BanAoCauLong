/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.shop.dao;

import com.mycompany.shop.model.khach_hang;
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
public class khach_hang_dao {
    public db_connect db = new db_connect();
    public Connection connection = db.getConnection();

    public void addKhachHang(String ma_khach_hang, String ho_va_ten, String so_dien_thoai, String dia_chi,
            String gioi_tinh, String trang_thai) {
        String sql = "INSERT INTO khach_hang (ma_khach_hang, ho_va_ten, so_dien_thoai, dia_chi, gioi_tinh, trang_thai) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, ma_khach_hang);
            ps.setString(2, ho_va_ten);
            ps.setString(3, so_dien_thoai);
            ps.setString(4, dia_chi);
            ps.setString(5, gioi_tinh);
            ps.setString(6, trang_thai);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateKhachHang(int id_khach_hang, String ma_khach_hang, String ho_va_ten, String so_dien_thoai,
            String dia_chi, String gioi_tinh, String trang_thai) {
        String sql = "UPDATE khach_hang SET ma_khach_hang = ?, ho_va_ten = ?, so_dien_thoai = ?, dia_chi = ?, gioi_tinh = ?, trang_thai = ? WHERE id_khach_hang = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, ma_khach_hang);
            ps.setString(2, ho_va_ten);
            ps.setString(3, so_dien_thoai);
            ps.setString(4, dia_chi);
            ps.setString(5, gioi_tinh);
            ps.setString(6, trang_thai);
            ps.setInt(7, id_khach_hang);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteKhachHang(int id_khach_hang) {
        String sql = "DELETE FROM khach_hang WHERE id_khach_hang = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id_khach_hang);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public khach_hang getKhachHangById(int id_khach_hang) {
        String sql = "SELECT * FROM khach_hang WHERE id_khach_hang = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id_khach_hang);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                khach_hang kh = new khach_hang();
                kh.setId_khach_hang(rs.getInt("id_khach_hang"));
                kh.setMa_khach_hang(rs.getString("ma_khach_hang"));
                kh.setHo_va_ten(rs.getString("ho_va_ten"));
                kh.setSo_dien_thoai(rs.getString("so_dien_thoai"));
                kh.setDia_chi(rs.getString("dia_chi"));
                kh.setGioi_tinh(rs.getString("gioi_tinh"));
                kh.setTrang_thai(rs.getString("trang_thai"));
                return kh;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<khach_hang> getAllKhachHang() {
        List<khach_hang> list = new ArrayList<>();
        String sql = "SELECT * FROM khach_hang";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                khach_hang kh = new khach_hang();
                kh.setId_khach_hang(rs.getInt("id_khach_hang"));
                kh.setMa_khach_hang(rs.getString("ma_khach_hang"));
                kh.setHo_va_ten(rs.getString("ho_va_ten"));
                kh.setSo_dien_thoai(rs.getString("so_dien_thoai"));
                kh.setDia_chi(rs.getString("dia_chi"));
                kh.setGioi_tinh(rs.getString("gioi_tinh"));
                kh.setTrang_thai(rs.getString("trang_thai"));
                list.add(kh);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public khach_hang getKhachHangByMa(String ma_khach_hang) {
        String sql = "SELECT * FROM khach_hang WHERE ma_khach_hang = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, ma_khach_hang);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                khach_hang kh = new khach_hang();
                kh.setId_khach_hang(rs.getInt("id_khach_hang"));
                kh.setMa_khach_hang(rs.getString("ma_khach_hang"));
                kh.setHo_va_ten(rs.getString("ho_va_ten"));
                kh.setSo_dien_thoai(rs.getString("so_dien_thoai"));
                kh.setDia_chi(rs.getString("dia_chi"));
                kh.setGioi_tinh(rs.getString("gioi_tinh"));
                kh.setTrang_thai(rs.getString("trang_thai"));
                return kh;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Method tìm kiếm khách hàng theo số điện thoại
    public khach_hang getKhachHangBySoDienThoai(String so_dien_thoai) {
        String sql = "SELECT * FROM khach_hang WHERE so_dien_thoai = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, so_dien_thoai);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                khach_hang kh = new khach_hang();
                kh.setId_khach_hang(rs.getInt("id_khach_hang"));
                kh.setMa_khach_hang(rs.getString("ma_khach_hang"));
                kh.setHo_va_ten(rs.getString("ho_va_ten"));
                kh.setSo_dien_thoai(rs.getString("so_dien_thoai"));
                kh.setDia_chi(rs.getString("dia_chi"));
                kh.setGioi_tinh(rs.getString("gioi_tinh"));
                kh.setTrang_thai(rs.getString("trang_thai"));
                return kh;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Method tìm kiếm khách hàng theo tên (LIKE)
    public List<khach_hang> searchKhachHangByName(String ho_va_ten) {
        List<khach_hang> list = new ArrayList<>();
        String sql = "SELECT * FROM khach_hang WHERE ho_va_ten LIKE ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, "%" + ho_va_ten + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                khach_hang kh = new khach_hang();
                kh.setId_khach_hang(rs.getInt("id_khach_hang"));
                kh.setMa_khach_hang(rs.getString("ma_khach_hang"));
                kh.setHo_va_ten(rs.getString("ho_va_ten"));
                kh.setSo_dien_thoai(rs.getString("so_dien_thoai"));
                kh.setDia_chi(rs.getString("dia_chi"));
                kh.setGioi_tinh(rs.getString("gioi_tinh"));
                kh.setTrang_thai(rs.getString("trang_thai"));
                list.add(kh);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Method tìm kiếm khách hàng theo số điện thoại (LIKE)
    public List<khach_hang> searchKhachHangByPhone(String so_dien_thoai) {
        List<khach_hang> list = new ArrayList<>();
        String sql = "SELECT * FROM khach_hang WHERE so_dien_thoai LIKE ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, "%" + so_dien_thoai + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                khach_hang kh = new khach_hang();
                kh.setId_khach_hang(rs.getInt("id_khach_hang"));
                kh.setMa_khach_hang(rs.getString("ma_khach_hang"));
                kh.setHo_va_ten(rs.getString("ho_va_ten"));
                kh.setSo_dien_thoai(rs.getString("so_dien_thoai"));
                kh.setDia_chi(rs.getString("dia_chi"));
                kh.setGioi_tinh(rs.getString("gioi_tinh"));
                kh.setTrang_thai(rs.getString("trang_thai"));
                list.add(kh);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Method kiểm tra số điện thoại đã tồn tại
    public boolean checkSoDienThoaiExists(String so_dien_thoai) {
        String sql = "SELECT COUNT(*) FROM khach_hang WHERE so_dien_thoai = ?";
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
}
