package com.example.duan1_baove.adapter;

import android.app.DatePickerDialog;
import android.app.Dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1_baove.R;
import com.example.duan1_baove.database.DuAn1DataBase;
import com.example.duan1_baove.model.ThietBi;
import com.google.android.material.textfield.TextInputLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ThietBiAdapter extends RecyclerView.Adapter<ThietBiAdapter.ViewHolder> implements Filterable {

    private Context context;
    private List<ThietBi> list;
    private List<ThietBi> listOld;

    EditText edt_id,edt_name,edt_loai,edt_soluong,edt_hangsanxuat,edt_ngaymua,edt_baotrigannhat;
    TextInputLayout txt_ngaymua,txt_ngaybaotri;
    Button btn_chonanh,btn_add,btn_huy;
    Date datenow = new Date();
    Date datebtri;
    Calendar datebaotri = Calendar.getInstance();
    int yearbtri,monthbtri,daybtri;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat sdfvn = new SimpleDateFormat("dd-MM-yyyy");
    Calendar lich = Calendar.getInstance();
    long songay;
    int chiphi;


    public ThietBiAdapter(Context context) {
        this.context = context;
    }
    public void setData(List<ThietBi> list){
        this.list = list;
        this.listOld = list;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(context).inflate(R.layout.item_thietbi,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ThietBi thietBi = list.get(position);
        if (thietBi!=null){
            Date datetest= new Date();
            Log.v("datetest",sdfvn.format(datetest));
            holder.tv_id.setText("Mã thiết bị: "+thietBi.getId());
            holder.tv_name.setText("Tên thiết bị: "+thietBi.getName());
            holder.tv_loai.setText("Loại: "+thietBi.getLoai());
            holder.tv_soluong.setText("Số lượng: "+thietBi.getSoLuong());
            holder.tv_hangsanxuat.setText("Hãng sản xuất: "+thietBi.getHangSanXuat());
            holder.tv_ngaymua.setText("Ngày mua: "+thietBi.getThoigianmua());
            holder.tv_ngaybaotrigannhat.setText("Ngày bảo trì gần nhất: "+thietBi.getThoigianbaotrigannhat());

            datebaotri.set(Calendar.DAY_OF_MONTH,getArrayDate(thietBi.getThoigianbaotrigannhat())[0]);
            datebaotri.set(Calendar.MONTH,getArrayDate(thietBi.getThoigianbaotrigannhat())[1]);
            datebaotri.set(Calendar.YEAR,getArrayDate(thietBi.getThoigianbaotrigannhat())[2]);

            yearbtri = datebaotri.get(Calendar.YEAR);
            monthbtri = datebaotri.get(Calendar.MONTH);
            daybtri = datebaotri.get(Calendar.DAY_OF_MONTH);

            try {
                datebtri = sdf.parse(yearbtri+"-"+monthbtri+"-"+daybtri);
                songay = datenow.getTime()-datebtri.getTime();
                if (TimeUnit.DAYS.convert(songay, TimeUnit.MILLISECONDS)>=95){
                    holder.tv_tinhtrang.setTextColor(Color.RED);
                    holder.tv_tinhtrang.setText("Tình trạng: Cần được bảo trì");
                    holder.btn_baotri.setText("Bảo trì");
                    holder.btn_baotri.setBackground(context.getDrawable(R.drawable.bg_green));
                    holder.btn_baotri.setEnabled(true);
                }else if (TimeUnit.DAYS.convert(songay, TimeUnit.MILLISECONDS)>=80){
                    holder.tv_tinhtrang.setTextColor(Color.YELLOW);
                    holder.tv_tinhtrang.setText("Tình trạng: Nên bảo trì");
                    holder.btn_baotri.setText("Bảo trì");
                    holder.btn_baotri.setBackground(context.getDrawable(R.drawable.bg_green));
                    holder.btn_baotri.setEnabled(true);
                }else{
                    holder.tv_tinhtrang.setTextColor(Color.GREEN);
                    holder.tv_tinhtrang.setText("Tình trạng: Tốt");
                    holder.btn_baotri.setText("Đã bảo trì");
                    holder.btn_baotri.setBackground(context.getDrawable(R.drawable.bg_gray));
                    holder.btn_baotri.setEnabled(false);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }

            if (thietBi.getHinhanh()==null){
                holder.img_thietbi.setImageResource(R.drawable.ic_account);
            }else {
                String linkimg = thietBi.getHinhanh();
                Log.d("adapter",linkimg+" link");
                holder.img_thietbi.setImageDrawable(Drawable.createFromPath(linkimg));
            }
            holder.btn_baotri.setOnClickListener(v -> {
                EditText editText = new EditText(context);
                new android.app.AlertDialog.Builder(context).setTitle("Nhập chi phí bảo trì: ")
                        .setView(editText)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                try {
                                    chiphi = Integer.parseInt(editText.getText().toString().trim());
                                }catch (Exception e){
                                    e.printStackTrace();
                                    Toast.makeText(context, "Vui lòng nhập đúng số tiền", Toast.LENGTH_SHORT).show();
                                }
                                thietBi.setTongchiphibaotri(DuAn1DataBase.getInstance(context).thietBiDAO().getTongSoTienBaoTri(String.valueOf(thietBi.getId()))+chiphi);
                                Date datetest= new Date();
                                thietBi.setThoigianbaotrigannhat(sdfvn.format(datetest));
                                DuAn1DataBase.getInstance(context).thietBiDAO().update(thietBi);
                                notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("No",null)
                        .show();
            });
            holder.layout_update.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    new AlertDialog.Builder(context).setTitle("Xoá thiết bị ?")
                            .setMessage("Bạn có chắc chắn muốn xoá thiết bị ?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    DuAn1DataBase.getInstance(context).thietBiDAO().delete(thietBi);
                                    list.remove(thietBi);
                                    notifyDataSetChanged();
                                }
                            })
                            .setNegativeButton("No",null)
                            .show();
                    return true;
                }
            });
            holder.layout_update.setOnClickListener(v -> {
                Dialog dialog = new Dialog(context);
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
                btn_add = dialog.findViewById(R.id.btn_luu_dialogthietbi);
                btn_huy = dialog.findViewById(R.id.btn_huy_dialogthietbi);
                txt_ngaymua = dialog.findViewById(R.id.txti_ngaymua_dialogthietbi);
                txt_ngaybaotri =dialog.findViewById(R.id.txti_ngaybaotrigannhat_dialogthietbi);
                edt_baotrigannhat = dialog.findViewById(R.id.edt_ngaybaotrigannhat_dialogthietbi);
                edt_ngaymua = dialog.findViewById(R.id.edt_ngaymua_dialogthietbi);



                edt_id.setText(thietBi.getId()+"");
                edt_name.setText(thietBi.getName());
                edt_loai.setText(thietBi.getLoai());
                edt_soluong.setText(thietBi.getSoLuong()+"");
                edt_hangsanxuat.setText(thietBi.getHangSanXuat());
                edt_baotrigannhat.setText(thietBi.getThoigianbaotrigannhat());
                edt_ngaymua.setText(thietBi.getThoigianmua());

                txt_ngaymua.setStartIconOnClickListener(v1 -> {
                    int year = lich.get(Calendar.YEAR);
                    int month = lich.get(Calendar.MONTH)+1;
                    int day = lich.get(Calendar.DAY_OF_MONTH);
                    new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            edt_ngaymua.setText(dayOfMonth+"-"+(month+1)+"-"+year);
                        }
                    },year,month,day).show();
                });
                txt_ngaybaotri.setStartIconOnClickListener(v1 -> {
                    int year = lich.get(Calendar.YEAR);
                    int month = lich.get(Calendar.MONTH)+1;
                    int day = lich.get(Calendar.DAY_OF_MONTH);
                    new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            edt_baotrigannhat.setText(dayOfMonth+"-"+(month+1)+"-"+year);
                        }
                    },year,month,day).show();
                });

                if (thietBi.getHinhanh()==null){
                    holder.img_thietbi.setImageResource(R.drawable.ic_account);
                }else {
                    String linkimg = thietBi.getHinhanh();
                    Log.d("adapter",linkimg+" link");
                    holder.img_thietbi.setImageDrawable(Drawable.createFromPath(linkimg));
                }
                btn_huy.setOnClickListener(v1-> {
                    dialog.cancel();
                });
                btn_add.setOnClickListener(v1 -> {
                    if (validate()){
                        thietBi.setName(edt_name.getText().toString().trim());
                        thietBi.setHangSanXuat(edt_hangsanxuat.getText().toString().trim());
                        thietBi.setLoai(edt_loai.getText().toString().trim());
                        thietBi.setSoLuong(Integer.parseInt(edt_soluong.getText().toString().trim()));
                        thietBi.setThoigianmua(edt_ngaymua.getText().toString().trim());
                        thietBi.setThoigianbaotrigannhat(edt_baotrigannhat.getText().toString().trim());
                        DuAn1DataBase.getInstance(context).thietBiDAO().update(thietBi);
                        Toast.makeText(context, "Update thiết bị thành công", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
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
                    List<ThietBi> listnew = new ArrayList<>();
                    for (ThietBi thietBi:listOld){
                        if (thietBi.getName().toLowerCase().contains(strSearch.toLowerCase())){
                            listnew.add(thietBi);
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
                list = (List<ThietBi>) results.values;
                notifyDataSetChanged();
            }
        };
    }
    private boolean validate(){
        if (edt_loai.getText().toString().trim().isEmpty() || edt_name.getText().toString().trim().isEmpty() || edt_soluong.getText().toString().trim().isEmpty() || edt_hangsanxuat.getText().toString().trim().isEmpty()){
            Toast.makeText(context, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return false;
        }else {
            try {
                Integer.parseInt(edt_soluong.getText().toString().trim());
                return true;
            }catch (Exception e){
                Toast.makeText(context, "Số lượng không hợp lệ", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_id,tv_name,tv_loai,tv_soluong,tv_hangsanxuat,tv_tinhtrang,tv_ngaymua,tv_ngaybaotrigannhat;
        private ImageView img_thietbi;
        private RelativeLayout layout_update;
        private Button btn_baotri;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_id = itemView.findViewById(R.id.tv_id_itemthietbi);
            tv_name = itemView.findViewById(R.id.tv_name_itemthietbi);
            tv_loai = itemView.findViewById(R.id.tv_loai_itemthietbi);
            tv_soluong = itemView.findViewById(R.id.tv_soluong_itemthietbi);
            tv_hangsanxuat = itemView.findViewById(R.id.tv_hangsanxuat_itemthietbi);
            tv_tinhtrang = itemView.findViewById(R.id.tv_tinhtrang_itemthietbi);
            img_thietbi = itemView.findViewById(R.id.avt_itemthietbi);
            layout_update = itemView.findViewById(R.id.layout_update_itemthietbi);
            tv_ngaybaotrigannhat = itemView.findViewById(R.id.tv_ngaybaotrigannhat_itemthietbi);
            tv_ngaymua = itemView.findViewById(R.id.tv_ngaymua_itemthietbi);
            btn_baotri = itemView.findViewById(R.id.btn_baotri_itemthietbi);
        }
    }
    public int[] getArrayDate(String date){
        String[] str = date.split("-");
        int arr[] = new int[str.length];
        try{
            for(int i = 0;i<str.length;i++){
                arr[i] = Integer.parseInt(str[i]);
            }
        }catch (NumberFormatException e){
            return null;
        }
        return arr;
    }
}
