/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.shop.model;

/**
 *
 * @author mailx
 */
public class loai_ao {
    public int id_loai_ao;
    public String ma_loai;
    public String ten_loai;

    public loai_ao(int id_loai_ao, String ma_loai, String ten_loai) {
        this.id_loai_ao = id_loai_ao;
        this.ma_loai = ma_loai;
        this.ten_loai = ten_loai;
    }

    public int getId_loai_ao() {
        return id_loai_ao;
    }

    public void setId_loai_ao(int id_loai_ao) {
        this.id_loai_ao = id_loai_ao;
    }

    public String getMa_loai() {
        return ma_loai;
    }

    public void setMa_loai(String ma_loai) {
        this.ma_loai = ma_loai;
    }

    public String getTen_loai() {
        return ten_loai;
    }

    public void setTen_loai(String ten_loai) {
        this.ten_loai = ten_loai;
    }
    
    
}
