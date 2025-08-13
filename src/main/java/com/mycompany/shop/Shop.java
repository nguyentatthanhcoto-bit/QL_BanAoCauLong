package com.mycompany.shop;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import screen.Login;

public class Shop {
    public static void main(String[] args) {
        try {
            // Thiết lập giao diện Nimbus cho ứng dụng
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
                | UnsupportedLookAndFeelException e) {
            try {
                // Fallback sang giao diện mặc định
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        // Khởi động ứng dụng
        SwingUtilities.invokeLater(() -> {
            // Tạo và hiển thị JFrame chứa màn hình đăng nhập
            JFrame loginFrame = new JFrame("Đăng nhập hệ thống");
            loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            loginFrame.getContentPane().add(new Login());
            loginFrame.pack();
            loginFrame.setLocationRelativeTo(null); // Hiển thị ở giữa màn hình
            loginFrame.setVisible(true);
        });
    }
}
