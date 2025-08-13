package com.mycompany.shop.util;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.HashMap;
import java.util.Map;

/**
 * Specialized manager for table responsive behavior
 */
public class TableResponsiveManager {

    private static final Map<JTable, Timer> resizeTimers = new HashMap<>();
    private static final int RESIZE_DELAY = 300; // ms - increased to reduce flickering

    /**
     * Make table fully responsive with proper column management
     */
    public static void makeTableResponsive(JTable table) {
        if (table == null)
            return;

        // Setup responsive scroll pane
        setupResponsiveScrollPane(table);

        // Setup responsive columns
        setupResponsiveColumns(table);

        // Add resize listener with debouncing
        addDebouncedResizeListener(table);

        // Initial adjustment
        adjustTableForCurrentSize(table);
    }

    /**
     * Setup responsive scroll pane
     */
    private static void setupResponsiveScrollPane(JTable table) {
        Container parent = table.getParent();
        if (parent instanceof JViewport) {
            JViewport viewport = (JViewport) parent;
            Container scrollPane = viewport.getParent();
            if (scrollPane instanceof JScrollPane) {
                JScrollPane sp = (JScrollPane) scrollPane;

                // Remove fixed sizes
                sp.setPreferredSize(null);
                sp.setMinimumSize(new Dimension(400, 200));
                sp.setMaximumSize(null);

                // Enable proper scrolling
                sp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
                sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

                // Fill viewport height
                table.setFillsViewportHeight(true);

                // Optimize viewport
                viewport.setOpaque(true);
                viewport.setBackground(table.getBackground());
            }
        }
    }

    /**
     * Setup responsive columns with intelligent sizing
     */
    private static void setupResponsiveColumns(JTable table) {
        TableColumnModel columnModel = table.getColumnModel();
        int columnCount = columnModel.getColumnCount();

        if (columnCount == 0)
            return;

        // Set intelligent column widths based on content type
        for (int i = 0; i < columnCount; i++) {
            TableColumn column = columnModel.getColumn(i);
            String columnName = table.getColumnName(i).toLowerCase();

            // Set preferred widths based on column type
            if (columnName.contains("ảnh") || columnName.contains("image")) {
                column.setPreferredWidth(80);
                column.setMinWidth(60);
                column.setMaxWidth(100);
            } else if (columnName.contains("mã") || columnName.contains("id")) {
                column.setPreferredWidth(100);
                column.setMinWidth(80);
                column.setMaxWidth(120);
            } else if (columnName.contains("tên") || columnName.contains("name")) {
                column.setPreferredWidth(200);
                column.setMinWidth(150);
                // No max width for name columns
            } else if (columnName.contains("giá") || columnName.contains("price") || columnName.contains("tiền")) {
                column.setPreferredWidth(120);
                column.setMinWidth(100);
                column.setMaxWidth(150);
            } else if (columnName.contains("số lượng") || columnName.contains("quantity")) {
                column.setPreferredWidth(80);
                column.setMinWidth(60);
                column.setMaxWidth(100);
            } else if (columnName.contains("size") || columnName.contains("kích thước")) {
                column.setPreferredWidth(80);
                column.setMinWidth(60);
                column.setMaxWidth(100);
            } else if (columnName.contains("màu") || columnName.contains("color")) {
                column.setPreferredWidth(100);
                column.setMinWidth(80);
                column.setMaxWidth(120);
            } else {
                // Default for other columns
                column.setPreferredWidth(120);
                column.setMinWidth(80);
            }
        }

        // Set auto-resize mode based on screen size
        adjustAutoResizeMode(table);
    }

    /**
     * Adjust auto-resize mode based on available space
     */
    private static void adjustAutoResizeMode(JTable table) {
        Container parent = table.getParent();
        if (parent instanceof JViewport) {
            JViewport viewport = (JViewport) parent;
            int availableWidth = viewport.getWidth();
            int preferredWidth = table.getPreferredSize().width;

            if (availableWidth > preferredWidth + 50) {
                // Enough space - resize all columns proportionally
                table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
            } else {
                // Limited space - allow horizontal scrolling
                table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            }
        } else {
            // Default behavior
            table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        }
    }

    /**
     * Add debounced resize listener
     */
    private static void addDebouncedResizeListener(JTable table) {
        Container parent = table.getParent();
        if (parent instanceof JViewport) {
            JViewport viewport = (JViewport) parent;
            Container scrollPane = viewport.getParent();
            if (scrollPane instanceof JScrollPane) {
                scrollPane.addComponentListener(new ComponentAdapter() {
                    @Override
                    public void componentResized(ComponentEvent e) {
                        debouncedResize(table);
                    }
                });
            }
        }
    }

