package screen;

import com.mycompany.shop.util.FormLayoutOptimizer;
import com.mycompany.shop.dao.ao_dao;
import com.mycompany.shop.dao.chi_tiet_hoa_don_dao;
import com.mycompany.shop.dao.hoa_don_dao;
import com.mycompany.shop.dao.khach_hang_dao;
import com.mycompany.shop.dao.loai_ao_dao;
import com.mycompany.shop.dao.mau_sac_dao;
import com.mycompany.shop.dao.nhan_vien_dao;
import com.mycompany.shop.dao.size_dao;
import com.mycompany.shop.dao.voucher_dao;
import com.mycompany.shop.model.UserSession;
import com.mycompany.shop.model.ao;
import com.mycompany.shop.model.chi_tiet_hoa_don;
import com.mycompany.shop.model.hoa_don;
import com.mycompany.shop.model.khach_hang;
import com.mycompany.shop.model.loai_ao;
import com.mycompany.shop.model.mau_sac;
import com.mycompany.shop.model.size;
import com.mycompany.shop.model.voucher;
import com.mycompany.shop.util.CartManager;
import com.mycompany.shop.util.CurrencyUtils;
import com.mycompany.shop.util.DataRefreshManager;
import com.mycompany.shop.util.TableStabilizer;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;


public class BanHang extends javax.swing.JFrame {

    
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
    public BanHang() {
        initComponents();
        setLocationRelativeTo(null);
         updateEmployeeInfo(); // Hiển thị thông tin nhân viên hiện tại
         loadData();
        
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
                        if (jLabel3 != null) {
                                jLabel3.setText(employeeInfo);
                        }
                } else {
                        System.err.println("WARNING: Không có thông tin đăng nhập!");
                        if (jLabel3 != null) {
                                jLabel3.setText("TẠO HÓA ĐƠN - Chưa đăng nhập");
                        }
                }
                 // Khởi tạo giỏ hàng
                gioHang = new ArrayList<>();
                 // Xóa thông tin hóa đơn hiện tại
                hoaDonHienTai = null;

                // Hiển thị thông tin giá ban đầu
                txtTenKH.setText("0");
                txtSDT1.setText("0");
                txtTienKhachDua.setText("0");
                lbTienThua.setText("0");
                lbTongTienHang1.setText("0");
                // Sự kiện mouse clicked cho bảng sản phẩm
                tblDanhSachSP.addMouseListener(new java.awt.event.MouseAdapter() {
                        public void mouseClicked(java.awt.event.MouseEvent evt) {
                                tblDanhSachSPMouseClicked(evt);
                        }
                });

                // Sự kiện mouse clicked cho bảng giỏ hàng
//                jTable2.addMouseListener(new java.awt.event.MouseAdapter() {
//                        public void mouseClicked(java.awt.event.MouseEvent evt) {
//                                jTable2MouseClicked(evt);
//                        }
//                });

                // Sự kiện mouse clicked cho bảng hóa đơn
//                jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
//                        public void mouseClicked(java.awt.event.MouseEvent evt) {
//                                jTable1MouseClicked(evt);
//                        }
//                });
                
        }
      private void loadData() {
                loadDanhSachSanPham();
//                loadDanhSachHoaDon();
//                loadDanhSachVoucher();

                hienThiDanhSachSanPham();
                //hienThiDanhSachHoaDon();

                // Khởi tạo giỏ hàng rỗng
                gioHang = new ArrayList<>();
                hienThiGioHang();

                // Setup event handlers
               // setupProductTableEvents();
        }

        private void loadDanhSachSanPham() {
                // Load chỉ sản phẩm còn hàng
                danhSachSanPham = aoDAO.getAoConHang();
        }

