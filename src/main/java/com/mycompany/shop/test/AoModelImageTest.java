package com.mycompany.shop.test;

import com.mycompany.shop.dao.ao_dao;
import com.mycompany.shop.model.ao;
import com.mycompany.shop.util.ImageUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;

/**
 * Test class để kiểm tra model áo với trường image đã được cập nhật
 */
public class AoModelImageTest {
    
    private static JFrame testFrame;
    private static JTextArea logArea;
    private static ao_dao aoDAO;
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            createTestUI();
        });
    }
    
    private static void createTestUI() {
        testFrame = new JFrame("Ao Model Image Test");
        testFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        testFrame.setSize(800, 600);
        testFrame.setLocationRelativeTo(null);
        
        // Initialize DAO
        try {
            aoDAO = new ao_dao();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Lỗi kết nối database: " + e.getMessage());
            return;
        }
        
        // Create main panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        
        // Create button panel
        JPanel buttonPanel = createButtonPanel();
        mainPanel.add(buttonPanel, BorderLayout.NORTH);
        
        // Create log area
        logArea = new JTextArea();
        logArea.setEditable(false);
        logArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(logArea);
        scrollPane.setPreferredSize(new Dimension(780, 400));
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        
        testFrame.add(mainPanel);
        testFrame.setVisible(true);
        
        log("=== Ao Model Image Test Started ===");
        log("Database connection: OK");
        log("Ready to test image field functionality");
    }
    
    private static JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout());
        
        // Test Model Constructor
        JButton testModelBtn = new JButton("Test Model Constructor");
        testModelBtn.addActionListener(e -> testModelConstructor());
        panel.add(testModelBtn);
        
        // Test DAO Add with Image
        JButton testAddBtn = new JButton("Test Add Ao with Image");
        testAddBtn.addActionListener(e -> testAddAoWithImage());
        panel.add(testAddBtn);
        
        // Test DAO Get All
        JButton testGetAllBtn = new JButton("Test Get All Ao");
        testGetAllBtn.addActionListener(e -> testGetAllAo());
        panel.add(testGetAllBtn);
        
        // Test Image Utils
        JButton testImageBtn = new JButton("Test Image Utils");
        testImageBtn.addActionListener(e -> testImageUtils());
        panel.add(testImageBtn);
        
        // Clear Log
        JButton clearBtn = new JButton("Clear Log");
        clearBtn.addActionListener(e -> logArea.setText(""));
        panel.add(clearBtn);
        
        return panel;
    }
    
    private static void testModelConstructor() {
        log("\n=== Testing Model Constructor ===");
        try {
            // Test constructor với image
            ao testAo = new ao(1, "TEST001", "Test Áo", 100000, 10, "Mô tả test", 
                              "images/test.jpg", 1, 1, 1);
            
            log("✓ Constructor with image: SUCCESS");
            log("  - ID: " + testAo.getId_ao());
            log("  - Mã: " + testAo.getMa_ao());
            log("  - Tên: " + testAo.getTen_ao());
            log("  - Image: " + testAo.getImage());
            
            // Test getter/setter
            testAo.setImage("images/new_test.jpg");
            log("✓ Image setter/getter: " + testAo.getImage());
            
        } catch (Exception e) {
            log("✗ Model Constructor Test FAILED: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private static void testAddAoWithImage() {
        log("\n=== Testing DAO Add with Image ===");
        try {
            String maSP = "IMG_TEST_" + System.currentTimeMillis();
            String imagePath = "images/products/test_product.jpg";
            
            aoDAO.addAo(maSP, "Test Áo với Image", 150000, 5, "Test mô tả", 
                       imagePath, 1, 1, 1);
            
            log("✓ Add Ao with image: SUCCESS");
            log("  - Mã SP: " + maSP);
            log("  - Image: " + imagePath);
            
            // Verify by getting the added product
            ao addedAo = aoDAO.getAoByMa(maSP);
            if (addedAo != null) {
                log("✓ Verification: Product found");
                log("  - Image from DB: " + addedAo.getImage());
            } else {
                log("✗ Verification: Product not found");
            }
            
        } catch (Exception e) {
            log("✗ Add Ao Test FAILED: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private static void testGetAllAo() {
        log("\n=== Testing Get All Ao ===");
        try {
            List<ao> danhSachAo = aoDAO.getAllAo();
            log("✓ Get All Ao: SUCCESS");
            log("  - Total products: " + danhSachAo.size());
            
            // Show first 3 products with image info
            int count = Math.min(3, danhSachAo.size());
            for (int i = 0; i < count; i++) {
                ao ao = danhSachAo.get(i);
                log("  - Product " + (i+1) + ": " + ao.getMa_ao() + 
                    " | Image: " + (ao.getImage() != null ? ao.getImage() : "null"));
            }
            
        } catch (Exception e) {
            log("✗ Get All Ao Test FAILED: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private static void testImageUtils() {
        log("\n=== Testing Image Utils ===");
        try {
            // Test default icon creation
            ImageIcon defaultIcon = ImageUtils.createDefaultProductIcon(100, 100);
            log("✓ Default icon creation: SUCCESS");
            log("  - Icon size: " + defaultIcon.getIconWidth() + "x" + defaultIcon.getIconHeight());
            
            // Test image directory
            String imageDir = ImageUtils.getImageDirectory();
            log("✓ Image directory: " + imageDir);
            
            // Test file validation
            File testFile = new File("test.jpg");
            boolean isValid = ImageUtils.isValidImageFile(testFile);
            log("✓ File validation test: " + (isValid ? "Valid format" : "Invalid format"));
            
        } catch (Exception e) {
            log("✗ Image Utils Test FAILED: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private static void log(String message) {
        SwingUtilities.invokeLater(() -> {
            logArea.append(message + "\n");
            logArea.setCaretPosition(logArea.getDocument().getLength());
        });
        System.out.println(message);
    }
}
