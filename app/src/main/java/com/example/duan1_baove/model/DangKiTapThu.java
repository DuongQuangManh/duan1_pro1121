package com.example.duan1_baove.model;

import static androidx.room.ForeignKey.CASCADE;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "dangkitapthu",
        foreignKeys = {@ForeignKey(entity = KhachHang.class,
                parentColumns = "soDienThoai",
                childColumns = "khachhang_id",
                onDelete = CASCADE,onUpdate = CASCADE)})
public class DangKiTapThu {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String khachhang_id;
    private String ngaydangki;
    private String ngayTap;

    public DangKiTapThu(int id, String khachhang_id, String ngaydangki, String ngayTap) {
        this.id = id;
        this.khachhang_id = khachhang_id;
        this.ngaydangki = ngaydangki;
        this.ngayTap = ngayTap;
    }

    public DangKiTapThu() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKhachhang_id() {
        return khachhang_id;
    }

    public void setKhachhang_id(String khachhang_id) {
        this.khachhang_id = khachhang_id;
    }

    public String getNgaydangki() {
        return ngaydangki;
    }

    public void setNgaydangki(String ngaydangki) {
        this.ngaydangki = ngaydangki;
    }

    public String getNgayTap() {
        return ngayTap;
    }

    public void setNgayTap(String ngayTap) {
        this.ngayTap = ngayTap;
    }
}
