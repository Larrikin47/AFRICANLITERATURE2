package com.example.africanliteraturelibraryapp.uicontrols;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.example.africanliteraturelibraryapp.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapViewActivity extends AppCompatActivity implements OnMapReadyCallback {

    private MapView mapView;
    private GoogleMap googleMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_view);

        mapView = findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState); // Important for MapView lifecycle
        mapView.getMapAsync(this); // Get the map asynchronously
    }

    @Override
    public void onMapReady(GoogleMap map) {
        googleMap = map;

        // Example: Mark a location (e.g., Nairobi, Kenya)
        LatLng nairobi = new LatLng(-1.286389, 36.817223);
        googleMap.addMarker(new MarkerOptions().position(nairobi).title("Nairobi, Kenya"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(nairobi, 10)); // Zoom level 10
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart(); // Important for MapView lifecycle
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume(); // Important for MapView lifecycle
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause(); // Important for MapView lifecycle
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop(); // Important for MapView lifecycle
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy(); // Important for MapView lifecycle
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory(); // Important for MapView lifecycle
    }
}
