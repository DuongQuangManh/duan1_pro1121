package com.example.duan1_baove.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.duan1_baove.R;
import com.example.duan1_baove.model.LoaiTheTap;

import java.util.List;

public class SpinnerAdapterLoaiTheTap extends ArrayAdapter<LoaiTheTap> {
    private Context context;
    private int resource;
    private List<LoaiTheTap> list;
    private LayoutInflater inflater;
    public SpinnerAdapterLoaiTheTap(@NonNull Context context, int resource, @NonNull List<LoaiTheTap> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource =resource;
        this.list = objects;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null){
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(resource,null);
            viewHolder.tv_name = convertView.findViewById(R.id.tv_name_spinnernaptien);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        LoaiTheTap loaiTheTap = list.get(position);
        viewHolder.tv_name.setText(loaiTheTap.getName());
        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null){
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(resource,null);
            viewHolder.tv_name = convertView.findViewById(R.id.tv_name_spinnernaptien);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        LoaiTheTap loaiTheTap = list.get(position);
        viewHolder.tv_name.setText(loaiTheTap.getName());
        return convertView;
    }

    public class ViewHolder{
        private TextView tv_name;
    }
}
