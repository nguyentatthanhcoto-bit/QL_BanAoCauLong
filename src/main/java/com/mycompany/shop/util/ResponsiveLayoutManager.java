package com.mycompany.shop.util;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.HashMap;
import java.util.Map;

/**
 * Responsive Layout Manager for adaptive UI components
 */
public class ResponsiveLayoutManager {

    private static final int BREAKPOINT_SMALL = 1024;
    private static final int BREAKPOINT_MEDIUM = 1366;
    private static final int BREAKPOINT_LARGE = 1920;

    /**
     * Screen size categories
     */
    public enum ScreenSize {
        SMALL, MEDIUM, LARGE, EXTRA_LARGE
    }

    /**
     * Get current screen size category based on component width
     */
    public static ScreenSize getScreenSize(Component component) {
        int width = component.getWidth();
        if (component.getParent() != null) {
            width = component.getParent().getWidth();
        }

        if (width < BREAKPOINT_SMALL) {
            return ScreenSize.SMALL;
        } else if (width < BREAKPOINT_MEDIUM) {
            return ScreenSize.MEDIUM;
        } else if (width < BREAKPOINT_LARGE) {
            return ScreenSize.LARGE;
        } else {
            return ScreenSize.EXTRA_LARGE;
        }
    }

    // Throttle resize events to prevent lag
    private static final Map<JPanel, Timer> resizeTimers = new HashMap<>();

