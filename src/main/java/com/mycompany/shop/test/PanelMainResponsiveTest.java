package com.mycompany.shop.test;

import screen.MainScreen;
import com.mycompany.shop.util.ModernTheme;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

/**
 * Test class to verify panel_main responsive behavior
 */
public class PanelMainResponsiveTest {
    
    private static JFrame testFrame;
    private static JLabel sizeLabel;
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            createTestEnvironment();
        });
    }
    
    private static void createTestEnvironment() {
        // Create test frame
        testFrame = new JFrame("Panel Main Responsive Test");
        testFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        testFrame.setSize(1200, 800);
        testFrame.setLocationRelativeTo(null);
        
        // Create main panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(ModernTheme.BACKGROUND_COLOR);
        
        // Create info panel
        JPanel infoPanel = createInfoPanel();
        mainPanel.add(infoPanel, BorderLayout.NORTH);
        
        // Create MainScreen instance (this will test panel_main)
        try {
            // Setup test user session first
            setupTestUserSession();
            
            MainScreen mainScreen = new MainScreen();
            mainPanel.add(mainScreen, BorderLayout.CENTER);
            
            // Add resize listener to monitor changes
            testFrame.addComponentListener(new ComponentAdapter() {
                @Override
                public void componentResized(ComponentEvent e) {
                    updateSizeInfo();
                }
            });
            
        } catch (Exception e) {
            JLabel errorLabel = new JLabel("Error creating MainScreen: " + e.getMessage());
            errorLabel.setForeground(Color.RED);
            errorLabel.setHorizontalAlignment(SwingConstants.CENTER);
            mainPanel.add(errorLabel, BorderLayout.CENTER);
            e.printStackTrace();
        }
        
        testFrame.add(mainPanel);
        testFrame.setVisible(true);
        
        // Initial size update
        SwingUtilities.invokeLater(() -> updateSizeInfo());
        
        System.out.println("Panel Main Responsive Test started!");
        System.out.println("- Resize the window to test responsive behavior");
        System.out.println("- Check if panel_main expands to fill available space");
        System.out.println("- Verify no white space on the right side");
    }
    
    private static JPanel createInfoPanel() {
        JPanel infoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        infoPanel.setBackground(ModernTheme.PRIMARY_COLOR);
        infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JLabel titleLabel = new JLabel("Panel Main Responsive Test");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(ModernTheme.FONT_HEADING);
        
        sizeLabel = new JLabel("Window Size: ");
        sizeLabel.setForeground(Color.WHITE);
        sizeLabel.setFont(ModernTheme.FONT_BODY);
        
        infoPanel.add(titleLabel);
        infoPanel.add(Box.createHorizontalStrut(20));
        infoPanel.add(sizeLabel);
        
        return infoPanel;
    }
    
    private static void updateSizeInfo() {
        if (sizeLabel != null && testFrame != null) {
            Dimension size = testFrame.getSize();
            sizeLabel.setText(String.format("Window Size: %dx%d", size.width, size.height));
        }
    }
    
    private static void setupTestUserSession() {
        // This is a simplified setup - in real app, UserSession would be properly initialized
        System.out.println("Setting up test user session...");
        // Note: We'll handle the UserSession issue separately if needed
    }
}
