package screen;

import com.mycompany.shop.dao.ao_dao;
import com.mycompany.shop.dao.chi_tiet_hoa_don_dao;
import com.mycompany.shop.dao.hoa_don_dao;
import com.mycompany.shop.dao.khach_hang_dao;
import com.mycompany.shop.dao.loai_ao_dao;
import com.mycompany.shop.dao.mau_sac_dao;
import com.mycompany.shop.dao.nhan_vien_dao;
import com.mycompany.shop.dao.size_dao;
import com.mycompany.shop.dao.voucher_dao;
import com.mycompany.shop.model.ao;
import com.mycompany.shop.model.chi_tiet_hoa_don;
import com.mycompany.shop.model.hoa_don;
import com.mycompany.shop.model.khach_hang;
import com.mycompany.shop.model.loai_ao;
import com.mycompany.shop.model.mau_sac;
import com.mycompany.shop.model.size;
import com.mycompany.shop.model.voucher;
import com.mycompany.shop.model.UserSession;
import com.mycompany.shop.util.CurrencyUtils;
import com.mycompany.shop.util.ModernTheme;
import com.mycompany.shop.util.UIHelper;
import com.mycompany.shop.util.TableResponsiveManager;
import com.mycompany.shop.util.ResponsiveLayoutManager;
import com.mycompany.shop.util.ComponentOptimizer;
import com.mycompany.shop.util.TableStabilizer;
import com.mycompany.shop.util.CartManager;
import com.mycompany.shop.util.DataRefreshManager;
import com.mycompany.shop.util.InvoiceManager;
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.SwingUtilities;
import javax.swing.JPopupMenu;
import javax.swing.JMenuItem;
import java.awt.Frame;

public class ThanhToan extends javax.swing.JPanel {

    private ao_dao aoDAO;
    private chi_tiet_hoa_don_dao chiTietHoaDonDAO;
    private hoa_don_dao hoaDonDAO;
    private khach_hang_dao khachHangDAO;
    private loai_ao_dao loaiAoDAO;
    private mau_sac_dao mauSacDAO;
    private size_dao sizeDAO;
    private voucher_dao voucherDAO;
    private nhan_vien_dao nvdao = new nhan_vien_dao();

    private List<ao> danhSachSanPham;
    private List<hoa_don> danhSachHoaDon;
    private List<chi_tiet_hoa_don> gioHang;
    private List<voucher> danhSachVoucher;

    private DefaultTableModel modelHoaDon;
    private DefaultTableModel modelGioHang;
    private DefaultTableModel modelSanPham;

    private hoa_don hoaDonHienTai;
    private khach_hang khachHangHienTai = null;
    private voucher voucherHienTai = null;
    private double tongTien = 0;
    private double giamGia = 0;
    private double tienKhachDua = 0;
    private double tienThua = 0;

    /**
     * Creates new form HoaDon
     */
    public ThanhToan() {
        initComponents();
        com.mycompany.shop.util.FormLayoutOptimizer.makeFormResponsive(this);
        initDAO();
        setupComponents();
        loadData();
        //applyModernTheme();
        updateEmployeeInfo(); // Hiển thị thông tin nhân viên hiện tại
    }

    /**
     * Update employee info display based on current logged in user
     */
    private void updateEmployeeInfo() {
        UserSession session = UserSession.getInstance();
        if (session.isLoggedIn()) {
            String employeeInfo = "TẠO HÓA ĐƠN - " + session.getUserName() + " (" + session.getUserCode()
                    + ")";
            System.out.println("=== THÔNG TIN NHÂN VIÊN ===");
            System.out.println("ID: " + session.getUserId());
            System.out.println("Mã: " + session.getUserCode());
            System.out.println("Tên: " + session.getUserName());
            System.out.println("Loại: " + session.getUserTypeString());
            System.out.println("========================");

            // Hiển thị thông tin nhân viên trên giao diện
            if (jLabel7 != null) {
                jLabel7.setText(employeeInfo);
            }
        } else {
            System.err.println("WARNING: Không có thông tin đăng nhập!");
            if (jLabel7 != null) {
                jLabel7.setText("TẠO HÓA ĐƠN - Chưa đăng nhập");
            }
        }
    }

//        private void applyModernTheme() {
//                // Apply modern theme with responsive design
//                UIHelper.applyModernTheme(this);
//
//                // Style search components with responsive sizing
//                ModernTheme.styleTextField(jTextField1, ModernTheme.ButtonSize.MEDIUM);
//                ModernTheme.styleButton(jButton1, ModernTheme.ButtonType.PRIMARY, ModernTheme.ButtonSize.MEDIUM);
//
//                // Style customer info fields with responsive sizing
//                ModernTheme.styleTextField(txtTenKH, ModernTheme.ButtonSize.MEDIUM);
//                ModernTheme.styleTextField(txtSDT, ModernTheme.ButtonSize.MEDIUM);
//                // ModernTheme.styleTextField(jTextField4, ModernTheme.ButtonSize.MEDIUM);
//                ModernTheme.styleTextField(txtTienKhachDua, ModernTheme.ButtonSize.MEDIUM);
//
//                // Style action buttons with appropriate sizes
//                ModernTheme.styleButton(newPay, ModernTheme.ButtonType.SUCCESS, ModernTheme.ButtonSize.LARGE);
//                ModernTheme.styleButton(jButton3, ModernTheme.ButtonType.PRIMARY, ModernTheme.ButtonSize.MEDIUM);
//
//                // Style tables with responsive behavior
//                ModernTheme.styleTable(jTable1);
//                ModernTheme.styleTable(jTable2);
//                ModernTheme.styleTable(jTable3);
//
//                // Update labels styling
//                updateLabelsTheme();
//
//                // Set background
//                this.setBackground(ModernTheme.BACKGROUND_COLOR);
//
//                // Make the entire panel responsive
//                UIHelper.makeComponentsResponsive(this);
//        }
    private void updateLabelsTheme() {
        // Update all labels with modern font and colors
        jLabel1.setFont(ModernTheme.FONT_HEADING);
        jLabel1.setForeground(ModernTheme.TEXT_PRIMARY);

        jLabel2.setFont(ModernTheme.FONT_BODY);
        jLabel2.setForeground(ModernTheme.TEXT_PRIMARY);

        jLabel3.setFont(ModernTheme.FONT_BODY);
        jLabel3.setForeground(ModernTheme.TEXT_PRIMARY);

        jLabel4.setFont(ModernTheme.FONT_BODY);
        jLabel4.setForeground(ModernTheme.TEXT_PRIMARY);

        jLabel5.setFont(ModernTheme.FONT_BODY);
        jLabel5.setForeground(ModernTheme.TEXT_PRIMARY);

        jLabel6.setFont(ModernTheme.FONT_BODY);
        jLabel6.setForeground(ModernTheme.TEXT_PRIMARY);

        jLabel7.setFont(ModernTheme.FONT_BODY);
        jLabel7.setForeground(ModernTheme.TEXT_PRIMARY);

        jLabel8.setFont(ModernTheme.FONT_BODY);
        jLabel8.setForeground(ModernTheme.TEXT_PRIMARY);

        // Style value labels with different color
        jLabel9.setFont(ModernTheme.FONT_SUBHEADING);
        jLabel9.setForeground(ModernTheme.PRIMARY_COLOR);

        jLabel10.setFont(ModernTheme.FONT_BODY);
        jLabel10.setForeground(ModernTheme.TEXT_PRIMARY);

        jLabel11.setFont(ModernTheme.FONT_SUBHEADING);
        jLabel11.setForeground(ModernTheme.SUCCESS_COLOR);

        // jLabel12.setFont(ModernTheme.FONT_BODY);
        // jLabel12.setForeground(ModernTheme.TEXT_PRIMARY);
        jLabel13.setFont(ModernTheme.FONT_BODY);
        jLabel13.setForeground(ModernTheme.TEXT_PRIMARY);

        jLabel14.setFont(ModernTheme.FONT_SUBHEADING);
        jLabel14.setForeground(ModernTheme.PRIMARY_COLOR);
    }

