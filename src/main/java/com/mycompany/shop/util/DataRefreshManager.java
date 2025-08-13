package com.mycompany.shop.util;

import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.JOptionPane;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Utility class to manage automatic data refresh across screens
 */
public class DataRefreshManager {

    private static final Map<String, Set<Runnable>> refreshCallbacks = new ConcurrentHashMap<>();
    private static final Map<String, Timer> refreshTimers = new ConcurrentHashMap<>();

    // Data change events
    public static final String PRODUCT_CHANGED = "product_changed";
    public static final String INVOICE_CHANGED = "invoice_changed";
    public static final String CUSTOMER_CHANGED = "customer_changed";
    public static final String EMPLOYEE_CHANGED = "employee_changed";
    public static final String VOUCHER_CHANGED = "voucher_changed";
    public static final String CART_CHANGED = "cart_changed";

    /**
     * Register a callback for data refresh
     */
    public static void registerRefreshCallback(String dataType, Runnable callback) {
        refreshCallbacks.computeIfAbsent(dataType, k -> ConcurrentHashMap.newKeySet()).add(callback);
    }

    /**
     * Unregister a callback
     */
    public static void unregisterRefreshCallback(String dataType, Runnable callback) {
        Set<Runnable> callbacks = refreshCallbacks.get(dataType);
        if (callbacks != null) {
            callbacks.remove(callback);
            if (callbacks.isEmpty()) {
                refreshCallbacks.remove(dataType);
            }
        }
    }

