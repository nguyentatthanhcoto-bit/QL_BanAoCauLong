package screen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Dialog để nhập số lượng sản phẩm khi thêm vào giỏ hàng
 */
public class SoLuongDialog extends JDialog {
    
    private JTextField soLuongField;
    private JButton confirmButton;
    private JButton cancelButton;
    private int soLuongDaChon = 0;
    private boolean isConfirmed = false;
    
    private String tenSanPham;
    private int soLuongTonKho;
    private int giaSanPham;
    
    public SoLuongDialog(Frame parent, String tenSanPham, int soLuongTonKho, int giaSanPham) {
        super(parent, "Chọn số lượng", true);
        this.tenSanPham = tenSanPham;
        this.soLuongTonKho = soLuongTonKho;
        this.giaSanPham = giaSanPham;
        
        initComponents();
        setupLayout();
        setupEvents();
        
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);
        pack();
        setLocationRelativeTo(parent);
    }
    
    private void initComponents() {
        soLuongField = new JTextField("1", 10);
        confirmButton = new JButton("Xác nhận");
        cancelButton = new JButton("Hủy");
        
        // Set focus vào text field
        soLuongField.selectAll();
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout());
        
        // Panel thông tin sản phẩm
        JPanel infoPanel = new JPanel(new GridBagLayout());
        infoPanel.setBorder(BorderFactory.createTitledBorder("Thông tin sản phẩm"));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Tên sản phẩm
        gbc.gridx = 0; gbc.gridy = 0;
        infoPanel.add(new JLabel("Tên sản phẩm:"), gbc);
        gbc.gridx = 1;
        JLabel tenLabel = new JLabel(tenSanPham);
        tenLabel.setFont(tenLabel.getFont().deriveFont(Font.BOLD));
        infoPanel.add(tenLabel, gbc);
        
        // Giá
        gbc.gridx = 0; gbc.gridy = 1;
        infoPanel.add(new JLabel("Đơn giá:"), gbc);
        gbc.gridx = 1;
        JLabel giaLabel = new JLabel(formatCurrency(giaSanPham));
        giaLabel.setForeground(Color.BLUE);
        giaLabel.setFont(giaLabel.getFont().deriveFont(Font.BOLD));
        infoPanel.add(giaLabel, gbc);
        
        // Số lượng tồn kho
        gbc.gridx = 0; gbc.gridy = 2;
        infoPanel.add(new JLabel("Tồn kho:"), gbc);
        gbc.gridx = 1;
        JLabel tonKhoLabel = new JLabel(soLuongTonKho + " sản phẩm");
        tonKhoLabel.setForeground(soLuongTonKho > 0 ? Color.GREEN : Color.RED);
        tonKhoLabel.setFont(tonKhoLabel.getFont().deriveFont(Font.BOLD));
        infoPanel.add(tonKhoLabel, gbc);
        
        // Panel nhập số lượng
        JPanel inputPanel = new JPanel(new FlowLayout());
        inputPanel.setBorder(BorderFactory.createTitledBorder("Chọn số lượng"));
        
        inputPanel.add(new JLabel("Số lượng:"));
        inputPanel.add(soLuongField);
        
        // Panel buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(confirmButton);
        buttonPanel.add(cancelButton);
        
        // Add to dialog
        add(infoPanel, BorderLayout.NORTH);
        add(inputPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    private void setupEvents() {
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                xacNhan();
            }
        });
        
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                huy();
            }
        });
        
        // Enter để xác nhận
        soLuongField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                xacNhan();
            }
        });
        
        // Escape để hủy
        KeyStroke escapeKeyStroke = KeyStroke.getKeyStroke("ESCAPE");
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(escapeKeyStroke, "ESCAPE");
        getRootPane().getActionMap().put("ESCAPE", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                huy();
            }
        });
        
        // Set default button
        getRootPane().setDefaultButton(confirmButton);
    }
    
    private void xacNhan() {
        try {
            String soLuongText = soLuongField.getText().trim();
            
            if (soLuongText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập số lượng!", 
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
                soLuongField.requestFocus();
                return;
            }
            
            int soLuong = Integer.parseInt(soLuongText);
            
            if (soLuong <= 0) {
                JOptionPane.showMessageDialog(this, "Số lượng phải lớn hơn 0!", 
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
                soLuongField.selectAll();
                soLuongField.requestFocus();
                return;
            }
            
            if (soLuong > soLuongTonKho) {
                JOptionPane.showMessageDialog(this, 
                        "Số lượng không được vượt quá tồn kho (" + soLuongTonKho + ")!", 
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
                soLuongField.selectAll();
                soLuongField.requestFocus();
                return;
            }
            
            soLuongDaChon = soLuong;
            isConfirmed = true;
            dispose();
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Số lượng phải là số nguyên!", 
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
            soLuongField.selectAll();
            soLuongField.requestFocus();
        }
    }
    
    private void huy() {
        isConfirmed = false;
        dispose();
    }
    
    private String formatCurrency(int amount) {
        return String.format("%,d VND", amount);
    }
    
    // Getters
    public int getSoLuongDaChon() {
        return soLuongDaChon;
    }
    
    public boolean isConfirmed() {
        return isConfirmed;
    }
    
    // Static method để sử dụng dễ dàng
    public static int showDialog(Frame parent, String tenSanPham, int soLuongTonKho, int giaSanPham) {
        SoLuongDialog dialog = new SoLuongDialog(parent, tenSanPham, soLuongTonKho, giaSanPham);
        dialog.setVisible(true);
        
        if (dialog.isConfirmed()) {
            return dialog.getSoLuongDaChon();
        }
        return 0; // Hủy hoặc không chọn
    }
}
