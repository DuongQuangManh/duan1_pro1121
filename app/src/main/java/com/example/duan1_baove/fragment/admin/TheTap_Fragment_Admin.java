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

import com.example.duan1_baove.HocVien_MainActivity;
import com.example.duan1_baove.R;
import com.example.duan1_baove.adapter.SpinnerAdapterLoaiTheTap;
import com.example.duan1_baove.adapter.SpinnerAdapterNapTien;
import com.example.duan1_baove.adapter.TheTapAdapter;
import com.example.duan1_baove.database.DuAn1DataBase;
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

    String[] tinhtrang = {"T???t c???","S???p h???t h???n"};
    String strTinhTrang = "T???t c???";

    SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy, hh:mm:ss");
    Calendar lichsearch;
    Calendar lichend1;
    long day1;
    String endtimenew;
    List<TheTap> listTT;


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
                if (strTinhTrang.equals("T???t c???")){
                    capNhat();
                }else if (strTinhTrang.equals("S???p h???t h???n")){
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
                spn_loaithetap.setSelection(0);
                listTT = DuAn1DataBase.getInstance(getContext()).theTapDAO().checkTheTap(strKhachHangID);
                if (listTT.size()<=0){
                    endtimenew = day+"-"+month+"-"+year;
                    Log.d("abcd","ch??a c?? th??? t???p");
                }else {
                    endtimenew = listTT.get(0).getNgayHetHan();
                    for(int i=1;i<listTT.size();i++){
                        endtimenew = getMax(endtimenew,listTT.get(i).getNgayHetHan());
                    }
                    Log.d("abcd"," c?? th??? t???p");
                }
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
                tinhsongay(position);
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
            if (DuAn1DataBase.getInstance(getContext()).theTapDAO().checkTheTap(strKhachHangID).size()<=0){
                theTap = new TheTap();
                theTap.setKhachhang_id(strKhachHangID);
                theTap.setLoaithetap_id(intLoaiTheTap);
                theTap.setNgayDangKy(endtimenew);
                theTap.setNgayHetHan(edt_endtime.getText().toString().trim());
                theTap.setTongsotiendamuathetap(DuAn1DataBase.getInstance(getContext()).theTapDAO().getTongSoTien(strKhachHangID)+DuAn1DataBase.getInstance(getContext()).loaiTheTapDAO().getByID(String.valueOf(intLoaiTheTap)).get(0).getGia());
                DuAn1DataBase.getInstance(getContext()).theTapDAO().insert(theTap);
                Toast.makeText(getContext(), "Mua th??? t???p th??nh c??ng", Toast.LENGTH_SHORT).show();
                capNhat();
            }else {
                EditText editText = new EditText(getContext());
                new AlertDialog.Builder(getContext())
                        .setTitle("Vui l??ng nh???p m???t kh???u !")
                        .setView(editText)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (checkPass(editText.getText().toString().trim(),DuAn1DataBase.getInstance(getContext()).khachHangDAO().getPass(strKhachHangID))){
                                    if (DuAn1DataBase.getInstance(getContext()).khachHangDAO().getSoDU(strKhachHangID)<DuAn1DataBase.getInstance(getContext()).loaiTheTapDAO().getGia(String.valueOf(intLoaiTheTap))){
                                        Toast.makeText(getContext(), "S??? d?? kh??ng ?????", Toast.LENGTH_SHORT).show();
                                    }else {
                                        theTap = new TheTap();
                                        theTap.setKhachhang_id(strKhachHangID);
                                        theTap.setLoaithetap_id(intLoaiTheTap);
                                        theTap.setNgayDangKy(endtimenew);
                                        theTap.setNgayHetHan(edt_endtime.getText().toString().trim());
                                        theTap.setTongsotiendamuathetap(DuAn1DataBase.getInstance(getContext()).theTapDAO().getTongSoTien(strKhachHangID)+DuAn1DataBase.getInstance(getContext()).loaiTheTapDAO().getGia(String.valueOf(intLoaiTheTap)));
                                        DuAn1DataBase.getInstance(getContext()).theTapDAO().insert(theTap);
                                        LichSuGiaoDich lichSuGiaoDich = new LichSuGiaoDich();
                                        lichSuGiaoDich.setType("Tr???");
                                        lichSuGiaoDich.setKhachang_id(strKhachHangID);
                                        lichSuGiaoDich.setSoTien(DuAn1DataBase.getInstance(getContext()).loaiTheTapDAO().getGia(String.valueOf(intLoaiTheTap)));
                                        lichSuGiaoDich.setThoigian(format.format(new Date()));
                                        DuAn1DataBase.getInstance(getContext()).lichSuGiaoDichDAO().insert(lichSuGiaoDich);
                                        Toast.makeText(getContext(), "Insert th??? t???p th??nh c??ng", Toast.LENGTH_SHORT).show();
                                        capNhat();
                                    }
                                }
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
            }
            dialog.dismiss();
        });
    }
    private boolean checkPass(String edt,String pass){
        if (edt.equals(pass)){
            return true;
        }else {
            Toast.makeText(getContext(), "M???t kh???u kh??ng ch??nh x??c", Toast.LENGTH_SHORT).show();
            return false;
        }
    }
    private void tinhsongay(int position){
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
    private void sapHetHan(){
        list = new ArrayList<>();
        lichsearch = Calendar.getInstance();
        lichsearch.add(Calendar.MONTH,1);
        List<TheTap> list1 = DuAn1DataBase.getInstance(getContext()).theTapDAO().getAll();
        for (int i=0;i<list1.size();i++){
            lichend1 = Calendar.getInstance();
            lichend1.set(Calendar.DAY_OF_MONTH,getArrayDate(list1.get(i).getNgayHetHan())[0]);
            lichend1.set(Calendar.MONTH,getArrayDate(list1.get(i).getNgayHetHan())[1]);
            lichend1.set(Calendar.YEAR,getArrayDate(list1.get(i).getNgayHetHan())[2]);
            day1 = lichend1.getTime().getTime() - lichsearch.getTime().getTime();
            if (TimeUnit.DAYS.convert(day1, TimeUnit.MILLISECONDS)<=10){
                list.add(list1.get(i));
                adapter = new TheTapAdapter(getActivity(), new TheTapAdapter.IClickListener() {
                    @Override
                    public void giahan(TheTap theTap) {
                        opendialog(theTap);
                    }
                });
            }
        }
        adapter.setData(list);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
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
    private void opendialog(TheTap theTap) {
        Toast.makeText(getContext(), "Vui l??ng mua th??m th??? t???p", Toast.LENGTH_SHORT).show();
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