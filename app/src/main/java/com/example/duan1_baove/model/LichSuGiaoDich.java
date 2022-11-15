package com.example.duan1_baove.model;

import static androidx.room.ForeignKey.CASCADE;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "lichsugiaodich",foreignKeys = {
        @ForeignKey(entity = KhachHang.class,parentColumns = "soDienThoai",childColumns = "khachang_id",onDelete = CASCADE,onUpdate = CASCADE),
})
public class LichSuGiaoDich {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String khachang_id;
    private String type;
    private int soTien;
    private String thoigian;

    public LichSuGiaoDich(int id, String khachang_id, String type, int soTien, String thoigian) {
        this.id = id;
        this.khachang_id = khachang_id;
        this.type = type;
        this.soTien = soTien;
        this.thoigian = thoigian;
    }

    public LichSuGiaoDich() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKhachang_id() {
        return khachang_id;
    }

    public void setKhachang_id(String khachang_id) {
        this.khachang_id = khachang_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getSoTien() {
        return soTien;
    }

    public void setSoTien(int soTien) {
        this.soTien = soTien;
    }

    public String getThoigian() {
        return thoigian;
    }

    public void setThoigian(String thoigian) {
        this.thoigian = thoigian;
    }
}
