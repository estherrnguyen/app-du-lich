package com.example.tieuluan;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SubActivity extends AppCompatActivity {
    String tenTinh;
    String diaDanh;
    float longitude;
    float latitude;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sub_activity);

        diaDanh = getIntent().getStringExtra("TenDiaDanh");
        tenTinh = getIntent().getStringExtra("TenTinh");

        QueryDataBase(diaDanh);
        Button khamPha = findViewById(R.id.khamPha);
        khamPha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SubActivity.this,MapsActivity.class);
                intent.putExtra("kinhdo",longitude);
                intent.putExtra("vido",latitude);
                startActivity(intent);
            }
        });
    }

    void QueryDataBase(String diaDanh) {
        SQLiteDatabase sql = openOrCreateDatabase("DuLich.db", MODE_PRIVATE, null);
        String query = "SELECT * FROM " + tenTinh + " WHERE diadanh = ?";
        Cursor cursor = sql.rawQuery(query, new String[]{diaDanh});

        if (cursor.moveToFirst()) {
            @SuppressLint("Range") String diaChi = cursor.getString(cursor.getColumnIndex("diachi"));
            @SuppressLint("Range") String hinhAnh = cursor.getString(cursor.getColumnIndex("hinhanh"));
            @SuppressLint("Range") String gioiThieu = cursor.getString(cursor.getColumnIndex("gioithieu"));
            @SuppressLint("Range") float kinhdo = cursor.getFloat(cursor.getColumnIndex("kinhdo"));
            @SuppressLint("Range") float vido = cursor.getFloat(cursor.getColumnIndex("vido"));

            int imageSource = getResources().getIdentifier(hinhAnh, "drawable", getPackageName());

            TextView textViewDiaDanh = findViewById(R.id.diaDanhSub);
            textViewDiaDanh.setText(" Địa danh: " + diaDanh);

            TextView textViewDiaChi = findViewById(R.id.diaChiSub);
            textViewDiaChi.setText("Địa chỉ: " + diaChi);

            TextView textGioiThieu = findViewById(R.id.gioiThieuSub);
            textGioiThieu.setText("Giới thiệu: " + gioiThieu);
            ImageView imageView = findViewById(R.id.hinhAnhSub);
            imageView.setImageResource(imageSource);

            longitude = kinhdo;
            latitude = vido;
        }

        cursor.close();
        sql.close();
    }
}
