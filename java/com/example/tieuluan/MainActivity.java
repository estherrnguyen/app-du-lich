package com.example.tieuluan;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;
import android.os.Looper;

import com.example.tieuluan.ArrayApdater.AdapterTenTinh;
import com.example.tieuluan.Item.TinhPB;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    String[] listDataBase = {"tphochiminh", "vungtau", "binhdinh", "camau", "cantho", "dalat", "danang", "dienbien", "gialai", "hanoi", "hue", "laocai", "nhatrang", "ninhbinh", "phanthiet", "phuquoc", "phuyen", "phutho", "quangninh", "sonla"};
    String[] listName = {"TP.Hồ Chí Minh", "Vũng Tàu", "TP.Bình Định", "TP.Cà Mau", "TP.Cần Thơ", "TP.Đà Lạt", "TP.Đà Nẵng", "TP.Điện Biên", "Tỉnh Gia Lai", "TP.Hà Nội", "TP.Huế", "TP.Lào Cai", "Nha Trang", "TP.Ninh Bình", "TP.Phan Thiết", "Đảo Phú Quốc", "Thị xã Phú Thọ", "Tỉnh Phú Yên", "Tỉnh Quảng Ninh", "Tp.Sơn La"};
    int[] image = {R.drawable.hn_hotay, R.drawable.hcm_dinhdoclap, R.drawable.ct_ninhkieu, R.drawable.dn_banahill, R.drawable.db_caonguyendatuachua, R.drawable.gl_hotnung, R.drawable.h_songhuong, R.drawable.lc_fansipan, R.drawable.nb_dongamtien, R.drawable.pt_quanthelocvung, R.drawable.qn_vinhhalong, R.drawable.sl_taxua, R.drawable.vt_tuongchua, R.drawable.py_diepson, R.drawable.pq_nhaga, R.drawable.pt_vanthuytu, R.drawable.nt_honmun, R.drawable.bd_tiensa, R.drawable.cm_khailong, R.drawable.dl_datanla};
    String[] tinhPhoBien = {"TP.Hồ Chí Minh", "TP.Hà Nội", "TP.Cần Thơ", "TP.Đà Nẵng"};
    Handler handler;
    ArrayList<TinhPB> myList;
    int index = 0;
    int indexTinh = 0;
    AutoCompleteTextView tenTinhAuto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SetUp();
        MoveImage();
        GetData();
    }

    void MoveImage() {
        ImageView hinhAnhDong = findViewById(R.id.hinhanhMove);
        handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                hinhAnhDong.setImageResource(image[index]);
                index++;
                if (index >= image.length) {
                    index = 0;
                }
                handler.postDelayed(this, 2000);
            }
        }, 2000);
    }
    // Phương thức để ẩn bàn phím ảo
    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(tenTinhAuto.getWindowToken(), 0);
    }


    void SetUp() {
        tenTinhAuto = findViewById(R.id.NhapTenTinh);

        //
        ArrayAdapter<String> tenPB = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, tinhPhoBien);
        ArrayAdapter<String> tenTinhAdapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, listName);
        //

        tenTinhAuto.setAdapter(tenTinhAdapter);
        //
        tenTinhAuto.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String tenTinhList = (String) parent.getItemAtPosition(position);
                TextView tenTinh = findViewById(R.id.tenTinh);
                tenTinh.setText(tenTinhList);
                tenTinhAuto.setText("");
                hideKeyboard();
                for(int i=0; i< listName.length;i++)
                {
                    if(listName[i].equals(tenTinhList))
                    {
                        indexTinh = i;
                        break;
                    }
                }
                GetData();
            }
        });
        tenTinhAuto.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE ||
                        actionId == EditorInfo.IME_ACTION_SEARCH ||
                        (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN)) {
                    String tenTinhList = tenTinhAuto.getText().toString();
                    TextView tenTinh = findViewById(R.id.tenTinh);
                    tenTinh.setText(tenTinhList);
                    tenTinhAuto.setText("");
                    hideKeyboard();
                    for(int i=0; i< listName.length;i++)
                    {
                        if(listName[i].equals(tenTinhList))
                        {
                            indexTinh = i;
                            break;
                        }
                    }
                    GetData();
                    return true;
                }
                return false;
            }
        });
    }

    void GetData() {
        SQLiteDatabase sql = openOrCreateDatabase("DuLich.db", MODE_PRIVATE, null);
        myList = new ArrayList<>();
        Cursor cursor = sql.query(listDataBase[indexTinh], null, null, null, null, null, null);

        int indexTenDiaDanh = cursor.getColumnIndex("diadanh");
        int indexDiaChi = cursor.getColumnIndex("diachi");
        int indexHinhAnhDiaDanh = cursor.getColumnIndex("hinhanh");

        if (indexTenDiaDanh != -1 && indexDiaChi != -1 && indexHinhAnhDiaDanh != -1) {
            if (cursor.moveToFirst()) {
                do {
                    String tenDiaDanh = cursor.getString(indexTenDiaDanh);
                    String diaChiDiaDanh = cursor.getString(indexDiaChi);
                    String imageDiaDanh = cursor.getString(indexHinhAnhDiaDanh);
                    int imageSource = getResources().getIdentifier(imageDiaDanh, "drawable", getPackageName());
                    myList.add(new TinhPB(tenDiaDanh, diaChiDiaDanh, imageSource));
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        sql.close();
        AdapterTenTinh adapterTenTinh = new AdapterTenTinh(MainActivity.this, R.layout.custom_list_tentinh, myList);
        ListView listView = findViewById(R.id.listMainDiaDanh);
        listView.setAdapter(adapterTenTinh);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TinhPB tinhPB = (TinhPB) parent.getItemAtPosition(position);
                String name = tinhPB.getTenDiaDanh();
                Intent intent = new Intent(MainActivity.this,SubActivity.class);
                intent.putExtra("TenDiaDanh",name);
                intent.putExtra("TenTinh",listDataBase[indexTinh]);
                startActivity(intent);
            }
        });
    }
}