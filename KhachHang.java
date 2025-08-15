/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package screen;

import com.mycompany.shop.dao.khach_hang_dao;
import com.mycompany.shop.dao.hoa_don_dao;
import com.mycompany.shop.model.khach_hang;
import com.mycompany.shop.model.hoa_don;
import com.mycompany.shop.util.CurrencyUtils;
import com.mycompany.shop.util.ModernTheme;
import com.mycompany.shop.util.UIHelper;
import com.mycompany.shop.util.TableOptimizer;
import com.mycompany.shop.util.ResponsiveLayoutManager;
import com.mycompany.shop.util.ScrollPaneOptimizer;
import com.mycompany.shop.util.ComponentOptimizer;
import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import java.util.List;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author nguyenthanhquoc
 */
public class KhachHang extends javax.swing.JPanel {

        // DAO instances
        private khach_hang_dao khachHangDAO;
        private hoa_don_dao hoaDonDAO;

        // Table models
        private DefaultTableModel modelKhachHang;
        private DefaultTableModel modelHoaDon;

        // Data lists
        private List<khach_hang> danhSachKhachHang;
        private List<hoa_don> danhSachHoaDonKhachHang;

        // Current selected customer
        private khach_hang khachHangDangChon = null;
        private boolean isEditing = false;

        /**
         * Creates new form KhachHang
         */
        public KhachHang() {
                initComponents();
                initDAO();
                setupComponents();
                loadData();
                setupEventHandlers();
                setupDataRefresh();
                applyModernTheme();
        }

        private void applyModernTheme() {
                // Apply modern theme with responsive design
                UIHelper.applyModernTheme(this);

                // Style search components with responsive sizing
                ModernTheme.styleTextField(jTextField1, ModernTheme.ButtonSize.MEDIUM);
                ModernTheme.styleButton(jButton1, ModernTheme.ButtonType.PRIMARY, ModernTheme.ButtonSize.MEDIUM);

                // Style input fields with responsive sizing
                ModernTheme.styleTextField(input_ma_khach_hang, ModernTheme.ButtonSize.MEDIUM);
                ModernTheme.styleTextField(input_ho_va_ten_khach_hang, ModernTheme.ButtonSize.MEDIUM);
                ModernTheme.styleTextField(input_so_dien_thoai_khach_hang, ModernTheme.ButtonSize.MEDIUM);
                ModernTheme.styleTextField(input_dia_chi_khach_hang, ModernTheme.ButtonSize.MEDIUM);
                ModernTheme.styleTextField(input_gioi_tinh_khach_hang, ModernTheme.ButtonSize.SMALL);
                ModernTheme.styleTextField(input_trang_thai_khach_hang, ModernTheme.ButtonSize.SMALL);

                // Style CRUD buttons with appropriate sizes
                ModernTheme.styleButton(btn_add, ModernTheme.ButtonType.SUCCESS, ModernTheme.ButtonSize.MEDIUM);
                ModernTheme.styleButton(btn_edit, ModernTheme.ButtonType.PRIMARY, ModernTheme.ButtonSize.MEDIUM);
                ModernTheme.styleButton(btn_delete, ModernTheme.ButtonType.DANGER, ModernTheme.ButtonSize.MEDIUM);

                // Style tables with responsive behavior
                ModernTheme.styleTable(jTable1);
                ModernTheme.styleTable(jTable2);

                // Update labels styling
                updateLabelsTheme();

                // Set background
                this.setBackground(ModernTheme.BACKGROUND_COLOR);

                // Make the entire panel responsive
                UIHelper.makeComponentsResponsive(this);
        }

        private void updateLabelsTheme() {
                // Update existing labels with modern font and colors
                try {
                        jLabel1.setFont(ModernTheme.FONT_BODY);
                        jLabel1.setForeground(ModernTheme.TEXT_PRIMARY);
                } catch (Exception e) {
                }

                try {
                        jLabel2.setFont(ModernTheme.FONT_BODY);
                        jLabel2.setForeground(ModernTheme.TEXT_PRIMARY);
                } catch (Exception e) {
                }

                try {
                        jLabel3.setFont(ModernTheme.FONT_BODY);
                        jLabel3.setForeground(ModernTheme.TEXT_PRIMARY);
                } catch (Exception e) {
                }
        }

        /**
         * Initialize DAO instances
         */
        private void initDAO() {
                khachHangDAO = new khach_hang_dao();
                hoaDonDAO = new hoa_don_dao();
        }