    /**
     * Apply responsive layout to a panel with throttling
     */
    public static void makeResponsive(JPanel panel) {
        panel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                throttleResize(panel);
            }
        });

        // Initial adjustment
        adjustLayoutForSize(panel);
    }

    /**
     * Throttle resize events to prevent lag during window resizing
     */
    private static void throttleResize(JPanel panel) {
        Timer existingTimer = resizeTimers.get(panel);
        if (existingTimer != null) {
            existingTimer.stop();
        }

        Timer timer = new Timer(50, e -> {
            adjustLayoutForSize(panel);
            resizeTimers.remove(panel);
        });
        timer.setRepeats(false);
        resizeTimers.put(panel, timer);
        timer.start();
    }

    /**
     * Adjust layout based on current size with optimized repainting
     */
    private static void adjustLayoutForSize(JPanel panel) {
        if (!panel.isDisplayable())
            return;

        ScreenSize screenSize = getScreenSize(panel);

        // Suspend repainting during bulk updates
        panel.setVisible(false);
        try {
            adjustComponentsForScreenSize(panel, screenSize);
            panel.revalidate();
        } finally {
            panel.setVisible(true);
            panel.repaint();
        }
    }

    /**
     * Adjust all components in panel for screen size
     */
    private static void adjustComponentsForScreenSize(Container container, ScreenSize screenSize) {
        for (Component component : container.getComponents()) {
            if (component instanceof JButton) {
                adjustButtonForScreenSize((JButton) component, screenSize);
            } else if (component instanceof JTextField) {
                adjustTextFieldForScreenSize((JTextField) component, screenSize);
            } else if (component instanceof JTable) {
                adjustTableForScreenSize((JTable) component, screenSize);
            } else if (component instanceof Container) {
                adjustComponentsForScreenSize((Container) component, screenSize);
            }
        }
    }

    /**
     * Adjust button size based on screen size
     */
    private static void adjustButtonForScreenSize(JButton button, ScreenSize screenSize) {
        ModernTheme.ButtonSize buttonSize;

        switch (screenSize) {
            case SMALL:
                buttonSize = ModernTheme.ButtonSize.SMALL;
                break;
            case LARGE:
            case EXTRA_LARGE:
                buttonSize = ModernTheme.ButtonSize.LARGE;
                break;
            default:
                buttonSize = ModernTheme.ButtonSize.MEDIUM;
                break;
        }

        // Re-apply styling with new size
        ModernTheme.ButtonType buttonType = getButtonType(button);
        ModernTheme.styleButton(button, buttonType, buttonSize);
    }

    /**
     * Adjust text field size based on screen size
     */
    private static void adjustTextFieldForScreenSize(JTextField textField, ScreenSize screenSize) {
        ModernTheme.ButtonSize inputSize;

        switch (screenSize) {
            case SMALL:
                inputSize = ModernTheme.ButtonSize.SMALL;
                break;
            case LARGE:
            case EXTRA_LARGE:
                inputSize = ModernTheme.ButtonSize.LARGE;
                break;
            default:
                inputSize = ModernTheme.ButtonSize.MEDIUM;
                break;
        }

        ModernTheme.styleTextField(textField, inputSize);
    }

    /**
     * Adjust table for screen size
     */
    private static void adjustTableForScreenSize(JTable table, ScreenSize screenSize) {
        int rowHeight;
        Font font;

        switch (screenSize) {
            case SMALL:
                rowHeight = 35;
                font = ModernTheme.FONT_SMALL;
                break;
            case LARGE:
            case EXTRA_LARGE:
                rowHeight = 45;
                font = ModernTheme.FONT_BODY;
                break;
            default:
                rowHeight = 40;
                font = ModernTheme.FONT_BODY;
                break;
        }

        table.setRowHeight(rowHeight);
        table.setFont(font);

        // Adjust column widths
        adjustTableColumnWidths(table, screenSize);
    }

    /**
     * Adjust table column widths based on screen size
     */
    private static void adjustTableColumnWidths(JTable table, ScreenSize screenSize) {
        if (table.getColumnModel().getColumnCount() == 0)
            return;

        int totalWidth = table.getParent().getWidth();
        if (totalWidth <= 0)
            return;

        // Calculate column widths based on screen size
        double[] columnRatios = getColumnRatios(table.getColumnModel().getColumnCount(), screenSize);

        for (int i = 0; i < table.getColumnModel().getColumnCount() && i < columnRatios.length; i++) {
            int columnWidth = (int) (totalWidth * columnRatios[i]);
            table.getColumnModel().getColumn(i).setPreferredWidth(columnWidth);
        }
    }

    /**
     * Get column width ratios based on screen size
     */
    private static double[] getColumnRatios(int columnCount, ScreenSize screenSize) {
        switch (screenSize) {
            case SMALL:
                // Smaller screens - hide some columns or make them narrower
                return getSmallScreenColumnRatios(columnCount);
            case LARGE:
            case EXTRA_LARGE:
                // Larger screens - more space for all columns
                return getLargeScreenColumnRatios(columnCount);
            default:
                // Medium screens - balanced approach
                return getMediumScreenColumnRatios(columnCount);
        }
    }

    private static double[] getSmallScreenColumnRatios(int columnCount) {
        // For small screens, prioritize important columns
        switch (columnCount) {
            case 3:
                return new double[] { 0.2, 0.5, 0.3 };
            case 4:
                return new double[] { 0.15, 0.35, 0.25, 0.25 };
            case 5:
                return new double[] { 0.1, 0.3, 0.25, 0.2, 0.15 };
            case 6:
                return new double[] { 0.1, 0.25, 0.2, 0.2, 0.15, 0.1 };
            case 7:
                return new double[] { 0.08, 0.22, 0.18, 0.18, 0.14, 0.12, 0.08 };
            default:
                double ratio = 1.0 / columnCount;
                double[] ratios = new double[columnCount];
                for (int i = 0; i < columnCount; i++) {
                    ratios[i] = ratio;
                }
                return ratios;
        }
    }

    private static double[] getMediumScreenColumnRatios(int columnCount) {
        // For medium screens, balanced distribution
        switch (columnCount) {
            case 3:
                return new double[] { 0.25, 0.45, 0.3 };
            case 4:
                return new double[] { 0.2, 0.35, 0.25, 0.2 };
            case 5:
                return new double[] { 0.15, 0.25, 0.25, 0.2, 0.15 };
            case 6:
                return new double[] { 0.12, 0.22, 0.2, 0.2, 0.16, 0.1 };
            case 7:
                return new double[] { 0.1, 0.2, 0.18, 0.18, 0.14, 0.12, 0.08 };
            default:
                double ratio = 1.0 / columnCount;
                double[] ratios = new double[columnCount];
                for (int i = 0; i < columnCount; i++) {
                    ratios[i] = ratio;
                }
                return ratios;
        }
    }

    private static double[] getLargeScreenColumnRatios(int columnCount) {
        // For large screens, more even distribution
        switch (columnCount) {
            case 3:
                return new double[] { 0.3, 0.4, 0.3 };
            case 4:
                return new double[] { 0.25, 0.3, 0.25, 0.2 };
            case 5:
                return new double[] { 0.2, 0.25, 0.25, 0.2, 0.1 };
            case 6:
                return new double[] { 0.15, 0.2, 0.2, 0.2, 0.15, 0.1 };
            case 7:
                return new double[] { 0.12, 0.18, 0.16, 0.16, 0.14, 0.12, 0.12 };
            default:
                double ratio = 1.0 / columnCount;
                double[] ratios = new double[columnCount];
                for (int i = 0; i < columnCount; i++) {
                    ratios[i] = ratio;
                }
                return ratios;
        }
    }

    /**
     * Determine button type from its appearance
     */
    private static ModernTheme.ButtonType getButtonType(JButton button) {
        Color bg = button.getBackground();

        if (bg.equals(ModernTheme.PRIMARY_COLOR)) {
            return ModernTheme.ButtonType.PRIMARY;
        } else if (bg.equals(ModernTheme.SUCCESS_COLOR)) {
            return ModernTheme.ButtonType.SUCCESS;
        } else if (bg.equals(ModernTheme.ERROR_COLOR)) {
            return ModernTheme.ButtonType.DANGER;
        } else if (bg.equals(ModernTheme.WARNING_COLOR)) {
            return ModernTheme.ButtonType.WARNING;
        } else if (bg.equals(ModernTheme.SECONDARY_COLOR)) {
            return ModernTheme.ButtonType.SECONDARY;
        } else {
            return ModernTheme.ButtonType.OUTLINE;
        }
    }

    /**
     * Create responsive grid layout
     */
    public static GridBagLayout createResponsiveGridLayout() {
        return new GridBagLayout();
    }

    /**
     * Create responsive grid constraints
     */
    public static GridBagConstraints createResponsiveConstraints(int gridx, int gridy, double weightx, double weighty) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = gridx;
        gbc.gridy = gridy;
        gbc.weightx = weightx;
        gbc.weighty = weighty;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(ModernTheme.COMPONENT_SPACING, ModernTheme.COMPONENT_SPACING,
                ModernTheme.COMPONENT_SPACING, ModernTheme.COMPONENT_SPACING);
        return gbc;
    }

    /**
     * Make panel responsive for MainScreen container with optimized performance
     */
    public static void makeResponsiveForMainScreen(JPanel panel, Container parentContainer) {
        // Add throttled resize listener for parent container changes
        if (parentContainer != null) {
            parentContainer.addComponentListener(new ComponentAdapter() {
                @Override
                public void componentResized(ComponentEvent e) {
                    throttleMainScreenResize(panel, parentContainer);
                }
            });
        }

        // Also make the panel itself responsive
        makeResponsive(panel);
    }

    /**
     * Throttle MainScreen resize events
     */
    private static void throttleMainScreenResize(JPanel panel, Container parentContainer) {
        Timer existingTimer = resizeTimers.get(panel);
        if (existingTimer != null) {
            existingTimer.stop();
        }

        Timer timer = new Timer(100, e -> {
            if (parentContainer.isDisplayable()) {
                // Update panel size to match parent
                Dimension parentSize = parentContainer.getSize();
                panel.setPreferredSize(parentSize);
                panel.setSize(parentSize);

                // Adjust components for new size
                adjustLayoutForSize(panel);
            }
            resizeTimers.remove(panel);
        });
        timer.setRepeats(false);
        resizeTimers.put(panel, timer);
        timer.start();
    }

    /**
     * Optimize panel for container embedding
     */
    public static void optimizeForContainer(JPanel panel) {
        // Remove fixed sizes that might prevent proper resizing
        panel.setPreferredSize(null);
        panel.setMaximumSize(null);

        // Ensure proper layout behavior
        if (panel.getLayout() == null) {
            panel.setLayout(new BorderLayout());
        }

        // Make all child components flexible
        makeChildComponentsFlexible(panel);
    }

    /**
     * Make child components flexible for resizing
     */
    private static void makeChildComponentsFlexible(Container container) {
        for (Component component : container.getComponents()) {
            if (component instanceof JScrollPane) {
                JScrollPane scrollPane = (JScrollPane) component;
                scrollPane.setPreferredSize(null);
                scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
                scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            } else if (component instanceof Container) {
                makeChildComponentsFlexible((Container) component);
            }
        }
    }
}
