package com.mycompany.shop.util;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.HashMap;
import java.util.Map;

/**
 * Utility to prevent table flickering and stabilize table updates
 */
public class TableStabilizer {

    private static final Map<JTable, Timer> resizeTimers = new HashMap<>();
    private static final Map<JTable, Boolean> updateFlags = new HashMap<>();

    /**
     * Stabilize a table to prevent flickering
     */
    public static void stabilizeTable(JTable table) {
        // Remove any existing listeners that might cause conflicts
        removeExistingListeners(table);

        // Add stabilized resize listener
        addStabilizedResizeListener(table);

        // Set update flag
        updateFlags.put(table, false);
    }

    /**
     * Remove existing component listeners that might cause conflicts
     */
    private static void removeExistingListeners(JTable table) {
        // Find scroll pane containing the table
        JScrollPane scrollPane = findScrollPane(table);
        if (scrollPane != null) {
            // Remove all component listeners from scroll pane
            java.awt.event.ComponentListener[] listeners = scrollPane.getComponentListeners();
            for (java.awt.event.ComponentListener listener : listeners) {
                scrollPane.removeComponentListener(listener);
            }
        }
    }

    /**
     * Add stabilized resize listener
     */
    private static void addStabilizedResizeListener(JTable table) {
        JScrollPane scrollPane = findScrollPane(table);
        if (scrollPane != null) {
            scrollPane.addComponentListener(new ComponentAdapter() {
                @Override
                public void componentResized(ComponentEvent e) {
                    scheduleStabilizedResize(table);
                }
            });
        }
    }

    /**
     * Schedule a stabilized resize with debouncing
     */
    private static void scheduleStabilizedResize(JTable table) {
        // Cancel existing timer
        Timer existingTimer = resizeTimers.get(table);
        if (existingTimer != null && existingTimer.isRunning()) {
            existingTimer.stop();
        }

        // Create new timer with longer delay to prevent flickering
        Timer newTimer = new Timer(300, e -> {
            if (!updateFlags.getOrDefault(table, false)) {
                performStabilizedResize(table);
            }
        });

        newTimer.setRepeats(false);
        resizeTimers.put(table, newTimer);
        newTimer.start();
    }

    /**
     * Perform stabilized resize without flickering
     */
    private static void performStabilizedResize(JTable table) {
        SwingUtilities.invokeLater(() -> {
            try {
                updateFlags.put(table, true);

                // Temporarily disable auto-resize to prevent flickering
                int originalMode = table.getAutoResizeMode();
                table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

                // Perform resize
                adjustTableColumns(table);

                // Re-enable auto-resize
                table.setAutoResizeMode(originalMode);

                // Revalidate and repaint
                table.revalidate();
                table.repaint();

            } finally {
                updateFlags.put(table, false);
            }
        });
    }

    /**
     * Adjust table columns without flickering
     */
    private static void adjustTableColumns(JTable table) {
        JScrollPane scrollPane = findScrollPane(table);
        if (scrollPane == null)
            return;

        int availableWidth = scrollPane.getViewport().getWidth();
        if (availableWidth <= 0)
            return;

        // Calculate optimal column widths
        int columnCount = table.getColumnCount();
        if (columnCount == 0)
            return;

        int[] preferredWidths = new int[columnCount];
        int totalPreferred = 0;

        for (int i = 0; i < columnCount; i++) {
            String columnName = table.getColumnName(i).toLowerCase();
            preferredWidths[i] = getOptimalColumnWidth(columnName);
            totalPreferred += preferredWidths[i];
        }

        // Adjust if total exceeds available width
        if (totalPreferred > availableWidth) {
            double scale = (double) availableWidth / totalPreferred;
            for (int i = 0; i < columnCount; i++) {
                preferredWidths[i] = (int) (preferredWidths[i] * scale);
            }
        } else {
            // Distribute extra space
            int extraSpace = availableWidth - totalPreferred;
            int extraPerColumn = extraSpace / columnCount;
            for (int i = 0; i < columnCount; i++) {
                preferredWidths[i] += extraPerColumn;
            }
        }

        // Apply widths
        for (int i = 0; i < columnCount; i++) {
            table.getColumnModel().getColumn(i).setPreferredWidth(preferredWidths[i]);
        }
    }

    /**
     * Get optimal column width based on column name
     */
    private static int getOptimalColumnWidth(String columnName) {
        if (columnName.contains("stt") || columnName.contains("số")) {
            return 50;
        } else if (columnName.contains("mã")) {
            return 100;
        } else if (columnName.contains("tên") || columnName.contains("họ")) {
            return 150;
        } else if (columnName.contains("địa chỉ")) {
            return 200;
        } else if (columnName.contains("số điện thoại") || columnName.contains("sđt")) {
            return 120;
        } else if (columnName.contains("ngày") || columnName.contains("thời gian")) {
            return 100;
        } else if (columnName.contains("giá") || columnName.contains("tiền")) {
            return 100;
        } else if (columnName.contains("ảnh") || columnName.contains("hình")) {
            return 60;
        } else if (columnName.contains("trạng thái") || columnName.contains("tình trạng")) {
            return 100;
        } else {
            return 80;
        }
    }

    /**
     * Find scroll pane containing the table
     */
    private static JScrollPane findScrollPane(JTable table) {
        java.awt.Container parent = table.getParent();
        while (parent != null) {
            if (parent instanceof JScrollPane) {
                return (JScrollPane) parent;
            }
            parent = parent.getParent();
        }
        return null;
    }

    /**
     * Safe table update without flickering
     */
    public static void safeUpdateTable(JTable table, Runnable updateAction) {
        if (updateFlags.getOrDefault(table, false)) {
            // Already updating, skip
            return;
        }

        SwingUtilities.invokeLater(() -> {
            try {
                updateFlags.put(table, true);

                // Disable auto-resize temporarily
                int originalMode = table.getAutoResizeMode();
                table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

                // Perform update
                updateAction.run();

                // Re-enable auto-resize
                table.setAutoResizeMode(originalMode);

            } finally {
                updateFlags.put(table, false);
            }
        });
    }

    /**
     * Safe model update without flickering
     */
    public static void safeUpdateModel(DefaultTableModel model, Runnable updateAction) {
        SwingUtilities.invokeLater(() -> {
            // Temporarily disable table events
            model.fireTableStructureChanged();

            // Perform update
            updateAction.run();

            // Re-enable events
            model.fireTableDataChanged();
        });
    }

    /**
     * Cleanup resources for a table
     */
    public static void cleanup(JTable table) {
        Timer timer = resizeTimers.remove(table);
        if (timer != null && timer.isRunning()) {
            timer.stop();
        }
        updateFlags.remove(table);
    }

    /**
     * Cleanup all resources
     */
    public static void cleanupAll() {
        for (Timer timer : resizeTimers.values()) {
            if (timer.isRunning()) {
                timer.stop();
            }
        }
        resizeTimers.clear();
        updateFlags.clear();
    }
}
