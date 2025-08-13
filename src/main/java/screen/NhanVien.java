/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */

package screen;

import com.mycompany.shop.dao.nhan_vien_dao;
import com.mycompany.shop.dao.quan_ly_dao;
import com.mycompany.shop.model.nhan_vien;
import com.mycompany.shop.model.quan_ly;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import com.mycompany.shop.util.TableResponsiveManager;
import com.mycompany.shop.util.ResponsiveLayoutManager;
import com.mycompany.shop.util.ComponentOptimizer;

/**
 *
 * @author nguyenthanhquoc
 */
public class NhanVien extends javax.swing.JPanel {

        // DAO instances
        private nhan_vien_dao nhanVienDAO;
        private quan_ly_dao quanLyDAO;

        // Table models
        private DefaultTableModel modelNhanVien;
        private DefaultTableModel modelQuanLy;

        // Data lists
        private List<nhan_vien> danhSachNhanVien;
        private List<quan_ly> danhSachQuanLy;

        // Tracking variables
        private String maNhanVienDangSua = null;
        private String maQuanLyDangSua = null;

        /** Creates new form NhanVien */
        public NhanVien() {
                initComponents();
                com.mycompany.shop.util.FormLayoutOptimizer.makeFormResponsive(this);
                initializeComponents();
                loadData();
                setupEventHandlers();
        }

        private void initializeComponents() {
                // Initialize DAOs
                nhanVienDAO = new nhan_vien_dao();
                quanLyDAO = new quan_ly_dao();

                // Initialize table models
                modelNhanVien = (DefaultTableModel) jTable2.getModel();
                modelQuanLy = (DefaultTableModel) jTable3.getModel();

                // Apply specialized table responsive management
                TableResponsiveManager.makeTableResponsive(jTable2);
                TableResponsiveManager.makeTableResponsive(jTable3);

                // Make layout responsive
                ResponsiveLayoutManager.makeResponsive(this);

                // Optimize components (buttons, text fields, etc.)
                ComponentOptimizer.optimizeComponents(this);

                // Setup window resize listener for table responsiveness
                setupWindowResizeListener();

                // Clear default text in input fields
                clearNhanVienForm();
                clearQuanLyForm();
        }

        private void loadData() {
                loadNhanVienData();
                loadQuanLyData();
        }

        private void loadNhanVienData() {
                try {
                        danhSachNhanVien = nhanVienDAO.getAllNhanVien();
                        modelNhanVien.setRowCount(0);

                        for (nhan_vien nv : danhSachNhanVien) {
                                // Get manager name
                                quan_ly ql = quanLyDAO.getQuanLyById(nv.getId_quan_ly());
                                String tenQuanLy = (ql != null) ? ql.getTen_quan_ly() : "N/A";

                                modelNhanVien.addRow(new Object[] {
                                                nv.getMa_nhan_vien(),
                                                nv.getTen_nhan_vien(),
                                                nv.getTai_khoan(),
                                                nv.getMat_khau(),
                                                nv.getSo_dien_thoai(),
                                                tenQuanLy
                                });
                        }
                } catch (Exception e) {
                        JOptionPane.showMessageDialog(this, "Lỗi khi tải dữ liệu nhân viên: " + e.getMessage(),
                                        "Lỗi", JOptionPane.ERROR_MESSAGE);
                        e.printStackTrace();
                }
        }

        private void loadQuanLyData() {
                try {
                        danhSachQuanLy = quanLyDAO.getAllQuanLy();
                        modelQuanLy.setRowCount(0);

                        for (quan_ly ql : danhSachQuanLy) {
                                modelQuanLy.addRow(new Object[] {
                                                ql.getMa_quan_ly(),
                                                ql.getTen_quan_ly(),
                                                ql.getTai_khoan(),
                                                ql.getMat_khau()
                                });
                        }
                } catch (Exception e) {
                        JOptionPane.showMessageDialog(this, "Lỗi khi tải dữ liệu quản lý: " + e.getMessage(),
                                        "Lỗi", JOptionPane.ERROR_MESSAGE);
                        e.printStackTrace();
                }
        }

        private void setupEventHandlers() {
                // Setup table click listeners
                setupNhanVienTableClickListener();
                setupQuanLyTableClickListener();

                // Setup search functionality
                setupSearchHandlers();
        }

