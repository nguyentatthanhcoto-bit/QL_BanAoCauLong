package com.mycompany.shop.test;

import com.mycompany.shop.dao.thong_ke_dao;
import com.mycompany.shop.dao.hoa_don_dao;
import com.mycompany.shop.model.hoa_don;
import com.mycompany.shop.db.db_connect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Test class để debug vấn đề Khách hàng VIP không hiển thị
 */
public class ThongKeDebugTest {
    
    public static void main(String[] args) {
        System.out.println("=== THỐNG KÊ DEBUG TEST ===");
        
        // Test 1: Kiểm tra dữ liệu hóa đơn
        testHoaDonData();
        
        // Test 2: Kiểm tra query khách hàng VIP
        testKhachHangVIPQuery();
        
        // Test 3: Kiểm tra data type của tong_tien
        testTongTienDataType();
        
        // Test 4: Test query đơn giản
        testSimpleQuery();
    }
    
    private static void testHoaDonData() {
        System.out.println("\n--- TEST 1: Kiểm tra dữ liệu hóa đơn ---");
        try {
            hoa_don_dao hoaDonDAO = new hoa_don_dao();
            List<hoa_don> danhSachHoaDon = hoaDonDAO.getAllHoaDon();
            
            System.out.println("Tổng số hóa đơn: " + danhSachHoaDon.size());
            
            int countWithCustomerName = 0;
            for (hoa_don hd : danhSachHoaDon) {
                if (hd.getHo_va_ten_khach() != null && !hd.getHo_va_ten_khach().trim().isEmpty()) {
                    countWithCustomerName++;
                    System.out.println("Hóa đơn " + hd.getMa_hoa_don() + 
                                     " - Khách: " + hd.getHo_va_ten_khach() + 
                                     " - SĐT: " + hd.getSo_dien_thoai_khach() +
                                     " - Tổng tiền: " + hd.getTong_tien());
                }
            }
            
            System.out.println("Số hóa đơn có tên khách hàng: " + countWithCustomerName);
            
        } catch (Exception e) {
            System.err.println("Lỗi khi test dữ liệu hóa đơn: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private static void testKhachHangVIPQuery() {
        System.out.println("\n--- TEST 2: Kiểm tra query khách hàng VIP ---");
        try {
            thong_ke_dao thongKeDAO = new thong_ke_dao();
            List<Map<String, Object>> result = thongKeDAO.getTopKhachHang(10);
            
            System.out.println("Số khách hàng VIP tìm được: " + result.size());
            
            for (Map<String, Object> item : result) {
                System.out.println("Khách: " + item.get("ten_khach") + 
                                 " - SĐT: " + item.get("sdt_khach") +
                                 " - Số đơn: " + item.get("so_don") +
                                 " - Tổng chi tiêu: " + item.get("tong_chi_tieu"));
            }
            
        } catch (Exception e) {
            System.err.println("Lỗi khi test query khách hàng VIP: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private static void testTongTienDataType() {
        System.out.println("\n--- TEST 3: Kiểm tra data type của tong_tien ---");
        try {
            db_connect db = new db_connect();
            Connection connection = db.getConnection();
            
            String sql = "SELECT ho_va_ten_khach, so_dien_thoai_khach, tong_tien, " +
                        "TYPEOF(tong_tien) as data_type FROM hoa_don " +
                        "WHERE ho_va_ten_khach IS NOT NULL AND ho_va_ten_khach != '' " +
                        "LIMIT 5";
            
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                System.out.println("Khách: " + rs.getString("ho_va_ten_khach") +
                                 " - Tổng tiền: " + rs.getString("tong_tien") +
                                 " - Data type: " + rs.getString("data_type"));
            }
            
        } catch (SQLException e) {
            System.err.println("Lỗi khi test data type: " + e.getMessage());
            // Thử query khác nếu TYPEOF không work
            testTongTienAlternative();
        }
    }
    
    private static void testTongTienAlternative() {
        System.out.println("\n--- TEST 3b: Kiểm tra tong_tien alternative ---");
        try {
            db_connect db = new db_connect();
            Connection connection = db.getConnection();
            
            String sql = "SELECT ho_va_ten_khach, so_dien_thoai_khach, tong_tien " +
                        "FROM hoa_don " +
                        "WHERE ho_va_ten_khach IS NOT NULL AND ho_va_ten_khach != '' " +
                        "LIMIT 5";
            
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                String tongTienStr = rs.getString("tong_tien");
                try {
                    int tongTienInt = rs.getInt("tong_tien");
                    System.out.println("Khách: " + rs.getString("ho_va_ten_khach") +
                                     " - Tổng tiền (String): " + tongTienStr +
                                     " - Tổng tiền (Int): " + tongTienInt);
                } catch (Exception e) {
                    System.out.println("Khách: " + rs.getString("ho_va_ten_khach") +
                                     " - Tổng tiền (String): " + tongTienStr +
                                     " - Không thể convert sang int: " + e.getMessage());
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Lỗi khi test tong_tien alternative: " + e.getMessage());
        }
    }
    
    private static void testSimpleQuery() {
        System.out.println("\n--- TEST 4: Test query đơn giản ---");
        try {
            db_connect db = new db_connect();
            Connection connection = db.getConnection();
            
            // Query đơn giản nhất
            String sql1 = "SELECT COUNT(*) as total FROM hoa_don WHERE ho_va_ten_khach IS NOT NULL";
            PreparedStatement ps1 = connection.prepareStatement(sql1);
            ResultSet rs1 = ps1.executeQuery();
            if (rs1.next()) {
                System.out.println("Số hóa đơn có ho_va_ten_khach IS NOT NULL: " + rs1.getInt("total"));
            }
            
            // Query với điều kiện != ''
            String sql2 = "SELECT COUNT(*) as total FROM hoa_don WHERE ho_va_ten_khach IS NOT NULL AND ho_va_ten_khach != ''";
            PreparedStatement ps2 = connection.prepareStatement(sql2);
            ResultSet rs2 = ps2.executeQuery();
            if (rs2.next()) {
                System.out.println("Số hóa đơn có ho_va_ten_khach IS NOT NULL AND != '': " + rs2.getInt("total"));
            }
            
            // Query với TRIM
            String sql3 = "SELECT COUNT(*) as total FROM hoa_don WHERE ho_va_ten_khach IS NOT NULL AND TRIM(ho_va_ten_khach) != ''";
            PreparedStatement ps3 = connection.prepareStatement(sql3);
            ResultSet rs3 = ps3.executeQuery();
            if (rs3.next()) {
                System.out.println("Số hóa đơn có ho_va_ten_khach IS NOT NULL AND TRIM != '': " + rs3.getInt("total"));
            }
            
            // Test SUM với CAST
            String sql4 = "SELECT ho_va_ten_khach, COUNT(*) as so_don, SUM(CAST(tong_tien AS SIGNED)) as tong_chi_tieu " +
                         "FROM hoa_don " +
                         "WHERE ho_va_ten_khach IS NOT NULL AND TRIM(ho_va_ten_khach) != '' " +
                         "GROUP BY ho_va_ten_khach " +
                         "ORDER BY tong_chi_tieu DESC " +
                         "LIMIT 5";
            PreparedStatement ps4 = connection.prepareStatement(sql4);
            ResultSet rs4 = ps4.executeQuery();
            
            System.out.println("Top khách hàng với CAST:");
            while (rs4.next()) {
                System.out.println("Khách: " + rs4.getString("ho_va_ten_khach") +
                                 " - Số đơn: " + rs4.getInt("so_don") +
                                 " - Tổng chi tiêu: " + rs4.getLong("tong_chi_tieu"));
            }
            
        } catch (SQLException e) {
            System.err.println("Lỗi khi test simple query: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
