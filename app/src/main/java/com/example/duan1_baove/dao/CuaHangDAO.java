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

    @Query("SELECT *FROM cuahang")
    List<CuaHang> getAll();
}
