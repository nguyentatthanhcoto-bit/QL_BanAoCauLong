package com.mycompany.shop.util;

import javax.swing.*;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.plaf.basic.BasicComboPopup;
import javax.swing.plaf.basic.ComboPopup;
import java.awt.*;
import java.awt.geom.Path2D;

/**
 * Modern ComboBox UI with custom arrow and styling
 */
public class ModernComboBoxUI extends BasicComboBoxUI {
    
    private static final int ARROW_SIZE = 8;
    private static final int ARROW_PADDING = 10;
    
    @Override
    protected JButton createArrowButton() {
        return new ModernArrowButton();
    }
    
    @Override
    protected ComboPopup createPopup() {
        BasicComboPopup popup = new BasicComboPopup(comboBox) {
            @Override
            protected void configurePopup() {
                super.configurePopup();
                setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(ModernTheme.BORDER_COLOR, 1),
                    BorderFactory.createEmptyBorder(4, 0, 4, 0)
                ));
                setBackground(Color.WHITE);
            }
        };
        return popup;
    }
    
    @Override
    public void paintCurrentValueBackground(Graphics g, Rectangle bounds, boolean hasFocus) {
        // Custom background painting
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        if (hasFocus) {
            g2.setColor(ModernTheme.PRIMARY_LIGHT);
        } else {
            g2.setColor(Color.WHITE);
        }
        
        g2.fillRoundRect(bounds.x, bounds.y, bounds.width, bounds.height, 6, 6);
        g2.dispose();
    }
    
    /**
     * Custom arrow button with modern design
     */
    private class ModernArrowButton extends JButton {
        
        public ModernArrowButton() {
            setPreferredSize(new Dimension(ARROW_SIZE + ARROW_PADDING * 2, 20));
            setBorder(BorderFactory.createEmptyBorder());
            setContentAreaFilled(false);
            setFocusPainted(false);
            setBackground(Color.WHITE);
        }
        
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            // Set arrow color
            if (getModel().isPressed()) {
                g2.setColor(ModernTheme.PRIMARY_DARK);
            } else if (getModel().isRollover()) {
                g2.setColor(ModernTheme.PRIMARY_COLOR);
            } else {
                g2.setColor(ModernTheme.TEXT_SECONDARY);
            }
            
            // Draw modern arrow
            int centerX = getWidth() / 2;
            int centerY = getHeight() / 2;
            
            Path2D arrow = new Path2D.Double();
            arrow.moveTo(centerX - ARROW_SIZE / 2, centerY - 2);
            arrow.lineTo(centerX + ARROW_SIZE / 2, centerY - 2);
            arrow.lineTo(centerX, centerY + ARROW_SIZE / 2 - 2);
            arrow.closePath();
            
            g2.fill(arrow);
            g2.dispose();
        }
        
        @Override
        public Dimension getPreferredSize() {
            return new Dimension(ARROW_SIZE + ARROW_PADDING * 2, super.getPreferredSize().height);
        }
    }
}
