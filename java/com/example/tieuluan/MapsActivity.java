package com.example.tieuluan;

import androidx.fragment.app.FragmentActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.tieuluan.databinding.ActivityMapsBinding;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private LatLng markerLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // Button to switch to Street View
        Button streetViewButton = findViewById(R.id.button_street_view);
        streetViewButton.setOnClickListener(v -> {
            if (markerLocation != null) {
                Intent intent = new Intent(MapsActivity.this, MapsActivity1.class);
                intent.putExtra("LATITUDE", markerLocation.latitude);
                intent.putExtra("LONGITUDE", markerLocation.longitude);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        float longitude = getIntent().getFloatExtra("kinhdo",0);
        float laitude = getIntent().getFloatExtra("vido",0);
        mMap = googleMap;

        // Add a marker and move the camera
        markerLocation = new LatLng(laitude,longitude );
        mMap.addMarker(new MarkerOptions().position(markerLocation).title(""));

        // Move the camera and zoom in
        float zoomLevel = 16.0f; // Bạn có thể điều chỉnh giá trị zoomLevel để zoom gần hơn hoặc xa hơn
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(markerLocation, zoomLevel));

        // Set a listener for map click to update marker location
        mMap.setOnMapClickListener(latLng -> updateMarkerLocation(latLng));
    }

    private void updateMarkerLocation(LatLng newLocation) {
        markerLocation = newLocation;
        mMap.clear(); // Clear existing markers
        mMap.addMarker(new MarkerOptions().position(markerLocation).title(""));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(markerLocation, 16.0f));
    }
}
