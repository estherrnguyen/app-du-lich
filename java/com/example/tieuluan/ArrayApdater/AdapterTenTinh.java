package com.example.tieuluan.ArrayApdater;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.tieuluan.Item.TinhPB;
import com.example.tieuluan.R;

import java.util.ArrayList;

public class AdapterTenTinh extends ArrayAdapter<TinhPB> {
    Activity content;
    int IdLayout;
    ArrayList<TinhPB> mylist;
    public AdapterTenTinh(Activity content, int idLayout, ArrayList<TinhPB> mylist) {
        super(content,idLayout,mylist);
        this.content = content;
        IdLayout = idLayout;
        this.mylist = mylist;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(content);
        convertView = layoutInflater.inflate(IdLayout, parent, false);

        TinhPB tinhPB = mylist.get(position);

        ImageView imageView = convertView.findViewById(R.id.listImage);
        // Áp dụng bo tròn vào hình ảnh sử dụng Glide và RequestOptions
        RequestOptions requestOptions = new RequestOptions().transform(new RoundedCorners(30));
        Glide.with(content)
                .load(tinhPB.getImage())
                .apply(requestOptions)
                .into(imageView);

        TextView tenDiaDanh = convertView.findViewById(R.id.listTenDiaDanh);
        tenDiaDanh.setText(tinhPB.getTenDiaDanh());

        TextView diaChiDiaDanh = convertView.findViewById(R.id.listDiaChi);
        diaChiDiaDanh.setText(tinhPB.getDiaChiDiaDanh());

        return convertView;
    }
}