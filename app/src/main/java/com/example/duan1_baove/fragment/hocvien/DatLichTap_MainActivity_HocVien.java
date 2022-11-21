package com.example.duan1_baove.fragment.hocvien;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.duan1_baove.HocVien_MainActivity;
import com.example.duan1_baove.R;
import com.example.duan1_baove.adapter.SpinnerAdapterPT;
import com.example.duan1_baove.database.DuAn1DataBase;
import com.example.duan1_baove.model.Admin;
import com.example.duan1_baove.model.DatLichTap;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class DatLichTap_MainActivity_HocVien extends AppCompatActivity {
    private EditText edt_name,edt_ngaydat,edt_ngaytap,edt_giotap,edt_gionghi;
    private TextInputLayout txt_ngay,txt_gio,txt_gionghi;
    private CheckBox cbo_pt;
    private Spinner spn_pt;
    private Button btn_huy,btn_dangky;
    private ImageView img_back;
    Calendar lich = Calendar.getInstance();
    SimpleDateFormat sdfvn = new SimpleDateFormat("dd-MM-yyyy, hh:mm:ss");
    String idpt;
    List<DatLichTap> listTheTapPT;
    Calendar lichNghi,lichTap,lichSelectTap,lichSelectNghi;
    List<Admin> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dat_lich_tap_main_hoc_vien);
        initUi();

        edt_giotap.addTextChangedListener(textWatcher);
        edt_ngaytap.addTextChangedListener(textWatcher);
        edt_gionghi.addTextChangedListener(textWatcher);
        edt_name.setText(DuAn1DataBase.getInstance(this).khachHangDAO().getName(HocVien_MainActivity.userHocVien));
        edt_ngaydat.setText(sdfvn.format(new Date()));

        txt_ngay.setStartIconOnClickListener(v -> {
            int year = lich.get(Calendar.YEAR);
            int month = lich.get(Calendar.MONTH);
            int day = lich.get(Calendar.DAY_OF_MONTH);
            new DatePickerDialog(DatLichTap_MainActivity_HocVien.this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    edt_ngaytap.setText(dayOfMonth+"-"+(month+1)+"-"+year);
                }
            },year,month,day).show();

        });

        txt_gio.setStartIconOnClickListener(v -> {
            int hour = lich.get(Calendar.HOUR);
            int minute = lich.get(Calendar.MINUTE);
            new TimePickerDialog(DatLichTap_MainActivity_HocVien.this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    edt_giotap.setText(hourOfDay+":"+minute);
                }
            },hour,minute,true).show();
        });

        txt_gionghi.setStartIconOnClickListener(v -> {
            int hour = lich.get(Calendar.HOUR);
            int minute = lich.get(Calendar.MINUTE);
            new TimePickerDialog(DatLichTap_MainActivity_HocVien.this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    edt_gionghi.setText(hourOfDay+":"+minute);
                }
            },hour,minute,true).show();
        });

        cbo_pt.setOnClickListener(v -> {
            if (cbo_pt.isChecked()){
                spn_pt.setVisibility(View.VISIBLE);
                spn_pt.setEnabled(true);
            }else {
                spn_pt.setVisibility(View.INVISIBLE);
                spn_pt.setEnabled(false);
            }
        });

        img_back.setOnClickListener(v -> {
            onBackPressed();
        });

        btn_huy.setOnClickListener(v -> {
            onBackPressed();
        });

        btn_dangky.setOnClickListener(v -> {
            DatLichTap datLichTap = new DatLichTap();
            datLichTap.setThoigiandat(sdfvn.format(new Date()));
            datLichTap.setNgaytap(edt_ngaytap.getText().toString().trim());
            datLichTap.setGiotap(edt_giotap.getText().toString().trim());
            datLichTap.setGionghi(edt_gionghi.getText().toString().trim());
            datLichTap.setKhachhang_id(HocVien_MainActivity.userHocVien);
            if (cbo_pt.isChecked()){
                datLichTap.setAdmin_id(idpt);
            }else {
                datLichTap.setAdmin_id("admin");
            }
            DuAn1DataBase.getInstance(this).datLichTapDAO().insert(datLichTap);
            Toast.makeText(this, "Đặt lịch thành công", Toast.LENGTH_SHORT).show();
            edt_giotap.setText("");
            edt_ngaytap.setText("");
            edt_gionghi.setText("");
            cbo_pt.setChecked(false);
        });
    }

    private void initUi() {
        edt_name = findViewById(R.id.edt_name_datlichtap);
        edt_ngaydat = findViewById(R.id.edt_ngaydat_datlichtap);
        edt_ngaytap = findViewById(R.id.edt_chonngaytap_datlichtap);
        edt_giotap = findViewById(R.id.edt_chongiotap_datlichtap);
        txt_ngay = findViewById(R.id.txti_chonngaytap_datlichtap);
        txt_gio = findViewById(R.id.txti_chongiotap_datlichtap);
        cbo_pt = findViewById(R.id.cbo_pt_datlichtap);
        spn_pt = findViewById(R.id.spn_pt_datlichtap);
        btn_huy = findViewById(R.id.btn_huy_datlichtap);
        btn_dangky = findViewById(R.id.btn_dangki_datlichtap);
        img_back = findViewById(R.id.img_back_datlichtap);
        edt_gionghi = findViewById(R.id.edt_chongionghi_datlichtap);
        txt_gionghi = findViewById(R.id.txti_chongionghi_datlichtap);
    }
    private TextWatcher textWatcher= new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String ngay = edt_ngaytap.getText().toString().trim();
            String gio = edt_giotap.getText().toString().trim();
            String gionghi = edt_gionghi.getText().toString().trim();
            if(ngay.isEmpty()||gio.isEmpty()||gionghi.isEmpty()){
                btn_dangky.setEnabled(false);
                btn_dangky.setBackground(getDrawable(R.drawable.bg_gray));
            }else {
                btn_dangky.setEnabled(true);
                btn_dangky.setBackground(getDrawable(R.drawable.bg_green));
                if (DuAn1DataBase.getInstance(DatLichTap_MainActivity_HocVien.this).donHangChiTietDAO().checkDichVu(HocVien_MainActivity.userHocVien).size()<=0){
                    cbo_pt.setEnabled(false);
                }else {
                    cbo_pt.setEnabled(true);
                }
                checkpt();
            }

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
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
    public int[] getArrayTime(String time){
        String[] str = time.split(":");
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


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Animatoo.INSTANCE.animateSlideRight(DatLichTap_MainActivity_HocVien.this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
    private void checkpt(){
        listTheTapPT = DuAn1DataBase.getInstance(DatLichTap_MainActivity_HocVien.this).datLichTapDAO().checkPTByTime();
        list = new ArrayList<>();
        list = DuAn1DataBase.getInstance(this).adminDAO().getPT("PT");
        for (int i=0;i<listTheTapPT.size();i++){
            String ngay = edt_ngaytap.getText().toString().trim();
            String gio = edt_giotap.getText().toString().trim();
            String gionghi = edt_gionghi.getText().toString().trim();

            lichNghi = Calendar.getInstance();
            lichTap = Calendar.getInstance();
            lichSelectTap = Calendar.getInstance();
            lichSelectNghi = Calendar.getInstance();

            lichTap.set(Calendar.DAY_OF_MONTH,getArrayDate(listTheTapPT.get(i).getNgaytap())[0]);
            lichTap.set(Calendar.MONTH,getArrayDate(listTheTapPT.get(i).getNgaytap())[1]);
            lichTap.set(Calendar.YEAR,getArrayDate(listTheTapPT.get(i).getNgaytap())[2]);
            lichTap.set(Calendar.HOUR_OF_DAY,getArrayTime(listTheTapPT.get(i).getGiotap())[0]);
            lichTap.set(Calendar.MINUTE,getArrayTime(listTheTapPT.get(i).getGiotap())[1]);

            lichNghi.set(Calendar.DAY_OF_MONTH,getArrayDate(listTheTapPT.get(i).getNgaytap())[0]);
            lichNghi.set(Calendar.MONTH,getArrayDate(listTheTapPT.get(i).getNgaytap())[1]);
            lichNghi.set(Calendar.YEAR,getArrayDate(listTheTapPT.get(i).getNgaytap())[2]);
            lichNghi.set(Calendar.HOUR_OF_DAY,getArrayTime(listTheTapPT.get(i).getGionghi())[0]);
            lichNghi.set(Calendar.MINUTE,getArrayTime(listTheTapPT.get(i).getGionghi())[1]);

            lichSelectTap.set(Calendar.DAY_OF_MONTH,getArrayDate(ngay)[0]);
            lichSelectTap.set(Calendar.MONTH,getArrayDate(ngay)[1]);
            lichSelectTap.set(Calendar.YEAR,getArrayDate(ngay)[2]);
            lichSelectTap.set(Calendar.HOUR_OF_DAY,getArrayTime(gio)[0]);
            lichSelectTap.set(Calendar.MINUTE,getArrayTime(gio)[1]);

            lichSelectNghi.set(Calendar.DAY_OF_MONTH,getArrayDate(ngay)[0]);
            lichSelectNghi.set(Calendar.MONTH,getArrayDate(ngay)[1]);
            lichSelectNghi.set(Calendar.YEAR,getArrayDate(ngay)[2]);
            lichSelectNghi.set(Calendar.HOUR_OF_DAY,getArrayTime(gionghi)[0]);
            lichSelectNghi.set(Calendar.MINUTE,getArrayTime(gionghi)[1]);

            if (lichSelectTap.after(lichTap) && lichSelectTap.before(lichNghi) || lichSelectNghi.after(lichTap)&& lichSelectNghi.before(lichNghi) || lichSelectTap.before(lichTap) && lichSelectNghi.after(lichNghi) || lichSelectTap.compareTo(lichTap) ==0&& lichSelectNghi.compareTo(lichNghi) == 0 || lichSelectTap.compareTo(lichTap)==0 && lichSelectNghi.after(lichNghi) || lichSelectTap.before(lichTap) && lichSelectNghi.compareTo(lichNghi)==0){
                for (int i1=0;i1<list.size();i1++){
                    if (list.get(i1).getUser().equals(listTheTapPT.get(i).getAdmin_id())){
                        list.remove(i1);
                    }
                }
            }
        }
        spinerPT();
    }
    private void spinerPT(){
        SpinnerAdapterPT adapterPT = new SpinnerAdapterPT(this,R.layout.item_spiner_naptien,list);
        spn_pt.setAdapter(adapterPT);
        spn_pt.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                idpt = list.get(position).getUser();
                Toast.makeText(DatLichTap_MainActivity_HocVien.this, idpt, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}