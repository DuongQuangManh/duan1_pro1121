package com.example.duan1_baove.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.util.DebugUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.duan1_baove.Admin_MainActivity;
import com.example.duan1_baove.R;
import com.example.duan1_baove.database.DuAn1DataBase;
import com.example.duan1_baove.model.ThongBao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class ThongBaoAdapter extends RecyclerView.Adapter<ThongBaoAdapter.ViewHolder> {
    private Context context;
    private List<ThongBao> list;

    private EditText edt_id,edt_title,edt_content;
    private Button btn_add,btn_huy;

    private MyOnclick myOnclick;

    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy,HH:mm:ss", Locale.getDefault());
    String currentDateandTime = sdf.format(new Date());

    public ThongBaoAdapter(Context context,MyOnclick myOnclick) {
        this.context = context;
        this.myOnclick = myOnclick;
    }
    public void setData(List<ThongBao> list){
        this.list = list;
        notifyDataSetChanged();
    }
    public interface MyOnclick{
        void update(ThongBao thongBao);
        void delete(ThongBao thongBao);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_thongbao,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ThongBao thongBao = list.get(position);
        if (thongBao!=null){
            holder.tv_title.setText("Tiêu đề: "+thongBao.getTieude());
            holder.tv_content.setText(thongBao.getNoidung());
            holder.tv_time.setText(thongBao.getThoigian());
            String user = DuAn1DataBase.getInstance(context).adminDAO().getName(thongBao.getUser_id());
            holder.tv_user.setText(user);
            if (position <= 1){
                holder.icnew.setVisibility(View.VISIBLE);
            }else {
                holder.icnew.setVisibility(View.INVISIBLE);
            }
            if (DuAn1DataBase.getInstance(context).adminDAO().checkaccount(thongBao.getUser_id()).get(0).getHinhanh()==null){
                holder.img_user.setImageResource(R.drawable.ic_account);
            }else {
                String linkimg = DuAn1DataBase.getInstance(context).adminDAO().checkaccount(thongBao.getUser_id()).get(0).getHinhanh();
                Log.d("adapter",linkimg+" link");
                holder.img_user.setImageDrawable(Drawable.createFromPath(linkimg));
            }
            holder.layout_update.setOnLongClickListener(v -> {
                myOnclick.delete(thongBao);
                notifyDataSetChanged();
                return true;
            });
            holder.layout_update.setOnClickListener(v -> {
                myOnclick.update(thongBao);
                notifyDataSetChanged();
            });
        }
    }

    @Override
    public int getItemCount() {
        if (list !=null){
            return list.size();
        }
        return 0;
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_id,tv_user,tv_time,tv_title,tv_content;
        private CircleImageView img_user;
        private RelativeLayout layout_update;
        private LottieAnimationView icnew;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_id = itemView.findViewById(R.id.edt_mathongbao_dialogthongbao);
            tv_user = itemView.findViewById(R.id.tv_tenuser_itemthongbao);
            tv_time = itemView.findViewById(R.id.tv_time_itemthongbao);
            tv_title = itemView.findViewById(R.id.tv_title_itemthongbao);
            tv_content = itemView.findViewById(R.id.tv_content_itemthongbao);
            img_user = itemView.findViewById(R.id.avt_itemthongbao);
            layout_update = itemView.findViewById(R.id.layout_update_itemthongbao);
            icnew = itemView.findViewById(R.id.lottie_icnew);
        }
    }
}
