/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.shop.util;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * Utility class for currency formatting
 * @author mailx
 */
public class CurrencyUtils {
    
    private static final NumberFormat VND_FORMAT = NumberFormat.getInstance(new Locale("vi", "VN"));
    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#,###");
    
    /**
     * Format số tiền thành chuỗi VND
     * @param amount số tiền
     * @return chuỗi đã format (ví dụ: "100,000 VND")
     */
    public static String formatVND(int amount) {
        return DECIMAL_FORMAT.format(amount) + " VND";
    }
    
    /**
     * Format số tiền thành chuỗi VND
     * @param amount số tiền
     * @return chuỗi đã format (ví dụ: "100,000 VND")
     */
    public static String formatVND(double amount) {
        return DECIMAL_FORMAT.format(amount) + " VND";
    }
    
    /**
     * Format số tiền thành chuỗi không có đơn vị
     * @param amount số tiền
     * @return chuỗi đã format (ví dụ: "100,000")
     */
    public static String formatNumber(int amount) {
        return DECIMAL_FORMAT.format(amount);
    }
    
    /**
     * Format số tiền thành chuỗi không có đơn vị
     * @param amount số tiền
     * @return chuỗi đã format (ví dụ: "100,000")
     */
    public static String formatNumber(double amount) {
        return DECIMAL_FORMAT.format(amount);
    }
    
    /**
     * Parse chuỗi tiền thành số
     * @param amountStr chuỗi tiền (có thể có dấu phẩy)
     * @return số tiền
     * @throws NumberFormatException nếu không parse được
     */
    public static int parseVND(String amountStr) throws NumberFormatException {
        if (amountStr == null || amountStr.trim().isEmpty()) {
            return 0;
        }
        
        // Loại bỏ "VND", khoảng trắng và dấu phẩy
        String cleanStr = amountStr.replaceAll("[VND,\\s]", "");
        return Integer.parseInt(cleanStr);
    }
    
    /**
     * Validate chuỗi tiền có hợp lệ không
     * @param amountStr chuỗi tiền
     * @return true nếu hợp lệ
     */
    public static boolean isValidAmount(String amountStr) {
        try {
            parseVND(amountStr);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
