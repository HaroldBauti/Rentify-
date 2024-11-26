package com.univerisity.rentify;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;
import java.util.Locale;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener, GoogleMap.OnMapLongClickListener {

    private GoogleMap map;
    private String address;
    private double longitude;
    private double latitude;
    private boolean isSelected = false;

    private TextView lbAddress;
    private TextView lbLogitude;
    private TextView lbLatitide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        Intent intent = getIntent();

        lbAddress = findViewById(R.id.txt_address_map);
        lbLogitude = findViewById(R.id.txt_longitude_map);
        lbLatitide = findViewById(R.id.txt_latitude_map);

        if(intent.getStringExtra("address") != null &&
            intent.getDoubleExtra("longitude", 0) != 0 &&
            intent.getDoubleExtra("latitude", 0) != 0){
            address = intent.getStringExtra("address");
            latitude = intent.getDoubleExtra("latitude", 0);
            longitude = intent.getDoubleExtra("longitude", 0);

            lbAddress.setText(address);
            lbLogitude.setText(String.valueOf(longitude));
            lbLatitide.setText(String.valueOf(latitude));
            Button btnSelect = findViewById(R.id.btn_agregar_map);
            btnSelect.setVisibility(View.INVISIBLE);
        }
        else{
            isSelected = true;
        }

        SupportMapFragment mapFragment = (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.fragment_map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        map = googleMap;
        this.map.setOnMapClickListener(this);
        this.map.setOnMapLongClickListener(this);

        if(!isSelected){

            LatLng lugar = new LatLng(latitude, longitude);
            Marker marker = map.addMarker(new MarkerOptions().position(lugar).title(address));
            map.moveCamera(CameraUpdateFactory.newLatLng(lugar));
        }
    }

    @Override
    public void onMapClick(@NonNull LatLng latLng) {
        Marker posicion = map.addMarker(new MarkerOptions().position(latLng).title("Seleccion"));
        map.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        latitude = latLng.latitude;
        longitude = latLng.longitude;
        address = getAddressFromLatLng(latitude, longitude);

        lbAddress.setText(address);
        lbLatitide.setText("Latitud: " + latitude);
        lbLogitude.setText("Longitud: " + longitude);
    }

    @Override
    public void onMapLongClick(@NonNull LatLng latLng) {

    }

    public void btnAgregar_Click(View view){
        Intent intent = new Intent();
        intent.putExtra("address", address);
        intent.putExtra("latitude", latitude);
        intent.putExtra("longitude", longitude);
        setResult(RESULT_OK, intent);
        finish();
    }

    private String getAddressFromLatLng(double latitude, double longitude) {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses != null && !addresses.isEmpty()) {
                Address address = addresses.get(0);
                return address.getAddressLine(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "No se pudo obtener la dirección.";
    }
}