        /**
         * Setup table models and components
         */
        private void setupComponents() {
                // Setup customer table model
                modelKhachHang = (DefaultTableModel) jTable1.getModel();
                modelKhachHang.setColumnIdentifiers(new String[] {
                                "STT", "Mã KH", "Họ và tên", "Số điện thoại", "Địa chỉ", "Giới tính", "Trạng thái"
                });

                // Setup invoice table model
                modelHoaDon = (DefaultTableModel) jTable2.getModel();
                modelHoaDon.setColumnIdentifiers(new String[] {
                                "STT", "Mã hóa đơn", "Ngày tạo", "Tổng tiền", "Nhân viên", "Voucher"
                });

                // Optimize tables for better display
                TableOptimizer.optimizeForScreen(jTable1, "khachhang");
                TableOptimizer.optimizeForScreen(jTable2, "khachhang");

                // Make layout responsive
                ResponsiveLayoutManager.makeResponsive(this);

                // Optimize scroll panes
                ScrollPaneOptimizer.optimizeScrollPanes(this);

                // Optimize components (buttons, text fields, etc.)
                ComponentOptimizer.optimizeComponents(this);

                // Setup gender combobox
                setupGenderComboBox();

                // Setup status combobox
                setupStatusComboBox();

                // Clear default text
                clearFormInputs();
        }

        /**
         * Setup gender combobox
         */
        private void setupGenderComboBox() {
                // Replace text field with combobox for gender
                // This would require form redesign, for now we'll use validation
        }

        /**
         * Setup status combobox
         */
        private void setupStatusComboBox() {
                // Replace text field with combobox for status
                // This would require form redesign, for now we'll use validation
        }

        /**
         * Setup data refresh callbacks
         */
        private void setupDataRefresh() {
                // Create refresh action for customer data
                Runnable refreshCustomers = com.mycompany.shop.util.DataRefreshManager.createSafeRefresh(() -> {
                        loadDanhSachKhachHang();
                        hienThiDanhSachKhachHang();
                }, "Khách hàng");

                // Register refresh callbacks
                com.mycompany.shop.util.DataRefreshManager.registerRefreshCallback(
                                com.mycompany.shop.util.DataRefreshManager.CUSTOMER_CHANGED, refreshCustomers);
        }

        /**
         * Load data from database
         */
        private void loadData() {
                loadDanhSachKhachHang();
                hienThiDanhSachKhachHang();
        }

        /**
         * Load customer list from database
         */
        private void loadDanhSachKhachHang() {
                try {
                        danhSachKhachHang = khachHangDAO.getAllKhachHang();
                } catch (Exception e) {
                        JOptionPane.showMessageDialog(this, "Lỗi khi tải danh sách khách hàng: " + e.getMessage(),
                                        "Lỗi", JOptionPane.ERROR_MESSAGE);
                        e.printStackTrace();
                }
        }

        /**
         * Display customer list in table
         */
        private void hienThiDanhSachKhachHang() {
                modelKhachHang.setRowCount(0);

                if (danhSachKhachHang != null) {
                        for (int i = 0; i < danhSachKhachHang.size(); i++) {
                                khach_hang kh = danhSachKhachHang.get(i);
                                modelKhachHang.addRow(new Object[] {
                                                i + 1,
                                                kh.getMa_khach_hang(),
                                                kh.getHo_va_ten(),
                                                kh.getSo_dien_thoai(),
                                                kh.getDia_chi(),
                                                kh.getGioi_tinh(),
                                                kh.getTrang_thai()
                                });
                        }
                }
        }

        /**
         * Setup event handlers
         */
        private void setupEventHandlers() {
                // Table selection event
                jTable1.getSelectionModel().addListSelectionListener(e -> {
                        if (!e.getValueIsAdjusting()) {
                                int selectedRow = jTable1.getSelectedRow();
                                if (selectedRow >= 0) {
                                        chonKhachHang(selectedRow);
                                }
                        }
                });

                // Search button event
                jButton1.addActionListener(e -> timKiemKhachHang());

                // CRUD button events
                btn_add.addActionListener(e -> themKhachHang());
                btn_edit.addActionListener(e -> suaKhachHang());
                btn_delete.addActionListener(e -> xoaKhachHang());

                // Enter key for search
                jTextField1.addActionListener(e -> timKiemKhachHang());
        }

