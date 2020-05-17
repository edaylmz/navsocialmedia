package com.example.nav;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class DialogFragment {
    private static FirebaseDatabase firebaseDatabase;
    private static DatabaseReference eventveri;
    private List<Event> list_event = new ArrayList<>();


    public Context context;
    static Event e;

    public static Dialog createDialog(final Context context, boolean isNew){
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog);

        final Button Btn_kaydet = (Button) dialog.findViewById(R.id.btn_kaydet);
        Button Btn_kapat = dialog.findViewById(R.id.btn_kapat);
        final EditText eventName = dialog.findViewById(R.id.txt_eventName);
        final EditText eventDetail = dialog.findViewById(R.id.txt_detay);
        final EditText eventDate = dialog.findViewById(R.id.txt_date);
        final EditText eventTime = dialog.findViewById(R.id.txt_saat);

        final FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();


        firebaseDatabase= FirebaseDatabase.getInstance();
        eventveri = firebaseDatabase.getReference("");

        //firebase listener kodları

        if(isNew){
            Btn_kaydet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(!eventName.getText().toString().isEmpty() && !eventDetail.getText().toString().isEmpty()&&
                            !eventDate.getText().toString().isEmpty()&&!eventTime.getText().toString().isEmpty()){
                        //firebase event ekleme kodları
                        LatLng latLng = MapTwo.getInstance().getMarker().getPosition();
                        Location location= new Location((long) latLng.longitude,(long) latLng.latitude);
                        String eventId =UUID.randomUUID().toString();
                        assert currentFirebaseUser != null;
                        Event event = new Event(eventId,
                                eventName.getText().toString(),
                                eventDetail.getText().toString(),eventDate.getText().toString(),
                                eventTime.getText().toString(),location.getLat(),location.getLng(),currentFirebaseUser.getUid());

                        eventveri.child("events").child(eventId).setValue(event);

                        dialog.dismiss();
                        MapTwo.getInstance().clearMap();
                        MapFragment.changeFabIconAdd();



                    }else {
                        Toast.makeText(context, "Gerekli alanları doldurunuz", Toast.LENGTH_LONG).show();
                    }

                }
            });
        }else{
            Btn_kaydet.setText("Güncelle");
            Btn_kaydet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String eventId=e.eventId;
                    if(!eventName.getText().toString().isEmpty() && !eventDetail.getText().toString().isEmpty()&&
                            !eventDate.getText().toString().isEmpty()&&!eventTime.getText().toString().isEmpty()){

                     /*  e.setEvent(eventName.getText().toString());
                        e.setDetail(eventDetail.getText().toString());
                        e.setDate(eventDate.getText().toString());
                        e.setTime(eventTime.getText().toString());
                        eventveri.child("events").child(eventId).updateChildren(eventId,e);*/
                        //firebase event update kodları
                    }

                }
            });



        }


        //Firebase kodları
     //   FirebaseApp.initializeApp();
      /*
        eventveri.child("events").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapShot:dataSnapshot.getChildren()){
                    Event event = postSnapShot.getValue(Event.class);
                    if(currentUser.userId==event.userId){
                       list_event.add(event);
                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });*/

      Btn_kapat.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              dialog.dismiss();
              MapTwo.getInstance().clearMap();
              MapFragment.changeFabIconAdd();


          }
      });

        return dialog;
    }
    public static void fillfields(Dialog dialog, Event event) {

        final EditText t = dialog.findViewById(R.id.txt_eventName);
        t.setText(event.getEvent());

        final EditText m = dialog.findViewById(R.id.txt_detay);
        m.setText(event.getDetail());

        final EditText d = dialog.findViewById(R.id.txt_date);
        d.setText(event.getDate());

        final EditText s= dialog.findViewById(R.id.txt_saat);
        s.setText(event.getTime());



        e = event;


    }
}
