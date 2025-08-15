/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package screen;

import com.mycompany.shop.dao.ao_dao;
import com.mycompany.shop.dao.loai_ao_dao;
import com.mycompany.shop.dao.mau_sac_dao;
import com.mycompany.shop.dao.size_dao;
import com.mycompany.shop.model.ao;
import com.mycompany.shop.model.loai_ao;
import com.mycompany.shop.model.mau_sac;
import com.mycompany.shop.model.size;
import com.mycompany.shop.util.CurrencyUtils;
import com.mycompany.shop.util.ModernTheme;
import com.mycompany.shop.util.UIHelper;
import com.mycompany.shop.util.TableResponsiveManager;
import com.mycompany.shop.util.ResponsiveLayoutManager;
import com.mycompany.shop.util.ComponentOptimizer;
import java.util.List;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author nguyenthanhquoc
 */
public class SanPham extends javax.swing.JPanel {

        private ao_dao aoDAO;
        private loai_ao_dao loaiAoDAO;
        private mau_sac_dao mauSacDAO;
        private size_dao sizeDAO;
        private ButtonGroup radioGroup;

        private List<ao> danhSachAo;
        private List<loai_ao> danhSachLoaiAo;
        private List<mau_sac> danhSachMauSac;
        private List<size> danhSachSize;

        private DefaultTableModel modelThuocTinh;
        private DefaultTableModel modelSanPham;

        // ComboBox references
        private javax.swing.JComboBox<String> jComboBoxSize;
        private javax.swing.JComboBox<String> jComboBoxMauSac;

        // Image components
        private javax.swing.JLabel jLabelImagePreview;
        private javax.swing.JButton jButtonChooseImage;
        private String currentImagePath = null;

        // Các loại thuộc tính
        private static final int LOAI_MAUSAC = 1;
        private static final int LOAI_KICHTHUOC = 2;
        private static final int LOAI_SANPHAM = 3;
        private int loaiThuocTinhDangChon = 0;

        // Biến để track sản phẩm đang được chỉnh sửa
        private String maSanPhamDangSua = null;

        /**
         * Creates new form SanPham
         */
        public SanPham() {
                System.out.println("=== SANPHAM CONSTRUCTOR START ===");

                System.out.println("1. Calling initComponents()...");
                initComponents();

                System.out.println("2. Optimizing form layout...");
                com.mycompany.shop.util.FormLayoutOptimizer.makeFormResponsive(this);

                System.out.println("3. Calling initDAO()...");
                initDAO();

                System.out.println("3. Calling setupComponents()...");
                setupComponents();

                System.out.println("4. Calling loadData()...");
                loadData();

                System.out.println("5. Calling applyModernTheme()...");
                applyModernTheme();

                System.out.println("=== SANPHAM CONSTRUCTOR COMPLETE ===");
                System.out.println("Final check - modelThuocTinh = " + modelThuocTinh);
                System.out.println("Final check - jTable1.getModel() = " + jTable1.getModel());
                System.out.println("Are they the same? " + (modelThuocTinh == jTable1.getModel()));
        }

        private void applyModernTheme() {
                // Apply modern theme with responsive design
                UIHelper.applyModernTheme(this);

                // Style text fields with responsive sizing
                ModernTheme.styleTextField(jTextField1, ModernTheme.ButtonSize.MEDIUM);
                ModernTheme.styleTextField(jTextField2, ModernTheme.ButtonSize.MEDIUM);
                ModernTheme.styleTextField(jTextField3, ModernTheme.ButtonSize.MEDIUM);
                ModernTheme.styleTextField(jTextField5, ModernTheme.ButtonSize.MEDIUM);
                ModernTheme.styleTextField(jTextField6, ModernTheme.ButtonSize.LARGE);
                ModernTheme.styleTextField(jTextField8, ModernTheme.ButtonSize.MEDIUM);

                // Style combo boxes
                ModernTheme.styleComboBox(jComboBox1);
                // ModernTheme.styleComboBox(jComboBox2);

                // Style product management buttons with appropriate sizes
                ModernTheme.styleButton(jButtonThemSanPham, ModernTheme.ButtonType.SUCCESS,
                                ModernTheme.ButtonSize.MEDIUM);
                ModernTheme.styleButton(jButtonSuaSanPham, ModernTheme.ButtonType.PRIMARY,
                                ModernTheme.ButtonSize.MEDIUM);
                ModernTheme.styleButton(jButtonXoaSanPham, ModernTheme.ButtonType.DANGER,
                                ModernTheme.ButtonSize.MEDIUM);

                // Style attribute management buttons
                ModernTheme.styleButton(jButton1, ModernTheme.ButtonType.SUCCESS, ModernTheme.ButtonSize.SMALL);
                ModernTheme.styleButton(jButton2, ModernTheme.ButtonType.PRIMARY, ModernTheme.ButtonSize.SMALL);
                ModernTheme.styleButton(jButton3, ModernTheme.ButtonType.DANGER, ModernTheme.ButtonSize.SMALL);

                // Style radio buttons
                styleRadioButtons();

                // Style tables with responsive behavior
                ModernTheme.styleTable(jTable1);
                ModernTheme.styleTable(jTable2);

                // Set background
                this.setBackground(ModernTheme.BACKGROUND_COLOR);

                // Make the entire panel responsive
                UIHelper.makeComponentsResponsive(this);
        }

        private void styleRadioButtons() {
                try {
                        jRadioButton1.setFont(ModernTheme.FONT_BODY);
                        jRadioButton1.setForeground(ModernTheme.TEXT_PRIMARY);
                        jRadioButton1.setBackground(ModernTheme.CARD_BACKGROUND);

                        jRadioButton2.setFont(ModernTheme.FONT_BODY);
                        jRadioButton2.setForeground(ModernTheme.TEXT_PRIMARY);
                        jRadioButton2.setBackground(ModernTheme.CARD_BACKGROUND);

                        // jRadioButton3.setFont(ModernTheme.FONT_BODY);
                        // jRadioButton3.setForeground(ModernTheme.TEXT_PRIMARY);
                        // jRadioButton3.setBackground(ModernTheme.CARD_BACKGROUND);
                } catch (Exception e) {
                        // Some radio buttons might not exist
                }
        }

        private void initDAO() {
                try {
                        aoDAO = new ao_dao();
                        loaiAoDAO = new loai_ao_dao();
                        mauSacDAO = new mau_sac_dao();
                        sizeDAO = new size_dao();
                } catch (Exception e) {
                        System.err.println("Lỗi khi khởi tạo DAO: " + e.getMessage());
                        JOptionPane.showMessageDialog(this,
                                        "Lỗi kết nối database! Vui lòng kiểm tra kết nối.",
                                        "Lỗi Database",
                                        JOptionPane.ERROR_MESSAGE);
                        e.printStackTrace();
                }
        }

