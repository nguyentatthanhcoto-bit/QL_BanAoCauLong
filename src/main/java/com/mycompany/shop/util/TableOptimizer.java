package com.mycompany.shop.util;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

/**
 * Utility class to optimize JTable display and responsiveness
 */
public class TableOptimizer {
    
    /**
     * Optimize table for responsive display
     */
    public static void optimizeTable(JTable table) {
        if (table == null) return;
        
        // Enable auto-resize
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        
        // Fill viewport height
        table.setFillsViewportHeight(true);
        
        // Enable column reordering and resizing
        table.getTableHeader().setReorderingAllowed(true);
        table.getTableHeader().setResizingAllowed(true);
        
        // Set row height for better readability
        table.setRowHeight(Math.max(table.getRowHeight(), 25));
        
        // Apply modern styling
        applyModernTableStyling(table);
        
        // Setup responsive column widths
        setupResponsiveColumns(table);
        
        // Add resize listener for dynamic adjustment
        addResizeListener(table);
    }
    
    /**
     * Apply modern styling to table
     */
    private static void applyModernTableStyling(JTable table) {
        // Alternating row colors
        table.setShowGrid(true);
        table.setGridColor(new Color(230, 230, 230));
        
        // Header styling
        table.getTableHeader().setBackground(new Color(240, 240, 240));
        table.getTableHeader().setFont(table.getTableHeader().getFont().deriveFont(Font.BOLD));
        
        // Selection colors
        table.setSelectionBackground(new Color(184, 207, 229));
        table.setSelectionForeground(Color.BLACK);
        
        // Cell padding
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setBorder(BorderFactory.createEmptyBorder(5, 8, 5, 8));
        
        // Apply renderer to all columns
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(renderer);
        }
    }
    
    /**
     * Setup responsive column widths based on content
     */
    private static void setupResponsiveColumns(JTable table) {
        TableColumnModel columnModel = table.getColumnModel();
        
        for (int i = 0; i < columnModel.getColumnCount(); i++) {
            TableColumn column = columnModel.getColumn(i);
            String columnName = column.getHeaderValue().toString();
            
            // Set column widths based on content type
            int preferredWidth = getPreferredColumnWidth(columnName);
            int minWidth = getMinimumColumnWidth(columnName);
            int maxWidth = getMaximumColumnWidth(columnName);
            
            column.setPreferredWidth(preferredWidth);
            column.setMinWidth(minWidth);
            if (maxWidth > 0) {
                column.setMaxWidth(maxWidth);
            }
        }
    }
    
    /**
     * Get preferred width for column based on header name
     */
    private static int getPreferredColumnWidth(String columnName) {
        String name = columnName.toLowerCase();
        
        if (name.contains("stt") || name.contains("số")) {
            return 50;
        } else if (name.contains("mã")) {
            return 100;
        } else if (name.contains("tên") || name.contains("họ")) {
            return 150;
        } else if (name.contains("địa chỉ")) {
            return 200;
        } else if (name.contains("số điện thoại") || name.contains("sđt")) {
            return 120;
        } else if (name.contains("ngày") || name.contains("thời gian")) {
            return 100;
        } else if (name.contains("giá") || name.contains("tiền")) {
            return 100;
        } else if (name.contains("ảnh") || name.contains("hình")) {
            return 60;
        } else if (name.contains("trạng thái") || name.contains("tình trạng")) {
            return 100;
        } else {
            return 80;
        }
    }
    
    /**
     * Get minimum width for column
     */
    private static int getMinimumColumnWidth(String columnName) {
        String name = columnName.toLowerCase();
        
        if (name.contains("stt")) {
            return 40;
        } else if (name.contains("ảnh")) {
            return 50;
        } else {
            return 60;
        }
    }
    
    /**
     * Get maximum width for column (0 = no limit)
     */
    private static int getMaximumColumnWidth(String columnName) {
        String name = columnName.toLowerCase();
        
        if (name.contains("stt")) {
            return 60;
        } else if (name.contains("ảnh")) {
            return 80;
        } else if (name.contains("mã")) {
            return 150;
        } else {
            return 0; // No maximum limit
        }
    }
    
    /**
     * Add resize listener for dynamic column adjustment
     */
    private static void addResizeListener(JTable table) {
        JScrollPane scrollPane = findScrollPane(table);
        if (scrollPane != null) {
            scrollPane.addComponentListener(new ComponentAdapter() {
                private Timer resizeTimer;
                
                @Override
                public void componentResized(ComponentEvent e) {
                    if (resizeTimer != null) {
                        resizeTimer.stop();
                    }
                    
                    resizeTimer = new Timer(100, evt -> {
                        adjustColumnsToFit(table);
                    });
                    resizeTimer.setRepeats(false);
                    resizeTimer.start();
                }
            });
        }
    }
    
    /**
     * Find the scroll pane containing the table
     */
    private static JScrollPane findScrollPane(JTable table) {
        Container parent = table.getParent();
        while (parent != null) {
            if (parent instanceof JScrollPane) {
                return (JScrollPane) parent;
            }
            parent = parent.getParent();
        }
        return null;
    }
    
    /**
     * Adjust columns to fit available space
     */
    private static void adjustColumnsToFit(JTable table) {
        JScrollPane scrollPane = findScrollPane(table);
        if (scrollPane == null) return;
        
        int availableWidth = scrollPane.getViewport().getWidth();
        int totalPreferredWidth = 0;
        
        TableColumnModel columnModel = table.getColumnModel();
        
        // Calculate total preferred width
        for (int i = 0; i < columnModel.getColumnCount(); i++) {
            totalPreferredWidth += columnModel.getColumn(i).getPreferredWidth();
        }
        
        // If total preferred width is less than available width, distribute extra space
        if (totalPreferredWidth < availableWidth) {
            int extraSpace = availableWidth - totalPreferredWidth;
            int extraPerColumn = extraSpace / columnModel.getColumnCount();
            
            for (int i = 0; i < columnModel.getColumnCount(); i++) {
                TableColumn column = columnModel.getColumn(i);
                int newWidth = column.getPreferredWidth() + extraPerColumn;
                column.setPreferredWidth(newWidth);
            }
        }
        
        table.revalidate();
        table.repaint();
    }
    
    /**
     * Optimize table for specific screen type
     */
    public static void optimizeForScreen(JTable table, String screenType) {
        optimizeTable(table);
        
        switch (screenType.toLowerCase()) {
            case "thanhtoan":
                optimizePaymentTable(table);
                break;
            case "sanpham":
                optimizeProductTable(table);
                break;
            case "khachhang":
                optimizeCustomerTable(table);
                break;
            case "nhanvien":
                optimizeEmployeeTable(table);
                break;
        }
    }
    
    /**
     * Specific optimizations for payment tables
     */
    private static void optimizePaymentTable(JTable table) {
        // Ensure money columns are right-aligned
        for (int i = 0; i < table.getColumnCount(); i++) {
            String columnName = table.getColumnName(i).toLowerCase();
            if (columnName.contains("giá") || columnName.contains("tiền")) {
                DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
                rightRenderer.setHorizontalAlignment(SwingConstants.RIGHT);
                table.getColumnModel().getColumn(i).setCellRenderer(rightRenderer);
            }
        }
    }
    
    /**
     * Specific optimizations for product tables
     */
    private static void optimizeProductTable(JTable table) {
        // Set row height for image display
        table.setRowHeight(60);
        
        // Center align certain columns
        for (int i = 0; i < table.getColumnCount(); i++) {
            String columnName = table.getColumnName(i).toLowerCase();
            if (columnName.contains("size") || columnName.contains("màu") || columnName.contains("số lượng")) {
                DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
                centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
                table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
            }
        }
    }
    
    /**
     * Specific optimizations for customer tables
     */
    private static void optimizeCustomerTable(JTable table) {
        // Standard optimizations already applied
    }
    
    /**
     * Specific optimizations for employee tables
     */
    private static void optimizeEmployeeTable(JTable table) {
        // Center align status columns
        for (int i = 0; i < table.getColumnCount(); i++) {
            String columnName = table.getColumnName(i).toLowerCase();
            if (columnName.contains("trạng thái") || columnName.contains("giới tính")) {
                DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
                centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
                table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
            }
        }
    }
}
