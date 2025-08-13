/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.shop.dao;

import com.mycompany.shop.model.chi_tiet_hoa_don;
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
public class chi_tiet_hoa_don_dao {
    public db_connect db = new db_connect();
    public Connection connection = db.getConnection();

    public void addChiTietHoaDon(chi_tiet_hoa_don chiTiet) {
        // Thêm chi tiết hóa đơn từ đối tượng
        String sql = "INSERT INTO chi_tiet_hoa_don (id_hoa_don, id_ao, so_luong, don_gia) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, chiTiet.getId_hoa_don());
            ps.setInt(2, chiTiet.getId_ao());
            ps.setInt(3, chiTiet.getSo_luong());
            ps.setInt(4, chiTiet.getDon_gia());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateChiTietHoaDon(int id_chi_tiet_hoa_don, int id_hoa_don, int id_ao, int so_luong, int don_gia) {
        String sql = "UPDATE chi_tiet_hoa_don SET id_hoa_don = ?, id_ao = ?, so_luong = ?, don_gia = ? WHERE id_chi_tiet_hoa_don = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id_hoa_don);
            ps.setInt(2, id_ao);
            ps.setInt(3, so_luong);
            ps.setInt(4, don_gia);
            ps.setInt(5, id_chi_tiet_hoa_don);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteChiTietHoaDon(int id_chi_tiet_hoa_don) {
        String sql = "DELETE FROM chi_tiet_hoa_don WHERE id_chi_tiet_hoa_don = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id_chi_tiet_hoa_don);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public chi_tiet_hoa_don getChiTietHoaDonById(int id_chi_tiet_hoa_don) {
        String sql = "SELECT * FROM chi_tiet_hoa_don WHERE id_chi_tiet_hoa_don = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id_chi_tiet_hoa_don);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                chi_tiet_hoa_don ct = new chi_tiet_hoa_don(
                        rs.getInt("id_chi_tiet_hoa_don"),
                        rs.getInt("id_hoa_don"),
                        rs.getInt("id_ao"),
                        rs.getInt("so_luong"),
                        rs.getInt("don_gia"));
                return ct;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<chi_tiet_hoa_don> getAllChiTietHoaDon() {
        List<chi_tiet_hoa_don> list = new ArrayList<>();
        String sql = "SELECT * FROM chi_tiet_hoa_don";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                chi_tiet_hoa_don ct = new chi_tiet_hoa_don(
                        rs.getInt("id_chi_tiet_hoa_don"),
                        rs.getInt("id_hoa_don"),
                        rs.getInt("id_ao"),
                        rs.getInt("so_luong"),
                        rs.getInt("don_gia"));
                list.add(ct);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<chi_tiet_hoa_don> getChiTietHoaDonByHoaDon(int id_hoa_don) {
        List<chi_tiet_hoa_don> list = new ArrayList<>();
        String sql = "SELECT * FROM chi_tiet_hoa_don WHERE id_hoa_don = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id_hoa_don);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                chi_tiet_hoa_don ct = new chi_tiet_hoa_don(
                        rs.getInt("id_chi_tiet_hoa_don"),
                        rs.getInt("id_hoa_don"),
                        rs.getInt("id_ao"),
                        rs.getInt("so_luong"),
                        rs.getInt("don_gia"));
                list.add(ct);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<chi_tiet_hoa_don> getChiTietHoaDonByAo(int id_ao) {
        List<chi_tiet_hoa_don> list = new ArrayList<>();
        String sql = "SELECT * FROM chi_tiet_hoa_don WHERE id_ao = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id_ao);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                chi_tiet_hoa_don ct = new chi_tiet_hoa_don(
                        rs.getInt("id_chi_tiet_hoa_don"),
                        rs.getInt("id_hoa_don"),
                        rs.getInt("id_ao"),
                        rs.getInt("so_luong"),
                        rs.getInt("don_gia"));
                list.add(ct);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<chi_tiet_hoa_don> getChiTietHoaDonByIdHoaDon(int id_hoa_don) {
        // Lấy chi tiết hóa đơn theo id hóa đơn
        return getChiTietHoaDonByHoaDon(id_hoa_don);
    }

    public void deleteChiTietHoaDonByIdHoaDon(int id_hoa_don) {
        // Xóa tất cả chi tiết hóa đơn theo id hóa đơn
        String sql = "DELETE FROM chi_tiet_hoa_don WHERE id_hoa_don = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id_hoa_don);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
