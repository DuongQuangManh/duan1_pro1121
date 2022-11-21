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
import com.example.duan1_baove.model.ChucVu;

import java.util.ArrayList;
import java.util.List;

public class ChucVuAdapter extends RecyclerView.Adapter<ChucVuAdapter.ViewHolder> implements Filterable {
    private Context context;

    private List<ChucVu> list;
    private List<ChucVu> listOld;
    private EditText edt_machucvu,edt_tenchucvu;
    private Button btn_add,btn_huy;

    public ChucVuAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<ChucVu> list){
        this.list = list;
        this.listOld = list;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_chucvu,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ChucVu chucVu = list.get(position);
        if (chucVu!=null){
            holder.tv_machucvu.setText("Mã chức vụ: "+chucVu.getId());
            holder.tv_tenchucvu.setText("Tên chức vụ: "+chucVu.getTenchucvu());
            holder.update.setOnLongClickListener(v -> {
                new AlertDialog.Builder(context).setTitle("Xoá chức vụ ?")
                        .setMessage("Bạn có chắc chắn muốn xoá chức vụ ?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (!chucVu.getTenchucvu().equals("admin")){
                                    DuAn1DataBase.getInstance(context).chucVuDAO().delete(chucVu);
                                    list.remove(chucVu);
                                    notifyDataSetChanged();
                                }else {
                                    Toast.makeText(context, "Không thể xoá admin", Toast.LENGTH_SHORT).show();
                                }

                            }
                        })
                        .setNegativeButton("No",null)
                        .show();
                return true;
            });
            holder.update.setOnClickListener(v -> {
                Dialog dialog = new Dialog(context);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_addchucvu);
                dialog.show();
                Window window = dialog.getWindow();
                if (window==null){
                    return;
                }
                window.setBackgroundDrawable(null);
                edt_machucvu = dialog.findViewById(R.id.edt_machucvu_dialogchucvu);
                edt_tenchucvu = dialog.findViewById(R.id.edt_tenchucvu_dialogchucvu);
                btn_add = dialog.findViewById(R.id.btn_luu_dialogchucvu);
                btn_huy = dialog.findViewById(R.id.btn_huy_dialogchucvu);

                edt_machucvu.setText(chucVu.getId()+"");
                edt_tenchucvu.setText(chucVu.getTenchucvu());
                btn_huy.setOnClickListener(v1 -> {
                    dialog.cancel();
                });
                btn_add.setOnClickListener(v1 -> {
                    if (validate()){
                        String ten = edt_tenchucvu.getText().toString().trim();
                        chucVu.setTenchucvu(ten);
                        DuAn1DataBase.getInstance(context).chucVuDAO().update(chucVu);
                        dialog.dismiss();
                        notifyDataSetChanged();
                    }else {
                        Toast.makeText(context, "Không được để trống thông tin", Toast.LENGTH_SHORT).show();
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
                    List<ChucVu> listnew = new ArrayList<>();
                    for (ChucVu chucVu:listOld){
                        if (chucVu.getTenchucvu().toLowerCase().contains(strSearch.toLowerCase())){
                            listnew.add(chucVu);
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
                list = (List<ChucVu>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_machucvu,tv_tenchucvu;
        private RelativeLayout update;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_machucvu = itemView.findViewById(R.id.tv_machucvu_itemchucvu);
            tv_tenchucvu = itemView.findViewById(R.id.tv_tenchucvu_itemchucvu);
            update = itemView.findViewById(R.id.layout_update_itemchucvu);
        }
    }
    private boolean validate(){
        if (edt_tenchucvu.getText().toString().trim().isEmpty()){
            return false;
        }else {
            return true;
        }
    }
}
