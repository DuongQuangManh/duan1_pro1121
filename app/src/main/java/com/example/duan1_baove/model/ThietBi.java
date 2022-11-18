package com.example.duan1_baove.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.android.material.tabs.TabLayout;

@Entity (tableName = "thietbi")
public class ThietBi{
    @PrimaryKey (autoGenerate = true)
    private int id;
    private String name;
    private String loai;
    private int soLuong;
    private String hangSanXuat;
    private String hinhanh;
    private String thoigianmua;
    private String thoigianbaotrigannhat;
    private int tongchiphibaotri;

    public ThietBi(int id, String name, String loai, int soLuong, String hangSanXuat, String hinhanh, String thoigianmua, String thoigianbaotrigannhat, int tongchiphibaotri) {
        this.id = id;
        this.name = name;
        this.loai = loai;
        this.soLuong = soLuong;
        this.hangSanXuat = hangSanXuat;
        this.hinhanh = hinhanh;
        this.thoigianmua = thoigianmua;
        this.thoigianbaotrigannhat = thoigianbaotrigannhat;
        this.tongchiphibaotri = tongchiphibaotri;
    }

    public ThietBi() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLoai() {
        return loai;
    }

    public void setLoai(String loai) {
        this.loai = loai;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public String getHangSanXuat() {
        return hangSanXuat;
    }

    public void setHangSanXuat(String hangSanXuat) {
        this.hangSanXuat = hangSanXuat;
    }

    public String getHinhanh() {
        return hinhanh;
    }

    public void setHinhanh(String hinhanh) {
        this.hinhanh = hinhanh;
    }

    public String getThoigianmua() {
        return thoigianmua;
    }

    public void setThoigianmua(String thoigianmua) {
        this.thoigianmua = thoigianmua;
    }

    public String getThoigianbaotrigannhat() {
        return thoigianbaotrigannhat;
    }

    public void setThoigianbaotrigannhat(String thoigianbaotrigannhat) {
        this.thoigianbaotrigannhat = thoigianbaotrigannhat;
    }

    public int getTongchiphibaotri() {
        return tongchiphibaotri;
    }

    public void setTongchiphibaotri(int tongchiphibaotri) {
        this.tongchiphibaotri = tongchiphibaotri;
    }
}
