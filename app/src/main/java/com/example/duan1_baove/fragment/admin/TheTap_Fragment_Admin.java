package com.example.duan1_baove.fragment.admin;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.duan1_baove.R;
import com.example.duan1_baove.adapter.NhanVienAdapter;
import com.example.duan1_baove.adapter.SpinnerAdapterLoaiTheTap;
import com.example.duan1_baove.adapter.SpinnerAdapterNapTien;
import com.example.duan1_baove.adapter.TheTapAdapter;
import com.example.duan1_baove.database.DuAn1DataBase;
import com.example.duan1_baove.fragment.hocvien.TheTapCuaToi_MainActivity_HocVien;
import com.example.duan1_baove.model.KhachHang;
import com.example.duan1_baove.model.LichSuGiaoDich;
import com.example.duan1_baove.model.LoaiTheTap;
import com.example.duan1_baove.model.TheTap;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class TheTap_Fragment_Admin extends Fragment {
    private List<TheTap> list;
    private View view;
    private RecyclerView recyclerView;
    private LinearLayout layout_search;
    private EditText edt_search;
    private FloatingActionButton fab1,fab2,fab3;
    private Animation fab_open,fab_close,rotateForward,rotateBackward;
    boolean isOpen;

    private TheTapAdapter adapter;
    private TheTap theTap;
    private EditText edt_id,edt_starttime,edt_endtime;
    private Spinner spn_khachhang,spn_loaithetap,spn_tinhtrangthetap;
    private Button btn_add,btn_huy;
    private List<KhachHang> listKhachHang;
    private List<LoaiTheTap> listLoaiTheTap;
    private Calendar calendar = Calendar.getInstance();
    int year = calendar.get(Calendar.YEAR);
    int month = calendar.get(Calendar.MONTH)+1;
    int day = calendar.get(Calendar.DAY_OF_MONTH);
    private Calendar calendarEndTime = Calendar.getInstance();
    private String strKhachHangID;
    private int intLoaiTheTap;
    int yearEnd,monthEnd, dayEnd;

    String[] tinhtrang = {"Tất cả","Sắp hết hạn"};
    String strTinhTrang = "Tất cả";

    private EditText edt_name_giahan,edt_starttime_giahan,edt_endtime_giahan;
    private Spinner spn_loaithetap_giahan;
    private Button btn_add_giahan,btn_huy_giahan;
    private List<LoaiTheTap> listLoaiTheTap_giahan;
    int idloaithetap;
    private Calendar calendar_giahan = Calendar.getInstance();
    int year_giahan = calendar_giahan.get(Calendar.YEAR);
    int month_giahan = calendar_giahan.get(Calendar.MONTH)+1;
    int day_giahan = calendar_giahan.get(Calendar.DAY_OF_MONTH);
    private Calendar calendarEndTime_giahan = Calendar.getInstance();
    int yearEnd_giahan,monthEnd_giahan, dayEnd_giahan;
    int TongTienGiaTheTap;
    SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy, hh:mm:ss");
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    Calendar lichsearch = Calendar.getInstance();
    Calendar lichend1 = Calendar.getInstance();
    int daysearch,monthsearch,yearsearch;
    int yearend1,monthend1,dayend1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_the_tap___admin, container, false);

        initUi();
        capNhat();

        ArrayAdapter adapterSpntinhtrang = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_dropdown_item,tinhtrang);
        spn_tinhtrangthetap.setAdapter(adapterSpntinhtrang);
        spn_tinhtrangthetap.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                strTinhTrang = tinhtrang[position];
                if (strTinhTrang.equals("Tất cả")){
                    capNhat();
                }else if (strTinhTrang.equals("Sắp hết hạn")){
                    sapHetHan();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        fab1.setOnClickListener(v -> {
            animateFab();
        });
        fab2.setOnClickListener(v -> {
            layout_search.setVisibility(View.VISIBLE);
            animateFab();
        });
        fab3.setOnClickListener(v -> {
            add();
            animateFab();
        });
        edt_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH){
                    search(edt_search.getText().toString().trim());
                    Log.d("testsearch",edt_search.getText().toString().trim()+" Fragment");
                    hideSoftKeyBroad();
                }
                return false;
            }
        });
        return view;
    }
    private void initUi() {
        recyclerView = view.findViewById(R.id.rcy_thetap);
        fab1 = view.findViewById(R.id.fab1_thetap);
        fab2 = view.findViewById(R.id.fab2_thetap);
        fab3 = view.findViewById(R.id.fab3_thetap);
        fab_open = AnimationUtils.loadAnimation(getContext(),R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getContext(),R.anim.fab_close);
        rotateForward = AnimationUtils.loadAnimation(getContext(),R.anim.rotate_forward);
        rotateBackward = AnimationUtils.loadAnimation(getContext(),R.anim.rotate_backward);
        layout_search = view.findViewById(R.id.layout_search_thetap);
        edt_search = view.findViewById(R.id.edt_search_thetap);
        spn_tinhtrangthetap = view.findViewById(R.id.spn_tinhtrangthetap);
    }

    private void search(String search) {
        adapter.getFilter().filter(search);
    }

    private void animateFab() {
        if (isOpen){
            fab1.startAnimation(rotateForward);
            fab2.startAnimation(fab_close);
            fab3.startAnimation(fab_close);
            fab2.setClickable(false);
            fab3.setClickable(false);
            fab1.setImageResource(R.drawable.ic_menu);
            isOpen = false;
        }else {
            fab1.startAnimation(rotateBackward);
            fab2.startAnimation(fab_open);
            fab3.startAnimation(fab_open);
            fab2.setClickable(true);
            fab3.setClickable(true);
            fab1.setImageResource(R.drawable.ic_close);
            isOpen = true;
        }
    }
    private void add() {
        Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_addthetap);
        dialog.show();
        Window window = dialog.getWindow();
        if (window == null){
            return;
        }
        window.setBackgroundDrawable(null);
        edt_id = dialog.findViewById(R.id.edt_mathetap_dialogthetap);
        edt_starttime = dialog.findViewById(R.id.edt_starttime_dialogthetap);
        edt_endtime = dialog.findViewById(R.id.edt_endtime_dialogthetap);
        btn_add = dialog.findViewById(R.id.btn_luu_dialogthetap);
        btn_huy = dialog.findViewById(R.id.btn_huy_dialogthetap);
        spn_khachhang = dialog.findViewById(R.id.spn_tenkhachhang_dialogthetap);
        spn_loaithetap = dialog.findViewById(R.id.spn_loaithetap_dialogthetap);

        listKhachHang = DuAn1DataBase.getInstance(getContext()).khachHangDAO().getAll();
        SpinnerAdapterNapTien spinnerKhachHang = new SpinnerAdapterNapTien(getContext(),R.layout.item_spiner_naptien,listKhachHang);
        spn_khachhang.setAdapter(spinnerKhachHang);
        spn_khachhang.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                strKhachHangID = listKhachHang.get(position).getSoDienThoai();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        listLoaiTheTap = DuAn1DataBase.getInstance(getContext()).loaiTheTapDAO().getAll();
        SpinnerAdapterLoaiTheTap spinnerLoaiTheTap = new SpinnerAdapterLoaiTheTap(getContext(),R.layout.item_spiner_naptien,listLoaiTheTap);
        spn_loaithetap.setAdapter(spinnerLoaiTheTap);
        spn_loaithetap.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                intLoaiTheTap = listLoaiTheTap.get(position).getId();
                calendarEndTime = Calendar.getInstance();
                calendarEndTime.add(Calendar.MONTH,Integer.parseInt(listLoaiTheTap.get(position).getHanSuDung()));
                yearEnd = calendarEndTime.get(Calendar.YEAR);
                monthEnd = calendarEndTime.get(Calendar.MONTH)+1;
                dayEnd = calendarEndTime.get(Calendar.DAY_OF_MONTH);
                edt_endtime.setText(dayEnd+"-"+monthEnd+"-"+yearEnd);
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
            if (validate(strKhachHangID)){
                theTap = new TheTap();
                theTap.setKhachhang_id(strKhachHangID);
                theTap.setLoaithetap_id(intLoaiTheTap);
                theTap.setNgayDangKy(edt_starttime.getText().toString().trim());
                theTap.setNgayHetHan(edt_endtime.getText().toString().trim());
                theTap.setTongsotiendamuathetap(DuAn1DataBase.getInstance(getContext()).theTapDAO().getTongSoTien(strKhachHangID)+DuAn1DataBase.getInstance(getContext()).loaiTheTapDAO().getByID(String.valueOf(intLoaiTheTap)).get(0).getGia());
                DuAn1DataBase.getInstance(getContext()).theTapDAO().insert(theTap);
                Toast.makeText(getContext(), "Insert thẻ tập thành công", Toast.LENGTH_SHORT).show();
                capNhat();
                dialog.dismiss();
            }
        });
    }
    private void sapHetHan(){
        daysearch = lichsearch.get(Calendar.DAY_OF_MONTH);
        monthsearch = lichsearch.get(Calendar.MONTH)+1;
        yearsearch = lichsearch.get(Calendar.YEAR);

        List<TheTap> list1 = DuAn1DataBase.getInstance(getContext()).theTapDAO().getAll();
        for (int i=0;i<list1.size();i++){
            lichend1.set(Calendar.DAY_OF_MONTH,getArrayDate(list1.get(i).getNgayHetHan())[0]);
            lichend1.set(Calendar.MONTH,getArrayDate(list1.get(i).getNgayHetHan())[1]);
            lichend1.set(Calendar.YEAR,getArrayDate(list1.get(i).getNgayHetHan())[2]);

            yearend1 = lichend1.get(Calendar.YEAR);
            monthend1 = lichend1.get(Calendar.MONTH);
            dayend1 = lichend1.get(Calendar.DAY_OF_MONTH);
            try {
                Date datenow = sdf.parse(yearsearch+"-"+monthsearch+"-"+daysearch);
                Date dateend = sdf.parse(yearend1+"-"+monthend1+"-"+dayend1);
                long day1 = dateend.getTime() - datenow.getTime();
                Log.v("abcd", TimeUnit.DAYS.convert(day1, TimeUnit.MILLISECONDS)+"soos ngay");
                if ( TimeUnit.DAYS.convert(day1, TimeUnit.MILLISECONDS)<=10){
                    list = new ArrayList<>();
                    list.add(list1.get(i));
                    adapter = new TheTapAdapter(getActivity(), new TheTapAdapter.IClickListener() {
                        @Override
                        public void giahan(TheTap theTap) {
                            opendialog(theTap);
                        }
                    });
                    adapter.setData(list);
                    LinearLayoutManager manager = new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false);
                    recyclerView.setLayoutManager(manager);
                    recyclerView.setAdapter(adapter);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }

    }

    private void capNhat(){
        list = DuAn1DataBase.getInstance(getContext()).theTapDAO().getAll();
        adapter = new TheTapAdapter(getActivity(), new TheTapAdapter.IClickListener() {
            @Override
            public void giahan(TheTap theTap) {
                opendialog(theTap);
            }
        });
        adapter.setData(list);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
    }
    private boolean validate(String str){
        List<TheTap> list = DuAn1DataBase.getInstance(getContext()).theTapDAO().checkTheTap(str);
        if (list.size()>0){
            Toast.makeText(getContext(), "Người dùng đã đăng kí", Toast.LENGTH_SHORT).show();
            return false;
        }else {
            return true;
        }
    }
    private void opendialog(TheTap theTap) {
        Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_giahangoitap);
        dialog.show();
        Window window = dialog.getWindow();
        if (window==null){
            return;
        }
        window.setBackgroundDrawable(null);
        edt_name_giahan = dialog.findViewById(R.id.edt_name_giahanthetap);
        edt_endtime_giahan = dialog.findViewById(R.id.edt_endtime_giahanthetap);
        edt_starttime_giahan = dialog.findViewById(R.id.edt_starttime_giahanthetap);
        spn_loaithetap_giahan = dialog.findViewById(R.id.spn_loaithetap_giahanthetap);
        btn_add_giahan = dialog.findViewById(R.id.btn_luu_giahanthetap);
        btn_huy_giahan = dialog.findViewById(R.id.btn_huy_giahanthetap);

        edt_name_giahan.setText(DuAn1DataBase.getInstance(getContext()).khachHangDAO().checkAcc(theTap.getKhachhang_id()).get(0).getHoten());

        listLoaiTheTap_giahan = DuAn1DataBase.getInstance(getContext()).loaiTheTapDAO().getAll();
        SpinnerAdapterLoaiTheTap spinnerLoaiTheTapgiahan = new SpinnerAdapterLoaiTheTap(getContext(),R.layout.item_spiner_naptien,listLoaiTheTap_giahan);
        spn_loaithetap_giahan.setAdapter(spinnerLoaiTheTapgiahan);
        spn_loaithetap_giahan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                idloaithetap = listLoaiTheTap_giahan.get(position).getId();
                calendarEndTime_giahan = Calendar.getInstance();
                calendarEndTime_giahan.add(Calendar.MONTH,Integer.parseInt(listLoaiTheTap_giahan.get(position).getHanSuDung()));
                yearEnd_giahan = calendarEndTime_giahan.get(Calendar.YEAR);
                monthEnd_giahan = calendarEndTime_giahan.get(Calendar.MONTH)+1;
                dayEnd_giahan = calendarEndTime_giahan.get(Calendar.DAY_OF_MONTH);
                edt_endtime_giahan.setText(dayEnd_giahan+"-"+monthEnd_giahan+"-"+yearEnd_giahan);
                TongTienGiaTheTap = DuAn1DataBase.getInstance(getContext()).loaiTheTapDAO().getByID(String.valueOf(idloaithetap)).get(0).getGia();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        edt_starttime_giahan.setText(day_giahan+"-"+month_giahan+"-"+year_giahan);


        btn_huy_giahan.setOnClickListener(v -> {
            dialog.cancel();
        });
        btn_add_giahan.setOnClickListener(v -> {
            EditText editText = new EditText(getContext());
            editText.setBackground(getContext().getDrawable(R.drawable.custom_edt));
            new AlertDialog.Builder(getContext()).setTitle("Mật khẩu")
                    .setMessage("Vui lòng nhập mật khẩu")
                    .setView(editText)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog1, int which) {
                            if (editText.getText().toString().trim().equals(DuAn1DataBase.getInstance(getContext()).khachHangDAO().getPass(theTap.getKhachhang_id()))){
                                if (TongTienGiaTheTap > DuAn1DataBase.getInstance(getContext()).khachHangDAO().checkAcc(theTap.getKhachhang_id()).get(0).getSoDu()){
                                    Toast.makeText(getContext(), "Số dư không đủ vui lòng kiểm tra lại tài khoản", Toast.LENGTH_SHORT).show();
                                }else {
                                    theTap.setLoaithetap_id(idloaithetap);
                                    theTap.setNgayDangKy(edt_starttime_giahan.getText().toString().trim());
                                    theTap.setNgayHetHan(edt_endtime_giahan.getText().toString().trim());
                                    theTap.setTongsotiendamuathetap(DuAn1DataBase.getInstance(getContext()).theTapDAO().getTongSoTien(theTap.getKhachhang_id())+
                                    DuAn1DataBase.getInstance(getContext()).loaiTheTapDAO().getByID(String.valueOf(idloaithetap)).get(0).getGia());
                                    DuAn1DataBase.getInstance(getContext()).theTapDAO().update(theTap);
                                    Toast.makeText(getContext(), "Gia hạn thẻ tập thành công", Toast.LENGTH_SHORT).show();
                                    KhachHang khachHang = DuAn1DataBase.getInstance(getContext()).khachHangDAO().checkAcc(theTap.getKhachhang_id()).get(0);
                                    khachHang.setSoDu(khachHang.getSoDu()-TongTienGiaTheTap);
                                    DuAn1DataBase.getInstance(getContext()).khachHangDAO().update(khachHang);
                                    LichSuGiaoDich lichSuGiaoDich = new LichSuGiaoDich();
                                    lichSuGiaoDich.setSoTien(TongTienGiaTheTap);
                                    lichSuGiaoDich.setType("Trừ");
                                    lichSuGiaoDich.setKhachang_id(theTap.getKhachhang_id());
                                    lichSuGiaoDich.setThoigian(format.format(new Date()));
                                    DuAn1DataBase.getInstance(getContext()).lichSuGiaoDichDAO().insert(lichSuGiaoDich);
                                    capNhat();
                                    dialog.dismiss();
                                }
                            }else {
                                Toast.makeText(getContext(), "Mật khẩu không chính xác", Toast.LENGTH_SHORT).show();
                            }
                        }
                    })
                    .setNegativeButton("No",null).show();
        });
    }
    public void hideSoftKeyBroad(){
        InputMethodManager inputMethodManager = (InputMethodManager) getContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),0);
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
}