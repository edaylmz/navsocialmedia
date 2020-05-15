package com.example.nav;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends Fragment implements OnMapReadyCallback {

    GoogleMap map;
    private static FloatingActionButton fab;
    static boolean isClicked = false;
    static boolean isDone = false;
    Context context;
    static Marker marker;


    public MapFragment() {
        MapTwo mapTwo = MapTwo.getInstance();
    }





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_map, container, false);
        fab = v.findViewById(R.id.fab);
        fab.setImageResource(R.drawable.ic_add_black_24dp);


        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context = getContext();
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map1);
        mapFragment.getMapAsync(this);
    }


    @Override
    public  void onMapReady(GoogleMap googleMap) {
        MapTwo.getInstance().setMap(googleMap);
        map = googleMap;


        CustomInfoWindowGoogleMap markerInfoWindowAdapter = new CustomInfoWindowGoogleMap(getContext());
        map.setInfoWindowAdapter(markerInfoWindowAdapter);






        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng destination) {
                if (isClicked) {

                    MapTwo.getInstance().clearMap();
                    marker = MapTwo.getInstance().addMarker(destination);






                    map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(destination.latitude, destination.longitude), 2.0f));
                    fab.setImageResource(R.drawable.ic_done_black_24dp);
                    isDone = true;




                }



            }


        });


        fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                isClicked = true;
                if (isDone) {
                    final Dialog dialog = DialogFragment.createDialog(context,true);
                    dialog.show();
                }


            }

        });


        map.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(Marker marker) {
                Log.d("System out", "onMarkerDragStart..."+ marker.getPosition().latitude+"..."+marker.getPosition().longitude);




            }

            @SuppressWarnings("unchecked")
            @Override
            public void onMarkerDrag(final Marker mark) {
                Log.d("System out", "onMarkerDragEnd..."+mark.getPosition().latitude+"..."+mark.getPosition().longitude);
                map.animateCamera(CameraUpdateFactory.newLatLng(mark.getPosition()));

            }

            @Override
            public void onMarkerDragEnd(final Marker marker) {
                Log.i("System out", "onMarkerDrag...");



                AlertDialog.Builder add = new AlertDialog.Builder(context);
                add.setMessage("Are you sure you want to update your location ?");
                add.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {

                        Toast.makeText(getContext(),
                                "You clicked on YES", Toast.LENGTH_SHORT)
                                .show();
                        Event event = Consts.Companion.getEvent();
                        event.setLocation(new Location(marker.getPosition().longitude,marker.getPosition().latitude));
                        //update event kodları

                    }
                })


                        .setNegativeButton("no", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                Toast.makeText(getContext(),
                                        "You clicked on NO", Toast.LENGTH_SHORT)
                                        .show();



                            }
                        });
                add.show();



            }
        });



    }


    public static void changeFabIconAdd() {
        fab.setImageResource(R.drawable.ic_add_black_24dp);

        isClicked = false;

        isDone = false;



    }

    public static void changeFabIconDone() {
        fab.setImageResource(R.drawable.ic_done_black_24dp);
        MapTwo.getInstance().clearMap();

    }

}