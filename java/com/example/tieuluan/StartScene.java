package com.example.tieuluan;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;

public class StartScene extends AppCompatActivity {
    private static final int SPLASH_SCREEN_DELAY = 3000; // Thời gian hiển thị Splash Screen (3 giây)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_scene);

        // Chuyển đến MainActivity sau khoảng thời gian định sẵn
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(StartScene.this, MainActivity.class);
                startActivity(intent);
                finish(); // Đóng SplashActivity để người dùng không thể quay lại
            }
        }, SPLASH_SCREEN_DELAY);
    }
}
