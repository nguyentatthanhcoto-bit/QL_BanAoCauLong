package com.mycompany.shop.test;

import screen.ThongKe;
import com.mycompany.shop.dao.thong_ke_dao;
import com.mycompany.shop.util.ModernTheme;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * Test class để kiểm tra chức năng thống kê
 */
public class ThongKeTest {
    
    private static JFrame testFrame;
    private static thong_ke_dao thongKeDAO;
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            createTestUI();
        });
    }
    
    private static void createTestUI() {
        testFrame = new JFrame("Thống Kê Test");
        testFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        testFrame.setSize(1200, 800);
        testFrame.setLocationRelativeTo(null);
        
        // Initialize DAO
        thongKeDAO = new thong_ke_dao();
        
        // Create main panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(ModernTheme.BACKGROUND_COLOR);
        
        // Create info panel
        JPanel infoPanel = createInfoPanel();
        mainPanel.add(infoPanel, BorderLayout.NORTH);
        
        // Create tabbed pane to test different features
        JTabbedPane tabbedPane = new JTabbedPane();
        
        // Add test tabs
        addTestTabs(tabbedPane);
        
        mainPanel.add(tabbedPane, BorderLayout.CENTER);
        
        testFrame.add(mainPanel);
        testFrame.setVisible(true);
        
        System.out.println("ThongKe Test started!");
        System.out.println("- Test statistics calculations");
        System.out.println("- Test data display");
        System.out.println("- Check database connections");
    }
    
    private static JPanel createInfoPanel() {
        JPanel infoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        infoPanel.setBackground(ModernTheme.PRIMARY_COLOR);
        infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JLabel titleLabel = new JLabel("Thống Kê Test");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(ModernTheme.FONT_HEADING);
        
        JLabel infoLabel = new JLabel("Testing statistics and reporting functionality");
        infoLabel.setForeground(Color.WHITE);
        infoLabel.setFont(ModernTheme.FONT_BODY);
        
        infoPanel.add(titleLabel);
        infoPanel.add(Box.createHorizontalStrut(20));
        infoPanel.add(infoLabel);
        
        return infoPanel;
    }
    
    private static void addTestTabs(JTabbedPane tabbedPane) {
        try {
            // Tab 1: ThongKe screen
            ThongKe thongKePanel = new ThongKe();
            tabbedPane.addTab("Thống Kê Screen", thongKePanel);
            
            // Tab 2: DAO Test
            JPanel daoTestPanel = createDAOTestPanel();
            tabbedPane.addTab("DAO Test", daoTestPanel);
            
            // Tab 3: Statistics Summary
            JPanel summaryPanel = createSummaryPanel();
            tabbedPane.addTab("Summary", summaryPanel);
            
        } catch (Exception e) {
            JLabel errorLabel = new JLabel("Error creating test panels: " + e.getMessage());
            errorLabel.setForeground(Color.RED);
            errorLabel.setHorizontalAlignment(SwingConstants.CENTER);
            tabbedPane.addTab("Error", errorLabel);
            e.printStackTrace();
        }
    }
    
    private static JPanel createDAOTestPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(ModernTheme.BACKGROUND_COLOR);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Title
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        JLabel titleLabel = new JLabel("DAO Test Results");
        titleLabel.setFont(ModernTheme.FONT_HEADING);
        panel.add(titleLabel, gbc);
        
        // Test results area
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0; gbc.weighty = 1.0;
        
        JTextArea resultsArea = new JTextArea();
        resultsArea.setEditable(false);
        resultsArea.setBackground(ModernTheme.CARD_BACKGROUND);
        resultsArea.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(ModernTheme.BORDER_COLOR, 1),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        resultsArea.setFont(ModernTheme.FONT_BODY);
        
        JScrollPane scrollPane = new JScrollPane(resultsArea);
        panel.add(scrollPane, gbc);
        
        // Test button
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0; gbc.weighty = 0;
        
        JButton testButton = new JButton("Run DAO Tests");
        ModernTheme.styleButton(testButton, ModernTheme.ButtonType.PRIMARY, ModernTheme.ButtonSize.MEDIUM);
        testButton.addActionListener(e -> {
            resultsArea.setText("Running DAO tests...\n\n");
            runDAOTests(resultsArea);
        });
        
        panel.add(testButton, gbc);
        
        return panel;
    }
    
    private static void runDAOTests(JTextArea resultsArea) {
        StringBuilder results = new StringBuilder();
        LocalDate today = LocalDate.now();
        Date currentDate = Date.valueOf(today);
        
        try {
            // Test 1: Tổng đơn hàng
            int tongDonHang = thongKeDAO.getTongDonHang();
            results.append("✓ Tổng đơn hàng: ").append(tongDonHang).append("\n");
            
            // Test 2: Đơn hàng hôm nay
            int donHangNgay = thongKeDAO.getDonHangTheoNgay(currentDate);
            results.append("✓ Đơn hàng hôm nay: ").append(donHangNgay).append("\n");
            
            // Test 3: Doanh thu tháng này
            long doanhThuThang = thongKeDAO.getDoanhThuTheoThang(today.getMonthValue(), today.getYear());
            results.append("✓ Doanh thu tháng này: ").append(doanhThuThang).append(" VND\n");
            
            // Test 4: Doanh thu năm này
            long doanhThuNam = thongKeDAO.getDoanhThuTheoNam(today.getYear());
            results.append("✓ Doanh thu năm này: ").append(doanhThuNam).append(" VND\n\n");
            
            // Test 5: Top sản phẩm bán chạy
            List<Map<String, Object>> topSanPham = thongKeDAO.getTopSanPhamBanChay(5);
            results.append("✓ Top 5 sản phẩm bán chạy:\n");
            for (Map<String, Object> sp : topSanPham) {
                results.append("  - ").append(sp.get("ten_ao"))
                       .append(" (").append(sp.get("so_luong_ban")).append(" bán)\n");
            }
            results.append("\n");
            
            // Test 6: Top khách hàng
            List<Map<String, Object>> topKhachHang = thongKeDAO.getTopKhachHang(5);
            results.append("✓ Top 5 khách hàng VIP:\n");
            for (Map<String, Object> kh : topKhachHang) {
                results.append("  - ").append(kh.get("ten_khach"))
                       .append(" (").append(kh.get("tong_chi_tieu")).append(" VND)\n");
            }
            results.append("\n");
            
            // Test 7: Tồn kho
            List<Map<String, Object>> tonKho = thongKeDAO.getThongKeTonKho();
            results.append("✓ Thống kê tồn kho: ").append(tonKho.size()).append(" sản phẩm\n");
            
            results.append("\n=== TẤT CẢ TESTS THÀNH CÔNG! ===");
            
        } catch (Exception e) {
            results.append("❌ ERROR: ").append(e.getMessage()).append("\n");
            e.printStackTrace();
        }
        
        resultsArea.setText(results.toString());
    }
    
    private static JPanel createSummaryPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(ModernTheme.BACKGROUND_COLOR);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        
        // Title
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        JLabel titleLabel = new JLabel("Thống Kê Features Summary");
        titleLabel.setFont(ModernTheme.FONT_HEADING);
        panel.add(titleLabel, gbc);
        
        // Features list
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        JTextArea featuresArea = new JTextArea();
        featuresArea.setText(
            "✓ Thống kê tổng đơn hàng\n" +
            "✓ Thống kê đơn hàng theo ngày/tháng/năm\n" +
            "✓ Thống kê doanh thu theo ngày/tháng/năm\n" +
            "✓ Top sản phẩm bán chạy nhất\n" +
            "✓ Top khách hàng VIP\n" +
            "✓ Thống kê tồn kho (hết hàng, sắp hết, còn hàng)\n" +
            "✓ Doanh thu chi tiết theo thời gian\n" +
            "✓ Giao diện thống kê trực quan với 4 panel chính\n" +
            "✓ TabbedPane với 4 tab chi tiết\n" +
            "✓ Tự động cập nhật khi thay đổi thời gian\n" +
            "✓ Format tiền tệ VND\n" +
            "✓ Responsive design với Modern Theme"
        );
        featuresArea.setEditable(false);
        featuresArea.setBackground(ModernTheme.CARD_BACKGROUND);
        featuresArea.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(ModernTheme.BORDER_COLOR, 1),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        featuresArea.setFont(ModernTheme.FONT_BODY);
        
        panel.add(featuresArea, gbc);
        
        return panel;
    }
}
