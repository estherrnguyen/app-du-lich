package com.example.tieuluan;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class GetDataBase extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_database);
        EditText editText = findViewById(R.id.editDatabase);
        Button button = findViewById(R.id.addDatabase);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dbName = editText.getText().toString().trim();
                if(!dbName.isEmpty())
                {
                    importDatabase(dbName);
                }else
                {
                    Toast.makeText(GetDataBase.this,"Chưa điền tên database",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    private void importDatabase(String dbName) {
        AssetManager assetManager = getAssets();
        String dbPath = getApplicationInfo().dataDir + "/databases/" + dbName;
        File dbFile = new File(dbPath);

        // Tạo thư mục đích nếu chưa tồn tại
        File dbDir = new File(dbFile.getParent());
        if (!dbDir.exists()) {
            dbDir.mkdirs();
        }

        if (!dbFile.exists()) {
            try {
                InputStream inputStream = assetManager.open(dbName);
                OutputStream outputStream = new FileOutputStream(dbPath);

                byte[] buffer = new byte[1024];
                int length;
                while ((length = inputStream.read(buffer)) > 0) {
                    outputStream.write(buffer, 0, length);
                }

                outputStream.flush();
                outputStream.close();
                inputStream.close();
                Toast.makeText(this, "Database đã được thêm vào", Toast.LENGTH_SHORT).show();
            } catch (IOException e) {

                Toast.makeText(this, "Database không thể thêm vào", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Database này đã tồn tại", Toast.LENGTH_SHORT).show();
        }
    }
    }

