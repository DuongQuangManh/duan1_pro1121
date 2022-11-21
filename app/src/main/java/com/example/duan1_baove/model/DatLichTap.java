package com.example.duan1_baove.model;

import static androidx.room.ForeignKey.CASCADE;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "datlichtap", foreignKeys = {
        @ForeignKey(entity = Admin.class,parentColumns = "user",childColumns = "admin_id",onDelete = CASCADE,onUpdate = CASCADE),
        @ForeignKey(entity = KhachHang.class,parentColumns = "soDienThoai",childColumns = "khachhang_id",onUpdate = CASCADE,onDelete = CASCADE)
})
public class DatLichTap {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String admin_id;
    private String khachhang_id;
    private String thoigiandat;
    private String ngaytap;
    private String giotap;
    private String gionghi;

    public DatLichTap() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAdmin_id() {
        return admin_id;
    }

    public void setAdmin_id(String admin_id) {
        this.admin_id = admin_id;
    }

    public String getKhachhang_id() {
        return khachhang_id;
    }

    public void setKhachhang_id(String khachhang_id) {
        this.khachhang_id = khachhang_id;
    }

    public String getThoigiandat() {
        return thoigiandat;
    }

    public void setThoigiandat(String thoigiandat) {
        this.thoigiandat = thoigiandat;
    }

    public String getGiotap() {
        return giotap;
    }

    public void setGiotap(String giotap) {
        this.giotap = giotap;
    }

    public String getNgaytap() {
        return ngaytap;
    }

    public void setNgaytap(String ngaytap) {
        this.ngaytap = ngaytap;
    }

    public String getGionghi() {
        return gionghi;
    }

    public void setGionghi(String gionghi) {
        this.gionghi = gionghi;
    }
}
