package com.mycompany.shop.test;

import com.mycompany.shop.util.ModernTheme;
import com.mycompany.shop.util.UIHelper;
import com.mycompany.shop.util.ResponsiveLayoutManager;

import javax.swing.*;
import java.awt.*;

/**
 * Demo class to showcase responsive design features
 */
public class ResponsiveDesignDemo extends JFrame {
    
    public ResponsiveDesignDemo() {
        initializeDemo();
    }
    
    private void initializeDemo() {
        setTitle("Responsive Design Demo - Shop Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        // Create main panel with responsive design
        JPanel mainPanel = createMainPanel();
        add(mainPanel, BorderLayout.CENTER);
        
        // Set initial size and make resizable
        setSize(1200, 800);
        setLocationRelativeTo(null);
        setResizable(true);
        
        // Apply modern theme
        UIHelper.applyModernTheme(mainPanel);
    }
    
    private JPanel createMainPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(ModernTheme.BACKGROUND_COLOR);
        
        // Header
        JPanel headerPanel = createHeaderPanel();
        panel.add(headerPanel, BorderLayout.NORTH);
        
        // Content area with tabs
        JTabbedPane tabbedPane = createContentTabs();
        panel.add(tabbedPane, BorderLayout.CENTER);
        
        // Footer with status
        JPanel footerPanel = createFooterPanel();
        panel.add(footerPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JPanel createHeaderPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(ModernTheme.HEADER_BACKGROUND);
        panel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        
        JLabel titleLabel = new JLabel("Shop Management System - Responsive Design Demo");
        titleLabel.setFont(ModernTheme.FONT_TITLE);
        titleLabel.setForeground(ModernTheme.TEXT_ON_PRIMARY);
        panel.add(titleLabel, BorderLayout.WEST);
        
        JLabel sizeLabel = new JLabel("Resize window to see responsive behavior");
        sizeLabel.setFont(ModernTheme.FONT_SMALL);
        sizeLabel.setForeground(ModernTheme.TEXT_ON_PRIMARY);
        panel.add(sizeLabel, BorderLayout.EAST);
        
        return panel;
    }
    
    private JTabbedPane createContentTabs() {
        JTabbedPane tabbedPane = new JTabbedPane();
        
        // Button Demo Tab
        tabbedPane.addTab("Buttons", createButtonDemoPanel());
        
        // Form Demo Tab
        tabbedPane.addTab("Forms", createFormDemoPanel());
        
        // Table Demo Tab
        tabbedPane.addTab("Tables", createTableDemoPanel());
        
        return tabbedPane;
    }
    
    private JPanel createButtonDemoPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(ModernTheme.BACKGROUND_COLOR);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        
        // Title
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 3;
        JLabel title = ModernTheme.createSectionTitle("Responsive Button Sizes");
        panel.add(title, gbc);
        
        // Small buttons
        gbc.gridy = 1; gbc.gridwidth = 1;
        gbc.gridx = 0;
        JButton smallPrimary = new JButton("Small Primary");
        ModernTheme.styleButton(smallPrimary, ModernTheme.ButtonType.PRIMARY, ModernTheme.ButtonSize.SMALL);
        panel.add(smallPrimary, gbc);
        
        gbc.gridx = 1;
        JButton smallSuccess = new JButton("Small Success");
        ModernTheme.styleButton(smallSuccess, ModernTheme.ButtonType.SUCCESS, ModernTheme.ButtonSize.SMALL);
        panel.add(smallSuccess, gbc);
        
        gbc.gridx = 2;
        JButton smallDanger = new JButton("Small Danger");
        ModernTheme.styleButton(smallDanger, ModernTheme.ButtonType.DANGER, ModernTheme.ButtonSize.SMALL);
        panel.add(smallDanger, gbc);
        
        // Medium buttons
        gbc.gridy = 2;
        gbc.gridx = 0;
        JButton mediumPrimary = new JButton("Medium Primary");
        ModernTheme.styleButton(mediumPrimary, ModernTheme.ButtonType.PRIMARY, ModernTheme.ButtonSize.MEDIUM);
        panel.add(mediumPrimary, gbc);
        
        gbc.gridx = 1;
        JButton mediumSuccess = new JButton("Medium Success");
        ModernTheme.styleButton(mediumSuccess, ModernTheme.ButtonType.SUCCESS, ModernTheme.ButtonSize.MEDIUM);
        panel.add(mediumSuccess, gbc);
        
        gbc.gridx = 2;
        JButton mediumDanger = new JButton("Medium Danger");
        ModernTheme.styleButton(mediumDanger, ModernTheme.ButtonType.DANGER, ModernTheme.ButtonSize.MEDIUM);
        panel.add(mediumDanger, gbc);
        
        // Large buttons
        gbc.gridy = 3;
        gbc.gridx = 0;
        JButton largePrimary = new JButton("Large Primary");
        ModernTheme.styleButton(largePrimary, ModernTheme.ButtonType.PRIMARY, ModernTheme.ButtonSize.LARGE);
        panel.add(largePrimary, gbc);
        
        gbc.gridx = 1;
        JButton largeSuccess = new JButton("Large Success");
        ModernTheme.styleButton(largeSuccess, ModernTheme.ButtonType.SUCCESS, ModernTheme.ButtonSize.LARGE);
        panel.add(largeSuccess, gbc);
        
        gbc.gridx = 2;
        JButton largeDanger = new JButton("Large Danger");
        ModernTheme.styleButton(largeDanger, ModernTheme.ButtonType.DANGER, ModernTheme.ButtonSize.LARGE);
        panel.add(largeDanger, gbc);
        
        return panel;
    }
    
