package com.example.duan1_baove.fragment.hocvien;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.duan1_baove.HocVien_MainActivity;
import com.example.duan1_baove.R;
import com.example.duan1_baove.adapter.DangKiTheTapThuAdapter;
import com.example.duan1_baove.adapter.DatLichTapAdapter;
import com.example.duan1_baove.adapter.SpinnerAdapterLoaiTheTap;
import com.example.duan1_baove.adapter.TheTapAdapter;
import com.example.duan1_baove.database.DuAn1DataBase;
import com.example.duan1_baove.model.Admin;
import com.example.duan1_baove.model.DangKiTapThu;
import com.example.duan1_baove.model.DatLichTap;
import com.example.duan1_baove.model.LoaiTheTap;
import com.example.duan1_baove.model.TheTap;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class TheTapCuaToi_MainActivity_HocVien extends AppCompatActivity {
    private RecyclerView rcy_thetapthu,rcy_lichtap;
    private DangKiTheTapThuAdapter adaptertapthu;
    private DatLichTapAdapter adapterdatlichtap;
    private ImageView img_back;
    private Button btn_datlichtap;
    private List<DangKiTapThu> listTheTapThu;
    private List<TheTap> listTheTap;
    private List<DatLichTap> listDatlichtap;

    private EditText edt_name,edt_starttime,edt_endtime;
    private Spinner spn_loaithetap;
    private Button btn_add,btn_huy;
    private List<LoaiTheTap> listLoaiTheTap;
    private TextView tv_name,tv_loaithe,tv_starttime,tv_endtime,tv_mathetap,tv_homnay,tv_hsd,tv_trangthai;

    private LinearLayout layout_giahan;
    private Button btn_giahan;

    int idloaithetap;
    private Calendar calendar = Calendar.getInstance();
    int year = calendar.get(Calendar.YEAR);
    int month = calendar.get(Calendar.MONTH)+1;
    int day = calendar.get(Calendar.DAY_OF_MONTH);
    private Calendar calendarEndTime = Calendar.getInstance();
    int yearEnd,monthEnd, dayEnd;
    int TongTienGiaTheTap;
    long songay;
    String starttimeold;
    String endtimenew;
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    Calendar now,end;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_the_tap_cua_toi_main_hoc_vien);
        initUi();
        capnhat();
        if (DuAn1DataBase.getInstance(this).theTapDAO().checkTheTap(HocVien_MainActivity.userHocVien).size()<=0){
            btn_datlichtap.setEnabled(false);
            btn_datlichtap.setBackground(getDrawable(R.drawable.bg_gray));
        }else {
            btn_datlichtap.setEnabled(true);
            btn_datlichtap.setBackground(getDrawable(R.drawable.custom_btn));
        }

        img_back.setOnClickListener(v -> {
            onBackPressed();
        });
        btn_datlichtap.setOnClickListener(v -> {
            Intent intent = new Intent(TheTapCuaToi_MainActivity_HocVien.this,DatLichTap_MainActivity_HocVien.class);
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
            Animatoo.INSTANCE.animateSwipeLeft(TheTapCuaToi_MainActivity_HocVien.this);
        });

    }
    private void initUi() {
        rcy_thetapthu = findViewById(R.id.rcy_thetapthu_hocvien);
        img_back = findViewById(R.id.img_back_thetapcuatoi);
        btn_datlichtap = findViewById(R.id.btn_datlichtap_thetapcuatoi);
        rcy_lichtap = findViewById(R.id.rcy_lichtap_hocvien);
        tv_name = findViewById(R.id.tv_tenkhachhang_thetapcuatoi);
        tv_starttime = findViewById(R.id.tv_starttime_thetapcuatoi);
        tv_endtime = findViewById(R.id.tv_endtime_thetapcuatoi);
        tv_mathetap = findViewById(R.id.tv_mathetap_thetapcuatoi);
        tv_homnay = findViewById(R.id.tv_ngayhomnay_thetapcuatoi);
        tv_hsd = findViewById(R.id.tv_songay_thetapcuatoi);
        tv_trangthai = findViewById(R.id.tv_trangthai_thetapcuatoi);
        layout_giahan = findViewById(R.id.layout_giahan_thetapcuatoi);
        btn_giahan = findViewById(R.id.btn_giahan_thetapcuatoi);
    }
    private void capnhat() {
        now = Calendar.getInstance();
        now.add(Calendar.MONTH ,1);
        end = Calendar.getInstance();
        listTheTap = DuAn1DataBase.getInstance(this).theTapDAO().checkTheTap(HocVien_MainActivity.userHocVien);
        if (listTheTap.size()<=0){
            tv_name.setText("Họ tên: Chưa có thẻ tập");
            tv_starttime.setText("Từ ngày: __-__-____");
            tv_endtime.setText("Đến ngày: __-__-____");
            tv_mathetap.setText("Mã thẻ tập: __");
            tv_hsd.setText("Còn: __");
            tv_trangthai.setText("Trạng thái: _____");
            tv_homnay.setText("Hôm nay: __-__-____");
        }else {
            tv_name.setText("Họ tên: "+DuAn1DataBase.getInstance(this).khachHangDAO().getName(HocVien_MainActivity.userHocVien));
            starttimeold = listTheTap.get(0).getNgayDangKy();
            endtimenew = listTheTap.get(0).getNgayHetHan();
            for (int i=1;i<listTheTap.size();i++){
                starttimeold = getMin(starttimeold,listTheTap.get(i).getNgayDangKy());
                endtimenew = getMax(endtimenew,listTheTap.get(i).getNgayHetHan());

            }
            tv_starttime.setText("Từ ngày: "+starttimeold);
            tv_endtime.setText("Đến ngày: "+endtimenew);
            tv_mathetap.setText("Mã thẻ tập: "+listTheTap.get(0).getId());
            tv_homnay.setText("Hôm nay: "+sdf.format(new Date()));
            end.set(Calendar.DAY_OF_MONTH,getArrayDate(endtimenew)[0]);
            end.set(Calendar.MONTH,getArrayDate(endtimenew)[1]);
            end.set(Calendar.YEAR,getArrayDate(endtimenew)[2]);
            songay = end.getTime().getTime()-now.getTime().getTime();
            tv_hsd.setText("Còn: "+ TimeUnit.DAYS.convert(songay,TimeUnit.MILLISECONDS));
            if ( TimeUnit.DAYS.convert(songay,TimeUnit.MILLISECONDS)<=0){
                tv_trangthai.setText("Trạng thái: hết hạn");
                tv_trangthai.setTextColor(Color.RED);
                btn_datlichtap.setEnabled(false);
                btn_datlichtap.setBackground(getDrawable(R.drawable.bg_gray));

                tv_homnay.setEnabled(false);
                tv_name.setEnabled(false);
                tv_hsd.setEnabled(false);
                tv_trangthai.setEnabled(false);
                tv_endtime.setEnabled(false);
                tv_starttime.setEnabled(false);
                tv_mathetap.setEnabled(false);
                layout_giahan.setVisibility(View.VISIBLE);
                btn_giahan.setVisibility(View.VISIBLE);
                btn_giahan.setEnabled(true);
            }else {
                tv_trangthai.setText("Trạng thái: Chưa hết hạn");
                tv_trangthai.setTextColor(Color.GREEN);
                btn_datlichtap.setEnabled(true);
                btn_datlichtap.setBackground(getDrawable(R.drawable.custom_btn));
                tv_homnay.setEnabled(true);
                tv_name.setEnabled(true);
                tv_hsd.setEnabled(true);
                tv_trangthai.setEnabled(true);
                tv_endtime.setEnabled(true);
                tv_starttime.setEnabled(true);
                tv_mathetap.setEnabled(true);
                layout_giahan.setVisibility(View.INVISIBLE);
                btn_giahan.setVisibility(View.INVISIBLE);
                btn_giahan.setEnabled(false);
            }
        }



        listTheTapThu = DuAn1DataBase.getInstance(this).dangKiTapThuDAO().check(HocVien_MainActivity.userHocVien);
        adaptertapthu = new DangKiTheTapThuAdapter(this);
        adaptertapthu.setData(listTheTapThu);
        LinearLayoutManager manager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        rcy_thetapthu.setLayoutManager(manager);
        rcy_thetapthu.setAdapter(adaptertapthu);

        listDatlichtap = DuAn1DataBase.getInstance(this).datLichTapDAO().getByIdKhachhang(HocVien_MainActivity.userHocVien);
        adapterdatlichtap = new DatLichTapAdapter(this, new DatLichTapAdapter.IClickListener() {
            @Override
            public void thongtinhocvien(DatLichTap datLichTap) {

            }

            @Override
            public void thongtinpt(DatLichTap datLichTap) {
                Admin admin = DuAn1DataBase.getInstance(TheTapCuaToi_MainActivity_HocVien.this).adminDAO().getObject(datLichTap.getAdmin_id());
                Dialog dialog = new Dialog(TheTapCuaToi_MainActivity_HocVien.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_thongtinpt);
                dialog.show();
                Window window = dialog.getWindow();
                if (window==null){
                    return;
                }
                window.setBackgroundDrawable(null);
                TextView tv_name = dialog.findViewById(R.id.tv_name_itemthongtinpt);
                TextView tv_chucvu = dialog.findViewById(R.id.tv_chucvu_itemthongtinpt);
                ImageView avt = dialog.findViewById(R.id.avt_itemthongtinpt);
                if (admin.getHinhanh()==null){
                    avt.setImageResource(R.drawable.ic_account);
                }else {
                    String linkimg = admin.getHinhanh();
                    Log.d("adapter",linkimg+" link");
                    avt.setImageDrawable(Drawable.createFromPath(linkimg));
                }
                tv_name.setText("Tên: "+admin.getName());
                tv_chucvu.setText("Nghề nghiệp: "+DuAn1DataBase.getInstance(TheTapCuaToi_MainActivity_HocVien.this).chucVuDAO().chechChucVu(String.valueOf(admin.getChucvu_id())));
            }
        });
        adapterdatlichtap.setData(listDatlichtap);
        LinearLayoutManager managerdatlichtap = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        rcy_lichtap.setLayoutManager(managerdatlichtap);
        rcy_lichtap.setAdapter(adapterdatlichtap);
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



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Animatoo.INSTANCE.animateSlideRight(TheTapCuaToi_MainActivity_HocVien.this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        capnhat();
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
    public String getMin(String date1,String date2){
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
            return date2;
        }else {
            return date1;
        }
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