/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package screen;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import com.mycompany.shop.model.UserSession;
import com.mycompany.shop.util.ModernTheme;
import com.mycompany.shop.util.UIHelper;
import com.mycompany.shop.util.ResponsiveLayoutManager;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

/**
 *
 * @author nguyenthanhquoc
 */
public class MainScreen extends javax.swing.JFrame {

        private static final java.util.logging.Logger logger = java.util.logging.Logger
                        .getLogger(MainScreen.class.getName());

        // Các panel chính của ứng dụng
        private SanPham panelSanPham;
        private ThanhToan panelThanhToan;
        private ThongKe panelThongKe;
        private KhachHang panelKhachHang;
        private NhanVien panelNhanVien;
        private KhuyenMai panelKhuyenMai;
        private LichSu panelLichSu;
        private HoaDon panelHoaDon;

        /**
         * Creates new form MainScreen
         */
        public MainScreen() {
                initComponents();
                setLocationRelativeTo(null); // Hiển thị cửa sổ ở giữa màn hình

                // Setup responsive design cho MainScreen
                setupResponsiveDesign();

                // Cập nhật title với thông tin user
                updateTitleWithUserInfo();

                // Khởi tạo các panel
                initPanels();

                // Mặc định hiển thị trang thống kê khi khởi động
                showPanel(panelThongKe);

                // Thêm sự kiện click cho các menu
                addMenuEvents();

                // Áp dụng phân quyền
                applyUserPermissions();
        }

        private void initPanels() {
                // Khởi tạo các panel với responsive design
                panelSanPham = new SanPham();
                panelThanhToan = new ThanhToan();
                panelThongKe = new ThongKe();
                panelKhachHang = new KhachHang();
                panelNhanVien = new NhanVien();
                panelKhuyenMai = new KhuyenMai();
                panelLichSu = new LichSu();
                panelHoaDon = new HoaDon();

                // Apply responsive design cho tất cả panels
                applyResponsiveDesignToPanels();
        }

        /**
         * Apply responsive design cho tất cả panels
         */
        private void applyResponsiveDesignToPanels() {
                JPanel[] panels = { panelSanPham, panelThanhToan, panelThongKe,
                                panelKhachHang, panelNhanVien, panelKhuyenMai, panelLichSu, panelHoaDon };

                for (JPanel panel : panels) {
                        if (panel != null) {
                                // Optimize panel for container embedding
                                ResponsiveLayoutManager.optimizeForContainer(panel);

                                // Set minimum size for usability
                                panel.setMinimumSize(new Dimension(800, 600));

                                // Apply modern theme with responsive behavior
                                UIHelper.applyModernTheme(panel);
                        }
                }

                // Setup responsive behavior for panel_main after all panels are initialized
                setupPanelMainResponsiveBehavior();
        }

        /**
         * Setup optimized responsive behavior for panel_main container
         */
        private void setupPanelMainResponsiveBehavior() {
                // Use throttled resize handling to prevent lag
                panel_main.addComponentListener(new ComponentAdapter() {
                        private Timer resizeTimer;

                        @Override
                        public void componentResized(ComponentEvent e) {
                                if (resizeTimer != null) {
                                        resizeTimer.stop();
                                }

                                resizeTimer = new Timer(50, evt -> {
                                        updateCurrentPanelSize();
                                });
                                resizeTimer.setRepeats(false);
                                resizeTimer.start();
                        }
                });
        }

        /**
         * Update current panel size efficiently
         */
        private void updateCurrentPanelSize() {
                if (panel_main.getComponentCount() > 0) {
                        Component currentPanel = panel_main.getComponent(0);
                        if (currentPanel instanceof JPanel) {
                                Dimension containerSize = panel_main.getSize();
                                currentPanel.setPreferredSize(containerSize);
                                currentPanel.setSize(containerSize);
                                currentPanel.revalidate();
                        }
                }
        }

        private void showPanel(JPanel panel) {
                // Xóa tất cả component hiện có trong panel_main
                panel_main.removeAll();

                // Setup layout cho panel_main nếu chưa có
                if (panel_main.getLayout() == null) {
                        panel_main.setLayout(new BorderLayout());
                }

                // Thêm panel mới vào panel_main với BorderLayout.CENTER để tự động co giãn
                panel_main.add(panel, BorderLayout.CENTER);

                // Đảm bảo panel con có thể co giãn theo panel_main
                Dimension panelMainSize = new Dimension(panel_main.getWidth(), panel_main.getHeight());
                panel.setPreferredSize(panelMainSize);
                panel.setSize(panelMainSize);
                panel.setVisible(true);

                // Force immediate layout update
                panel.doLayout();

                // Cập nhật giao diện
                panel_main.revalidate();
                panel_main.repaint();
        }

