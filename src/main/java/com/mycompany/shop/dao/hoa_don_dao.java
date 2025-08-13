/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.shop.dao;

import com.mycompany.shop.model.hoa_don;
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
public class hoa_don_dao {
    public db_connect db = new db_connect();
    public Connection connection = db.getConnection();

    public void addHoaDon(hoa_don hd) {
        // Thêm hóa đơn từ đối tượng
        String sql = "INSERT INTO hoa_don (ma_hoa_don, ngay_tao, tong_tien, id_nhan_vien, id_khach_hang, id_voucher, ho_va_ten_khach, so_dien_thoai_khach, dia_chi_khach, trang_thai) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, hd.getMa_hoa_don());
            ps.setDate(2, hd.getNgay_tao());
            ps.setInt(3, hd.getTong_tien());
            ps.setInt(4, hd.getId_nhan_vien());

            if (hd.getId_khach_hang() == 0) {
                ps.setNull(5, java.sql.Types.INTEGER);
            } else {
                ps.setInt(5, hd.getId_khach_hang());
            }

            if (hd.getId_voucher() == 0) {
                ps.setNull(6, java.sql.Types.INTEGER);
            } else {
                ps.setInt(6, hd.getId_voucher());
            }

            ps.setString(7, hd.getHo_va_ten_khach());
            ps.setString(8, hd.getSo_dien_thoai_khach());
            ps.setString(9, hd.getDia_chi_khach());
            ps.setInt(10, hd.getTrang_thai());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateHoaDon(hoa_don hd) {
        // Cập nhật hóa đơn từ đối tượng
        String sql = "UPDATE hoa_don SET ma_hoa_don = ?, ngay_tao = ?, tong_tien = ?, id_nhan_vien = ?, id_khach_hang = ?, id_voucher = ?, ho_va_ten_khach = ?, so_dien_thoai_khach = ?, dia_chi_khach = ?, trang_thai = ? WHERE id_hoa_don = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, hd.getMa_hoa_don());
            ps.setDate(2, hd.getNgay_tao());
            ps.setInt(3, hd.getTong_tien());
            ps.setInt(4, hd.getId_nhan_vien());

            if (hd.getId_khach_hang() == 0) {
                ps.setNull(5, java.sql.Types.INTEGER);
            } else {
                ps.setInt(5, hd.getId_khach_hang());
            }

            if (hd.getId_voucher() == 0) {
                ps.setNull(6, java.sql.Types.INTEGER);
            } else {
                ps.setInt(6, hd.getId_voucher());
            }

            ps.setString(7, hd.getHo_va_ten_khach());
            ps.setString(8, hd.getSo_dien_thoai_khach());
            ps.setString(9, hd.getDia_chi_khach());
            ps.setInt(10, hd.getTrang_thai());
            ps.setInt(11, hd.getId_hoa_don());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteHoaDon(int id_hoa_don) {
        String sql = "DELETE FROM hoa_don WHERE id_hoa_don = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id_hoa_don);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public hoa_don getHoaDonById(int id_hoa_don) {
        String sql = "SELECT * FROM hoa_don WHERE id_hoa_don = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id_hoa_don);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                hoa_don hd = new hoa_don(
                        rs.getInt("id_hoa_don"),
                        rs.getString("ma_hoa_don"),
                        rs.getDate("ngay_tao"),
                        rs.getInt("tong_tien"),
                        rs.getInt("id_nhan_vien"),
                        rs.getInt("id_khach_hang"),
                        rs.getInt("id_voucher"),
                        rs.getString("ho_va_ten_khach"),
                        rs.getString("so_dien_thoai_khach"),
                        rs.getString("dia_chi_khach"),
                        rs.getInt("trang_thai"));
                return hd;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<hoa_don> getAllHoaDon() {
        List<hoa_don> list = new ArrayList<>();
        String sql = "SELECT * FROM hoa_don ORDER BY ngay_tao DESC";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                hoa_don hd = new hoa_don(
                        rs.getInt("id_hoa_don"),
                        rs.getString("ma_hoa_don"),
                        rs.getDate("ngay_tao"),
                        rs.getInt("tong_tien"),
                        rs.getInt("id_nhan_vien"),
                        rs.getInt("id_khach_hang"),
                        rs.getInt("id_voucher"),
                        rs.getString("ho_va_ten_khach"),
                        rs.getString("so_dien_thoai_khach"),
                        rs.getString("dia_chi_khach"),
                        rs.getInt("trang_thai"));
                list.add(hd);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Search invoices by customer name or employee name
     */
    public List<hoa_don> searchHoaDon(String searchTerm) {
        List<hoa_don> list = new ArrayList<>();
        String sql = """
                SELECT h.*,
                       COALESCE(kh.ho_va_ten, h.ho_va_ten_khach) as ten_khach_hang,
                       nv.ten_nhan_vien as ten_nhan_vien
                FROM hoa_don h
                LEFT JOIN khach_hang kh ON h.id_khach_hang = kh.id_khach_hang
                LEFT JOIN nhan_vien nv ON h.id_nhan_vien = nv.id_nhan_vien
                WHERE LOWER(COALESCE(kh.ho_va_ten, h.ho_va_ten_khach)) LIKE LOWER(?)
                   OR LOWER(nv.ten_nhan_vien) LIKE LOWER(?)
                   OR LOWER(h.ma_hoa_don) LIKE LOWER(?)
                ORDER BY h.ngay_tao DESC
                """;

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            String searchPattern = "%" + searchTerm + "%";
            ps.setString(1, searchPattern);
            ps.setString(2, searchPattern);
            ps.setString(3, searchPattern);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                hoa_don hd = new hoa_don(
                        rs.getInt("id_hoa_don"),
                        rs.getString("ma_hoa_don"),
                        rs.getDate("ngay_tao"),
                        rs.getInt("tong_tien"),
                        rs.getInt("id_nhan_vien"),
                        rs.getInt("id_khach_hang"),
                        rs.getInt("id_voucher"),
                        rs.getString("ho_va_ten_khach"),
                        rs.getString("so_dien_thoai_khach"),
                        rs.getString("dia_chi_khach"),
                        rs.getInt("trang_thai"));
                list.add(hd);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public hoa_don getHoaDonByMa(String ma_hoa_don) {
        String sql = "SELECT * FROM hoa_don WHERE ma_hoa_don = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, ma_hoa_don);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                hoa_don hd = new hoa_don(
                        rs.getInt("id_hoa_don"),
                        rs.getString("ma_hoa_don"),
                        rs.getDate("ngay_tao"),
                        rs.getInt("tong_tien"),
                        rs.getInt("id_nhan_vien"),
                        rs.getInt("id_khach_hang"),
                        rs.getInt("id_voucher"),
                        rs.getString("ho_va_ten_khach"),
                        rs.getString("so_dien_thoai_khach"),
                        rs.getString("dia_chi_khach"),
                        rs.getInt("trang_thai"));
                return hd;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<hoa_don> getHoaDonByNhanVien(int id_nhan_vien) {
        List<hoa_don> list = new ArrayList<>();
        String sql = "SELECT * FROM hoa_don WHERE id_nhan_vien = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id_nhan_vien);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                hoa_don hd = new hoa_don(
                        rs.getInt("id_hoa_don"),
                        rs.getString("ma_hoa_don"),
                        rs.getDate("ngay_tao"),
                        rs.getInt("tong_tien"),
                        rs.getInt("id_nhan_vien"),
                        rs.getInt("id_khach_hang"),
                        rs.getInt("id_voucher"),
                        rs.getString("ho_va_ten_khach"),
                        rs.getString("so_dien_thoai_khach"),
                        rs.getString("dia_chi_khach"),
                        rs.getInt("trang_thai"));
                list.add(hd);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<hoa_don> getHoaDonByKhachHang(int id_khach_hang) {
        List<hoa_don> list = new ArrayList<>();
        String sql = "SELECT * FROM hoa_don WHERE id_khach_hang = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id_khach_hang);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                hoa_don hd = new hoa_don(
                        rs.getInt("id_hoa_don"),
                        rs.getString("ma_hoa_don"),
                        rs.getDate("ngay_tao"),
                        rs.getInt("tong_tien"),
                        rs.getInt("id_nhan_vien"),
                        rs.getInt("id_khach_hang"),
                        rs.getInt("id_voucher"),
                        rs.getString("ho_va_ten_khach"),
                        rs.getString("so_dien_thoai_khach"),
                        rs.getString("dia_chi_khach"),
                        rs.getInt("trang_thai"));
                list.add(hd);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Method để tính giảm giá từ voucher
    public int getGiamGiaFromVoucher(int id_voucher) {
        if (id_voucher == 0) {
            return 0;
        }

        String sql = "SELECT gia_tri_giam FROM voucher WHERE id_voucher = ? AND trang_thai = 'active' AND so_luong > 0 AND ngay_bat_dau <= NOW() AND ngay_ket_thuc >= NOW()";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id_voucher);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("gia_tri_giam");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // Method để lấy thông tin voucher
    public String getVoucherInfo(int id_voucher) {
        if (id_voucher == 0) {
            return "Không có voucher";
        }

        String sql = "SELECT ten_voucher, gia_tri_giam FROM voucher WHERE id_voucher = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id_voucher);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("ten_voucher") + " (-" + rs.getInt("gia_tri_giam") + " VND)";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "Voucher không hợp lệ";
    }
}