    /**
     * Trigger data refresh for a specific type
     */
    public static void triggerRefresh(String dataType) {
        Set<Runnable> callbacks = refreshCallbacks.get(dataType);
        if (callbacks != null) {
            SwingUtilities.invokeLater(() -> {
                for (Runnable callback : callbacks) {
                    try {
                        callback.run();
                    } catch (Exception e) {
                        System.err.println("Error in refresh callback for " + dataType + ": " + e.getMessage());
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    /**
     * Trigger refresh with delay to batch multiple changes
     */
    public static void triggerDelayedRefresh(String dataType, int delayMs) {
        Timer existingTimer = refreshTimers.get(dataType);
        if (existingTimer != null && existingTimer.isRunning()) {
            existingTimer.stop();
        }

        Timer newTimer = new Timer(delayMs, e -> triggerRefresh(dataType));
        newTimer.setRepeats(false);
        refreshTimers.put(dataType, newTimer);
        newTimer.start();
    }

    /**
     * Trigger multiple data type refreshes
     */
    public static void triggerMultipleRefresh(String... dataTypes) {
        for (String dataType : dataTypes) {
            triggerRefresh(dataType);
        }
    }

    /**
     * Setup automatic refresh for a screen
     */
    public static void setupScreenRefresh(String screenName, Runnable refreshAction) {
        // Register for relevant data types based on screen
        switch (screenName.toLowerCase()) {
            case "thanhtoan":
                registerRefreshCallback(PRODUCT_CHANGED, refreshAction);
                registerRefreshCallback(INVOICE_CHANGED, refreshAction);
                registerRefreshCallback(VOUCHER_CHANGED, refreshAction);
                break;

            case "sanpham":
                registerRefreshCallback(PRODUCT_CHANGED, refreshAction);
                break;

            case "khachhang":
                registerRefreshCallback(CUSTOMER_CHANGED, refreshAction);
                registerRefreshCallback(INVOICE_CHANGED, refreshAction);
                break;

            case "nhanvien":
                registerRefreshCallback(EMPLOYEE_CHANGED, refreshAction);
                break;

            default:
                // Register for all changes
                registerRefreshCallback(PRODUCT_CHANGED, refreshAction);
                registerRefreshCallback(INVOICE_CHANGED, refreshAction);
                registerRefreshCallback(CUSTOMER_CHANGED, refreshAction);
                registerRefreshCallback(EMPLOYEE_CHANGED, refreshAction);
                registerRefreshCallback(VOUCHER_CHANGED, refreshAction);
                break;
        }
    }

    /**
     * Cleanup screen refresh callbacks
     */
    public static void cleanupScreenRefresh(String screenName, Runnable refreshAction) {
        // Unregister from all data types
        for (String dataType : Arrays.asList(PRODUCT_CHANGED, INVOICE_CHANGED,
                CUSTOMER_CHANGED, EMPLOYEE_CHANGED, VOUCHER_CHANGED)) {
            unregisterRefreshCallback(dataType, refreshAction);
        }
    }

    /**
     * Create a debounced refresh action
     */
    public static Runnable createDebouncedRefresh(Runnable originalAction, int delayMs) {
        final Timer[] timer = { null };

        return () -> {
            if (timer[0] != null && timer[0].isRunning()) {
                timer[0].stop();
            }

            timer[0] = new Timer(delayMs, e -> {
                SwingUtilities.invokeLater(originalAction);
            });
            timer[0].setRepeats(false);
            timer[0].start();
        };
    }

    /**
     * Notify product changes
     */
    public static void notifyProductChanged() {
        triggerDelayedRefresh(PRODUCT_CHANGED, 500);
    }

    /**
     * Notify invoice changes
     */
    public static void notifyInvoiceChanged() {
        triggerDelayedRefresh(INVOICE_CHANGED, 500);
    }

    /**
     * Notify customer changes
     */
    public static void notifyCustomerChanged() {
        triggerDelayedRefresh(CUSTOMER_CHANGED, 500);
    }

    /**
     * Notify employee changes
     */
    public static void notifyEmployeeChanged() {
        triggerDelayedRefresh(EMPLOYEE_CHANGED, 500);
    }

    /**
     * Notify voucher changes
     */
    public static void notifyVoucherChanged() {
        triggerDelayedRefresh(VOUCHER_CHANGED, 500);
    }

    /**
     * Notify cart changes
     */
    public static void notifyCartChanged() {
        triggerRefresh(CART_CHANGED);
    }

    /**
     * Create a safe refresh action that handles exceptions
     */
    public static Runnable createSafeRefresh(Runnable originalAction, String actionName) {
        return () -> {
            try {
                originalAction.run();
            } catch (Exception e) {
                System.err.println("Error in " + actionName + " refresh: " + e.getMessage());
                e.printStackTrace();

                // Show user-friendly error message
                SwingUtilities.invokeLater(() -> {
                    JOptionPane.showMessageDialog(null,
                            "Có lỗi xảy ra khi cập nhật dữ liệu: " + actionName + "\n" +
                                    "Vui lòng thử lại hoặc liên hệ hỗ trợ.",
                            "Lỗi cập nhật dữ liệu",
                            JOptionPane.ERROR_MESSAGE);
                });
            }
        };
    }

    /**
     * Setup periodic refresh for a data type
     */
    public static void setupPeriodicRefresh(String dataType, int intervalMs) {
        Timer existingTimer = refreshTimers.get(dataType + "_periodic");
        if (existingTimer != null && existingTimer.isRunning()) {
            existingTimer.stop();
        }

        Timer periodicTimer = new Timer(intervalMs, e -> triggerRefresh(dataType));
        periodicTimer.setRepeats(true);
        refreshTimers.put(dataType + "_periodic", periodicTimer);
        periodicTimer.start();
    }

    /**
     * Stop periodic refresh for a data type
     */
    public static void stopPeriodicRefresh(String dataType) {
        Timer timer = refreshTimers.remove(dataType + "_periodic");
        if (timer != null && timer.isRunning()) {
            timer.stop();
        }
    }

    /**
     * Get refresh statistics
     */
    public static Map<String, Integer> getRefreshStats() {
        Map<String, Integer> stats = new HashMap<>();
        for (Map.Entry<String, Set<Runnable>> entry : refreshCallbacks.entrySet()) {
            stats.put(entry.getKey(), entry.getValue().size());
        }
        return stats;
    }

    /**
     * Cleanup all resources
     */
    public static void cleanup() {
        // Stop all timers
        for (Timer timer : refreshTimers.values()) {
            if (timer.isRunning()) {
                timer.stop();
            }
        }

        // Clear all data
        refreshTimers.clear();
        refreshCallbacks.clear();
    }

    /**
     * Force immediate refresh of all registered callbacks
     */
    public static void forceRefreshAll() {
        for (String dataType : refreshCallbacks.keySet()) {
            triggerRefresh(dataType);
        }
    }
}
