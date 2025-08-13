package com.mycompany.shop.util;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Utility class to optimize individual components
 */
public class ComponentOptimizer {
    
    // Modern color scheme
    private static final Color PRIMARY_COLOR = new Color(52, 152, 219);
    private static final Color SUCCESS_COLOR = new Color(46, 204, 113);
    private static final Color WARNING_COLOR = new Color(241, 196, 15);
    private static final Color DANGER_COLOR = new Color(231, 76, 60);
    private static final Color LIGHT_GRAY = new Color(236, 240, 241);
    private static final Color DARK_GRAY = new Color(52, 73, 94);
    
    /**
     * Optimize all components in a container
     */
    public static void optimizeComponents(Container container) {
        optimizeComponentsRecursive(container);
    }
    
    /**
     * Recursively optimize components
     */
    private static void optimizeComponentsRecursive(Container container) {
        for (Component comp : container.getComponents()) {
            if (comp instanceof JButton) {
                optimizeButton((JButton) comp);
            } else if (comp instanceof JTextField) {
                optimizeTextField((JTextField) comp);
            } else if (comp instanceof JLabel) {
                optimizeLabel((JLabel) comp);
            } else if (comp instanceof JComboBox) {
                optimizeComboBox((JComboBox<?>) comp);
            } else if (comp instanceof JPanel) {
                optimizePanel((JPanel) comp);
            }
            
            // Recurse into containers
            if (comp instanceof Container) {
                optimizeComponentsRecursive((Container) comp);
            }
        }
    }
    
    /**
     * Optimize button appearance and behavior
     */
    public static void optimizeButton(JButton button) {
        // Set modern styling
        button.setFocusPainted(false);
        button.setBorderPainted(true);
        button.setContentAreaFilled(true);
        
        // Set font
        Font font = button.getFont();
        if (font != null) {
            button.setFont(font.deriveFont(Font.BOLD, 12f));
        }
        
        // Set colors based on button text/purpose
        String text = button.getText().toLowerCase();
        if (text.contains("thêm") || text.contains("add") || text.contains("tạo")) {
            setButtonStyle(button, SUCCESS_COLOR, Color.WHITE);
        } else if (text.contains("sửa") || text.contains("cập nhật") || text.contains("edit") || text.contains("update")) {
            setButtonStyle(button, PRIMARY_COLOR, Color.WHITE);
        } else if (text.contains("xóa") || text.contains("delete") || text.contains("remove")) {
            setButtonStyle(button, DANGER_COLOR, Color.WHITE);
        } else if (text.contains("tìm") || text.contains("search")) {
            setButtonStyle(button, WARNING_COLOR, Color.WHITE);
        } else {
            setButtonStyle(button, LIGHT_GRAY, DARK_GRAY);
        }
        
        // Add hover effects
        addButtonHoverEffect(button);
        
        // Set minimum size
        Dimension minSize = new Dimension(80, 32);
        if (button.getPreferredSize().width < minSize.width || 
            button.getPreferredSize().height < minSize.height) {
            button.setPreferredSize(minSize);
        }
    }
    
