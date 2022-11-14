package com.example.duan1_baove.fragment.hocvien;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.duan1_baove.HocVien_MainActivity;
import com.example.duan1_baove.R;
import com.example.duan1_baove.adapter.CuaHangHocVienAdapter;
import com.example.duan1_baove.database.DuAn1DataBase;
import com.example.duan1_baove.model.CuaHang;
import com.example.duan1_baove.model.DonHangChiTiet;
import com.example.duan1_baove.model.KhachHang;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class CuaHang_Fragment_HocVien extends Fragment {
    private View view;
    private EditText edt_search,edt_soluong;
    private RecyclerView recyclerView;
    private Spinner spn_theloai,spn_hansudung;
    private List<CuaHang> list;
    private CuaHangHocVienAdapter adapter;
    private ImageView img_shop,img_close,img_tru,img_cong,img_avt_monhang;
    private TextView tv_gia_layoutmuahang,tv_soluong_layoutmuahang,tv_tongtien_layoutmuahang;
    private Button btn_muangay;
    String[] theloai = {"Tất cả","Món hàng","Dịch vụ"};
    String strTheloai = "Tất cả",strHanSuDung = "1 tuần";
    String[] hansudung = {"1 tuần","1 tháng","3 tháng","6 tháng","1 năm","3 năm","5 năm"};
    NumberFormat numberFormat = new DecimalFormat("###,###,###");
    private RelativeLayout layout_muahang,layout_soluong,layout_hansudung;
    float v = 0;
    int soluong = 1,tongtien = 0,soluongtrongkho,songay,soDuMoi=0;
    Calendar lichStart = Calendar.getInstance();
    Calendar lichEnd = Calendar.getInstance();
    int yearStart = lichStart.get(Calendar.YEAR);
    int monthStart = lichStart.get(Calendar.MONTH);
    int dayStart = lichStart.get(Calendar.DAY_OF_MONTH);
    int yearEnd = lichEnd.get(Calendar.YEAR);
    int monthEnd = lichEnd.get(Calendar.MONTH);
    int dayEnd = lichEnd.get(Calendar.DAY_OF_MONTH);
    int hour = lichStart.get(Calendar.HOUR);
    int minute = lichStart.get(Calendar.MINUTE);
    int second = lichStart.get(Calendar.SECOND);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_cua_hang_hoc_vien, container, false);
        initUi();

        layout_muahang.setTranslationY(800);
        layout_muahang.setAlpha(v);

        ArrayAdapter adapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_dropdown_item,theloai);
        spn_theloai.setAdapter(adapter);
        spn_theloai.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                strTheloai = theloai[position];
                if (strTheloai.equals("Tất cả")){
                    capnhat();
                }else{
                    retrieveByType(strTheloai);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        edt_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId== EditorInfo.IME_ACTION_SEARCH){
                    search(edt_search.getText().toString().trim());
                    hideSoftKeyBroad();
                }
                return false;
            }
        });

        img_shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),DonHangChiTiet_MainActivity_HocVien.class);
                getActivity().startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle());
            }
        });
        return view;
    }
    private void initUi() {
        edt_search = view.findViewById(R.id.edt_search_cuahanghocvien);
        recyclerView = view.findViewById(R.id.rcy_cuahang_hocvien);
        spn_theloai = view.findViewById(R.id.spn_theloai_cuahanghocvien);
        img_shop = view.findViewById(R.id.img_shop_cuahanghocvien);
        layout_muahang = view.findViewById(R.id.layout_muahang_cuahanghocvien);
        layout_soluong = view.findViewById(R.id.layout_soluong);
        layout_hansudung = view.findViewById(R.id.layout_hansudung);
        img_close = view.findViewById(R.id.img_close_layoutmuahang);
        img_tru = view.findViewById(R.id.img_tru_layoutsoluong);
        img_cong = view.findViewById(R.id.img_cong_layoutsoluong);
        spn_hansudung = view.findViewById(R.id.spn_hansudung_layouthansudung);
        edt_soluong = view.findViewById(R.id.edt_soluong_layoutsoluong);
        img_avt_monhang = view.findViewById(R.id.img_avt_layoutmuahang);
        tv_gia_layoutmuahang = view.findViewById(R.id.tv_gia_layout_muahang);
        tv_soluong_layoutmuahang = view.findViewById(R.id.tv_soluong_layoutmuahang);
        btn_muangay = view.findViewById(R.id.btn_muangay_layoutmuahang);
        tv_tongtien_layoutmuahang = view.findViewById(R.id.tv_tongtien_layoutmuahang);
    }
    private void search(String strSearch) {
        adapter.getFilter().filter(strSearch);
    }

    private void retrieveByType(String strTheloai) {
        list = new ArrayList<>();
        list = DuAn1DataBase.getInstance(getContext()).cuaHangDAO().retrieveByType(strTheloai);
        GridLayoutManager manager = new GridLayoutManager(getContext(),2,RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(manager);
        adapter = new CuaHangHocVienAdapter(getContext(), new CuaHangHocVienAdapter.IclickListener() {
            @Override
            public void muahang(CuaHang cuaHang) {
                muaHang(cuaHang);
            }
        });
        adapter.setData(list);
        recyclerView.setAdapter(adapter);
    }

    private void capnhat() {
        list = DuAn1DataBase.getInstance(getContext()).cuaHangDAO().getAll();
        GridLayoutManager manager = new GridLayoutManager(getContext(),2,RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(manager);
        adapter = new CuaHangHocVienAdapter(getContext(), new CuaHangHocVienAdapter.IclickListener() {
            @Override
            public void muahang(CuaHang cuaHang) {
                muaHang(cuaHang);
            }
        });
        adapter.setData(list);
        recyclerView.setAdapter(adapter);

    }
    private void muaHang(CuaHang cuaHang){
        layout_muahang.animate().alpha(1).translationY(0).setDuration(800).start();
        soluongtrongkho = cuaHang.getSoLuong();
        img_close.setOnClickListener(v1 -> {
            layout_muahang.animate().alpha(v).translationY(800).setStartDelay(300).setDuration(1000).start();
        });
        if (cuaHang.getTheloai().equals("Món hàng")){
            tv_gia_layoutmuahang.setText(numberFormat.format(cuaHang.getGia())+" vnđ /sp");
            tongtien = soluong*cuaHang.getGia();
            tv_tongtien_layoutmuahang.setText(numberFormat.format(tongtien)+" vnđ");
            layout_hansudung.setVisibility(View.GONE);
            layout_soluong.setVisibility(View.VISIBLE);
            edt_soluong.addTextChangedListener(textWatcher);
            img_tru.setOnClickListener(v1 -> {
                soluong-=1;
                edt_soluong.setText(soluong+"");
                tongtien = soluong*cuaHang.getGia();
                tv_tongtien_layoutmuahang.setText(numberFormat.format(tongtien)+" vnđ");
            });
            img_cong.setOnClickListener(v1 -> {
                soluong+=1;
                edt_soluong.setText(soluong+"");
                tongtien = soluong*cuaHang.getGia();
                tv_tongtien_layoutmuahang.setText(numberFormat.format(tongtien)+" vnđ");
            });
            btn_muangay.setOnClickListener(v1 -> {
                int soDu = DuAn1DataBase.getInstance(getContext()).khachHangDAO().checkAcc(HocVien_MainActivity.userHocVien).get(0).getSoDu();
                if (tongtien > soDu){
                    Toast.makeText(getContext(), "Số dư không đủ", Toast.LENGTH_SHORT).show();
                }else {
                    DonHangChiTiet donHangChiTiet = new DonHangChiTiet();
                    donHangChiTiet.setSoLuong(soluong);
                    donHangChiTiet.setKhachang_id(HocVien_MainActivity.userHocVien);
                    donHangChiTiet.setCuahang_id(cuaHang.getId());
                    donHangChiTiet.setStarttime(dayStart+"-"+monthStart+"-"+yearStart+", "+hour+":"+minute+":"+second);
                    donHangChiTiet.setTongtien(tongtien);
                    donHangChiTiet.setGianiemyet(cuaHang.getGia());
                    donHangChiTiet.setTinhTrang("Chưa kiểm duyệt");
                    DuAn1DataBase.getInstance(getContext()).donHangChiTietDAO().insert(donHangChiTiet);
                    Toast.makeText(getContext(), "Mua hàng thành công vui lòng ra quầy nhận hàng", Toast.LENGTH_SHORT).show();
                    List<KhachHang> list1 = DuAn1DataBase.getInstance(getContext()).khachHangDAO().checkAcc(HocVien_MainActivity.userHocVien);
                    KhachHang khachHang = list1.get(0);
                    soDuMoi = soDu - tongtien;
                    khachHang.setSoDu(soDuMoi);
                    DuAn1DataBase.getInstance(getContext()).khachHangDAO().update(khachHang);
                    List<CuaHang> list2 = DuAn1DataBase.getInstance(getContext()).cuaHangDAO().getByID(String.valueOf(cuaHang.getId()));
                    CuaHang cuaHang1 = list2.get(0);
                    cuaHang1.setSoLuong(cuaHang1.getSoLuong()-soluong);
                    DuAn1DataBase.getInstance(getContext()).cuaHangDAO().update(cuaHang1);
                    layout_muahang.animate().alpha(v).translationY(800).setDuration(600).start();
                    capnhat();
                }
            });
        }else if (cuaHang.getTheloai().equals("Dịch vụ")){
            tv_gia_layoutmuahang.setText(numberFormat.format(cuaHang.getGia())+" vnđ /Tháng");
            layout_soluong.setVisibility(View.GONE);
            layout_hansudung.setVisibility(View.VISIBLE);
            ArrayAdapter adapter1 = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_dropdown_item,hansudung);
            spn_hansudung.setAdapter(adapter1);
            spn_hansudung.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    strHanSuDung = hansudung[position];
                    if (position==0){
                        songay = 7;
                        tongtien = cuaHang.getGia()/4;
                    }else if (position==1){
                        songay = 30;
                        tongtien = cuaHang.getGia();
                    }else if (position==2){
                        songay = 90;
                        tongtien = cuaHang.getGia()*3;
                    }else if (position==3){
                        songay = 180;
                        tongtien = cuaHang.getGia()*6;
                    }else if (position==4){
                        songay = 360;
                        tongtien = cuaHang.getGia()*12;
                    }else if(position==5){
                        songay = 1080;
                        tongtien = cuaHang.getGia()*36;
                    }else if (position==6){
                        songay = 1800;
                        tongtien =cuaHang.getGia()*60;
                    }
                    tv_tongtien_layoutmuahang.setText(numberFormat.format(tongtien)+ " vnđ");
                }
                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            btn_muangay.setOnClickListener(v1 -> {
                int soDu = DuAn1DataBase.getInstance(getContext()).khachHangDAO().checkAcc(HocVien_MainActivity.userHocVien).get(0).getSoDu();
                if (tongtien > soDu){
                    Toast.makeText(getContext(), "Số dư không đủ", Toast.LENGTH_SHORT).show();
                }else {
                    DonHangChiTiet donHangChiTiet = new DonHangChiTiet();
                    donHangChiTiet.setKhachang_id(HocVien_MainActivity.userHocVien);
                    donHangChiTiet.setCuahang_id(cuaHang.getId());
                    donHangChiTiet.setTinhTrang("Chưa kiểm duyệt");
                    donHangChiTiet.setTongtien(tongtien);
                    donHangChiTiet.setGianiemyet(cuaHang.getGia());
                    donHangChiTiet.setStarttime(dayStart+"-"+monthStart+"-"+yearStart);
                    lichEnd.roll(Calendar.DAY_OF_MONTH,songay);
                    donHangChiTiet.setEndtime(dayEnd+"-"+monthEnd+"-"+yearEnd);
                    DuAn1DataBase.getInstance(getContext()).donHangChiTietDAO().insert(donHangChiTiet);
                    Toast.makeText(getContext(), "Đăng kí dịch vụ thành công", Toast.LENGTH_SHORT).show();
                    List<KhachHang> list1 = DuAn1DataBase.getInstance(getContext()).khachHangDAO().checkAcc(HocVien_MainActivity.userHocVien);
                    KhachHang khachHang = list1.get(0);
                    soDuMoi = soDu - tongtien;
                    khachHang.setSoDu(soDuMoi);
                    DuAn1DataBase.getInstance(getContext()).khachHangDAO().update(khachHang);
                    List<CuaHang> list2 = DuAn1DataBase.getInstance(getContext()).cuaHangDAO().getByID(String.valueOf(cuaHang.getId()));
                    CuaHang cuaHang1 = list2.get(0);
                    cuaHang1.setSoLuong(cuaHang1.getSoLuong()-1);
                    DuAn1DataBase.getInstance(getContext()).cuaHangDAO().update(cuaHang1);
                    layout_muahang.animate().alpha(v).translationY(800).setDuration(600).start();
                    capnhat();
                }
            });
        }

        if (cuaHang.getImg()==null){
            img_avt_monhang.setImageResource(R.drawable.ic_account);
        }else {
            String linkimg = cuaHang.getImg();
            Log.d("adapter",linkimg+" link");
            img_avt_monhang.setImageDrawable(Drawable.createFromPath(linkimg));
        }
        tv_soluong_layoutmuahang.setText("SL: "+cuaHang.getSoLuong());
        edt_soluong.setText(soluong+"");
    }
    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String soluong = edt_soluong.getText().toString().trim();
            if (Integer.parseInt(soluong)<=1){
                img_tru.setEnabled(false);
            }else{
                img_tru.setEnabled(true);
            }
            if (Integer.parseInt(soluong)>= soluongtrongkho){
                img_cong.setEnabled(false);
            }else {
                img_cong.setEnabled(true);
            }

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    public void hideSoftKeyBroad(){
        InputMethodManager inputMethodManager = (InputMethodManager) getContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),0);
    }

}