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
import com.example.duan1_baove.adapter.SpinnerAdapter;
import com.example.duan1_baove.database.DuAn1DataBase;
import com.example.duan1_baove.model.Admin;
import com.example.duan1_baove.model.ChucVu;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class NhanVien_Fragment_Admin extends Fragment {

    private static final int IMAGE_PICKER_SELECT = 0;
    List<Admin> list;
    View view;
    RecyclerView recyclerView;
    LinearLayout layout_search;
    EditText edt_search;
    FloatingActionButton fab1,fab2,fab3;
    Animation fab_open,fab_close,rotateForward,rotateBackward;
    boolean isOpen;

    CircleImageView avt;
    EditText edt_user,edt_name,edt_pass,edt_luong,edt_tennganhang,edt_stk;
    Spinner spn_chucvu,spn_locchucvu;
    Button btn_add,btn_huy;

    SpinnerAdapter spinerAdapter;
    List<ChucVu> chucVuList;
    NhanVienAdapter adapter;
    int chucvu_id;
    Admin admin;
    String img;

    String[] locchucvu = {"Tất cả","admin","PT","Quản lý thu ngân","Bảo vệ","Seller"};
    String strLocChuVu;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_nhan_vien___admin, container, false);
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
        ArrayAdapter adapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_dropdown_item,locchucvu);
        spn_locchucvu.setAdapter(adapter);
        spn_locchucvu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                strLocChuVu = locchucvu[position];
                if (strLocChuVu.equals("Tất cả")){
                    capNhat();
                }else{
                    locthechucvu(strLocChuVu);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return view;
    }

    private void locthechucvu(String loc){
        adapter.getFilter().filter(loc);
    }

    private void initUi() {
        recyclerView = view.findViewById(R.id.rcy_nhanvien);
        fab1 = view.findViewById(R.id.fab1_nhanvien);
        fab2 = view.findViewById(R.id.fab2_nhanvien);
        fab3 = view.findViewById(R.id.fab3_nhanvien);
        fab_open = AnimationUtils.loadAnimation(getContext(),R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getContext(),R.anim.fab_close);
        rotateForward = AnimationUtils.loadAnimation(getContext(),R.anim.rotate_forward);
        rotateBackward = AnimationUtils.loadAnimation(getContext(),R.anim.rotate_backward);
        layout_search = view.findViewById(R.id.layout_search_nhanvien);
        edt_search = view.findViewById(R.id.edt_search_nhanvien);
        spn_locchucvu = view.findViewById(R.id.spn_chucvu_nhanvien);
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
        dialog.setContentView(R.layout.dialog_addnhanvien);
        dialog.show();
        Window window = dialog.getWindow();
        if (window == null){
            return;
        }
        window.setBackgroundDrawable(null);
        avt = dialog.findViewById(R.id.avt_dialognhanvien);
        edt_user = dialog.findViewById(R.id.edt_taikhoan_dialognhanvien);
        edt_name = dialog.findViewById(R.id.edt_hoten_dialognhanvien);
        edt_pass = dialog.findViewById(R.id.edt_mk_dialognhanvien);
        edt_luong = dialog.findViewById(R.id.edt_luong_dialognhanvien);
        edt_tennganhang = dialog.findViewById(R.id.edt_tennganhang_dialognhanvien);
        edt_stk = dialog.findViewById(R.id.edt_stk_dialognhanvien);
        btn_add = dialog.findViewById(R.id.btn_luu_dialognhanvien);
        btn_huy = dialog.findViewById(R.id.btn_huy_dialognhanvien);
        spn_chucvu = dialog.findViewById(R.id.spinner_chucvu_dialognhanvien);

        chucVuList = DuAn1DataBase.getInstance(getContext()).chucVuDAO().getAll();
        spinerAdapter = new SpinnerAdapter(getContext(),R.layout.item_spiner,chucVuList);
        spn_chucvu.setAdapter(spinerAdapter);

        avt.setOnClickListener(v -> {
            selectImg();
        });

        spn_chucvu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                chucvu_id = chucVuList.get(position).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btn_huy.setOnClickListener(v -> {
            dialog.cancel();
        });
        btn_add.setOnClickListener(v -> {
            if (validate()){
                admin = new Admin();
                admin.setUser(edt_user.getText().toString().trim());
                admin.setName(edt_name.getText().toString().trim());
                admin.setPass(edt_pass.getText().toString().trim());
                admin.setChucvu_id(chucvu_id);
                admin.setLuong(Integer.parseInt(edt_luong.getText().toString().trim()));
                admin.setTennganhang(edt_tennganhang.getText().toString().trim());
                admin.setStk(edt_stk.getText().toString().trim());
                admin.setHinhanh(img);
                DuAn1DataBase.getInstance(getContext()).adminDAO().insert(admin);
                Toast.makeText(getContext(), "Insert nhân viên thành công", Toast.LENGTH_SHORT).show();
                capNhat();
                dialog.dismiss();
            }
        });
    }


    private void capNhat(){
        list = DuAn1DataBase.getInstance(getContext()).adminDAO().getAll();
        adapter = new NhanVienAdapter(getActivity());
        adapter.setData(list);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
    }
    private boolean validate(){
        if (edt_user.getText().toString().trim().isEmpty() || edt_name.getText().toString().trim().isEmpty() || edt_pass.getText().toString().trim().isEmpty() || edt_luong.getText().toString().trim().isEmpty()){
            Toast.makeText(getContext(), "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return false;
        }else {
            if (edt_pass.getText().toString().trim().length()<8){
                Toast.makeText(getContext(), "Vui lòng nhập mật khẩu dài hơn 8 kí tự", Toast.LENGTH_SHORT).show();
                return false;
            }else {
                try {
                    Integer.parseInt(edt_luong.getText().toString().trim());
                    return true;
                }catch (Exception e){
                    Toast.makeText(getContext(), "Lương không hợp lệ", Toast.LENGTH_SHORT).show();
                    return false;
                }
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
            avt.setImageDrawable(Drawable.createFromPath(img));

        }
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
    public void hideSoftKeyBroad(){
        InputMethodManager inputMethodManager = (InputMethodManager) getContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),0);
    }
}