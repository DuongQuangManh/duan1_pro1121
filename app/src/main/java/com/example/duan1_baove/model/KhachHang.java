package com.example.duan1_baove.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "khachhang")
public class KhachHang implements Serializable {
    @PrimaryKey
    @NonNull
    private String soDienThoai;
    private String hoten;
    private String namSinh;
    private String gioitinh;
    private int soDu;
    private String avata;
    private String pass;
    private String ngay;


    public KhachHang(@NonNull String soDienThoai, String hoten, String namSinh, String gioitinh, int soDu, String avata, String pass, String ngay) {
        this.soDienThoai = soDienThoai;
        this.hoten = hoten;
        this.namSinh = namSinh;
        this.gioitinh = gioitinh;
        this.soDu = soDu;
        this.avata = avata;
        this.pass = pass;
        this.ngay = ngay;
    }

    public KhachHang() {
    }

    public String getHoten() {
        return hoten;
    }

    public void setHoten(String hoten) {
        this.hoten = hoten;
    }

    public String getNamSinh() {
        return namSinh;
    }

    public void setNamSinh(String namSinh) {
        this.namSinh = namSinh;
    }

    public String getGioitinh() {
        return gioitinh;
    }

    public void setGioitinh(String gioitinh) {
        this.gioitinh = gioitinh;
    }

    public int getSoDu() {
        return soDu;
    }

    public void setSoDu(int soDu) {
        this.soDu = soDu;
    }

    public String getAvata() {
        return avata;
    }

    public void setAvata(String avata) {
        this.avata = avata;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getNgay() {
        return ngay;
    }

    public void setNgay(String ngay) {
        this.ngay = ngay;
    }
}
