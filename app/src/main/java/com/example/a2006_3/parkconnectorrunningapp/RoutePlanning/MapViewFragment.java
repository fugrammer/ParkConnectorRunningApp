package com.example.a2006_3.parkconnectorrunningapp.RoutePlanning;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.a2006_3.parkconnectorrunningapp.Commons.Coordinate;
import com.example.a2006_3.parkconnectorrunningapp.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.RoundCap;

import java.util.ArrayList;

public class MapViewFragment extends Fragment{
    MapView mMapView;
    private GoogleMap googleMap;
    ArrayList<Polyline> polylines;
    private int colors[] = {Color.BLACK,Color.BLUE,Color.YELLOW, Color.GREEN, Color.MAGENTA, Color.DKGRAY};
    private int routeSelected = 0;

    public interface PolylineClickListener {
        void onClick(int id);
    }
    private PolylineClickListener polylineClickListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.polylineClickListener = (PolylineClickListener) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_map_view, container, false);
        polylines = new ArrayList<>();
        mMapView = (MapView) rootView.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);

        mMapView.onResume(); // needed to get the map to display immediately
        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;
                // For showing a move to my location button
                if (checkPermission()) {
                    googleMap.setMyLocationEnabled(true);
                    // For dropping a marker at a point on the Map
//                    LatLng sydney = new LatLng(-34, 151);
//                    googleMap.addMarker(new MarkerOptions().position(sydney).title("Marker Title").snippet("Marker Description"));
                    // For zooming automatically to the location of the marker
//                    CameraPosition cameraPosition = new CameraPosition.Builder().target(sydney).zoom(12).build();
//                    googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                    googleMap.setOnPolylineClickListener(new GoogleMap.OnPolylineClickListener() {
                        @Override
                        public void onPolylineClick(Polyline polyline) {
                            Log.e("Line clicked ",String.valueOf(polyline.getTag()));
                            polylineClickListener.onClick((int)polyline.getTag());
                            for (Polyline polyline1:polylines){
                                if ((int)polyline1.getTag()==(int)polyline.getTag()){
                                    polyline1.setColor(Color.BLUE);
                                    polyline1.setZIndex(1);
                                }
                                else{
                                    polyline1.setZIndex(0.5f);
                                    polyline1.setColor(Color.DKGRAY);
                                }
                            }
                        }
                    });
                    try {
                        polylineClickListener.onClick(-1);
                    } catch (Exception e){
                        Log.e("ERROR",e.toString());
                    }
                }
            }
        });


        return rootView;
    }

    public void clearRoutes(){
        for (Polyline polyline : polylines){
            polyline.remove();
        }
        polylines.clear();
    }

    public void clearRoutesBut(int id){
        for (Polyline polyline : polylines){
            if ((int) polyline.getTag() != id) {
                polyline.remove();
            }
        }
    }

    public void zoomIn(Location location){
        LatLng start = new LatLng(location.getLatitude(),location.getLongitude());
        CameraPosition cameraPosition = new CameraPosition.Builder().target(start).zoom(15).build();
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    public void displayRoute(ArrayList<Coordinate> coordinates, int id){
        routeSelected = id;
        try {
            PolylineOptions rectOptions = new PolylineOptions();
            LatLng last = new LatLng(-34, 151);
            for (Coordinate coordinate : coordinates) {
                float lat = coordinate.lat;
                float lng = coordinate.lng;
                rectOptions.add(new LatLng(lat, lng));
                last = new LatLng(lat, lng);
            }

            Polyline polyline = googleMap.addPolyline(rectOptions);
            polyline.setClickable(true);
            if (id == 0) {
                polyline.setColor(Color.BLUE);
                polyline.setZIndex(1);
            } else {
                polyline.setColor(Color.DKGRAY);
                polyline.setZIndex(0.5f);
            }
            polyline.setTag(id);
            polyline.setWidth(10);
            polyline.setStartCap(new RoundCap());
            polylines.add(polyline);
            // For zooming automatically to the location of the marker
            CameraPosition cameraPosition = new CameraPosition.Builder().target(last).zoom(12).build();
            googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        }catch (Exception e){
            Log.e("MAP ERROR",e.toString());
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    private boolean checkPermission() {
        // Ask for permission if it wasn't granted yet
        return (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED );
    }


}
