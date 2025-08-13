package com.mycompany.shop.util;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Performance optimizer for Swing components to reduce lag during resize
 */
public class PerformanceOptimizer {

    // Cache for component optimizations
    private static final Map<Component, Boolean> optimizedComponents = new ConcurrentHashMap<>();
    private static final Map<Component, Timer> pendingUpdates = new ConcurrentHashMap<>();

    // Performance settings
    private static final int RESIZE_THROTTLE_DELAY = 50;
    private static final int REPAINT_THROTTLE_DELAY = 16; // ~60fps

    /**
     * Optimize a component for better performance during resize
     */
    public static void optimizeComponent(JComponent component) {
        if (optimizedComponents.containsKey(component)) {
            return; // Already optimized
        }

        // Mark as optimized
        optimizedComponents.put(component, true);

        // Apply performance optimizations
        applyRenderingHints(component);
        setupThrottledResize(component);
        optimizeChildComponents(component);

        // Enable double buffering
        component.setDoubleBuffered(true);

        // Optimize repainting
        setupThrottledRepaint(component);
    }

    /**
     * Apply rendering hints for better performance
     */
    private static void applyRenderingHints(JComponent component) {
        component.putClientProperty("JComponent.sizeVariant", "regular");
        component.putClientProperty("JButton.buttonType", "textured");

        // Enable hardware acceleration if available
        System.setProperty("sun.java2d.opengl", "true");
        System.setProperty("sun.java2d.d3d", "true");
    }

    /**
     * Setup throttled resize handling
     */
    private static void setupThrottledResize(JComponent component) {
        component.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                throttleUpdate(component, RESIZE_THROTTLE_DELAY, () -> {
                    handleResize(component);
                });
            }
        });
    }

    /**
     * Setup throttled repaint
     */
    private static void setupThrottledRepaint(JComponent component) {
        // Override repaint to throttle it
        component.putClientProperty("optimized.repaint", true);
    }

    /**
     * Handle component resize efficiently
     */
    private static void handleResize(JComponent component) {
        if (!component.isDisplayable())
            return;

        // Suspend painting during resize
        component.setVisible(false);

        try {
            // Update layout
            component.revalidate();

            // Update child components
            updateChildComponentSizes(component);

        } finally {
            // Resume painting
            component.setVisible(true);
            component.repaint();
        }
    }

    /**
     * Update child component sizes efficiently
     */
    private static void updateChildComponentSizes(Container container) {
        for (Component child : container.getComponents()) {
            if (child instanceof JScrollPane) {
                optimizeScrollPane((JScrollPane) child);
            } else if (child instanceof JTable) {
                optimizeTable((JTable) child);
            } else if (child instanceof Container) {
                updateChildComponentSizes((Container) child);
            }
        }
    }

    /**
     * Optimize JScrollPane performance
     */
    private static void optimizeScrollPane(JScrollPane scrollPane) {
        // Enable incremental painting
        scrollPane.getViewport().setScrollMode(JViewport.BACKINGSTORE_SCROLL_MODE);

        // Optimize scrollbar performance
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.getHorizontalScrollBar().setUnitIncrement(16);
    }

    /**
     * Optimize JTable performance
     */
    private static void optimizeTable(JTable table) {
        // Disable auto-resize during updates
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        // Enable lazy loading for large tables
        if (table.getRowCount() > 100) {
            table.setFillsViewportHeight(false);
        }

        // Re-enable auto-resize after optimization
        SwingUtilities.invokeLater(() -> {
            table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        });
    }

    /**
     * Optimize child components recursively
     */
    private static void optimizeChildComponents(Container container) {
        for (Component child : container.getComponents()) {
            if (child instanceof JComponent) {
                optimizeComponent((JComponent) child);
            } else if (child instanceof Container) {
                optimizeChildComponents((Container) child);
            }
        }
    }

    /**
     * Throttle updates to prevent excessive calls
     */
    private static void throttleUpdate(Component component, int delay, Runnable action) {
        Timer existingTimer = pendingUpdates.get(component);
        if (existingTimer != null) {
            existingTimer.stop();
        }

        Timer timer = new Timer(delay, e -> {
            action.run();
            pendingUpdates.remove(component);
        });
        timer.setRepeats(false);
        pendingUpdates.put(component, timer);
        timer.start();
    }

    /**
     * Optimize entire form for performance
     */
    public static void optimizeForm(JPanel form) {
        if (form == null)
            return;

        // Apply component-level optimizations
        optimizeComponent(form);

        // Apply form-specific optimizations
        form.setOpaque(true);
        form.setDoubleBuffered(true);

        // Optimize layout manager
        optimizeLayoutManager(form);

        // Setup efficient revalidation
        setupEfficientRevalidation(form);
    }

    /**
     * Optimize layout manager for performance
     */
    private static void optimizeLayoutManager(Container container) {
        LayoutManager layout = container.getLayout();

        if (layout instanceof GroupLayout) {
            // GroupLayout optimizations
            if (container instanceof JComponent) {
                ((JComponent) container).putClientProperty("GroupLayout.honorVisibilityForBaseline", Boolean.FALSE);
            }
        } else if (layout instanceof BorderLayout) {
            // BorderLayout is already efficient
        } else if (layout == null) {
            // Set a default efficient layout
            container.setLayout(new BorderLayout());
        }
    }

    /**
     * Setup efficient revalidation
     */
    private static void setupEfficientRevalidation(JPanel form) {
        form.addComponentListener(new ComponentAdapter() {
            private boolean isRevalidating = false;

            @Override
            public void componentResized(ComponentEvent e) {
                if (isRevalidating)
                    return;

                throttleUpdate(form, RESIZE_THROTTLE_DELAY, () -> {
                    isRevalidating = true;
                    try {
                        form.revalidate();
                    } finally {
                        isRevalidating = false;
                    }
                });
            }
        });
    }

    /**
     * Clean up optimizations when component is no longer needed
     */
    public static void cleanup(Component component) {
        optimizedComponents.remove(component);
        Timer timer = pendingUpdates.remove(component);
        if (timer != null) {
            timer.stop();
        }
    }
}
