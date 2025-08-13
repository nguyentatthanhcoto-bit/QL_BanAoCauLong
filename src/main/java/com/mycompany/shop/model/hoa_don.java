/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.shop.model;

import java.sql.Date;
import java.sql.Timestamp;

/**
 *
 * @author mailx
 */
public class hoa_don {
    public int id_hoa_don;
    public String ma_hoa_don;
    public Date ngay_tao;
    public int tong_tien;
    public int id_nhan_vien;
    public int id_khach_hang;
    public int id_voucher;
    public String ho_va_ten_khach;
    public String so_dien_thoai_khach;
    public String dia_chi_khach;
    public int trang_thai; // 0: chưa thanh toán, 1: đã thanh toán

    public hoa_don() {
        // Constructor không tham số
    }

    public hoa_don(int id_hoa_don, String ma_hoa_don, Date ngay_tao, int tong_tien, int id_nhan_vien, int id_khach_hang,
            int id_voucher, String ho_va_ten_khach, String so_dien_thoai_khach, String dia_chi_khach, int trang_thai) {
        this.id_hoa_don = id_hoa_don;
        this.ma_hoa_don = ma_hoa_don;
        this.ngay_tao = ngay_tao;
        this.tong_tien = tong_tien;
        this.id_nhan_vien = id_nhan_vien;
        this.id_khach_hang = id_khach_hang;
        this.id_voucher = id_voucher;
        this.ho_va_ten_khach = ho_va_ten_khach;
        this.so_dien_thoai_khach = so_dien_thoai_khach;
        this.dia_chi_khach = dia_chi_khach;
        this.trang_thai = trang_thai;
    }

    public int getId_hoa_don() {
        return id_hoa_don;
    }

    public void setId_hoa_don(int id_hoa_don) {
        this.id_hoa_don = id_hoa_don;
    }

    public String getMa_hoa_don() {
        return ma_hoa_don;
    }

    public void setMa_hoa_don(String ma_hoa_don) {
        this.ma_hoa_don = ma_hoa_don;
    }

    public Date getNgay_tao() {
        return ngay_tao;
    }

    public void setNgay_tao(Date ngay_tao) {
        this.ngay_tao = ngay_tao;
    }

    // Thêm phương thức để hỗ trợ cả Timestamp
    public void setNgay_tao(Timestamp timestamp) {
        this.ngay_tao = new Date(timestamp.getTime());
    }

    public int getTong_tien() {
        return tong_tien;
    }

    public void setTong_tien(int tong_tien) {
        this.tong_tien = tong_tien;
    }

    public int getId_nhan_vien() {
        return id_nhan_vien;
    }

    public void setId_nhan_vien(int id_nhan_vien) {
        this.id_nhan_vien = id_nhan_vien;
    }

    public int getId_khach_hang() {
        return id_khach_hang;
    }

    public void setId_khach_hang(int id_khach_hang) {
        this.id_khach_hang = id_khach_hang;
    }

    public int getId_voucher() {
        return id_voucher;
    }

    public void setId_voucher(int id_voucher) {
        this.id_voucher = id_voucher;
    }

    public String getHo_va_ten_khach() {
        return ho_va_ten_khach;
    }

    public void setHo_va_ten_khach(String ho_va_ten_khach) {
        this.ho_va_ten_khach = ho_va_ten_khach;
    }

    public String getSo_dien_thoai_khach() {
        return so_dien_thoai_khach;
    }

    public void setSo_dien_thoai_khach(String so_dien_thoai_khach) {
        this.so_dien_thoai_khach = so_dien_thoai_khach;
    }

    public String getDia_chi_khach() {
        return dia_chi_khach;
    }

    public void setDia_chi_khach(String dia_chi_khach) {
        this.dia_chi_khach = dia_chi_khach;
    }

    public int getTrang_thai() {
        return trang_thai;
    }

    public void setTrang_thai(int trang_thai) {
        this.trang_thai = trang_thai;
    }

    // Helper methods
    public boolean isDaThanhToan() {
        return trang_thai == 1;
    }

    public boolean isChuaThanhToan() {
        return trang_thai == 0;
    }
}