        private void setupNhanVienTableClickListener() {
                jTable2.addMouseListener(new java.awt.event.MouseAdapter() {
                        @Override
                        public void mouseClicked(java.awt.event.MouseEvent evt) {
                                if (evt.getClickCount() == 1) {
                                        loadNhanVienFromSelectedRow();
                                }
                        }
                });
        }

        private void setupQuanLyTableClickListener() {
                jTable3.addMouseListener(new java.awt.event.MouseAdapter() {
                        @Override
                        public void mouseClicked(java.awt.event.MouseEvent evt) {
                                if (evt.getClickCount() == 1) {
                                        loadQuanLyFromSelectedRow();
                                }
                        }
                });
        }

        private void setupSearchHandlers() {
                // Search for employees
                jTextField8.addKeyListener(new java.awt.event.KeyAdapter() {
                        @Override
                        public void keyReleased(java.awt.event.KeyEvent evt) {
                                searchNhanVien(jTextField8.getText().trim());
                        }
                });

                // Search for managers
                jTextField11.addKeyListener(new java.awt.event.KeyAdapter() {
                        @Override
                        public void keyReleased(java.awt.event.KeyEvent evt) {
                                searchQuanLy(jTextField11.getText().trim());
                        }
                });
        }

        private void clearNhanVienForm() {
                input_ma_nhan_vien.setText("");
                input_Ten_nhan_vien.setText("");
                input_tai_khoan.setText("");
                input_mat_khau.setText("");
                input_so_dien_thoai.setText("");
                maNhanVienDangSua = null;
        }

        private void clearQuanLyForm() {
                input_ma_quan_ly.setText("");
                input_ten_quan_ly.setText("");
                input_tai_khoan_quan_ly.setText("");
                input_mat_khau__quan_ly.setText("");
                input_so_dien_thoai_quan_ly.setText("");
                maQuanLyDangSua = null;
        }

        private void loadNhanVienFromSelectedRow() {
                int selectedRow = jTable2.getSelectedRow();
                if (selectedRow >= 0) {
                        try {
                                String maNV = (String) modelNhanVien.getValueAt(selectedRow, 0);
                                String tenNV = (String) modelNhanVien.getValueAt(selectedRow, 1);
                                String taiKhoan = (String) modelNhanVien.getValueAt(selectedRow, 2);
                                String matKhau = (String) modelNhanVien.getValueAt(selectedRow, 3);
                                String soDienThoai = (String) modelNhanVien.getValueAt(selectedRow, 4);

                                input_ma_nhan_vien.setText(maNV);
                                input_Ten_nhan_vien.setText(tenNV);
                                input_tai_khoan.setText(taiKhoan);
                                input_mat_khau.setText(matKhau);
                                input_so_dien_thoai.setText(soDienThoai);

                                maNhanVienDangSua = maNV;

                                JOptionPane.showMessageDialog(this,
                                                "Đã load dữ liệu nhân viên '" + tenNV + "' lên form.",
                                                "Thông tin", JOptionPane.INFORMATION_MESSAGE);

                        } catch (Exception e) {
                                JOptionPane.showMessageDialog(this, "Lỗi khi load dữ liệu: " + e.getMessage(),
                                                "Lỗi", JOptionPane.ERROR_MESSAGE);
                        }
                }
        }

        private void loadQuanLyFromSelectedRow() {
                int selectedRow = jTable3.getSelectedRow();
                if (selectedRow >= 0) {
                        try {
                                String maQL = (String) modelQuanLy.getValueAt(selectedRow, 0);
                                String tenQL = (String) modelQuanLy.getValueAt(selectedRow, 1);
                                String taiKhoan = (String) modelQuanLy.getValueAt(selectedRow, 2);
                                String matKhau = (String) modelQuanLy.getValueAt(selectedRow, 3);

                                input_ma_quan_ly.setText(maQL);
                                input_ten_quan_ly.setText(tenQL);
                                input_tai_khoan_quan_ly.setText(taiKhoan);
                                input_mat_khau__quan_ly.setText(matKhau);
                                input_so_dien_thoai_quan_ly.setText(""); // Quản lý không có SĐT trong DB

                                maQuanLyDangSua = maQL;

                                JOptionPane.showMessageDialog(this,
                                                "Đã load dữ liệu quản lý '" + tenQL + "' lên form.",
                                                "Thông tin", JOptionPane.INFORMATION_MESSAGE);

                        } catch (Exception e) {
                                JOptionPane.showMessageDialog(this, "Lỗi khi load dữ liệu: " + e.getMessage(),
                                                "Lỗi", JOptionPane.ERROR_MESSAGE);
                        }
                }
        }

