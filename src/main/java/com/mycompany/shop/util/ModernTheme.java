package com.mycompany.shop.util;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Modern Theme for Shop Application
 * Provides consistent colors, fonts, and styling across all components
 */
public class ModernTheme {

    // Color Palette - Modern Blue Theme
    public static final Color PRIMARY_COLOR = new Color(33, 150, 243); // Material Blue
    public static final Color PRIMARY_DARK = new Color(25, 118, 210); // Darker Blue
    public static final Color PRIMARY_LIGHT = new Color(144, 202, 249); // Light Blue

    public static final Color SECONDARY_COLOR = new Color(255, 193, 7); // Amber
    public static final Color SECONDARY_DARK = new Color(255, 160, 0); // Dark Amber

    public static final Color SUCCESS_COLOR = new Color(76, 175, 80); // Green
    public static final Color WARNING_COLOR = new Color(255, 152, 0); // Orange
    public static final Color ERROR_COLOR = new Color(244, 67, 54); // Red
    public static final Color INFO_COLOR = new Color(33, 150, 243); // Blue

    // Background Colors
    public static final Color BACKGROUND_COLOR = new Color(250, 250, 250); // Light Gray
    public static final Color BACKGROUND_LIGHT = new Color(248, 249, 250); // Very Light Gray
    public static final Color CARD_BACKGROUND = Color.WHITE;
    public static final Color SIDEBAR_BACKGROUND = new Color(245, 245, 245);
    public static final Color HEADER_BACKGROUND = PRIMARY_COLOR;

    // Text Colors
    public static final Color TEXT_PRIMARY = new Color(33, 33, 33); // Dark Gray
    public static final Color TEXT_SECONDARY = new Color(117, 117, 117); // Medium Gray
    public static final Color TEXT_ON_PRIMARY = Color.WHITE;
    public static final Color TEXT_DISABLED = new Color(189, 189, 189);

    // Border Colors
    public static final Color BORDER_COLOR = new Color(224, 224, 224);
    public static final Color BORDER_FOCUS = PRIMARY_COLOR;

    // Fonts
    public static final Font FONT_TITLE = new Font("Segoe UI", Font.BOLD, 24);
    public static final Font FONT_HEADING = new Font("Segoe UI", Font.BOLD, 18);
    public static final Font FONT_SUBHEADING = new Font("Segoe UI", Font.BOLD, 16);
    public static final Font FONT_BODY = new Font("Segoe UI", Font.PLAIN, 14);
    public static final Font FONT_SMALL = new Font("Segoe UI", Font.PLAIN, 12);
    public static final Font FONT_BUTTON = new Font("Segoe UI", Font.BOLD, 14);

    // Responsive Dimensions
    public static final int BORDER_RADIUS = 8;
    public static final int BUTTON_HEIGHT = 42;
    public static final int BUTTON_HEIGHT_SMALL = 36;
    public static final int BUTTON_HEIGHT_LARGE = 48;
    public static final int INPUT_HEIGHT = 38;
    public static final int INPUT_HEIGHT_SMALL = 32;
    public static final int INPUT_HEIGHT_LARGE = 44;
    public static final int CARD_PADDING = 20;
    public static final int CARD_PADDING_SMALL = 15;
    public static final int CARD_PADDING_LARGE = 25;
    public static final int COMPONENT_SPACING = 12;
    public static final int COMPONENT_SPACING_SMALL = 8;
    public static final int COMPONENT_SPACING_LARGE = 16;

    // Responsive breakpoints
    public static final int SCREEN_SMALL = 1024;
    public static final int SCREEN_MEDIUM = 1366;
    public static final int SCREEN_LARGE = 1920;

    // Button widths
    public static final int BUTTON_WIDTH_SMALL = 80;
    public static final int BUTTON_WIDTH_MEDIUM = 120;
    public static final int BUTTON_WIDTH_LARGE = 160;
    public static final int BUTTON_WIDTH_EXTRA_LARGE = 200;

    /**
     * Apply modern styling to JButton with responsive sizing
     */
    public static void styleButton(JButton button, ButtonType type) {
        styleButton(button, type, ButtonSize.MEDIUM);
    }