    /**
     * Debounced resize to prevent performance issues
     */
    private static void debouncedResize(JTable table) {
        Timer existingTimer = resizeTimers.get(table);
        if (existingTimer != null && existingTimer.isRunning()) {
            existingTimer.stop();
        }

        Timer newTimer = new Timer(RESIZE_DELAY, e -> {
            SwingUtilities.invokeLater(() -> adjustTableForCurrentSize(table));
        });
        newTimer.setRepeats(false);
        resizeTimers.put(table, newTimer);
        newTimer.start();
    }

    /**
     * Adjust table for current size with anti-flicker measures
     */
    private static void adjustTableForCurrentSize(JTable table) {
        if (!table.isDisplayable() || !table.isShowing())
            return;

        // Use double buffering to prevent flicker
        SwingUtilities.invokeLater(() -> {
            try {
                // Suspend layout during updates
                Container parent = table.getParent();
                if (parent != null) {
                    parent.setVisible(false);
                }

                // Adjust auto-resize mode
                adjustAutoResizeMode(table);

                // Adjust row height based on screen size
                adjustRowHeight(table);

                // Batch revalidation
                table.revalidate();

            } finally {
                // Re-enable layout
                Container parent = table.getParent();
                if (parent != null) {
                    parent.setVisible(true);
                }
            }
        });
    }

    /**
     * Adjust row height based on screen size and content
     */
    private static void adjustRowHeight(JTable table) {
        Container parent = table.getParent();
        if (parent instanceof JViewport) {
            JViewport viewport = (JViewport) parent;
            int availableHeight = viewport.getHeight();
            int rowCount = table.getRowCount();

            if (rowCount > 0) {
                // Check if table has image columns
                boolean hasImageColumn = false;
                for (int i = 0; i < table.getColumnCount(); i++) {
                    String columnName = table.getColumnName(i).toLowerCase();
                    if (columnName.contains("ảnh") || columnName.contains("image")) {
                        hasImageColumn = true;
                        break;
                    }
                }

                if (hasImageColumn) {
                    // Larger row height for image columns
                    table.setRowHeight(Math.max(60, Math.min(80, availableHeight / Math.max(rowCount, 5))));
                } else {
                    // Standard row height
                    table.setRowHeight(Math.max(25, Math.min(35, availableHeight / Math.max(rowCount, 10))));
                }
            }
        }
    }

    /**
     * Optimize table for product display specifically
     */
    public static void optimizeProductTable(JTable table) {
        makeTableResponsive(table);

        // Product-specific optimizations
        table.setRowHeight(60); // Fixed height for product images

        // Setup center alignment for specific columns
        TableColumnModel columnModel = table.getColumnModel();
        for (int i = 0; i < columnModel.getColumnCount(); i++) {
            String columnName = table.getColumnName(i).toLowerCase();
            if (columnName.contains("size") || columnName.contains("màu") ||
                    columnName.contains("số lượng") || columnName.contains("kích thước")) {
                DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
                centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
                columnModel.getColumn(i).setCellRenderer(centerRenderer);
            }
        }

        // Enable row selection
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setRowSelectionAllowed(true);
        table.setColumnSelectionAllowed(false);
    }

    /**
     * Force refresh table layout
     */
    public static void refreshTableLayout(JTable table) {
        SwingUtilities.invokeLater(() -> {
            adjustTableForCurrentSize(table);
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
    }

    /**
     * Get optimal table size for container
     */
    public static Dimension getOptimalTableSize(JTable table, Container container) {
        if (container == null) {
            return new Dimension(800, 400);
        }

        Dimension containerSize = container.getSize();
        int width = Math.max(400, containerSize.width - 50);
        int height = Math.max(200, containerSize.height - 100);

        return new Dimension(width, height);
    }

    /**
     * Apply responsive behavior to all tables in a container
     */
    public static void makeAllTablesResponsive(Container container) {
        Component[] components = container.getComponents();
        for (Component component : components) {
            if (component instanceof JTable) {
                makeTableResponsive((JTable) component);
            } else if (component instanceof JScrollPane) {
                JScrollPane scrollPane = (JScrollPane) component;
                Component view = scrollPane.getViewport().getView();
                if (view instanceof JTable) {
                    makeTableResponsive((JTable) view);
                }
            } else if (component instanceof Container) {
                makeAllTablesResponsive((Container) component);
            }
        }
    }
}