        private void searchNhanVien(String keyword) {
                try {
                        if (keyword.isEmpty()) {
                                loadNhanVienData();
                                return;
                        }

                        List<nhan_vien> searchResults = nhanVienDAO.searchNhanVienByName(keyword);
                        modelNhanVien.setRowCount(0);

                        for (nhan_vien nv : searchResults) {
                                quan_ly ql = quanLyDAO.getQuanLyById(nv.getId_quan_ly());
                                String tenQuanLy = (ql != null) ? ql.getTen_quan_ly() : "N/A";

                                modelNhanVien.addRow(new Object[] {
                                                nv.getMa_nhan_vien(),
                                                nv.getTen_nhan_vien(),
                                                nv.getTai_khoan(),
                                                nv.getMat_khau(),
                                                nv.getSo_dien_thoai(),
                                                tenQuanLy
                                });
                        }
                } catch (Exception e) {
                        e.printStackTrace();
                }
        }

        private void searchQuanLy(String keyword) {
                try {
                        if (keyword.isEmpty()) {
                                loadQuanLyData();
                                return;
                        }

                        List<quan_ly> searchResults = quanLyDAO.searchQuanLyByName(keyword);
                        modelQuanLy.setRowCount(0);

                        for (quan_ly ql : searchResults) {
                                modelQuanLy.addRow(new Object[] {
                                                ql.getMa_quan_ly(),
                                                ql.getTen_quan_ly(),
                                                ql.getTai_khoan(),
                                                ql.getMat_khau()
                                });
                        }
                } catch (Exception e) {
                        e.printStackTrace();
                }
        }

        // ==================== NHÂN VIÊN METHODS ====================

        private void themNhanVien() {
                try {
                        // Lấy dữ liệu từ form
                        String maNV = input_ma_nhan_vien.getText().trim();
                        String tenNV = input_Ten_nhan_vien.getText().trim();
                        String taiKhoan = input_tai_khoan.getText().trim();
                        String matKhau = input_mat_khau.getText().trim();
                        String soDienThoai = input_so_dien_thoai.getText().trim();

                        // Validation
                        if (maNV.isEmpty() || tenNV.isEmpty() || taiKhoan.isEmpty() ||
                                        matKhau.isEmpty() || soDienThoai.isEmpty()) {
                                JOptionPane.showMessageDialog(this, "Vui lòng điền đầy đủ thông tin!",
                                                "Lỗi", JOptionPane.ERROR_MESSAGE);
                                return;
                        }

                        // Kiểm tra mã nhân viên đã tồn tại
                        if (nhanVienDAO.checkMaNhanVienExists(maNV)) {
                                JOptionPane.showMessageDialog(this, "Mã nhân viên đã tồn tại!",
                                                "Lỗi", JOptionPane.ERROR_MESSAGE);
                                return;
                        }

                        // Kiểm tra tài khoản đã tồn tại
                        if (nhanVienDAO.checkTaiKhoanExists(taiKhoan)) {
                                JOptionPane.showMessageDialog(this, "Tài khoản đã tồn tại!",
                                                "Lỗi", JOptionPane.ERROR_MESSAGE);
                                return;
                        }

                        // Kiểm tra số điện thoại đã tồn tại
                        if (nhanVienDAO.checkSoDienThoaiExists(soDienThoai)) {
                                JOptionPane.showMessageDialog(this, "Số điện thoại đã được sử dụng!",
                                                "Lỗi", JOptionPane.ERROR_MESSAGE);
                                return;
                        }

                        // Lấy quản lý đầu tiên (hoặc có thể thêm ComboBox để chọn)
                        if (danhSachQuanLy.isEmpty()) {
                                JOptionPane.showMessageDialog(this, "Cần có ít nhất một quản lý trong hệ thống!",
                                                "Lỗi", JOptionPane.ERROR_MESSAGE);
                                return;
                        }

                        int idQuanLy = danhSachQuanLy.get(0).getId_quan_ly(); // Tạm thời lấy quản lý đầu tiên

                        // Thêm vào database
                        nhanVienDAO.addNhanVien(maNV, tenNV, taiKhoan, matKhau, soDienThoai, idQuanLy);

                        JOptionPane.showMessageDialog(this, "Thêm nhân viên thành công!",
                                        "Thành công", JOptionPane.INFORMATION_MESSAGE);

                        // Refresh data và clear form
                        loadNhanVienData();
                        clearNhanVienForm();

                } catch (Exception e) {
                        JOptionPane.showMessageDialog(this, "Lỗi khi thêm nhân viên: " + e.getMessage(),
                                        "Lỗi", JOptionPane.ERROR_MESSAGE);
                        e.printStackTrace();
                }
        }

