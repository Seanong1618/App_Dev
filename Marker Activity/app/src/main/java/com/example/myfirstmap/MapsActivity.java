package com.example.myfirstmap;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        // Set the location for manila PH
        LatLng Philippines = new LatLng(14.599512,120.984222);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(Philippines));
        // Add a marker secondary school for SEAN
        LatLng Amadeo = new LatLng(16.205639, 120.487541);
        mMap.addMarker(new MarkerOptions()
                .position(Amadeo)
                .title("DON AMADEO PEREZ NATIONAL HIGH SCHOOL")
                .snippet("SEAN's SECONDARY SCHOOL")
        );

        // Add a marker secondary school for KEZY
        LatLng Benigno = new LatLng(16.11442231260515, 120.54655163775092);
        mMap.addMarker(new MarkerOptions()
                .position(Benigno)
                .title("BENIGNO V. ALDANA NATIONAL HIGH SCHOOL")
                .snippet("KEZY's SECONDARY SCHOOL")
        );

        // Add a marker secondary school for ERICA
        LatLng SanIsidro = new LatLng(16.09962264213339, 120.74228891024423);
        mMap.addMarker(new MarkerOptions()
                .position(SanIsidro)
                .title("San Isidro National High School, San Nicolas, Pangasinan")
                .snippet("ERICA's SECONDARY SCHOOL")
        );

    }
}