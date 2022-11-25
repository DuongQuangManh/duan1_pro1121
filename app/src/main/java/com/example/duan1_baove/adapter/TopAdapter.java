package com.example.duan1_baove.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1_baove.R;
import com.example.duan1_baove.database.DuAn1DataBase;
import com.example.duan1_baove.model.ThongKe;

import java.util.List;

public class TopAdapter extends RecyclerView.Adapter<TopAdapter.ViewHolder> {
    private Context context;
    private List<ThongKe> list;

    public TopAdapter(Context context) {
        this.context = context;
    }
    public void setData(List<ThongKe> list){
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_top,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ThongKe thongKe = list.get(position);
        if (thongKe!=null){
            holder.tv_id.setText(position+1+"");
            holder.tv_name.setText(DuAn1DataBase.getInstance(context).cuaHangDAO().getName(thongKe.getCuahang_id()));
            holder.tv_sl.setText(thongKe.getSoluong()+"");
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
        TextView tv_id,tv_name,tv_sl;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_id = itemView.findViewById(R.id.tv_id_top);
            tv_name = itemView.findViewById(R.id.tv_name_top);
            tv_sl = itemView.findViewById(R.id.tv_soluongtop);
        }
    }
}
