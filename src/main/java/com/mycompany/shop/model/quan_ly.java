/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.shop.model;

/**
 *
 * @author mailx
 */
public class quan_ly {
    public int id_quan_ly;
    public String ma_quan_ly;
    public String ten_quan_ly;
    public String tai_khoan;
    public String mat_khau;

    public quan_ly(int id_quan_ly, String ma_quan_ly, String ten_quan_ly, String tai_khoan, String mat_khau) {
        this.id_quan_ly = id_quan_ly;
        this.ma_quan_ly = ma_quan_ly;
        this.ten_quan_ly = ten_quan_ly;
        this.tai_khoan = tai_khoan;
        this.mat_khau = mat_khau;
    }

    public quan_ly() {
    }
    

    public int getId_quan_ly() {
        return id_quan_ly;
    }

    public void setId_quan_ly(int id_quan_ly) {
        this.id_quan_ly = id_quan_ly;
    }

    public String getMa_quan_ly() {
        return ma_quan_ly;
    }

    public void setMa_quan_ly(String ma_quan_ly) {
        this.ma_quan_ly = ma_quan_ly;
    }

    public String getTen_quan_ly() {
        return ten_quan_ly;
    }

    public void setTen_quan_ly(String ten_quan_ly) {
        this.ten_quan_ly = ten_quan_ly;
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
    
    
}
