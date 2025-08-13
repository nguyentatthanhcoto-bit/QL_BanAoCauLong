/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.shop.model;

/**
 *
 * @author mailx
 */
public class nhan_vien {
    public int id_nhan_vien;
    public String ma_nhan_vien;
    public String ten_nhan_vien;
    public String tai_khoan;
    public String mat_khau;
    public String so_dien_thoai;
    public int id_quan_ly;

    public nhan_vien(int id_nhan_vien, String ma_nhan_vien, String ten_nhan_vien, String tai_khoan, String mat_khau, String so_dien_thoai, int id_quan_ly) {
        this.id_nhan_vien = id_nhan_vien;
        this.ma_nhan_vien = ma_nhan_vien;
        this.ten_nhan_vien = ten_nhan_vien;
        this.tai_khoan = tai_khoan;
        this.mat_khau = mat_khau;
        this.so_dien_thoai = so_dien_thoai;
        this.id_quan_ly = id_quan_ly;
    }

    public int getId_nhan_vien() {
        return id_nhan_vien;
    }

    public void setId_nhan_vien(int id_nhan_vien) {
        this.id_nhan_vien = id_nhan_vien;
    }

    public String getMa_nhan_vien() {
        return ma_nhan_vien;
    }

    public void setMa_nhan_vien(String ma_nhan_vien) {
        this.ma_nhan_vien = ma_nhan_vien;
    }

    public String getTen_nhan_vien() {
        return ten_nhan_vien;
    }

    public void setTen_nhan_vien(String ten_nhan_vien) {
        this.ten_nhan_vien = ten_nhan_vien;
    }

    public String getTai_khoan() {
        return tai_khoan;
    }

    public void setTai_khoan(String tai_khoan) {
        this.tai_khoan = tai_khoan;
    }

    public String getMat_khau() {
        return mat_khau;
    }

    public void setMat_khau(String mat_khau) {
        this.mat_khau = mat_khau;
    }

    public String getSo_dien_thoai() {
        return so_dien_thoai;
    }

    public void setSo_dien_thoai(String so_dien_thoai) {
        this.so_dien_thoai = so_dien_thoai;
    }

    public int getId_quan_ly() {
        return id_quan_ly;
    }

    public void setId_quan_ly(int id_quan_ly) {
        this.id_quan_ly = id_quan_ly;
    }
    
    
    
}
