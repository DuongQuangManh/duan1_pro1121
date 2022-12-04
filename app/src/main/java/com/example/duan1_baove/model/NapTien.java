package com.example.duan1_baove.model;

import static androidx.room.ForeignKey.CASCADE;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "naptien",foreignKeys = {
        @ForeignKey(entity = KhachHang.class,parentColumns = "soDienThoai",childColumns = "khachhang_id",onUpdate = CASCADE,onDelete = CASCADE)
})
public class NapTien {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int sotien;
    private String trangthai;
    private String khachhang_id;
    private String date;

    public NapTien(int id, int sotien, String trangthai, String khachhang_id, String date) {
        this.id = id;
        this.sotien = sotien;
        this.trangthai = trangthai;
        this.khachhang_id = khachhang_id;
        this.date = date;
    }


    public NapTien() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSotien() {
        return sotien;
    }

    public void setSotien(int sotien) {
        this.sotien = sotien;
    }

    public String getTrangthai() {
        return trangthai;
    }

    public void setTrangthai(String trangthai) {
        this.trangthai = trangthai;
    }

    public String getKhachhang_id() {
        return khachhang_id;
    }

    public void setKhachhang_id(String khachhang_id) {
        this.khachhang_id = khachhang_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