    private void initDAO() {
        aoDAO = new ao_dao();
        chiTietHoaDonDAO = new chi_tiet_hoa_don_dao();
        hoaDonDAO = new hoa_don_dao();
        khachHangDAO = new khach_hang_dao();
        loaiAoDAO = new loai_ao_dao();
        mauSacDAO = new mau_sac_dao();
        sizeDAO = new size_dao();
        voucherDAO = new voucher_dao();
    }

    private void setupComponents() {
        // Thiết lập model cho bảng hóa đơn
        modelHoaDon = (DefaultTableModel) jTable1.getModel();
        modelHoaDon.setColumnIdentifiers(
                new String[]{"STT", "Mã HD", "Tên NV", "Trạng thái", "Ngày tạo", "Tổng tiền",
                    "Voucher"});

        // Thiết lập model cho bảng giỏ hàng
        modelGioHang = (DefaultTableModel) jTable2.getModel();
        modelGioHang.setColumnIdentifiers(
                new String[]{"STT", "Mã SP", "Tên SP", "Số lượng", "Đơn giá", "Thành tiền"});

        // Thiết lập model cho bảng sản phẩm
        modelSanPham = (DefaultTableModel) jTable3.getModel();
        modelSanPham.setColumnIdentifiers(
                new String[]{"Ảnh", "Mã SP", "Tên SP", "Loại", "Size", "Màu sắc", "Số lượng",
                    "Đơn giá"});

        // Khởi tạo giỏ hàng
        gioHang = new ArrayList<>();

        // Apply specialized table responsive management
        TableResponsiveManager.makeTableResponsive(jTable1);
        TableResponsiveManager.makeTableResponsive(jTable2);
        TableResponsiveManager.makeTableResponsive(jTable3);

        // Stabilize tables to prevent flickering
        TableStabilizer.stabilizeTable(jTable1);
        TableStabilizer.stabilizeTable(jTable2);
        TableStabilizer.stabilizeTable(jTable3);

        // Make layout responsive
        ResponsiveLayoutManager.makeResponsive(this);

        // Setup window resize listener for table responsiveness
        setupWindowResizeListener();

        // Optimize components (buttons, text fields, etc.)
        ComponentOptimizer.optimizeComponents(this);

        // Setup data refresh management
        setupDataRefresh();

        // Add cart management buttons
        addCartManagementButtons();

        // Clear text fields
        jTextField1.setText("");
        txtTenKH.setText("");
        txtSDT.setText("");
        // jTextField4.setText("");
        txtTienKhachDua.setText("");

        // Xóa thông tin hóa đơn hiện tại
        hoaDonHienTai = null;

        // Hiển thị thông tin giá ban đầu
        jLabel9.setText("0");
        jLabel11.setText("0");
        jLabel14.setText("0");

        // Thêm event listeners
        // Sự kiện mouse clicked cho bảng sản phẩm
        jTable3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable3MouseClicked(evt);
            }
        });

        // Sự kiện mouse clicked cho bảng giỏ hàng
        jTable2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable2MouseClicked(evt);
            }
        });

        // Sự kiện mouse clicked cho bảng hóa đơn
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });

        // Sự kiện key released cho text field tiền khách đưa
        txtTienKhachDua.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField5KeyReleased(evt);
            }
        });

        // Sự kiện action performed cho nút thanh toán
        newPay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        // Sự kiện action performed cho nút in
