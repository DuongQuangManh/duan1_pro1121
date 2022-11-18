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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.duan1_baove.R;
import com.example.duan1_baove.adapter.ThietBiAdapter;
import com.example.duan1_baove.database.DuAn1DataBase;
import com.example.duan1_baove.model.ThietBi;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Calendar;
import java.util.List;

public class ThietBi_Fragment_Admin extends Fragment {
    private static final int IMAGE_PICKER_SELECT = 0;
    List<ThietBi> list;
    View view;
    RecyclerView recyclerView;
    LinearLayout layout_search;
    EditText edt_search;
    FloatingActionButton fab1,fab2,fab3;
    Animation fab_open,fab_close,rotateForward,rotateBackward;
    boolean isOpen;

    ThietBi thietBi;

    EditText edt_id,edt_name,edt_loai,edt_soluong,edt_hangsanxuat,edt_ngaymua,edt_baotrigannhat;
    TextInputLayout txt_ngaymua,txt_ngaybaotri;
    Button btn_chonanh,btn_add,btn_huy;
    ImageView img_thietbi;
    String img;
    Calendar lich = Calendar.getInstance();
    ThietBiAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_thiet_bi___admin, container, false);
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
        recyclerView = view.findViewById(R.id.rcy_thietbi);
        fab1 = view.findViewById(R.id.fab1_thietbi);
        fab2 = view.findViewById(R.id.fab2_thietbi);
        fab3 = view.findViewById(R.id.fab3_thietbi);
        fab_open = AnimationUtils.loadAnimation(getContext(),R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getContext(),R.anim.fab_close);
        rotateForward = AnimationUtils.loadAnimation(getContext(),R.anim.rotate_forward);
        rotateBackward = AnimationUtils.loadAnimation(getContext(),R.anim.rotate_backward);
        layout_search = view.findViewById(R.id.layout_search_thietbi);
        edt_search = view.findViewById(R.id.edt_search_thietbi);
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
        dialog.setContentView(R.layout.dialog_addthietbi);
        dialog.show();
        Window window = dialog.getWindow();
        if (window == null){
            return;
        }
        window.setBackgroundDrawable(null);
        edt_id = dialog.findViewById(R.id.edt_id_dialogthietbi);
        edt_name = dialog.findViewById(R.id.edt_ten_dialogthietbi);
        edt_loai = dialog.findViewById(R.id.edt_loai_dialogthietbi);
        edt_soluong = dialog.findViewById(R.id.edt_soLuong_dialogthietbi);
        edt_hangsanxuat = dialog.findViewById(R.id.edt_hangsanxuat_dialogthietbi);
        btn_chonanh = dialog.findViewById(R.id.btn_selectimage_dialogthietbi);
        img_thietbi = dialog.findViewById(R.id.avt_dialogthietbi);
        btn_add = dialog.findViewById(R.id.btn_luu_dialogthietbi);
        btn_huy = dialog.findViewById(R.id.btn_huy_dialogthietbi);
        txt_ngaymua = dialog.findViewById(R.id.txti_ngaymua_dialogthietbi);
        txt_ngaybaotri =dialog.findViewById(R.id.txti_ngaybaotrigannhat_dialogthietbi);
        edt_baotrigannhat = dialog.findViewById(R.id.edt_ngaybaotrigannhat_dialogthietbi);
        edt_ngaymua = dialog.findViewById(R.id.edt_ngaymua_dialogthietbi);

        btn_chonanh.setOnClickListener(v -> {
            selectImg();
        });

        txt_ngaymua.setStartIconOnClickListener(v -> {
            int year = lich.get(Calendar.YEAR);
            int month = lich.get(Calendar.MONTH);
            int day = lich.get(Calendar.DAY_OF_MONTH);
            new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    edt_ngaymua.setText(dayOfMonth+"-"+(month+1)+"-"+year);
                }
            },year,month,day).show();
        });
        txt_ngaybaotri.setStartIconOnClickListener(v -> {
            int year = lich.get(Calendar.YEAR);
            int month = lich.get(Calendar.MONTH);
            int day = lich.get(Calendar.DAY_OF_MONTH);
            new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    edt_baotrigannhat.setText(dayOfMonth+"-"+(month+1)+"-"+year);
                }
            },year,month,day).show();
        });

        btn_huy.setOnClickListener(v -> {
            dialog.cancel();
        });
        btn_add.setOnClickListener(v -> {
            if (validate()){
                thietBi = new ThietBi();
                thietBi.setName(edt_name.getText().toString().trim());
                thietBi.setHangSanXuat(edt_hangsanxuat.getText().toString().trim());
                thietBi.setLoai(edt_loai.getText().toString().trim());
                thietBi.setSoLuong(Integer.parseInt(edt_soluong.getText().toString().trim()));
                thietBi.setHinhanh(img);
                thietBi.setTongchiphibaotri(0);
                thietBi.setThoigianmua(edt_ngaymua.getText().toString().trim());
                thietBi.setThoigianbaotrigannhat(edt_ngaymua.getText().toString().trim());
                DuAn1DataBase.getInstance(getContext()).thietBiDAO().insert(thietBi);
                Toast.makeText(getContext(), "Insert thiết bị thành công", Toast.LENGTH_SHORT).show();
                capNhat();
                dialog.dismiss();
            }
        });
    }
    private void capNhat(){
        list = DuAn1DataBase.getInstance(getContext()).thietBiDAO().getAll();
        adapter = new ThietBiAdapter(getActivity());
        adapter.setData(list);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
    }
    private boolean validate(){
        if (edt_loai.getText().toString().trim().isEmpty() || edt_name.getText().toString().trim().isEmpty() || edt_soluong.getText().toString().trim().isEmpty() || edt_hangsanxuat.getText().toString().trim().isEmpty()||edt_ngaymua.getText().toString().trim().isEmpty()||edt_baotrigannhat.getText().toString().trim().isEmpty()){
            Toast.makeText(getContext(), "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return false;
        }else {
            try {
                Integer.parseInt(edt_soluong.getText().toString().trim());
                return true;
            }catch (Exception e){
                Toast.makeText(getContext(), "Số lượng không hợp lệ", Toast.LENGTH_SHORT).show();
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
            img_thietbi.setImageDrawable(Drawable.createFromPath(img));

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
}