package com.example.nav;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapTwo {

    private Marker marker;
    private GoogleMap map;



    private static MapTwo mapTwo = null;

    private MapTwo() { }
    //dışarıdan sınıfı çağıracak method
    public static synchronized MapTwo getInstance() {
        if (mapTwo == null) {
            mapTwo = new MapTwo();
        }
        return mapTwo;
    }



    public void setMap(GoogleMap map){
        this.map = map;
    }

    public void clearMap(){
        map.clear();
    }

    public Marker addMarker(LatLng destination) {

            MarkerOptions options = new MarkerOptions();
            options.position(destination);
            this.marker = map.addMarker(options);
            options.title("Lat=" + destination.latitude + ", Long=" + destination.longitude);
            map.animateCamera(CameraUpdateFactory.newLatLng(destination));
            this.marker.setDraggable(true);

            return marker;

    }

    public void  setMarker(Marker marker){
        this.marker = marker;

    }


    public Marker getMarker(){
        return marker;
    }
}