        private void suaNhanVien() {
                try {
                        // Kiểm tra có nhân viên được chọn
                        if (maNhanVienDangSua == null || maNhanVienDangSua.isEmpty()) {
                                JOptionPane.showMessageDialog(this, "Vui lòng chọn nhân viên từ danh sách để sửa!",
                                                "Thông báo", JOptionPane.WARNING_MESSAGE);
                                return;
                        }

                        // Lấy dữ liệu từ form
                        String maNV = input_ma_nhan_vien.getText().trim();
                        String tenNV = input_Ten_nhan_vien.getText().trim();
                        String taiKhoan = input_tai_khoan.getText().trim();
                        String matKhau = input_mat_khau.getText().trim();
                        String soDienThoai = input_so_dien_thoai.getText().trim();

                        // Validation
                        if (maNV.isEmpty() || tenNV.isEmpty() || taiKhoan.isEmpty() ||
                                        matKhau.isEmpty() || soDienThoai.isEmpty()) {
                                JOptionPane.showMessageDialog(this, "Vui lòng điền đầy đủ thông tin!",
                                                "Lỗi", JOptionPane.ERROR_MESSAGE);
                                return;
                        }

                        // Tìm nhân viên cần sửa
                        nhan_vien nvCanSua = null;
                        for (nhan_vien nv : danhSachNhanVien) {
                                if (nv.getMa_nhan_vien().equals(maNhanVienDangSua)) {
                                        nvCanSua = nv;
                                        break;
                                }
                        }

                        if (nvCanSua == null) {
                                JOptionPane.showMessageDialog(this, "Không tìm thấy nhân viên cần sửa!",
                                                "Lỗi", JOptionPane.ERROR_MESSAGE);
                                return;
                        }

                        // Kiểm tra mã nhân viên trùng lặp (nếu thay đổi mã)
                        if (!maNV.equals(maNhanVienDangSua) && nhanVienDAO.checkMaNhanVienExists(maNV)) {
                                JOptionPane.showMessageDialog(this, "Mã nhân viên đã tồn tại!",
                                                "Lỗi", JOptionPane.ERROR_MESSAGE);
                                return;
                        }

                        // Kiểm tra số điện thoại trùng lặp (nếu thay đổi SDT)
                        if (!soDienThoai.equals(nvCanSua.getSo_dien_thoai())
                                        && nhanVienDAO.checkSoDienThoaiExists(soDienThoai)) {
                                JOptionPane.showMessageDialog(this, "Số điện thoại đã được sử dụng!",
                                                "Lỗi", JOptionPane.ERROR_MESSAGE);
                                return;
                        }

                        // Cập nhật trong database
                        nhanVienDAO.updateNhanVien(nvCanSua.getId_nhan_vien(), maNV, tenNV,
                                        taiKhoan, matKhau, soDienThoai, nvCanSua.getId_quan_ly());

                        JOptionPane.showMessageDialog(this, "Sửa nhân viên thành công!",
                                        "Thành công", JOptionPane.INFORMATION_MESSAGE);

                        // Refresh data và clear form
                        loadNhanVienData();
                        clearNhanVienForm();

                } catch (Exception e) {
                        JOptionPane.showMessageDialog(this, "Lỗi khi sửa nhân viên: " + e.getMessage(),
                                        "Lỗi", JOptionPane.ERROR_MESSAGE);
                        e.printStackTrace();
                }
        }

