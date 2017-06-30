package com.eduaraujodev.a28scj_trabalho_cloud.activity;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.eduaraujodev.a28scj_trabalho_cloud.R;
import com.eduaraujodev.a28scj_trabalho_cloud.domain.Locale;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private GoogleMap map;
    private Locale locale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        locale = getIntent().getParcelableExtra("locale");

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        LatLng location = new LatLng(locale.lat, locale.log);
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(location, 13);
        map.moveCamera(update);

        map.addMarker(new MarkerOptions()
                .title(String.valueOf(locale.lat) + " - " + String.valueOf(locale.log))
                .snippet(String.valueOf(locale.lat) + " - " + String.valueOf(locale.log))
                .position(location));
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
    }
}
