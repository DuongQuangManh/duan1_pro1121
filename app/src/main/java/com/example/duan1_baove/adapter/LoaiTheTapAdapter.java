package com.example.duan1_baove.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1_baove.R;
import com.example.duan1_baove.database.DuAn1DataBase;
import com.example.duan1_baove.model.LoaiTheTap;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class LoaiTheTapAdapter extends RecyclerView.Adapter<LoaiTheTapAdapter.ViewHolder> implements Filterable {
    private Context context;

    private List<LoaiTheTap> list;
    private List<LoaiTheTap> listOld;
    EditText edt_maloaithe,edt_tenloaithe,edt_gialoaithe,edt_hansudung;
    Button btn_add,btn_huy;

    NumberFormat numberFormat = new DecimalFormat("###,###,###");
    public LoaiTheTapAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<LoaiTheTap> list){
        this.list = list;
        this.listOld = list;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_loaithetap,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LoaiTheTap loaiTheTap = list.get(position);
        if (loaiTheTap!=null){
            holder.tv_maloaithetap.setText("Mã loại thẻ tập: "+loaiTheTap.getId());
            holder.tv_tenloaithetap.setText("Tên loại thẻ tập: "+loaiTheTap.getName());
            holder.tv_gialoaithetap.setText("Giá: "+numberFormat.format(loaiTheTap.getGia()) + " vnđ");
            holder.tv_hansudung.setText("Hạn sử dụng: "+loaiTheTap.getHanSuDung()+" tháng");
            holder.update.setOnLongClickListener(v -> {
                new AlertDialog.Builder(context).setTitle("Xoá loại thẻ tập ?")
                        .setMessage("Bạn có chắc chắn muốn loại thẻ tập ?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                DuAn1DataBase.getInstance(context).loaiTheTapDAO().delete(loaiTheTap);
                                list.remove(loaiTheTap);
                                notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("No",null)
                        .show();
                return true;
            });
            holder.update.setOnClickListener(v -> {
                Dialog dialog = new Dialog(context);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_addloaithetap);
                dialog.show();
                Window window = dialog.getWindow();
                if (window == null){
                    return;
                }
                window.setBackgroundDrawable(null);
                edt_maloaithe = dialog.findViewById(R.id.edt_maloaithetap_dialogloaithetap);
                edt_tenloaithe = dialog.findViewById(R.id.edt_tenthetap_dialogloaithetap);
                edt_gialoaithe = dialog.findViewById(R.id.edt_giathetap_dialogloaithetap);
                edt_hansudung = dialog.findViewById(R.id.edt_hansudung_dialogloaithetap);
                btn_add = dialog.findViewById(R.id.btn_luu_dialogloaithetap);
                btn_huy = dialog.findViewById(R.id.btn_huy_dialogloaithetap);

                edt_maloaithe.setText(loaiTheTap.getId()+"");
                edt_tenloaithe.setText(loaiTheTap.getName());
                edt_gialoaithe.setText(loaiTheTap.getGia()+"");
                edt_hansudung.setText(loaiTheTap.getHanSuDung());

                btn_huy.setOnClickListener(v1 -> {
                    dialog.cancel();
                });
                btn_add.setOnClickListener(v1 -> {
                    if (validate()){
                        loaiTheTap.setName(edt_tenloaithe.getText().toString().trim());
                        loaiTheTap.setGia(Integer.parseInt(edt_gialoaithe.getText().toString()));
                        loaiTheTap.setHanSuDung(edt_hansudung.getText().toString().trim());
                        DuAn1DataBase.getInstance(context).loaiTheTapDAO().update(loaiTheTap);
                        Toast.makeText(context, "Update loại thẻ tập thành công", Toast.LENGTH_SHORT).show();
                        dialog.cancel();
                        notifyDataSetChanged();
                    }
                });
            });
        }

    }

    @Override
    public int getItemCount() {
        if (list != null){
            return list.size();
        }
        return 0;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String strSearch = constraint.toString();
                if (strSearch.isEmpty()){
                    list = listOld;
                }else {
                    List<LoaiTheTap> listnew = new ArrayList<>();
                    for (LoaiTheTap loaiTheTap:listOld){
                        if (loaiTheTap.getName().toLowerCase().contains(strSearch.toLowerCase())){
                            listnew.add(loaiTheTap);
                        }
                    }
                    list = listnew;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = list;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                list = (List<LoaiTheTap>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_maloaithetap,tv_tenloaithetap,tv_gialoaithetap,tv_hansudung;
        private RelativeLayout update;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_maloaithetap = itemView.findViewById(R.id.tv_maloaithetap_itemloaithetap);
            tv_tenloaithetap = itemView.findViewById(R.id.tv_tenthetap_itemloaithetap);
            tv_gialoaithetap = itemView.findViewById(R.id.tv_gia_itemloaithetap);
            tv_hansudung = itemView.findViewById(R.id.tv_hansudung_itemloaithetap);
            update = itemView.findViewById(R.id.layout_update_itemloaithetap);
        }
    }
    private boolean validate(){
        if (edt_tenloaithe.getText().toString().isEmpty() || edt_gialoaithe.getText().toString().isEmpty() || edt_hansudung.getText().toString().isEmpty()){
            Toast.makeText(context, "Vui lòng không bỏ trống thông tin", Toast.LENGTH_SHORT).show();
            return false;
        }else {
            try {
                Integer.parseInt(edt_gialoaithe.getText().toString());
                Integer.parseInt(edt_hansudung.getText().toString());
                return true;
            }catch (Exception e){
                Toast.makeText(context, "Giá và hạn sử dụng phải là số", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
    }
}