//                jButton3.addActionListener(new java.awt.event.ActionListener() {
//                        public void actionPerformed(java.awt.event.ActionEvent evt) {
//                                jButton3ActionPerformed(evt);
//                        }
//                });
        // Event cho text field tiền khách đưa để tính tiền thối
        txtTienKhachDua.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent evt) {
                capNhatTienThoi();
            }
        });

        // Event double click cho table giỏ hàng để xóa sản phẩm
        jTable2.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) { // Double click
                    xoaSanPhamKhoiGioHang();
                }
            }
        });

        // Event right click cho text field khách hàng để tìm kiếm
        txtTenKH.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getButton() == java.awt.event.MouseEvent.BUTTON3) { // Right click
                    timKiemKhachHang();
                }
            }
        });

        // Event right click cho label tổng tiền để áp dụng voucher
        jLabel9.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getButton() == java.awt.event.MouseEvent.BUTTON3) { // Right click
                    apDungVoucher();
                }
            }
        });
    }

    private void loadData() {
        loadDanhSachSanPham();
        loadDanhSachHoaDon();
        loadDanhSachVoucher();

        hienThiDanhSachSanPham();
        hienThiDanhSachHoaDon();

        // Khởi tạo giỏ hàng rỗng
        gioHang = new ArrayList<>();
        hienThiGioHang();

        // Setup event handlers
        setupProductTableEvents();
    }

    private void loadDanhSachSanPham() {
        // Load chỉ sản phẩm còn hàng
        danhSachSanPham = aoDAO.getAoConHang();
    }

    private void loadDanhSachHoaDon() {
        danhSachHoaDon = hoaDonDAO.getAllHoaDon();
    }

    private void loadDanhSachVoucher() {
        danhSachVoucher = voucherDAO.getActiveVouchers();
    }

    private void setupDataRefresh() {
        // Create refresh actions
        Runnable refreshProducts = DataRefreshManager.createSafeRefresh(() -> {
            loadDanhSachSanPham();
            hienThiDanhSachSanPham();
        }, "Sản phẩm");

        Runnable refreshInvoices = DataRefreshManager.createSafeRefresh(() -> {
            loadDanhSachHoaDon();
            hienThiDanhSachHoaDon();
        }, "Hóa đơn");

        Runnable refreshVouchers = DataRefreshManager.createSafeRefresh(() -> {
            loadDanhSachVoucher();
        }, "Voucher");

        // Register refresh callbacks
        DataRefreshManager.registerRefreshCallback(DataRefreshManager.PRODUCT_CHANGED, refreshProducts);
        DataRefreshManager.registerRefreshCallback(DataRefreshManager.INVOICE_CHANGED, refreshInvoices);
        DataRefreshManager.registerRefreshCallback(DataRefreshManager.VOUCHER_CHANGED, refreshVouchers);
    }

    private void addCartManagementButtons() {
        // Add context menu to cart table for better UX
        JPopupMenu cartPopupMenu = new JPopupMenu();

        JMenuItem editQuantityItem = new JMenuItem("Sửa số lượng");
        editQuantityItem.addActionListener(e -> {
            int row = jTable2.getSelectedRow();
            if (row >= 0 && row < gioHang.size()) {
                chi_tiet_hoa_don cartItem = gioHang.get(row);
                ao product = aoDAO.getAoById(cartItem.getId_ao());
                CartManager.updateProductQuantity(gioHang, cartItem, product, modelGioHang, () -> {
                    hienThiGioHang();
                    DataRefreshManager.notifyCartChanged();
                }, true);
            }
        });

        JMenuItem removeItem = new JMenuItem("Xóa sản phẩm");
        removeItem.addActionListener(e -> {
            int row = jTable2.getSelectedRow();
            if (row >= 0 && row < gioHang.size()) {
                chi_tiet_hoa_don cartItem = gioHang.get(row);
                CartManager.removeProductFromCart(gioHang, cartItem, modelGioHang, () -> {
                    hienThiGioHang();
                    DataRefreshManager.notifyCartChanged();
                });
            }
        });

        JMenuItem clearCartItem = new JMenuItem("Xóa toàn bộ giỏ hàng");
        clearCartItem.addActionListener(e -> {
            CartManager.clearCart(gioHang, modelGioHang, () -> {
                hienThiGioHang();
                DataRefreshManager.notifyCartChanged();
            });
        });

        cartPopupMenu.add(editQuantityItem);
        cartPopupMenu.add(removeItem);
        cartPopupMenu.addSeparator();
        cartPopupMenu.add(clearCartItem);

        // Add popup menu to cart table
        jTable2.setComponentPopupMenu(cartPopupMenu);
    }

    private void hienThiDanhSachSanPham() {
        // Use TableStabilizer for safe updates
        TableStabilizer.safeUpdateTable(jTable3, () -> {
            modelSanPham.setRowCount(0);

            for (ao item : danhSachSanPham) {
                String tenLoai = getTenLoaiAo(item.getId_loai());
                String tenMauSac = getTenMauSac(item.getId_mau_sac());
                String tenSize = getTenSize(item.getId_size());

                // Tạo ImageIcon cho ảnh sản phẩm
                javax.swing.ImageIcon imageIcon = com.mycompany.shop.util.ImageUtils.createImageIcon(
                        item.getImage(), 50, 50);

                modelSanPham.addRow(new Object[]{
                    imageIcon,
                    item.getMa_ao(),
                    item.getTen_ao(),
                    tenLoai,
                    tenSize,
                    tenMauSac,
                    item.getSo_luong(),
                    item.getGia()
                });
            }

            // Setup custom renderer cho cột ảnh
            setupImageColumnRenderer();
        });
    }

    private void setupImageColumnRenderer() {
        // Set custom renderer cho cột ảnh (cột 0)
        jTable3.getColumnModel().getColumn(0).setCellRenderer(new javax.swing.table.DefaultTableCellRenderer() {
            @Override
            public java.awt.Component getTableCellRendererComponent(javax.swing.JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                if (value instanceof javax.swing.ImageIcon) {
                    javax.swing.JLabel label = new javax.swing.JLabel(
                            (javax.swing.ImageIcon) value);
                    label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
                    label.setOpaque(true);

                    if (isSelected) {
                        label.setBackground(table.getSelectionBackground());
                    } else {
                        label.setBackground(table.getBackground());
                    }

                    return label;
                } else {
                    return super.getTableCellRendererComponent(table, value, isSelected, hasFocus,
                            row, column);
                }
            }
        });

        // Set chiều rộng cột ảnh
        jTable3.getColumnModel().getColumn(0).setPreferredWidth(60);
        jTable3.getColumnModel().getColumn(0).setMaxWidth(60);
        jTable3.getColumnModel().getColumn(0).setMinWidth(60);

        // Set chiều cao hàng để hiển thị ảnh tốt hơn
        jTable3.setRowHeight(60);
    }

    private void hienThiDanhSachHoaDon() {
        // Use TableStabilizer for safe updates
        TableStabilizer.safeUpdateTable(jTable1, () -> {
            modelHoaDon.setRowCount(0);
            int stt = 1;

            for (int i = 0; i < danhSachHoaDon.size(); i++) {
                hoa_don hd = danhSachHoaDon.get(i);

                // Chỉ hiển thị hóa đơn chưa thanh toán trong màn Bán Hàng
                if (hd.isChuaThanhToan()) {
                    // Hiển thị trạng thái thực tế của hóa đơn
                    String trangThai = "Chưa thanh toán";

                    // Format date
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                    String ngayTao = sdf.format(new Date(hd.getNgay_tao().getTime()));

                    // Tính giảm giá từ voucher
                    String voucherInfo = hoaDonDAO.getVoucherInfo(hd.getId_voucher());

                    modelHoaDon.addRow(new Object[]{
                        stt++,
                        hd.getMa_hoa_don(),
                        "NV" + nvdao.getNhanVienById(hd.getId_nhan_vien())
                        .getTen_nhan_vien(),
                        trangThai,
                        ngayTao,
                        CurrencyUtils.formatVND(hd.getTong_tien()),
                        voucherInfo
                    });
                }
            }
        });
    }

    private void hienThiGioHang() {
        // Use TableStabilizer for safe updates
        TableStabilizer.safeUpdateTable(jTable2, () -> {
            modelGioHang.setRowCount(0);
            tongTien = 0;

            for (int i = 0; i < gioHang.size(); i++) {
                chi_tiet_hoa_don chiTiet = gioHang.get(i);
                ao sanPham = aoDAO.getAoById(chiTiet.getId_ao());

                double thanhTien = chiTiet.getSo_luong() * chiTiet.getDon_gia();
                tongTien += thanhTien;

                modelGioHang.addRow(new Object[]{
                    i + 1,
                    sanPham.getMa_ao(),
                    sanPham.getTen_ao(),
                    chiTiet.getSo_luong(),
                    CurrencyUtils.formatVND(chiTiet.getDon_gia()),
                    CurrencyUtils.formatVND((int) thanhTien)
                });
            }

            // Cập nhật tổng tiền
            jLabel9.setText(CurrencyUtils.formatVND((int) tongTien));
            capNhatTienThoi();
        });
    }

    private void capNhatTienThoi() {
        // Cập nhật giảm giá
        jLabel11.setText(CurrencyUtils.formatVND((int) giamGia));

        try {
            String tienKhachDuaStr = txtTienKhachDua.getText().trim();
            if (!tienKhachDuaStr.isEmpty()) {
                double khachDua = Double.parseDouble(tienKhachDuaStr);
                double thanhTien = tongTien - giamGia;
                double tienThoi = khachDua - thanhTien;

                if (tienThoi >= 0) {
                    jLabel14.setText(CurrencyUtils.formatVND((int) tienThoi));
                } else {
                    jLabel14.setText("Không đủ");
                }
            } else {
                jLabel14.setText(CurrencyUtils.formatVND(0));
            }
        } catch (NumberFormatException e) {
            jLabel14.setText(CurrencyUtils.formatVND(0));
        }
    }

    private String getTenLoaiAo(int id_loai) {
        loai_ao loai = loaiAoDAO.getLoaiAoById(id_loai);
        return loai != null ? loai.getTen_loai() : "Không xác định";
    }

    private String getTenMauSac(int id_mau_sac) {
        mau_sac mau = mauSacDAO.getMauSacById(id_mau_sac);
        return mau != null ? mau.getTen_mau_sac() : "Không xác định";
    }

    private String getTenSize(int id_size) {
        size s = sizeDAO.getSizeById(id_size);
        return s != null ? s.getTen_size() : "Không xác định";
    }

    private void themSanPhamVaoGioHang(ao sanPham) {
        // Use CartManager for better cart management
        boolean success = CartManager.addProductToCart(gioHang, sanPham, modelGioHang, () -> {
            hienThiGioHang();
            DataRefreshManager.notifyCartChanged();
        });

        if (success) {
            // Notify data changes
            DataRefreshManager.notifyProductChanged();
        }
    }

    private khach_hang getKhachHangBySDT(String sdt) {
        // Phương thức này lấy khách hàng theo số điện thoại
        if (sdt == null || sdt.trim().isEmpty()) {
            return null;
        }

        List<khach_hang> dsKhachHang = khachHangDAO.getAllKhachHang();
        for (khach_hang kh : dsKhachHang) {
            if (kh.getSo_dien_thoai() != null && kh.getSo_dien_thoai().equals(sdt.trim())) {
                return kh;
            }
        }
        return null;
    }

    private void taoHoaDonMoi() {
        // Tạo hóa đơn mới
        hoaDonHienTai = new hoa_don();
        String maHD = "HD" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        hoaDonHienTai.setMa_hoa_don(maHD);

        // Lấy ID nhân viên từ UserSession
        UserSession session = UserSession.getInstance();
        if (session.isLoggedIn()) {
            hoaDonHienTai.setId_nhan_vien(session.getUserId());
        } else {
            // Fallback nếu chưa đăng nhập (không nên xảy ra)
            hoaDonHienTai.setId_nhan_vien(1);
            System.err.println("WARNING: Tạo hóa đơn khi chưa đăng nhập!");
        }

        hoaDonHienTai.setNgay_tao(new java.sql.Timestamp(System.currentTimeMillis()));

        // Nếu có thông tin khách hàng
        if (!txtTenKH.getText().trim().isEmpty() && !txtSDT.getText().trim().isEmpty()) {
            // Tìm khách hàng theo SĐT
            khach_hang kh = getKhachHangBySDT(txtSDT.getText().trim());

            // Nếu không tìm thấy thì tạo mới
            if (kh == null) {
                // Tạo thông tin khách hàng mới
                String tenKH = txtTenKH.getText().trim();
                String sdtKH = txtSDT.getText().trim();
                String maKH = "KH" + System.currentTimeMillis();

                // Thêm khách hàng mới vào CSDL
                khachHangDAO.addKhachHang(maKH, tenKH, sdtKH, "", "", "Hoạt động");

                // Lấy lại thông tin khách hàng vừa tạo
                kh = getKhachHangBySDT(sdtKH);

                // Thông báo cho màn Khách Hàng refresh dữ liệu
                com.mycompany.shop.util.DataRefreshManager.notifyCustomerChanged();

                System.out.println("Đã tạo khách hàng mới: " + tenKH + " - " + sdtKH);
            } else {
                // Khách hàng đã tồn tại, sử dụng thông tin có sẵn
                System.out.println("Sử dụng khách hàng có sẵn: " + kh.getHo_va_ten() + " - "
                        + kh.getSo_dien_thoai());
            }

            if (kh != null) {
                hoaDonHienTai.setId_khach_hang(kh.getId_khach_hang());
            }
        }

        // Lưu hóa đơn vào CSDL
        hoaDonDAO.addHoaDon(hoaDonHienTai);

        // Lấy ID hóa đơn vừa tạo
        hoaDonHienTai = hoaDonDAO.getHoaDonByMa(hoaDonHienTai.getMa_hoa_don());

        // Refresh danh sách hóa đơn để hiển thị hóa đơn vừa tạo
        loadDanhSachHoaDon();
        hienThiDanhSachHoaDon();

        // JOptionPane.showMessageDialog(this, "Đã tạo hóa đơn mới: " + hoaDonHienTai.getMa_hoa_don());
    }

