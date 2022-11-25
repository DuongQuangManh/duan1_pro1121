package com.example.duan1_baove.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.duan1_baove.model.SoTienBaoTriThietBi;

@Dao
public interface SoTienBaoTriThietBiDAO {

    @Insert
    void insert(SoTienBaoTriThietBi soTienBaoTriThietBi);


    @Query("SELECT SUM(sotien) FROM sotienbaotrithietbi WHERE date LIKE :date")
    int getMoneyByDate(String date);
}
