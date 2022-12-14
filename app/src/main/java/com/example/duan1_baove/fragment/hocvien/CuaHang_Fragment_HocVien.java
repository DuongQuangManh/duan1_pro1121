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
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.duan1_baove.HocVien_MainActivity;
import com.example.duan1_baove.R;
import com.example.duan1_baove.adapter.CuaHangHocVienAdapter;
import com.example.duan1_baove.database.DuAn1DataBase;
import com.example.duan1_baove.model.CuaHang;
import com.example.duan1_baove.model.DonHangChiTiet;
import com.example.duan1_baove.model.KhachHang;
import com.example.duan1_baove.model.LichSuGiaoDich;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
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
    private TextView tv_muathetap;
    String[] theloai = {"T???t c???","M??n h??ng","D???ch v???"};
    String strTheloai = "T???t c???",strHanSuDung = "1 tu???n";
    String[] hansudung = {"1 tu???n","1 th??ng","3 th??ng","6 th??ng","1 n??m","3 n??m","5 n??m"};
    NumberFormat numberFormat = new DecimalFormat("###,###,###");
    private RelativeLayout layout_muahang,layout_soluong,layout_hansudung;
    float v = 0;
    int soluong = 1,tongtien = 0,soluongtrongkho,soDuMoi=0;
    Calendar lichStart = Calendar.getInstance();
    Calendar lichEnd = Calendar.getInstance();
    int yearStart = lichStart.get(Calendar.YEAR);
    int monthStart = lichStart.get(Calendar.MONTH)+1;
    int dayStart = lichStart.get(Calendar.DAY_OF_MONTH);
    int hour = lichStart.get(Calendar.HOUR);
    int minute = lichStart.get(Calendar.MINUTE);
    int second = lichStart.get(Calendar.SECOND);
    SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy, hh:mm:ss");
    Spinner spn_hinhthucthanhtoan;
    String[] hinhthuc = {"Thanh to??n b???ng ti???n trong t??i kho???n","Thanh to??n khi nh???n h??ng"};
    String strhinhthuc;
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
                if (strTheloai.equals("T???t c???")){
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
                getActivity().startActivity(intent);
                Animatoo.INSTANCE.animateFade(getContext());
            }
        });
        tv_muathetap.setOnClickListener(v1 -> {
            Intent intent = new Intent(getContext(),MuaTheTap_MainActivity_HocVien.class);
            getActivity().startActivity(intent);
            Animatoo.INSTANCE.animateFade(getContext());
        });

        ArrayAdapter adapter1 = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_dropdown_item,hinhthuc);
        spn_hinhthucthanhtoan.setAdapter(adapter1);
        spn_hinhthucthanhtoan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                strhinhthuc = hinhthuc[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

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
        tv_muathetap = view.findViewById(R.id.tv_muathetap_cuahanghocvien);
        spn_hinhthucthanhtoan = view.findViewById(R.id.spn_hinhthucthanhtoan);
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
        if (soluongtrongkho<=0){
            btn_muangay.setEnabled(false);
            btn_muangay.setBackground(getActivity().getDrawable(R.drawable.bg_gray));
        }else {
            btn_muangay.setEnabled(true);
            btn_muangay.setBackground(getActivity().getDrawable(R.drawable.bg_green));
        }
        img_close.setOnClickListener(v1 -> {
            layout_muahang.animate().alpha(v).translationY(800).setStartDelay(300).setDuration(1000).start();
        });
        if (cuaHang.getTheloai().equals("M??n h??ng")){
            tv_gia_layoutmuahang.setText(numberFormat.format(cuaHang.getGia())+" vn?? /sp");
            tongtien = soluong*cuaHang.getGia();
            tv_tongtien_layoutmuahang.setText(numberFormat.format(tongtien)+" vn??");
            layout_hansudung.setVisibility(View.GONE);
            layout_soluong.setVisibility(View.VISIBLE);
            edt_soluong.addTextChangedListener(textWatcher);
            img_tru.setOnClickListener(v1 -> {
                soluong-=1;
                edt_soluong.setText(soluong+"");
                tongtien = soluong*cuaHang.getGia();
                tv_tongtien_layoutmuahang.setText(numberFormat.format(tongtien)+" vn??");
            });
            img_cong.setOnClickListener(v1 -> {
                soluong+=1;
                edt_soluong.setText(soluong+"");
                tongtien = soluong*cuaHang.getGia();
                tv_tongtien_layoutmuahang.setText(numberFormat.format(tongtien)+" vn??");
            });
            btn_muangay.setOnClickListener(v1 -> {
                if (strhinhthuc.equals("Thanh to??n b???ng ti???n trong t??i kho???n")){
                    int soDu = DuAn1DataBase.getInstance(getContext()).khachHangDAO().checkAcc(HocVien_MainActivity.userHocVien).get(0).getSoDu();
                    if (tongtien > soDu){
                        Toast.makeText(getContext(), "S??? d?? kh??ng ?????", Toast.LENGTH_SHORT).show();
                    }else {
                        DonHangChiTiet donHangChiTiet = new DonHangChiTiet();
                        donHangChiTiet.setSoLuong(soluong);
                        donHangChiTiet.setKhachang_id(HocVien_MainActivity.userHocVien);
                        donHangChiTiet.setCuahang_id(cuaHang.getId());
                        donHangChiTiet.setStarttime(dayStart+"-"+monthStart+"-"+yearStart+", "+hour+":"+minute+":"+second);
                        donHangChiTiet.setTongtien(tongtien);
                        donHangChiTiet.setGianiemyet(cuaHang.getGianhap());
                        donHangChiTiet.setTinhTrang("Ch??a ki???m duy???t");
                        donHangChiTiet.setHinhthucthanhtoan(strhinhthuc);
                        DuAn1DataBase.getInstance(getContext()).donHangChiTietDAO().insert(donHangChiTiet);
                        Toast.makeText(getContext(), "Mua h??ng th??nh c??ng vui l??ng ra qu???y nh???n h??ng", Toast.LENGTH_SHORT).show();
                        List<KhachHang> list1 = DuAn1DataBase.getInstance(getContext()).khachHangDAO().checkAcc(HocVien_MainActivity.userHocVien);
                        KhachHang khachHang = list1.get(0);
                        soDuMoi = soDu - tongtien;
                        khachHang.setSoDu(soDuMoi);
                        DuAn1DataBase.getInstance(getContext()).khachHangDAO().update(khachHang);
                        List<CuaHang> list2 = DuAn1DataBase.getInstance(getContext()).cuaHangDAO().getByID(String.valueOf(cuaHang.getId()));
                        CuaHang cuaHang1 = list2.get(0);
                        cuaHang1.setSoLuong(cuaHang1.getSoLuong()-soluong);
                        DuAn1DataBase.getInstance(getContext()).cuaHangDAO().update(cuaHang1);

                        LichSuGiaoDich lichSuGiaoDich = new LichSuGiaoDich();
                        lichSuGiaoDich.setSoTien(tongtien);
                        lichSuGiaoDich.setType("Tr???");
                        lichSuGiaoDich.setKhachang_id(HocVien_MainActivity.userHocVien);
                        lichSuGiaoDich.setThoigian(format.format(new Date()));
                        DuAn1DataBase.getInstance(getContext()).lichSuGiaoDichDAO().insert(lichSuGiaoDich);

                        layout_muahang.animate().alpha(v).translationY(800).setDuration(600).start();
                        capnhat();
                    }
                }else {
                    DonHangChiTiet donHangChiTiet = new DonHangChiTiet();
                    donHangChiTiet.setSoLuong(soluong);
                    donHangChiTiet.setKhachang_id(HocVien_MainActivity.userHocVien);
                    donHangChiTiet.setCuahang_id(cuaHang.getId());
                    donHangChiTiet.setStarttime(dayStart+"-"+monthStart+"-"+yearStart+", "+hour+":"+minute+":"+second);
                    donHangChiTiet.setTongtien(tongtien);
                    donHangChiTiet.setGianiemyet(cuaHang.getGianhap());
                    donHangChiTiet.setTinhTrang("Ch??a ki???m duy???t");
                    donHangChiTiet.setHinhthucthanhtoan(strhinhthuc);
                    DuAn1DataBase.getInstance(getContext()).donHangChiTietDAO().insert(donHangChiTiet);
                    Toast.makeText(getContext(), "Mua h??ng th??nh c??ng vui l??ng ra qu???y nh???n h??ng", Toast.LENGTH_SHORT).show();
                    layout_muahang.animate().alpha(v).translationY(800).setDuration(600).start();
                    capnhat();
                }
            });
        }else if (cuaHang.getTheloai().equals("D???ch v???")){
            tv_gia_layoutmuahang.setText(numberFormat.format(cuaHang.getGia())+" vn?? /Th??ng");
            layout_soluong.setVisibility(View.GONE);
            layout_hansudung.setVisibility(View.VISIBLE);
            ArrayAdapter adapter1 = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_dropdown_item,hansudung);
            spn_hansudung.setAdapter(adapter1);
            spn_hansudung.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    strHanSuDung = hansudung[position];
                    if (position==0){
                        lichEnd = Calendar.getInstance();
                        lichEnd.add(Calendar.DAY_OF_MONTH,7);
                        tongtien = cuaHang.getGia()/4;
                    }else if (position==1){
                        lichEnd = Calendar.getInstance();
                        lichEnd.add(Calendar.DAY_OF_MONTH,30);
                        tongtien = cuaHang.getGia();
                    }else if (position==2){
                        lichEnd = Calendar.getInstance();
                        lichEnd.add(Calendar.DAY_OF_MONTH,90);
                        tongtien = cuaHang.getGia()*3;
                    }else if (position==3){
                        lichEnd = Calendar.getInstance();
                        lichEnd.add(Calendar.DAY_OF_MONTH,180);
                        tongtien = cuaHang.getGia()*6;
                    }else if (position==4){
                        lichEnd = Calendar.getInstance();
                        lichEnd.add(Calendar.DAY_OF_MONTH,360);
                        tongtien = cuaHang.getGia()*12;
                    }else if(position==5){
                        lichEnd = Calendar.getInstance();
                        lichEnd.add(Calendar.DAY_OF_MONTH,1080);
                        tongtien = cuaHang.getGia()*36;
                    }else if (position==6){
                        lichEnd = Calendar.getInstance();
                        lichEnd.add(Calendar.DAY_OF_MONTH,1800);
                        tongtien =cuaHang.getGia()*60;
                    }
                    tv_tongtien_layoutmuahang.setText(numberFormat.format(tongtien)+ " vn??");
                }
                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            btn_muangay.setOnClickListener(v1 -> {
                if (strhinhthuc.equals("Thanh to??n b???ng ti???n trong t??i kho???n")){
                    int soDu = DuAn1DataBase.getInstance(getContext()).khachHangDAO().checkAcc(HocVien_MainActivity.userHocVien).get(0).getSoDu();
                    if (tongtien > soDu){
                        Toast.makeText(getContext(), "S??? d?? kh??ng ?????", Toast.LENGTH_SHORT).show();
                    }else {
                        int yearEnd = lichEnd.get(Calendar.YEAR);
                        int monthEnd = lichEnd.get(Calendar.MONTH)+1;
                        int dayEnd = lichEnd.get(Calendar.DAY_OF_MONTH);
                        DonHangChiTiet donHangChiTiet = new DonHangChiTiet();
                        donHangChiTiet.setKhachang_id(HocVien_MainActivity.userHocVien);
                        donHangChiTiet.setCuahang_id(cuaHang.getId());
                        donHangChiTiet.setTinhTrang("Ch??a ki???m duy???t");
                        donHangChiTiet.setTongtien(tongtien);
                        donHangChiTiet.setGianiemyet(cuaHang.getGia());
                        donHangChiTiet.setStarttime(dayStart+"-"+monthStart+"-"+yearStart);
                        donHangChiTiet.setEndtime(dayEnd+"-"+monthEnd+"-"+yearEnd);
                        donHangChiTiet.setHinhthucthanhtoan(strhinhthuc);
                        DuAn1DataBase.getInstance(getContext()).donHangChiTietDAO().insert(donHangChiTiet);
                        Toast.makeText(getContext(), "????ng k?? d???ch v??? th??nh c??ng", Toast.LENGTH_SHORT).show();
                        List<KhachHang> list1 = DuAn1DataBase.getInstance(getContext()).khachHangDAO().checkAcc(HocVien_MainActivity.userHocVien);
                        KhachHang khachHang = list1.get(0);
                        soDuMoi = soDu - tongtien;
                        khachHang.setSoDu(soDuMoi);
                        DuAn1DataBase.getInstance(getContext()).khachHangDAO().update(khachHang);
                        List<CuaHang> list2 = DuAn1DataBase.getInstance(getContext()).cuaHangDAO().getByID(String.valueOf(cuaHang.getId()));
                        CuaHang cuaHang1 = list2.get(0);
                        cuaHang1.setSoLuong(cuaHang1.getSoLuong()-1);
                        DuAn1DataBase.getInstance(getContext()).cuaHangDAO().update(cuaHang1);

                        LichSuGiaoDich lichSuGiaoDich = new LichSuGiaoDich();
                        lichSuGiaoDich.setSoTien(tongtien);
                        lichSuGiaoDich.setType("Tr???");
                        lichSuGiaoDich.setKhachang_id(HocVien_MainActivity.userHocVien);
                        lichSuGiaoDich.setThoigian(format.format(new Date()));
                        DuAn1DataBase.getInstance(getContext()).lichSuGiaoDichDAO().insert(lichSuGiaoDich);

                        layout_muahang.animate().alpha(v).translationY(800).setDuration(600).start();
                        capnhat();
                    }
                }else {
                    int yearEnd = lichEnd.get(Calendar.YEAR);
                    int monthEnd = lichEnd.get(Calendar.MONTH)+1;
                    int dayEnd = lichEnd.get(Calendar.DAY_OF_MONTH);
                    DonHangChiTiet donHangChiTiet = new DonHangChiTiet();
                    donHangChiTiet.setKhachang_id(HocVien_MainActivity.userHocVien);
                    donHangChiTiet.setCuahang_id(cuaHang.getId());
                    donHangChiTiet.setTinhTrang("Ch??a ki???m duy???t");
                    donHangChiTiet.setTongtien(tongtien);
                    donHangChiTiet.setGianiemyet(cuaHang.getGia());
                    donHangChiTiet.setStarttime(dayStart+"-"+monthStart+"-"+yearStart);
                    donHangChiTiet.setEndtime(dayEnd+"-"+monthEnd+"-"+yearEnd);
                    donHangChiTiet.setHinhthucthanhtoan(strhinhthuc);
                    DuAn1DataBase.getInstance(getContext()).donHangChiTietDAO().insert(donHangChiTiet);
                    Toast.makeText(getContext(), "????ng k?? d???ch v??? th??nh c??ng", Toast.LENGTH_SHORT).show();

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