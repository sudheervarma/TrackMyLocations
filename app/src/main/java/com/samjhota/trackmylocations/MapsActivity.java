package com.samjhota.trackmylocations;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.Criteria;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private UiSettings uiSettings;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        final SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
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

        uiSettings = mMap.getUiSettings();
        uiSettings.setZoomControlsEnabled(true);
        uiSettings.setCompassEnabled(true);
        uiSettings.setMapToolbarEnabled(true);
        uiSettings.setZoomGesturesEnabled(true);
        uiSettings.setScrollGesturesEnabled(true);
        uiSettings.setTiltGesturesEnabled(true);
        uiSettings.setRotateGesturesEnabled(true);
        uiSettings.setIndoorLevelPickerEnabled(true);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        } else {
            // Show rationale and request permission.
        }

//        // Adding a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(-34, 151);
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
//        mMap.animateCamera(CameraUpdateFactory.zoomTo(10.0f));
//        mMap.addMarker(new MarkerOptions()
//                .title("Sydney")
//                .snippet("Congaroo State")
//                .position(sydney));
//
//        // Adding a marker in California and move the camera
//        LatLng california = new LatLng(37.725977, -121.933576);
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(california));
//        mMap.animateCamera(CameraUpdateFactory.zoomTo(10.0f));
//        mMap.addMarker(new MarkerOptions()
//                .title("California")
//                .snippet("Golden State")
//                .position(california));
//
        // Adding a marker in Florida and move the camera
        LatLng florida = new LatLng(28.054647, -80.624894);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(florida));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(10.0f));
        mMap.addMarker(new MarkerOptions()
                .title("Florida")
                .snippet("Sunshine State")
                .position(florida));

///////----------------------------------Zooming camera to position user-----------------
//
//        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//        Criteria criteria = new Criteria();
//
//        Location location = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, false));
//        if (location != null) {
//            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
//                    new LatLng(location.getLatitude(), location.getLongitude()), 13));
//
//            CameraPosition cameraPosition = new CameraPosition.Builder()
//                    .target(new LatLng(location.getLatitude(), location.getLongitude()))      // Sets the center of the map to location user
//                    .zoom(17)                   // Sets the zoom
//                    .bearing(90)                // Sets the orientation of the camera to east
//                    .tilt(40)                   // Sets the tilt of the camera to 30 degrees
//                    .build();                   // Creates a CameraPosition from the builder
//            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
//
//        }
//
///////----------------------------------Zooming camera to position user-----------------

    }
}