//        private void loadDanhSachHoaDon() {
//                danhSachHoaDon = hoaDonDAO.getAllHoaDon();
//        }
//
//        private void loadDanhSachVoucher() {
//                danhSachVoucher = voucherDAO.getActiveVouchers();
//        }
private void hienThiDanhSachSanPham() {
                // Đảm bảo model trỏ đúng bảng
    DefaultTableModel modelSanPham = (DefaultTableModel) tblDanhSachSP.getModel();

    // Xóa dữ liệu cũ
    modelSanPham.setRowCount(0);

    // Load dữ liệu vào bảng
    for (ao item : danhSachSanPham) {
        String tenLoai = getTenLoaiAo(item.getId_loai());
        String tenMauSac = getTenMauSac(item.getId_mau_sac());
        String tenSize = getTenSize(item.getId_size());

       

        // Thêm một hàng vào bảng
        modelSanPham.addRow(new Object[] {
          //  imageIcon,                   // Ảnh
            item.getMa_ao(),              // Mã SP
            item.getTen_ao(),             // Tên SP
            tenLoai,                      // Loại
            tenSize,                      // Size
            tenMauSac,                    // Màu sắc
            item.getSo_luong(),            // Số lượng
            item.getGia()// Đơn giá
        });
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
//        private void setupProductTableEvents() {
//                // Event click vào table sản phẩm để thêm vào giỏ hàng
//                jTable3.addMouseListener(new java.awt.event.MouseAdapter() {
//                        @Override
//                        public void mouseClicked(java.awt.event.MouseEvent evt) {
//                                if (evt.getClickCount() == 1) { // Single click
//                                        themSanPhamVaoGioHang();
//                                }
//                        }
//                });
//        }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tblHoaDon = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblDanhSachSP = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblGioHang = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtTienKhachDua = new javax.swing.JTextField();
        btnThanhToan = new javax.swing.JButton();
        btnTaoHoaDon = new javax.swing.JButton();
        btnHuyHoaDom = new javax.swing.JButton();
        txtTenKH = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        lbTienThua = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        lbTongTienHang1 = new javax.swing.JLabel();
        lbGiamGia = new javax.swing.JLabel();
        txtSDT1 = new javax.swing.JTextField();
        btnInHoaDon = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tblHoaDon.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "Mã HD", "Tên NV", "Trạng thái", "Ngày tạo", "Tổng tiền", "Voucher"
            }
        ));
        jScrollPane1.setViewportView(tblHoaDon);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 520, 261));

        tblDanhSachSP.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Ảnh", "Mã SP", "Tên SP", "Loại", "Size", "Màu sắc", "Số lượng", "Đơn giá"
            }
        ));
        tblDanhSachSP.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDanhSachSPMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblDanhSachSP);

        getContentPane().add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 310, 520, 343));

        tblGioHang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "Mã SP", "Tên SP", "Số lượng", "Đơn giá", "Thành tiền"
            }
        ));
        jScrollPane3.setViewportView(tblGioHang);

        getContentPane().add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 50, -1, 260));

        jLabel1.setText("SDT:");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 360, -1, -1));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setText("DANH SACH SAN PHAM");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 290, -1, -1));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setText("TAO HOA DON");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 0, -1, -1));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setText("HOA DON");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        jLabel5.setText("Tien Thua:");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 460, -1, -1));
        getContentPane().add(txtTienKhachDua, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 430, 260, -1));

        btnThanhToan.setBackground(new java.awt.Color(0, 204, 0));
        btnThanhToan.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnThanhToan.setText("THANH TOAN");
        getContentPane().add(btnThanhToan, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 500, 140, 40));

        btnTaoHoaDon.setBackground(new java.awt.Color(0, 204, 51));
        btnTaoHoaDon.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnTaoHoaDon.setText("TAO HOA DON");
        getContentPane().add(btnTaoHoaDon, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 500, 140, 40));

        btnHuyHoaDom.setBackground(new java.awt.Color(255, 0, 51));
        btnHuyHoaDom.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnHuyHoaDom.setText("HUY");
        getContentPane().add(btnHuyHoaDom, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 560, 140, 40));
        getContentPane().add(txtTenKH, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 320, 260, -1));

        jLabel6.setText("Ten Khach Hang:");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 320, -1, -1));

        lbTienThua.setText("999999999");
        getContentPane().add(lbTienThua, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 460, -1, -1));

        jLabel8.setText("Giam Gia:");
        getContentPane().add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 410, -1, -1));

        jLabel9.setText("Khach Dua:");
        getContentPane().add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 430, -1, -1));

        jLabel10.setText("Tong Tien Hang:");
        getContentPane().add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 380, -1, -1));

        lbTongTienHang1.setText("999999999");
        getContentPane().add(lbTongTienHang1, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 380, -1, -1));

        lbGiamGia.setText("999999999");
        getContentPane().add(lbGiamGia, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 410, -1, -1));
        getContentPane().add(txtSDT1, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 350, 260, -1));

        btnInHoaDon.setBackground(new java.awt.Color(255, 255, 0));
        btnInHoaDon.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnInHoaDon.setText("IN");
        getContentPane().add(btnInHoaDon, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 560, 140, 40));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel7.setText("GIO HANG");
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 20, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tblDanhSachSPMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDanhSachSPMouseClicked
        // Xử lý khi click vào bảng sản phẩm để thêm vào giỏ hàng
                if (evt.getClickCount() == 2) { // Double click
                        int row = tblDanhSachSP.getSelectedRow();
                        if (row >= 0) {
                                String maSanPham = (String) tblDanhSachSP.getValueAt(row, 1); // Cột 1 vì cột 0 là ảnh

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
                
    }//GEN-LAST:event_tblDanhSachSPMouseClicked
 private void hienThiGioHang() {
                // Use TableStabilizer for safe updates
                TableStabilizer.safeUpdateTable(tblGioHang, () -> {
                        modelGioHang.setRowCount(0);
                        tongTien = 0;

                        for (int i = 0; i < gioHang.size(); i++) {
                                chi_tiet_hoa_don chiTiet = gioHang.get(i);
                                ao sanPham = aoDAO.getAoById(chiTiet.getId_ao());

                                double thanhTien = chiTiet.getSo_luong() * chiTiet.getDon_gia();
                                tongTien += thanhTien;

                                modelGioHang.addRow(new Object[] {
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
    private void capNhatTienThoi() {
                // Cập nhật giảm giá
                lbGiamGia.setText(CurrencyUtils.formatVND((int) giamGia));

                try {
                        String tienKhachDuaStr = txtTienKhachDua.getText().trim();
                        if (!tienKhachDuaStr.isEmpty()) {
                                double khachDua = Double.parseDouble(tienKhachDuaStr);
                                double thanhTien = tongTien - giamGia;
                                double tienThoi = khachDua - thanhTien;

                                if (tienThoi >= 0) {
                                        lbTienThua.setText(CurrencyUtils.formatVND((int) tienThoi));
                                } else {
                                        lbTienThua.setText("Không đủ");
                                }
                        } else {
                                lbTienThua.setText(CurrencyUtils.formatVND(0));
                        }
                } catch (NumberFormatException e) {
                        lbTienThua.setText(CurrencyUtils.formatVND(0));
                }
        }
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(BanHang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(BanHang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(BanHang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(BanHang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new BanHang().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnHuyHoaDom;
    private javax.swing.JButton btnInHoaDon;
    private javax.swing.JButton btnTaoHoaDon;
    private javax.swing.JButton btnThanhToan;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lbGiamGia;
    private javax.swing.JLabel lbTienThua;
    private javax.swing.JLabel lbTongTienHang1;
    private javax.swing.JTable tblDanhSachSP;
    private javax.swing.JTable tblGioHang;
    private javax.swing.JTable tblHoaDon;
    private javax.swing.JTextField txtSDT1;
    private javax.swing.JTextField txtTenKH;
    private javax.swing.JTextField txtTienKhachDua;
    // End of variables declaration//GEN-END:variables
}
