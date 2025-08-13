package com.mycompany.shop.util;

import javax.swing.*;
import java.awt.*;

/**
 * Utility class to optimize form layouts for better responsive behavior
 */
public class FormLayoutOptimizer {

    /**
     * Make form responsive with proper layout management and anti-flicker measures
     */
    public static void makeFormResponsive(JPanel form) {
        optimizeGroupLayout(form);
    }

    /**
     * Convert fixed-size GroupLayout to flexible layout with anti-flicker measures
     */
    public static void optimizeGroupLayout(JPanel panel) {
        if (panel == null)
            return;

        // Use double buffering to prevent flicker during layout changes
        SwingUtilities.invokeLater(() -> {
            // Enable double buffering
            panel.setDoubleBuffered(true);

            // Remove fixed preferred sizes
            panel.setPreferredSize(null);
            panel.setMaximumSize(null);
            panel.setMinimumSize(new Dimension(800, 600));

            // Make all child components flexible
            optimizeChildComponents(panel);
        });
    }

    /**
     * Optimize child components for flexibility with anti-flicker measures
     */
    private static void optimizeChildComponents(Container container) {
        // Batch process components to reduce repaints
        container.setVisible(false);
        try {
            for (Component comp : container.getComponents()) {
                // Enable double buffering for components
                if (comp instanceof JComponent) {
                    ((JComponent) comp).setDoubleBuffered(true);
                }

                if (comp instanceof JScrollPane) {
                    optimizeScrollPane((JScrollPane) comp);
                } else if (comp instanceof JTable) {
                    optimizeTable((JTable) comp);
                } else if (comp instanceof JTabbedPane) {
                    optimizeTabbedPane((JTabbedPane) comp);
                } else if (comp instanceof Container) {
                    optimizeChildComponents((Container) comp);
                }

                // Remove fixed sizes for flexibility
                if (!(comp instanceof JButton)) {
                    comp.setPreferredSize(null);
                    comp.setMaximumSize(null);
                }
            }
        } finally {
            container.setVisible(true);
        }
    }

    /**
     * Optimize JScrollPane for responsive behavior
     */
    private static void optimizeScrollPane(JScrollPane scrollPane) {
        scrollPane.setPreferredSize(null);
        scrollPane.setMaximumSize(null);
        scrollPane.setMinimumSize(new Dimension(300, 200));

        // Enable horizontal scrolling if needed
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
    }

    /**
     * Optimize JTable for responsive behavior
     */
    private static void optimizeTable(JTable table) {
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        table.setFillsViewportHeight(true);

        // Enable column reordering and resizing
        table.getTableHeader().setReorderingAllowed(true);
        table.getTableHeader().setResizingAllowed(true);
    }

    /**
     * Optimize JTabbedPane for responsive behavior
     */
    private static void optimizeTabbedPane(JTabbedPane tabbedPane) {
        tabbedPane.setPreferredSize(null);
        tabbedPane.setMaximumSize(null);
        tabbedPane.setMinimumSize(new Dimension(400, 300));

        // Optimize all tab components
        for (int i = 0; i < tabbedPane.getTabCount(); i++) {
            Component tabComponent = tabbedPane.getComponentAt(i);
            if (tabComponent instanceof Container) {
                optimizeChildComponents((Container) tabComponent);
            }
        }
    }
}
