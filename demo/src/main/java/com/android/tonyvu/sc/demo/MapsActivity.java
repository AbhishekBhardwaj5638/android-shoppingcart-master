package com.android.tonyvu.sc.demo;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {


    private GoogleMap mMap;
    String values[]={"Ghar Ka Khana ,Parkway Forest Drive ", "Ghar Ka Khana ,Location2","Ghar Ka Khana ,Location3","Ghar Ka Khana ,Location4","Ghar Ka Khana ,Location5"};
     AutoCompleteTextView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

        //final EditText storeLocation=(EditText)findViewById(R.id.etStore);

         lv=(AutoCompleteTextView)findViewById(R.id.autoCompleteTextView1);
//adapter will adapt the string and pass the string to lv object
        ArrayAdapter<?> adapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, values);
        lv.setAdapter(adapter);
        



        //getting Intent


        String email=getIntent().getStringExtra("email");
        float tPrice=Float.parseFloat(getIntent().getStringExtra("totalprice"));

        Button checkOut=(Button)findViewById(R.id.buttonCheckOut);

        checkOut.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                String store=lv.getText().toString();
                Toast.makeText(MapsActivity.this, store, Toast.LENGTH_SHORT).show();
                if (store.isEmpty()){
                    showError();
                }
                    else {
                    Intent i=new Intent(MapsActivity.this,Payment.class);
                    i.putExtra("email",getIntent().getStringExtra("email"));
                    i.putExtra("totalprice",getIntent().getStringExtra("totalprice"));
                    i.putExtra("store",store);
                    i.putExtra("guestMail",getIntent().getStringExtra("guestMail"));
                    startActivity(i);}
                    finish();
                }
        });



        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        if (mapFragment == null) {
            Toast.makeText(this, "Sorry could not create map", Toast.LENGTH_SHORT).show();
        }


    }

    private void showError() {
        Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);


                lv.startAnimation(shake);
                lv.setError("Pick up store must be selected");

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
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.setTrafficEnabled(true);
        mMap.setIndoorEnabled(true);
        mMap.setBuildingsEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(43.777365,-79.3406);
        LatLng loc22=new LatLng(43.775748,-79.336674);
        LatLng loc3=new LatLng(43.769240,-79.360010);
        LatLng loc4=new LatLng(43.769290,-79.400101);
        LatLng loc5=new LatLng(43.769552,-79.601201);
        mMap.addMarker(new MarkerOptions().position(sydney).title(values[0]));
        mMap.addMarker(new MarkerOptions().position(loc22).title(values[1]));
        mMap.addMarker(new MarkerOptions().position(loc3).title(values[2]));
        mMap.addMarker(new MarkerOptions().position(loc4).title(values[3]));
        mMap.addMarker(new MarkerOptions().position(loc5).title(values[4]));

        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}
