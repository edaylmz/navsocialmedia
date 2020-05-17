package com.example.nav;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

public class KullaniciAdapter extends RecyclerView.Adapter<KullaniciAdapter.ViewHolder> {

    private Context mContext;
    private List<Kullanici> mKullanicilar;
    private FirebaseUser firebaseKullanici;

    public KullaniciAdapter(Context mContext, List<Kullanici> mKullanicilar) {
        this.mContext = mContext;
        this.mKullanicilar = mKullanicilar;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.kullanici_ogesi,parent,false);

        return new KullaniciAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        firebaseKullanici= FirebaseAuth.getInstance().getCurrentUser();

        final Kullanici kullanici = mKullanicilar.get(position);

        holder.takipEt.setVisibility(View.VISIBLE);
        holder.kullaniciAdi.setText(kullanici.getKullaniciAdi());
        takipEdiliyor(kullanici.getId(),holder.takipEt);

        if(kullanici.getId().equals(firebaseKullanici.getUid())){
            holder.takipEt.setVisibility(View.GONE);
        }
      /*  holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor= mContext.getSharedPreferences("PREFS",Context.MODE_PRIVATE).edit();
                editor.putString("profileId",kullanici.getId());
                editor.apply();

                ((FragmentActivity)mContext).getSupportFragmentManager().beginTransaction().replace(ma,
                        new )
            }
        });*/
      holder.takipEt.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              if(holder.takipEt.getText().equals("Takip Et")){
                    FirebaseDatabase.getInstance().getReference().child("Takip")
                            .child(firebaseKullanici.getUid()).child("takipEdilenler").child(kullanici.getId()).setValue(true);

                    FirebaseDatabase.getInstance().getReference().child("Takip")
                            .child(kullanici.getId()).child("Takipciler").child(firebaseKullanici.getUid()).setValue(true);
              }
              else
              {
                  FirebaseDatabase.getInstance().getReference().child("Takip")
                          .child(firebaseKullanici.getUid()).child("takipEdilenler").child(kullanici.getId()).removeValue();

                  FirebaseDatabase.getInstance().getReference().child("Takip")
                          .child(kullanici.getId()).child("Takipciler").child(firebaseKullanici.getUid()).removeValue();
              }
          }
      });

    }

    @Override
    public int getItemCount() {
        return mKullanicilar.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView kullaniciAdi;
        public Button takipEt;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            kullaniciAdi=itemView.findViewById(R.id.txt_kullaniciadi_oge);
            takipEt=itemView.findViewById(R.id.btn_takipet_oge);
        }
    }

    private void takipEdiliyor(final String kullaniciId, final Button button){
        DatabaseReference takipyolu= FirebaseDatabase.getInstance().getReference().child("Takip")
                .child(firebaseKullanici.getUid()).child("takipEdilenler");

        takipyolu.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(kullaniciId).exists())
                {
                    button.setText("Takip Ediliyor");
                }
                else
                {
                    button.setText("Takip Et");

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