        private void xoaNhanVien() {
                try {
                        // Kiểm tra có nhân viên được chọn
                        if (maNhanVienDangSua == null || maNhanVienDangSua.isEmpty()) {
                                JOptionPane.showMessageDialog(this, "Vui lòng chọn nhân viên từ danh sách để xóa!",
                                                "Thông báo", JOptionPane.WARNING_MESSAGE);
                                return;
                        }

                        // Hiển thị dialog xác nhận
                        String tenNV = input_Ten_nhan_vien.getText().trim();
                        int confirm = JOptionPane.showConfirmDialog(this,
                                        "Bạn có chắc chắn muốn xóa nhân viên:\n" +
                                                        "Mã: " + maNhanVienDangSua + "\n" +
                                                        "Tên: " + tenNV + "\n\n" +
                                                        "Hành động này không thể hoàn tác!",
                                        "Xác nhận xóa nhân viên",
                                        JOptionPane.YES_NO_OPTION,
                                        JOptionPane.QUESTION_MESSAGE);

                        if (confirm != JOptionPane.YES_OPTION) {
                                return;
                        }

                        // Tìm nhân viên cần xóa
                        nhan_vien nvCanXoa = null;
                        for (nhan_vien nv : danhSachNhanVien) {
                                if (nv.getMa_nhan_vien().equals(maNhanVienDangSua)) {
                                        nvCanXoa = nv;
                                        break;
                                }
                        }

                        if (nvCanXoa == null) {
                                JOptionPane.showMessageDialog(this, "Không tìm thấy nhân viên cần xóa!",
                                                "Lỗi", JOptionPane.ERROR_MESSAGE);
                                return;
                        }

                        // Xóa trong database
                        nhanVienDAO.deleteNhanVien(nvCanXoa.getId_nhan_vien());

                        JOptionPane.showMessageDialog(this, "Xóa nhân viên thành công!",
                                        "Thành công", JOptionPane.INFORMATION_MESSAGE);

                        // Refresh data và clear form
                        loadNhanVienData();
                        clearNhanVienForm();

                } catch (Exception e) {
                        JOptionPane.showMessageDialog(this, "Lỗi khi xóa nhân viên: " + e.getMessage(),
                                        "Lỗi", JOptionPane.ERROR_MESSAGE);
                        e.printStackTrace();
                }
        }

        // ==================== QUẢN LÝ METHODS ====================

        private void themQuanLy() {
                try {
                        // Lấy dữ liệu từ form
                        String maQL = input_ma_quan_ly.getText().trim();
                        String tenQL = input_ten_quan_ly.getText().trim();
                        String taiKhoan = input_tai_khoan_quan_ly.getText().trim();
                        String matKhau = input_mat_khau__quan_ly.getText().trim();

                        // Validation
                        if (maQL.isEmpty() || tenQL.isEmpty() || taiKhoan.isEmpty() || matKhau.isEmpty()) {
                                JOptionPane.showMessageDialog(this, "Vui lòng điền đầy đủ thông tin!",
                                                "Lỗi", JOptionPane.ERROR_MESSAGE);
                                return;
                        }

                        // Kiểm tra mã quản lý đã tồn tại
                        if (quanLyDAO.checkMaQuanLyExists(maQL)) {
                                JOptionPane.showMessageDialog(this, "Mã quản lý đã tồn tại!",
                                                "Lỗi", JOptionPane.ERROR_MESSAGE);
                                return;
                        }

                        // Kiểm tra tài khoản đã tồn tại
                        if (quanLyDAO.checkTaiKhoanExists(taiKhoan)) {
                                JOptionPane.showMessageDialog(this, "Tài khoản đã tồn tại!",
                                                "Lỗi", JOptionPane.ERROR_MESSAGE);
                                return;
                        }

                        // Thêm vào database
                        quanLyDAO.addQuanLy(maQL, tenQL, taiKhoan, matKhau);

                        JOptionPane.showMessageDialog(this, "Thêm quản lý thành công!",
                                        "Thành công", JOptionPane.INFORMATION_MESSAGE);

                        // Refresh data và clear form
                        loadQuanLyData();
                        clearQuanLyForm();

                } catch (Exception e) {
                        JOptionPane.showMessageDialog(this, "Lỗi khi thêm quản lý: " + e.getMessage(),
                                        "Lỗi", JOptionPane.ERROR_MESSAGE);
                        e.printStackTrace();
                }
        }