    /**
     * Set button style with colors
     */
    private static void setButtonStyle(JButton button, Color background, Color foreground) {
        button.setBackground(background);
        button.setForeground(foreground);
        
        // Create rounded border
        Border border = BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(background.darker(), 1),
            BorderFactory.createEmptyBorder(8, 16, 8, 16)
        );
        button.setBorder(border);
    }
    
    /**
     * Add hover effect to button
     */
    private static void addButtonHoverEffect(JButton button) {
        Color originalBackground = button.getBackground();
        Color hoverBackground = originalBackground.brighter();
        
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (button.isEnabled()) {
                    button.setBackground(hoverBackground);
                    button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                }
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(originalBackground);
                button.setCursor(Cursor.getDefaultCursor());
            }
        });
    }
    
    /**
     * Optimize text field appearance and behavior
     */
    public static void optimizeTextField(JTextField textField) {
        // Set border
        Border border = BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189, 195, 199), 1),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
        );
        textField.setBorder(border);
        
        // Set font
        Font font = textField.getFont();
        if (font != null) {
            textField.setFont(font.deriveFont(12f));
        }
        
        // Set background
        textField.setBackground(Color.WHITE);
        
        // Add focus effects
        addTextFieldFocusEffect(textField);
        
        // Set minimum height
        Dimension prefSize = textField.getPreferredSize();
        if (prefSize.height < 32) {
            textField.setPreferredSize(new Dimension(prefSize.width, 32));
        }
    }
    
    /**
     * Add focus effect to text field
     */
    private static void addTextFieldFocusEffect(JTextField textField) {
        Border normalBorder = textField.getBorder();
        Border focusBorder = BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(PRIMARY_COLOR, 2),
            BorderFactory.createEmptyBorder(7, 11, 7, 11)
        );
        
        textField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                textField.setBorder(focusBorder);
            }
            
            @Override
            public void focusLost(FocusEvent e) {
                textField.setBorder(normalBorder);
            }
        });
    }
    
    /**
     * Optimize label appearance
     */
    public static void optimizeLabel(JLabel label) {
        // Set font based on label purpose
        String text = label.getText().toLowerCase();
        Font font = label.getFont();
        
        if (font != null) {
            if (text.contains("title") || text.contains("tiêu đề")) {
                // Title labels
                label.setFont(font.deriveFont(Font.BOLD, 16f));
                label.setForeground(DARK_GRAY);
            } else if (text.contains("error") || text.contains("lỗi")) {
                // Error labels
                label.setFont(font.deriveFont(Font.PLAIN, 12f));
                label.setForeground(DANGER_COLOR);
            } else if (text.contains("success") || text.contains("thành công")) {
                // Success labels
                label.setFont(font.deriveFont(Font.PLAIN, 12f));
                label.setForeground(SUCCESS_COLOR);
            } else {
                // Normal labels
                label.setFont(font.deriveFont(Font.PLAIN, 12f));
                label.setForeground(DARK_GRAY);
            }
        }
        
        // Set alignment for better layout
        if (label.getHorizontalAlignment() == SwingConstants.LEADING) {
            label.setHorizontalAlignment(SwingConstants.LEFT);
        }
    }
    
    /**
     * Optimize combo box appearance
     */
    public static void optimizeComboBox(JComboBox<?> comboBox) {
        // Set font
        Font font = comboBox.getFont();
        if (font != null) {
            comboBox.setFont(font.deriveFont(12f));
        }
        
        // Set background
        comboBox.setBackground(Color.WHITE);
        
        // Set minimum height
        Dimension prefSize = comboBox.getPreferredSize();
        if (prefSize.height < 32) {
            comboBox.setPreferredSize(new Dimension(prefSize.width, 32));
        }
        
        // Add focus effect
        addComboBoxFocusEffect(comboBox);
    }
    
    /**
     * Add focus effect to combo box
     */
    private static void addComboBoxFocusEffect(JComboBox<?> comboBox) {
        comboBox.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                comboBox.setBorder(BorderFactory.createLineBorder(PRIMARY_COLOR, 2));
            }
            
            @Override
            public void focusLost(FocusEvent e) {
                comboBox.setBorder(UIManager.getBorder("ComboBox.border"));
            }
        });
    }
    
    /**
     * Optimize panel appearance
     */
    public static void optimizePanel(JPanel panel) {
        // Set background
        if (panel.getBackground() == null || panel.getBackground().equals(UIManager.getColor("Panel.background"))) {
            panel.setBackground(Color.WHITE);
        }
        
        // Add padding if no border is set
        if (panel.getBorder() == null) {
            panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        }
    }
    
    /**
     * Create a modern styled button
     */
    public static JButton createStyledButton(String text, Color backgroundColor, Color textColor) {
        JButton button = new JButton(text);
        setButtonStyle(button, backgroundColor, textColor);
        addButtonHoverEffect(button);
        return button;
    }
    
    /**
     * Create a primary button (blue)
     */
    public static JButton createPrimaryButton(String text) {
        return createStyledButton(text, PRIMARY_COLOR, Color.WHITE);
    }
    
    /**
     * Create a success button (green)
     */
    public static JButton createSuccessButton(String text) {
        return createStyledButton(text, SUCCESS_COLOR, Color.WHITE);
    }
    
    /**
     * Create a warning button (yellow)
     */
    public static JButton createWarningButton(String text) {
        return createStyledButton(text, WARNING_COLOR, Color.WHITE);
    }
    
    /**
     * Create a danger button (red)
     */
    public static JButton createDangerButton(String text) {
        return createStyledButton(text, DANGER_COLOR, Color.WHITE);
    }
    
    /**
     * Create a modern styled text field
     */
    public static JTextField createStyledTextField(String placeholder) {
        JTextField textField = new JTextField();
        optimizeTextField(textField);
        
        // Add placeholder functionality
        if (placeholder != null && !placeholder.isEmpty()) {
            addPlaceholder(textField, placeholder);
        }
        
        return textField;
    }
    
    /**
     * Add placeholder text to text field
     */
    private static void addPlaceholder(JTextField textField, String placeholder) {
        Color placeholderColor = new Color(149, 165, 166);
        Color normalColor = textField.getForeground();
        
        textField.setText(placeholder);
        textField.setForeground(placeholderColor);
        
        textField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (textField.getText().equals(placeholder)) {
                    textField.setText("");
                    textField.setForeground(normalColor);
                }
            }
            
            @Override
            public void focusLost(FocusEvent e) {
                if (textField.getText().isEmpty()) {
                    textField.setText(placeholder);
                    textField.setForeground(placeholderColor);
                }
            }
        });
    }
    
    /**
     * Create a modern styled label
     */
    public static JLabel createStyledLabel(String text, int style, float size) {
        JLabel label = new JLabel(text);
        Font font = label.getFont();
        if (font != null) {
            label.setFont(font.deriveFont(style, size));
        }
        label.setForeground(DARK_GRAY);
        return label;
    }
    
    /**
     * Create a title label
     */
    public static JLabel createTitleLabel(String text) {
        return createStyledLabel(text, Font.BOLD, 16f);
    }
    
    /**
     * Create a subtitle label
     */
    public static JLabel createSubtitleLabel(String text) {
        return createStyledLabel(text, Font.PLAIN, 14f);
    }
}
