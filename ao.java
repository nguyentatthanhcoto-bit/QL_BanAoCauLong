/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.shop.model;

/**
 *
 * @author mailx
 */
public class ao {
    public int id_ao;
    public String ma_ao;
    public String ten_ao;
    public int gia;
    public int so_luong;
    public String mo_ta;
    public String image; // Thêm trường image
    public int id_loai;
    public int id_mau_sac;
    public int id_size;

    public ao() {
        // Constructor không tham số
    }

    public ao(int id_ao, String ma_ao, String ten_ao, int gia, int so_luong, String mo_ta, String image, int id_loai,
            int id_mau_sac,
            int id_size) {
        this.id_ao = id_ao;
        this.ma_ao = ma_ao;
        this.ten_ao = ten_ao;
        this.gia = gia;
        this.so_luong = so_luong;
        this.mo_ta = mo_ta;
        this.image = image;
        this.id_loai = id_loai;
        this.id_mau_sac = id_mau_sac;
        this.id_size = id_size;
    }

    public int getId_ao() {
        return id_ao;
    }

    public void setId_ao(int id_ao) {
        this.id_ao = id_ao;
    }

    public String getMa_ao() {
        return ma_ao;
    }

    public void setMa_ao(String ma_ao) {
        this.ma_ao = ma_ao;
    }

    public String getTen_ao() {
        return ten_ao;
    }

    public void setTen_ao(String ten_ao) {
        this.ten_ao = ten_ao;
    }

    public int getGia() {
        return gia;
    }

    public void setGia(int gia) {
        this.gia = gia;
    }

    public int getSo_luong() {
        return so_luong;
    }

    public void setSo_luong(int so_luong) {
        this.so_luong = so_luong;
    }

    public String getMo_ta() {
        return mo_ta;
    }

    public void setMo_ta(String mo_ta) {
        this.mo_ta = mo_ta;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getId_loai() {
        return id_loai;
    }

    public void setId_loai(int id_loai) {
        this.id_loai = id_loai;
    }

    public int getId_mau_sac() {
        return id_mau_sac;
    }

    public void setId_mau_sac(int id_mau_sac) {
        this.id_mau_sac = id_mau_sac;
    }

    public int getId_size() {
        return id_size;
    }

    public void setId_size(int id_size) {
        this.id_size = id_size;
    }

}
