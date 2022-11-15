package com.example.duan1_baove.model;

import static androidx.room.ForeignKey.CASCADE;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "lichsugiaodich",foreignKeys = {
        @ForeignKey(entity = KhachHang.class,parentColumns = "soDienThoai",childColumns = "khachang_id",onDelete = CASCADE,onUpdate = CASCADE),
        @ForeignKey(entity = DonHangChiTiet.class,parentColumns = "id",childColumns = "donhangchitiet_id",onDelete = CASCADE,onUpdate = CASCADE)
})
public class LichSuGiaoDich {
    @PrimaryKey
    private int id;
    private String khachang_id;
    private int donhangchitiet_id;
    private String type;
    private int soTien;

    public LichSuGiaoDich(int id, String khachang_id, int donhangchitiet_id, String type, int soTien) {
        this.id = id;
        this.khachang_id = khachang_id;
        this.donhangchitiet_id = donhangchitiet_id;
        this.type = type;
        this.soTien = soTien;
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

    public int getDonhangchitiet_id() {
        return donhangchitiet_id;
    }

    public void setDonhangchitiet_id(int donhangchitiet_id) {
        this.donhangchitiet_id = donhangchitiet_id;
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
}
