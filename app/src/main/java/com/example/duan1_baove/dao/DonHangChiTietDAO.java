package com.example.duan1_baove.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.duan1_baove.adapter.DonHangAdapter;
import com.example.duan1_baove.model.DonHangChiTiet;

import java.util.List;

@Dao
public interface DonHangChiTietDAO {
    @Insert
    void insert(DonHangChiTiet donHangChiTiet);

    @Update
    void update(DonHangChiTiet donHangChiTiet);

    @Query("SELECT *FROM donhangchitiet ORDER BY id DESC")
    List<DonHangChiTiet> getAll();

    @Query("SELECT *FROM donhangchitiet WHERE tinhTrang= :trangthai")
    List<DonHangChiTiet> getByTrangThai(String trangthai);

    @Query("SELECT *FROM donhangchitiet WHERE id= :id")
    List<DonHangChiTiet> getByID(String id);
}
