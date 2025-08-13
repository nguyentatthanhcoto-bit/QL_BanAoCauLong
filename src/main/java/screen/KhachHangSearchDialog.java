package screen;

import com.mycompany.shop.dao.khach_hang_dao;
import com.mycompany.shop.model.khach_hang;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

/**
 * Dialog để tìm kiếm và chọn khách hàng
 */
public class KhachHangSearchDialog extends JDialog {
    
    private JTextField searchField;
    private JButton searchButton;
    private JButton selectButton;
    private JButton cancelButton;
    private JTable customerTable;
    private DefaultTableModel tableModel;
    
    private khach_hang_dao khachHangDAO;
    private khach_hang selectedCustomer = null;
    private boolean isConfirmed = false;
    
    public KhachHangSearchDialog(Frame parent) {
        super(parent, "Tìm kiếm khách hàng", true);
        
        khachHangDAO = new khach_hang_dao();
        
        initComponents();
        setupLayout();
        setupEvents();
        loadAllCustomers();
        
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(parent);
    }
    
    private void initComponents() {
        searchField = new JTextField(20);
        searchButton = new JButton("Tìm kiếm");
        selectButton = new JButton("Chọn khách hàng");
        cancelButton = new JButton("Hủy");
        
        // Setup table
        String[] columnNames = {"Mã KH", "Họ và tên", "Số điện thoại", "Địa chỉ", "Giới tính"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Không cho edit
            }
        };
        customerTable = new JTable(tableModel);
        customerTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        // Disable select button initially
        selectButton.setEnabled(false);
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout());
        
        // Search panel
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.setBorder(BorderFactory.createTitledBorder("Tìm kiếm"));
        searchPanel.add(new JLabel("Tên hoặc SĐT:"));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        
        // Table panel
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createTitledBorder("Danh sách khách hàng"));
        JScrollPane scrollPane = new JScrollPane(customerTable);
        tablePanel.add(scrollPane, BorderLayout.CENTER);
        
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(selectButton);
        buttonPanel.add(cancelButton);
        
        // Add to dialog
        add(searchPanel, BorderLayout.NORTH);
        add(tablePanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    private void setupEvents() {
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timKiemKhachHang();
            }
        });
        
        selectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chonKhachHang();
            }
        });
        
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                huy();
            }
        });
        
        // Enter để tìm kiếm
        searchField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timKiemKhachHang();
            }
        });
        
        // Double click để chọn
        customerTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    chonKhachHang();
                }
            }
        });
        
        // Selection change để enable/disable button
        customerTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                selectButton.setEnabled(customerTable.getSelectedRow() >= 0);
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
        getRootPane().setDefaultButton(searchButton);
    }
    
    private void loadAllCustomers() {
        try {
            List<khach_hang> customers = khachHangDAO.getAllKhachHang();
            updateTable(customers);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi tải danh sách khách hàng: " + e.getMessage(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void timKiemKhachHang() {
        String keyword = searchField.getText().trim();
        
        if (keyword.isEmpty()) {
            loadAllCustomers();
            return;
        }
        
        try {
            List<khach_hang> customers;
            
            // Tìm kiếm theo tên trước
            customers = khachHangDAO.searchKhachHangByName(keyword);
            
            // Nếu không tìm thấy theo tên, thử tìm theo số điện thoại
            if (customers.isEmpty()) {
                customers = khachHangDAO.searchKhachHangByPhone(keyword);
            }
            
            updateTable(customers);
            
            if (customers.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Không tìm thấy khách hàng nào!",
                        "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi tìm kiếm: " + e.getMessage(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void updateTable(List<khach_hang> customers) {
        tableModel.setRowCount(0);
        
        for (khach_hang kh : customers) {
            Object[] row = {
                kh.getMa_khach_hang(),
                kh.getHo_va_ten(),
                kh.getSo_dien_thoai(),
                kh.getDia_chi(),
                kh.getGioi_tinh()
            };
            tableModel.addRow(row);
        }
    }
    
    private void chonKhachHang() {
        int selectedRow = customerTable.getSelectedRow();
        if (selectedRow >= 0) {
            String maKhachHang = (String) tableModel.getValueAt(selectedRow, 0);
            
            try {
                selectedCustomer = khachHangDAO.getKhachHangByMa(maKhachHang);
                if (selectedCustomer != null) {
                    isConfirmed = true;
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Không thể lấy thông tin khách hàng!",
                            "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Lỗi khi lấy thông tin khách hàng: " + e.getMessage(),
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một khách hàng!",
                    "Thông báo", JOptionPane.WARNING_MESSAGE);
        }
    }
    
    private void huy() {
        isConfirmed = false;
        dispose();
    }
    
    // Getters
    public khach_hang getSelectedCustomer() {
        return selectedCustomer;
    }
    
    public boolean isConfirmed() {
        return isConfirmed;
    }
    
    // Static method để sử dụng dễ dàng
    public static khach_hang showDialog(Frame parent) {
        KhachHangSearchDialog dialog = new KhachHangSearchDialog(parent);
        dialog.setVisible(true);
        
        if (dialog.isConfirmed()) {
            return dialog.getSelectedCustomer();
        }
        return null;
    }
}
