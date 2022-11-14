package com.example.duan1_baove.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.duan1_baove.model.LoaiTheTap;

import java.util.List;

@Dao
public interface LoaiTheTapDAO {


    @Insert
    void insert(LoaiTheTap loaiTheTap);

    @Update
    void update(LoaiTheTap loaiTheTap);

    @Delete
    void delete(LoaiTheTap loaiTheTap);

    @Query("SELECT *FROM loaithetap")
    List<LoaiTheTap> getAll();

    @Query("SELECT *FROM loaithetap WHERE id= :id")
    List<LoaiTheTap> getByID(String id);
}