        /**
         * Setup responsive design cho MainScreen
         */
        private void setupResponsiveDesign() {
                // Apply modern theme cho MainScreen
                this.getContentPane().setBackground(ModernTheme.BACKGROUND_COLOR);

                // Override form-generated settings for panel_main
                overridePanelMainSettings();

                // Add component listener để handle resize events
                this.addComponentListener(new ComponentAdapter() {
                        @Override
                        public void componentResized(ComponentEvent e) {
                                handleMainScreenResize();
                        }
                });

                // Setup responsive behavior cho panel_main
                panel_main.addComponentListener(new ComponentAdapter() {
                        @Override
                        public void componentResized(ComponentEvent e) {
                                handlePanelMainResize();
                        }
                });
        }

        /**
         * Override form-generated settings for responsive behavior
         */
        private void overridePanelMainSettings() {
                // Remove ALL fixed size constraints set by form designer
                panel_main.setPreferredSize(null);
                panel_main.setMaximumSize(null);
                panel_main.setMinimumSize(null);

                // Set responsive layout (override GroupLayout from form)
                panel_main.setLayout(new BorderLayout());
                panel_main.setBackground(ModernTheme.BACKGROUND_COLOR);

                // Ensure panel can expand and fill available space
                panel_main.setOpaque(true);

                // Force immediate layout update
                panel_main.revalidate();
                panel_main.repaint();
        }

        /**
         * Handle resize events cho MainScreen
         */
        private void handleMainScreenResize() {
                // Cập nhật size cho panel_main khi MainScreen resize
                if (panel_main.getComponentCount() > 0) {
                        java.awt.Component currentPanel = panel_main.getComponent(0);
                        if (currentPanel instanceof JPanel) {
                                currentPanel.setPreferredSize(
                                                new Dimension(panel_main.getWidth(), panel_main.getHeight()));
                                currentPanel.revalidate();
                        }
                }
        }

        /**
         * Handle resize events cho panel_main
         */
        private void handlePanelMainResize() {
                // Apply responsive behavior cho panel con hiện tại
                if (panel_main.getComponentCount() > 0) {
                        java.awt.Component currentPanel = panel_main.getComponent(0);
                        if (currentPanel instanceof JPanel) {
                                ResponsiveLayoutManager.makeResponsive((JPanel) currentPanel);
                        }
                }
        }

        private void addMenuEvents() {
                // Sản phẩm
                txt_sanpham.addMouseListener(new java.awt.event.MouseAdapter() {
                        public void mouseClicked(java.awt.event.MouseEvent evt) {
                                showPanel(panelSanPham);
                        }
                });
                txt_sanpham.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

                // Thanh toán
                txt_thanhtoan.addMouseListener(new java.awt.event.MouseAdapter() {
                        public void mouseClicked(java.awt.event.MouseEvent evt) {
                                showPanel(panelThanhToan);
                        }
                });
                txt_thanhtoan.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

                // Hóa đơn - Quản lý hóa đơn
                txt_hoadon.addMouseListener(new java.awt.event.MouseAdapter() {
                        public void mouseClicked(java.awt.event.MouseEvent evt) {
                                showPanel(panelHoaDon);
                        }
                });
                txt_hoadon.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

                // Thống kê
                txt_thongke.addMouseListener(new java.awt.event.MouseAdapter() {
                        public void mouseClicked(java.awt.event.MouseEvent evt) {
                                showPanel(panelThongKe);
                        }
                });
                txt_thongke.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

                // Khách hàng
                txt_khachhang.addMouseListener(new java.awt.event.MouseAdapter() {
                        public void mouseClicked(java.awt.event.MouseEvent evt) {
                                showPanel(panelKhachHang);
                        }
                });
                txt_khachhang.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

                // Nhân viên
                txt_nhanvien.addMouseListener(new java.awt.event.MouseAdapter() {
                        public void mouseClicked(java.awt.event.MouseEvent evt) {
                                handleNhanVienMenuClick();
                        }
                });
                txt_nhanvien.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

                // Khuyến mãi
                txt_khuyenmai.addMouseListener(new java.awt.event.MouseAdapter() {
                        public void mouseClicked(java.awt.event.MouseEvent evt) {
                                showPanel(panelKhuyenMai);
                        }
                });
                txt_khuyenmai.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

                // Đổi mật khẩu
                txt_doimatkhau.addMouseListener(new java.awt.event.MouseAdapter() {
                        public void mouseClicked(java.awt.event.MouseEvent evt) {
                                showDoiMatKhauDialog();
                        }
                });
                txt_doimatkhau.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

                // Đăng xuất
                txt_dangxuat.addMouseListener(new java.awt.event.MouseAdapter() {
                        public void mouseClicked(java.awt.event.MouseEvent evt) {
                                logout();
                        }
                });
                txt_dangxuat.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        }