        private void setupComponents() {
                // Khởi tạo nhóm radio button
                radioGroup = new ButtonGroup();
                radioGroup.add(jRadioButton1); // Màu sắc
                radioGroup.add(jRadioButton2); // Kích thước
                radioGroup.add(jRadioButton4); // Loại sản phẩm

                // *** QUAN TRỌNG: Thêm event listeners cho radio buttons ***
                System.out.println("Adding event listeners to radio buttons...");

                jRadioButton1.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                jRadioButton1ActionPerformed(evt);
                        }
                });

                jRadioButton2.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                jRadioButton2ActionPerformed(evt);
                        }
                });

                jRadioButton4.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                jRadioButton4ActionPerformed(evt);
                        }
                });

                System.out.println("Event listeners added successfully!");

                // *** QUAN TRỌNG: Thêm event listeners cho buttons Thêm/Sửa/Xóa ***
                System.out.println("Adding event listeners to buttons...");

                jButton1.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                jButton1ActionPerformed(evt);
                        }
                });

                jButton2.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                jButton2ActionPerformed(evt);
                        }
                });

                jButton3.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                jButton3ActionPerformed(evt);
                        }
                });

                System.out.println("Button event listeners added successfully!");

                // Ẩn radio button chất liệu vì không có trong database
                // jRadioButton3.setVisible(false);

                // Setup layout - gán reference từ jTextField4/jTextField7 (đã là ComboBox từ
                // .form)
                setupComboBoxLayout();

                // Ẩn các component không cần thiết
                jLabel13.setVisible(false); // Ẩn label "Chất liệu"
                // jComboBox2.setVisible(false); // Ẩn ComboBox chất liệu
                // Không ẩn jTextField7 và jTextField4 vì đã được thay thế bằng ComboBox

                // Thêm listener để refresh ComboBox khi chuyển tab
                setupTabChangeListener();

                // Setup image components
                setupImageComponents();

                // Thêm listener cho table để load dữ liệu khi click
                setupTableClickListener();

                // Setup search functionality
                setupSearchFunctionality();

                // Setup window resize listener for table responsiveness
                setupWindowResizeListener();

                // *** QUAN TRỌNG: Thiết lập model cho bảng PHẢI SAU initComponents() ***
                System.out.println("Setting up table models...");

                // Thiết lập model cho bảng thuộc tính
                modelThuocTinh = (DefaultTableModel) jTable1.getModel();
                System.out.println("modelThuocTinh = " + modelThuocTinh);
                modelThuocTinh.setColumnIdentifiers(new String[] { "STT", "Loại thuộc tính", "Tên thuộc tính" });
                System.out.println("Set column identifiers for modelThuocTinh");

                // Thiết lập model cho bảng sản phẩm
                modelSanPham = (DefaultTableModel) jTable2.getModel();
                System.out.println("modelSanPham = " + modelSanPham);
                modelSanPham.setColumnIdentifiers(
                                new String[] { "Ảnh", "Mã SP", "Tên SP", "Loại SP", "Kích thước", "Màu sắc", "Đơn giá",
                                                "Số lượng" });
                System.out.println("Set column identifiers for modelSanPham");

                // Apply specialized table responsive management
                TableResponsiveManager.optimizeProductTable(jTable1);
                TableResponsiveManager.optimizeProductTable(jTable2);

                // Make layout responsive
                ResponsiveLayoutManager.makeResponsive(this);

                // Optimize components (buttons, text fields, etc.)
                ComponentOptimizer.optimizeComponents(this);

                // Clear text fields
                jTextField1.setText("");
                jTextField2.setText("");
                jTextField3.setText("");
                // jTextField4 và jTextField7 bây giờ là ComboBox, reset về item đầu tiên
                if (jTextField4.getItemCount() > 0)
                        jTextField4.setSelectedIndex(0);
                jTextField5.setText("");
                jTextField6.setText("");
                if (jTextField7.getItemCount() > 0)
                        jTextField7.setSelectedIndex(0);
                jTextField8.setText("");
        }

        private void setupComboBoxLayout() {
                // Bây giờ jTextField4 và jTextField7 đã là ComboBox từ file .form
                // Chỉ cần gán reference và setup dữ liệu

                // jTextField4 bây giờ là ComboBox cho kích thước
                jComboBoxSize = jTextField4;

                // jTextField7 bây giờ là ComboBox cho màu sắc
                jComboBoxMauSac = jTextField7;

                // Khởi tạo và thêm button (tạm thời comment để test ComboBox trước)
                /*
                 * jButtonThemSanPham = new javax.swing.JButton();
                 * jButtonThemSanPham.setText("Thêm sản phẩm");
                 * jButtonThemSanPham.addActionListener(evt -> themSanPham());
                 * 
                 * jButtonSuaSanPham = new javax.swing.JButton();
                 * jButtonSuaSanPham.setText("Sửa sản phẩm");
                 * jButtonSuaSanPham.addActionListener(evt -> suaSanPham());
                 */
        }

        // Helper methods để lấy giá trị từ ComboBox
        private String getSelectedMauSac() {
                Object selected = jComboBoxMauSac.getSelectedItem();
                return selected != null ? selected.toString() : "";
        }

        private String getSelectedSize() {
                Object selected = jComboBoxSize.getSelectedItem();
                return selected != null ? selected.toString() : "";
        }

        private String getSelectedLoaiSanPham() {
                Object selected = jComboBox1.getSelectedItem();
                return selected != null ? selected.toString() : "";
        }

        // Helper methods để lấy ID từ ComboBox
        private int getIdLoaiFromComboBox() {
                String tenLoai = getSelectedLoaiSanPham();
                if (danhSachLoaiAo != null) {
                        for (loai_ao loai : danhSachLoaiAo) {
                                if (loai.getTen_loai().equals(tenLoai)) {
                                        return loai.getId_loai_ao();
                                }
                        }
                }
                return -1;
        }

        private int getIdMauSacFromComboBox() {
                String tenMauSac = getSelectedMauSac();
                if (danhSachMauSac != null) {
                        for (mau_sac mau : danhSachMauSac) {
                                if (mau.getTen_mau_sac().equals(tenMauSac)) {
                                        return mau.getId_mau_sac();
                                }
                        }
                }
                return -1;
        }

        private int getIdSizeFromComboBox() {
                String tenSize = getSelectedSize();
                if (danhSachSize != null) {
                        for (size s : danhSachSize) {
                                if (s.getTen_size().equals(tenSize)) {
                                        return s.getId_size();
                                }
                        }
                }
                return -1;
        }

        // Method clear form sản phẩm
        private void clearFormSanPham() {
                jTextField2.setText(""); // Mã SP
                jTextField3.setText(""); // Tên SP
                jTextField5.setText(""); // Số lượng
                jTextField6.setText(""); // Đơn giá
                // jTextArea1.setText(""); // Mô tả - sẽ sửa sau

                // Reset ComboBox về item đầu tiên
                if (jComboBox1.getItemCount() > 0)
                        jComboBox1.setSelectedIndex(0);
                if (jComboBoxMauSac.getItemCount() > 0)
                        jComboBoxMauSac.setSelectedIndex(0);
                if (jComboBoxSize.getItemCount() > 0)
                        jComboBoxSize.setSelectedIndex(0);

                // Reset image preview
                updateImagePreview(null);

                // Reset biến tracking
                maSanPhamDangSua = null;
        }

        // Method thêm sản phẩm mới
        private void themSanPham() {
                try {
                        // Lấy dữ liệu từ form
                        String maSP = jTextField2.getText().trim(); // Mã sản phẩm
                        String tenSP = jTextField3.getText().trim(); // Tên sản phẩm
                        String soLuongSP = jTextField5.getText().trim(); // Số lượng
                        String giaSP = jTextField6.getText().trim(); // Đơn giá
                        String moTa = ""; // Tạm thời để trống vì chưa có TextArea mô tả

                        // Hỏi người dùng có muốn thêm ảnh không
                        int choice = javax.swing.JOptionPane.showConfirmDialog(this,
                                        "Bạn có muốn thêm ảnh cho sản phẩm này không?",
                                        "Thêm ảnh sản phẩm",
                                        javax.swing.JOptionPane.YES_NO_OPTION);

                        if (choice == javax.swing.JOptionPane.YES_OPTION) {
                                // Chọn ảnh trước khi thêm sản phẩm
                                chooseImageForProduct(maSP);
                        }

                        // Validation
                        if (maSP.isEmpty() || tenSP.isEmpty() || giaSP.isEmpty() || soLuongSP.isEmpty()) {
                                JOptionPane.showMessageDialog(this, "Vui lòng điền đầy đủ thông tin!", "Lỗi",
                                                JOptionPane.ERROR_MESSAGE);
                                return;
                        }

                        // Kiểm tra mã sản phẩm trùng lặp
                        if (kiemTraMaSanPhamTonTai(maSP)) {
                                JOptionPane.showMessageDialog(this, "Mã sản phẩm đã tồn tại! Vui lòng chọn mã khác.",
                                                "Lỗi",
                                                JOptionPane.ERROR_MESSAGE);
                                return;
                        }

                        // Validate giá và số lượng
                        int gia, soLuong;
                        try {
                                gia = Integer.parseInt(giaSP);
                                soLuong = Integer.parseInt(soLuongSP);
                                if (gia <= 0 || soLuong < 0) {
                                        throw new NumberFormatException();
                                }
                        } catch (NumberFormatException e) {
                                JOptionPane.showMessageDialog(this, "Giá phải > 0 và số lượng phải >= 0!", "Lỗi",
                                                JOptionPane.ERROR_MESSAGE);
                                return;
                        }

                        // Lấy ID từ ComboBox
                        int idLoai = getIdLoaiFromComboBox();
                        int idMauSac = getIdMauSacFromComboBox();
                        int idSize = getIdSizeFromComboBox();

                        if (idLoai == -1 || idMauSac == -1 || idSize == -1) {
                                JOptionPane.showMessageDialog(this, "Vui lòng chọn đầy đủ loại, màu sắc và kích thước!",
                                                "Lỗi", JOptionPane.ERROR_MESSAGE);
                                return;
                        }

                        // Thêm vào database với image đã chọn
                        aoDAO.addAo(maSP, tenSP, gia, soLuong, moTa, currentImagePath, idLoai, idMauSac, idSize);

                        JOptionPane.showMessageDialog(this, "Thêm sản phẩm thành công!", "Thông báo",
                                        JOptionPane.INFORMATION_MESSAGE);

                        // Refresh danh sách và clear form
                        loadData();
                        clearFormSanPham();

                        // Thông báo cho các màn hình khác refresh danh sách sản phẩm
                        com.mycompany.shop.util.DataRefreshManager.notifyProductChanged();

                } catch (Exception e) {
                        JOptionPane.showMessageDialog(this, "Lỗi khi thêm sản phẩm: " + e.getMessage(), "Lỗi",
                                        JOptionPane.ERROR_MESSAGE);
                        e.printStackTrace();
                }
        }

        // Method sửa sản phẩm
        private void suaSanPham() {
                try {
                        // Kiểm tra xem có sản phẩm nào đang được chọn để sửa không
                        if (maSanPhamDangSua == null || maSanPhamDangSua.isEmpty()) {
                                JOptionPane.showMessageDialog(this,
                                                "Vui lòng chọn sản phẩm từ danh sách để sửa!",
                                                "Thông báo", JOptionPane.WARNING_MESSAGE);
                                return;
                        }

                        // Lấy dữ liệu từ form
                        String maSP = jTextField2.getText().trim(); // Mã sản phẩm
                        String tenSP = jTextField3.getText().trim(); // Tên sản phẩm
                        String soLuongSP = jTextField5.getText().trim(); // Số lượng
                        String giaSP = jTextField6.getText().trim(); // Đơn giá
                        String moTa = ""; // Tạm thời để trống vì chưa có TextArea mô tả

                        // Hỏi người dùng có muốn thay đổi ảnh không
                        int choice = javax.swing.JOptionPane.showConfirmDialog(this,
                                        "Bạn có muốn thay đổi ảnh cho sản phẩm này không?",
                                        "Thay đổi ảnh sản phẩm",
                                        javax.swing.JOptionPane.YES_NO_OPTION);

                        if (choice == javax.swing.JOptionPane.YES_OPTION) {
                                // Chọn ảnh mới
                                chooseImageForProduct(maSP);
                        }

                        // Validation
                        if (maSP.isEmpty() || tenSP.isEmpty() || giaSP.isEmpty() || soLuongSP.isEmpty()) {
                                JOptionPane.showMessageDialog(this, "Vui lòng điền đầy đủ thông tin!", "Lỗi",
                                                JOptionPane.ERROR_MESSAGE);
                                return;
                        }

                        // Kiểm tra mã sản phẩm trùng lặp (loại trừ sản phẩm hiện tại)
                        if (kiemTraMaSanPhamTrungLapKhiSua(maSP, maSanPhamDangSua)) {
                                JOptionPane.showMessageDialog(this, "Mã sản phẩm '" + maSP + "' đã tồn tại!", "Lỗi",
                                                JOptionPane.ERROR_MESSAGE);
                                return;
                        }

                        // Validate giá và số lượng
                        int gia, soLuong;
                        try {
                                gia = Integer.parseInt(giaSP);
                                soLuong = Integer.parseInt(soLuongSP);
                                if (gia <= 0 || soLuong < 0) {
                                        throw new NumberFormatException();
                                }
                        } catch (NumberFormatException e) {
                                JOptionPane.showMessageDialog(this, "Giá phải > 0 và số lượng phải >= 0!", "Lỗi",
                                                JOptionPane.ERROR_MESSAGE);
                                return;
                        }

                        // Lấy ID từ ComboBox
                        int idLoai = getIdLoaiFromComboBox();
                        int idMauSac = getIdMauSacFromComboBox();
                        int idSize = getIdSizeFromComboBox();

                        if (idLoai == -1 || idMauSac == -1 || idSize == -1) {
                                JOptionPane.showMessageDialog(this, "Vui lòng chọn đầy đủ loại, màu sắc và kích thước!",
                                                "Lỗi", JOptionPane.ERROR_MESSAGE);
                                return;
                        }

                        // Tìm sản phẩm cần sửa
                        ao sanPhamCanSua = null;
                        for (ao sp : danhSachAo) {
                                if (sp.getMa_ao().equals(maSanPhamDangSua)) {
                                        sanPhamCanSua = sp;
                                        break;
                                }
                        }

                        if (sanPhamCanSua == null) {
                                JOptionPane.showMessageDialog(this, "Không tìm thấy sản phẩm cần sửa!", "Lỗi",
                                                JOptionPane.ERROR_MESSAGE);
                                return;
                        }

                        // Cập nhật trong database - sử dụng ảnh mới nếu có, không thì giữ nguyên
                        String image;
                        if (currentImagePath != null && !currentImagePath.isEmpty()) {
                                // Có ảnh mới được chọn
                                image = currentImagePath;
                                System.out.println("Sử dụng ảnh mới: " + image);
                        } else {
                                // Không có ảnh mới, giữ nguyên ảnh cũ
                                image = sanPhamCanSua.getImage();
                                System.out.println("Giữ nguyên ảnh cũ: " + image);
                        }

                        System.out.println("Calling aoDAO.updateAo() with image: " + image);
                        aoDAO.updateAo(sanPhamCanSua.getId_ao(), maSP, tenSP, gia, soLuong, moTa, image, idLoai,
                                        idMauSac, idSize);
                        System.out.println("aoDAO.updateAo() completed successfully");

                        JOptionPane.showMessageDialog(this, "Sửa sản phẩm thành công!", "Thành công",
                                        JOptionPane.INFORMATION_MESSAGE);

                        // Refresh danh sách và clear form
                        loadData();
                        clearFormSanPham();

                        // Thông báo cho các màn hình khác refresh danh sách sản phẩm
                        com.mycompany.shop.util.DataRefreshManager.notifyProductChanged();

                } catch (Exception e) {
                        JOptionPane.showMessageDialog(this, "Lỗi khi sửa sản phẩm: " + e.getMessage(), "Lỗi",
                                        JOptionPane.ERROR_MESSAGE);
                        e.printStackTrace();
                }
        }

        // Method xóa sản phẩm
        private void xoaSanPham() {
                try {
                        // Kiểm tra xem có sản phẩm nào đang được chọn để xóa không
                        if (maSanPhamDangSua == null || maSanPhamDangSua.isEmpty()) {
                                JOptionPane.showMessageDialog(this,
                                                "Vui lòng chọn sản phẩm từ danh sách để xóa!",
                                                "Thông báo", JOptionPane.WARNING_MESSAGE);
                                return;
                        }

                        // Hiển thị dialog xác nhận xóa
                        String tenSP = jTextField3.getText().trim();
                        int confirm = JOptionPane.showConfirmDialog(this,
                                        "Bạn có chắc chắn muốn xóa sản phẩm:\n" +
                                                        "Mã: " + maSanPhamDangSua + "\n" +
                                                        "Tên: " + tenSP + "\n\n" +
                                                        "Hành động này không thể hoàn tác!",
                                        "Xác nhận xóa sản phẩm",
                                        JOptionPane.YES_NO_OPTION,
                                        JOptionPane.QUESTION_MESSAGE);

                        if (confirm != JOptionPane.YES_OPTION) {
                                return; // User chọn No hoặc Cancel
                        }

                        // Tìm sản phẩm cần xóa
                        ao sanPhamCanXoa = null;
                        for (ao sp : danhSachAo) {
                                if (sp.getMa_ao().equals(maSanPhamDangSua)) {
                                        sanPhamCanXoa = sp;
                                        break;
                                }
                        }

                        if (sanPhamCanXoa == null) {
                                JOptionPane.showMessageDialog(this, "Không tìm thấy sản phẩm cần xóa!", "Lỗi",
                                                JOptionPane.ERROR_MESSAGE);
                                return;
                        }

                        // Xóa trong database
                        aoDAO.deleteAo(sanPhamCanXoa.getId_ao());

                        JOptionPane.showMessageDialog(this, "Xóa sản phẩm thành công!", "Thành công",
                                        JOptionPane.INFORMATION_MESSAGE);

                        // Refresh danh sách và clear form
                        loadData();
                        clearFormSanPham();

                } catch (Exception e) {
                        JOptionPane.showMessageDialog(this, "Lỗi khi xóa sản phẩm: " + e.getMessage(), "Lỗi",
                                        JOptionPane.ERROR_MESSAGE);
                        e.printStackTrace();
                }
        }

        // Method kiểm tra mã sản phẩm tồn tại
        private boolean kiemTraMaSanPhamTonTai(String maSP) {
                try {
                        if (danhSachAo != null) {
                                for (ao sanPham : danhSachAo) {
                                        if (sanPham.getMa_ao().equals(maSP)) {
                                                return true;
                                        }
                                }
                        }
                        return false;
                } catch (Exception e) {
                        System.err.println("Lỗi khi kiểm tra mã sản phẩm: " + e.getMessage());
                        return false;
                }
        }

        // Method kiểm tra mã sản phẩm trùng lặp khi sửa (loại trừ sản phẩm hiện tại)
        private boolean kiemTraMaSanPhamTrungLapKhiSua(String maSP, String maSPHienTai) {
                try {
                        if (danhSachAo != null) {
                                for (ao sanPham : danhSachAo) {
                                        // Bỏ qua sản phẩm hiện tại đang sửa
                                        if (!sanPham.getMa_ao().equals(maSPHienTai)
                                                        && sanPham.getMa_ao().equals(maSP)) {
                                                return true;
                                        }
                                }
                        }
                        return false;
                } catch (Exception e) {
                        System.err.println("Lỗi khi kiểm tra mã sản phẩm trùng lặp: " + e.getMessage());
                        return false;
                }
        }

        // Setup listener để refresh ComboBox khi chuyển tab
        private void setupTabChangeListener() {
                jTabbedPane1.addChangeListener(e -> {
                        int selectedIndex = jTabbedPane1.getSelectedIndex();
                        if (selectedIndex == 1) { // Tab 2: "Thông tin chi tiết"
                                // Refresh ComboBox khi chuyển sang Tab 2
                                refreshComboBoxData();
                        }
                });
        }

        private void setupImageComponents() {
                // Khởi tạo image preview label
                jLabelImagePreview = new javax.swing.JLabel();
                jLabelImagePreview.setPreferredSize(new java.awt.Dimension(150, 150));
                jLabelImagePreview.setBorder(javax.swing.BorderFactory.createLineBorder(
                                com.mycompany.shop.util.ModernTheme.BORDER_COLOR, 1));
                jLabelImagePreview.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
                jLabelImagePreview.setVerticalAlignment(javax.swing.SwingConstants.CENTER);

                // Set default image
                updateImagePreview(null);

                // Khởi tạo choose image button
                jButtonChooseImage = new javax.swing.JButton("Chọn ảnh");
                com.mycompany.shop.util.ModernTheme.styleButton(jButtonChooseImage,
                                com.mycompany.shop.util.ModernTheme.ButtonType.OUTLINE,
                                com.mycompany.shop.util.ModernTheme.ButtonSize.SMALL);

                jButtonChooseImage.addActionListener(e -> chooseImage());

                // Thêm components vào panel (sẽ cần cập nhật layout sau)
                // Tạm thời add vào jPanel5 (form sản phẩm)
                addImageComponentsToForm();
        }

        private void updateImagePreview(String imagePath) {
                System.out.println("=== UPDATE IMAGE PREVIEW ===");
                System.out.println("Input imagePath: " + imagePath);
                System.out.println("Current currentImagePath before: " + currentImagePath);

                if (imagePath == null || imagePath.trim().isEmpty()) {
                        // Hiển thị default image
                        javax.swing.ImageIcon defaultIcon = com.mycompany.shop.util.ImageUtils
                                        .createDefaultProductIcon(150, 150);
                        jLabelImagePreview.setIcon(defaultIcon);
                        jLabelImagePreview.setText("");
                        System.out.println("Set default image");
                } else {
                        // Hiển thị ảnh từ đường dẫn
                        javax.swing.ImageIcon imageIcon = com.mycompany.shop.util.ImageUtils.createImageIcon(imagePath,
                                        150, 150);
                        jLabelImagePreview.setIcon(imageIcon);
                        jLabelImagePreview.setText("");
                        System.out.println("Set custom image: " + imagePath);
                }

                currentImagePath = imagePath;
                System.out.println("Current currentImagePath after: " + currentImagePath);
                System.out.println("=== UPDATE IMAGE PREVIEW COMPLETE ===");
        }

        private void chooseImage() {
                java.io.File selectedFile = com.mycompany.shop.util.ImageUtils.chooseImageFile(this);
                if (selectedFile != null) {
                        // Lưu ảnh vào thư mục products với tên dựa trên mã sản phẩm
                        String maSP = jTextField2.getText().trim();
                        if (maSP.isEmpty()) {
                                javax.swing.JOptionPane.showMessageDialog(this,
                                                "Vui lòng nhập mã sản phẩm trước khi chọn ảnh!",
                                                "Thông báo", javax.swing.JOptionPane.WARNING_MESSAGE);
                                return;
                        }

                        String savedImagePath = com.mycompany.shop.util.ImageUtils.saveProductImage(selectedFile, maSP);
                        if (savedImagePath != null) {
                                updateImagePreview(savedImagePath);
                                javax.swing.JOptionPane.showMessageDialog(this,
                                                "Đã chọn ảnh thành công!",
                                                "Thông báo", javax.swing.JOptionPane.INFORMATION_MESSAGE);
                        } else {
                                javax.swing.JOptionPane.showMessageDialog(this,
                                                "Lỗi khi lưu ảnh!",
                                                "Lỗi", javax.swing.JOptionPane.ERROR_MESSAGE);
                        }
                }
        }

        private void chooseImageForProduct(String maSP) {
                System.out.println("=== CHOOSE IMAGE FOR PRODUCT ===");
                System.out.println("Mã sản phẩm: " + maSP);

                if (maSP == null || maSP.trim().isEmpty()) {
                        javax.swing.JOptionPane.showMessageDialog(this,
                                        "Mã sản phẩm không hợp lệ!",
                                        "Lỗi", javax.swing.JOptionPane.ERROR_MESSAGE);
                        return;
                }

                java.io.File selectedFile = com.mycompany.shop.util.ImageUtils.chooseImageFile(this);
                if (selectedFile != null) {
                        System.out.println("Selected file: " + selectedFile.getAbsolutePath());
                        String savedImagePath = com.mycompany.shop.util.ImageUtils.saveProductImage(selectedFile, maSP);
                        System.out.println("Saved image path: " + savedImagePath);

                        if (savedImagePath != null) {
                                updateImagePreview(savedImagePath);
                                System.out.println("currentImagePath after update: " + currentImagePath);
                                javax.swing.JOptionPane.showMessageDialog(this,
                                                "Đã chọn ảnh thành công cho sản phẩm: " + maSP,
                                                "Thông báo", javax.swing.JOptionPane.INFORMATION_MESSAGE);
                        } else {
                                System.out.println("ERROR: Failed to save image!");
                                javax.swing.JOptionPane.showMessageDialog(this,
                                                "Lỗi khi lưu ảnh!",
                                                "Lỗi", javax.swing.JOptionPane.ERROR_MESSAGE);
                        }
                } else {
                        System.out.println("No file selected");
                }
                System.out.println("=== CHOOSE IMAGE COMPLETE ===");
        }

        private void addImageComponentsToForm() {
                // Tạm thời không thêm vào form tự động
                // Thay vào đó, sẽ tạo một button "Chọn ảnh" trong form
                // và hiển thị dialog khi cần
                System.out.println("Image components initialized. Use chooseImage() method to select images.");
        }

        // Setup listener cho table để load dữ liệu khi click vào dòng
        private void setupTableClickListener() {
                jTable2.addMouseListener(new java.awt.event.MouseAdapter() {
                        @Override
                        public void mouseClicked(java.awt.event.MouseEvent evt) {
                                if (evt.getClickCount() == 1) { // Single click
                                        loadDataFromSelectedRow();
                                }
                        }
                });
        }

        /**
         * Setup search functionality for product search
         */
        private void setupSearchFunctionality() {
                // Add key listener for real-time search
                jTextField8.addKeyListener(new java.awt.event.KeyAdapter() {
                        @Override
                        public void keyReleased(java.awt.event.KeyEvent evt) {
                                timKiemSanPham();
                        }
                });

                // Set placeholder text
                jTextField8.setText("");
                jTextField8.setToolTipText("Nhập tên sản phẩm để tìm kiếm...");
        }

        /**
         * Search products by name
         */
        private void timKiemSanPham() {
                String tuKhoa = jTextField8.getText().trim();

                if (tuKhoa.isEmpty()) {
                        // Nếu từ khóa trống, hiển thị tất cả sản phẩm
                        danhSachAo = aoDAO.getAllAo();
                        hienThiDanhSachAo();
                        return;
                }

                try {
                        // Tìm kiếm sản phẩm theo tên
                        List<ao> ketQuaTimKiem = aoDAO.searchAoByName(tuKhoa);

                        // Cập nhật danh sách hiển thị
                        danhSachAo = ketQuaTimKiem;
                        hienThiDanhSachAo();

                        System.out.println("Tìm kiếm '" + tuKhoa + "': " + ketQuaTimKiem.size() + " kết quả");

                } catch (Exception e) {
                        System.err.println("Lỗi khi tìm kiếm sản phẩm: " + e.getMessage());
                        e.printStackTrace();
                        JOptionPane.showMessageDialog(this,
                                        "Lỗi khi tìm kiếm: " + e.getMessage(),
                                        "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
        }

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
                                });
                        }
                });
        }

        // Load dữ liệu từ dòng được chọn lên form
        private void loadDataFromSelectedRow() {
                int selectedRow = jTable2.getSelectedRow();
                if (selectedRow >= 0) {
                        try {
                                // Lấy dữ liệu từ table (cập nhật index vì đã thêm cột ảnh)
                                String maSP = (String) modelSanPham.getValueAt(selectedRow, 1); // Cột 1: Mã SP
                                String tenSP = (String) modelSanPham.getValueAt(selectedRow, 2); // Cột 2: Tên SP
                                String loaiSP = (String) modelSanPham.getValueAt(selectedRow, 3); // Cột 3: Loại SP
                                String kichThuoc = (String) modelSanPham.getValueAt(selectedRow, 4); // Cột 4: Kích
                                                                                                     // thước
                                String mauSac = (String) modelSanPham.getValueAt(selectedRow, 5); // Cột 5: Màu sắc
                                String donGiaStr = (String) modelSanPham.getValueAt(selectedRow, 6); // Cột 6: Đơn giá
                                Object soLuongObj = modelSanPham.getValueAt(selectedRow, 7); // Cột 7: Số lượng

                                // Load dữ liệu lên form
                                jTextField2.setText(maSP); // Mã sản phẩm
                                jTextField3.setText(tenSP); // Tên sản phẩm
                                jTextField5.setText(String.valueOf(soLuongObj)); // Số lượng

                                // Xử lý đơn giá (loại bỏ format VND)
                                String donGia = donGiaStr.replaceAll("[^0-9]", ""); // Chỉ giữ lại số
                                jTextField6.setText(donGia); // Đơn giá

                                // Set ComboBox selections
                                setComboBoxSelection(jComboBox1, loaiSP); // Loại sản phẩm
                                setComboBoxSelection(jComboBoxMauSac, mauSac); // Màu sắc
                                setComboBoxSelection(jComboBoxSize, kichThuoc); // Kích thước

                                // Load image của sản phẩm
                                ao sanPham = aoDAO.getAoByMa(maSP);
                                if (sanPham != null) {
                                        updateImagePreview(sanPham.getImage());
                                }

                                // Set biến tracking để biết đang sửa sản phẩm nào
                                maSanPhamDangSua = maSP;

                                // Chuyển sang Tab 2 để hiển thị form
                                jTabbedPane1.setSelectedIndex(1);

                                // Hiển thị thông báo
                                System.out.println("Đã load dữ liệu sản phẩm: " + maSP + " lên form để chỉnh sửa");

                                // Hiển thị thông báo cho user
                                JOptionPane.showMessageDialog(this,
                                                "Đã load dữ liệu sản phẩm '" + tenSP + "' lên form.\n" +
                                                                "Bạn có thể chỉnh sửa và click 'Sửa sản phẩm' để lưu thay đổi.",
                                                "Thông tin", JOptionPane.INFORMATION_MESSAGE);

                        } catch (Exception e) {
                                System.err.println("Lỗi khi load dữ liệu từ table: " + e.getMessage());
                                e.printStackTrace();
                        }
                }
        }

        // Helper method để set selection cho ComboBox
        private void setComboBoxSelection(javax.swing.JComboBox<String> comboBox, String value) {
                if (comboBox != null && value != null) {
                        for (int i = 0; i < comboBox.getItemCount(); i++) {
                                if (value.equals(comboBox.getItemAt(i))) {
                                        comboBox.setSelectedIndex(i);
                                        break;
                                }
                        }
                }
        }

        // Method refresh dữ liệu ComboBox
        private void refreshComboBoxData() {
                // Reload dữ liệu từ database
                loadMauSacData();
                loadSizeData();
                loadLoaiAoData();
        }

        // Load dữ liệu màu sắc vào ComboBox
        private void loadMauSacData() {
                try {
                        danhSachMauSac = mauSacDAO.getAllMauSac();
                        if (jComboBoxMauSac != null) {
                                jComboBoxMauSac.removeAllItems();
                                if (danhSachMauSac != null) {
                                        for (mau_sac mauSac : danhSachMauSac) {
                                                jComboBoxMauSac.addItem(mauSac.getTen_mau_sac());
                                        }
                                }
                        }
                } catch (Exception e) {
                        System.err.println("Lỗi khi load dữ liệu màu sắc: " + e.getMessage());
                        e.printStackTrace();
                }
        }

        // Load dữ liệu kích thước vào ComboBox
        private void loadSizeData() {
                try {
                        danhSachSize = sizeDAO.getAllSize();
                        if (jComboBoxSize != null) {
                                jComboBoxSize.removeAllItems();
                                if (danhSachSize != null) {
                                        for (size s : danhSachSize) {
                                                jComboBoxSize.addItem(s.getTen_size());
                                        }
                                }
                        }
                } catch (Exception e) {
                        System.err.println("Lỗi khi load dữ liệu kích thước: " + e.getMessage());
                        e.printStackTrace();
                }
        }

        // Load dữ liệu loại áo vào ComboBox
        private void loadLoaiAoData() {
                try {
                        danhSachLoaiAo = loaiAoDAO.getAllLoaiAo();
                        if (jComboBox1 != null) {
                                jComboBox1.removeAllItems();
                                if (danhSachLoaiAo != null) {
                                        for (loai_ao loaiAo : danhSachLoaiAo) {
                                                jComboBox1.addItem(loaiAo.getTen_loai());
                                        }
                                }
                        }
                } catch (Exception e) {
                        System.err.println("Lỗi khi load dữ liệu loại áo: " + e.getMessage());
                        e.printStackTrace();
                }
        }

        private void loadData() {
                System.out.println("=== LOADING DATA FOR SANPHAM ===");

                loadDanhSachAo();
                System.out.println("Loaded " + (danhSachAo != null ? danhSachAo.size() : 0) + " sản phẩm");

                loadDanhSachLoaiAo();
                System.out.println("Loaded " + (danhSachLoaiAo != null ? danhSachLoaiAo.size() : 0) + " loại áo");

                loadDanhSachMauSac();
                System.out.println("Loaded " + (danhSachMauSac != null ? danhSachMauSac.size() : 0) + " màu sắc");

                loadDanhSachSize();
                System.out.println("Loaded " + (danhSachSize != null ? danhSachSize.size() : 0) + " size");

                hienThiDanhSachAo();
                capNhatComboBoxLoaiSanPham();
                capNhatComboBoxMauSac();
                capNhatComboBoxSize();

                System.out.println("=== LOAD DATA COMPLETE ===");
        }

        private void loadDanhSachAo() {
                try {
                        danhSachAo = aoDAO.getAllAo();
                        if (danhSachAo == null) {
                                danhSachAo = new java.util.ArrayList<>();
                        }
                } catch (Exception e) {
                        System.err.println("Lỗi khi load danh sách áo: " + e.getMessage());
                        danhSachAo = new java.util.ArrayList<>();
                }
        }

        private void loadDanhSachLoaiAo() {
                try {
                        danhSachLoaiAo = loaiAoDAO.getAllLoaiAo();
                        if (danhSachLoaiAo == null) {
                                danhSachLoaiAo = new java.util.ArrayList<>();
                        }
                } catch (Exception e) {
                        System.err.println("Lỗi khi load danh sách loại áo: " + e.getMessage());
                        danhSachLoaiAo = new java.util.ArrayList<>();
                }
        }

        private void loadDanhSachMauSac() {
                try {
                        danhSachMauSac = mauSacDAO.getAllMauSac();
                        if (danhSachMauSac == null) {
                                danhSachMauSac = new java.util.ArrayList<>();
                        }
                } catch (Exception e) {
                        System.err.println("Lỗi khi load danh sách màu sắc: " + e.getMessage());
                        danhSachMauSac = new java.util.ArrayList<>();
                }
        }

        private void loadDanhSachSize() {
                try {
                        danhSachSize = sizeDAO.getAllSize();
                        if (danhSachSize == null) {
                                danhSachSize = new java.util.ArrayList<>();
                        }
                } catch (Exception e) {
                        System.err.println("Lỗi khi load danh sách size: " + e.getMessage());
                        danhSachSize = new java.util.ArrayList<>();
                }
        }

        private void hienThiDanhSachAo() {
                modelSanPham.setRowCount(0);

                if (danhSachAo != null) {
                        for (ao item : danhSachAo) {
                                String tenLoai = getTenLoaiAo(item.getId_loai());
                                String tenMauSac = getTenMauSac(item.getId_mau_sac());
                                String tenSize = getTenSize(item.getId_size());

                                // Tạo ImageIcon cho ảnh sản phẩm
                                javax.swing.ImageIcon imageIcon = com.mycompany.shop.util.ImageUtils.createImageIcon(
                                                item.getImage(), 50, 50);

                                modelSanPham.addRow(new Object[] {
                                                imageIcon,
                                                item.getMa_ao(),
                                                item.getTen_ao(),
                                                tenLoai,
                                                tenSize,
                                                tenMauSac,
                                                CurrencyUtils.formatVND(item.getGia()),
                                                item.getSo_luong()
                                });
                        }
                }

                // Setup custom renderer cho cột ảnh
                setupProductTableImageRenderer();
        }

        private void setupProductTableImageRenderer() {
                // Set custom renderer cho cột ảnh (cột 0)
                jTable2.getColumnModel().getColumn(0).setCellRenderer(new javax.swing.table.DefaultTableCellRenderer() {
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
                jTable2.getColumnModel().getColumn(0).setPreferredWidth(60);
                jTable2.getColumnModel().getColumn(0).setMaxWidth(60);
                jTable2.getColumnModel().getColumn(0).setMinWidth(60);

                // Set chiều cao hàng để hiển thị ảnh tốt hơn
                jTable2.setRowHeight(60);
        }

        private String getTenLoaiAo(int id_loai) {
                for (loai_ao loai : danhSachLoaiAo) {
                        if (loai.getId_loai_ao() == id_loai) {
                                return loai.getTen_loai();
                        }
                }
                return "Không xác định";
        }

        private String getTenMauSac(int id_mau_sac) {
                for (mau_sac mau : danhSachMauSac) {
                        if (mau.getId_mau_sac() == id_mau_sac) {
                                return mau.getTen_mau_sac();
                        }
                }
                return "Không xác định";
        }

        private String getTenSize(int id_size) {
                for (size s : danhSachSize) {
                        if (s.getId_size() == id_size) {
                                return s.getTen_size();
                        }
                }
                return "Không xác định";
        }

        private void capNhatComboBoxLoaiSanPham() {
                DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
                for (loai_ao loai : danhSachLoaiAo) {
                        model.addElement(loai.getTen_loai());
                }
                jComboBox1.setModel(model);
        }

        private void capNhatComboBoxMauSac() {
                DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
                for (mau_sac mau : danhSachMauSac) {
                        model.addElement(mau.getTen_mau_sac());
                }
                jComboBoxMauSac.setModel(model); // Sử dụng ComboBox mới cho màu sắc
        }

        private void capNhatComboBoxSize() {
                DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
                for (size s : danhSachSize) {
                        model.addElement(s.getTen_size());
                }
                jComboBoxSize.setModel(model); // Sử dụng ComboBox thay vì TextField
        }

        private void hienThiDanhSachThuocTinh() {
                System.out.println("=== HIỂN THỊ DANH SÁCH THUỘC TÍNH ===");
                System.out.println("loaiThuocTinhDangChon = " + loaiThuocTinhDangChon);
                System.out.println("LOAI_MAUSAC = " + LOAI_MAUSAC);
                System.out.println("LOAI_KICHTHUOC = " + LOAI_KICHTHUOC);
                System.out.println("LOAI_SANPHAM = " + LOAI_SANPHAM);

                modelThuocTinh.setRowCount(0);
                System.out.println("Cleared table rows");

                if (loaiThuocTinhDangChon == LOAI_MAUSAC) {
                        System.out.println("Hiển thị màu sắc...");
                        // Hiển thị danh sách màu sắc
                        if (danhSachMauSac != null) {
                                System.out.println("danhSachMauSac có " + danhSachMauSac.size() + " items");
                                for (int i = 0; i < danhSachMauSac.size(); i++) {
                                        String tenMauSac = danhSachMauSac.get(i).getTen_mau_sac();
                                        System.out.println("Adding màu sắc: " + tenMauSac);
                                        modelThuocTinh.addRow(new Object[] {
                                                        i + 1,
                                                        "Màu sắc",
                                                        tenMauSac
                                        });
                                }
                                System.out.println("Added " + danhSachMauSac.size() + " màu sắc to table");
                        } else {
                                System.out.println("danhSachMauSac is NULL!");
                        }
                } else if (loaiThuocTinhDangChon == LOAI_KICHTHUOC) {
                        System.out.println("Hiển thị kích thước...");
                        // Hiển thị danh sách kích thước
                        if (danhSachSize != null) {
                                System.out.println("danhSachSize có " + danhSachSize.size() + " items");
                                for (int i = 0; i < danhSachSize.size(); i++) {
                                        String tenSize = danhSachSize.get(i).getTen_size();
                                        System.out.println("Adding size: " + tenSize);
                                        modelThuocTinh.addRow(new Object[] {
                                                        i + 1,
                                                        "Kích thước",
                                                        tenSize
                                        });
                                }
                                System.out.println("Added " + danhSachSize.size() + " size to table");
                        } else {
                                System.out.println("danhSachSize is NULL!");
                        }
                } else if (loaiThuocTinhDangChon == LOAI_SANPHAM) {
                        System.out.println("Hiển thị loại sản phẩm...");
                        // Hiển thị danh sách loại sản phẩm
                        if (danhSachLoaiAo != null) {
                                System.out.println("danhSachLoaiAo có " + danhSachLoaiAo.size() + " items");
                                for (int i = 0; i < danhSachLoaiAo.size(); i++) {
                                        String tenLoai = danhSachLoaiAo.get(i).getTen_loai();
                                        System.out.println("Adding loại: " + tenLoai);
                                        modelThuocTinh.addRow(new Object[] {
                                                        i + 1,
                                                        "Loại sản phẩm",
                                                        tenLoai
                                        });
                                }
                                System.out.println("Added " + danhSachLoaiAo.size() + " loại to table");
                        } else {
                                System.out.println("danhSachLoaiAo is NULL!");
                        }
                } else {
                        System.out.println("Không có loại thuộc tính nào được chọn!");
                }

                System.out.println("Table row count after update: " + modelThuocTinh.getRowCount());
                System.out.println("=== HIỂN THỊ DANH SÁCH THUỘC TÍNH COMPLETE ===");
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

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        jRadioButton4 = new javax.swing.JRadioButton();
        jLabel1 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jTextField6 = new javax.swing.JTextField();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JComboBox<>();
        jTextField7 = new javax.swing.JComboBox<>();
        jButtonThemSanPham = new javax.swing.JButton();
        jButtonSuaSanPham = new javax.swing.JButton();
        jButtonXoaSanPham = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jTextField8 = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jLabel2.setText("Tên thuộc tính");

        jTextField1.setText("jTextField1");

        jButton1.setBackground(new java.awt.Color(102, 255, 102));
        jButton1.setFont(new java.awt.Font("Helvetica Neue", 1, 13)); // NOI18N
        jButton1.setText("Thêm");

        jButton2.setBackground(new java.awt.Color(255, 255, 102));
        jButton2.setFont(new java.awt.Font("Helvetica Neue", 1, 13)); // NOI18N
        jButton2.setText("Sửa");

        jButton3.setBackground(new java.awt.Color(255, 102, 102));
        jButton3.setFont(new java.awt.Font("Helvetica Neue", 1, 13)); // NOI18N
        jButton3.setText("Ẩn");

        jRadioButton1.setText("màu sắc");

        jRadioButton2.setText("kích thước");

        jRadioButton4.setText("tên loại sản phẩm");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(61, 61, 61)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(29, 29, 29)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(32, 32, 32)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jTextField1))
                .addGap(72, 72, 72)
                .addComponent(jRadioButton1)
                .addGap(18, 18, 18)
                .addComponent(jRadioButton2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jRadioButton4)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jRadioButton1)
                    .addComponent(jRadioButton2)
                    .addComponent(jRadioButton4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14))
        );

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("THÔNG TIN THUỘC TÍNH");

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "STT", "Loại thuộc tính", "Tên thuộc tính"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 597, Short.MAX_VALUE)
        );

        jLabel3.setBackground(new java.awt.Color(255, 255, 255));
        jLabel3.setFont(new java.awt.Font("Helvetica Neue", 1, 15)); // NOI18N
        jLabel3.setText("THUỘC TÍNH SẢN PHẨM");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(32, 32, 32)
                                .addComponent(jLabel1))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(36, 36, 36)
                                .addComponent(jLabel3)))
                        .addGap(0, 1349, Short.MAX_VALUE))
                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Thuộc tính sản phẩm", jPanel1);

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));

        jLabel6.setText("Mã sản phẩm");

        jTextField2.setText("jTextField2");

        jLabel7.setText("Tên sản phẩm");

        jTextField3.setText("jTextField2");

        jLabel8.setText("Loại sản phẩm");

        jLabel9.setText("Số Lượng");

        jTextField5.setText("jTextField2");

        jLabel10.setText("Đơn Giá");

        jTextField6.setText("jTextField2");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel11.setText("Màu sắc");

        jLabel12.setText("Kích thước");

        jLabel13.setText("Chất liệu");

        jButtonThemSanPham.setBackground(new java.awt.Color(102, 255, 102));
        jButtonThemSanPham.setFont(new java.awt.Font("Helvetica Neue", 1, 13)); // NOI18N
        jButtonThemSanPham.setText("Thêm sản phẩm");
        jButtonThemSanPham.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonThemSanPhamActionPerformed(evt);
            }
        });

        jButtonSuaSanPham.setBackground(new java.awt.Color(255, 255, 153));
        jButtonSuaSanPham.setFont(new java.awt.Font("Helvetica Neue", 1, 13)); // NOI18N
        jButtonSuaSanPham.setText("Sửa sản phẩm");
        jButtonSuaSanPham.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSuaSanPhamActionPerformed(evt);
            }
        });

        jButtonXoaSanPham.setBackground(new java.awt.Color(255, 102, 102));
        jButtonXoaSanPham.setFont(new java.awt.Font("Helvetica Neue", 1, 13)); // NOI18N
        jButtonXoaSanPham.setText("Xóa sản phẩm");
        jButtonXoaSanPham.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonXoaSanPhamActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jTextField5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel5Layout.createSequentialGroup()
                            .addGap(24, 24, 24)
                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(jPanel5Layout.createSequentialGroup()
                                    .addComponent(jLabel8)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel5Layout.createSequentialGroup()
                                    .addComponent(jLabel7)
                                    .addGap(18, 18, 18)
                                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel5Layout.createSequentialGroup()
                                    .addComponent(jLabel6)
                                    .addGap(18, 18, 18)
                                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel10)
                                .addGap(18, 18, 18)
                                .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(144, 144, 144)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel13)
                                    .addComponent(jLabel11))
                                .addGap(15, 15, 15))
                            .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextField4, javax.swing.GroupLayout.Alignment.TRAILING, 0, 316, Short.MAX_VALUE)
                            .addComponent(jTextField7, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jButtonThemSanPham)
                        .addGap(28, 28, 28)
                        .addComponent(jButtonSuaSanPham)
                        .addGap(18, 18, 18)
                        .addComponent(jButtonXoaSanPham)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel12)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel13))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jButtonSuaSanPham, javax.swing.GroupLayout.DEFAULT_SIZE, 52, Short.MAX_VALUE)
                            .addComponent(jButtonThemSanPham, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButtonXoaSanPham, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(38, Short.MAX_VALUE))
        );

        jLabel4.setFont(new java.awt.Font("Helvetica Neue", 1, 15)); // NOI18N
        jLabel4.setText("Quản lý sản phẩm");

        jLabel5.setFont(new java.awt.Font("Helvetica Neue", 1, 15)); // NOI18N
        jLabel5.setText("Thông tin sản phẩm");

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));

        jLabel14.setText("Tìm kiếm");

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Ảnh", "Mã sản phẩm", "Tên sản phẩm", "Loại sản phẩm", "Kích thước", "Màu sắc", "Đơn giá", "Số lượng"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, true, true, true, true, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(jTable2);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12))
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 403, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel4))
                        .addGap(0, 1417, Short.MAX_VALUE))
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Thông tin chi tiết", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, 800, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, 600, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

        private void jButtonThemSanPhamActionPerformed(java.awt.event.ActionEvent evt) {
                themSanPham();
        }

        private void jButtonSuaSanPhamActionPerformed(java.awt.event.ActionEvent evt) {
                suaSanPham();
        }

        private void jButtonXoaSanPhamActionPerformed(java.awt.event.ActionEvent evt) {
                xoaSanPham();
        }

        private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton1ActionPerformed
                // Thêm thuộc tính mới
                System.out.println("=== BUTTON 1 CLICKED - THÊM THUỘC TÍNH ===");
                String tenThuocTinh = jTextField1.getText().trim();
                System.out.println("Tên thuộc tính: " + tenThuocTinh);

                if (tenThuocTinh.isEmpty()) {
                        JOptionPane.showMessageDialog(this, "Vui lòng nhập tên thuộc tính!", "Lỗi",
                                        JOptionPane.ERROR_MESSAGE);
                        return;
                }

                if (!jRadioButton1.isSelected() && !jRadioButton2.isSelected() && !jRadioButton4.isSelected()) {
                        JOptionPane.showMessageDialog(this, "Vui lòng chọn loại thuộc tính!", "Lỗi",
                                        JOptionPane.ERROR_MESSAGE);
                        return;
                }

                // Thêm thuộc tính vào CSDL và cập nhật danh sách
                try {
                        if (jRadioButton1.isSelected()) { // Màu sắc
                                // Tạo mã màu sắc theo thời gian hiện tại
                                String maMauSac = "MS" + System.currentTimeMillis();
                                mauSacDAO.addMauSac(maMauSac, tenThuocTinh);
                                loadDanhSachMauSac();
                                loaiThuocTinhDangChon = LOAI_MAUSAC;
                        } else if (jRadioButton2.isSelected()) { // Kích thước
                                // Tạo mã kích thước theo thời gian hiện tại
                                String maSize = "SZ" + System.currentTimeMillis();
                                sizeDAO.addSize(maSize, tenThuocTinh);
                                loadDanhSachSize();
                                loaiThuocTinhDangChon = LOAI_KICHTHUOC;
                        } else if (jRadioButton4.isSelected()) { // Loại sản phẩm
                                // Tạo mã loại áo theo thời gian hiện tại
                                String maLoai = "LA" + System.currentTimeMillis();
                                loaiAoDAO.addLoaiAo(maLoai, tenThuocTinh);
                                loadDanhSachLoaiAo();
                                loaiThuocTinhDangChon = LOAI_SANPHAM;
                        }

                        // Cập nhật hiển thị
                        hienThiDanhSachThuocTinh();
                        jTextField1.setText("");
                        JOptionPane.showMessageDialog(this, "Đã thêm thuộc tính thành công!", "Thông báo",
                                        JOptionPane.INFORMATION_MESSAGE);

                } catch (Exception ex) {
                        JOptionPane.showMessageDialog(this, "Lỗi khi thêm thuộc tính: " + ex.getMessage(), "Lỗi",
                                        JOptionPane.ERROR_MESSAGE);
                }
        }// GEN-LAST:event_jButton1ActionPerformed

        private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton2ActionPerformed
                // Sửa thuộc tính
                System.out.println("=== BUTTON 2 CLICKED - SỬA THUỘC TÍNH ===");
                int selectedRow = jTable1.getSelectedRow();
                System.out.println("Selected row: " + selectedRow);

                if (selectedRow < 0) {
                        JOptionPane.showMessageDialog(this, "Vui lòng chọn thuộc tính cần sửa!", "Cảnh báo",
                                        JOptionPane.WARNING_MESSAGE);
                        return;
                }

                String tenThuocTinhMoi = jTextField1.getText().trim();

                if (tenThuocTinhMoi.isEmpty()) {
                        JOptionPane.showMessageDialog(this, "Vui lòng nhập tên thuộc tính mới!", "Lỗi",
                                        JOptionPane.ERROR_MESSAGE);
                        return;
                }

                String loaiThuocTinh = (String) jTable1.getValueAt(selectedRow, 1);
                String tenThuocTinhCu = (String) jTable1.getValueAt(selectedRow, 2);

                try {
                        // Cập nhật thuộc tính trong CSDL
                        if (loaiThuocTinh.equals("Màu sắc")) {
                                mau_sac mauSac = null;
                                for (mau_sac mau : danhSachMauSac) {
                                        if (mau.getTen_mau_sac().equals(tenThuocTinhCu)) {
                                                mauSac = mau;
                                                break;
                                        }
                                }
                                if (mauSac != null) {
                                        mauSacDAO.updateMauSac(mauSac.getId_mau_sac(), mauSac.getMa_mau_sac(),
                                                        tenThuocTinhMoi);
                                        loadDanhSachMauSac();
                                        loaiThuocTinhDangChon = LOAI_MAUSAC;
                                }
                        } else if (loaiThuocTinh.equals("Kích thước")) {
                                size kichThuoc = null;
                                for (size s : danhSachSize) {
                                        if (s.getTen_size().equals(tenThuocTinhCu)) {
                                                kichThuoc = s;
                                                break;
                                        }
                                }
                                if (kichThuoc != null) {
                                        sizeDAO.updateSize(kichThuoc.getId_size(), kichThuoc.getMa_size(),
                                                        tenThuocTinhMoi);
                                        loadDanhSachSize();
                                        loaiThuocTinhDangChon = LOAI_KICHTHUOC;
                                }
                        } else if (loaiThuocTinh.equals("Loại sản phẩm")) {
                                loai_ao loaiAo = null;
                                for (loai_ao loai : danhSachLoaiAo) {
                                        if (loai.getTen_loai().equals(tenThuocTinhCu)) {
                                                loaiAo = loai;
                                                break;
                                        }
                                }
                                if (loaiAo != null) {
                                        loaiAoDAO.updateLoaiAo(loaiAo.getId_loai_ao(), loaiAo.getMa_loai(),
                                                        tenThuocTinhMoi);
                                        loadDanhSachLoaiAo();
                                        loaiThuocTinhDangChon = LOAI_SANPHAM;
                                }
                        }

                        // Cập nhật hiển thị
                        hienThiDanhSachThuocTinh();
                        hienThiDanhSachAo();
                        jTextField1.setText("");
                        JOptionPane.showMessageDialog(this, "Đã cập nhật thuộc tính thành công!", "Thông báo",
                                        JOptionPane.INFORMATION_MESSAGE);

                } catch (Exception ex) {
                        JOptionPane.showMessageDialog(this, "Lỗi khi cập nhật thuộc tính: " + ex.getMessage(), "Lỗi",
                                        JOptionPane.ERROR_MESSAGE);
                }
        }// GEN-LAST:event_jButton2ActionPerformed

        private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton3ActionPerformed
                // Xóa thuộc tính
                System.out.println("=== BUTTON 3 CLICKED - XÓA THUỘC TÍNH ===");
                int selectedRow = jTable1.getSelectedRow();
                System.out.println("Selected row: " + selectedRow);

                if (selectedRow < 0) {
                        JOptionPane.showMessageDialog(this, "Vui lòng chọn thuộc tính cần xóa!", "Cảnh báo",
                                        JOptionPane.WARNING_MESSAGE);
                        return;
                }

                String loaiThuocTinh = (String) jTable1.getValueAt(selectedRow, 1);
                String tenThuocTinh = (String) jTable1.getValueAt(selectedRow, 2);

                int confirm = JOptionPane.showConfirmDialog(this,
                                "Bạn có chắc chắn muốn xóa thuộc tính này?",
                                "Xác nhận xóa",
                                JOptionPane.YES_NO_OPTION);

                if (confirm != JOptionPane.YES_OPTION) {
                        return;
                }

                try {
                        // Xóa thuộc tính từ CSDL
                        if (loaiThuocTinh.equals("Màu sắc")) {
                                for (mau_sac mau : danhSachMauSac) {
                                        if (mau.getTen_mau_sac().equals(tenThuocTinh)) {
                                                // Kiểm tra xem có sản phẩm nào đang sử dụng màu sắc này không
                                                List<ao> sanPhamSuDung = aoDAO.getAoByMauSac(mau.getId_mau_sac());
                                                if (!sanPhamSuDung.isEmpty()) {
                                                        JOptionPane.showMessageDialog(this,
                                                                        "Không thể xóa màu sắc này vì có "
                                                                                        + sanPhamSuDung.size() +
                                                                                        " sản phẩm đang sử dụng!\nVui lòng xóa hoặc chuyển màu sắc của các sản phẩm này trước.",
                                                                        "Không thể xóa", JOptionPane.WARNING_MESSAGE);
                                                        return;
                                                }

                                                mauSacDAO.deleteMauSac(mau.getId_mau_sac());
                                                break;
                                        }
                                }
                                loadDanhSachMauSac();
                        } else if (loaiThuocTinh.equals("Kích thước")) {
                                for (size s : danhSachSize) {
                                        if (s.getTen_size().equals(tenThuocTinh)) {
                                                // Kiểm tra xem có sản phẩm nào đang sử dụng size này không
                                                List<ao> sanPhamSuDung = aoDAO.getAoBySize(s.getId_size());
                                                if (!sanPhamSuDung.isEmpty()) {
                                                        JOptionPane.showMessageDialog(this,
                                                                        "Không thể xóa kích thước này vì có "
                                                                                        + sanPhamSuDung.size() +
                                                                                        " sản phẩm đang sử dụng!\nVui lòng xóa hoặc chuyển kích thước của các sản phẩm này trước.",
                                                                        "Không thể xóa", JOptionPane.WARNING_MESSAGE);
                                                        return;
                                                }

                                                sizeDAO.deleteSize(s.getId_size());
                                                break;
                                        }
                                }
                                loadDanhSachSize();
                        } else if (loaiThuocTinh.equals("Loại sản phẩm")) {
                                for (loai_ao loai : danhSachLoaiAo) {
                                        if (loai.getTen_loai().equals(tenThuocTinh)) {
                                                // Kiểm tra xem có sản phẩm nào đang sử dụng loại này không
                                                List<ao> sanPhamSuDung = aoDAO.getAoByLoai(loai.getId_loai_ao());
                                                if (!sanPhamSuDung.isEmpty()) {
                                                        JOptionPane.showMessageDialog(this,
                                                                        "Không thể xóa loại sản phẩm này vì có "
                                                                                        + sanPhamSuDung.size() +
                                                                                        " sản phẩm đang sử dụng!\nVui lòng xóa hoặc chuyển loại của các sản phẩm này trước.",
                                                                        "Không thể xóa", JOptionPane.WARNING_MESSAGE);
                                                        return;
                                                }

                                                loaiAoDAO.deleteLoaiAo(loai.getId_loai_ao());
                                                break;
                                        }
                                }
                                loadDanhSachLoaiAo();
                        }

                        // Cập nhật hiển thị
                        hienThiDanhSachThuocTinh();
                        jTextField1.setText("");
                        JOptionPane.showMessageDialog(this, "Đã xóa thuộc tính thành công!", "Thông báo",
                                        JOptionPane.INFORMATION_MESSAGE);

                } catch (Exception ex) {
                        JOptionPane.showMessageDialog(this, "Lỗi khi xóa thuộc tính: " + ex.getMessage(), "Lỗi",
                                        JOptionPane.ERROR_MESSAGE);
                }
        }// GEN-LAST:event_jButton3ActionPerformed

        private void jRadioButton1ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jRadioButton1ActionPerformed
                // Radio button Màu sắc
                System.out.println("=== RADIO BUTTON 1 CLICKED - MÀU SẮC ===");
                loaiThuocTinhDangChon = LOAI_MAUSAC;
                System.out.println("loaiThuocTinhDangChon = " + loaiThuocTinhDangChon);
                System.out.println(
                                "danhSachMauSac size = " + (danhSachMauSac != null ? danhSachMauSac.size() : "null"));
                hienThiDanhSachThuocTinh();
                System.out.println("=== RADIO BUTTON 1 COMPLETE ===");
        }// GEN-LAST:event_jRadioButton1ActionPerformed

        private void jRadioButton2ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jRadioButton2ActionPerformed
                // Radio button Kích thước
                System.out.println("=== RADIO BUTTON 2 CLICKED - KÍCH THƯỚC ===");
                loaiThuocTinhDangChon = LOAI_KICHTHUOC;
                System.out.println("loaiThuocTinhDangChon = " + loaiThuocTinhDangChon);
                System.out.println("danhSachSize size = " + (danhSachSize != null ? danhSachSize.size() : "null"));
                hienThiDanhSachThuocTinh();
                System.out.println("=== RADIO BUTTON 2 COMPLETE ===");
        }// GEN-LAST:event_jRadioButton2ActionPerformed

        private void jRadioButton3ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jRadioButton3ActionPerformed
                // Radio button Chất liệu - Không sử dụng vì không có trong database
                System.out.println("=== RADIO BUTTON 3 CLICKED - CHẤT LIỆU (DISABLED) ===");
                // Không làm gì cả
        }// GEN-LAST:event_jRadioButton3ActionPerformed

        private void jRadioButton4ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jRadioButton4ActionPerformed
                // Radio button Loại sản phẩm
                System.out.println("=== RADIO BUTTON 4 CLICKED - LOẠI SẢN PHẨM ===");
                loaiThuocTinhDangChon = LOAI_SANPHAM;
                System.out.println("loaiThuocTinhDangChon = " + loaiThuocTinhDangChon);
                System.out.println(
                                "danhSachLoaiAo size = " + (danhSachLoaiAo != null ? danhSachLoaiAo.size() : "null"));
                hienThiDanhSachThuocTinh();
                System.out.println("=== RADIO BUTTON 4 COMPLETE ===");
        }// GEN-LAST:event_jRadioButton4ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButtonSuaSanPham;
    private javax.swing.JButton jButtonThemSanPham;
    private javax.swing.JButton jButtonXoaSanPham;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
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
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JRadioButton jRadioButton4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JComboBox<String> jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JComboBox<String> jTextField7;
    private javax.swing.JTextField jTextField8;
    // End of variables declaration//GEN-END:variables

        /**
         * Cleanup resources when panel is destroyed
         */
        public void cleanup() {
                TableResponsiveManager.cleanup(jTable1);
                TableResponsiveManager.cleanup(jTable2);
        }
}
