package com.example.duan1_baove.model;

import static androidx.room.ForeignKey.CASCADE;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "sotienbaotrithietbi",foreignKeys = {@ForeignKey(entity = ThietBi.class,parentColumns = "id",childColumns = "thietbi_id",onDelete = CASCADE,onUpdate = CASCADE)})
public class SoTienBaoTriThietBi {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int thietbi_id;
    private int sotien;
    private String date;

    public SoTienBaoTriThietBi(int id, int thietbi_id, int sotien, String date) {
        this.id = id;
        this.thietbi_id = thietbi_id;
        this.sotien = sotien;
        this.date = date;
    }

    public SoTienBaoTriThietBi() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getThietbi_id() {
        return thietbi_id;
    }

    public void setThietbi_id(int thietbi_id) {
        this.thietbi_id = thietbi_id;
    }

    public int getSotien() {
        return sotien;
    }

    public void setSotien(int sotien) {
        this.sotien = sotien;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
