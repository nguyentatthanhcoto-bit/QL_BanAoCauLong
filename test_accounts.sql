-- Script tạo tài khoản test cho hệ thống đăng nhập mới
-- Chạy script này để có dữ liệu test

-- Thêm quản lý test
INSERT INTO quan_ly (ma_quan_ly, ten_quan_ly, tai_khoan, mat_khau) VALUES 
('QL001', 'Nguyễn Văn Admin', 'admin', '123456'),
('QL002', 'Trần Thị Manager', 'manager', '123456');

-- Thêm nhân viên test (cần có quản lý trước)
INSERT INTO nhan_vien (ma_nhan_vien, ten_nhan_vien, tai_khoan, mat_khau, so_dien_thoai, id_quan_ly) VALUES 
('NV001', 'Lê Văn Nhân Viên', 'nhanvien1', '123456', '0123456789', 1),
('NV002', 'Phạm Thị Bán Hàng', 'nhanvien2', '123456', '0987654321', 1),
('NV003', 'Hoàng Văn Thu Ngân', 'nhanvien3', '123456', '0111222333', 2);

-- Thêm voucher test (chỉ quản lý mới được tạo)
INSERT INTO voucher (ma_voucher, ten_voucher, gia_tri_giam, ngay_bat_dau, ngay_ket_thuc, so_luong, trang_thai, id_quan_ly) VALUES
('VC001', 'Giảm giá mùa hè', 20, '2024-06-01', '2024-08-31', 100, 'Hoạt động', 1),
('VC002', 'Khuyến mãi cuối năm', 30, '2024-11-01', '2024-12-31', 50, 'Hoạt động', 1),
('VC003', 'Voucher VIP', 15, '2024-01-01', '2024-12-31', 200, 'Hoạt động', 2),
('VC004', 'Giảm giá sinh nhật', 25, '2024-03-01', '2024-03-31', 30, 'Tạm dừng', 2);

-- Kiểm tra dữ liệu đã thêm
SELECT 'QUAN_LY' as table_name, ma_quan_ly as ma, ten_quan_ly as ten, tai_khoan, mat_khau FROM quan_ly
UNION ALL
SELECT 'NHAN_VIEN' as table_name, ma_nhan_vien as ma, ten_nhan_vien as ten, tai_khoan, mat_khau FROM nhan_vien
UNION ALL
SELECT 'VOUCHER' as table_name, ma_voucher as ma, ten_voucher as ten, CONCAT(gia_tri_giam, '%') as tai_khoan, trang_thai as mat_khau FROM voucher;