        private void suaQuanLy() {
                try {
                        // Kiểm tra có quản lý được chọn
                        if (maQuanLyDangSua == null || maQuanLyDangSua.isEmpty()) {
                                JOptionPane.showMessageDialog(this, "Vui lòng chọn quản lý từ danh sách để sửa!",
                                                "Thông báo", JOptionPane.WARNING_MESSAGE);
                                return;
                        }

                        // Lấy dữ liệu từ form
                        String maQL = input_ma_quan_ly.getText().trim();
                        String tenQL = input_ten_quan_ly.getText().trim();
                        String taiKhoan = input_tai_khoan_quan_ly.getText().trim();
                        String matKhau = input_mat_khau__quan_ly.getText().trim();

                        // Validation
                        if (maQL.isEmpty() || tenQL.isEmpty() || taiKhoan.isEmpty() || matKhau.isEmpty()) {
                                JOptionPane.showMessageDialog(this, "Vui lòng điền đầy đủ thông tin!",
                                                "Lỗi", JOptionPane.ERROR_MESSAGE);
                                return;
                        }

                        // Tìm quản lý cần sửa
                        quan_ly qlCanSua = null;
                        for (quan_ly ql : danhSachQuanLy) {
                                if (ql.getMa_quan_ly().equals(maQuanLyDangSua)) {
                                        qlCanSua = ql;
                                        break;
                                }
                        }

                        if (qlCanSua == null) {
                                JOptionPane.showMessageDialog(this, "Không tìm thấy quản lý cần sửa!",
                                                "Lỗi", JOptionPane.ERROR_MESSAGE);
                                return;
                        }

                        // Kiểm tra mã quản lý trùng lặp (nếu thay đổi mã)
                        if (!maQL.equals(maQuanLyDangSua) && quanLyDAO.checkMaQuanLyExists(maQL)) {
                                JOptionPane.showMessageDialog(this, "Mã quản lý đã tồn tại!",
                                                "Lỗi", JOptionPane.ERROR_MESSAGE);
                                return;
                        }

                        // Cập nhật trong database
                        quanLyDAO.updateQuanLy(qlCanSua.getId_quan_ly(), maQL, tenQL, taiKhoan, matKhau);

                        JOptionPane.showMessageDialog(this, "Sửa quản lý thành công!",
                                        "Thành công", JOptionPane.INFORMATION_MESSAGE);

                        // Refresh data và clear form
                        loadQuanLyData();
                        clearQuanLyForm();

                } catch (Exception e) {
                        JOptionPane.showMessageDialog(this, "Lỗi khi sửa quản lý: " + e.getMessage(),
                                        "Lỗi", JOptionPane.ERROR_MESSAGE);
                        e.printStackTrace();
                }
        }

        private void xoaQuanLy() {
                try {
                        // Kiểm tra có quản lý được chọn
                        if (maQuanLyDangSua == null || maQuanLyDangSua.isEmpty()) {
                                JOptionPane.showMessageDialog(this, "Vui lòng chọn quản lý từ danh sách để xóa!",
                                                "Thông báo", JOptionPane.WARNING_MESSAGE);
                                return;
                        }

                        // Hiển thị dialog xác nhận
                        String tenQL = input_ten_quan_ly.getText().trim();
                        int confirm = JOptionPane.showConfirmDialog(this,
                                        "Bạn có chắc chắn muốn xóa quản lý:\n" +
                                                        "Mã: " + maQuanLyDangSua + "\n" +
                                                        "Tên: " + tenQL + "\n\n" +
                                                        "Hành động này không thể hoàn tác!",
                                        "Xác nhận xóa quản lý",
                                        JOptionPane.YES_NO_OPTION,
                                        JOptionPane.QUESTION_MESSAGE);

                        if (confirm != JOptionPane.YES_OPTION) {
                                return;
                        }

                        // Tìm quản lý cần xóa
                        quan_ly qlCanXoa = null;
                        for (quan_ly ql : danhSachQuanLy) {
                                if (ql.getMa_quan_ly().equals(maQuanLyDangSua)) {
                                        qlCanXoa = ql;
                                        break;
                                }
                        }

                        if (qlCanXoa == null) {
                                JOptionPane.showMessageDialog(this, "Không tìm thấy quản lý cần xóa!",
                                                "Lỗi", JOptionPane.ERROR_MESSAGE);
                                return;
                        }

                        // Kiểm tra xem có nhân viên nào đang thuộc quản lý này không
                        List<nhan_vien> nhanVienCuaQuanLy = nhanVienDAO.getNhanVienByQuanLy(qlCanXoa.getId_quan_ly());
                        if (!nhanVienCuaQuanLy.isEmpty()) {
                                JOptionPane.showMessageDialog(this,
                                                "Không thể xóa quản lý này vì còn có " + nhanVienCuaQuanLy.size()
                                                                + " nhân viên đang thuộc quản lý!",
                                                "Lỗi", JOptionPane.ERROR_MESSAGE);
                                return;
                        }

                        // Xóa trong database
                        quanLyDAO.deleteQuanLy(qlCanXoa.getId_quan_ly());

                        JOptionPane.showMessageDialog(this, "Xóa quản lý thành công!",
                                        "Thành công", JOptionPane.INFORMATION_MESSAGE);

                        // Refresh data và clear form
                        loadQuanLyData();
                        clearQuanLyForm();

                } catch (Exception e) {
                        JOptionPane.showMessageDialog(this, "Lỗi khi xóa quản lý: " + e.getMessage(),
                                        "Lỗi", JOptionPane.ERROR_MESSAGE);
                        e.printStackTrace();
                }
        }

