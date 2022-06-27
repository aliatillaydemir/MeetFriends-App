package com.example.meetfriends.settings;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.meetfriends.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;

public class SettingsActivity extends AppCompatActivity {

    Button verifyBut;
    TextView verifyText;
    Switch s1;
    SharedPreferences screen_mode; //dark modu işaretlediğimizi tutmak için

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        auth = FirebaseAuth.getInstance();

        verifyBut = findViewById(R.id.verifyButton);
        verifyText = findViewById(R.id.verifyText);
        s1 = findViewById(R.id.switch1);
        screen_mode = this.getSharedPreferences("com.example.trashtracking.settings", Context.MODE_PRIVATE);



        verifyFun(); // e mail onaylamak için metod

        darkMode();   //dark modu açmak için metod



    }


    private void darkMode() {

        Boolean mode = screen_mode.getBoolean("screen_mode",false);
        if(mode){
            s1.setChecked(true);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);}

        else{
            s1.setChecked(false);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }




        s1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){

                    // dark mod on
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    screen_mode.edit().putBoolean("screen_mode",true).apply();

                }
                else
                {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    screen_mode.edit().putBoolean("screen_mode",false).apply();

                }
            }
        });



    }



    private void verifyFun() {

        if(!auth.getCurrentUser().isEmailVerified())
        {
            verifyBut.setVisibility(View.VISIBLE);
            verifyText.setVisibility(View.VISIBLE);
        }else{
            verifyBut.setVisibility(View.INVISIBLE);
            verifyText.setVisibility(View.INVISIBLE);
        }

        verifyBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.getCurrentUser().sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(SettingsActivity.this, "Verification Email Sent", Toast.LENGTH_LONG).show();

                        verifyBut.setVisibility(View.GONE);
                        verifyText.setVisibility(View.GONE);

                    }
                });
            }
        });


    }






}