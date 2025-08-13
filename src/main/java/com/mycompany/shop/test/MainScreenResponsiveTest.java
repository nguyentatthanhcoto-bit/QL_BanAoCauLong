package com.mycompany.shop.test;

import screen.MainScreen;
import com.mycompany.shop.model.UserSession;
import com.mycompany.shop.model.quan_ly;

import javax.swing.*;
import java.awt.*;

/**
 * Test class for MainScreen responsive behavior
 */
public class MainScreenResponsiveTest {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                // Set system look and feel
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }

            // Setup test user session
            setupTestUserSession();

            // Create and show MainScreen
            MainScreen mainScreen = new MainScreen();

            // Configure for testing
            configureForTesting(mainScreen);

            // Show the screen
            mainScreen.setVisible(true);

            // Add test instructions
            showTestInstructions();
        });
    }

    private static void setupTestUserSession() {
        // Create a test manager user
        quan_ly testManager = new quan_ly();
        testManager.setId_quan_ly(1);
        testManager.setMa_quan_ly("QL001");
        testManager.setTai_khoan("test_manager");
        testManager.setMat_khau("password");
        testManager.setTen_quan_ly("Test Manager");

        // Login the test user
        UserSession session = UserSession.getInstance();
        session.loginAsManager(testManager);
    }

    private static void configureForTesting(MainScreen mainScreen) {
        // Set initial size
        mainScreen.setSize(1200, 800);
        mainScreen.setLocationRelativeTo(null);

        // Make resizable for testing
        mainScreen.setResizable(true);

        // Set title to indicate test mode
        mainScreen.setTitle("MainScreen Responsive Test - Resize window to test responsive behavior");

        // Add window state listener for debugging
        mainScreen.addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent e) {
                Dimension size = mainScreen.getSize();
                System.out.println("MainScreen resized to: " + size.width + "x" + size.height);
            }
        });
    }

    private static void showTestInstructions() {
        SwingUtilities.invokeLater(() -> {
            String instructions = "MAINSCREEN RESPONSIVE TEST\n\n" +
                    "Instructions:\n" +
                    "1. Resize the main window to different sizes\n" +
                    "2. Switch between different panels (Sản phẩm, Hóa đơn, etc.)\n" +
                    "3. Observe how the content adapts to window size\n" +
                    "4. Check that tables, buttons, and forms resize properly\n" +
                    "5. Test both small (< 1024px) and large (> 1366px) window sizes\n\n" +
                    "Expected behavior:\n" +
                    "- Content should fill the available space\n" +
                    "- Components should resize appropriately\n" +
                    "- No content should be cut off or overlapping\n" +
                    "- Tables should adjust column widths\n" +
                    "- Buttons should maintain proper proportions\n\n" +
                    "Check console for resize events.";

            JOptionPane.showMessageDialog(null, instructions,
                    "MainScreen Responsive Test Instructions",
                    JOptionPane.INFORMATION_MESSAGE);
        });
    }
}
