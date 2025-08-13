package screen;

import com.mycompany.shop.dao.quan_ly_dao;
import com.mycompany.shop.dao.nhan_vien_dao;
import com.mycompany.shop.model.quan_ly;
import com.mycompany.shop.model.nhan_vien;
import com.mycompany.shop.model.UserSession;
import com.mycompany.shop.util.ModernTheme;
import com.mycompany.shop.util.UIHelper;
import javax.swing.JOptionPane;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class Login extends javax.swing.JPanel {

        public Login() {
                initComponents();
                applyModernTheme();
        }

        private void applyModernTheme() {
                // Apply modern theme to the login panel
                UIHelper.applyModernTheme(this);

                // Style specific components
                ModernTheme.styleTextField(input_username);
                ModernTheme.styleTextField(input_password);
                ModernTheme.styleButton(button_login, ModernTheme.ButtonType.PRIMARY);
                ModernTheme.styleButton(button_exit, ModernTheme.ButtonType.OUTLINE);

                // Update title styling
                jLabel1.setFont(ModernTheme.FONT_TITLE);
                jLabel1.setForeground(ModernTheme.TEXT_PRIMARY);

                // Update other labels
                jLabel4.setFont(ModernTheme.FONT_BODY);
                jLabel4.setForeground(ModernTheme.TEXT_PRIMARY);
                jLabel5.setFont(ModernTheme.FONT_BODY);
                jLabel5.setForeground(ModernTheme.TEXT_PRIMARY);

                // Style forget password link
                text_forget.setFont(ModernTheme.FONT_SMALL);
                text_forget.setForeground(ModernTheme.PRIMARY_COLOR);
                text_forget.setCursor(new Cursor(Cursor.HAND_CURSOR));

                // Set background
                this.setBackground(ModernTheme.BACKGROUND_COLOR);
        }

        @SuppressWarnings("unchecked")
        // <editor-fold defaultstate="collapsed" desc="Generated
        // Code">//GEN-BEGIN:initComponents
        private void initComponents() {

                input_username = new javax.swing.JTextField();
                jLabel1 = new javax.swing.JLabel();
                input_password = new javax.swing.JPasswordField();
                button_login = new javax.swing.JButton();
                button_exit = new javax.swing.JButton();
                text_forget = new javax.swing.JLabel();
                jLabel4 = new javax.swing.JLabel();
                jLabel5 = new javax.swing.JLabel();
                jLabel3 = new javax.swing.JLabel();

                input_username.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                input_usernameActionPerformed(evt);
                        }
                });

                jLabel1.setFont(new java.awt.Font("Helvetica Neue", 1, 18)); // NOI18N
                jLabel1.setText("ĐĂNG NHẬP");

                input_password.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
                input_password.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                input_passwordActionPerformed(evt);
                        }
                });

                button_login.setText("Đăng nhập");
                button_login.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                button_loginActionPerformed(evt);
                        }
                });

                button_exit.setText("Thoát");
                button_exit.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                button_exitActionPerformed(evt);
                        }
                });

                text_forget.setText("Quên mật khẩu?");
                text_forget.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
                text_forget.addMouseListener(new java.awt.event.MouseAdapter() {
                        public void mouseClicked(java.awt.event.MouseEvent evt) {
                                text_forgetMouseClicked(evt);
                        }
                });

                jLabel4.setIcon(new javax.swing.ImageIcon(
                                getClass().getResource("/resources/images/icon-password.png"))); // NOI18N
                jLabel4.setText("jLabel3");
                jLabel4.setMaximumSize(new java.awt.Dimension(64, 64));
                jLabel4.setMinimumSize(new java.awt.Dimension(558, 512));

                jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/images/icon-user-1.png"))); // NOI18N
                jLabel5.setText("jLabel3");
                jLabel5.setMaximumSize(new java.awt.Dimension(64, 64));
                jLabel5.setMinimumSize(new java.awt.Dimension(558, 512));

                jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/images/Logo.png"))); // NOI18N
                jLabel3.setText("jLabel3");

                javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
                this.setLayout(layout);
                layout.setHorizontalGroup(
                                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(layout.createSequentialGroup()
                                                                .addComponent(jLabel3,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                300,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGroup(layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                .addGroup(layout.createSequentialGroup()
                                                                                                .addGap(123, 123, 123)
                                                                                                .addComponent(button_login,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                123,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                .addGap(45, 45, 45)
                                                                                                .addGroup(layout
                                                                                                                .createParallelGroup(
                                                                                                                                javax.swing.GroupLayout.Alignment.TRAILING)
                                                                                                                .addComponent(button_exit,
                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                123,
                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                                .addComponent(text_forget)))
                                                                                .addGroup(layout.createSequentialGroup()
                                                                                                .addPreferredGap(
                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                Short.MAX_VALUE)
                                                                                                .addGroup(layout
                                                                                                                .createParallelGroup(
                                                                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
                                                                                                                                layout.createSequentialGroup()
                                                                                                                                                .addComponent(jLabel1)
                                                                                                                                                .addGap(121, 121,
                                                                                                                                                                121))
                                                                                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
                                                                                                                                layout
                                                                                                                                                .createSequentialGroup()
                                                                                                                                                .addGroup(layout.createParallelGroup(
                                                                                                                                                                javax.swing.GroupLayout.Alignment.TRAILING,
                                                                                                                                                                false)
                                                                                                                                                                .addComponent(jLabel4,
                                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                                                51,
                                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                                                                                .addComponent(jLabel5,
                                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                                                1,
                                                                                                                                                                                Short.MAX_VALUE))
                                                                                                                                                .addPreferredGap(
                                                                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                                                                                                .addGroup(layout.createParallelGroup(
                                                                                                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                                                                                                .addComponent(input_username,
                                                                                                                                                                                javax.swing.GroupLayout.Alignment.TRAILING,
                                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                                                314,
                                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                                                                                .addComponent(input_password,
                                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                                                314,
                                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))))))
                                                                .addContainerGap(45, Short.MAX_VALUE)));
                layout.setVerticalGroup(
                                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(layout.createSequentialGroup()
                                                                .addGap(64, 64, 64)
                                                                .addComponent(jLabel1)
                                                                .addGap(55, 55, 55)
                                                                .addGroup(layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                .addComponent(input_username,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                36,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                .addComponent(jLabel5,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addGap(15, 15, 15)
                                                                .addGroup(layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.TRAILING)
                                                                                .addComponent(jLabel4,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                .addComponent(input_password,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                36,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addGap(41, 41, 41)
                                                                .addGroup(layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                .addComponent(button_login,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                34,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                .addComponent(button_exit,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                34,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addGap(18, 18, 18)
                                                                .addComponent(text_forget)
                                                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                Short.MAX_VALUE))
                                                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 431,
                                                                Short.MAX_VALUE));
        }// </editor-fold>//GEN-END:initComponents

        private void input_passwordActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_input_passwordActionPerformed
                // Đăng nhập khi nhấn Enter ở ô password
                button_loginActionPerformed(evt);
        }// GEN-LAST:event_input_passwordActionPerformed

        private void input_usernameActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_input_usernameActionPerformed
                // Chuyển focus sang ô password khi nhấn Enter ở ô username
                input_password.requestFocus();
        }// GEN-LAST:event_input_usernameActionPerformed

        private void button_loginActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_button_loginActionPerformed
                String username = input_username.getText().trim();
                String password = new String(input_password.getPassword()).trim();

                // Validation
                if (username.isEmpty() || password.isEmpty()) {
                        JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ tên đăng nhập và mật khẩu!",
                                        "Lỗi", JOptionPane.WARNING_MESSAGE);
                        return;
                }

                // Thử đăng nhập với tài khoản quản lý trước
                quan_ly_dao quanLyDAO = new quan_ly_dao();
                quan_ly manager = quanLyDAO.checkLogin(username, password);

                if (manager != null) {
                        // Đăng nhập thành công với tài khoản quản lý
                        UserSession.getInstance().loginAsManager(manager);

                        JOptionPane.showMessageDialog(this,
                                        "Đăng nhập thành công!\nChào mừng Quản lý: " + manager.getTen_quan_ly(),
                                        "Thành công", JOptionPane.INFORMATION_MESSAGE);

                        // Mở trang chủ với quyền quản lý
                        MainScreen main = new MainScreen();
                        main.setVisible(true);
                        this.getParent().setVisible(false);
                        return;
                }

                // Nếu không phải quản lý, thử đăng nhập với tài khoản nhân viên
                nhan_vien_dao nhanVienDAO = new nhan_vien_dao();
                nhan_vien employee = nhanVienDAO.checkLoginNhanVien(username, password);

                if (employee != null) {
                        // Đăng nhập thành công với tài khoản nhân viên
                        UserSession.getInstance().loginAsEmployee(employee);

                        JOptionPane.showMessageDialog(this,
                                        "Đăng nhập thành công!\nChào mừng Nhân viên: " + employee.getTen_nhan_vien(),
                                        "Thành công", JOptionPane.INFORMATION_MESSAGE);

                        // Mở trang chủ với quyền nhân viên
                        MainScreen main = new MainScreen();
                        main.setVisible(true);
                        this.getParent().setVisible(false);
                        return;
                }

                // Cả hai đều không thành công
                JOptionPane.showMessageDialog(this,
                                "Tên đăng nhập hoặc mật khẩu không đúng!\nVui lòng kiểm tra lại thông tin đăng nhập.",
                                "Lỗi đăng nhập", JOptionPane.ERROR_MESSAGE);
        }// GEN-LAST:event_button_loginActionPerformed

        private void button_exitActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_button_exitActionPerformed
                // Xử lý sự kiện cho nút thoát
                int confirm = JOptionPane.showConfirmDialog(this,
                                "Bạn có chắc chắn muốn thoát khỏi ứng dụng?",
                                "Xác nhận thoát",
                                JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {
                        System.exit(0);
                }
        }// GEN-LAST:event_button_exitActionPerformed

        private void text_forgetMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_text_forgetMouseClicked
                // Xử lý khi click vào quên mật khẩu
                JOptionPane.showMessageDialog(this,
                                "Vui lòng liên hệ với quản trị viên để đặt lại mật khẩu!",
                                "Quên mật khẩu",
                                JOptionPane.INFORMATION_MESSAGE);
        }// GEN-LAST:event_text_forgetMouseClicked

        // Variables declaration - do not modify//GEN-BEGIN:variables
        private javax.swing.JButton button_exit;
        private javax.swing.JButton button_login;
        private javax.swing.JPasswordField input_password;
        private javax.swing.JTextField input_username;
        private javax.swing.JLabel jLabel1;
        private javax.swing.JLabel jLabel3;
        private javax.swing.JLabel jLabel4;
        private javax.swing.JLabel jLabel5;
        private javax.swing.JLabel text_forget;
        // End of variables declaration//GEN-END:variables
}
