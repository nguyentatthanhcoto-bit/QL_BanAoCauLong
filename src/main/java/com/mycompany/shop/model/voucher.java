/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.shop.model;

import java.sql.Date;

/**
 *
 * @author mailx
 */
public class voucher {
    public int id_voucher;
    public String ma_voucher;
    public String ten_voucher;
    public int gia_tri_giam;
    public Date ngay_bat_dau;
    public Date ngay_ket_thuc;
    public int so_luong;
    public String trang_thai;
    public int id_quan_ly;

    public voucher(int id_voucher, String ma_voucher, String ten_voucher, int gia_tri_giam, Date ngay_bat_dau, Date ngay_ket_thuc, int so_luong, String trang_thai, int id_quan_ly) {
        this.id_voucher = id_voucher;
        this.ma_voucher = ma_voucher;
        this.ten_voucher = ten_voucher;
        this.gia_tri_giam = gia_tri_giam;
        this.ngay_bat_dau = ngay_bat_dau;
        this.ngay_ket_thuc = ngay_ket_thuc;
        this.so_luong = so_luong;
        this.trang_thai = trang_thai;
        this.id_quan_ly = id_quan_ly;
    }

    public int getId_voucher() {
        return id_voucher;
    }

    public void setId_voucher(int id_voucher) {
        this.id_voucher = id_voucher;
    }

    public String getMa_voucher() {
        return ma_voucher;
    }

    public void setMa_voucher(String ma_voucher) {
        this.ma_voucher = ma_voucher;
    }

    public String getTen_voucher() {
        return ten_voucher;
    }

    public void setTen_voucher(String ten_voucher) {
        this.ten_voucher = ten_voucher;
    }

    public int getGia_tri_giam() {
        return gia_tri_giam;
    }

    public void setGia_tri_giam(int gia_tri_giam) {
        this.gia_tri_giam = gia_tri_giam;
    }

    public Date getNgay_bat_dau() {
        return ngay_bat_dau;
    }

    public void setNgay_bat_dau(Date ngay_bat_dau) {
        this.ngay_bat_dau = ngay_bat_dau;
    }

    public Date getNgay_ket_thuc() {
        return ngay_ket_thuc;
    }

    public void setNgay_ket_thuc(Date ngay_ket_thuc) {
        this.ngay_ket_thuc = ngay_ket_thuc;
    }

    public int getSo_luong() {
        return so_luong;
    }

    public void setSo_luong(int so_luong) {
        this.so_luong = so_luong;
    }

    public String getTrang_thai() {
        return trang_thai;
    }

    public void setTrang_thai(String trang_thai) {
        this.trang_thai = trang_thai;
    }

    public int getId_quan_ly() {
        return id_quan_ly;
    }

    public void setId_quan_ly(int id_quan_ly) {
        this.id_quan_ly = id_quan_ly;
    }
    
    
}
