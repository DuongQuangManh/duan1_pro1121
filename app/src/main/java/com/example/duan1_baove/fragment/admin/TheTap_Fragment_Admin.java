package com.example.duan1_baove.fragment.admin;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.example.duan1_baove.model.KhachHang;
import com.example.duan1_baove.model.LoaiTheTap;
import com.example.duan1_baove.model.TheTap;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Calendar;
import java.util.List;

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
    private Spinner spn_khachhang,spn_loaithetap;
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
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_the_tap___admin, container, false);

        initUi();
        capNhat();
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
    }

    private void search(String search) {
//        adapter.getFilter().filter(search);
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
                DuAn1DataBase.getInstance(getContext()).theTapDAO().insert(theTap);
                Toast.makeText(getContext(), "Insert thẻ tập thành công", Toast.LENGTH_SHORT).show();
                capNhat();
                dialog.dismiss();
            }
        });
    }


    private void capNhat(){
        list = DuAn1DataBase.getInstance(getContext()).theTapDAO().getAll();
        adapter = new TheTapAdapter(getActivity());
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
    public void hideSoftKeyBroad(){
        InputMethodManager inputMethodManager = (InputMethodManager) getContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),0);
    }
}