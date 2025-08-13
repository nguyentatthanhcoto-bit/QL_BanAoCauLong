package com.mycompany.shop.util;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

/**
 * Utility class to optimize JScrollPane behavior
 */
public class ScrollPaneOptimizer {
    
    /**
     * Optimize all scroll panes in a container
     */
    public static void optimizeScrollPanes(Container container) {
        optimizeScrollPanesRecursive(container);
    }
    
    /**
     * Recursively optimize scroll panes
     */
    private static void optimizeScrollPanesRecursive(Container container) {
        for (Component comp : container.getComponents()) {
            if (comp instanceof JScrollPane) {
                optimizeScrollPane((JScrollPane) comp);
            } else if (comp instanceof Container) {
                optimizeScrollPanesRecursive((Container) comp);
            }
        }
    }
    
    /**
     * Optimize a single scroll pane
     */
    public static void optimizeScrollPane(JScrollPane scrollPane) {
        // Improve scrolling performance
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.getHorizontalScrollBar().setUnitIncrement(16);
        
        // Enable smooth scrolling
        enableSmoothScrolling(scrollPane);
        
        // Optimize viewport
        optimizeViewport(scrollPane);
        
        // Set appropriate scroll bar policies
        setOptimalScrollBarPolicies(scrollPane);
        
        // Improve visual appearance
        improveAppearance(scrollPane);
    }
    
    /**
     * Enable smooth scrolling
     */
    private static void enableSmoothScrolling(JScrollPane scrollPane) {
        scrollPane.addMouseWheelListener(new SmoothScrollListener());
    }
    
    /**
     * Optimize viewport settings
     */
    private static void optimizeViewport(JScrollPane scrollPane) {
        JViewport viewport = scrollPane.getViewport();
        
        // Enable backing store for better performance
        viewport.setScrollMode(JViewport.BACKINGSTORE_SCROLL_MODE);
        
        // Set background color
        viewport.setBackground(Color.WHITE);
    }
    
    /**
     * Set optimal scroll bar policies
     */
    private static void setOptimalScrollBarPolicies(JScrollPane scrollPane) {
        Component view = scrollPane.getViewport().getView();
        
        if (view instanceof JTable) {
            // For tables, show horizontal scrollbar when needed
            scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        } else if (view instanceof JTextArea || view instanceof JEditorPane) {
            // For text areas, usually want vertical scrolling
            scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        } else {
            // Default: show scrollbars as needed
            scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        }
    }
    
    /**
     * Improve visual appearance
     */
    private static void improveAppearance(JScrollPane scrollPane) {
        // Remove border if desired
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        
        // Set corner component
        scrollPane.setCorner(JScrollPane.UPPER_RIGHT_CORNER, new JPanel());
        
        // Customize scroll bars
        customizeScrollBars(scrollPane);
    }
    
    /**
     * Customize scroll bar appearance
     */
    private static void customizeScrollBars(JScrollPane scrollPane) {
        JScrollBar vScrollBar = scrollPane.getVerticalScrollBar();
        JScrollBar hScrollBar = scrollPane.getHorizontalScrollBar();
        
        // Set preferred size for scroll bars
        vScrollBar.setPreferredSize(new Dimension(12, 0));
        hScrollBar.setPreferredSize(new Dimension(0, 12));
        
        // Set colors
        Color scrollBarColor = new Color(200, 200, 200);
        vScrollBar.setBackground(scrollBarColor);
        hScrollBar.setBackground(scrollBarColor);
    }
    
    /**
     * Smooth scroll listener
     */
    private static class SmoothScrollListener implements MouseWheelListener {
        private Timer scrollTimer;
        private int targetScrollPosition;
        private JScrollBar scrollBar;
        private int scrollDirection;
        
        @Override
        public void mouseWheelMoved(MouseWheelEvent e) {
            JScrollPane scrollPane = (JScrollPane) e.getSource();
            
            // Determine which scroll bar to use
            if (e.isShiftDown()) {
                scrollBar = scrollPane.getHorizontalScrollBar();
            } else {
                scrollBar = scrollPane.getVerticalScrollBar();
            }
            
            // Calculate scroll amount
            int scrollAmount = e.getUnitsToScroll() * scrollBar.getUnitIncrement();
            scrollDirection = scrollAmount > 0 ? 1 : -1;
            
            // Calculate target position
            targetScrollPosition = scrollBar.getValue() + scrollAmount;
            targetScrollPosition = Math.max(scrollBar.getMinimum(), 
                                          Math.min(scrollBar.getMaximum() - scrollBar.getVisibleAmount(), 
                                                 targetScrollPosition));
            
            // Stop any existing animation
            if (scrollTimer != null && scrollTimer.isRunning()) {
                scrollTimer.stop();
            }
            
            // Start smooth scroll animation
            startSmoothScroll();
            
            // Consume the event to prevent default scrolling
            e.consume();
        }
        
