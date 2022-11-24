package com.example.duan1_baove.fragment.hocvien;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.duan1_baove.HocVien_MainActivity;
import com.example.duan1_baove.R;
import com.example.duan1_baove.adapter.SpinnerAdapterLoaiTheTap;
import com.example.duan1_baove.adapter.SpinnerAdapterNapTien;
import com.example.duan1_baove.database.DuAn1DataBase;
import com.example.duan1_baove.model.KhachHang;
import com.example.duan1_baove.model.LichSuGiaoDich;
import com.example.duan1_baove.model.LoaiTheTap;
import com.example.duan1_baove.model.TheTap;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MuaTheTap_MainActivity_HocVien extends AppCompatActivity {
    ImageView img_back;
    EditText edt_mathetap_dialogthetap,edt_starttime,edt_endtime,edt_tenkhachhang_dialogthetap;
    Spinner spn_loaithetap;
    Button btn_luu_dialogthetap,btn_huy_dialogthetap;
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy, hh:mm-ss");
    SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");

    private List<KhachHang> listKhachHang;
    KhachHang khachHang;
    TheTap theTap;

    private List<TheTap> theTapList;

    private int intLoaiTheTap =0;
    private Calendar calendar = Calendar.getInstance();
    private Calendar calendarEndTime = Calendar.getInstance();
    int yearEnd,monthEnd, dayEnd;
    String strKhachHangID;
    private List<LoaiTheTap> listLoaiTheTap;
    int year = calendar.get(Calendar.YEAR);
    int month = calendar.get(Calendar.MONTH)+1;
    int day = calendar.get(Calendar.DAY_OF_MONTH);
    //int day,month,year;

    String endtimenew;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mua_the_tap_main_hoc_vien);

        initUi();
        listKhachHang = DuAn1DataBase.getInstance(this).khachHangDAO().checkAcc(HocVien_MainActivity.userHocVien);
        khachHang = listKhachHang.get(0);
        showTT();

        img_back.setOnClickListener(v -> {
            onBackPressed();
        });
        btn_huy_dialogthetap.setOnClickListener(v -> {
            onBackPressed();
        });
        btn_luu_dialogthetap.setOnClickListener(v -> {
            add();
        });

    }

    private void initUi() {
        img_back = findViewById(R.id.img_back_muathetaphocvien);
        edt_mathetap_dialogthetap = findViewById(R.id.edt_mathetap_dialogthetap);
        edt_starttime = findViewById(R.id.edt_starttime_dialogthetap);
        edt_endtime = findViewById(R.id.edt_endtime_dialogthetap);
        edt_tenkhachhang_dialogthetap = findViewById(R.id.edt_tenkhachhang_dialogthetap);
        spn_loaithetap = findViewById(R.id.spn_loaithetap_dialogthetap);
        btn_luu_dialogthetap = findViewById(R.id.btn_luu_dialogthetap);
        btn_huy_dialogthetap = findViewById(R.id.btn_huy_dialogthetap);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Animatoo.INSTANCE.animateSlideRight(MuaTheTap_MainActivity_HocVien.this);
    }
    private void showTT(){
        List<TheTap> listTT = DuAn1DataBase.getInstance(this).theTapDAO().checkTheTap(HocVien_MainActivity.userHocVien);
        if (listTT.size()<=0){
            endtimenew = day+"-"+month+"-"+year;
            Log.d("abcd","chưa có thẻ tập");
        }else {
            endtimenew = listTT.get(0).getNgayHetHan();
            for(int i=1;i<listTT.size();i++){
                endtimenew = getMax(endtimenew,listTT.get(i).getNgayHetHan());
            }
            Log.d("abcd"," có thẻ tập");

        }
        edt_tenkhachhang_dialogthetap.setText(khachHang.getHoten());
        listLoaiTheTap = DuAn1DataBase.getInstance(getApplicationContext()).loaiTheTapDAO().getAll();
        SpinnerAdapterLoaiTheTap spinnerLoaiTheTap = new SpinnerAdapterLoaiTheTap(getApplicationContext(),R.layout.item_spiner_naptien,listLoaiTheTap);
        spn_loaithetap.setAdapter(spinnerLoaiTheTap);
        spn_loaithetap.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                intLoaiTheTap = listLoaiTheTap.get(position).getId();
                calendarEndTime = Calendar.getInstance();
                if (listTT.size()>0){
                    calendarEndTime.set(Calendar.DAY_OF_MONTH,getArrayDate(endtimenew)[0]);
                    calendarEndTime.set(Calendar.MONTH,getArrayDate(endtimenew)[1]);
                    calendarEndTime.set(Calendar.YEAR,getArrayDate(endtimenew)[2]);
                    calendarEndTime.add(Calendar.MONTH,Integer.parseInt(listLoaiTheTap.get(position).getHanSuDung()));
                    yearEnd = calendarEndTime.get(Calendar.YEAR);
                    monthEnd = calendarEndTime.get(Calendar.MONTH);
                    dayEnd = calendarEndTime.get(Calendar.DAY_OF_MONTH);
                    edt_endtime.setText(dayEnd+"-"+monthEnd+"-"+yearEnd);
                }else {
                    calendarEndTime.add(Calendar.MONTH,Integer.parseInt(listLoaiTheTap.get(position).getHanSuDung()));
                    yearEnd = calendarEndTime.get(Calendar.YEAR);
                    monthEnd = calendarEndTime.get(Calendar.MONTH)+1;
                    dayEnd = calendarEndTime.get(Calendar.DAY_OF_MONTH);
                    edt_endtime.setText(dayEnd+"-"+monthEnd+"-"+yearEnd);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        edt_starttime.setText(day+"-"+month+"-"+year);

    }
    private void add(){
        if (checksodu(DuAn1DataBase.getInstance(this).loaiTheTapDAO().getGia(String.valueOf(intLoaiTheTap)))){

            Log.d("date",endtimenew);
            theTap = new TheTap();
            theTap.setKhachhang_id(HocVien_MainActivity.userHocVien);
            theTap.setLoaithetap_id(intLoaiTheTap);
            theTap.setNgayDangKy(endtimenew);
            theTap.setNgayHetHan(edt_endtime.getText().toString().trim());
            theTap.setTongsotiendamuathetap(DuAn1DataBase.getInstance(getApplicationContext()).theTapDAO().getTongSoTien(strKhachHangID)+DuAn1DataBase.getInstance(getApplicationContext()).loaiTheTapDAO().getByID(String.valueOf(intLoaiTheTap)).get(0).getGia());
            DuAn1DataBase.getInstance(this).theTapDAO().insert(theTap);
            KhachHang khachHang = DuAn1DataBase.getInstance(this).khachHangDAO().getObject(HocVien_MainActivity.userHocVien);
            khachHang.setSoDu(DuAn1DataBase.getInstance(this).khachHangDAO().getSoDU(HocVien_MainActivity.userHocVien)-DuAn1DataBase.getInstance(this).loaiTheTapDAO().getGia(String.valueOf(intLoaiTheTap)));
            DuAn1DataBase.getInstance(this).khachHangDAO().update(khachHang);
            LichSuGiaoDich lichSuGiaoDich = new LichSuGiaoDich();
            lichSuGiaoDich.setType("Trừ");
            lichSuGiaoDich.setKhachang_id(HocVien_MainActivity.userHocVien);
            lichSuGiaoDich.setThoigian(sdf.format(new Date()));
            lichSuGiaoDich.setSoTien(DuAn1DataBase.getInstance(this).loaiTheTapDAO().getGia(String.valueOf(intLoaiTheTap)));
            DuAn1DataBase.getInstance(this).lichSuGiaoDichDAO().insert(lichSuGiaoDich);
            Toast.makeText(this, "Mua thẻ tập thành công", Toast.LENGTH_SHORT).show();
        }
    }
    private boolean checksodu(int giagoitap){
        if (DuAn1DataBase.getInstance(this).khachHangDAO().getSoDU(HocVien_MainActivity.userHocVien)<giagoitap ){
            Toast.makeText(this, "Số dư của quý khách không đủ !", Toast.LENGTH_SHORT).show();
            return false;
        }else {
            return true;
        }
    }
    public int[] getArrayDate(String date){
        String[] str = date.split("-");
        int arr[] = new int[str.length];
        try{
            for(int i = 0;i<str.length;i++){
                arr[i] = Integer.parseInt(str[i]);
            }
        }catch (NumberFormatException e){
            return null;
        }
        return arr;
    }
    public String getMax(String date1,String date2){
        int day1 = getArrayDate(date1)[0];
        int month1 = getArrayDate(date1)[1];
        int year1 = getArrayDate(date1)[2];

        int day2 = getArrayDate(date2)[0];
        int month2 = getArrayDate(date2)[1];
        int year2 = getArrayDate(date2)[2];
        Calendar calendar1 = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();

        calendar1.set(Calendar.DAY_OF_MONTH,day1);
        calendar1.set(Calendar.MONTH,month1);
        calendar1.set(Calendar.YEAR,year1);

        calendar2.set(Calendar.DAY_OF_MONTH,day2);
        calendar2.set(Calendar.MONTH,month2);
        calendar2.set(Calendar.YEAR,year2);

        if (calendar1.after(calendar2)){
            return date1;
        }else {
            return date2;
        }
    }
}