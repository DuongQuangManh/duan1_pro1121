package com.example.duan1_baove.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.LongDef;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1_baove.R;
import com.example.duan1_baove.database.DuAn1DataBase;
import com.example.duan1_baove.model.TheTap;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class TheTapAdapter extends RecyclerView.Adapter<TheTapAdapter.ViewHolder> {
    private Context context;
    private List<TheTap> list;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    Calendar calendar = Calendar.getInstance();
    int year = calendar.get(Calendar.YEAR);
    int month = calendar.get(Calendar.MONTH)+1;
    int day = calendar.get(Calendar.DAY_OF_MONTH);

    public TheTapAdapter(Context context) {
        this.context = context;
    }
    public void setData(List<TheTap> list){
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_thetap,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TheTap theTap = list.get(position);
        if (theTap!=null){
            holder.tv_id.setText("Mã thẻ tập: "+theTap.getId());
            holder.tv_hoten.setText("Họ tên: "+ DuAn1DataBase.getInstance(context).khachHangDAO().checkAcc(theTap.getKhachhang_id()).get(0).getHoten());
            holder.tv_loathe.setText("Loại thẻ: "+ DuAn1DataBase.getInstance(context).loaiTheTapDAO().getByID(String.valueOf(theTap.getLoaithetap_id())).get(0).getName());
            holder.tv_starttime.setText("Thời gian bắt đầu: "+theTap.getNgayDangKy());
            holder.tv_endtime.setText("Thời gian kết thúc: "+theTap.getNgayHetHan());
            try {
                Date datenow = sdf.parse(day+"-"+month+"-"+year);
                Date dateend = sdf.parse(theTap.getNgayHetHan());
                if (datenow.after(dateend)){
                    holder.tv_trangthai.setTextColor(Color.GREEN);
                    holder.tv_trangthai.setText("Trạng thái: Chưa hết hạn");
                    Log.d("ngay","GREEN");
                }else {
                    Log.d("ngay","Red");
                    holder.tv_trangthai.setTextColor(Color.RED);
                    holder.tv_trangthai.setText("Trạng thái: hết hạn");
                }
            } catch (Exception e) {
                e.printStackTrace();
                Log.d("zzzz",e.getMessage());
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
        private TextView tv_id,tv_hoten,tv_loathe,tv_starttime,tv_endtime,tv_trangthai;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_id = itemView.findViewById(R.id.tv_mathetap_itemthetap);
            tv_hoten = itemView.findViewById(R.id.tv_tenkhachhang_itemthetap);
            tv_loathe = itemView.findViewById(R.id.tv_tenloaithetap_itemthetap);
            tv_starttime = itemView.findViewById(R.id.tv_starttime_itemthetap);
            tv_endtime = itemView.findViewById(R.id.tv_endtime_itemthetap);
            tv_trangthai = itemView.findViewById(R.id.tv_trangthai_itemthetap);
        }
    }
}
