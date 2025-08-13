package com.mycompany.shop.dao;

import com.mycompany.shop.db.db_connect;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * DAO class for statistics and reporting
 */
public class thong_ke_dao {
    public db_connect db = new db_connect();
    public Connection connection = db.getConnection();

    // Thống kê tổng đơn hàng
    public int getTongDonHang() {
        String sql = "SELECT COUNT(*) as total FROM hoa_don";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // Thống kê đơn hàng theo ngày
    public int getDonHangTheoNgay(Date ngay) {
        String sql = "SELECT COUNT(*) as total FROM hoa_don WHERE DATE(ngay_tao) = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setDate(1, ngay);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // Thống kê đơn hàng theo tháng
    public int getDonHangTheoThang(int thang, int nam) {
        String sql = "SELECT COUNT(*) as total FROM hoa_don WHERE MONTH(ngay_tao) = ? AND YEAR(ngay_tao) = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, thang);
            ps.setInt(2, nam);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // Thống kê đơn hàng theo năm
    public int getDonHangTheoNam(int nam) {
        String sql = "SELECT COUNT(*) as total FROM hoa_don WHERE YEAR(ngay_tao) = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, nam);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // Thống kê doanh thu theo ngày
    public long getDoanhThuTheoNgay(Date ngay) {
        String sql = "SELECT COALESCE(SUM(tong_tien), 0) as total FROM hoa_don WHERE DATE(ngay_tao) = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setDate(1, ngay);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getLong("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // Thống kê doanh thu theo tháng
    public long getDoanhThuTheoThang(int thang, int nam) {
        String sql = "SELECT COALESCE(SUM(tong_tien), 0) as total FROM hoa_don WHERE MONTH(ngay_tao) = ? AND YEAR(ngay_tao) = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, thang);
            ps.setInt(2, nam);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getLong("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // Thống kê doanh thu theo năm
    public long getDoanhThuTheoNam(int nam) {
        String sql = "SELECT COALESCE(SUM(tong_tien), 0) as total FROM hoa_don WHERE YEAR(ngay_tao) = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, nam);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getLong("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // Thống kê sản phẩm bán chạy nhất
    public List<Map<String, Object>> getTopSanPhamBanChay(int limit) {
        List<Map<String, Object>> result = new ArrayList<>();
        String sql = "SELECT a.ma_ao, a.ten_ao, SUM(ct.so_luong) as total_sold, SUM(ct.so_luong * ct.don_gia) as total_revenue "
                +
                "FROM chi_tiet_hoa_don ct " +
                "JOIN ao a ON ct.id_ao = a.id_ao " +
                "JOIN hoa_don h ON ct.id_hoa_don = h.id_hoa_don " +
                "WHERE h.trang_thai = 1 " +
                "GROUP BY a.id_ao, a.ma_ao, a.ten_ao " +
                "ORDER BY total_sold DESC " +
                "LIMIT ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, limit);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Map<String, Object> item = new HashMap<>();
                item.put("ma_ao", rs.getString("ma_ao"));
                item.put("ten_ao", rs.getString("ten_ao"));
                item.put("so_luong_ban", rs.getInt("total_sold"));
                item.put("doanh_thu", rs.getLong("total_revenue"));
                result.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    // Thống kê doanh thu theo từng ngày trong tháng
    public List<Map<String, Object>> getDoanhThuTheoNgayTrongThang(int thang, int nam) {
        List<Map<String, Object>> result = new ArrayList<>();
        String sql = "SELECT DATE(ngay_tao) as ngay, COALESCE(SUM(tong_tien), 0) as doanh_thu, COUNT(*) as so_don " +
                "FROM hoa_don " +
                "WHERE MONTH(ngay_tao) = ? AND YEAR(ngay_tao) = ? " +
                "GROUP BY DATE(ngay_tao) " +
                "ORDER BY ngay";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, thang);
            ps.setInt(2, nam);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Map<String, Object> item = new HashMap<>();
                item.put("ngay", rs.getDate("ngay"));
                item.put("doanh_thu", rs.getLong("doanh_thu"));
                item.put("so_don", rs.getInt("so_don"));
                result.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    // Thống kê doanh thu theo từng tháng trong năm
    public List<Map<String, Object>> getDoanhThuTheoThangTrongNam(int nam) {
        List<Map<String, Object>> result = new ArrayList<>();
        String sql = "SELECT MONTH(ngay_tao) as thang, COALESCE(SUM(tong_tien), 0) as doanh_thu, COUNT(*) as so_don " +
                "FROM hoa_don " +
                "WHERE YEAR(ngay_tao) = ? " +
                "GROUP BY MONTH(ngay_tao) " +
                "ORDER BY thang";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, nam);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Map<String, Object> item = new HashMap<>();
                item.put("thang", rs.getInt("thang"));
                item.put("doanh_thu", rs.getLong("doanh_thu"));
                item.put("so_don", rs.getInt("so_don"));
                result.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    // Thống kê khách hàng mua nhiều nhất (cả khách cũ và khách vãng lai)
    public List<Map<String, Object>> getTopKhachHang(int limit) {
        List<Map<String, Object>> result = new ArrayList<>();

        // Query UNION để lấy cả khách cũ (từ bảng khach_hang) và khách vãng lai (từ
        // hoa_don)
        String sql = "(" +
        // Khách cũ - lấy từ bảng khach_hang
                "SELECT kh.ho_va_ten as ten_khach, kh.so_dien_thoai as sdt_khach, " +
                "COUNT(*) as so_don, SUM(CAST(hd.tong_tien AS SIGNED)) as tong_chi_tieu " +
                "FROM hoa_don hd " +
                "JOIN khach_hang kh ON hd.id_khach_hang = kh.id_khach_hang " +
                "WHERE hd.id_khach_hang IS NOT NULL " +
                "GROUP BY kh.id_khach_hang, kh.ho_va_ten, kh.so_dien_thoai" +
                ") UNION ALL (" +
                // Khách vãng lai - lấy từ hoa_don
                "SELECT hd.ho_va_ten_khach as ten_khach, hd.so_dien_thoai_khach as sdt_khach, " +
                "COUNT(*) as so_don, SUM(CAST(hd.tong_tien AS SIGNED)) as tong_chi_tieu " +
                "FROM hoa_don hd " +
                "WHERE hd.id_khach_hang IS NULL " +
                "AND hd.ho_va_ten_khach IS NOT NULL AND TRIM(hd.ho_va_ten_khach) != '' " +
                "GROUP BY hd.ho_va_ten_khach, hd.so_dien_thoai_khach" +
                ") ORDER BY tong_chi_tieu DESC LIMIT ?";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, limit);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Map<String, Object> item = new HashMap<>();
                item.put("ten_khach", rs.getString("ten_khach"));
                item.put("sdt_khach", rs.getString("sdt_khach"));
                item.put("so_don", rs.getInt("so_don"));
                item.put("tong_chi_tieu", rs.getLong("tong_chi_tieu"));
                result.add(item);
            }
        } catch (SQLException e) {
            System.err.println("Lỗi trong getTopKhachHang: " + e.getMessage());
            e.printStackTrace();

            // Fallback query nếu UNION không work
            return getTopKhachHangFallback(limit);
        }
        return result;
    }

    // Fallback method nếu query chính không work
    private List<Map<String, Object>> getTopKhachHangFallback(int limit) {
        List<Map<String, Object>> result = new ArrayList<>();
        String sql = "SELECT ho_va_ten_khach, so_dien_thoai_khach, COUNT(*) as so_don, " +
                "SUM(tong_tien) as tong_chi_tieu " +
                "FROM hoa_don " +
                "WHERE ho_va_ten_khach IS NOT NULL AND ho_va_ten_khach <> '' " +
                "GROUP BY ho_va_ten_khach, so_dien_thoai_khach " +
                "ORDER BY tong_chi_tieu DESC " +
                "LIMIT ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, limit);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Map<String, Object> item = new HashMap<>();
                item.put("ten_khach", rs.getString("ho_va_ten_khach"));
                item.put("sdt_khach", rs.getString("so_dien_thoai_khach"));
                item.put("so_don", rs.getInt("so_don"));
                item.put("tong_chi_tieu", rs.getLong("tong_chi_tieu"));
                result.add(item);
            }
        } catch (SQLException e) {
            System.err.println("Lỗi trong getTopKhachHangFallback: " + e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    // Thống kê tồn kho
    public List<Map<String, Object>> getThongKeTonKho() {
        List<Map<String, Object>> result = new ArrayList<>();
        String sql = "SELECT a.ma_ao, a.ten_ao, a.so_luong, " +
                "CASE " +
                "   WHEN a.so_luong = 0 THEN 'Hết hàng' " +
                "   WHEN a.so_luong <= 5 THEN 'Sắp hết' " +
                "   ELSE 'Còn hàng' " +
                "END as trang_thai " +
                "FROM ao a " +
                "ORDER BY a.so_luong ASC";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Map<String, Object> item = new HashMap<>();
                item.put("ma_ao", rs.getString("ma_ao"));
                item.put("ten_ao", rs.getString("ten_ao"));
                item.put("so_luong", rs.getInt("so_luong"));
                item.put("trang_thai", rs.getString("trang_thai"));
                result.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    // Thống kê đơn hàng theo khoảng thời gian
    public int getDonHangTheoKhoangThoiGian(Date ngayBatDau, Date ngayKetThuc) {
        String sql = "SELECT COUNT(*) as total FROM hoa_don WHERE DATE(ngay_tao) BETWEEN ? AND ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setDate(1, ngayBatDau);
            ps.setDate(2, ngayKetThuc);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // Thống kê doanh thu theo khoảng thời gian
    public long getDoanhThuTheoKhoangThoiGian(Date ngayBatDau, Date ngayKetThuc) {
        String sql = "SELECT COALESCE(SUM(tong_tien), 0) as total FROM hoa_don WHERE DATE(ngay_tao) BETWEEN ? AND ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setDate(1, ngayBatDau);
            ps.setDate(2, ngayKetThuc);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getLong("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // Thống kê sản phẩm bán chạy theo khoảng thời gian
    public List<Map<String, Object>> getTopSanPhamBanChayTheoKhoangThoiGian(Date ngayBatDau, Date ngayKetThuc,
            int limit) {
        List<Map<String, Object>> result = new ArrayList<>();
        String sql = "SELECT a.ma_ao, a.ten_ao, SUM(ct.so_luong) as total_sold, SUM(ct.so_luong * ct.don_gia) as total_revenue "
                +
                "FROM chi_tiet_hoa_don ct " +
                "JOIN ao a ON ct.id_ao = a.id_ao " +
                "JOIN hoa_don h ON ct.id_hoa_don = h.id_hoa_don " +
                "WHERE DATE(h.ngay_tao) BETWEEN ? AND ? AND h.trang_thai = 1 " +
                "GROUP BY a.id_ao, a.ma_ao, a.ten_ao " +
                "ORDER BY total_sold DESC " +
                "LIMIT ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setDate(1, ngayBatDau);
            ps.setDate(2, ngayKetThuc);
            ps.setInt(3, limit);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Map<String, Object> item = new HashMap<>();
                item.put("ma_ao", rs.getString("ma_ao"));
                item.put("ten_ao", rs.getString("ten_ao"));
                item.put("so_luong_ban", rs.getInt("total_sold"));
                item.put("doanh_thu", rs.getLong("total_revenue"));
                result.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    // Thống kê khách hàng top theo khoảng thời gian (cả khách cũ và khách vãng lai)
    public List<Map<String, Object>> getTopKhachHangTheoKhoangThoiGian(Date ngayBatDau, Date ngayKetThuc, int limit) {
        List<Map<String, Object>> result = new ArrayList<>();

        // Query UNION để lấy cả khách cũ và khách vãng lai theo khoảng thời gian
        String sql = "(" +
        // Khách cũ - lấy từ bảng khach_hang
                "SELECT kh.ho_va_ten as ten_khach, kh.so_dien_thoai as sdt_khach, " +
                "COUNT(*) as so_don, SUM(CAST(hd.tong_tien AS SIGNED)) as tong_chi_tieu " +
                "FROM hoa_don hd " +
                "JOIN khach_hang kh ON hd.id_khach_hang = kh.id_khach_hang " +
                "WHERE hd.id_khach_hang IS NOT NULL " +
                "AND DATE(hd.ngay_tao) BETWEEN ? AND ? " +
                "GROUP BY kh.id_khach_hang, kh.ho_va_ten, kh.so_dien_thoai" +
                ") UNION ALL (" +
                // Khách vãng lai - lấy từ hoa_don
                "SELECT hd.ho_va_ten_khach as ten_khach, hd.so_dien_thoai_khach as sdt_khach, " +
                "COUNT(*) as so_don, SUM(CAST(hd.tong_tien AS SIGNED)) as tong_chi_tieu " +
                "FROM hoa_don hd " +
                "WHERE hd.id_khach_hang IS NULL " +
                "AND hd.ho_va_ten_khach IS NOT NULL AND TRIM(hd.ho_va_ten_khach) != '' " +
                "AND DATE(hd.ngay_tao) BETWEEN ? AND ? " +
                "GROUP BY hd.ho_va_ten_khach, hd.so_dien_thoai_khach" +
                ") ORDER BY tong_chi_tieu DESC LIMIT ?";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setDate(1, ngayBatDau);
            ps.setDate(2, ngayKetThuc);
            ps.setDate(3, ngayBatDau); // Cho phần khách vãng lai
            ps.setDate(4, ngayKetThuc); // Cho phần khách vãng lai
            ps.setInt(5, limit);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Map<String, Object> item = new HashMap<>();
                item.put("ten_khach", rs.getString("ten_khach"));
                item.put("sdt_khach", rs.getString("sdt_khach"));
                item.put("so_don", rs.getInt("so_don"));
                item.put("tong_chi_tieu", rs.getLong("tong_chi_tieu"));
                result.add(item);
            }
        } catch (SQLException e) {
            System.err.println("Lỗi trong getTopKhachHangTheoKhoangThoiGian: " + e.getMessage());
            e.printStackTrace();

            // Fallback query nếu UNION không work
            return getTopKhachHangTheoKhoangThoiGianFallback(ngayBatDau, ngayKetThuc, limit);
        }
        return result;
    }

    // Fallback method cho khoảng thời gian
    private List<Map<String, Object>> getTopKhachHangTheoKhoangThoiGianFallback(Date ngayBatDau, Date ngayKetThuc,
            int limit) {
        List<Map<String, Object>> result = new ArrayList<>();
        String sql = "SELECT ho_va_ten_khach, so_dien_thoai_khach, COUNT(*) as so_don, " +
                "SUM(tong_tien) as tong_chi_tieu " +
                "FROM hoa_don " +
                "WHERE ho_va_ten_khach IS NOT NULL AND ho_va_ten_khach <> '' " +
                "AND DATE(ngay_tao) BETWEEN ? AND ? " +
                "GROUP BY ho_va_ten_khach, so_dien_thoai_khach " +
                "ORDER BY tong_chi_tieu DESC " +
                "LIMIT ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setDate(1, ngayBatDau);
            ps.setDate(2, ngayKetThuc);
            ps.setInt(3, limit);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Map<String, Object> item = new HashMap<>();
                item.put("ten_khach", rs.getString("ho_va_ten_khach"));
                item.put("sdt_khach", rs.getString("so_dien_thoai_khach"));
                item.put("so_don", rs.getInt("so_don"));
                item.put("tong_chi_tieu", rs.getLong("tong_chi_tieu"));
                result.add(item);
            }
        } catch (SQLException e) {
            System.err.println("Lỗi trong getTopKhachHangTheoKhoangThoiGianFallback: " + e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    // Method test đơn giản để debug
    public void testKhachHangData() {
        System.out.println("=== TEST KHÁCH HÀNG DATA ===");

        // Test 1: Đếm tổng số hóa đơn
        try {
            String sql1 = "SELECT COUNT(*) as total FROM hoa_don";
            PreparedStatement ps1 = connection.prepareStatement(sql1);
            ResultSet rs1 = ps1.executeQuery();
            if (rs1.next()) {
                System.out.println("Tổng số hóa đơn: " + rs1.getInt("total"));
            }
        } catch (SQLException e) {
            System.err.println("Lỗi test 1: " + e.getMessage());
        }

        // Test 2: Đếm hóa đơn có tên khách hàng (khách vãng lai)
        try {
            String sql2 = "SELECT COUNT(*) as total FROM hoa_don WHERE ho_va_ten_khach IS NOT NULL";
            PreparedStatement ps2 = connection.prepareStatement(sql2);
            ResultSet rs2 = ps2.executeQuery();
            if (rs2.next()) {
                System.out.println("Hóa đơn khách vãng lai (có tên khách): " + rs2.getInt("total"));
            }
        } catch (SQLException e) {
            System.err.println("Lỗi test 2: " + e.getMessage());
        }

        // Test 3: Đếm hóa đơn có id_khach_hang (khách cũ)
        try {
            String sql3 = "SELECT COUNT(*) as total FROM hoa_don WHERE id_khach_hang IS NOT NULL";
            PreparedStatement ps3 = connection.prepareStatement(sql3);
            ResultSet rs3 = ps3.executeQuery();
            if (rs3.next()) {
                System.out.println("Hóa đơn khách cũ (có id_khach_hang): " + rs3.getInt("total"));
            }
        } catch (SQLException e) {
            System.err.println("Lỗi test 3: " + e.getMessage());
        }

        // Test 4: Lấy 5 hóa đơn khách cũ với thông tin từ bảng khach_hang
        try {
            String sql4 = "SELECT kh.ho_va_ten, kh.so_dien_thoai, hd.tong_tien, hd.ma_hoa_don " +
                    "FROM hoa_don hd " +
                    "JOIN khach_hang kh ON hd.id_khach_hang = kh.id_khach_hang " +
                    "WHERE hd.id_khach_hang IS NOT NULL LIMIT 5";
            PreparedStatement ps4 = connection.prepareStatement(sql4);
            ResultSet rs4 = ps4.executeQuery();
            System.out.println("--- Khách cũ (từ bảng khach_hang) ---");
            while (rs4.next()) {
                System.out.println("Khách cũ: " + rs4.getString("ho_va_ten") +
                        " - SĐT: " + rs4.getString("so_dien_thoai") +
                        " - Tổng tiền: " + rs4.getString("tong_tien") +
                        " - Mã HĐ: " + rs4.getString("ma_hoa_don"));
            }
        } catch (SQLException e) {
            System.err.println("Lỗi test 4: " + e.getMessage());
        }

        // Test 5: Lấy 5 hóa đơn khách vãng lai
        try {
            String sql5 = "SELECT ho_va_ten_khach, so_dien_thoai_khach, tong_tien, ma_hoa_don " +
                    "FROM hoa_don WHERE ho_va_ten_khach IS NOT NULL LIMIT 5";
            PreparedStatement ps5 = connection.prepareStatement(sql5);
            ResultSet rs5 = ps5.executeQuery();
            System.out.println("--- Khách vãng lai (từ hoa_don) ---");
            while (rs5.next()) {
                System.out.println("Khách vãng lai: " + rs5.getString("ho_va_ten_khach") +
                        " - SĐT: " + rs5.getString("so_dien_thoai_khach") +
                        " - Tổng tiền: " + rs5.getString("tong_tien") +
                        " - Mã HĐ: " + rs5.getString("ma_hoa_don"));
            }
        } catch (SQLException e) {
            System.err.println("Lỗi test 5: " + e.getMessage());
        }
    }
}
