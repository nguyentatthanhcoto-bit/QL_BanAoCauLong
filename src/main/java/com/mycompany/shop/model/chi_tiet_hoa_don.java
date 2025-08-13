/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.shop.model;

/**
 *
 * @author mailx
 */
public class chi_tiet_hoa_don {
    public int id_chi_tiet_hoa_don;
    public int id_hoa_don;
    public int id_ao;
    public int so_luong;
    public int don_gia;

    public chi_tiet_hoa_don() {
        // Constructor không tham số
    }

    public chi_tiet_hoa_don(int id_chi_tiet_hoa_don, int id_hoa_don, int id_ao, int so_luong, int don_gia) {
        this.id_chi_tiet_hoa_don = id_chi_tiet_hoa_don;
        this.id_hoa_don = id_hoa_don;
        this.id_ao = id_ao;
        this.so_luong = so_luong;
        this.don_gia = don_gia;
    }

    public int getId_chi_tiet_hoa_don() {
        return id_chi_tiet_hoa_don;
    }

    public void setId_chi_tiet_hoa_don(int id_chi_tiet_hoa_don) {
        this.id_chi_tiet_hoa_don = id_chi_tiet_hoa_don;
    }

    public int getId_hoa_don() {
        return id_hoa_don;
    }

    public void setId_hoa_don(int id_hoa_don) {
        this.id_hoa_don = id_hoa_don;
    }

    public int getId_ao() {
        return id_ao;
    }

    public void setId_ao(int id_ao) {
        this.id_ao = id_ao;
    }

    public int getSo_luong() {
        return so_luong;
    }

    public void setSo_luong(int so_luong) {
        this.so_luong = so_luong;
    }

    public int getDon_gia() {
        return don_gia;
    }

    public void setDon_gia(int don_gia) {
        this.don_gia = don_gia;
    }
}
