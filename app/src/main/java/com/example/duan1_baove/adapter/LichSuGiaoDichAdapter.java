package com.example.duan1_baove.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1_baove.R;
import com.example.duan1_baove.model.LichSuGiaoDich;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

public class LichSuGiaoDichAdapter extends RecyclerView.Adapter<LichSuGiaoDichAdapter.ViewHolder> {
    private Context context;
    private List<LichSuGiaoDich> list;
    NumberFormat numberFormat = new DecimalFormat("###,###,###");

    public LichSuGiaoDichAdapter(Context context) {
        this.context = context;
    }
    public void setData(List<LichSuGiaoDich> list){
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_lichsugiaodich,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LichSuGiaoDich lichSuGiaoDich = list.get(position);
        if (lichSuGiaoDich!=null){
            Log.d("920",lichSuGiaoDich.getThoigian());
            holder.tv_ngay.setText(lichSuGiaoDich.getThoigian());

            if (lichSuGiaoDich.getType().equals("Cộng")){
                holder.tv_sotien.setTextColor(Color.GREEN);
                holder.img_upordown.setImageResource(R.drawable.ic_down);
                holder.tv_sotien.setText("+"+numberFormat.format(lichSuGiaoDich.getSoTien())+" vnđ");
            }else if (lichSuGiaoDich.getType().equals("Trừ")){
                holder.tv_sotien.setText("-"+numberFormat.format(lichSuGiaoDich.getSoTien())+" vnđ");
                holder.tv_sotien.setTextColor(Color.RED);
                holder.img_upordown.setImageResource(R.drawable.ic_up);
            }
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
        private TextView tv_ngay,tv_sotien;
        private ImageView img_upordown;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_ngay = itemView.findViewById(R.id.tv_ngay_itemlichsugiaodich);
            tv_sotien = itemView.findViewById(R.id.tv_sotien_itemlichsugiaodich);
            img_upordown = itemView.findViewById(R.id.img_icon_itemlichsugiaodich);
        }
    }
}
