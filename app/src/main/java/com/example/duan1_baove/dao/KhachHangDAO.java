package com.example.duan1_baove.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.duan1_baove.model.KhachHang;

import java.util.List;

@Dao
public interface KhachHangDAO {

    @Insert
    void insert(KhachHang khachHang);

    @Update
    void update(KhachHang khachHang);

    @Delete
    void delete(KhachHang khachHang);

    @Query("SELECT *FROM khachhang WHERE soDienThoai= :sdt")
    List<KhachHang> checkAcc(String sdt);

    @Query("SELECT *FROM khachhang WHERE soDienThoai= :sdt")
    KhachHang getObject(String sdt);

    @Query("SELECT *FROM khachhang")
    List<KhachHang> getAll();

    @Query("SELECT pass FROM khachhang WHERE soDienThoai= :sdt")
    String getPass(String sdt);
    @Query("SELECT soDu FROM khachhang WHERE soDienThoai= :sdt")
    int getSoDU(String sdt);

    @Query("SELECT hoten FROM khachhang WHERE soDienThoai= :id")
    String getName(String id);

    @Query("SELECT COUNT(soDienThoai) FROM KHACHHANG")
    int getCount();
}
