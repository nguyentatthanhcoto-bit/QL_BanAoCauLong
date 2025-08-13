/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package screen;

import com.mycompany.shop.dao.hoa_don_dao;
import com.mycompany.shop.dao.chi_tiet_hoa_don_dao;
import com.mycompany.shop.dao.khach_hang_dao;
import com.mycompany.shop.dao.nhan_vien_dao;
import com.mycompany.shop.dao.ao_dao;
import com.mycompany.shop.model.hoa_don;
import com.mycompany.shop.model.chi_tiet_hoa_don;
import com.mycompany.shop.model.khach_hang;
import com.mycompany.shop.model.nhan_vien;
import com.mycompany.shop.util.CurrencyUtils;
import com.mycompany.shop.util.ModernTheme;
import com.mycompany.shop.util.UIHelper;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;
import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;

/**
 *
 * @author mailx
 */
public class HoaDon extends javax.swing.JPanel {

    // DAO objects
    private hoa_don_dao hoaDonDAO;
    private chi_tiet_hoa_don_dao chiTietHoaDonDAO;
    private khach_hang_dao khachHangDAO;
    private nhan_vien_dao nhanVienDAO;
    private ao_dao aoDAO;

    // Table models
    private DefaultTableModel modelHoaDon;
    private DefaultTableModel modelChiTietHoaDon;

    // Data lists
    private List<hoa_don> danhSachHoaDon;
    private List<hoa_don> danhSachHoaDonGoc; // Original list for search functionality
    private List<chi_tiet_hoa_don> danhSachChiTietHoaDon;

    // Current selected invoice
    private hoa_don hoaDonDangChon = null;

    // Search state
    private boolean isSearching = false;

    /**
     * Creates new form HoaDon
     */
    public HoaDon() {
        initComponents();
        initDAO();
        setupComponents();
        loadData();
        applyModernTheme();
    }

