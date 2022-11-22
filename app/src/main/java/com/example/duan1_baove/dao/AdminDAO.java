package com.example.duan1_baove.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.duan1_baove.model.Admin;

import java.util.List;

@Dao
public interface AdminDAO {

    @Insert
    void insert(Admin admin);

    @Update
    void update(Admin admin);

    @Delete
    void delete(Admin admin);

    @Query("SELECT pass FROM admin WHERE user= :user")
    String getPass(String user);

    @Query("SELECT *FROM admin WHERE user = :user")
    List<Admin> checkaccount(String user);

    @Query("SELECT name FROM admin WHERE user = :user")
    String getName(String user);

    @Query("SELECT *FROM admin WHERE user = :user")
    Admin getObject(String user);

    @Query("SELECT *FROM admin")
    List<Admin> getAll();

    @Query("SELECT COUNT(user) FROM admin JOIN chucvu ON admin.chucvu_id = chucvu.id WHERE tenchucvu= :string")
    int getSoluongPT(String string);

    @Query("SELECT *FROM admin JOIN chucvu ON admin.chucvu_id = chucvu.id WHERE tenchucvu= :string")
    List<Admin> getPT(String string);

    @Query("SELECT SUM(luong) FROM admin")
    int getTongLuong();

    @Query("SELECT COUNT(user) FROM admin")
    int getCount();
}
