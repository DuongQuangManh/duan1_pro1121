package com.example.duan1_baove.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.duan1_baove.model.CuaHang;

import java.util.List;

@Dao
public interface CuaHangDAO {

    @Insert
    void insert(CuaHang cuaHang);

    @Update
    void update(CuaHang cuaHang);
    @Delete
    void delete(CuaHang cuaHang);

    @Query("SELECT *FROM cuahang ORDER BY id DESC")
    List<CuaHang> getAll();

    @Query("SELECT *FROM cuahang WHERE id= :id")
    List<CuaHang> getByID(String id);

    @Query("SELECT *FROM cuahang WHERE theloai= :type")
    List<CuaHang> retrieveByType(String type);

    @Query("SELECT gia FROM cuahang WHERE id= :id")
    int getGiaBan(String id);

    @Query("SELECT name FROM cuahang WHERE id= :id")
    String getName(String id);
}