    private void initDAO() {
        try {
            hoaDonDAO = new hoa_don_dao();
            chiTietHoaDonDAO = new chi_tiet_hoa_don_dao();
            khachHangDAO = new khach_hang_dao();
            nhanVienDAO = new nhan_vien_dao();
            aoDAO = new ao_dao();
        } catch (Exception e) {
            System.err.println("Lỗi khi khởi tạo DAO: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void setupComponents() {
        // Setup table models
        modelHoaDon = (DefaultTableModel) jTableHoaDon.getModel();
        modelHoaDon.setColumnIdentifiers(new String[] {
                "Mã HĐ", "Ngày tạo", "Khách hàng", "Nhân viên", "Tổng tiền", "Trạng thái"
        });

        modelChiTietHoaDon = (DefaultTableModel) jTableChiTietHoaDon.getModel();
        modelChiTietHoaDon.setColumnIdentifiers(new String[] {
                "Mã SP", "Tên SP", "Màu sắc", "Size", "Loại", "Số lượng", "Đơn giá", "Thành tiền"
        });

        // Add table selection listener
        jTableHoaDon.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                loadChiTietHoaDon();
            }
        });
    }

    private void applyModernTheme() {
        // Apply modern theme
        ModernTheme.styleTable(jTableHoaDon);
        ModernTheme.styleTable(jTableChiTietHoaDon);

        // Style buttons
        ModernTheme.styleButton(jButtonRefresh, ModernTheme.ButtonType.PRIMARY, ModernTheme.ButtonSize.MEDIUM);
        ModernTheme.styleButton(jButtonXemChiTiet, ModernTheme.ButtonType.OUTLINE, ModernTheme.ButtonSize.MEDIUM);
        ModernTheme.styleButton(jButtonXuatHoaDon, ModernTheme.ButtonType.SUCCESS, ModernTheme.ButtonSize.MEDIUM);

        // Set background
        this.setBackground(ModernTheme.BACKGROUND_COLOR);
    }

    private void loadData() {
        loadDanhSachHoaDon();
    }

    private void loadDanhSachHoaDon() {
        try {
            danhSachHoaDon = hoaDonDAO.getAllHoaDon();
            danhSachHoaDonGoc = new ArrayList<>(danhSachHoaDon); // Keep original list for search
            hienThiDanhSachHoaDon();
        } catch (Exception e) {
            System.err.println("Lỗi khi load danh sách hóa đơn: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void hienThiDanhSachHoaDon() {
        modelHoaDon.setRowCount(0);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        if (danhSachHoaDon != null) {
            for (hoa_don hoaDon : danhSachHoaDon) {
                String tenKhachHang = getTenKhachHang(hoaDon.getId_khach_hang(),
                        hoaDon.getHo_va_ten_khach());
                String tenNhanVien = getTenNhanVien(hoaDon.getId_nhan_vien());
                String trangThai = hoaDon.isDaThanhToan() ? "Đã thanh toán" : "Chưa thanh toán";

                modelHoaDon.addRow(new Object[] {
                        hoaDon.getMa_hoa_don(),
                        sdf.format(hoaDon.getNgay_tao()),
                        tenKhachHang,
                        tenNhanVien,
                        CurrencyUtils.formatVND(hoaDon.getTong_tien()),
                        trangThai
                });
            }
        }
    }

    private void loadChiTietHoaDon() {
        int selectedRow = jTableHoaDon.getSelectedRow();
        if (selectedRow >= 0) {
            String maHoaDon = (String) modelHoaDon.getValueAt(selectedRow, 0);

            // Find selected invoice
            hoaDonDangChon = null;
            if (danhSachHoaDon != null) {
                for (hoa_don hd : danhSachHoaDon) {
                    if (hd.getMa_hoa_don().equals(maHoaDon)) {
                        hoaDonDangChon = hd;
                        break;
                    }
                }
            }

            if (hoaDonDangChon != null) {
                try {
                    danhSachChiTietHoaDon = chiTietHoaDonDAO.getChiTietHoaDonByIdHoaDon(hoaDonDangChon.getId_hoa_don());
                    hienThiChiTietHoaDon();
                } catch (Exception e) {
                    System.err.println("Lỗi khi load chi tiết hóa đơn: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        } else {
            // Clear chi tiết table if no invoice selected
            modelChiTietHoaDon.setRowCount(0);
            hoaDonDangChon = null;
        }
    }

    private void hienThiChiTietHoaDon() {
        modelChiTietHoaDon.setRowCount(0);

        if (danhSachChiTietHoaDon != null) {
            for (chi_tiet_hoa_don chiTiet : danhSachChiTietHoaDon) {
                String maSanPham = getMaSanPham(chiTiet.getId_ao());
                String tenSanPham = getTenSanPham(chiTiet.getId_ao());
                String mauSac = getMauSacSanPham(chiTiet.getId_ao());
                String size = getSizeSanPham(chiTiet.getId_ao());
                String loai = getLoaiSanPham(chiTiet.getId_ao());
                long thanhTien = chiTiet.getSo_luong() * chiTiet.getDon_gia();

                modelChiTietHoaDon.addRow(new Object[] {
                        maSanPham,
                        tenSanPham,
                        mauSac,
                        size,
                        loai,
                        chiTiet.getSo_luong(),
                        CurrencyUtils.formatVND(chiTiet.getDon_gia()),
                        CurrencyUtils.formatVND(thanhTien)
                });
            }
        }
    }

    private String getTenKhachHang(Integer idKhachHang, String hoVaTenKhach) {
        if (idKhachHang != null) {
            try {
                khach_hang kh = khachHangDAO.getKhachHangById(idKhachHang);
                return kh != null ? kh.getHo_va_ten() : "Không xác định";
            } catch (Exception e) {
                return "Lỗi load tên";
            }
        } else if (hoVaTenKhach != null && !hoVaTenKhach.trim().isEmpty()) {
            return hoVaTenKhach + " (Vãng lai)";
        } else {
            return "Khách vãng lai";
        }
    }

    private String getTenNhanVien(int idNhanVien) {
        try {
            nhan_vien nv = nhanVienDAO.getNhanVienById(idNhanVien);
            return nv != null ? nv.getTen_nhan_vien() : "Không xác định";
        } catch (Exception e) {
            return "Lỗi load tên";
        }
    }

    private String getMaSanPham(int idAo) {
        try {
            com.mycompany.shop.model.ao ao = aoDAO.getAoById(idAo);
            return ao != null ? ao.getMa_ao() : "SP_" + idAo;
        } catch (Exception e) {
            return "SP_" + idAo;
        }
    }

    private String getTenSanPham(int idAo) {
        try {
            com.mycompany.shop.model.ao ao = aoDAO.getAoById(idAo);
            return ao != null ? ao.getTen_ao() : "Sản phẩm " + idAo;
        } catch (Exception e) {
            return "Sản phẩm " + idAo;
        }
    }

    private String getMauSacSanPham(int idAo) {
        try {
            com.mycompany.shop.model.ao ao = aoDAO.getAoById(idAo);
            if (ao != null) {
                com.mycompany.shop.dao.mau_sac_dao mauSacDAO = new com.mycompany.shop.dao.mau_sac_dao();
                com.mycompany.shop.model.mau_sac mauSac = mauSacDAO.getMauSacById(ao.getId_mau_sac());
                return mauSac != null ? mauSac.getTen_mau_sac() : "Không xác định";
            }
            return "Không xác định";
        } catch (Exception e) {
            return "Không xác định";
        }
    }

    private String getSizeSanPham(int idAo) {
        try {
            com.mycompany.shop.model.ao ao = aoDAO.getAoById(idAo);
            if (ao != null) {
                com.mycompany.shop.dao.size_dao sizeDAO = new com.mycompany.shop.dao.size_dao();
                com.mycompany.shop.model.size size = sizeDAO.getSizeById(ao.getId_size());
                return size != null ? size.getTen_size() : "Không xác định";
            }
            return "Không xác định";
        } catch (Exception e) {
            return "Không xác định";
        }
    }

    private String getLoaiSanPham(int idAo) {
        try {
            com.mycompany.shop.model.ao ao = aoDAO.getAoById(idAo);
            if (ao != null) {
                com.mycompany.shop.dao.loai_ao_dao loaiAoDAO = new com.mycompany.shop.dao.loai_ao_dao();
                com.mycompany.shop.model.loai_ao loaiAo = loaiAoDAO.getLoaiAoById(ao.getId_loai());
                return loaiAo != null ? loaiAo.getTen_loai() : "Không xác định";
            }
            return "Không xác định";
        } catch (Exception e) {
            return "Không xác định";
        }
    }

    /**
     * Search invoices by customer name, employee name, or invoice code
     */
    private void timKiemHoaDon(String searchTerm) {
        if (searchTerm == null || searchTerm.trim().isEmpty()) {
            // If search term is empty, show all invoices
            danhSachHoaDon = new ArrayList<>(danhSachHoaDonGoc);
            isSearching = false;
        } else {
            try {
                // Use DAO search method for database search
                danhSachHoaDon = hoaDonDAO.searchHoaDon(searchTerm.trim());
                isSearching = true;
            } catch (Exception e) {
                System.err.println("Lỗi khi tìm kiếm hóa đơn: " + e.getMessage());
                e.printStackTrace();
                JOptionPane.showMessageDialog(this,
                        "Lỗi khi tìm kiếm: " + e.getMessage(),
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        hienThiDanhSachHoaDon();

        // Show search result info
        if (isSearching) {
            String message = String.format("Tìm thấy %d hóa đơn với từ khóa: '%s'",
                    danhSachHoaDon.size(), searchTerm);
            System.out.println(message);
        }
    }

    /**
     * Clear search and show all invoices
     */
    private void xoaTimKiem() {
        danhSachHoaDon = new ArrayList<>(danhSachHoaDonGoc);
        isSearching = false;
        hienThiDanhSachHoaDon();

        // Clear search field
        if (jTextFieldSearch != null) {
            jTextFieldSearch.setText("");
        }

        System.out.println("Đã xóa tìm kiếm, hiển thị tất cả " + danhSachHoaDon.size() + " hóa đơn");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jTextFieldSearch = new javax.swing.JTextField();
        jButtonSearch = new javax.swing.JButton();
        jButtonClearSearch = new javax.swing.JButton();
        jButtonRefresh = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableHoaDon = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTableChiTietHoaDon = new javax.swing.JTable();
        jButtonXemChiTiet = new javax.swing.JButton();
        jButtonXuatHoaDon = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Helvetica Neue", 1, 24)); // NOI18N
        jLabel1.setText("QUẢN LÝ HÓA ĐƠN");

        jLabel3.setText("Tìm kiếm:");

        jTextFieldSearch.setToolTipText("Nhập tên khách hàng hoặc tên nhân viên");
        jTextFieldSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextFieldSearchKeyReleased(evt);
            }
        });

        jButtonSearch.setText("Tìm");
        jButtonSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSearchActionPerformed(evt);
            }
        });

        jButtonClearSearch.setText("Xóa");
        jButtonClearSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonClearSearchActionPerformed(evt);
            }
        });

        jButtonRefresh.setText("Làm mới");
        jButtonRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRefreshActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButtonRefresh)
                .addGap(218, 218, 218)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFieldSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonSearch)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonClearSearch)
                .addGap(103, 103, 103))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jButtonRefresh)
                    .addComponent(jLabel3)
                    .addComponent(jTextFieldSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonSearch)
                    .addComponent(jButtonClearSearch))
                .addGap(20, 20, 20))
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel2.setFont(new java.awt.Font("Helvetica Neue", 1, 16)); // NOI18N
        jLabel2.setText("DANH SÁCH HÓA ĐƠN");

        jTableHoaDon.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã HĐ", "Ngày tạo", "Khách hàng", "Nhân viên", "Tổng tiền", "Trạng thái"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTableHoaDon);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(20, 20, 20))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabel2)
                .addGap(10, 10, 10)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 328, Short.MAX_VALUE)
                .addGap(10, 10, 10))
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jLabel4.setFont(new java.awt.Font("Helvetica Neue", 1, 16)); // NOI18N
        jLabel4.setText("CHI TIẾT HÓA ĐƠN");

        jTableChiTietHoaDon.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã SP", "Tên SP", "Số lượng", "Đơn giá", "Thành tiền"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(jTableChiTietHoaDon);

        jButtonXemChiTiet.setText("Xem chi tiết");
        jButtonXemChiTiet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonXemChiTietActionPerformed(evt);
            }
        });

        jButtonXuatHoaDon.setText("Xuất hóa đơn");
        jButtonXuatHoaDon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonXuatHoaDonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 1160, Short.MAX_VALUE)
                        .addComponent(jButtonXemChiTiet)
                        .addGap(10, 10, 10)
                        .addComponent(jButtonXuatHoaDon)))
                .addGap(20, 20, 20))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jButtonXemChiTiet)
                    .addComponent(jButtonXuatHoaDon))
                .addGap(10, 10, 10)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 328, Short.MAX_VALUE)
                .addGap(10, 10, 10))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonRefreshActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButtonRefreshActionPerformed
        // Làm mới dữ liệu và reset search
        loadData();
        xoaTimKiem();
        JOptionPane.showMessageDialog(this, "Đã làm mới dữ liệu!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
    }// GEN-LAST:event_jButtonRefreshActionPerformed

    private void jButtonXemChiTietActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButtonXemChiTietActionPerformed
        // Xem chi tiết hóa đơn được chọn
        int selectedRow = jTableHoaDon.getSelectedRow();
        if (selectedRow >= 0) {
            loadChiTietHoaDon();
            JOptionPane.showMessageDialog(this, "Đã load chi tiết hóa đơn!", "Thông báo",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn hóa đơn để xem chi tiết!", "Thông báo",
                    JOptionPane.WARNING_MESSAGE);
        }
    }// GEN-LAST:event_jButtonXemChiTietActionPerformed

    private void jButtonXuatHoaDonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButtonXuatHoaDonActionPerformed
        // Xuất hóa đơn
        int selectedRow = jTableHoaDon.getSelectedRow();
        if (selectedRow >= 0) {
            String maHoaDon = (String) modelHoaDon.getValueAt(selectedRow, 0);
            JOptionPane.showMessageDialog(this, "Chức năng xuất hóa đơn " + maHoaDon + " đang được phát triển!",
                    "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn hóa đơn để xuất!", "Thông báo",
                    JOptionPane.WARNING_MESSAGE);
        }
    }// GEN-LAST:event_jButtonXuatHoaDonActionPerformed

    private void jButtonSearchActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButtonSearchActionPerformed
        // Tìm kiếm hóa đơn
        String searchTerm = jTextFieldSearch.getText().trim();
        if (!searchTerm.isEmpty()) {
            timKiemHoaDon(searchTerm);
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập từ khóa tìm kiếm!", "Thông báo",
                    JOptionPane.WARNING_MESSAGE);
        }
    }// GEN-LAST:event_jButtonSearchActionPerformed

    private void jButtonClearSearchActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButtonClearSearchActionPerformed
        // Xóa tìm kiếm
        xoaTimKiem();
        JOptionPane.showMessageDialog(this, "Đã xóa tìm kiếm!", "Thông báo",
                JOptionPane.INFORMATION_MESSAGE);
    }// GEN-LAST:event_jButtonClearSearchActionPerformed

    private void jTextFieldSearchKeyReleased(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_jTextFieldSearchKeyReleased
        // Tìm kiếm khi nhấn Enter
        if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
            String searchTerm = jTextFieldSearch.getText().trim();
            timKiemHoaDon(searchTerm);
        }
    }// GEN-LAST:event_jTextFieldSearchKeyReleased

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonClearSearch;
    private javax.swing.JButton jButtonRefresh;
    private javax.swing.JButton jButtonSearch;
    private javax.swing.JButton jButtonXemChiTiet;
    private javax.swing.JButton jButtonXuatHoaDon;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTableChiTietHoaDon;
    private javax.swing.JTable jTableHoaDon;
    private javax.swing.JTextField jTextFieldSearch;
    // End of variables declaration//GEN-END:variables
}