    private JPanel createFormDemoPanel() {
        JPanel panel = UIHelper.createResponsiveFormPanel("Responsive Form Demo");
        
        GridBagConstraints gbc = UIHelper.createResponsiveConstraints(0, 0, 1.0, 0.0);
        
        // Form fields with different sizes
        JTextField smallField = new JTextField("Small Input");
        ModernTheme.styleTextField(smallField, ModernTheme.ButtonSize.SMALL);
        UIHelper.addResponsiveFormRow(panel, gbc, "Small Field:", smallField);
        
        JTextField mediumField = new JTextField("Medium Input");
        ModernTheme.styleTextField(mediumField, ModernTheme.ButtonSize.MEDIUM);
        UIHelper.addResponsiveFormRow(panel, gbc, "Medium Field:", mediumField);
        
        JTextField largeField = new JTextField("Large Input");
        ModernTheme.styleTextField(largeField, ModernTheme.ButtonSize.LARGE);
        UIHelper.addResponsiveFormRow(panel, gbc, "Large Field:", largeField);
        
        // Combo box
        JComboBox<String> comboBox = new JComboBox<>(new String[]{"Option 1", "Option 2", "Option 3"});
        ModernTheme.styleComboBox(comboBox);
        UIHelper.addResponsiveFormRow(panel, gbc, "Combo Box:", comboBox);
        
        // Button panel
        JButton saveBtn = new JButton("Save");
        ModernTheme.styleButton(saveBtn, ModernTheme.ButtonType.SUCCESS, ModernTheme.ButtonSize.MEDIUM);
        
        JButton cancelBtn = new JButton("Cancel");
        ModernTheme.styleButton(cancelBtn, ModernTheme.ButtonType.OUTLINE, ModernTheme.ButtonSize.MEDIUM);
        
        JPanel buttonPanel = UIHelper.createResponsiveButtonPanel(saveBtn, cancelBtn);
        
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(buttonPanel, gbc);
        
        return panel;
    }
    
    private JPanel createTableDemoPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(ModernTheme.BACKGROUND_COLOR);
        
        // Create sample table
        String[] columnNames = {"ID", "Name", "Email", "Phone", "Status", "Actions"};
        Object[][] data = {
            {1, "John Doe", "john@example.com", "123-456-7890", "Active", "Edit"},
            {2, "Jane Smith", "jane@example.com", "098-765-4321", "Inactive", "Edit"},
            {3, "Bob Johnson", "bob@example.com", "555-123-4567", "Active", "Edit"},
            {4, "Alice Brown", "alice@example.com", "777-888-9999", "Active", "Edit"},
            {5, "Charlie Wilson", "charlie@example.com", "111-222-3333", "Inactive", "Edit"}
        };
        
        JTable table = new JTable(data, columnNames);
        ModernTheme.styleTable(table);
        
        JPanel tablePanel = UIHelper.createResponsiveTablePanel("Responsive Table Demo", table);
        panel.add(tablePanel, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createFooterPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.setBackground(ModernTheme.SIDEBAR_BACKGROUND);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        
        JLabel statusLabel = new JLabel("Status: Responsive design active");
        statusLabel.setFont(ModernTheme.FONT_SMALL);
        statusLabel.setForeground(ModernTheme.TEXT_SECONDARY);
        panel.add(statusLabel);
        
        return panel;
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            new ResponsiveDesignDemo().setVisible(true);
        });
    }
}
