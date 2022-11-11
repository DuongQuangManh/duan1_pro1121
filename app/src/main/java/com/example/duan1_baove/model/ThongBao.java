package com.example.duan1_baove.model;

import static androidx.room.ForeignKey.CASCADE;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "thongbao",foreignKeys = @ForeignKey(entity = Admin.class,parentColumns = "user",childColumns = "user_id",onDelete = CASCADE,onUpdate = CASCADE))
public class ThongBao{
    @PrimaryKey (autoGenerate = true)
    private int id;
    private String tieude;
    private String thoigian;
    private String noidung;
    private String user_id;


    public ThongBao(int id, String tieude, String thoigian, String noidung, String user_id) {
        this.id = id;
        this.tieude = tieude;
        this.thoigian = thoigian;
        this.noidung = noidung;
        this.user_id = user_id;
    }

    public ThongBao() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTieude() {
        return tieude;
    }

    public void setTieude(String tieude) {
        this.tieude = tieude;
    }

    public String getThoigian() {
        return thoigian;
    }

    public void setThoigian(String thoigian) {
        this.thoigian = thoigian;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getNoidung() {
        return noidung;
    }

    public void setNoidung(String noidung) {
        this.noidung = noidung;
    }
}