    /**
     * Apply modern styling to JButton with specific size
     */
    public static void styleButton(JButton button, ButtonType type, ButtonSize size) {
        button.setFont(FONT_BUTTON);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Set responsive dimensions
        int height = getButtonHeight(size);
        int width = getButtonWidth(button.getText(), size);
        button.setPreferredSize(new Dimension(width, height));
        button.setMinimumSize(new Dimension(width, height));

        // Apply color scheme
        switch (type) {
            case PRIMARY:
                button.setBackground(PRIMARY_COLOR);
                button.setForeground(TEXT_ON_PRIMARY);
                addHoverEffect(button, PRIMARY_COLOR, PRIMARY_DARK);
                break;
            case SECONDARY:
                button.setBackground(SECONDARY_COLOR);
                button.setForeground(TEXT_PRIMARY);
                addHoverEffect(button, SECONDARY_COLOR, SECONDARY_DARK);
                break;
            case SUCCESS:
                button.setBackground(SUCCESS_COLOR);
                button.setForeground(TEXT_ON_PRIMARY);
                addHoverEffect(button, SUCCESS_COLOR, SUCCESS_COLOR.darker());
                break;
            case WARNING:
                button.setBackground(WARNING_COLOR);
                button.setForeground(TEXT_ON_PRIMARY);
                addHoverEffect(button, WARNING_COLOR, WARNING_COLOR.darker());
                break;
            case DANGER:
                button.setBackground(ERROR_COLOR);
                button.setForeground(TEXT_ON_PRIMARY);
                addHoverEffect(button, ERROR_COLOR, ERROR_COLOR.darker());
                break;
            case OUTLINE:
                button.setBackground(Color.WHITE);
                button.setForeground(PRIMARY_COLOR);
                button.setBorder(BorderFactory.createLineBorder(PRIMARY_COLOR, 2));
                button.setBorderPainted(true);
                addOutlineHoverEffect(button);
                break;
        }
    }

    /**
     * Get button height based on size
     */
    private static int getButtonHeight(ButtonSize size) {
        switch (size) {
            case SMALL:
                return BUTTON_HEIGHT_SMALL;
            case LARGE:
                return BUTTON_HEIGHT_LARGE;
            default:
                return BUTTON_HEIGHT;
        }
    }

    /**
     * Get button width based on text and size
     */
    private static int getButtonWidth(String text, ButtonSize size) {
        if (text == null || text.trim().isEmpty()) {
            return BUTTON_WIDTH_MEDIUM;
        }

        int baseWidth;
        switch (size) {
            case SMALL:
                baseWidth = BUTTON_WIDTH_SMALL;
                break;
            case LARGE:
                baseWidth = BUTTON_WIDTH_LARGE;
                break;
            default:
                baseWidth = BUTTON_WIDTH_MEDIUM;
                break;
        }

        // Adjust width based on text length
        int textLength = text.length();
        if (textLength <= 4) {
            return baseWidth;
        } else if (textLength <= 8) {
            return baseWidth + 20;
        } else if (textLength <= 12) {
            return baseWidth + 40;
        } else {
            return baseWidth + 60;
        }
    }

    /**
     * Apply modern styling to JTextField with responsive sizing
     */
    public static void styleTextField(JTextField textField) {
        styleTextField(textField, ButtonSize.MEDIUM);
    }

