package de.info3.navigation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.OnLifecycleEvent;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;


import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mapbox.android.core.location.LocationEngine;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.annotations.PolylineOptions;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.location.LocationComponent;
import com.mapbox.mapboxsdk.location.modes.CameraMode;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;


import com.mapbox.mapboxsdk.maps.Style;
//import com.mapbox.mapboxsdk.plugins.locationlayer.LocationLayerPlugin;
import com.nabinbhandari.android.permissions.PermissionHandler;
import com.nabinbhandari.android.permissions.Permissions;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class MainActivityTrackingApp extends AppCompatActivity  {

    private MapView mapView;
    double lat = 49.014362;
    double lon = 8.404128;
    public static final String EXTRA_MESSAGE = " ";


    private MapboxMap map;
    //private LocationEngine locationEngine;
    //private LocationLayerPlugin locationLayerPlugin;
    //private Location originLocation;
    private LocationComponent locationComponent;
    private LocationManager locationManager;
    private android.location.LocationListener locationListener;
    private final List trackingPositions=new ArrayList();
    private DB_Helper database;

    private String modi="Fuß";
    private String time;
    private String distance;
    private String location;
    private String calender;
    private double timeTrackingStarted;


    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Intent intent=getIntent();
        Mapbox.getInstance(this, getString(R.string.access_token));
        setContentView(R.layout.activity_tracking_main);




        Permissions.check(this, Manifest.permission.ACCESS_FINE_LOCATION, null, new PermissionHandler() {
            @Override
            public void onGranted() {
                Toast.makeText(MainActivityTrackingApp.this, "Permission Granted", Toast.LENGTH_SHORT).show();
            }
        });

        database = new DB_Helper(MainActivityTrackingApp.this);



        Toolbar toolbar = findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        mapView = (MapView) findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);

        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull MapboxMap mapboxMap) {

                map = mapboxMap;

                mapboxMap.getUiSettings().setCompassEnabled(true);
                mapboxMap.getUiSettings().setAllGesturesEnabled(true);

                mapboxMap.setCameraPosition(new CameraPosition.Builder()
                        .zoom(12)
                        .target(new LatLng(lat, lon))
                        .tilt(9)
                        .build());

                mapboxMap.addMarker(new MarkerOptions()
                        .position(new LatLng(lat, lon))
                        .title("Koordinaten: " + lat + " ; " + lon));

                mapboxMap.setStyle(Style.MAPBOX_STREETS, new Style.OnStyleLoaded() {
                    @Override
                    public void onStyleLoaded(@NonNull Style style) {

                    }
                });
            }
        });


        ImageButton imageButton = this.findViewById(R.id.target_button);
        ImageButton imageButton2 = this.findViewById(R.id.track_button);
        ImageButton homeButton =this.findViewById(R.id.home_button);

        imageButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent2 = new Intent(MainActivityTrackingApp.this, Tracks_Activity.class);

                startActivity(intent2);
            }
        });

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                enableLocationComponent(map.getStyle());
                locationComponent.zoomWhileTracking(15);

            }
        });

        imageButton.setOnLongClickListener(new View.OnLongClickListener() {
            @SuppressLint("MissingPermission")
            @Override
            public boolean onLongClick(View v) {

                enableLocationComponent(map.getStyle());
                locationComponent.zoomWhileTracking(12);

                return true;
            }
        });

        homeButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent tent=new Intent(MainActivityTrackingApp.this, MainActivity.class);
                startActivity(tent);
            }
        });

        FloatingActionButton floatingActionButtonLeft  = this.findViewById(R.id.fableft);
        FloatingActionButton floatingActionButtonRight = this.findViewById(R.id.fabright);


        Bundle name = getIntent().getExtras();
        if (name != null){

            String mode = name.getString("name");
            modi=mode;


            if (mode.equals("Fuß")) {
                floatingActionButtonLeft.setImageResource(R.drawable.ic_feet);
            } else if (mode.equals("Fahrrad")) {
                floatingActionButtonLeft.setImageResource(R.drawable.ic_bike);
            } else if (mode.equals("ÖPNV")) {
                floatingActionButtonLeft.setImageResource(R.drawable.ic_opnv);
            } else if (mode.equals("E-Scooter")) {
                floatingActionButtonLeft.setImageResource(R.drawable.ic_escooter);
            } else if (mode.equals("MIV-Fahrer")) {
                floatingActionButtonLeft.setImageResource(R.drawable.ic_miv_fahrer);
            } else if (mode.equals("MIV-Mitfahrer")) {
                floatingActionButtonLeft.setImageResource(R.drawable.ic_mitfahrer);
            } else if (mode.equals("Sonstiges")) {
                floatingActionButtonLeft.setImageResource(R.drawable.ic_sonstiges);
            }


        }


        final boolean[] useModeButton = {true};

        floatingActionButtonLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (useModeButton[0]) {

                    Intent intent = new Intent(MainActivityTrackingApp.this, SelectModeActivity.class);
                    startActivity(intent);

                }

            }
        });

        final boolean[] trackingStarted = {false};

        floatingActionButtonRight.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("MissingPermission")
            @Override
            public void onClick(View v) {
                if(!trackingStarted[0]) {

                    floatingActionButtonRight.setImageResource(R.drawable.ic_stopp);
                    Toast.makeText(MainActivityTrackingApp.this, "Tracking started!", Toast.LENGTH_SHORT).show();
                    timeTrackingStarted=System.currentTimeMillis();
                    trackingStarted[0] = true;
                    useModeButton[0] = false;



                    locationListener = new android.location.LocationListener() {
                        @Override
                        public void onLocationChanged(@NonNull Location location) {
                            LatLng currentPosition = new LatLng(location.getLatitude(), location.getLongitude());
                            trackingPositions.add(currentPosition);
                        }
                    };

                    locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5, (android.location.LocationListener) locationListener);

                }

                else if(trackingStarted[0]){

                    floatingActionButtonRight.setImageResource(R.drawable.ic_start);
                    Toast.makeText(MainActivityTrackingApp.this, "Tracking finished", Toast.LENGTH_SHORT).show();
                    trackingStarted[0] = false;
                    useModeButton [0] = true;

                    calender =  (new SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date()));
                    int timeInMs= (int) (System.currentTimeMillis()-timeTrackingStarted);
                    time = Integer.toString(timeInMs/1000)+" s";


                    locationManager.removeUpdates((android.location.LocationListener) locationListener);
                    drawPolyLine();
                    addData(modi, time, distance, calender, location);

                }
            }

        });


    }

    public void addData(String modi, String time, String distance, String calender, String location){
        database.addData(modi, time, distance, calender, location);

    }

    @SuppressLint("MissingPermission")
    private void enableLocationComponent(Style loadedMapStyle) {
        locationComponent = map.getLocationComponent();
        locationComponent.activateLocationComponent(this,loadedMapStyle);
        locationComponent.setLocationComponentEnabled(true);
        locationComponent.setCameraMode(CameraMode.TRACKING);
    }

    private void drawPolyLine(){
        LatLng[] latLng=new LatLng[trackingPositions.size()];
        String locations="";
        for(int i=0;i< trackingPositions.size();i++){
            latLng[i]=(LatLng) trackingPositions.get(i);
            String locationComponent=Double.toString(latLng[i].getLatitude())+";"+Double.toString(latLng[i].getLongitude());
            locations=locations+locationComponent+"~";
        }
        this.location=locations;

        map.addPolyline(new PolylineOptions().add(latLng).color(Color.parseColor("black")).width(5));
        double distance=0;

        for(int j=0;j<trackingPositions.size()-1;j++){
            LatLng l1=new LatLng();
            LatLng l2=new LatLng();
            l1=(LatLng) trackingPositions.get(j);
            l2=(LatLng) trackingPositions.get(j+1);
            distance=distance+l1.distanceTo(l2);
        }

        String string=Integer.toString((int) distance)+" m";

        this.distance=string;

    }


    public void onResume(){
       super.onResume();
       mapView.onResume();

    }
    public void onStart(){
        super.onStart();
        mapView.onStart();
    }
    public void onStop(){
        super.onStop();
        mapView.onStop();
    }
    public void onDestroy(){
        super.onDestroy();
        mapView.onDestroy();
    }
    public void onSaveInstanceState (Bundle outState){
        super.onSaveInstanceState( outState);
        mapView.onSaveInstanceState( outState);
    }
    public void onLowMemory (){
        super.onLowMemory();
        mapView.onLowMemory();
    }

}