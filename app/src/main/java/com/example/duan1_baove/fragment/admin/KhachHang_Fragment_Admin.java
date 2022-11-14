package com.example.duan1_baove.fragment.admin;

import android.app.Activity;
import android.app.DatePickerDialog;
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
import android.text.InputType;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.duan1_baove.R;
import com.example.duan1_baove.adapter.KhachHangAdapter;
import com.example.duan1_baove.database.DuAn1DataBase;
import com.example.duan1_baove.model.KhachHang;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Calendar;
import java.util.List;
import java.util.regex.Pattern;

import de.hdodenhof.circleimageview.CircleImageView;

public class KhachHang_Fragment_Admin extends Fragment {
    private static final int IMAGE_PICKER_SELECT = 0;
    List<KhachHang> list;
    View view;
    RecyclerView recyclerView;
    LinearLayout layout_search;
    EditText edt_search;
    FloatingActionButton fab1,fab2,fab3;
    Animation fab_open,fab_close,rotateForward,rotateBackward;
    boolean isOpen;

    EditText edt_user,edt_name,edt_ngaysinh,edt_pass;
    TextInputLayout txt_pass,txt_ngaysinh;
    Button btn_add,btn_huy;
    Spinner spn_gioitinh;
    CircleImageView avt_khachhang;
    Calendar lich = Calendar.getInstance();
    String[] Listgioitinh = {"Nam","Nữ"};
    ArrayAdapter gioitinhAdapter;
    String gioitinh;
    KhachHang khachHang;
    String img;

    KhachHangAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_khach_hang___admin, container, false);
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
        recyclerView = view.findViewById(R.id.rcy_khachhang);
        fab1 = view.findViewById(R.id.fab1_khachhang);
        fab2 = view.findViewById(R.id.fab2_khachhang);
        fab3 = view.findViewById(R.id.fab3_khachhang);
        fab_open = AnimationUtils.loadAnimation(getContext(),R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getContext(),R.anim.fab_close);
        rotateForward = AnimationUtils.loadAnimation(getContext(),R.anim.rotate_forward);
        rotateBackward = AnimationUtils.loadAnimation(getContext(),R.anim.rotate_backward);
        layout_search = view.findViewById(R.id.layout_search_khachhang);
        edt_search = view.findViewById(R.id.edt_search_khachhang);
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
        dialog.setContentView(R.layout.dialog_addkhachhang);
        dialog.show();
        Window window = dialog.getWindow();
        if (window == null){
            return;
        }
        window.setBackgroundDrawable(null);
        avt_khachhang = dialog.findViewById(R.id.avt_dialogkhachhang);
        edt_user = dialog.findViewById(R.id.edt_tk_dialogkhachhang);
        edt_name = dialog.findViewById(R.id.edt_name_dialogkhachhang);
        edt_ngaysinh = dialog.findViewById(R.id.edt_ngaysinh_dialogkhachhang);
        edt_pass = dialog.findViewById(R.id.edt_mk1_dialogkhachhang);
        txt_pass = dialog.findViewById(R.id.textinput1_dialogkhachhang);
        txt_ngaysinh = dialog.findViewById(R.id.txtinput_ngay_dialogkhachhang);
        btn_add = dialog.findViewById(R.id.btn_luu_dialogkhachhang);
        btn_huy = dialog.findViewById(R.id.btn_huy_dialogkhachhang);
        spn_gioitinh = dialog.findViewById(R.id.spn_gioitinh_dialogkhachhang);

        gioitinhAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_dropdown_item,Listgioitinh);
        spn_gioitinh.setAdapter(gioitinhAdapter);

        avt_khachhang.setOnClickListener(v -> {
            selectImg();
        });

        spn_gioitinh.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                gioitinh = Listgioitinh[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        txt_ngaysinh.setStartIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int year = lich.get(Calendar.YEAR);
                int month = lich.get(Calendar.MONTH);
                int day = lich.get(Calendar.DAY_OF_MONTH);
                new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        edt_ngaysinh.setText(dayOfMonth+"-"+(month+1)+"-"+year);
                    }
                },year,month,day).show();
            }
        });
        txt_pass.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showpass(txt_pass,edt_pass);
            }
        });

        btn_huy.setOnClickListener(v -> {
            dialog.cancel();
        });
        btn_add.setOnClickListener(v -> {
            if (validate()){
                khachHang = new KhachHang();
                khachHang.setGioitinh(gioitinh);
                khachHang.setHoten(edt_name.getText().toString().trim());
                khachHang.setSoDienThoai(edt_user.getText().toString().trim());
                khachHang.setSoDu(0);
                khachHang.setNamSinh(edt_ngaysinh.getText().toString().trim());
                khachHang.setPass(edt_pass.getText().toString().trim());
                khachHang.setAvata(img);
                List<KhachHang> check = DuAn1DataBase.getInstance(getContext()).khachHangDAO().checkAcc(khachHang.getSoDienThoai());
                if (check.size()>0){
                    Toast.makeText(getContext(), "Tài khoản đã tồn tại", Toast.LENGTH_SHORT).show();
                }else {
                    DuAn1DataBase.getInstance(getContext()).khachHangDAO().insert(khachHang);
                    Toast.makeText(getContext(), "Insert khách hàng thành công", Toast.LENGTH_SHORT).show();
                    capNhat();
                    dialog.dismiss();
                }
            }
        });
    }

    private void capNhat(){
        list = DuAn1DataBase.getInstance(getContext()).khachHangDAO().getAll();
        adapter = new KhachHangAdapter(getActivity());
        adapter.setData(list);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
    }
    private boolean validate(){
        String email = "^\\w@\\w.\\w";
        String sdt = "^0\\d{9}";
        Pattern patternEmail = Pattern.compile(email);
        Pattern patternSdt = Pattern.compile(sdt);
        String user = edt_user.getText().toString().trim();
        if (edt_user.getText().toString().trim().isEmpty()||edt_name.getText().toString().trim().isEmpty()||edt_ngaysinh.getText().toString().trim().isEmpty()|| edt_pass.getText().toString().trim().isEmpty()){
            Toast.makeText(getContext(), "Vui lòng không bỏ trống thông tin", Toast.LENGTH_SHORT).show();
            return false;
        }else {
            if (patternEmail.matcher(user).find() || patternSdt.matcher(user).find()){
                if (edt_pass.getText().toString().trim().length()<8){
                    Toast.makeText(getContext(), "Vui lòng nhập mật khẩu dài hơn 8 kí tự", Toast.LENGTH_SHORT).show();
                    return false;
                }else {
                    return true;
                }
            }
            else {
                Toast.makeText(getContext(), "Vui lòng nhập đúng định dạng tài khoản", Toast.LENGTH_SHORT).show();
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
            avt_khachhang.setImageDrawable(Drawable.createFromPath(img));

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
    private void showpass(TextInputLayout txt,EditText edt) {
        if(edt.getInputType() == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
            edt.setInputType( InputType.TYPE_CLASS_TEXT |
                    InputType.TYPE_TEXT_VARIATION_PASSWORD);
            txt.setEndIconDrawable(R.drawable.ic_eye_off);
        }else {
            edt.setInputType( InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD );
            txt.setEndIconDrawable(R.drawable.ic_eye);
        }
        edt.setSelection(edt.getText().length());
    }
}