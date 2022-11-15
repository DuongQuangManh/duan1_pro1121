package com.example.duan1_baove.model;

import static androidx.room.ForeignKey.CASCADE;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "lichsugiaodich",foreignKeys = {
        @ForeignKey(entity = KhachHang.class,parentColumns = "soDienThoai",childColumns = "KhachHang_id",onDelete = CASCADE,onUpdate = CASCADE),
        @ForeignKey(entity = DonHangChiTiet.class,parentColumns = "id",childColumns = "DonHangChiTiet_id",onDelete = CASCADE,onUpdate = CASCADE)
})
public class LichSuGiaoDich {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String KhachHang_id;
    private String DonHangChiTiet_id;
    private String type;
    private int soTien;

    public LichSuGiaoDich(int id, String khachHang_id, String donHangChiTiet_id, String type, int soTien) {
        this.id = id;
        KhachHang_id = khachHang_id;
        DonHangChiTiet_id = donHangChiTiet_id;
        this.type = type;
        this.soTien = soTien;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKhachHang_id() {
        return KhachHang_id;
    }

    public void setKhachHang_id(String khachHang_id) {
        KhachHang_id = khachHang_id;
    }

    public String getDonHangChiTiet_id() {
        return DonHangChiTiet_id;
    }

    public void setDonHangChiTiet_id(String donHangChiTiet_id) {
        DonHangChiTiet_id = donHangChiTiet_id;
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
