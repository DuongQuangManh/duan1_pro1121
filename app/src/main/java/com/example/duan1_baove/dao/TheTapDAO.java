package com.example.duan1_baove.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.duan1_baove.model.TheTap;

import java.util.List;

@Dao
public interface TheTapDAO {

    @Insert
    void insert(TheTap theTap);

    @Query("SELECT *FROM thetap")
    List<TheTap> getAll();

    @Query("SELECT *FROM thetap WHERE khachhang_id= :makhachang")
    List<TheTap> checkTheTap(String makhachang);
}