        /**
         * This method is called from within the constructor to initialize the form.
         * WARNING: Do NOT modify this code. The content of this method is always
         * regenerated by the Form Editor.
         */
        @SuppressWarnings("unchecked")
        // <editor-fold defaultstate="collapsed" desc="Generated
        // <editor-fold defaultstate="collapsed" desc="Generated
        // <editor-fold defaultstate="collapsed" desc="Generated
        // Code">//GEN-BEGIN:initComponents
        private void initComponents() {

                jPanel1 = new javax.swing.JPanel();
                jScrollPane1 = new javax.swing.JScrollPane();
                jTable1 = new javax.swing.JTable();
                jLabel1 = new javax.swing.JLabel();
                jTextField1 = new javax.swing.JTextField();
                jButton1 = new javax.swing.JButton();
                jPanel2 = new javax.swing.JPanel();
                jLabel2 = new javax.swing.JLabel();
                jLabel3 = new javax.swing.JLabel();
                jLabel5 = new javax.swing.JLabel();
                jLabel7 = new javax.swing.JLabel();
                jLabel9 = new javax.swing.JLabel();
                jLabel11 = new javax.swing.JLabel();
                jLabel14 = new javax.swing.JLabel();
                jLabel15 = new javax.swing.JLabel();
                jScrollPane2 = new javax.swing.JScrollPane();
                jTable2 = new javax.swing.JTable();
                input_ma_khach_hang = new javax.swing.JTextField();
                input_ho_va_ten_khach_hang = new javax.swing.JTextField();
                input_so_dien_thoai_khach_hang = new javax.swing.JTextField();
                input_dia_chi_khach_hang = new javax.swing.JTextField();
                input_gioi_tinh_khach_hang = new javax.swing.JTextField();
                input_trang_thai_khach_hang = new javax.swing.JTextField();
                btn_add = new javax.swing.JButton();
                btn_edit = new javax.swing.JButton();
                btn_delete = new javax.swing.JButton();

                setMinimumSize(new java.awt.Dimension(800, 600));
                setPreferredSize(new java.awt.Dimension(1200, 800));

                jPanel1.setBackground(new java.awt.Color(255, 255, 255));

                jTable1.setModel(new javax.swing.table.DefaultTableModel(
                                new Object[][] {
                                                { null, null, null },
                                                { null, null, null },
                                                { null, null, null },
                                                { null, null, null },
                                                { null, null, null },
                                                { null, null, null },
                                                { null, null, null },
                                                { null, null, null },
                                                { null, null, null },
                                                { null, null, null },
                                                { null, null, null },
                                                { null, null, null },
                                                { null, null, null },
                                                { null, null, null },
                                                { null, null, null },
                                                { null, null, null },
                                                { null, null, null },
                                                { null, null, null },
                                                { null, null, null },
                                                { null, null, null }
                                },
                                new String[] {
                                                "ID Khách", "Tên khách", "Hóa đơn"
                                }));
                jScrollPane1.setViewportView(jTable1);
                if (jTable1.getColumnModel().getColumnCount() > 0) {
                        jTable1.getColumnModel().getColumn(2).setResizable(false);
                }

                jLabel1.setFont(new java.awt.Font("Helvetica Neue", 1, 13)); // NOI18N
                jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
                jLabel1.setText("DANH SÁCH KHÁCH HÀNG");

                jButton1.setText("Tìm");
                jButton1.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                jButton1ActionPerformed(evt);
                        }
                });

                javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
                jPanel1.setLayout(jPanel1Layout);
                jPanel1Layout.setHorizontalGroup(
                                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(jPanel1Layout.createSequentialGroup()
                                                                .addContainerGap()
                                                                .addGroup(jPanel1Layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                .addComponent(jScrollPane1,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                964, Short.MAX_VALUE)
                                                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
                                                                                                jPanel1Layout.createSequentialGroup()
                                                                                                                .addGap(0, 0, Short.MAX_VALUE)
                                                                                                                .addComponent(jLabel1,
                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                278,
                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
                                                                                                jPanel1Layout.createSequentialGroup()
                                                                                                                .addComponent(jTextField1)
                                                                                                                .addPreferredGap(
                                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                                                                .addComponent(jButton1,
                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                79,
                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                                .addGap(41, 41, 41)))
                                                                .addContainerGap()));
                jPanel1Layout.setVerticalGroup(
                                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout
                                                                .createSequentialGroup()
                                                                .addContainerGap()
                                                                .addComponent(jLabel1,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                17,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGap(18, 18, 18)
                                                                .addGroup(jPanel1Layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                .addComponent(jTextField1,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                .addComponent(jButton1))
                                                                .addGap(21, 21, 21)
                                                                .addComponent(jScrollPane1)
                                                                .addContainerGap()));

                jPanel2.setBackground(new java.awt.Color(255, 255, 255));

                jLabel2.setFont(new java.awt.Font("Helvetica Neue", 1, 13)); // NOI18N
                jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
                jLabel2.setText("CHI TIẾT KHÁCH HÀNG");

                jLabel3.setText("Mã Khách hàng:");

                jLabel5.setText("Họ tên khách:");

                jLabel7.setText("Số điện thoại");

                jLabel9.setText("Địa chỉ");

                jLabel11.setText("Giới tính:");

                jLabel14.setText("Trạng thái:");

                jLabel15.setFont(new java.awt.Font("Helvetica Neue", 1, 13)); // NOI18N
                jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
                jLabel15.setText("CHI TIẾT MUA HÀNG");

                jTable2.setModel(new javax.swing.table.DefaultTableModel(
                                new Object[][] {
                                                { null, null, null, null, null },
                                                { null, null, null, null, null },
                                                { null, null, null, null, null },
                                                { null, null, null, null, null }
                                },
                                new String[] {
                                                "Mã Hóa đơn", "Ngày tạo", "Tổng tiên", "Nhân viên", "Voucher"
                                }));
                jScrollPane2.setViewportView(jTable2);

                input_ma_khach_hang.setText("jTextField2");

                input_ho_va_ten_khach_hang.setText("jTextField2");

                input_so_dien_thoai_khach_hang.setText("jTextField2");

                input_dia_chi_khach_hang.setText("jTextField2");

                input_gioi_tinh_khach_hang.setText("jTextField2");

                input_trang_thai_khach_hang.setText("jTextField2");

                btn_add.setBackground(new java.awt.Color(153, 255, 153));
                btn_add.setText("Thêm");
                btn_add.setToolTipText("");

                btn_edit.setBackground(new java.awt.Color(255, 255, 153));
                btn_edit.setText("Sửa");

                btn_delete.setBackground(new java.awt.Color(255, 153, 153));
                btn_delete.setText("Xóa");

                javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
                jPanel2.setLayout(jPanel2Layout);
                jPanel2Layout.setHorizontalGroup(
                                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(jPanel2Layout.createSequentialGroup()
                                                                .addComponent(jLabel2,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                278,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGap(0, 0, Short.MAX_VALUE))
                                                .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addGroup(jPanel2Layout.createSequentialGroup()
                                                                .addContainerGap()
                                                                .addGroup(jPanel2Layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                .addComponent(jScrollPane2,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                558, Short.MAX_VALUE)
                                                                                .addGroup(jPanel2Layout
                                                                                                .createSequentialGroup()
                                                                                                .addGroup(jPanel2Layout
                                                                                                                .createParallelGroup(
                                                                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                                                .addGroup(jPanel2Layout
                                                                                                                                .createSequentialGroup()
                                                                                                                                .addComponent(jLabel3,
                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                111,
                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                                                .addPreferredGap(
                                                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                                                .addComponent(input_ma_khach_hang,
                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                240,
                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                                                                .addGroup(jPanel2Layout
                                                                                                                                .createSequentialGroup()
                                                                                                                                .addGroup(jPanel2Layout
                                                                                                                                                .createParallelGroup(
                                                                                                                                                                javax.swing.GroupLayout.Alignment.LEADING,
                                                                                                                                                                false)
                                                                                                                                                .addComponent(jLabel5,
                                                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                                                111,
                                                                                                                                                                Short.MAX_VALUE)
                                                                                                                                                .addComponent(jLabel7,
                                                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                                                Short.MAX_VALUE)
                                                                                                                                                .addComponent(jLabel9,
                                                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                                                Short.MAX_VALUE)
                                                                                                                                                .addComponent(jLabel11,
                                                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                                                Short.MAX_VALUE)
                                                                                                                                                .addComponent(jLabel14,
                                                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                                                Short.MAX_VALUE))
                                                                                                                                .addPreferredGap(
                                                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                                                .addGroup(jPanel2Layout
                                                                                                                                                .createParallelGroup(
                                                                                                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                                                                                .addComponent(input_ho_va_ten_khach_hang,
                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                                240,
                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                                                                .addComponent(input_so_dien_thoai_khach_hang,
                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                                240,
                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                                                                .addComponent(input_dia_chi_khach_hang,
                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                                240,
                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                                                                .addComponent(input_gioi_tinh_khach_hang,
                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                                240,
                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                                                                .addComponent(input_trang_thai_khach_hang,
                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                                240,
                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))))
                                                                                                .addPreferredGap(
                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                Short.MAX_VALUE)
                                                                                                .addGroup(jPanel2Layout
                                                                                                                .createParallelGroup(
                                                                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                                                .addComponent(btn_add)
                                                                                                                .addGroup(jPanel2Layout
                                                                                                                                .createParallelGroup(
                                                                                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                                                                .addComponent(btn_edit,
                                                                                                                                                javax.swing.GroupLayout.Alignment.TRAILING)
                                                                                                                                .addComponent(btn_delete,
                                                                                                                                                javax.swing.GroupLayout.Alignment.TRAILING)))
                                                                                                .addContainerGap()))));
                jPanel2Layout.setVerticalGroup(
                                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(jPanel2Layout.createSequentialGroup()
                                                                .addContainerGap()
                                                                .addComponent(jLabel2,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                17,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGap(18, 18, 18)
                                                                .addGroup(jPanel2Layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                .addComponent(jLabel3)
                                                                                .addComponent(input_ma_khach_hang,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                .addComponent(btn_add))
                                                                .addGroup(jPanel2Layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                .addGroup(jPanel2Layout
                                                                                                .createSequentialGroup()
                                                                                                .addPreferredGap(
                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                                                .addGroup(jPanel2Layout
                                                                                                                .createParallelGroup(
                                                                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                                                .addComponent(jLabel5)
                                                                                                                .addComponent(input_ho_va_ten_khach_hang,
                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                                                .addPreferredGap(
                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                .addGroup(jPanel2Layout
                                                                                                                .createParallelGroup(
                                                                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                                                .addComponent(jLabel7)
                                                                                                                .addComponent(input_so_dien_thoai_khach_hang,
                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                                                .addGroup(jPanel2Layout
                                                                                                .createSequentialGroup()
                                                                                                .addGap(22, 22, 22)
                                                                                                .addComponent(btn_edit)))
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                .addGroup(jPanel2Layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                .addComponent(jLabel9)
                                                                                .addComponent(input_dia_chi_khach_hang,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                .addComponent(btn_delete))
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                .addGroup(jPanel2Layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                .addComponent(jLabel11)
                                                                                .addComponent(input_gioi_tinh_khach_hang,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                .addGroup(jPanel2Layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                .addComponent(jLabel14)
                                                                                .addComponent(input_trang_thai_khach_hang,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addGap(24, 24, 24)
                                                                .addComponent(jLabel15,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                17,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGap(18, 18, 18)
                                                                .addComponent(jScrollPane2,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                520, Short.MAX_VALUE)
                                                                .addContainerGap()));

                javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
                this.setLayout(layout);
                layout.setHorizontalGroup(
                                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(layout.createSequentialGroup()
                                                                .addContainerGap()
                                                                .addComponent(jPanel1,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                Short.MAX_VALUE)
                                                                .addGap(18, 18, 18)
                                                                .addComponent(jPanel2,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addContainerGap()));
                layout.setVerticalGroup(
                                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(layout.createSequentialGroup()
                                                                .addContainerGap()
                                                                .addGroup(layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                .addComponent(jPanel1,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                Short.MAX_VALUE)
                                                                                .addComponent(jPanel2,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                Short.MAX_VALUE))
                                                                .addContainerGap()));
        }// </editor-fold>//GEN-END:initComponents

        private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton1ActionPerformed
                // TODO add your handling code here:
        }// GEN-LAST:event_jButton1ActionPerformed

        // Variables declaration - do not modify//GEN-BEGIN:variables
        private javax.swing.JButton btn_add;
        private javax.swing.JButton btn_delete;
        private javax.swing.JButton btn_edit;
        private javax.swing.JTextField input_dia_chi_khach_hang;
        private javax.swing.JTextField input_gioi_tinh_khach_hang;
        private javax.swing.JTextField input_ho_va_ten_khach_hang;
        private javax.swing.JTextField input_ma_khach_hang;
        private javax.swing.JTextField input_so_dien_thoai_khach_hang;
        private javax.swing.JTextField input_trang_thai_khach_hang;
        private javax.swing.JButton jButton1;
        private javax.swing.JLabel jLabel1;
        private javax.swing.JLabel jLabel11;
        private javax.swing.JLabel jLabel14;
        private javax.swing.JLabel jLabel15;
        private javax.swing.JLabel jLabel2;
        private javax.swing.JLabel jLabel3;
        private javax.swing.JLabel jLabel5;
        private javax.swing.JLabel jLabel7;
        private javax.swing.JLabel jLabel9;
        private javax.swing.JPanel jPanel1;
        private javax.swing.JPanel jPanel2;
        private javax.swing.JScrollPane jScrollPane1;
        private javax.swing.JScrollPane jScrollPane2;
        private javax.swing.JTable jTable1;
        private javax.swing.JTable jTable2;
        private javax.swing.JTextField jTextField1;
        // End of variables declaration//GEN-END:variables

        /**
         * Clear form inputs
         */
        private void clearFormInputs() {
                input_ma_khach_hang.setText("");
                input_ho_va_ten_khach_hang.setText("");
                input_so_dien_thoai_khach_hang.setText("");
                input_dia_chi_khach_hang.setText("");
                input_gioi_tinh_khach_hang.setText("");
                input_trang_thai_khach_hang.setText("");

                khachHangDangChon = null;
                isEditing = false;

                // Enable/disable buttons
                btn_add.setText("Thêm");
                btn_edit.setEnabled(false);
                btn_delete.setEnabled(false);
        }

        /**
         * Select customer from table
         */
        private void chonKhachHang(int selectedRow) {
                if (selectedRow >= 0 && selectedRow < danhSachKhachHang.size()) {
                        khachHangDangChon = danhSachKhachHang.get(selectedRow);

                        // Fill form with selected customer data
                        input_ma_khach_hang.setText(khachHangDangChon.getMa_khach_hang());
                        input_ho_va_ten_khach_hang.setText(khachHangDangChon.getHo_va_ten());
                        input_so_dien_thoai_khach_hang.setText(khachHangDangChon.getSo_dien_thoai());
                        input_dia_chi_khach_hang.setText(khachHangDangChon.getDia_chi());
                        input_gioi_tinh_khach_hang.setText(khachHangDangChon.getGioi_tinh());
                        input_trang_thai_khach_hang.setText(khachHangDangChon.getTrang_thai());

                        // Enable edit/delete buttons
                        btn_edit.setEnabled(true);
                        btn_delete.setEnabled(true);

                        // Load customer's invoices
                        loadHoaDonKhachHang(khachHangDangChon.getId_khach_hang());
                }
        }

        /**
         * Load customer's invoices
         */
        private void loadHoaDonKhachHang(int idKhachHang) {
                try {
                        danhSachHoaDonKhachHang = hoaDonDAO.getHoaDonByKhachHang(idKhachHang);
                        hienThiHoaDonKhachHang();
                } catch (Exception e) {
                        JOptionPane.showMessageDialog(this, "Lỗi khi tải hóa đơn khách hàng: " + e.getMessage(),
                                        "Lỗi", JOptionPane.ERROR_MESSAGE);
                        e.printStackTrace();
                }
        }

        /**
         * Display customer's invoices
         */
        private void hienThiHoaDonKhachHang() {
                modelHoaDon.setRowCount(0);

                if (danhSachHoaDonKhachHang != null) {
                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

                        for (int i = 0; i < danhSachHoaDonKhachHang.size(); i++) {
                                hoa_don hd = danhSachHoaDonKhachHang.get(i);

                                String ngayTao = sdf.format(new Date(hd.getNgay_tao().getTime()));
                                String voucherInfo = hoaDonDAO.getVoucherInfo(hd.getId_voucher());

                                modelHoaDon.addRow(new Object[] {
                                                i + 1,
                                                hd.getMa_hoa_don(),
                                                ngayTao,
                                                CurrencyUtils.formatVND(hd.getTong_tien()),
                                                "NV" + hd.getId_nhan_vien(),
                                                voucherInfo != null ? voucherInfo : "Không có"
                                });
                        }
                }
        }

        /**
         * Search customers
         */
        private void timKiemKhachHang() {
                String keyword = jTextField1.getText().trim();

                if (keyword.isEmpty()) {
                        // Show all customers
                        loadDanhSachKhachHang();
                        hienThiDanhSachKhachHang();
                        return;
                }

                try {
                        List<khach_hang> ketQuaTimKiem;

                        // Search by name first
                        ketQuaTimKiem = khachHangDAO.searchKhachHangByName(keyword);

                        // If not found by name, try phone
                        if (ketQuaTimKiem.isEmpty()) {
                                ketQuaTimKiem = khachHangDAO.searchKhachHangByPhone(keyword);
                        }

                        // Update display
                        danhSachKhachHang = ketQuaTimKiem;
                        hienThiDanhSachKhachHang();

                        if (ketQuaTimKiem.isEmpty()) {
                                JOptionPane.showMessageDialog(this, "Không tìm thấy khách hàng nào!",
                                                "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                        }

                } catch (Exception e) {
                        JOptionPane.showMessageDialog(this, "Lỗi khi tìm kiếm: " + e.getMessage(),
                                        "Lỗi", JOptionPane.ERROR_MESSAGE);
                        e.printStackTrace();
                }
        }

        /**
         * Add new customer
         */
        private void themKhachHang() {
                if (isEditing) {
                        // Cancel edit mode
                        clearFormInputs();
                        return;
                }

                // Validate input
                if (!validateInput()) {
                        return;
                }

                try {
                        String maKH = input_ma_khach_hang.getText().trim();
                        String hoTen = input_ho_va_ten_khach_hang.getText().trim();
                        String sdt = input_so_dien_thoai_khach_hang.getText().trim();
                        String diaChi = input_dia_chi_khach_hang.getText().trim();
                        String gioiTinh = input_gioi_tinh_khach_hang.getText().trim();
                        String trangThai = input_trang_thai_khach_hang.getText().trim();

                        // Check if customer code already exists
                        khach_hang existingKH = khachHangDAO.getKhachHangByMa(maKH);
                        if (existingKH != null) {
                                JOptionPane.showMessageDialog(this, "Mã khách hàng đã tồn tại!",
                                                "Lỗi", JOptionPane.ERROR_MESSAGE);
                                return;
                        }

                        // Check if phone number already exists
                        if (khachHangDAO.checkSoDienThoaiExists(sdt)) {
                                JOptionPane.showMessageDialog(this, "Số điện thoại đã được sử dụng!",
                                                "Lỗi", JOptionPane.ERROR_MESSAGE);
                                return;
                        }

                        // Add customer
                        khachHangDAO.addKhachHang(maKH, hoTen, sdt, diaChi, gioiTinh, trangThai);

                        JOptionPane.showMessageDialog(this, "Thêm khách hàng thành công!",
                                        "Thành công", JOptionPane.INFORMATION_MESSAGE);

                        // Refresh data and clear form
                        loadData();
                        clearFormInputs();

                } catch (Exception e) {
                        JOptionPane.showMessageDialog(this, "Lỗi khi thêm khách hàng: " + e.getMessage(),
                                        "Lỗi", JOptionPane.ERROR_MESSAGE);
                        e.printStackTrace();
                }
        }

        /**
         * Edit customer
         */
        private void suaKhachHang() {
                if (khachHangDangChon == null) {
                        JOptionPane.showMessageDialog(this, "Vui lòng chọn khách hàng để sửa!",
                                        "Thông báo", JOptionPane.WARNING_MESSAGE);
                        return;
                }

                if (!isEditing) {
                        // Enter edit mode
                        isEditing = true;
                        btn_add.setText("Hủy");
                        btn_edit.setText("Lưu");
                        return;
                }

                // Save changes
                if (!validateInput()) {
                        return;
                }

                try {
                        String maKH = input_ma_khach_hang.getText().trim();
                        String hoTen = input_ho_va_ten_khach_hang.getText().trim();
                        String sdt = input_so_dien_thoai_khach_hang.getText().trim();
                        String diaChi = input_dia_chi_khach_hang.getText().trim();
                        String gioiTinh = input_gioi_tinh_khach_hang.getText().trim();
                        String trangThai = input_trang_thai_khach_hang.getText().trim();

                        // Check if customer code changed and already exists
                        if (!maKH.equals(khachHangDangChon.getMa_khach_hang())) {
                                khach_hang existingKH = khachHangDAO.getKhachHangByMa(maKH);
                                if (existingKH != null) {
                                        JOptionPane.showMessageDialog(this, "Mã khách hàng đã tồn tại!",
                                                        "Lỗi", JOptionPane.ERROR_MESSAGE);
                                        return;
                                }
                        }

                        // Check if phone number changed and already exists
                        if (!sdt.equals(khachHangDangChon.getSo_dien_thoai())) {
                                if (khachHangDAO.checkSoDienThoaiExists(sdt)) {
                                        JOptionPane.showMessageDialog(this, "Số điện thoại đã được sử dụng!",
                                                        "Lỗi", JOptionPane.ERROR_MESSAGE);
                                        return;
                                }
                        }

                        // Update customer
                        khachHangDAO.updateKhachHang(khachHangDangChon.getId_khach_hang(),
                                        maKH, hoTen, sdt, diaChi, gioiTinh, trangThai);

                        JOptionPane.showMessageDialog(this, "Cập nhật khách hàng thành công!",
                                        "Thành công", JOptionPane.INFORMATION_MESSAGE);

                        // Refresh data and clear form
                        loadData();
                        clearFormInputs();

                } catch (Exception e) {
                        JOptionPane.showMessageDialog(this, "Lỗi khi cập nhật khách hàng: " + e.getMessage(),
                                        "Lỗi", JOptionPane.ERROR_MESSAGE);
                        e.printStackTrace();
                }
        }

        /**
         * Delete customer
         */
        private void xoaKhachHang() {
                if (khachHangDangChon == null) {
                        JOptionPane.showMessageDialog(this, "Vui lòng chọn khách hàng để xóa!",
                                        "Thông báo", JOptionPane.WARNING_MESSAGE);
                        return;
                }

                // Confirm deletion
                int confirm = JOptionPane.showConfirmDialog(this,
                                "Bạn có chắc chắn muốn xóa khách hàng: " + khachHangDangChon.getHo_va_ten() + "?\n" +
                                                "Hành động này không thể hoàn tác!",
                                "Xác nhận xóa",
                                JOptionPane.YES_NO_OPTION,
                                JOptionPane.WARNING_MESSAGE);

                if (confirm != JOptionPane.YES_OPTION) {
                        return;
                }

                try {
                        // Check if customer has invoices
                        List<hoa_don> hoaDonList = hoaDonDAO.getHoaDonByKhachHang(khachHangDangChon.getId_khach_hang());
                        if (hoaDonList != null && !hoaDonList.isEmpty()) {
                                JOptionPane.showMessageDialog(this,
                                                "Không thể xóa khách hàng vì đã có " + hoaDonList.size()
                                                                + " hóa đơn liên quan!",
                                                "Không thể xóa", JOptionPane.ERROR_MESSAGE);
                                return;
                        }

                        // Delete customer
                        khachHangDAO.deleteKhachHang(khachHangDangChon.getId_khach_hang());

                        JOptionPane.showMessageDialog(this, "Xóa khách hàng thành công!",
                                        "Thành công", JOptionPane.INFORMATION_MESSAGE);

                        // Refresh data and clear form
                        loadData();
                        clearFormInputs();

                } catch (Exception e) {
                        JOptionPane.showMessageDialog(this, "Lỗi khi xóa khách hàng: " + e.getMessage(),
                                        "Lỗi", JOptionPane.ERROR_MESSAGE);
                        e.printStackTrace();
                }
        }

        /**
         * Validate input fields
         */
        private boolean validateInput() {
                String maKH = input_ma_khach_hang.getText().trim();
                String hoTen = input_ho_va_ten_khach_hang.getText().trim();
                String sdt = input_so_dien_thoai_khach_hang.getText().trim();
                String diaChi = input_dia_chi_khach_hang.getText().trim();
                String gioiTinh = input_gioi_tinh_khach_hang.getText().trim();
                String trangThai = input_trang_thai_khach_hang.getText().trim();

                // Check required fields
                if (maKH.isEmpty()) {
                        JOptionPane.showMessageDialog(this, "Vui lòng nhập mã khách hàng!",
                                        "Lỗi", JOptionPane.ERROR_MESSAGE);
                        input_ma_khach_hang.requestFocus();
                        return false;
                }

                if (hoTen.isEmpty()) {
                        JOptionPane.showMessageDialog(this, "Vui lòng nhập họ tên khách hàng!",
                                        "Lỗi", JOptionPane.ERROR_MESSAGE);
                        input_ho_va_ten_khach_hang.requestFocus();
                        return false;
                }

                if (sdt.isEmpty()) {
                        JOptionPane.showMessageDialog(this, "Vui lòng nhập số điện thoại!",
                                        "Lỗi", JOptionPane.ERROR_MESSAGE);
                        input_so_dien_thoai_khach_hang.requestFocus();
                        return false;
                }

                // Validate phone number format
                if (!sdt.matches("^[0-9]{10,11}$")) {
                        JOptionPane.showMessageDialog(this, "Số điện thoại phải có 10-11 chữ số!",
                                        "Lỗi", JOptionPane.ERROR_MESSAGE);
                        input_so_dien_thoai_khach_hang.requestFocus();
                        return false;
                }

                // Validate gender
                if (!gioiTinh.isEmpty() && !gioiTinh.equalsIgnoreCase("Nam") && !gioiTinh.equalsIgnoreCase("Nữ")) {
                        JOptionPane.showMessageDialog(this, "Giới tính phải là 'Nam' hoặc 'Nữ'!",
                                        "Lỗi", JOptionPane.ERROR_MESSAGE);
                        input_gioi_tinh_khach_hang.requestFocus();
                        return false;
                }

                // Validate status
                if (!trangThai.isEmpty() && !trangThai.equalsIgnoreCase("Khách Mới")
                                && !trangThai.equalsIgnoreCase("Khách Cũ")) {
                        JOptionPane.showMessageDialog(this, "Trạng thái phải là 'Khách Mới' hoặc 'Khách Cũ'!",
                                        "Lỗi", JOptionPane.ERROR_MESSAGE);
                        input_trang_thai_khach_hang.requestFocus();
                        return false;
                }

                return true;
        }
}
