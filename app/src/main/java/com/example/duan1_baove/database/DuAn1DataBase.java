package com.example.duan1_baove.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.duan1_baove.dao.AdminDAO;
import com.example.duan1_baove.dao.ChucVuDAO;
import com.example.duan1_baove.dao.CuaHangDAO;
import com.example.duan1_baove.dao.DangKiTapThuDAO;
import com.example.duan1_baove.dao.DatLichTapDAO;
import com.example.duan1_baove.dao.DonHangChiTietDAO;
import com.example.duan1_baove.dao.KhachHangDAO;
import com.example.duan1_baove.dao.LichSuGiaoDichDAO;
import com.example.duan1_baove.dao.LoaiTheTapDAO;
import com.example.duan1_baove.dao.SoTienBaoTriThietBiDAO;
import com.example.duan1_baove.dao.TheTapDAO;
import com.example.duan1_baove.dao.ThietBiDAO;
import com.example.duan1_baove.dao.ThongBaoDAO;
import com.example.duan1_baove.model.Admin;
import com.example.duan1_baove.model.ChucVu;
import com.example.duan1_baove.model.CuaHang;
import com.example.duan1_baove.model.DangKiTapThu;
import com.example.duan1_baove.model.DatLichTap;
import com.example.duan1_baove.model.DonHangChiTiet;
import com.example.duan1_baove.model.KhachHang;
import com.example.duan1_baove.model.LichSuGiaoDich;
import com.example.duan1_baove.model.LoaiTheTap;
import com.example.duan1_baove.model.SoTienBaoTriThietBi;
import com.example.duan1_baove.model.TheTap;
import com.example.duan1_baove.model.ThietBi;
import com.example.duan1_baove.model.ThongBao;

@Database(entities = {Admin.class, ChucVu.class, CuaHang.class,
        DonHangChiTiet.class, KhachHang.class, LoaiTheTap.class,
        TheTap.class, ThietBi.class, ThongBao.class, LichSuGiaoDich.class,
        DangKiTapThu.class, DatLichTap.class, SoTienBaoTriThietBi.class},version = 1)
public abstract class DuAn1DataBase extends RoomDatabase {
    private static final String DATABASE_NAME= "duan1.fpt";
    private static DuAn1DataBase instance;

    public static synchronized DuAn1DataBase getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),DuAn1DataBase.class,DATABASE_NAME).allowMainThreadQueries().build();
        }
        return instance;
    }

    public abstract AdminDAO adminDAO();
    public abstract ChucVuDAO chucVuDAO();
    public abstract CuaHangDAO cuaHangDAO();
    public abstract DonHangChiTietDAO donHangChiTietDAO();
    public abstract KhachHangDAO khachHangDAO();
    public abstract LoaiTheTapDAO loaiTheTapDAO();
    public abstract TheTapDAO theTapDAO();
    public abstract ThietBiDAO thietBiDAO();
    public abstract ThongBaoDAO thongBaoDAO();
    public abstract LichSuGiaoDichDAO lichSuGiaoDichDAO();
    public abstract DangKiTapThuDAO dangKiTapThuDAO();
    public abstract DatLichTapDAO datLichTapDAO();
    public abstract SoTienBaoTriThietBiDAO soTienBaoTriThietBiDAO();


}