        /**
         * Cập nhật title với thông tin user đang đăng nhập
         */
        private void updateTitleWithUserInfo() {
                UserSession session = UserSession.getInstance();
                if (session.isLoggedIn()) {
                        String title = "Hệ Thống Quản Lý Cửa Hàng Thời Trang - " + session.getDisplayInfo();
                        setTitle(title);
                } else {
                        setTitle("Hệ Thống Quản Lý Cửa Hàng Thời Trang");
                }
        }

        /**
         * Áp dụng phân quyền dựa trên loại user
         */
        private void applyUserPermissions() {
                UserSession session = UserSession.getInstance();

                if (!session.isLoggedIn()) {
                        // Nếu chưa đăng nhập, ẩn tất cả menu
                      //  hideAllMenus();
                        return;
                }

                if (session.isEmployee()) {
                        // Nhân viên: Ẩn menu Nhân viên
                        txt_nhanvien.setVisible(false);
                        jLabel14.setVisible(false); // Icon nhân viên

                        // Hiển thị thông báo quyền hạn
                        JOptionPane.showMessageDialog(this,
                                        "Bạn đang đăng nhập với quyền Nhân viên.\n" +
                                                        "Một số chức năng quản lý sẽ bị hạn chế.",
                                        "Thông tin quyền hạn", JOptionPane.INFORMATION_MESSAGE);
                } else if (session.isManager()) {
                        // Quản lý: Hiển thị tất cả menu
                        txt_nhanvien.setVisible(true);
                        jLabel14.setVisible(true);
                }
        }

        /**
         * Ẩn tất cả menu (dùng khi chưa đăng nhập)
         */
        private void hideAllMenus() {
                txt_sanpham.setVisible(false);
                txt_hoadon.setVisible(false);
                txt_thongke.setVisible(false);
                txt_khachhang.setVisible(false);
                txt_nhanvien.setVisible(false);
                txt_khuyenmai.setVisible(false);
        }

        /**
         * Xử lý click vào menu Nhân viên với kiểm tra quyền
         */
        private void handleNhanVienMenuClick() {
                UserSession session = UserSession.getInstance();

                if (!session.isLoggedIn()) {
                        JOptionPane.showMessageDialog(this,
                                        "Bạn cần đăng nhập để truy cập chức năng này!",
                                        "Yêu cầu đăng nhập", JOptionPane.WARNING_MESSAGE);
                        return;
                }

                if (!session.canAccessEmployeeManagement()) {
                        JOptionPane.showMessageDialog(this,
                                        "Bạn không có quyền truy cập chức năng quản lý nhân viên!\n" +
                                                        "Chỉ tài khoản Quản lý mới được phép sử dụng chức năng này.",
                                        "Không đủ quyền hạn", JOptionPane.ERROR_MESSAGE);
                        return;
                }

                // Có quyền truy cập, hiển thị panel nhân viên
                showPanel(panelNhanVien);
        }

        private void showDoiMatKhauDialog() {
                JOptionPane.showMessageDialog(this, "Chức năng đổi mật khẩu đang được phát triển", "Thông báo",
                                JOptionPane.INFORMATION_MESSAGE);
        }

