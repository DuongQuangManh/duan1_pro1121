package com.example.duan1_baove.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.duan1_baove.model.DonHangChiTiet;

import java.util.List;

@Dao
public interface DonHangChiTietDAO {
    @Insert
    void insert(DonHangChiTiet donHangChiTiet);

    @Update
    void update(DonHangChiTiet donHangChiTiet);

    @Query("SELECT *FROM donhangchitiet ORDER BY id DESC")
    List<DonHangChiTiet> getAllAdmin();

    @Query("SELECT *FROM donhangchitiet WHERE khachang_id= :khachhang_id ORDER BY id DESC")
    List<DonHangChiTiet> getAll(String khachhang_id);

    @Query("SELECT *FROM donhangchitiet WHERE tinhTrang= :trangthai")
    List<DonHangChiTiet> getByTrangThai(String trangthai);

    @Query("SELECT *FROM donhangchitiet WHERE id= :id")
    List<DonHangChiTiet> getByID(String id);

    @Query("SELECT *FROM donhangchitiet JOIN cuahang ON donhangchitiet.cuahang_id = cuahang.id WHERE khachang_id= :idkhachhang AND name= 'PT' AND donhangchitiet.tinhTrang= 'Đã kiểm duyệt'")
    List<DonHangChiTiet> checkDichVu(String idkhachhang);

    @Query("SELECT *FROM donhangchitiet JOIN cuahang ON donhangchitiet.cuahang_id = cuahang.id WHERE khachang_id= :idkhachhang AND theloai= 'Dịch vụ'")
    List<DonHangChiTiet> getDichVu(String idkhachhang);

    @Query("SELECT COUNT(id) FROM donhangchitiet")
    int getCount();

    @Query("SELECT SUM(tongtien) FROM donhangchitiet")
    int getTongDoanhThu();

    @Query("SELECT SUM(tongtien) FROM donhangchitiet JOIN cuahang ON donhangchitiet.cuahang_id = cuahang.id WHERE theloai= 'Dịch vụ'")
    int getTongSoTienKiemDuocTuDichVu();


    @Query("SELECT SUM(tongtien) FROM donhangchitiet WHERE starttime LIKE :date")
    int getTongSoTienTrongThang(String date);

}
