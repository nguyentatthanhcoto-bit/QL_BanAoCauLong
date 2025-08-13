package com.mycompany.shop.util;

import com.mycompany.shop.model.hoa_don;
import com.mycompany.shop.model.chi_tiet_hoa_don;
import com.mycompany.shop.model.ao;
import com.mycompany.shop.dao.chi_tiet_hoa_don_dao;
import com.mycompany.shop.dao.ao_dao;
import javax.swing.*;
import java.util.List;
import java.util.ArrayList;

/**
 * Utility class to manage invoice operations
 */
public class InvoiceManager {

    /**
     * Load invoice details into cart for editing
     */
    public static boolean loadInvoiceForEditing(hoa_don invoice,
            List<chi_tiet_hoa_don> cart,
            Runnable updateCallback) {

        if (invoice == null) {
            JOptionPane.showMessageDialog(null,
                    "Không tìm thấy thông tin hóa đơn!",
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Check if invoice is already paid
        if (invoice.isDaThanhToan()) {
            int choice = JOptionPane.showConfirmDialog(null,
                    "Hóa đơn này đã được thanh toán!\n" +
                            "Bạn có muốn xem chi tiết không?",
                    "Hóa đơn đã thanh toán",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.INFORMATION_MESSAGE);

            if (choice == JOptionPane.YES_OPTION) {
                showInvoiceDetails(invoice);
            }
            return false;
        }

        try {
            // Load invoice details
            chi_tiet_hoa_don_dao chiTietDAO = new chi_tiet_hoa_don_dao();
            List<chi_tiet_hoa_don> chiTietList = chiTietDAO.getChiTietHoaDonByIdHoaDon(invoice.getId_hoa_don());

            // Cho phép load hóa đơn dù không có sản phẩm để người dùng có thể thêm tiếp
            if (chiTietList == null) {
                chiTietList = new ArrayList<>();
            }
            if (chiTietList.isEmpty()) {
                JOptionPane.showMessageDialog(null,
                        "Hóa đơn chưa có sản phẩm. Bạn có thể thêm sản phẩm để tiếp tục!",
                        "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            }

            // Validate stock availability
            ao_dao aoDAO = new ao_dao();
            List<String> outOfStockItems = new ArrayList<>();

            for (chi_tiet_hoa_don chiTiet : chiTietList) {
                ao product = aoDAO.getAoById(chiTiet.getId_ao());
                if (product == null || product.getSo_luong() < chiTiet.getSo_luong()) {
                    String productName = product != null ? product.getTen_ao() : "ID: " + chiTiet.getId_ao();
                    outOfStockItems.add(productName + " (Cần: " + chiTiet.getSo_luong() +
                            ", Tồn: " + (product != null ? product.getSo_luong() : 0) + ")");
                }
            }

            // Show stock warning if any
            if (!outOfStockItems.isEmpty()) {
                StringBuilder message = new StringBuilder();
                message.append("Một số sản phẩm trong hóa đơn không đủ tồn kho:\n\n");
                for (String item : outOfStockItems) {
                    message.append("• ").append(item).append("\n");
                }
                message.append("\nBạn có muốn tiếp tục chỉnh sửa hóa đơn không?");

                int choice = JOptionPane.showConfirmDialog(null,
                        message.toString(),
                        "Cảnh báo tồn kho",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE);

                if (choice != JOptionPane.YES_OPTION) {
                    return false;
                }
            }

            // Clear current cart and load invoice items
            cart.clear();
            cart.addAll(chiTietList);

            // Update display
            if (updateCallback != null) {
                updateCallback.run();
            }

            // Show success message
            JOptionPane.showMessageDialog(null,
                    "Đã tải hóa đơn '" + invoice.getMa_hoa_don() + "' để chỉnh sửa!\n" +
                            "Số sản phẩm: " + chiTietList.size(),
                    "Thành công", JOptionPane.INFORMATION_MESSAGE);

            return true;

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                    "Lỗi khi tải hóa đơn: " + e.getMessage(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Show invoice details in a dialog
     */
    public static void showInvoiceDetails(hoa_don invoice) {
        if (invoice == null)
            return;

        try {
            chi_tiet_hoa_don_dao chiTietDAO = new chi_tiet_hoa_don_dao();
            ao_dao aoDAO = new ao_dao();
            List<chi_tiet_hoa_don> chiTietList = chiTietDAO.getChiTietHoaDonByIdHoaDon(invoice.getId_hoa_don());

            StringBuilder details = new StringBuilder();
            details.append("=== CHI TIẾT HÓA ĐƠN ===\n\n");
            details.append("Mã hóa đơn: ").append(invoice.getMa_hoa_don()).append("\n");
            details.append("Ngày tạo: ").append(invoice.getNgay_tao()).append("\n");
            details.append("Trạng thái: ").append(invoice.isDaThanhToan() ? "Đã thanh toán" : "Chưa thanh toán")
                    .append("\n");

            if (invoice.getHo_va_ten_khach() != null && !invoice.getHo_va_ten_khach().isEmpty()) {
                details.append("Khách hàng: ").append(invoice.getHo_va_ten_khach()).append("\n");
                if (invoice.getSo_dien_thoai_khach() != null) {
                    details.append("SĐT: ").append(invoice.getSo_dien_thoai_khach()).append("\n");
                }
            }

            // Voucher info
            if (invoice.getId_voucher() > 0) {
                try {
                    com.mycompany.shop.dao.voucher_dao voucherDAO = new com.mycompany.shop.dao.voucher_dao();
                    com.mycompany.shop.model.voucher voucher = voucherDAO.getVoucherById(invoice.getId_voucher());
                    if (voucher != null) {
                        details.append("Voucher: ").append(voucher.getTen_voucher()).append(" (")
                                .append(voucher.getMa_voucher()).append(")\n");
                        details.append("Giá trị giảm: ").append(voucher.getGia_tri_giam()).append("%\n");

                        // Tính giá trị giảm thực tế
                        double discountAmount = voucherDAO.calculateDiscount(voucher.getMa_voucher(),
                                invoice.getTong_tien());
                        details.append("Số tiền giảm: ").append(CurrencyUtils.formatVND((int) discountAmount))
                                .append("\n");
                    }
                } catch (Exception e) {
                    details.append("Voucher: Lỗi khi tải thông tin voucher\n");
                }
            }

            details.append("\n=== DANH SÁCH SẢN PHẨM ===\n");

            int totalQuantity = 0;
            double totalAmount = 0;

            if (chiTietList != null && !chiTietList.isEmpty()) {
                for (int i = 0; i < chiTietList.size(); i++) {
                    chi_tiet_hoa_don chiTiet = chiTietList.get(i);
                    ao product = aoDAO.getAoById(chiTiet.getId_ao());

                    String productName = product != null ? product.getTen_ao() : "Sản phẩm không tồn tại";
                    double itemTotal = chiTiet.getSo_luong() * chiTiet.getDon_gia();

                    details.append(String.format("%d. %s\n", i + 1, productName));
                    details.append(String.format("   Số lượng: %d\n", chiTiet.getSo_luong()));
                    details.append(String.format("   Đơn giá: %s\n", CurrencyUtils.formatVND(chiTiet.getDon_gia())));
                    details.append(String.format("   Thành tiền: %s\n\n", CurrencyUtils.formatVND((int) itemTotal)));

                    totalQuantity += chiTiet.getSo_luong();
                    totalAmount += itemTotal;
                }
            } else {
                details.append("Không có sản phẩm nào.\n\n");
            }

            details.append("\n=== TỔNG KẾT ===\n");
            details.append("Tổng số lượng: ").append(totalQuantity).append("\n");
            details.append("Tổng tiền: ").append(CurrencyUtils.formatVND(invoice.getTong_tien())).append("\n");

            // Show in a scrollable dialog
            JTextArea textArea = new JTextArea(details.toString());
            textArea.setEditable(false);
            textArea.setFont(new java.awt.Font("Monospaced", java.awt.Font.PLAIN, 12));

            JScrollPane scrollPane = new JScrollPane(textArea);
            scrollPane.setPreferredSize(new java.awt.Dimension(500, 400));

            JOptionPane.showMessageDialog(null, scrollPane,
                    "Chi tiết hóa đơn: " + invoice.getMa_hoa_don(),
                    JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                    "Lỗi khi hiển thị chi tiết hóa đơn: " + e.getMessage(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    /**
     * Check if invoice can be edited
     */
    public static boolean canEditInvoice(hoa_don invoice) {
        if (invoice == null)
            return false;
        return invoice.isChuaThanhToan();
    }

    /**
     * Get invoice action options
     */
    public static String[] getInvoiceActionOptions(hoa_don invoice) {
        if (invoice == null) {
            return new String[] { "Đóng" };
        }

        if (invoice.isDaThanhToan()) {
            return new String[] { "Xem chi tiết", "In hóa đơn", "Đóng" };
        } else {
            return new String[] { "Chỉnh sửa", "Xem chi tiết", "Xóa hóa đơn", "Đóng" };
        }
    }

    /**
     * Handle invoice action selection
     */
    public static void handleInvoiceAction(hoa_don invoice, String action,
            List<chi_tiet_hoa_don> cart,
            Runnable updateCallback,
            Runnable refreshCallback) {

        switch (action) {
            case "Chỉnh sửa":
                if (loadInvoiceForEditing(invoice, cart, updateCallback)) {
                    // Set current invoice for editing
                    // This should be handled by the calling screen
                }
                break;

            case "Xem chi tiết":
                showInvoiceDetails(invoice);
                break;

            case "In hóa đơn":
                printInvoice(invoice);
                break;

            case "Xóa hóa đơn":
                deleteInvoice(invoice, refreshCallback);
                break;

            default:
                // Do nothing for "Đóng"
                break;
        }
    }

    /**
     * Print invoice (placeholder)
     */
    private static void printInvoice(hoa_don invoice) {
        JOptionPane.showMessageDialog(null,
                "Chức năng in hóa đơn sẽ được phát triển trong phiên bản tiếp theo.",
                "Thông báo", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Delete invoice with confirmation
     */
    private static void deleteInvoice(hoa_don invoice, Runnable refreshCallback) {
        if (invoice.isDaThanhToan()) {
            JOptionPane.showMessageDialog(null,
                    "Không thể xóa hóa đơn đã thanh toán!",
                    "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(null,
                "Bạn có chắc chắn muốn xóa hóa đơn '" + invoice.getMa_hoa_don() + "'?\n" +
                        "Hành động này không thể hoàn tác!",
                "Xác nhận xóa hóa đơn",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                // Delete invoice details first
                chi_tiet_hoa_don_dao chiTietDAO = new chi_tiet_hoa_don_dao();
                chiTietDAO.deleteChiTietHoaDonByIdHoaDon(invoice.getId_hoa_don());

                // Delete invoice
                com.mycompany.shop.dao.hoa_don_dao hoaDonDAO = new com.mycompany.shop.dao.hoa_don_dao();
                hoaDonDAO.deleteHoaDon(invoice.getId_hoa_don());

                JOptionPane.showMessageDialog(null,
                        "Đã xóa hóa đơn '" + invoice.getMa_hoa_don() + "' thành công!",
                        "Thành công", JOptionPane.INFORMATION_MESSAGE);

                // Refresh data
                if (refreshCallback != null) {
                    refreshCallback.run();
                }

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null,
                        "Lỗi khi xóa hóa đơn: " + e.getMessage(),
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }
}
