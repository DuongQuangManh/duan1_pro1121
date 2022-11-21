package com.example.duan1_baove.fragment.admin;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.duan1_baove.R;
import com.example.duan1_baove.adapter.CuaHangAdapter;
import com.example.duan1_baove.database.DuAn1DataBase;
import com.example.duan1_baove.model.CuaHang;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class CuaHang_Fragment_Admin extends Fragment {
    private static final int IMAGE_PICKER_SELECT = 0;
    private List<CuaHang> list;
    private View view;
    private RecyclerView recyclerView;
    private LinearLayout layout_search;
    private EditText edt_search;
    private FloatingActionButton fab1,fab2,fab3;
    private Animation fab_open,fab_close,rotateForward,rotateBackward;
    boolean isOpen;

    private EditText edt_id,edt_name,edt_gia,
            edt_tinhtrang,edt_soluong,
            edt_trongluong,edt_hangsanxuat;
    private Button btn_chonanh,btn_add,btn_huy;
    private Spinner spn_theloai;
    private ImageView img_cuahang;
    private String img;
    private CuaHang cuaHang;

    private CuaHangAdapter adapter;
    String[] theloai = {"Món hàng","Dịch vụ"};
    String strTheloai = "Món hàng";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_cua_hang___admin, container, false);
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
        recyclerView = view.findViewById(R.id.rcy_cuahang);
        fab1 = view.findViewById(R.id.fab1_cuahang);
        fab2 = view.findViewById(R.id.fab2_cuahang);
        fab3 = view.findViewById(R.id.fab3_cuahang);
        fab_open = AnimationUtils.loadAnimation(getContext(),R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getContext(),R.anim.fab_close);
        rotateForward = AnimationUtils.loadAnimation(getContext(),R.anim.rotate_forward);
        rotateBackward = AnimationUtils.loadAnimation(getContext(),R.anim.rotate_backward);
        layout_search = view.findViewById(R.id.layout_search_cuahang);
        edt_search = view.findViewById(R.id.edt_search_cuahang);
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
        dialog.setContentView(R.layout.dialog_addcuahang);
        dialog.show();
        Window window = dialog.getWindow();
        if (window == null){
            return;
        }
        window.setBackgroundDrawable(null);
        edt_id = dialog.findViewById(R.id.edt_id_dialogcuahang);
        edt_name = dialog.findViewById(R.id.edt_ten_dialogcuahang);
        edt_gia = dialog.findViewById(R.id.edt_gia_dialogcuahang);
        edt_tinhtrang = dialog.findViewById(R.id.edt_tinhtrang_dialogcuahang);
        edt_soluong = dialog.findViewById(R.id.edt_soLuong_dialogcuahang);
        edt_trongluong = dialog.findViewById(R.id.edt_trongluong_dialogcuahang);
        edt_hangsanxuat = dialog.findViewById(R.id.edt_hangsanxuat_dialogcuahang);
        btn_chonanh = dialog.findViewById(R.id.btn_selectimage_dialogcuahang);
        btn_add = dialog.findViewById(R.id.btn_luu_dialogcuahang);
        btn_huy = dialog.findViewById(R.id.btn_huy_dialogcuahang);
        img_cuahang = dialog.findViewById(R.id.avt_dialogcuahang);
        spn_theloai = dialog.findViewById(R.id.spn_theloai_dialogcuahang);

        ArrayAdapter adapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_dropdown_item,theloai);
        spn_theloai.setAdapter(adapter);

        spn_theloai.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                strTheloai = theloai[position];
                if (strTheloai.equals("Dịch vụ")){
                    edt_name.addTextChangedListener(textWatcher);
                    edt_hangsanxuat.setEnabled(false);
                    edt_hangsanxuat.setVisibility(View.GONE);
                    edt_trongluong.setEnabled(false);
                    edt_trongluong.setVisibility(View.GONE);
                }else if (strTheloai.equals("Món hàng")){
                    edt_hangsanxuat.setEnabled(true);
                    edt_hangsanxuat.setVisibility(View.VISIBLE);
                    edt_trongluong.setEnabled(true);
                    edt_trongluong.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btn_chonanh.setOnClickListener(v -> {
            selectImg();
        });

        btn_huy.setOnClickListener(v -> {
            dialog.cancel();
        });
        btn_add.setOnClickListener(v -> {
            if (validate()){
                cuaHang = new CuaHang();
                cuaHang.setName(edt_name.getText().toString().trim());
                cuaHang.setGia(Integer.parseInt(edt_gia.getText().toString().trim()));
                cuaHang.setSoLuong(Integer.parseInt(edt_soluong.getText().toString().trim()));
                if (strTheloai.equals("Món hàng")){
                    cuaHang.setTrongLuong(Float.parseFloat(edt_trongluong.getText().toString().trim()));
                    cuaHang.setHangSanXuat(edt_hangsanxuat.getText().toString().trim());
                }else if (strTheloai.equals("Dịch vụ")){
                    cuaHang.setTrongLuong(0);
                    cuaHang.setHangSanXuat("");
                }
                cuaHang.setTheloai(strTheloai);
                cuaHang.setImg(img);
                if (Integer.parseInt(edt_soluong.getText().toString().trim())>0){
                    cuaHang.setTinhTrang("Còn hàng");
                }else {
                    cuaHang.setTinhTrang("Hết hàng");
                }
                DuAn1DataBase.getInstance(getContext()).cuaHangDAO().insert(cuaHang);
                Toast.makeText(getContext(), "Insert món hàng thành công", Toast.LENGTH_SHORT).show();
                capNhat();
                dialog.dismiss();
            }
        });
    }
    private void capNhat(){
        list = DuAn1DataBase.getInstance(getContext()).cuaHangDAO().getAll();
        adapter = new CuaHangAdapter(getActivity());
        adapter.setData(list);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
    }
    private boolean validate(){
        if (edt_gia.getText().toString().trim().isEmpty() || edt_name.getText().toString().trim().isEmpty() || edt_soluong.getText().toString().trim().isEmpty()){
            Toast.makeText(getContext(), "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return false;
        }else {
            try {
                Integer.parseInt(edt_soluong.getText().toString().trim());
                Integer.parseInt(edt_gia.getText().toString().trim());
                if (!edt_trongluong.getText().toString().trim().isEmpty()){
                    Float.parseFloat(edt_trongluong.getText().toString().trim());
                }
                return true;
            }catch (Exception e){
                Toast.makeText(getContext(), "Định dạng không hợp lệ", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
    }
    private void selectImg(){
        Intent i = new Intent(
                Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        i.setType("image/*, video/*");
        if (i.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(i, IMAGE_PICKER_SELECT);
        }

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == IMAGE_PICKER_SELECT
                && resultCode == Activity.RESULT_OK) {
            img = getPathFromURI(Uri.parse(data.getDataString()));
            img_cuahang.setImageDrawable(Drawable.createFromPath(img));

        }
    }

    public void hideSoftKeyBroad(){
        InputMethodManager inputMethodManager = (InputMethodManager) getContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),0);
    }
    public String getPathFromURI(Uri contentUri) {
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getActivity().getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;
    }
    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (edt_name.getText().toString().trim().equals("PT")){
                edt_soluong.setText(DuAn1DataBase.getInstance(getContext()).adminDAO().getSoluongPT(edt_name.getText().toString().trim())+"");
                edt_soluong.setEnabled(false);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
}