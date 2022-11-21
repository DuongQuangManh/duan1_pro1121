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
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.duan1_baove.R;
import com.example.duan1_baove.adapter.ChucVuAdapter;
import com.example.duan1_baove.database.DuAn1DataBase;
import com.example.duan1_baove.model.ChucVu;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class ChucVu_Fragment_Admin extends Fragment {
    View view;
    RecyclerView recyclerView;
    LinearLayout layout_search;
    EditText edt_search;
    FloatingActionButton fab1,fab2,fab3;
    Animation fab_open,fab_close,rotateForward,rotateBackward;
    boolean isOpen;


    EditText edt_machuvu,edt_tenchucvu;
    Button btn_add,btn_huy;

    ChucVuAdapter adapter;
    List<ChucVu> list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_chuc_vu___admin, container, false);
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
        recyclerView = view.findViewById(R.id.rcy_chucvu);
        fab1 = view.findViewById(R.id.fab1_chucvu);
        fab2 = view.findViewById(R.id.fab2_chucvu);
        fab3 = view.findViewById(R.id.fab3_chucvu);
        fab_open = AnimationUtils.loadAnimation(getContext(),R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getContext(),R.anim.fab_close);
        rotateForward = AnimationUtils.loadAnimation(getContext(),R.anim.rotate_forward);
        rotateBackward = AnimationUtils.loadAnimation(getContext(),R.anim.rotate_backward);
        layout_search = view.findViewById(R.id.layout_search_chucvu);
        edt_search = view.findViewById(R.id.edt_search_chucvu);
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
        dialog.setContentView(R.layout.dialog_addchucvu);
        dialog.show();
        Window window = dialog.getWindow();
        if (window==null){
            return;
        }
        window.setBackgroundDrawable(null);
        edt_machuvu = dialog.findViewById(R.id.edt_machucvu_dialogchucvu);
        edt_tenchucvu = dialog.findViewById(R.id.edt_tenchucvu_dialogchucvu);
        btn_add = dialog.findViewById(R.id.btn_luu_dialogchucvu);
        btn_huy = dialog.findViewById(R.id.btn_huy_dialogchucvu);

        btn_huy.setOnClickListener(v -> {
            dialog.cancel();
        });
        btn_add.setOnClickListener(v -> {
            if (validate()){
                ChucVu chucVu = new ChucVu();
                chucVu.setTenchucvu(edt_tenchucvu.getText().toString().trim());
                DuAn1DataBase.getInstance(getContext()).chucVuDAO().insert(chucVu);
                capNhat();
                dialog.dismiss();
                Toast.makeText(getContext(), "Insert chức vụ thành công", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(getContext(), "Không được để trống thông tin", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private boolean validate(){
        if (edt_tenchucvu.getText().toString().trim().isEmpty()){
            return false;
        }else {
            return true;
        }
    }
    public void capNhat(){
        list = DuAn1DataBase.getInstance(getContext()).chucVuDAO().getAll();
        adapter = new ChucVuAdapter(getContext());
        adapter.setData(list);
        LinearLayoutManager manager = new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
    }
    public void hideSoftKeyBroad(){
        InputMethodManager inputMethodManager = (InputMethodManager) getContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),0);
    }

}