package com.mycompany.shop.test;

import com.mycompany.shop.util.ModernTheme;
import javax.swing.*;
import java.awt.*;

/**
 * Test class to verify ComboBox styling improvements
 */
public class ComboBoxStyleTest {
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            createAndShowTestFrame();
        });
    }
    
    private static void createAndShowTestFrame() {
        JFrame frame = new JFrame("ComboBox Style Test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);
        
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        mainPanel.setBackground(ModernTheme.BACKGROUND_COLOR);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Test ComboBox 1 - Loại sản phẩm
        gbc.gridx = 0; gbc.gridy = 0;
        mainPanel.add(new JLabel("Loại sản phẩm:"), gbc);
        
        JComboBox<String> comboLoai = new JComboBox<>(new String[]{"Cổ tròn", "Cổ tim", "Cổ V", "Polo"});
        ModernTheme.styleComboBox(comboLoai);
        gbc.gridx = 1; gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        mainPanel.add(comboLoai, gbc);
        
        // Test ComboBox 2 - Màu sắc
        gbc.gridx = 0; gbc.gridy = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        mainPanel.add(new JLabel("Màu sắc:"), gbc);
        
        JComboBox<String> comboMau = new JComboBox<>(new String[]{"Đen", "Trắng", "Xanh", "Đỏ", "Vàng"});
        ModernTheme.styleComboBox(comboMau);
        gbc.gridx = 1; gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        mainPanel.add(comboMau, gbc);
        
        // Test ComboBox 3 - Kích thước
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        mainPanel.add(new JLabel("Kích thước:"), gbc);
        
        JComboBox<String> comboSize = new JComboBox<>(new String[]{"S", "M", "L", "XL", "XXL"});
        ModernTheme.styleComboBox(comboSize, ModernTheme.ButtonSize.SMALL);
        gbc.gridx = 1; gbc.gridy = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        mainPanel.add(comboSize, gbc);
        
        // Test Button for comparison
        gbc.gridx = 0; gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        
        JButton testButton = new JButton("Test Button");
//        ModernTheme.styleButton(testButton);
        mainPanel.add(testButton, gbc);
        
        frame.add(mainPanel);
        frame.setVisible(true);
        
        System.out.println("ComboBox Style Test started successfully!");
        System.out.println("- Test different ComboBox styles");
        System.out.println("- Check arrow appearance");
        System.out.println("- Verify focus effects");
        System.out.println("- Compare with button styling");
    }
}
