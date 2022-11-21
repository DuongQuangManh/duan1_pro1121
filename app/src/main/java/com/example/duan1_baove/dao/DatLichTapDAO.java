package com.example.duan1_baove.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.duan1_baove.model.DatLichTap;

import java.util.List;

@Dao
public interface DatLichTapDAO {

    @Insert
    void insert(DatLichTap datLichTap);

    @Query("SELECT *FROM datlichtap WHERE khachhang_id= :id ORDER BY id DESC")
    List<DatLichTap> getByIdKhachhang(String id);

    @Query("SELECT *FROM datlichtap JOIN admin ON datlichtap.admin_id = admin.user " +
            "JOIN chucvu ON admin.chucvu_id = chucvu.id " +
            "WHERE tenchucvu= 'PT'")
    List<DatLichTap> checkPTByTime();

    @Query("SELECT *FROM datlichtap WHERE admin_id= :id")
    List<DatLichTap> getCVbyIdPT(String id);
}
