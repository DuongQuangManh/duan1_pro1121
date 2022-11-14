package com.example.duan1_baove.model;

import static androidx.room.ForeignKey.CASCADE;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.google.android.material.tabs.TabLayout;

@Entity (tableName = "donhangchitiet", foreignKeys = {
        @ForeignKey(entity = CuaHang.class,
                parentColumns = "id",
                childColumns = "cuahang_id",onDelete = CASCADE,onUpdate = CASCADE),
        @ForeignKey(entity = KhachHang.class,
                parentColumns = "soDienThoai",
                childColumns = "khachang_id",onDelete = CASCADE,onUpdate = CASCADE)})
public class DonHangChiTiet {
    @PrimaryKey (autoGenerate = true)
    private int id;
    private int cuahang_id;
    private String khachang_id;
    private int soLuong;
    private String starttime;
    private String endtime;
    private int tongtien;
    private int gianiemyet;
    private String tinhTrang;

    public DonHangChiTiet(int id, int cuahang_id, String khachang_id, int soLuong, String starttime, String endtime, int tongtien, int gianiemyet, String tinhTrang) {
        this.id = id;
        this.cuahang_id = cuahang_id;
        this.khachang_id = khachang_id;
        this.soLuong = soLuong;
        this.starttime = starttime;
        this.endtime = endtime;
        this.tongtien = tongtien;
        this.gianiemyet = gianiemyet;
        this.tinhTrang = tinhTrang;
    }

    public DonHangChiTiet() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCuahang_id() {
        return cuahang_id;
    }

    public void setCuahang_id(int cuahang_id) {
        this.cuahang_id = cuahang_id;
    }

    public String getKhachang_id() {
        return khachang_id;
    }

    public void setKhachang_id(String khachang_id) {
        this.khachang_id = khachang_id;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public String getTinhTrang() {
        return tinhTrang;
    }

    public void setTinhTrang(String tinhTrang) {
        this.tinhTrang = tinhTrang;
    }

    public int getTongtien() {
        return tongtien;
    }

    public void setTongtien(int tongtien) {
        this.tongtien = tongtien;
    }

    public int getGianiemyet() {
        return gianiemyet;
    }

    public void setGianiemyet(int gianiemyet) {
        this.gianiemyet = gianiemyet;
    }
}
