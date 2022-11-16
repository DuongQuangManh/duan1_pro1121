package com.example.duan1_baove.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.duan1_baove.model.DangKiTapThu;

import java.util.List;

@Dao
public interface DangKiTapThuDAO{

    @Insert
    void insert(DangKiTapThu dangKiTapThu);

    @Query("SELECT *FROM dangkitapthu WHERE khachhang_id= :user")
    List<DangKiTapThu> check (String user);

    @Query("SELECT *FROM dangkitapthu")
    List<DangKiTapThu> getAll();
}