//        private void prepareNewInvoice() {
//                // Tạo hóa đơn mới nhưng chưa lưu vào DB để tránh flicker
//                hoaDonHienTai = new hoa_don();
//                hoaDonHienTai.setMa_hoa_don("HD" + System.currentTimeMillis());
//
//                // Lấy ID nhân viên từ UserSession
//                UserSession session = UserSession.getInstance();
//                if (session.isLoggedIn()) {
//                        hoaDonHienTai.setId_nhan_vien(session.getUserId());
//                } else {
//                        // Fallback nếu chưa đăng nhập (không nên xảy ra)
//                        hoaDonHienTai.setId_nhan_vien(1);
//                        System.err.println("WARNING: Tạo hóa đơn khi chưa đăng nhập!");
//                }
//
//                hoaDonHienTai.setNgay_tao(new java.sql.Timestamp(System.currentTimeMillis()));
//                hoaDonHienTai.setTrang_thai(0); // Chưa thanh toán
//
//                // Không lưu vào DB ở đây để tránh flicker trong danh sách hóa đơn
//        }
    private void thanhToanHoaDon() {
        // Validate cart first
        if (!CartManager.validateCart(gioHang, danhSachSanPham)) {
            return;
        }

        // Validate customer phone number if provided
        if (!txtSDT.getText().trim().isEmpty()) {
            String phoneNumber = txtSDT.getText().trim();
            khach_hang existingCustomer = khachHangDAO.getKhachHangBySoDienThoai(phoneNumber);

            if (existingCustomer != null) {
                // Phone number exists, ask user to select existing customer
                int choice = JOptionPane.showConfirmDialog(this,
                        "Số điện thoại này đã tồn tại trong hệ thống!\n"
                        + "Khách hàng: " + existingCustomer.getHo_va_ten() + "\n"
                        + "Bạn có muốn sử dụng thông tin khách hàng này không?",
                        "Số điện thoại đã tồn tại",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE);

                if (choice == JOptionPane.YES_OPTION) {
                    // Use existing customer info
                    khachHangHienTai = existingCustomer;
                    txtTenKH.setText(existingCustomer.getHo_va_ten());
                    txtSDT.setText(existingCustomer.getSo_dien_thoai());
                } else {
                    // User wants to use different info, continue with manual entry
                    khachHangHienTai = null;
                }
            }
        }

        // Prepare invoice data but don't save to DB yet to prevent flicker
//                if (hoaDonHienTai == null) {
//                        prepareNewInvoice();
//                }
        try {
            // Kiểm tra tiền khách đưa
            double khachDua = 0;
            if (!txtTienKhachDua.getText().trim().isEmpty()) {
                khachDua = Double.parseDouble(txtTienKhachDua.getText().trim());
            }

            double tongTienThanhToan = tongTien - giamGia;

            if (khachDua < tongTienThanhToan) {
                JOptionPane.showMessageDialog(this, "Số tiền khách đưa không đủ!", "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Determine if this is a new invoice or editing existing one
            boolean isEditingExistingInvoice = (hoaDonHienTai.getId_hoa_don() > 0);

            // Cập nhật thông tin hóa đơn
            hoaDonHienTai.setTong_tien((int) (tongTien - giamGia));
            hoaDonHienTai.setTrang_thai(1); // Mark as paid

            // Cập nhật thông tin khách hàng từ form nếu có
            if (!txtTenKH.getText().trim().isEmpty() && !txtSDT.getText().trim().isEmpty()) {
                // Tìm khách hàng theo SĐT
                khach_hang kh = getKhachHangBySDT(txtSDT.getText().trim());

                // Nếu không tìm thấy thì tạo mới
                if (kh == null) {
                    // Tạo thông tin khách hàng mới
                    String tenKH = txtTenKH.getText().trim();
                    String sdtKH = txtSDT.getText().trim();
                    String maKH = "KH" + System.currentTimeMillis();

                    // Thêm khách hàng mới vào CSDL
                    khachHangDAO.addKhachHang(maKH, tenKH, sdtKH, "", "", "Khach Hàng Mới");

                    // Lấy lại thông tin khách hàng vừa tạo
                    kh = getKhachHangBySDT(sdtKH);

                    // Thông báo cho màn Khách Hàng refresh dữ liệu
                    com.mycompany.shop.util.DataRefreshManager.notifyCustomerChanged();

                    System.out.println("Đã tạo khách hàng mới: " + tenKH + " - " + sdtKH);
                }

                if (kh != null) {
                    hoaDonHienTai.setId_khach_hang(kh.getId_khach_hang());
                    hoaDonHienTai.setHo_va_ten_khach(kh.getHo_va_ten());
                    hoaDonHienTai.setSo_dien_thoai_khach(kh.getSo_dien_thoai());
                }
            } else if (khachHangHienTai != null) {
                // Sử dụng khách hàng đã chọn
                hoaDonHienTai.setId_khach_hang(khachHangHienTai.getId_khach_hang());
                hoaDonHienTai.setHo_va_ten_khach(khachHangHienTai.getHo_va_ten());
                hoaDonHienTai.setSo_dien_thoai_khach(khachHangHienTai.getSo_dien_thoai());
            }

            // Cập nhật voucher nếu có
            if (voucherHienTai != null) {
                hoaDonHienTai.setId_voucher(voucherHienTai.getId_voucher());
            }

            if (isEditingExistingInvoice) {
                // Update existing invoice
                hoaDonDAO.updateHoaDon(hoaDonHienTai);

                // Delete existing invoice details first
                chiTietHoaDonDAO.deleteChiTietHoaDonByIdHoaDon(hoaDonHienTai.getId_hoa_don());
            } else {
                // Add new invoice - chỉ lưu vào DB khi thanh toán thành công
                hoaDonDAO.addHoaDon(hoaDonHienTai);

                // Lấy ID hóa đơn vừa tạo
                hoaDonHienTai = hoaDonDAO.getHoaDonByMa(hoaDonHienTai.getMa_hoa_don());
            }

            // Lưu chi tiết hóa đơn
            for (chi_tiet_hoa_don chiTiet : gioHang) {
                chiTiet.setId_hoa_don(hoaDonHienTai.getId_hoa_don());
                chiTietHoaDonDAO.addChiTietHoaDon(chiTiet);

                // Cập nhật số lượng sản phẩm (giảm số lượng khi bán)
                boolean capNhatThanhCong = aoDAO.giamSoLuong(chiTiet.getId_ao(), chiTiet.getSo_luong());
                if (!capNhatThanhCong) {
                    throw new Exception("Không thể cập nhật số lượng sản phẩm ID: "
                            + chiTiet.getId_ao());
                }
            }

            // Sử dụng voucher nếu có (giảm số lượng voucher)
            if (voucherHienTai != null) {
                boolean suDungVoucherThanhCong = voucherDAO.useVoucher(voucherHienTai.getMa_voucher());
                if (!suDungVoucherThanhCong) {
                    throw new Exception(
                            "Không thể sử dụng voucher: " + voucherHienTai.getMa_voucher());
                }
            }

            JOptionPane.showMessageDialog(this,
                    "Thanh toán thành công!\nSố tiền trả lại khách: "
                    + (khachDua - tongTienThanhToan),
                    "Thông báo", JOptionPane.INFORMATION_MESSAGE);

            // Reset sau khi thanh toán
            resetHoaDon();

            // Notify data changes to refresh all related screens
            DataRefreshManager.notifyInvoiceChanged();
            DataRefreshManager.notifyProductChanged();
            DataRefreshManager.notifyVoucherChanged();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Lỗi định dạng số!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi thanh toán: " + e.getMessage(), "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void resetHoaDon() {
        hoaDonHienTai = null;
        khachHangHienTai = null;
        voucherHienTai = null;
        gioHang.clear();

        txtTenKH.setText("");
        txtSDT.setText("");
        // jTextField4.setText("");
        txtTienKhachDua.setText("");

        tongTien = 0;
        giamGia = 0;

        jLabel9.setText("0");
        jLabel11.setText("0");
        jLabel14.setText("0");

        hienThiGioHang();
    }

    /**
     * Setup event handlers cho table sản phẩm
     */
    private void setupProductTableEvents() {
        // Event click vào table sản phẩm để thêm vào giỏ hàng
        jTable3.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 1) { // Single click
                    themSanPhamVaoGioHang();
                }
            }
        });
    }

    /**
     * Thêm sản phẩm vào giỏ hàng
     */
    private void themSanPhamVaoGioHang() {
        int selectedRow = jTable3.getSelectedRow();
        if (selectedRow >= 0) {
            try {
                // Lấy thông tin sản phẩm từ table (cập nhật index vì đã thêm cột ảnh)
                String maSanPham = (String) modelSanPham.getValueAt(selectedRow, 1); // Cột 1: Mã SP
                String tenSanPham = (String) modelSanPham.getValueAt(selectedRow, 2); // Cột 2: Tên SP
                int soLuongTonKho = (Integer) modelSanPham.getValueAt(selectedRow, 6); // Cột 6: Số
                // lượng
                int giaSanPham = (Integer) modelSanPham.getValueAt(selectedRow, 7); // Cột 7: Đơn giá

                // Kiểm tra tồn kho
                if (soLuongTonKho <= 0) {
                    JOptionPane.showMessageDialog(this, "Sản phẩm đã hết hàng!",
                            "Thông báo", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                // Hiển thị dialog chọn số lượng
                Frame parentFrame = (Frame) SwingUtilities.getWindowAncestor(this);
                int soLuongChon = SoLuongDialog.showDialog(parentFrame, tenSanPham, soLuongTonKho,
                        giaSanPham);

                if (soLuongChon > 0) {
                    // Lấy thông tin sản phẩm từ DAO
                    ao sanPham = aoDAO.getAoByMa(maSanPham);
                    if (sanPham != null) {
                        // Kiểm tra xem sản phẩm đã có trong giỏ hàng chưa
                        boolean daTonTai = false;
                        for (chi_tiet_hoa_don item : gioHang) {
                            if (item.getId_ao() == sanPham.getId_ao()) {
                                // Cập nhật số lượng
                                int soLuongMoi = item.getSo_luong() + soLuongChon;
                                if (soLuongMoi <= soLuongTonKho) {
                                    item.setSo_luong(soLuongMoi);
                                    daTonTai = true;
                                } else {
                                    JOptionPane.showMessageDialog(this,
                                            "Tổng số lượng không được vượt quá tồn kho ("
                                            + soLuongTonKho
                                            + ")!",
                                            "Lỗi",
                                            JOptionPane.ERROR_MESSAGE);
                                    return;
                                }
                                break;
                            }
                        }

                        // Nếu chưa có trong giỏ hàng, thêm mới
                        if (!daTonTai) {
                            chi_tiet_hoa_don chiTiet = new chi_tiet_hoa_don();
                            chiTiet.setId_ao(sanPham.getId_ao());
                            chiTiet.setSo_luong(soLuongChon);
                            chiTiet.setDon_gia(sanPham.getGia());
                            gioHang.add(chiTiet);
                        }

                        // Cập nhật hiển thị giỏ hàng
                        hienThiGioHang();

                        JOptionPane.showMessageDialog(this,
                                "Đã thêm " + soLuongChon + " " + tenSanPham
                                + " vào giỏ hàng!",
                                "Thành công", JOptionPane.INFORMATION_MESSAGE);
                    }
                }

            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Lỗi khi thêm sản phẩm: " + e.getMessage(),
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }

    /**
     * Tìm kiếm và chọn khách hàng
     */
    private void timKiemKhachHang() {
        Frame parentFrame = (Frame) SwingUtilities.getWindowAncestor(this);
        khach_hang selectedCustomer = KhachHangSearchDialog.showDialog(parentFrame);

        if (selectedCustomer != null) {
            khachHangHienTai = selectedCustomer;

            // Hiển thị thông tin khách hàng lên form
            txtTenKH.setText(selectedCustomer.getHo_va_ten());
            txtSDT.setText(selectedCustomer.getSo_dien_thoai());

            JOptionPane.showMessageDialog(this,
                    "Đã chọn khách hàng: " + selectedCustomer.getHo_va_ten(),
                    "Thành công", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * Xóa sản phẩm khỏi giỏ hàng
     */
    private void xoaSanPhamKhoiGioHang() {
        int selectedRow = jTable2.getSelectedRow();
        if (selectedRow >= 0) {
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Bạn có chắc chắn muốn xóa sản phẩm này khỏi giỏ hàng?",
                    "Xác nhận xóa",
                    JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                gioHang.remove(selectedRow);
                hienThiGioHang();

                JOptionPane.showMessageDialog(this, "Đã xóa sản phẩm khỏi giỏ hàng!",
                        "Thành công", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    /**
     * Áp dụng voucher giảm giá
     */
    private void apDungVoucher() {
        if (gioHang.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng thêm sản phẩm vào giỏ hàng trước!",
                    "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String maVoucher = JOptionPane.showInputDialog(this,
                "Nhập mã voucher:",
                "Áp dụng voucher",
                JOptionPane.QUESTION_MESSAGE);

        if (maVoucher != null && !maVoucher.trim().isEmpty()) {
            voucher v = voucherDAO.getVoucherByMa(maVoucher.trim());

            if (v != null && v.getTrang_thai().equals("Hoạt động") && v.getSo_luong() > 0) {
                // Kiểm tra ngày hiệu lực
                Date currentDate = new Date();
                if (currentDate.after(v.getNgay_bat_dau())
                        && currentDate.before(v.getNgay_ket_thuc())) {
                    voucherHienTai = v;

                    // Tính giảm giá
                    double giaTriGiam = voucherDAO.calculateDiscount(maVoucher.trim(), tongTien);
                    giamGia = giaTriGiam;

                    // Cập nhật hiển thị
                    capNhatTienThoi();

                    JOptionPane.showMessageDialog(this,
                            "Áp dụng voucher thành công!\n"
                            + "Voucher: " + v.getTen_voucher() + "\n"
                            + "Giảm giá: "
                            + CurrencyUtils.formatVND((int) giaTriGiam),
                            "Thành công", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Voucher đã hết hạn sử dụng!",
                            "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Mã voucher không hợp lệ hoặc đã hết!",
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
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
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        txtTenKH = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtSDT = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        newPay = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtTienKhachDua = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();

        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Helvetica Neue", 1, 13)); // NOI18N
        jLabel1.setText("DANH SÁCH HÓA ĐƠN");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 6, 191, -1));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "STT", "Mã HD", "Tên NV", "Trạng thái", "Ngày tạo"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 30, 450, 338));

        add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 521, 376));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel5.setText("Tên Khách hàng");
        jPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 313, 169, -1));
        jPanel2.add(txtTenKH, new org.netbeans.lib.awtextra.AbsoluteConstraints(181, 310, 340, -1));

        jLabel6.setText("Số điện thoại");
        jPanel2.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, 169, -1));
        jPanel2.add(txtSDT, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 360, 340, -1));

        jLabel7.setFont(new java.awt.Font("Helvetica Neue", 1, 15)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("TẠO HÓA ĐƠN");
        jPanel2.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 6, 649, -1));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButton3.setBackground(new java.awt.Color(255, 255, 51));
        jButton3.setText("CLEAR FORM");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 90, 160, 60));

        jButton4.setBackground(new java.awt.Color(255, 102, 102));
        jButton4.setText("HỦY");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 90, 160, 60));

        newPay.setBackground(new java.awt.Color(204, 255, 204));
        newPay.setFont(new java.awt.Font("Helvetica Neue", 1, 14)); // NOI18N
        newPay.setText("TẠO HÓA ĐƠN");
        newPay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newPayActionPerformed(evt);
            }
        });
        jPanel3.add(newPay, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 10, 160, 60));

        jButton2.setBackground(new java.awt.Color(204, 255, 204));
        jButton2.setFont(new java.awt.Font("Helvetica Neue", 1, 14)); // NOI18N
        jButton2.setText("THANH TOÁN");
        jButton2.setToolTipText("");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 10, 160, 60));

        jPanel2.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 550, 430, -1));

        jLabel2.setFont(new java.awt.Font("Helvetica Neue", 1, 13)); // NOI18N
        jLabel2.setText("GIỎ HÀNG");
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 44, 143, -1));

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "STT", "Mã SP", "Tên SP", "Số lượng", "Đơn giá", "Giảm giá", "Thành tiền", "Trạng thái"
            }
        ));
        jScrollPane2.setViewportView(jTable2);

        jPanel2.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 68, 519, 224));

        jLabel4.setFont(new java.awt.Font("Helvetica Neue", 1, 18)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("THANH TOÁN");
        jPanel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 390, 637, -1));

        jLabel10.setText("Giảm giá:");
        jPanel2.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 442, 164, -1));
        jPanel2.add(txtTienKhachDua, new org.netbeans.lib.awtextra.AbsoluteConstraints(188, 464, 330, -1));

        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel13.setText("Tiền thừa");
        jPanel2.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 520, 164, -1));

        jLabel14.setFont(new java.awt.Font("Helvetica Neue", 1, 13)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(0, 102, 51));
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel14.setText("88888888");
        jPanel2.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 520, 455, -1));

        jLabel17.setText("Khách đưa");
        jPanel2.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 467, 164, -1));

        jLabel9.setText("99999999");
        jPanel2.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(188, 420, 455, -1));

        jLabel8.setText("Tổng tiền hàng:");
        jPanel2.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 420, 164, -1));

        jLabel11.setText("-1000");
        jPanel2.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(188, 442, 455, -1));

        add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 0, 538, 811));

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setFont(new java.awt.Font("Helvetica Neue", 1, 13)); // NOI18N
        jLabel3.setText("DANH SÁCH SẢN PHẨM");
        jPanel4.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 8, 214, -1));

        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });
        jPanel4.add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 10, 160, -1));

        jButton1.setText("Tìm sản phẩm");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 0, 110, -1));

        jTable3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Ảnh", "Mã SP", "Tên SP", "Loại", "Size", "Màu sắc", "Số lượng", "Đơn giá"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, true, true, true, true, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(jTable3);

        jPanel4.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, 530, 220));

        add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 380, 560, 430));
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton1ActionPerformed
        // Tìm kiếm sản phẩm
        String tuKhoa = jTextField1.getText().trim();

        if (tuKhoa.isEmpty()) {
            // Nếu từ khóa trống, hiển thị tất cả sản phẩm còn hàng
            danhSachSanPham = aoDAO.getAoConHang();
            hienThiDanhSachSanPham();
            return;
        }

        // Tìm kiếm sản phẩm theo tên
        List<ao> ketQuaTimKiem = aoDAO.searchAoByName(tuKhoa);

        // Lọc chỉ sản phẩm còn hàng
        List<ao> sanPhamConHang = new ArrayList<>();
        for (ao item : ketQuaTimKiem) {
            if (item.getSo_luong() > 0) {
                sanPhamConHang.add(item);
            }
        }

        // Cập nhật danh sách và hiển thị
        danhSachSanPham = sanPhamConHang;
        hienThiDanhSachSanPham();

        if (sanPhamConHang.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy sản phẩm nào còn hàng!",
                    "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        }
    }// GEN-LAST:event_jButton1ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton4ActionPerformed
        // Hủy hóa đơn hoặc clear form
        if (hoaDonHienTai == null && gioHang.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không có gì để hủy!", "Thông báo",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        String message;
        if (hoaDonHienTai != null) {
            message = "Bạn có chắc chắn muốn hủy hóa đơn này?";
        } else {
            message = "Bạn có chắc chắn muốn xóa toàn bộ thông tin hiện tại?";
        }

        int confirm = JOptionPane.showConfirmDialog(this, message, "Xác nhận hủy", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                // Nếu có hóa đơn hiện tại và đã được lưu vào DB, xóa khỏi DB
                if (hoaDonHienTai != null && hoaDonHienTai.getId_hoa_don() > 0) {
                    // Xóa chi tiết hóa đơn trước để tránh foreign key constraint violation
                    chiTietHoaDonDAO.deleteChiTietHoaDonByIdHoaDon(hoaDonHienTai.getId_hoa_don());

                    // Sau đó xóa hóa đơn
                    hoaDonDAO.deleteHoaDon(hoaDonHienTai.getId_hoa_don());

                    JOptionPane.showMessageDialog(this, "Đã hủy hóa đơn thành công!", "Thông báo",
                            JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Đã xóa thông tin hiện tại!", "Thông báo",
                            JOptionPane.INFORMATION_MESSAGE);
                }

                // Reset sau khi hủy
                resetHoaDon();

                // Cập nhật lại danh sách hóa đơn
                loadDanhSachHoaDon();
                hienThiDanhSachHoaDon();

            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Lỗi khi hủy: " + e.getMessage(), "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }// GEN-LAST:event_jButton4ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {
        // Thanh toán hóa đơn
        thanhToanHoaDon();
    }

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {
        // Clear form
        int confirm = JOptionPane.showConfirmDialog(this,
                "Bạn có chắc chắn muốn xóa toàn bộ thông tin hiện tại?",
                "Xác nhận Clear Form",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            // Reset toàn bộ form
            resetHoaDon();
            JOptionPane.showMessageDialog(this, "Đã xóa toàn bộ thông tin!", "Thông báo",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void jTable3MouseClicked(java.awt.event.MouseEvent evt) {
        // Xử lý khi click vào bảng sản phẩm để thêm vào giỏ hàng
        if (evt.getClickCount() == 2) { // Double click
            int row = jTable3.getSelectedRow();
            if (row >= 0) {
                String maSanPham = (String) jTable3.getValueAt(row, 1); // Cột 1 vì cột 0 là ảnh

                // Tìm sản phẩm theo mã
                for (ao item : danhSachSanPham) {
                    if (item.getMa_ao().equals(maSanPham)) {
                        if (item.getSo_luong() > 0) {
                            themSanPhamVaoGioHang(item);
                        } else {
                            JOptionPane.showMessageDialog(this, "Sản phẩm đã hết hàng!",
                                    "Thông báo",
                                    JOptionPane.WARNING_MESSAGE);
                        }
                        break;
                    }
                }
            }
        }
    }

    private void jTable2MouseClicked(java.awt.event.MouseEvent evt) {
        // Xử lý khi click vào bảng giỏ hàng
        int row = jTable2.getSelectedRow();
        if (row >= 0 && row < gioHang.size()) {
            chi_tiet_hoa_don cartItem = gioHang.get(row);
            ao product = aoDAO.getAoById(cartItem.getId_ao());

            if (evt.getClickCount() == 1) { // Single click - Edit quantity
                CartManager.updateProductQuantity(gioHang, cartItem, product, modelGioHang, () -> {
                    hienThiGioHang();
                    DataRefreshManager.notifyCartChanged();
                }, true);
            } else if (evt.getClickCount() == 2) { // Double click - Remove
                CartManager.removeProductFromCart(gioHang, cartItem, modelGioHang, () -> {
                    hienThiGioHang();
                    DataRefreshManager.notifyCartChanged();
                });
            }
        }
    }

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {
        // Xử lý khi click vào bảng hóa đơn
        int row = jTable1.getSelectedRow();
        if (row >= 0) {
            // Lấy mã hóa đơn từ bảng (cột 1)
            String maHoaDon = (String) jTable1.getValueAt(row, 1);

            // Tìm hóa đơn theo mã
            hoa_don selectedInvoice = null;
            for (hoa_don hd : danhSachHoaDon) {
                if (hd.getMa_hoa_don().equals(maHoaDon)) {
                    selectedInvoice = hd;
                    break;
                }
            }

            if (selectedInvoice != null) {
                if (evt.getClickCount() == 1) { // Single click
                    // Show invoice action options
                    showInvoiceActionDialog(selectedInvoice);
                } else if (evt.getClickCount() == 2) { // Double click
                    // Quick action based on invoice status
                    if (selectedInvoice.isChuaThanhToan()) {
                        // Load for editing
                        loadInvoiceForEditing(selectedInvoice);
                    } else {
                        // Show details
                        InvoiceManager.showInvoiceDetails(selectedInvoice);
                    }
                }
            }
        }
    }

    /**
     * Show invoice action dialog with options
     */
    private void showInvoiceActionDialog(hoa_don invoice) {
        String[] options = InvoiceManager.getInvoiceActionOptions(invoice);

        String selectedAction = (String) JOptionPane.showInputDialog(
                this,
                "Chọn hành động cho hóa đơn '" + invoice.getMa_hoa_don() + "':",
                "Tùy chọn hóa đơn",
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]);

        if (selectedAction != null && !selectedAction.equals("Đóng")) {
            InvoiceManager.handleInvoiceAction(invoice, selectedAction, gioHang,
                    () -> {
                        hienThiGioHang();
                        DataRefreshManager.notifyCartChanged();
                        // Set current invoice for editing
                        hoaDonHienTai = invoice;
                        loadInvoiceCustomerInfo(invoice);
                        // Load voucher after cart is displayed and tongTien is calculated
                        loadInvoiceVoucher(invoice);
                    },
                    () -> {
                        DataRefreshManager.notifyInvoiceChanged();
                    });
        }
    }

    /**
     * Load invoice for editing (quick action)
     */
    private void loadInvoiceForEditing(hoa_don invoice) {
        boolean success = InvoiceManager.loadInvoiceForEditing(invoice, gioHang, () -> {
            hienThiGioHang();
            DataRefreshManager.notifyCartChanged();
        });

        if (success) {
            // Set current invoice for editing
            hoaDonHienTai = invoice;
            loadInvoiceCustomerInfo(invoice);

            // Use SwingUtilities.invokeLater to ensure tongTien is calculated before
            // loading voucher
            javax.swing.SwingUtilities.invokeLater(() -> {
                loadInvoiceVoucher(invoice);
            });
        }
    }

    /**
     * Load customer info from invoice
     */
    private void loadInvoiceCustomerInfo(hoa_don invoice) {
        // Load customer info
        if (invoice.getHo_va_ten_khach() != null && !invoice.getHo_va_ten_khach().isEmpty()) {
            txtTenKH.setText(invoice.getHo_va_ten_khach());
            txtSDT.setText(invoice.getSo_dien_thoai_khach());
            // Note: No address field in current form

            // Try to find and set customer object
            khachHangHienTai = getKhachHangBySDT(invoice.getSo_dien_thoai_khach());
        }
    }

    /**
     * Load voucher from invoice (call after cart is loaded and tongTien
     * calculated)
     */
    private void loadInvoiceVoucher(hoa_don invoice) {
        // Load voucher info
        if (invoice.getId_voucher() > 0) {
            try {
                voucher voucherFromInvoice = voucherDAO.getVoucherById(invoice.getId_voucher());
                if (voucherFromInvoice != null) {
                    voucherHienTai = voucherFromInvoice;

                    // Calculate discount with current tongTien
                    System.out.println("Loading voucher: " + voucherFromInvoice.getTen_voucher()
                            + ", tongTien: " + tongTien);

                    giamGia = voucherDAO.calculateDiscount(voucherFromInvoice.getMa_voucher(),
                            tongTien);

                    // Update display
                    jLabel11.setText(CurrencyUtils.formatVND((int) giamGia));
                    capNhatTienThoi();

                    System.out.println("Voucher loaded successfully: "
                            + voucherHienTai.getTen_voucher()
                            + ", discount: " + giamGia);

                    // Check if voucher is still valid
                    if (voucherFromInvoice.getSo_luong() <= 0) {
                        JOptionPane.showMessageDialog(this,
                                "Cảnh báo: Voucher trong hóa đơn đã hết số lượng!",
                                "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Voucher trong hóa đơn không tồn tại!",
                            "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception e) {
                System.err.println("Lỗi khi load voucher từ hóa đơn: " + e.getMessage());
                e.printStackTrace();
                JOptionPane.showMessageDialog(this,
                        "Lỗi khi tải voucher: " + e.getMessage(),
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            // Reset voucher if invoice has no voucher
            voucherHienTai = null;
            giamGia = 0;
            jLabel11.setText(CurrencyUtils.formatVND(0));
            capNhatTienThoi();
        }
    }

    private void jTextField5KeyReleased(java.awt.event.KeyEvent evt) {
        // Cập nhật tiền thừa khi nhập tiền khách đưa
        capNhatTienThoi();
    }

    private void newPayActionPerformed(java.awt.event.ActionEvent evt) {
        System.out.println("=== NEW PAY BUTTON CLICKED ===");

        // Tạo hóa đơn mới luôn, không kiểm tra gì
        taoHoaDonMoiFromButton();

        System.out.println("=== NEW PAY BUTTON COMPLETE ===");
    }
//        private void newPayActionPerformed(java.awt.event.ActionEvent evt) {
//                // Xử lý tạo hóa đơn mới
//                System.out.println("=== NEW PAY BUTTON CLICKED ===");
//
//                // Kiểm tra giỏ hàng có trống không trước khi tạo hóa đơn
//                if (gioHang == null || gioHang.isEmpty()) {
//                        JOptionPane.showMessageDialog(this,
//                                        "Giỏ hàng trống! Vui lòng thêm sản phẩm trước khi tạo hóa đơn mới.",
//                                        "Thông báo",
//                                        JOptionPane.WARNING_MESSAGE);
//
//                        // Clear form để cung cấp trạng thái sạch cho người dùng
//                        resetHoaDon();
//
//                        System.out.println("=== NEW PAY BUTTON CANCELLED - EMPTY CART ===");
//                        return; // Thoát sớm không tạo hóa đơn
//                }
//
//                // Kiểm tra xem có hóa đơn hiện tại chưa thanh toán không
//                if (hoaDonHienTai != null && hoaDonHienTai.isChuaThanhToan()) {
//                        int choice = JOptionPane.showConfirmDialog(this,
//                                        "Bạn có hóa đơn chưa thanh toán. Bạn có muốn lưu tạm và tạo hóa đơn mới không?",
//                                        "Xác nhận tạo hóa đơn mới",
//                                        JOptionPane.YES_NO_OPTION,
//                                        JOptionPane.QUESTION_MESSAGE);
//
//                        if (choice == JOptionPane.YES_OPTION) {
//                                // Lưu tạm hóa đơn hiện tại
//                                luuTamHoaDon();
//                                // Tạo hóa đơn mới
//                                taoHoaDonMoiFromButton();
//                        }
//                        // Nếu chọn NO thì không làm gì cả
//                } else {
//                        // Không có hóa đơn hiện tại hoặc đã thanh toán, tạo mới luôn
//                        taoHoaDonMoiFromButton();
//                }
//
//                System.out.println("=== NEW PAY BUTTON COMPLETE ===");
//        }

    private void luuTamHoaDon() {
        try {
            System.out.println("Lưu tạm hóa đơn hiện tại: " + hoaDonHienTai.getMa_hoa_don());

            // Cập nhật hóa đơn trong database với trạng thái "chưa thanh toán"
            hoaDonDAO.updateHoaDon(hoaDonHienTai);

            // Refresh danh sách hóa đơn để hiển thị hóa đơn vừa lưu tạm
            loadDanhSachHoaDon();

            JOptionPane.showMessageDialog(this,
                    "Đã lưu tạm hóa đơn " + hoaDonHienTai.getMa_hoa_don(),
                    "Thông báo",
                    JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception e) {
            System.err.println("Lỗi khi lưu tạm hóa đơn: " + e.getMessage());
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Lỗi khi lưu tạm hóa đơn: " + e.getMessage(),
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    ///////
        private void taoHoaDonMoiFromButton() {
        try {
            System.out.println("Tạo hóa đơn mới từ button...");

            // Kiểm tra giỏ hàng có trống không (double-check safety)
//            if (gioHang == null || gioHang.isEmpty()) {
//                JOptionPane.showMessageDialog(this,
//                        "Không thể tạo hóa đơn với giỏ hàng trống!",
//                        "Lỗi",
//                        JOptionPane.ERROR_MESSAGE);
//                return;
//            }
            // Kiểm tra giới hạn 10 hóa đơn chưa thanh toán
            if (demHoaDonChuaThanhToan() >= 5) {
                JOptionPane.showMessageDialog(this,
                        "Đã đạt giới hạn tối đa 5 hóa đơn chưa thanh toán!\n"
                        + "Vui lòng thanh toán hoặc hủy một số hóa đơn trước khi tạo mới.",
                        "Cảnh báo",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Gọi method existing để tạo hóa đơn mới
            taoHoaDonMoi(); // Gọi method existing

            JOptionPane.showMessageDialog(this,
                    "Đã tạo hóa đơn mới: " + hoaDonHienTai.getMa_hoa_don(),
                    "Thông báo",
                    JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception e) {
            System.err.println("Lỗi khi tạo hóa đơn mới: " + e.getMessage());
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Lỗi khi tạo hóa đơn mới: " + e.getMessage(),
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Đếm số hóa đơn chưa thanh toán
     */
    private int demHoaDonChuaThanhToan() {
        int count = 0;
        if (danhSachHoaDon != null) {
            for (hoa_don hd : danhSachHoaDon) {
                if (hd.isChuaThanhToan()) {
                    count++;
                }
            }
        }
        return count;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JButton newPay;
    private javax.swing.JTextField txtSDT;
    private javax.swing.JTextField txtTenKH;
    private javax.swing.JTextField txtTienKhachDua;
    // End of variables declaration//GEN-END:variables

    // Setup window resize listener for table responsiveness
    private void setupWindowResizeListener() {
        // Add component listener to the main panel to handle resize events
        this.addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent evt) {
                // Refresh table layouts when window is resized
                javax.swing.SwingUtilities.invokeLater(() -> {
                    TableResponsiveManager.refreshTableLayout(jTable1);
                    TableResponsiveManager.refreshTableLayout(jTable2);
                    TableResponsiveManager.refreshTableLayout(jTable3);
                });
            }
        });
    }

    /**
     * Cleanup resources when panel is destroyed
     */
    public void cleanup() {
        TableResponsiveManager.cleanup(jTable1);
        TableResponsiveManager.cleanup(jTable2);
        TableResponsiveManager.cleanup(jTable3);
    }
}
