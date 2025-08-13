/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.shop.model;

/**
 *
 * @author mailx
 */
public class size {
    public  int id_size;
    public String ma_size;
    public String ten_size;

    public size(int id_size, String ma_size, String ten_size) {
        this.id_size = id_size;
        this.ma_size = ma_size;
        this.ten_size = ten_size;
    }

    public size() {
    }
    

    public int getId_size() {
        return id_size;
    }

    public void setId_size(int id_size) {
        this.id_size = id_size;
    }

    public String getMa_size() {
        return ma_size;
    }

    public void setMa_size(String ma_size) {
        this.ma_size = ma_size;
    }

    public String getTen_size() {
        return ten_size;
    }

    public void setTen_size(String ten_size) {
        this.ten_size = ten_size;
    }
    
    
}
