package com.mycompany.shop.util;

import com.mycompany.shop.model.ao;
import com.mycompany.shop.model.chi_tiet_hoa_don;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

/**
 * Utility class to manage shopping cart operations
 */
public class CartManager {
    
    /**
     * Add product to cart with quantity dialog
     */
    public static boolean addProductToCart(List<chi_tiet_hoa_don> cart, ao product, 
                                         DefaultTableModel cartModel, Runnable updateCallback) {
        
        // Check stock availability
        if (product.getSo_luong() <= 0) {
            JOptionPane.showMessageDialog(null, 
                "Sản phẩm '" + product.getTen_ao() + "' đã hết hàng!", 
                "Thông báo", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        // Check if product already exists in cart
        chi_tiet_hoa_don existingItem = findProductInCart(cart, product.getId_ao());
        
        if (existingItem != null) {
            // Product exists, ask for quantity to add
            return updateProductQuantity(cart, existingItem, product, cartModel, updateCallback, false);
        } else {
            // New product, ask for initial quantity
            return addNewProductToCart(cart, product, cartModel, updateCallback);
        }
    }
    
    /**
     * Add new product to cart
     */
    private static boolean addNewProductToCart(List<chi_tiet_hoa_don> cart, ao product, 
                                             DefaultTableModel cartModel, Runnable updateCallback) {
        
        String quantityStr = JOptionPane.showInputDialog(null,
            "Nhập số lượng cho sản phẩm '" + product.getTen_ao() + "':\n" +
            "Số lượng tồn kho: " + product.getSo_luong(),
            "Thêm sản phẩm", JOptionPane.QUESTION_MESSAGE);
        
        if (quantityStr == null || quantityStr.trim().isEmpty()) {
            return false; // User cancelled
        }
        
        try {
            int quantity = Integer.parseInt(quantityStr.trim());
            
            if (quantity <= 0) {
                JOptionPane.showMessageDialog(null, 
                    "Số lượng phải lớn hơn 0!", 
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
                return false;
            }
            
            if (quantity > product.getSo_luong()) {
                JOptionPane.showMessageDialog(null, 
                    "Số lượng không được vượt quá tồn kho (" + product.getSo_luong() + ")!", 
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
                return false;
            }
            
            // Create new cart item
            chi_tiet_hoa_don newItem = new chi_tiet_hoa_don();
            newItem.setId_ao(product.getId_ao());
            newItem.setSo_luong(quantity);
            newItem.setDon_gia(product.getGia());
            
            cart.add(newItem);
            
            // Update display
            if (updateCallback != null) {
                updateCallback.run();
            }
            
            JOptionPane.showMessageDialog(null, 
                "Đã thêm " + quantity + " sản phẩm '" + product.getTen_ao() + "' vào giỏ hàng!", 
                "Thành công", JOptionPane.INFORMATION_MESSAGE);
            
            return true;
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, 
                "Số lượng không hợp lệ!", 
                "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    /**
     * Update product quantity in cart
     */
    public static boolean updateProductQuantity(List<chi_tiet_hoa_don> cart, 
                                               chi_tiet_hoa_don cartItem, ao product,
                                               DefaultTableModel cartModel, Runnable updateCallback,
                                               boolean isEdit) {
        
        String action = isEdit ? "Sửa" : "Thêm";
        String currentInfo = isEdit ? "\nSố lượng hiện tại: " + cartItem.getSo_luong() : "";
        
        String quantityStr = JOptionPane.showInputDialog(null,
            action + " số lượng cho sản phẩm '" + product.getTen_ao() + "':" +
            currentInfo + "\n" +
            "Số lượng tồn kho: " + product.getSo_luong(),
            action + " sản phẩm", JOptionPane.QUESTION_MESSAGE);
        
        if (quantityStr == null || quantityStr.trim().isEmpty()) {
            return false; // User cancelled
        }
        
        try {
            int newQuantity = Integer.parseInt(quantityStr.trim());
            
            if (newQuantity <= 0) {
                // Ask if user wants to remove the item
                int confirm = JOptionPane.showConfirmDialog(null,
                    "Số lượng = 0. Bạn có muốn xóa sản phẩm này khỏi giỏ hàng?",
                    "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
                
                if (confirm == JOptionPane.YES_OPTION) {
                    return removeProductFromCart(cart, cartItem, cartModel, updateCallback);
                } else {
                    return false;
                }
            }
            
            if (newQuantity > product.getSo_luong()) {
                JOptionPane.showMessageDialog(null, 
                    "Số lượng không được vượt quá tồn kho (" + product.getSo_luong() + ")!", 
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
                return false;
            }
            
            // Update quantity
            if (isEdit) {
                cartItem.setSo_luong(newQuantity);
            } else {
                cartItem.setSo_luong(cartItem.getSo_luong() + newQuantity);
                
                // Check total quantity doesn't exceed stock
                if (cartItem.getSo_luong() > product.getSo_luong()) {
                    JOptionPane.showMessageDialog(null, 
                        "Tổng số lượng không được vượt quá tồn kho (" + product.getSo_luong() + ")!", 
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
                    cartItem.setSo_luong(cartItem.getSo_luong() - newQuantity); // Revert
                    return false;
                }
            }
            
            // Update display
            if (updateCallback != null) {
                updateCallback.run();
            }
            
            String message = isEdit ? 
                "Đã cập nhật số lượng sản phẩm '" + product.getTen_ao() + "' thành " + cartItem.getSo_luong() + "!" :
                "Đã thêm " + newQuantity + " sản phẩm '" + product.getTen_ao() + "' vào giỏ hàng!";
            
            JOptionPane.showMessageDialog(null, message, "Thành công", JOptionPane.INFORMATION_MESSAGE);
            
            return true;
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, 
                "Số lượng không hợp lệ!", 
                "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    /**
     * Remove product from cart
     */
    public static boolean removeProductFromCart(List<chi_tiet_hoa_don> cart, 
                                              chi_tiet_hoa_don cartItem,
                                              DefaultTableModel cartModel, 
                                              Runnable updateCallback) {
        
        int confirm = JOptionPane.showConfirmDialog(null,
            "Bạn có chắc chắn muốn xóa sản phẩm này khỏi giỏ hàng?",
            "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            cart.remove(cartItem);
            
            // Update display
            if (updateCallback != null) {
                updateCallback.run();
            }
            
            JOptionPane.showMessageDialog(null, 
                "Đã xóa sản phẩm khỏi giỏ hàng!", 
                "Thành công", JOptionPane.INFORMATION_MESSAGE);
            
            return true;
        }
        
        return false;
    }
    
    /**
     * Find product in cart by ID
     */
    public static chi_tiet_hoa_don findProductInCart(List<chi_tiet_hoa_don> cart, int productId) {
        for (chi_tiet_hoa_don item : cart) {
            if (item.getId_ao() == productId) {
                return item;
            }
        }
        return null;
    }
    
    /**
     * Clear entire cart
     */
    public static boolean clearCart(List<chi_tiet_hoa_don> cart, 
                                  DefaultTableModel cartModel, 
                                  Runnable updateCallback) {
        
        if (cart.isEmpty()) {
            JOptionPane.showMessageDialog(null, 
                "Giỏ hàng đã trống!", 
                "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            return false;
        }
        
        int confirm = JOptionPane.showConfirmDialog(null,
            "Bạn có chắc chắn muốn xóa toàn bộ giỏ hàng?",
            "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            cart.clear();
            
            // Update display
            if (updateCallback != null) {
                updateCallback.run();
            }
            
            JOptionPane.showMessageDialog(null, 
                "Đã xóa toàn bộ giỏ hàng!", 
                "Thành công", JOptionPane.INFORMATION_MESSAGE);
            
            return true;
        }
        
        return false;
    }
    
    /**
     * Get cart total amount
     */
    public static double getCartTotal(List<chi_tiet_hoa_don> cart) {
        double total = 0;
        for (chi_tiet_hoa_don item : cart) {
            total += item.getSo_luong() * item.getDon_gia();
        }
        return total;
    }
    
    /**
     * Get cart item count
     */
    public static int getCartItemCount(List<chi_tiet_hoa_don> cart) {
        int count = 0;
        for (chi_tiet_hoa_don item : cart) {
            count += item.getSo_luong();
        }
        return count;
    }
    
    /**
     * Validate cart before checkout
     */
    public static boolean validateCart(List<chi_tiet_hoa_don> cart, List<ao> availableProducts) {
        if (cart.isEmpty()) {
            JOptionPane.showMessageDialog(null, 
                "Giỏ hàng trống, vui lòng thêm sản phẩm trước khi thanh toán!", 
                "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        // Check stock availability for each item
        for (chi_tiet_hoa_don cartItem : cart) {
            ao product = findProductById(availableProducts, cartItem.getId_ao());
            
            if (product == null) {
                JOptionPane.showMessageDialog(null, 
                    "Sản phẩm ID " + cartItem.getId_ao() + " không tồn tại!", 
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
                return false;
            }
            
            if (cartItem.getSo_luong() > product.getSo_luong()) {
                JOptionPane.showMessageDialog(null, 
                    "Sản phẩm '" + product.getTen_ao() + "' không đủ số lượng!\n" +
                    "Yêu cầu: " + cartItem.getSo_luong() + ", Tồn kho: " + product.getSo_luong(), 
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }
        
        return true;
    }
    
    /**
     * Find product by ID in list
     */
    private static ao findProductById(List<ao> products, int productId) {
        for (ao product : products) {
            if (product.getId_ao() == productId) {
                return product;
            }
        }
        return null;
    }
}
