package com.example.nav;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.*;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    public Button btn_Kayit;
    public Button btn_Login;
    public EditText eMail;
    public EditText pass;
    FirebaseAuth girisYetki;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btn_Login=findViewById(R.id.btn_login);
        btn_Kayit=findViewById(R.id.btn_kayit);
        eMail=findViewById(R.id.email_txt);
        pass=findViewById(R.id.sifre_txt);
        girisYetki = FirebaseAuth.getInstance();

        btn_Kayit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent kayit= new Intent(LoginActivity.this,KayitActivity.class);
                startActivity(kayit);
            }
        });

        btn_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ProgressDialog pdGiris = new ProgressDialog(LoginActivity.this);
                pdGiris.setMessage("Giriş Yapılıyor..");
                pdGiris.show();


                String emailAddr = eMail.getText().toString();
                String pswrd =pass.getText().toString();


                if (TextUtils.isEmpty(emailAddr)||TextUtils.isEmpty(pswrd))
                {
                    Toast.makeText(LoginActivity.this,"Bütün alanları doldurun..",Toast.LENGTH_LONG).show();
                }
                else
                {
                    //giriş yapma kodları
                    girisYetki.signInWithEmailAndPassword(emailAddr,pswrd)
                            .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>()
                            {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task)
                                {
                                    if(task.isSuccessful())
                                    {
                                        DatabaseReference pathGiris = FirebaseDatabase.getInstance().getReference().
                                                child("Kullanicilar").child(girisYetki.getCurrentUser().getUid());
                                        pathGiris.addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                pdGiris.dismiss();

                                                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                                                startActivity(intent);
                                                finish();
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                                pdGiris.dismiss();

                                            }
                                        });

                                    }
                                    else
                                    {
                                        pdGiris.dismiss();
                                        Toast.makeText(LoginActivity.this,"Giriş Başarısız..",Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });
                }


            }
        });

    }
}