        private void startSmoothScroll() {
            scrollTimer = new Timer(10, e -> {
                int currentPosition = scrollBar.getValue();
                int distance = targetScrollPosition - currentPosition;
                
                if (Math.abs(distance) <= 1) {
                    // Close enough, stop animation
                    scrollBar.setValue(targetScrollPosition);
                    scrollTimer.stop();
                } else {
                    // Move closer to target
                    int step = Math.max(1, Math.abs(distance) / 5);
                    int newPosition = currentPosition + (scrollDirection * step);
                    
                    // Ensure we don't overshoot
                    if (scrollDirection > 0) {
                        newPosition = Math.min(newPosition, targetScrollPosition);
                    } else {
                        newPosition = Math.max(newPosition, targetScrollPosition);
                    }
                    
                    scrollBar.setValue(newPosition);
                }
            });
            
            scrollTimer.start();
        }
    }
    
    /**
     * Create an optimized scroll pane for a component
     */
    public static JScrollPane createOptimizedScrollPane(Component component) {
        JScrollPane scrollPane = new JScrollPane(component);
        optimizeScrollPane(scrollPane);
        return scrollPane;
    }
    
    /**
     * Create an optimized scroll pane for a table
     */
    public static JScrollPane createOptimizedTableScrollPane(JTable table) {
        JScrollPane scrollPane = new JScrollPane(table);
        optimizeScrollPane(scrollPane);
        
        // Table-specific optimizations
        scrollPane.setPreferredSize(new Dimension(600, 300));
        scrollPane.getViewport().setBackground(table.getBackground());
        
        // Ensure table fills viewport height
        table.setFillsViewportHeight(true);
        
        return scrollPane;
    }
    
    /**
     * Optimize scroll pane for mobile-like experience
     */
    public static void enableMobileScrolling(JScrollPane scrollPane) {
        // Increase scroll sensitivity
        scrollPane.getVerticalScrollBar().setUnitIncrement(25);
        scrollPane.getHorizontalScrollBar().setUnitIncrement(25);
        
        // Enable kinetic scrolling effect
        enableKineticScrolling(scrollPane);
    }
    
    /**
     * Enable kinetic scrolling (momentum scrolling)
     */
    private static void enableKineticScrolling(JScrollPane scrollPane) {
        scrollPane.addMouseWheelListener(new KineticScrollListener());
    }
    
    /**
     * Kinetic scroll listener for momentum scrolling
     */
    private static class KineticScrollListener implements MouseWheelListener {
        private Timer kineticTimer;
        private double velocity = 0;
        private double friction = 0.95;
        private JScrollBar scrollBar;
        
        @Override
        public void mouseWheelMoved(MouseWheelEvent e) {
            JScrollPane scrollPane = (JScrollPane) e.getSource();
            
            // Determine scroll bar
            if (e.isShiftDown()) {
                scrollBar = scrollPane.getHorizontalScrollBar();
            } else {
                scrollBar = scrollPane.getVerticalScrollBar();
            }
            
            // Add to velocity
            velocity += e.getUnitsToScroll() * 2;
            
            // Start kinetic scrolling
            startKineticScroll();
            
            e.consume();
        }
        
        private void startKineticScroll() {
            if (kineticTimer != null && kineticTimer.isRunning()) {
                kineticTimer.stop();
            }
            
            kineticTimer = new Timer(16, e -> {
                if (Math.abs(velocity) < 0.1) {
                    kineticTimer.stop();
                    return;
                }
                
                int currentValue = scrollBar.getValue();
                int newValue = currentValue + (int) velocity;
                
                // Clamp to valid range
                newValue = Math.max(scrollBar.getMinimum(), 
                                  Math.min(scrollBar.getMaximum() - scrollBar.getVisibleAmount(), 
                                         newValue));
                
                scrollBar.setValue(newValue);
                
                // Apply friction
                velocity *= friction;
            });
            
            kineticTimer.start();
        }
    }
}
