package com.example.duan1_baove.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1_baove.R;
import com.example.duan1_baove.database.DuAn1DataBase;
import com.example.duan1_baove.model.NapTien;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

public class DanhSachNapTienAdapter extends RecyclerView.Adapter<DanhSachNapTienAdapter.ViewHolder> {
    private Context context;
    private List<NapTien> list;
    IClickListener iClickListener;
    NumberFormat numberFormat = new DecimalFormat("###,###,###");

    public DanhSachNapTienAdapter(Context context,IClickListener iClickListener) {
        this.context = context;
        this.iClickListener = iClickListener;
    }
    public interface IClickListener{
        void duyet(NapTien napTien);
    }
    public void setData(List<NapTien> list){
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_danhsachyeucaunaptien,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        NapTien napTien = list.get(position);
        if (napTien!=null){
            holder.tv_name.setText(DuAn1DataBase.getInstance(context).khachHangDAO().getName(napTien.getKhachhang_id()));
            holder.tv_ngay.setText(napTien.getDate());
            holder.tv_sotien.setText(numberFormat.format(napTien.getSotien())+" vnđ");
            if (napTien.getTrangthai().equals("Đã kiểm duyệt")){
                holder.tv_trangthai.setTextColor(Color.GREEN);
                holder.tv_trangthai.setEnabled(false);
            }else {
                holder.tv_trangthai.setTextColor(Color.RED);
                holder.tv_trangthai.setEnabled(true);
            }
            holder.tv_trangthai.setText(napTien.getTrangthai());
            holder.tv_trangthai.setOnClickListener(v -> {
                iClickListener.duyet(napTien);
            });
        }
    }

    @Override
    public int getItemCount() {
        if (list!=null){
            return list.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_name,tv_ngay,tv_sotien,tv_trangthai;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.tv_hoten_dsnt);
            tv_ngay = itemView.findViewById(R.id.tv_ngay_dsnt);
            tv_sotien = itemView.findViewById(R.id.tv_sotien_dsnt);
            tv_trangthai = itemView.findViewById(R.id.tv_trangthai_dsnt);
        }
    }
}
