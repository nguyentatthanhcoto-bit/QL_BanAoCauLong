package com.mycompany.shop.model;

/**
 * Class để quản lý session của user đang đăng nhập
 * Hỗ trợ cả nhân viên và quản lý với phân quyền khác nhau
 */
public class UserSession {
    private static UserSession instance;
    
    // User info
    private int userId;
    private String userCode;
    private String userName;
    private String userAccount;
    private UserType userType;
    
    // Enum để phân biệt loại user
    public enum UserType {
        QUAN_LY,    // Quản lý - có full quyền
        NHAN_VIEN   // Nhân viên - quyền hạn chế
    }
    
    // Private constructor for Singleton
    private UserSession() {}
    
    // Get singleton instance
    public static UserSession getInstance() {
        if (instance == null) {
            instance = new UserSession();
        }
        return instance;
    }
    
    // Login as manager
    public void loginAsManager(quan_ly manager) {
        this.userId = manager.getId_quan_ly();
        this.userCode = manager.getMa_quan_ly();
        this.userName = manager.getTen_quan_ly();
        this.userAccount = manager.getTai_khoan();
        this.userType = UserType.QUAN_LY;
    }
    
    // Login as employee
    public void loginAsEmployee(nhan_vien employee) {
        this.userId = employee.getId_nhan_vien();
        this.userCode = employee.getMa_nhan_vien();
        this.userName = employee.getTen_nhan_vien();
        this.userAccount = employee.getTai_khoan();
        this.userType = UserType.NHAN_VIEN;
    }
    
    // Logout
    public void logout() {
        this.userId = 0;
        this.userCode = null;
        this.userName = null;
        this.userAccount = null;
        this.userType = null;
    }
    
    // Check if user is logged in
    public boolean isLoggedIn() {
        return userType != null && userId > 0;
    }
    
    // Check if user is manager
    public boolean isManager() {
        return userType == UserType.QUAN_LY;
    }
    
    // Check if user is employee
    public boolean isEmployee() {
        return userType == UserType.NHAN_VIEN;
    }
    
    // Check if user has permission to access employee management
    public boolean canAccessEmployeeManagement() {
        return isManager(); // Chỉ quản lý mới được truy cập
    }
    
    // Getters
    public int getUserId() {
        return userId;
    }
    
    public String getUserCode() {
        return userCode;
    }
    
    public String getUserName() {
        return userName;
    }
    
    public String getUserAccount() {
        return userAccount;
    }
    
    public UserType getUserType() {
        return userType;
    }
    
    public String getUserTypeString() {
        return userType == UserType.QUAN_LY ? "Quản lý" : "Nhân viên";
    }
    
    // Get display info
    public String getDisplayInfo() {
        if (!isLoggedIn()) {
            return "Chưa đăng nhập";
        }
        return userName + " (" + getUserTypeString() + ")";
    }
}
