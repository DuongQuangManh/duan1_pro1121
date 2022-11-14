package com.example.duan1_baove.model;

import static androidx.room.ForeignKey.CASCADE;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity (tableName = "thetap",foreignKeys = {
        @ForeignKey(entity = KhachHang.class,
                parentColumns = "soDienThoai",
                childColumns = "khachhang_id",onDelete = CASCADE,onUpdate = CASCADE),
        @ForeignKey(entity = LoaiTheTap.class,
                parentColumns = "id",
                childColumns = "loaithetap_id",onDelete = CASCADE,onUpdate = CASCADE)
})

public class TheTap {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String khachhang_id;
    private int loaithetap_id;
    private String ngayDangKy;
    private String ngayHetHan;

    public TheTap(int id, String khachhang_id, int loaithetap_id, String ngayDangKy, String ngayHetHan) {
        this.id = id;
        this.khachhang_id = khachhang_id;
        this.loaithetap_id = loaithetap_id;
        this.ngayDangKy = ngayDangKy;
        this.ngayHetHan = ngayHetHan;
    }

    public TheTap() {
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

    public int getLoaithetap_id() {
        return loaithetap_id;
    }

    public void setLoaithetap_id(int loaithetap_id) {
        this.loaithetap_id = loaithetap_id;
    }

    public String getNgayDangKy() {
        return ngayDangKy;
    }

    public void setNgayDangKy(String ngayDangKy) {
        this.ngayDangKy = ngayDangKy;
    }

    public String getNgayHetHan() {
        return ngayHetHan;
    }

    public void setNgayHetHan(String ngayHetHan) {
        this.ngayHetHan = ngayHetHan;
    }
}
