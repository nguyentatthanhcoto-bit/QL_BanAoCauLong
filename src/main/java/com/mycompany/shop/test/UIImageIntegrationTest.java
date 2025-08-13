package com.mycompany.shop.test;

import screen.SanPham;
import screen.ThanhToan;
import com.mycompany.shop.util.ModernTheme;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Test class để kiểm tra giao diện đã được cập nhật với image support
 */
public class UIImageIntegrationTest {
    
    private static JFrame testFrame;
    private static JTabbedPane tabbedPane;
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            createTestUI();
        });
    }
    
    private static void createTestUI() {
        testFrame = new JFrame("UI Image Integration Test");
        testFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        testFrame.setSize(1200, 800);
        testFrame.setLocationRelativeTo(null);
        
        // Create main panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(ModernTheme.BACKGROUND_COLOR);
        
        // Create info panel
        JPanel infoPanel = createInfoPanel();
        mainPanel.add(infoPanel, BorderLayout.NORTH);
        
        // Create tabbed pane to test different screens
        tabbedPane = new JTabbedPane();
        
        // Add test tabs
        addTestTabs();
        
        mainPanel.add(tabbedPane, BorderLayout.CENTER);
        
        testFrame.add(mainPanel);
        testFrame.setVisible(true);
        
        System.out.println("UI Image Integration Test started!");
        System.out.println("- Test SanPham screen with image support");
        System.out.println("- Test HoaDon screen with product images");
        System.out.println("- Check image display in tables");
    }
    
    private static JPanel createInfoPanel() {
        JPanel infoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        infoPanel.setBackground(ModernTheme.PRIMARY_COLOR);
        infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JLabel titleLabel = new JLabel("UI Image Integration Test");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(ModernTheme.FONT_HEADING);
        
        JLabel infoLabel = new JLabel("Testing image support in product management screens");
        infoLabel.setForeground(Color.WHITE);
        infoLabel.setFont(ModernTheme.FONT_BODY);
        
        infoPanel.add(titleLabel);
        infoPanel.add(Box.createHorizontalStrut(20));
        infoPanel.add(infoLabel);
        
        return infoPanel;
    }
    
    private static void addTestTabs() {
        try {
            // Tab 1: SanPham screen
            SanPham sanPhamPanel = new SanPham();
            tabbedPane.addTab("Sản Phẩm (với Image)", sanPhamPanel);
            
            // Tab 2: HoaDon screen
            ThanhToan hoaDonPanel = new ThanhToan();
            tabbedPane.addTab("Hóa Đơn (với Product Images)", hoaDonPanel);
            
            // Tab 3: Image Utils Demo
            JPanel imageUtilsPanel = createImageUtilsDemo();
            tabbedPane.addTab("Image Utils Demo", imageUtilsPanel);
            
        } catch (Exception e) {
            JLabel errorLabel = new JLabel("Error creating test panels: " + e.getMessage());
            errorLabel.setForeground(Color.RED);
            errorLabel.setHorizontalAlignment(SwingConstants.CENTER);
            tabbedPane.addTab("Error", errorLabel);
            e.printStackTrace();
        }
    }
    
    private static JPanel createImageUtilsDemo() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(ModernTheme.BACKGROUND_COLOR);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        
        // Title
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        JLabel titleLabel = new JLabel("Image Utils Demo");
        titleLabel.setFont(ModernTheme.FONT_HEADING);
        panel.add(titleLabel, gbc);
        
        // Default image preview
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 1;
        JLabel imageLabel = new JLabel();
        imageLabel.setPreferredSize(new Dimension(150, 150));
        imageLabel.setBorder(BorderFactory.createLineBorder(ModernTheme.BORDER_COLOR, 1));
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        // Set default image
        ImageIcon defaultIcon = com.mycompany.shop.util.ImageUtils.createDefaultProductIcon(150, 150);
        imageLabel.setIcon(defaultIcon);
        
        panel.add(imageLabel, gbc);
        
        // Buttons panel
        gbc.gridx = 1; gbc.gridy = 1;
        JPanel buttonsPanel = new JPanel(new GridLayout(3, 1, 5, 5));
        
        JButton chooseImageBtn = new JButton("Choose Image");
        ModernTheme.styleButton(chooseImageBtn, ModernTheme.ButtonType.PRIMARY, ModernTheme.ButtonSize.MEDIUM);
        chooseImageBtn.addActionListener(e -> {
            java.io.File file = com.mycompany.shop.util.ImageUtils.chooseImageFile(panel);
            if (file != null) {
                ImageIcon icon = com.mycompany.shop.util.ImageUtils.createImageIcon(file.getAbsolutePath(), 150, 150);
                imageLabel.setIcon(icon);
            }
        });
        
        JButton resetBtn = new JButton("Reset to Default");
        ModernTheme.styleButton(resetBtn, ModernTheme.ButtonType.OUTLINE, ModernTheme.ButtonSize.MEDIUM);
        resetBtn.addActionListener(e -> {
            ImageIcon icon = com.mycompany.shop.util.ImageUtils.createDefaultProductIcon(150, 150);
            imageLabel.setIcon(icon);
        });
        
        JButton infoBtn = new JButton("Show Info");
        ModernTheme.styleButton(infoBtn, ModernTheme.ButtonType.SECONDARY, ModernTheme.ButtonSize.MEDIUM);
        infoBtn.addActionListener(e -> {
            String info = "Image Utils Features:\n" +
                         "- Choose image files\n" +
                         "- Create default product icons\n" +
                         "- Resize images automatically\n" +
                         "- Save images to products folder\n" +
                         "- Support multiple formats (jpg, png, gif, bmp)";
            JOptionPane.showMessageDialog(panel, info, "Image Utils Info", JOptionPane.INFORMATION_MESSAGE);
        });
        
        buttonsPanel.add(chooseImageBtn);
        buttonsPanel.add(resetBtn);
        buttonsPanel.add(infoBtn);
        
        panel.add(buttonsPanel, gbc);
        
        // Features list
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        JTextArea featuresArea = new JTextArea();
        featuresArea.setText(
            "✓ SanPham screen: Image preview and selection\n" +
            "✓ HoaDon screen: Product images in table\n" +
            "✓ Custom table cell renderers for images\n" +
            "✓ Automatic image resizing\n" +
            "✓ Default image fallback\n" +
            "✓ Image file validation\n" +
            "✓ Responsive image display"
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
