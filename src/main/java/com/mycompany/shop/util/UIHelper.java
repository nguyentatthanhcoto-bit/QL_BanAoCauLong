package com.mycompany.shop.util;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

/**
 * UI Helper class for applying modern theme to components
 */
public class UIHelper {

    /**
     * Apply modern theme to a panel (main container)
     */
    public static void applyPanelTheme(JPanel panel) {
        panel.setBackground(ModernTheme.BACKGROUND_COLOR);
    }

    /**
     * Create a modern form panel with title
     */
    public static JPanel createFormPanel(String title) {
        JPanel panel = ModernTheme.createCard();
        panel.setLayout(new GridBagLayout());

        if (title != null && !title.isEmpty()) {
            TitledBorder border = BorderFactory.createTitledBorder(
                    BorderFactory.createLineBorder(ModernTheme.BORDER_COLOR),
                    title,
                    TitledBorder.LEFT,
                    TitledBorder.TOP,
                    ModernTheme.FONT_SUBHEADING,
                    ModernTheme.TEXT_PRIMARY);
            panel.setBorder(BorderFactory.createCompoundBorder(
                    border,
                    BorderFactory.createEmptyBorder(10, 15, 15, 15)));
        }

        return panel;
    }

    /**
     * Create a modern table panel with title
     */
    public static JPanel createTablePanel(String title, JTable table) {
        JPanel panel = ModernTheme.createCard();
        panel.setLayout(new BorderLayout());

        // Add title
        if (title != null && !title.isEmpty()) {
            JLabel titleLabel = ModernTheme.createSectionTitle(title);
            titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
            panel.add(titleLabel, BorderLayout.NORTH);
        }

        // Style and add table
        ModernTheme.styleTable(table);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(ModernTheme.BORDER_COLOR));
        scrollPane.getViewport().setBackground(Color.WHITE);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    /**
     * Create a search panel with text field and button
     */
    public static JPanel createSearchPanel(JTextField searchField, JButton searchButton) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        panel.setBackground(ModernTheme.CARD_BACKGROUND);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(ModernTheme.BORDER_COLOR),
                BorderFactory.createEmptyBorder(10, 15, 10, 15)));

        JLabel searchLabel = new JLabel("Tìm kiếm:");
        searchLabel.setFont(ModernTheme.FONT_BODY);
        searchLabel.setForeground(ModernTheme.TEXT_PRIMARY);

        ModernTheme.styleTextField(searchField);
        searchField.setPreferredSize(new Dimension(250, ModernTheme.INPUT_HEIGHT));

        ModernTheme.styleButton(searchButton, ModernTheme.ButtonType.PRIMARY);

        panel.add(searchLabel);
        panel.add(searchField);
        panel.add(searchButton);

        return panel;
    }

    /**
     * Create a button panel with consistent spacing
     */
    public static JPanel createButtonPanel(JButton... buttons) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        panel.setBackground(ModernTheme.CARD_BACKGROUND);

        for (JButton button : buttons) {
            panel.add(button);
        }

        return panel;
    }

    /**
     * Create a form row with label and component
     */
    public static void addFormRow(JPanel parent, GridBagConstraints gbc, String labelText, JComponent component) {
        // Label
        JLabel label = new JLabel(labelText);
        label.setFont(ModernTheme.FONT_BODY);
        label.setForeground(ModernTheme.TEXT_PRIMARY);

        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 0, 5, 15);
        parent.add(label, gbc);

        // Component
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(5, 0, 5, 0);
        parent.add(component, gbc);

        gbc.gridy++;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE;
    }

    /**
     * Apply theme to all text fields in a container
     */
    public static void applyTextFieldTheme(Container container) {
        for (Component component : container.getComponents()) {
            if (component instanceof JTextField) {
                ModernTheme.styleTextField((JTextField) component);
            } else if (component instanceof Container) {
                applyTextFieldTheme((Container) component);
            }
        }
    }

    /**
     * Apply theme to all buttons in a container
     */
    public static void applyButtonTheme(Container container) {
        for (Component component : container.getComponents()) {
            if (component instanceof JButton) {
                JButton button = (JButton) component;
                String text = button.getText().toLowerCase();

                // Determine button type based on text
                if (text.contains("thêm") || text.contains("tạo") || text.contains("lưu")) {
                    ModernTheme.styleButton(button, ModernTheme.ButtonType.SUCCESS);
                } else if (text.contains("sửa") || text.contains("cập nhật")) {
                    ModernTheme.styleButton(button, ModernTheme.ButtonType.PRIMARY);
                } else if (text.contains("xóa") || text.contains("hủy")) {
                    ModernTheme.styleButton(button, ModernTheme.ButtonType.DANGER);
                } else if (text.contains("tìm")) {
                    ModernTheme.styleButton(button, ModernTheme.ButtonType.PRIMARY);
                } else {
                    ModernTheme.styleButton(button, ModernTheme.ButtonType.OUTLINE);
                }
            } else if (component instanceof Container) {
                applyButtonTheme((Container) component);
            }
        }
    }

    /**
     * Apply theme to all tables in a container
     */
    public static void applyTableTheme(Container container) {
        for (Component component : container.getComponents()) {
            if (component instanceof JScrollPane) {
                JScrollPane scrollPane = (JScrollPane) component;
                if (scrollPane.getViewport().getView() instanceof JTable) {
                    JTable table = (JTable) scrollPane.getViewport().getView();
                    ModernTheme.styleTable(table);
                    scrollPane.setBorder(BorderFactory.createLineBorder(ModernTheme.BORDER_COLOR));
                    scrollPane.getViewport().setBackground(Color.WHITE);
                }
            } else if (component instanceof Container) {
                applyTableTheme((Container) component);
            }
        }
    }

    /**
     * Apply complete modern theme to a panel with responsive design
     */
    public static void applyModernTheme(JPanel panel) {
        applyPanelTheme(panel);
        applyTextFieldTheme(panel);
        applyButtonTheme(panel);
        applyTableTheme(panel);

        // Apply to all child panels
        applyThemeRecursively(panel);

        // Make responsive
        ResponsiveLayoutManager.makeResponsive(panel);
    }

    /**
     * Apply modern theme without responsive behavior
     */
    public static void applyModernThemeStatic(JPanel panel) {
        applyPanelTheme(panel);
        applyTextFieldTheme(panel);
        applyButtonTheme(panel);
        applyTableTheme(panel);

        // Apply to all child panels
        applyThemeRecursively(panel);
    }

    private static void applyThemeRecursively(Container container) {
        for (Component component : container.getComponents()) {
            if (component instanceof JPanel) {
                JPanel childPanel = (JPanel) component;
                if (childPanel.getBackground().equals(new Color(238, 238, 238)) ||
                        childPanel.getBackground().equals(Color.LIGHT_GRAY)) {
                    childPanel.setBackground(ModernTheme.CARD_BACKGROUND);
                }
                applyThemeRecursively(childPanel);
            }
        }
    }

    /**
     * Create a status label with appropriate color
     */
    public static JLabel createStatusLabel(String text, boolean isActive) {
        JLabel label = new JLabel(text);
        label.setFont(ModernTheme.FONT_SMALL);
        label.setOpaque(true);
        label.setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));

        if (isActive) {
            label.setBackground(ModernTheme.SUCCESS_COLOR);
            label.setForeground(Color.WHITE);
        } else {
            label.setBackground(ModernTheme.ERROR_COLOR);
            label.setForeground(Color.WHITE);
        }

        return label;
    }

    /**
     * Create a modern combo box
     */
    public static void styleComboBox(JComboBox<?> comboBox) {
        comboBox.setFont(ModernTheme.FONT_BODY);
        comboBox.setPreferredSize(new Dimension(comboBox.getPreferredSize().width, ModernTheme.INPUT_HEIGHT));
        comboBox.setBackground(Color.WHITE);
        comboBox.setBorder(BorderFactory.createLineBorder(ModernTheme.BORDER_COLOR));
    }

    /**
     * Create responsive form panel with proper layout
     */
    public static JPanel createResponsiveFormPanel(String title) {
        JPanel panel = createFormPanel(title);
        panel.setLayout(ResponsiveLayoutManager.createResponsiveGridLayout());
        ResponsiveLayoutManager.makeResponsive(panel);
        return panel;
    }

    /**
     * Create responsive button panel with proper spacing
     */
    public static JPanel createResponsiveButtonPanel(JButton... buttons) {
        JPanel panel = new JPanel();
        panel.setBackground(ModernTheme.CARD_BACKGROUND);
        panel.setLayout(
                new FlowLayout(FlowLayout.CENTER, ModernTheme.COMPONENT_SPACING, ModernTheme.COMPONENT_SPACING));

        for (JButton button : buttons) {
            panel.add(button);
        }

        ResponsiveLayoutManager.makeResponsive(panel);
        return panel;
    }

    /**
     * Create responsive table panel with adaptive column sizing
     */
    public static JPanel createResponsiveTablePanel(String title, JTable table) {
        JPanel panel = createTablePanel(title, table);
        ResponsiveLayoutManager.makeResponsive(panel);
        return panel;
    }

    /**
     * Add responsive form row with adaptive sizing
     */
    public static void addResponsiveFormRow(JPanel parent, GridBagConstraints gbc, String labelText,
            JComponent component) {
        // Label
        JLabel label = new JLabel(labelText);
        label.setFont(ModernTheme.FONT_BODY);
        label.setForeground(ModernTheme.TEXT_PRIMARY);

        // Create responsive constraints for label
        GridBagConstraints labelGbc = (GridBagConstraints) gbc.clone();
        labelGbc.gridx = 0;
        labelGbc.weightx = 0.3;
        labelGbc.anchor = GridBagConstraints.WEST;
        labelGbc.fill = GridBagConstraints.NONE;
        labelGbc.insets = new Insets(ModernTheme.COMPONENT_SPACING, 0, ModernTheme.COMPONENT_SPACING,
                ModernTheme.COMPONENT_SPACING);
        parent.add(label, labelGbc);

        // Create responsive constraints for component
        GridBagConstraints compGbc = (GridBagConstraints) gbc.clone();
        compGbc.gridx = 1;
        compGbc.weightx = 0.7;
        compGbc.fill = GridBagConstraints.HORIZONTAL;
        compGbc.insets = new Insets(ModernTheme.COMPONENT_SPACING, 0, ModernTheme.COMPONENT_SPACING, 0);
        parent.add(component, compGbc);

        gbc.gridy++;
    }

    /**
     * Create responsive grid constraints
     */
    public static GridBagConstraints createResponsiveConstraints(int gridx, int gridy, double weightx, double weighty) {
        return ResponsiveLayoutManager.createResponsiveConstraints(gridx, gridy, weightx, weighty);
    }

    /**
     * Apply responsive sizing to existing components
     */
    public static void makeComponentsResponsive(Container container) {
        ResponsiveLayoutManager.makeResponsive((JPanel) container);
    }
}
