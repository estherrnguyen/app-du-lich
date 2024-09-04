package com.example.tieuluan;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.appcompat.app.AppCompatActivity;

public class MapsActivity1 extends AppCompatActivity {

    private WebView webView;
    private double latitude;
    private double longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps1);

        webView = findViewById(R.id.webview);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());

        // Nhận tọa độ từ Intent
        latitude = getIntent().getDoubleExtra("LATITUDE", 0);
        longitude = getIntent().getDoubleExtra("LONGITUDE", 0);

        // Tạo URL cho Street View với tọa độ đã nhận
        String url = "https://www.google.com/maps/embed/v1/streetview?key=AIzaSyB-jdw1BfhJjoKbnzo_YAXBwmN5PXsUnxY&location=" + latitude + "," + longitude;

        // Tạo trang HTML động để tải URL này
        String html = "<!DOCTYPE html>" +
                "<html lang=\"en\">" +
                "<head>" +
                "    <meta charset=\"UTF-8\">" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">" +
                "    <title>Street View</title>" +
                "    <style>" +
                "        html, body {" +
                "            height: 100%;" +
                "            margin: 0;" +
                "            padding: 0;" +
                "        }" +
                "        #street-view-iframe {" +
                "            position: absolute;" +
                "            top: 0;" +
                "            left: 0;" +
                "            width: 100%;" +
                "            height: 100%;" +
                "            border: 0;" +
                "        }" +
                "    </style>" +
                "</head>" +
                "<body>" +
                "    <iframe" +
                "        id=\"street-view-iframe\"" +
                "        src=\"" + url + "\"" +
                "        allowfullscreen>" +
                "    </iframe>" +
                "</body>" +
                "</html>";

        // Tải trang HTML vào WebView
        webView.loadDataWithBaseURL(null, html, "text/html", "UTF-8", null);
    }
}