    /**
     * Apply modern styling to JTextField with specific size
     */
    public static void styleTextField(JTextField textField, ButtonSize size) {
        textField.setFont(FONT_BODY);

        // Set responsive dimensions
        int height = getInputHeight(size);
        int currentWidth = textField.getPreferredSize().width;
        int minWidth = getMinInputWidth(size);
        int width = Math.max(currentWidth, minWidth);

        textField.setPreferredSize(new Dimension(width, height));
        textField.setMinimumSize(new Dimension(minWidth, height));

        // Set responsive padding
        int padding = getInputPadding(size);
        textField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_COLOR, 1),
                BorderFactory.createEmptyBorder(padding, padding + 4, padding, padding + 4)));

        // Add focus effect
        textField.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent evt) {
                textField.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(BORDER_FOCUS, 2),
                        BorderFactory.createEmptyBorder(padding - 1, padding + 3, padding - 1, padding + 3)));
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent evt) {
                textField.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(BORDER_COLOR, 1),
                        BorderFactory.createEmptyBorder(padding, padding + 4, padding, padding + 4)));
            }
        });
    }

    /**
     * Get input height based on size
     */
    private static int getInputHeight(ButtonSize size) {
        switch (size) {
            case SMALL:
                return INPUT_HEIGHT_SMALL;
            case LARGE:
                return INPUT_HEIGHT_LARGE;
            default:
                return INPUT_HEIGHT;
        }
    }

    /**
     * Get minimum input width based on size
     */
    private static int getMinInputWidth(ButtonSize size) {
        switch (size) {
            case SMALL:
                return 120;
            case LARGE:
                return 200;
            default:
                return 150;
        }
    }

    /**
     * Get input padding based on size
     */
    private static int getInputPadding(ButtonSize size) {
        switch (size) {
            case SMALL:
                return 6;
            case LARGE:
                return 10;
            default:
                return 8;
        }
    }

    /**
     * Apply modern styling to JComboBox
     */
    public static void styleComboBox(JComboBox<?> comboBox) {
        styleComboBox(comboBox, ButtonSize.MEDIUM);
    }

    /**
     * Apply modern styling to JComboBox with specific size
     */
    public static void styleComboBox(JComboBox<?> comboBox, ButtonSize size) {
        comboBox.setFont(FONT_BODY);

        // Set responsive dimensions
        int height = getInputHeight(size);
        int currentWidth = comboBox.getPreferredSize().width;
        int minWidth = getMinInputWidth(size);
        int width = Math.max(currentWidth, minWidth);

        comboBox.setPreferredSize(new Dimension(width, height));
        comboBox.setMinimumSize(new Dimension(minWidth, height));

        // Modern styling
        comboBox.setBackground(Color.WHITE);
        comboBox.setForeground(TEXT_PRIMARY);

        // Custom border with rounded corners
        comboBox.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_COLOR, 1),
                BorderFactory.createEmptyBorder(4, 8, 4, 8)));

        // Remove default UI and set custom renderer
        try {
            comboBox.setUI(new ModernComboBoxUI());
        } catch (Exception e) {
            // Fallback to basic styling if custom UI fails
            comboBox.setBorder(BorderFactory.createLineBorder(BORDER_COLOR, 1));
        }

        // Set custom renderer for better appearance
        comboBox.setRenderer(new ModernComboBoxRenderer());

        // Add focus effect
        comboBox.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent evt) {
                comboBox.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(BORDER_FOCUS, 2),
                        BorderFactory.createEmptyBorder(3, 7, 3, 7)));
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent evt) {
                comboBox.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(BORDER_COLOR, 1),
                        BorderFactory.createEmptyBorder(4, 8, 4, 8)));
            }
        });
    }

    /**
     * Apply modern styling to JTable
     */
    public static void styleTable(JTable table) {
        table.setFont(FONT_BODY);
        table.setRowHeight(40);
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 0));
        table.setSelectionBackground(PRIMARY_LIGHT);
        table.setSelectionForeground(TEXT_PRIMARY);
        table.setBackground(Color.WHITE);

        // Style header
        JTableHeader header = table.getTableHeader();
        header.setFont(FONT_SUBHEADING);
        header.setBackground(SIDEBAR_BACKGROUND);
        header.setForeground(TEXT_PRIMARY);
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, BORDER_COLOR));
        header.setPreferredSize(new Dimension(header.getPreferredSize().width, 45));

        // Alternate row colors
        table.setDefaultRenderer(Object.class, new AlternatingRowRenderer());
    }

    /**
     * Create a modern card panel
     */
    public static JPanel createCard() {
        JPanel card = new JPanel();
        card.setBackground(CARD_BACKGROUND);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_COLOR, 1),
                BorderFactory.createEmptyBorder(CARD_PADDING, CARD_PADDING, CARD_PADDING, CARD_PADDING)));
        return card;
    }

    /**
     * Create a section title label
     */
    public static JLabel createSectionTitle(String text) {
        JLabel label = new JLabel(text);
        label.setFont(FONT_HEADING);
        label.setForeground(TEXT_PRIMARY);
        return label;
    }

    /**
     * Create a subtitle label
     */
    public static JLabel createSubtitle(String text) {
        JLabel label = new JLabel(text);
        label.setFont(FONT_SUBHEADING);
        label.setForeground(TEXT_SECONDARY);
        return label;
    }

    // Helper methods
    private static void addHoverEffect(JButton button, Color normalColor, Color hoverColor) {
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(hoverColor);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(normalColor);
            }
        });
    }

    private static void addOutlineHoverEffect(JButton button) {
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(PRIMARY_LIGHT);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(Color.WHITE);
            }
        });
    }

    // Custom table cell renderer for alternating rows
    private static class AlternatingRowRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                boolean hasFocus, int row, int column) {
            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            if (!isSelected) {
                if (row % 2 == 0) {
                    c.setBackground(Color.WHITE);
                } else {
                    c.setBackground(new Color(248, 249, 250));
                }
            }

            setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
            return c;
        }
    }

    // Button types enum
    public enum ButtonType {
        PRIMARY, SECONDARY, SUCCESS, WARNING, DANGER, OUTLINE
    }

    // Button sizes enum
    public enum ButtonSize {
        SMALL, MEDIUM, LARGE
    }
}
