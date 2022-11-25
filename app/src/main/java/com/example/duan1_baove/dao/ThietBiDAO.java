package com.example.duan1_baove.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.duan1_baove.model.ThietBi;

import java.util.List;

@Dao
public interface ThietBiDAO {

    @Insert
    void insert(ThietBi thietBi);

    @Query("SELECT *FROM thietbi")
    List<ThietBi> getAll();

    @Update
    void update(ThietBi thietBi);

    @Delete
    void delete(ThietBi thietBi);

    @Query("SELECT *FROM thietbi WHERE id= :id")
    ThietBi getThietBiById(String id);

    @Query("SELECT tongchiphibaotri FROM thietbi WHERE id= :id")
    int getTongSoTienBaoTri(String id);

    @Query("SELECT COUNT(id) FROM thietbi")
    int getCount();

}
