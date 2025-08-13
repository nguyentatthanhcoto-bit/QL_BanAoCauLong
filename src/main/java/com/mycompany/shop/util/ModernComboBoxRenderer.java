package com.mycompany.shop.util;

import javax.swing.*;
import java.awt.*;

/**
 * Modern ComboBox renderer with consistent styling
 */
public class ModernComboBoxRenderer extends DefaultListCellRenderer {
    
    @Override
    public Component getListCellRendererComponent(
            JList<?> list, Object value, int index,
            boolean isSelected, boolean cellHasFocus) {
        
        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        
        // Set font
        setFont(ModernTheme.FONT_BODY);
        
        // Set colors based on selection state
        if (isSelected) {
            setBackground(ModernTheme.PRIMARY_COLOR);
            setForeground(Color.WHITE);
        } else {
            setBackground(Color.WHITE);
            setForeground(ModernTheme.TEXT_PRIMARY);
        }
        
        // Add padding
        setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
        
        // Handle null values
        if (value == null) {
            setText("");
        } else {
            setText(value.toString());
        }
        
        return this;
    }
    
    @Override
    public Dimension getPreferredSize() {
        Dimension size = super.getPreferredSize();
        return new Dimension(size.width, Math.max(size.height, 32));
    }
}
