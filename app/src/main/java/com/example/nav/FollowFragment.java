package com.example.nav;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.appbar.AppBarLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class FollowFragment extends Fragment {

    private RecyclerView recyclerView;
    private KullaniciAdapter kullaniciAdapter;
    private List<Kullanici> mKullanicilar;

    Button btn_kapat;
    EditText arama_bar;


    public FollowFragment() {
    }



    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.activity_follow,container,false);

        recyclerView = view.findViewById(R.id.recycler_view_arama);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        arama_bar = view.findViewById(R.id.edit_arama_bar);

        mKullanicilar= new ArrayList<>();
        kullaniciAdapter= new KullaniciAdapter(getContext(),mKullanicilar);



        recyclerView.setAdapter(kullaniciAdapter);
        kullanilariOku();
        arama_bar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                kullaniciAra(s.toString().toLowerCase());

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return view;

    }

    private void kullaniciAra(String s){
        Query sorgu = FirebaseDatabase.getInstance().getReference("Kullanicilar").orderByChild("kullaniciAdi")
                .startAt(s)
                .endAt(s+"\uf8ff");
        sorgu.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Kullanici kullanici = snapshot.getValue(Kullanici.class);
                    mKullanicilar.add(kullanici);
                }
                kullaniciAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void kullanilariOku(){
        DatabaseReference kullanicilarYol=FirebaseDatabase.getInstance().getReference("Kullanicilar");

        kullanicilarYol.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(arama_bar.getText().toString().equals("")){
                    mKullanicilar.clear();
                    for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                        Kullanici kullanici = snapshot.getValue(Kullanici.class);
                        mKullanicilar.add(kullanici);

                    }
                    kullaniciAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
