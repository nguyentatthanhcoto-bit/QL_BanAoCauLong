/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.shop.model;

/**
 *
 * @author mailx
 */
public class khach_hang {
    public int id_khach_hang;
    public String ho_va_ten;
    public String so_dien_thoai;
    public String dia_chi;
    public String ma_khach_hang;
    public String gioi_tinh;
    public String trang_thai;

    public khach_hang(int id_khach_hang, String ho_va_ten, String so_dien_thoai, String dia_chi, String ma_khach_hang, String gioi_tinh, String trang_thai) {
        this.id_khach_hang = id_khach_hang;
        this.ho_va_ten = ho_va_ten;
        this.so_dien_thoai = so_dien_thoai;
        this.dia_chi = dia_chi;
        this.ma_khach_hang = ma_khach_hang;
        this.gioi_tinh = gioi_tinh;
        this.trang_thai = trang_thai;
    }

    public khach_hang() {
    }

    public int getId_khach_hang() {
        return id_khach_hang;
    }

    public void setId_khach_hang(int id_khach_hang) {
        this.id_khach_hang = id_khach_hang;
    }

    public String getHo_va_ten() {
        return ho_va_ten;
    }

    public void setHo_va_ten(String ho_va_ten) {
        this.ho_va_ten = ho_va_ten;
    }

    public String getSo_dien_thoai() {
        return so_dien_thoai;
    }

    public void setSo_dien_thoai(String so_dien_thoai) {
        this.so_dien_thoai = so_dien_thoai;
    }

    public String getDia_chi() {
        return dia_chi;
    }

    public void setDia_chi(String dia_chi) {
        this.dia_chi = dia_chi;
    }
    
    public String getMa_khach_hang() {
        return ma_khach_hang;
    }

    public void setMa_khach_hang(String ma_khach_hang) {
        this.ma_khach_hang = ma_khach_hang;
    }

    public String getGioi_tinh() {
        return gioi_tinh;
    }

    public void setGioi_tinh(String gioi_tinh) {
        this.gioi_tinh = gioi_tinh;
    }

    public String getTrang_thai() {
        return trang_thai;
    }

    public void setTrang_thai(String trang_thai) {
        this.trang_thai = trang_thai;
    }
    
    
}
