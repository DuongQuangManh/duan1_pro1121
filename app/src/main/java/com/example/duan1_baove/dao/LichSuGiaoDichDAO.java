package com.example.duan1_baove.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.duan1_baove.model.LichSuGiaoDich;

import java.util.List;

@Dao
public interface LichSuGiaoDichDAO {

    @Insert
    void insert(LichSuGiaoDich lichSuGiaoDich);

    @Query("SELECT *FROM lichsugiaodich WHERE khachang_id= :id ORDER BY id DESC")
    List<LichSuGiaoDich> getByID(String id);
}
