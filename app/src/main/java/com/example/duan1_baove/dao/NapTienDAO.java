package com.example.duan1_baove.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.duan1_baove.model.NapTien;

import java.util.List;

@Dao
public interface NapTienDAO {

    @Insert
    void insert(NapTien napTien);

    @Update
    void update(NapTien napTien);
    @Query("SELECT *FROM naptien WHERE khachhang_id= :id ORDER BY id DESC")
    List<NapTien> getByID(String id);

    @Query("SELECT *FROM naptien ORDER BY id DESC")
    List<NapTien> getAll();
}