        /**
         * This method is called from within the constructor to
         * initialize the form.
         * WARNING: Do NOT modify this code. The content of this method is
         * always regenerated by the Form Editor.
         */
        @SuppressWarnings("unchecked")
        // <editor-fold defaultstate="collapsed" desc="Generated
        // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        input_ma_nhan_vien = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        input_Ten_nhan_vien = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        input_mat_khau = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        input_so_dien_thoai = new javax.swing.JTextField();
        input_tai_khoan = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jTextField8 = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        input_ma_quan_ly = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        input_ten_quan_ly = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        input_mat_khau__quan_ly = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        input_so_dien_thoai_quan_ly = new javax.swing.JTextField();
        input_tai_khoan_quan_ly = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jTextField11 = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));

        jLabel6.setText("Mã nhân viên");

        input_ma_nhan_vien.setText("jTextField2");

        jLabel7.setText("Tên nhân viên");

        input_Ten_nhan_vien.setText("jTextField2");

        jLabel8.setText("Tài khoản");

        jLabel9.setText("Mật khẩu");

        input_mat_khau.setText("jTextField2");

        jLabel10.setText("Số điện thoại");

        input_so_dien_thoai.setText("jTextField2");

        input_tai_khoan.setText("jTextField1");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(18, 18, 18)
                        .addComponent(input_ma_nhan_vien, javax.swing.GroupLayout.PREFERRED_SIZE, 346, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(jLabel8)
                            .addComponent(jLabel9)
                            .addComponent(jLabel10))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(input_so_dien_thoai)
                            .addComponent(input_mat_khau)
                            .addComponent(input_Ten_nhan_vien)
                            .addComponent(input_tai_khoan))))
                .addContainerGap(40, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(input_ma_nhan_vien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(input_Ten_nhan_vien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(input_tai_khoan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(input_mat_khau, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(input_so_dien_thoai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(22, Short.MAX_VALUE))
        );

        jLabel4.setFont(new java.awt.Font("Helvetica Neue", 1, 15)); // NOI18N
        jLabel4.setText("Quản lý nhân viên");

        jLabel5.setFont(new java.awt.Font("Helvetica Neue", 1, 15)); // NOI18N
        jLabel5.setText("Thông tin nhân viên");

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));

        jLabel14.setText("Tìm kiếm");

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Mã nhân viên", "Tên nhân viên", "Tài khoản", "Mật khẩu", "SĐT", "Tên quản lý"
            }
        ));
        jScrollPane2.setViewportView(jTable2);
        if (jTable2.getColumnModel().getColumnCount() > 0) {
            jTable2.getColumnModel().getColumn(4).setHeaderValue("SĐT");
            jTable2.getColumnModel().getColumn(5).setHeaderValue("Tên quản lý");
        }

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addContainerGap(1241, Short.MAX_VALUE)
                        .addComponent(jLabel14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane2))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 412, Short.MAX_VALUE))
        );

        jButton1.setBackground(new java.awt.Color(102, 255, 102));
        jButton1.setFont(new java.awt.Font("Helvetica Neue", 1, 13)); // NOI18N
        jButton1.setText("THÊM");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setBackground(new java.awt.Color(255, 102, 102));
        jButton2.setFont(new java.awt.Font("Helvetica Neue", 1, 13)); // NOI18N
        jButton2.setText("XÓA");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setBackground(new java.awt.Color(255, 255, 102));
        jButton3.setFont(new java.awt.Font("Helvetica Neue", 1, 13)); // NOI18N
        jButton3.setText("CẬP NHẬT");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(201, 201, 201)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel4)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(34, 34, 34)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addGap(18, 18, 18)
                .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Nhân viên", jPanel2);

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));

        jLabel11.setText("Mã quản lý");

        input_ma_quan_ly.setText("jTextField2");

        jLabel12.setText("Tên quản lý");

        input_ten_quan_ly.setText("jTextField2");

        jLabel13.setText("Tài khoản");

        jLabel15.setText("Mật khẩu");

        input_mat_khau__quan_ly.setText("jTextField2");

        jLabel16.setText("Số điện thoại");

        input_so_dien_thoai_quan_ly.setText("jTextField2");

        input_tai_khoan_quan_ly.setText("jTextField1");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, 88, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(input_mat_khau__quan_ly)
                            .addComponent(input_so_dien_thoai_quan_ly)))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, 92, Short.MAX_VALUE)
                                .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(2, 2, 2)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(input_ma_quan_ly, javax.swing.GroupLayout.DEFAULT_SIZE, 534, Short.MAX_VALUE)
                            .addComponent(input_ten_quan_ly)
                            .addComponent(input_tai_khoan_quan_ly))))
                .addContainerGap(20, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(input_ma_quan_ly, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(input_ten_quan_ly, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(input_tai_khoan_quan_ly, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(input_mat_khau__quan_ly, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(input_so_dien_thoai_quan_ly, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(22, Short.MAX_VALUE))
        );

        jLabel17.setFont(new java.awt.Font("Helvetica Neue", 1, 15)); // NOI18N
        jLabel17.setText("Quản lý ");

        jLabel18.setFont(new java.awt.Font("Helvetica Neue", 1, 15)); // NOI18N
        jLabel18.setText("Thông tin quản lý");

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));

        jLabel19.setText("Tìm kiếm");

        jTable3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Mã quản lý", "Tên quản lý", "Tài khoản", "Mật khẩu"
            }
        ));
        jScrollPane3.setViewportView(jTable3);

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel19)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextField11, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 1558, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel19))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 418, Short.MAX_VALUE)
                .addContainerGap())
        );

        jButton4.setBackground(new java.awt.Color(102, 255, 102));
        jButton4.setFont(new java.awt.Font("Helvetica Neue", 1, 13)); // NOI18N
        jButton4.setText("THÊM");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setBackground(new java.awt.Color(255, 102, 102));
        jButton5.setFont(new java.awt.Font("Helvetica Neue", 1, 13)); // NOI18N
        jButton5.setText("XÓA");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setBackground(new java.awt.Color(255, 255, 102));
        jButton6.setFont(new java.awt.Font("Helvetica Neue", 1, 13)); // NOI18N
        jButton6.setText("CẬP NHẬT");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel17)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 393, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(100, 100, 100)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addContainerGap(661, Short.MAX_VALUE))))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel17)
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(34, 34, 34)
                        .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel18)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Quản lý", jPanel3);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, 600, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

        private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton6ActionPerformed
                suaQuanLy();
        }// GEN-LAST:event_jButton6ActionPerformed

        private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton5ActionPerformed
                xoaQuanLy();
        }// GEN-LAST:event_jButton5ActionPerformed

        private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton4ActionPerformed
                themQuanLy();
        }// GEN-LAST:event_jButton4ActionPerformed

        private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton3ActionPerformed
                suaNhanVien();
        }// GEN-LAST:event_jButton3ActionPerformed

        private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton2ActionPerformed
                xoaNhanVien();
        }// GEN-LAST:event_jButton2ActionPerformed

        private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton1ActionPerformed
                themNhanVien();
        }// GEN-LAST:event_jButton1ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField input_Ten_nhan_vien;
    private javax.swing.JTextField input_ma_nhan_vien;
    private javax.swing.JTextField input_ma_quan_ly;
    private javax.swing.JTextField input_mat_khau;
    private javax.swing.JTextField input_mat_khau__quan_ly;
    private javax.swing.JTextField input_so_dien_thoai;
    private javax.swing.JTextField input_so_dien_thoai_quan_ly;
    private javax.swing.JTextField input_tai_khoan;
    private javax.swing.JTextField input_tai_khoan_quan_ly;
    private javax.swing.JTextField input_ten_quan_ly;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    private javax.swing.JTextField jTextField11;
    private javax.swing.JTextField jTextField8;
    // End of variables declaration//GEN-END:variables

        // Setup window resize listener for table responsiveness
        private void setupWindowResizeListener() {
                // Add component listener to the main panel to handle resize events
                this.addComponentListener(new java.awt.event.ComponentAdapter() {
                        @Override
                        public void componentResized(java.awt.event.ComponentEvent evt) {
                                // Refresh table layouts when window is resized
                                javax.swing.SwingUtilities.invokeLater(() -> {
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
                TableResponsiveManager.cleanup(jTable2);
                TableResponsiveManager.cleanup(jTable3);
        }

}
