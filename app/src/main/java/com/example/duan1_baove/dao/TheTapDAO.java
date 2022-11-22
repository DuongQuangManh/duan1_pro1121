package com.example.duan1_baove.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.duan1_baove.model.TheTap;

import java.util.List;

@Dao
public interface TheTapDAO {

    @Insert
    void insert(TheTap theTap);

    @Update
    void update(TheTap theTap);

    @Query("SELECT *FROM thetap")
    List<TheTap> getAll();

    @Query("SELECT *FROM thetap WHERE khachhang_id= :makhachang")
    List<TheTap> checkTheTap(String makhachang);

    @Query("SELECT tongsotiendamuathetap FROM thetap WHERE khachhang_id= :makhachhang")
    int getTongSoTien(String makhachhang);

    @Query("SELECT COUNT(id) FROM thetap")
    int getCount();
}
