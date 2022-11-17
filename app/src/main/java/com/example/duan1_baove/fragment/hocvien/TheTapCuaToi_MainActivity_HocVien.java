package com.example.duan1_baove.fragment.hocvien;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.duan1_baove.HocVien_MainActivity;
import com.example.duan1_baove.R;
import com.example.duan1_baove.adapter.DangKiTheTapThuAdapter;
import com.example.duan1_baove.adapter.SpinnerAdapterLoaiTheTap;
import com.example.duan1_baove.adapter.TheTapAdapter;
import com.example.duan1_baove.database.DuAn1DataBase;
import com.example.duan1_baove.model.DangKiTapThu;
import com.example.duan1_baove.model.LoaiTheTap;
import com.example.duan1_baove.model.TheTap;

import java.util.Calendar;
import java.util.List;

public class TheTapCuaToi_MainActivity_HocVien extends AppCompatActivity {
    private RecyclerView rcy_thetapthu,rcy_thetap;
    private TheTapAdapter adapter;
    private DangKiTheTapThuAdapter adaptertapthu;
    private ImageView img_back;
    private List<DangKiTapThu> listTheTapThu;
    private List<TheTap> listTheTap;

    private EditText edt_name,edt_starttime,edt_endtime;
    private Spinner spn_loaithetap;
    private Button btn_add,btn_huy;
    private List<LoaiTheTap> listLoaiTheTap;
    int idloaithetap;
    private Calendar calendar = Calendar.getInstance();
    int year = calendar.get(Calendar.YEAR);
    int month = calendar.get(Calendar.MONTH)+1;
    int day = calendar.get(Calendar.DAY_OF_MONTH);
    private Calendar calendarEndTime = Calendar.getInstance();
    int yearEnd,monthEnd, dayEnd;
    int TongTienGiaTheTap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_the_tap_cua_toi_main_hoc_vien);
        initUi();
        capnhat();
        img_back.setOnClickListener(v -> {
            onBackPressed();
        });
    }

    private void capnhat() {
        listTheTapThu = DuAn1DataBase.getInstance(this).dangKiTapThuDAO().check(HocVien_MainActivity.userHocVien);
        adaptertapthu = new DangKiTheTapThuAdapter(this);
        adaptertapthu.setData(listTheTapThu);
        LinearLayoutManager manager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        rcy_thetapthu.setLayoutManager(manager);
        rcy_thetapthu.setAdapter(adaptertapthu);

        listTheTap = DuAn1DataBase.getInstance(this).theTapDAO().checkTheTap(HocVien_MainActivity.userHocVien);
        adapter = new TheTapAdapter(this, new TheTapAdapter.IClickListener() {
            @Override
            public void giahan(TheTap theTap) {
                opendialog(theTap);
            }
        });
        adapter.setData(listTheTap);
        LinearLayoutManager managerthetap = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        rcy_thetap.setLayoutManager(managerthetap);
        rcy_thetap.setAdapter(adapter);
    }

    private void opendialog(TheTap theTap) {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_giahangoitap);
        dialog.show();
        Window window = dialog.getWindow();
        if (window==null){
            return;
        }
        window.setBackgroundDrawable(null);
        edt_name = dialog.findViewById(R.id.edt_name_giahanthetap);
        edt_endtime = dialog.findViewById(R.id.edt_endtime_giahanthetap);
        edt_starttime = dialog.findViewById(R.id.edt_starttime_giahanthetap);
        spn_loaithetap = dialog.findViewById(R.id.spn_loaithetap_giahanthetap);
        btn_add = dialog.findViewById(R.id.btn_luu_giahanthetap);
        btn_huy = dialog.findViewById(R.id.btn_huy_giahanthetap);

        listLoaiTheTap = DuAn1DataBase.getInstance(this).loaiTheTapDAO().getAll();
        SpinnerAdapterLoaiTheTap spinnerLoaiTheTap = new SpinnerAdapterLoaiTheTap(this,R.layout.item_spiner_naptien,listLoaiTheTap);
        spn_loaithetap.setAdapter(spinnerLoaiTheTap);
        spn_loaithetap.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                idloaithetap = listLoaiTheTap.get(position).getId();
                calendarEndTime = Calendar.getInstance();
                calendarEndTime.add(Calendar.MONTH,Integer.parseInt(listLoaiTheTap.get(position).getHanSuDung()));
                yearEnd = calendarEndTime.get(Calendar.YEAR);
                monthEnd = calendarEndTime.get(Calendar.MONTH)+1;
                dayEnd = calendarEndTime.get(Calendar.DAY_OF_MONTH);
                edt_endtime.setText(dayEnd+"-"+monthEnd+"-"+yearEnd);
                TongTienGiaTheTap = DuAn1DataBase.getInstance(TheTapCuaToi_MainActivity_HocVien.this).loaiTheTapDAO().getByID(String.valueOf(idloaithetap)).get(0).getGia();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        edt_starttime.setText(day+"-"+month+"-"+year);


        btn_huy.setOnClickListener(v -> {
            dialog.cancel();
        });
        btn_add.setOnClickListener(v -> {
            new AlertDialog.Builder(this).setTitle("Bạn có chắc chắn muốn gia hạn gói tập")
                            .setMessage("Giá của gói tập bạn chọn là "+TongTienGiaTheTap
                            +" vnđ và chúng tôi sẽ trừ "+TongTienGiaTheTap+" vnđ trong tài khoản của bạn !")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (TongTienGiaTheTap > DuAn1DataBase.getInstance(TheTapCuaToi_MainActivity_HocVien.this).khachHangDAO().checkAcc(theTap.getKhachhang_id()).get(0).getSoDu()){
                                        Toast.makeText(TheTapCuaToi_MainActivity_HocVien.this, "Số dư không đủ vui lòng kiểm tra lại tài khoản", Toast.LENGTH_SHORT).show();
                                    }else {
                                        theTap.setLoaithetap_id(idloaithetap);
                                        theTap.setNgayDangKy(edt_starttime.getText().toString().trim());
                                        theTap.setNgayHetHan(edt_endtime.getText().toString().trim());
                                        theTap.setTongsotiendamuathetap(DuAn1DataBase.getInstance(TheTapCuaToi_MainActivity_HocVien.this).theTapDAO().getTongSoTien(theTap.getKhachhang_id())+
                                                DuAn1DataBase.getInstance(TheTapCuaToi_MainActivity_HocVien.this).loaiTheTapDAO().getByID(String.valueOf(idloaithetap)).get(0).getGia());
                                        DuAn1DataBase.getInstance(TheTapCuaToi_MainActivity_HocVien.this).theTapDAO().update(theTap);
                                        Toast.makeText(TheTapCuaToi_MainActivity_HocVien.this, "Gia hạn thẻ tập thành công", Toast.LENGTH_SHORT).show();
                                        capnhat();
                                        dialog.dismiss();
                                    }
                                }
                            })
                            .setNegativeButton("No",null)
                            .show();
        });
    }

    private void initUi() {
        rcy_thetap = findViewById(R.id.rcy_thetap_hocvien);
        rcy_thetapthu = findViewById(R.id.rcy_thetapthu_hocvien);
        img_back = findViewById(R.id.img_back_thetapcuatoi);
    }

}