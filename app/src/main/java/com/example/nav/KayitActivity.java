package com.example.nav;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
//import android.text.Editable;
//import android.text.TextUtils;
import android.view.View;
import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.OnFailureListener;
//import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import android.widget.*;

import java.util.HashMap;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;

public class KayitActivity extends AppCompatActivity {
    public Button btn_Kayit;
    public EditText username;
    public EditText password;
    public EditText confirm;
    public EditText email;
    public TextView giris;
    FirebaseAuth firebaseAuth;
    DatabaseReference path;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kayit);
        username=findViewById(R.id.username_kayit);
        password=findViewById(R.id.sifre_kayit);
        email=findViewById(R.id.email_kayit);
        confirm=findViewById(R.id.confirm_kayit);
        btn_Kayit=(Button) findViewById(R.id.button_kayit);
        giris=findViewById(R.id.giris_git);
        firebaseAuth=FirebaseAuth.getInstance();





        btn_Kayit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pd= new ProgressDialog(KayitActivity.this);
                pd.setMessage("Lütfen bekleyin..");
                pd.show();

                String emailAddr = email.getText().toString();
                String u_name=username.getText().toString();
                String pass = password.getText().toString();


                if (username.length() == 0) {
                    username.setError("bu alan boş olamaz");
                } else if (email.length() == 0) {
                    email.setError("bu alan boş olamaz");
                } else if (password.length() == 0) {
                    password.setError("bu alan boş olamaz");
                } else if (confirm.length() == 0) {
                    confirm.setError("bu alan boş olamaz");

                } else if (!password.getText().toString().equals(confirm.getText().toString())){
                    confirm.setError("şifre aynı değil");

                }

                else {
                    //yeni kullanici kaydet kodlarını çağır
                    kaydet(u_name,emailAddr,pass);

                }
            }
        });



        giris.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent girisgit = new Intent(KayitActivity.this,LoginActivity.class);
                startActivity(girisgit);
            }
        });

    }
    private void kaydet(final String kullaniciAdi, final String emailAd, String sifre){
        //yeni kullanıcı kaydet kodları
        firebaseAuth.createUserWithEmailAndPassword(emailAd,sifre)
                .addOnCompleteListener(KayitActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()){

                            FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

                            String kullaniciId =firebaseAuth.getUid();

                            path = FirebaseDatabase.getInstance().getReference().child("Kullanicilar").child(kullaniciId);

                            HashMap<String,Object> hashMap = new HashMap<>();

                            hashMap.put("id",kullaniciId);
                            hashMap.put("kullaniciAdi",kullaniciAdi.toLowerCase());


                            path.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        pd.dismiss();
                                        Intent i = new Intent(KayitActivity.this,MainActivity.class);
                                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(i);

                                    }

                                }
                            });
                        }
                        else{
                            pd.dismiss();
                            Toast.makeText(KayitActivity.this,"Kayıt Başarısız..",Toast.LENGTH_LONG).show();
                        }

                    }
                });

    }
}
