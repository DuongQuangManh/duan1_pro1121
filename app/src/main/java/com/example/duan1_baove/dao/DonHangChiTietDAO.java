package com.example.duan1_baove.dao;

import androidx.room.Dao;
import androidx.room.Insert;

import com.example.duan1_baove.model.DonHangChiTiet;

@Dao
public interface DonHangChiTietDAO {
    @Insert
    void insert(DonHangChiTiet donHangChiTiet);
}