        private void logout() {
                UserSession session = UserSession.getInstance();
                String userInfo = session.isLoggedIn() ? session.getDisplayInfo() : "người dùng";

                int confirm = JOptionPane.showConfirmDialog(this,
                                "Bạn có muốn đăng xuất khỏi tài khoản " + userInfo + " không?",
                                "Xác nhận đăng xuất",
                                JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {
                        // Clear session
                        session.logout();

                        this.dispose(); // Đóng cửa sổ hiện tại

                        // Tạo và hiển thị cửa sổ đăng nhập mới
                        javax.swing.JFrame loginFrame = new javax.swing.JFrame("Đăng nhập");
                        loginFrame.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
                        loginFrame.getContentPane().add(new Login());
                        loginFrame.pack();
                        loginFrame.setLocationRelativeTo(null);
                        loginFrame.setVisible(true);
                }
        }

        /**
         * This method is called from within the constructor to initialize the form.
         * WARNING: Do NOT modify this code. The content of this method is always
         * regenerated by the Form Editor.
         */
        @SuppressWarnings("unchecked")
        // <editor-fold defaultstate="collapsed" desc="Generated
        // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txt_sanpham = new javax.swing.JLabel();
        txt_khachhang = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txt_nhanvien = new javax.swing.JLabel();
        txt_thanhtoan = new javax.swing.JLabel();
        txt_hoadon = new javax.swing.JLabel();
        txt_doimatkhau = new javax.swing.JLabel();
        txt_khuyenmai = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        txt_dangxuat = new javax.swing.JLabel();
        txt_thongke = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        panel_main = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        txt_sanpham.setFont(new java.awt.Font("Helvetica Neue", 1, 16)); // NOI18N
        txt_sanpham.setText("Sản phẩm");

        txt_khachhang.setFont(new java.awt.Font("Helvetica Neue", 1, 16)); // NOI18N
        txt_khachhang.setText("Khách hàng");

        jLabel2.setFont(new java.awt.Font("Helvetica Neue", 1, 20)); // NOI18N
        jLabel2.setText("SHOP XỊN");

        txt_nhanvien.setFont(new java.awt.Font("Helvetica Neue", 1, 16)); // NOI18N
        txt_nhanvien.setText("Nhân viên");

        txt_thanhtoan.setFont(new java.awt.Font("Helvetica Neue", 1, 16)); // NOI18N
        txt_thanhtoan.setText("Thanh toán");

        txt_hoadon.setFont(new java.awt.Font("Helvetica Neue", 1, 16)); // NOI18N
        txt_hoadon.setText("Hóa đơn");

        txt_doimatkhau.setFont(new java.awt.Font("Helvetica Neue", 1, 16)); // NOI18N
        txt_doimatkhau.setText("Đổi mật khẩu");

        txt_khuyenmai.setFont(new java.awt.Font("Helvetica Neue", 1, 16)); // NOI18N
        txt_khuyenmai.setText("Khuyến mại");

        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/images/logo-new.png"))); // NOI18N

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/images/icon-thongke.png"))); // NOI18N

        jLabel20.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/images/icon-dangxuat.png"))); // NOI18N

        txt_dangxuat.setFont(new java.awt.Font("Helvetica Neue", 1, 16)); // NOI18N
        txt_dangxuat.setText("Đăng xuất");

        txt_thongke.setFont(new java.awt.Font("Helvetica Neue", 1, 16)); // NOI18N
        txt_thongke.setText("Thống kê");

        jLabel18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/images/icon-khuyenmai.png"))); // NOI18N

        jLabel19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/images/icon-doimatkhau.png"))); // NOI18N

        jLabel16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/images/icon-khachang.png"))); // NOI18N

        jLabel15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/images/icon-hoadon.png"))); // NOI18N

        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/images/icon-nhanvien.png"))); // NOI18N

        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/images/icon-sanpham.png"))); // NOI18N

        jLabel21.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/images/icon-hoadon.png"))); // NOI18N

        panel_main.setBackground(new java.awt.Color(250, 250, 250));
        panel_main.setMinimumSize(new java.awt.Dimension(800, 600));
        panel_main.setLayout(new java.awt.BorderLayout());

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 1724, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(19, 19, 19)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txt_doimatkhau, javax.swing.GroupLayout.DEFAULT_SIZE, 145, Short.MAX_VALUE)
                                    .addComponent(txt_dangxuat, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(23, 23, 23)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel1)
                                    .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txt_thanhtoan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txt_hoadon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txt_thongke, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txt_nhanvien, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txt_sanpham, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txt_khachhang, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txt_khuyenmai, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addGap(27, 27, 27)
                        .addComponent(panel_main, javax.swing.GroupLayout.PREFERRED_SIZE, 1570, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(panel_main, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGap(83, 83, 83)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txt_thongke, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_thanhtoan, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_hoadon, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_nhanvien, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_sanpham, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_khachhang, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_khuyenmai, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 208, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txt_doimatkhau, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_dangxuat, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(64, 64, 64))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

        /**
         * @param args the command line arguments
         */
        public static void main(String args[]) {
                /* Set the Nimbus look and feel */
                // <editor-fold defaultstate="collapsed" desc=" Look and feel setting code
                // (optional) ">
                /*
                 * If Nimbus (introduced in Java SE 6) is not available, stay with the default
                 * look and feel.
                 * For details see
                 * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
                 */
                try {
                        for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager
                                        .getInstalledLookAndFeels()) {
                                if ("Nimbus".equals(info.getName())) {
                                        javax.swing.UIManager.setLookAndFeel(info.getClassName());
                                        break;
                                }
                        }
                } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
                        logger.log(java.util.logging.Level.SEVERE, null, ex);
                }
                // </editor-fold>

                /* Create and display the form */
                java.awt.EventQueue.invokeLater(() -> new MainScreen().setVisible(true));
        }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JPanel panel_main;
    private javax.swing.JLabel txt_dangxuat;
    private javax.swing.JLabel txt_doimatkhau;
    private javax.swing.JLabel txt_hoadon;
    private javax.swing.JLabel txt_khachhang;
    private javax.swing.JLabel txt_khuyenmai;
    private javax.swing.JLabel txt_nhanvien;
    private javax.swing.JLabel txt_sanpham;
    private javax.swing.JLabel txt_thanhtoan;
    private javax.swing.JLabel txt_thongke;
    // End of variables declaration//GEN-END:variables
}
