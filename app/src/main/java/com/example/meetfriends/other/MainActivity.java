package com.example.meetfriends.other;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.example.meetfriends.etkinlikFragmentlar.EtkinlikMain;
import com.example.meetfriends.R;
import com.example.meetfriends.info.infoTabLayoutActivity;
import com.example.meetfriends.loginRegister.LoginActivity;
import com.example.meetfriends.loginRegister.ResetPassActivity;
import com.example.meetfriends.profile.ProfileActivity;
import com.example.meetfriends.settings.SettingsActivity;
import com.example.meetfriends.weather.WeatherActivity;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private InterstitialAd mInterstitialAd;  //reklamlar için, ayarlara giderken geçişli reklam çıkacak.

    ImageView profilIm;
    ImageView trekkingIm;
    ImageView mapIm;
    ImageView weatherIm;
    ImageView infoIm;
    ImageView settingsIm;

    FirebaseAuth auth;
    AlertDialog.Builder reset;
    LayoutInflater inflater;

    SharedPreferences screen_mode; //dark modu işaretlediğimizi tutmak için


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auth = FirebaseAuth.getInstance();

        reset = new AlertDialog.Builder(this);
        inflater = this.getLayoutInflater();

        mapIm = findViewById(R.id.harita_image);
        profilIm = findViewById(R.id.profil_image);
        trekkingIm = findViewById(R.id.etkinlik_image);
        weatherIm = findViewById(R.id.hava_image);
        infoIm = findViewById(R.id.bilgi_image);
        settingsIm = findViewById(R.id.ayarlar_image);

//dark mod için
        screen_mode = this.getSharedPreferences("com.example.trashtracking.settings", Context.MODE_PRIVATE);

        Boolean mode = screen_mode.getBoolean("screen_mode",true);


        if(mode){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            screen_mode.edit().putBoolean("screen_mode",true).apply();
        }
        else{ AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            screen_mode.edit().putBoolean("screen_mode",false).apply();
            }

/////////////dark mod



        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {}
        });



        mapIm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MapActivity.class));
                //finish(); //bunu dersen geri tuşuna bastığında uygulamadan çıkar. bitirme islemidir.
            }
        });


        profilIm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ProfileActivity.class));

            }
        });


        trekkingIm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), EtkinlikMain.class));
            }
        });


        weatherIm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), WeatherActivity.class));

            }
        });


        //burası şimdilik info'da, sonra etkinlik ile birleştirilecek.
        infoIm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                  //startActivity(new Intent(getApplicationContext(), FotoActivity.class));
                //startActivity(new Intent(getApplicationContext(), UploadActivity.class));
                startActivity(new Intent(getApplicationContext(), infoTabLayoutActivity.class));

            }
        });
        // foto sınıfına atıyoruz kendimizi.



        setAds(); //settingse gitmeden önce reklam için çağırıyoruz.
        settingsIm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(mInterstitialAd != null){
                    mInterstitialAd.show(MainActivity.this);
                    mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                        @Override
                        public void onAdDismissedFullScreenContent() {
                            super.onAdDismissedFullScreenContent();
                            startActivity(new Intent(MainActivity.this, SettingsActivity.class));  //settingse git kodu, bloktaki diğer şeyler Interstitial reklamlar için.
                            setAds(); //bunu tekrar çağırıyoruz çünkü aksi takdirde settingse gitmiyor.
                        }
                    });

                }else{

                    startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
                }

            }
        });





//onCreate sonu.
    }



    //Buradan aşağısı menü için. şifremi, emailimi değiştir, log out yap gibi...
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option,menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == R.id.resetPass)
        {
        startActivity(new Intent(getApplicationContext(), ResetPassActivity.class));
        }


        if(item.getItemId() == R.id.updateEmail)
        {

            View view = inflater.inflate(R.layout.reset_popup,null);


            reset.setTitle("Update Email")
                    .setMessage("Enter ur new email")
                    .setPositiveButton("Update", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            EditText email = view.findViewById(R.id.reset_email);
                            if(email.getText().toString().isEmpty())
                            {
                                email.setError("email Required");
                                return;
                            }

                            FirebaseUser user = auth.getCurrentUser();
                            user.updateEmail(email.getText().toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {

                                    Toast.makeText(MainActivity.this, "Email Updated", Toast.LENGTH_SHORT).show();
                                    
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                    
                                }
                            });

                        }
                    }).setNegativeButton("Cancel",null)
                    .setView(view)
                    .create().show();

        }

        if(item.getItemId()==R.id.delete)
        {

            reset.setTitle("Delete this account")
                    .setMessage("Are you sure that?")
                    .setPositiveButton("Okey", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            FirebaseUser user = auth.getCurrentUser();
                            user.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {

                                    Toast.makeText(MainActivity.this, "Account Deleted", Toast.LENGTH_SHORT).show();
                                    auth.signOut();
                                    startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                                    finish();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });

                        }
                    }).setNegativeButton("Cancel",null)
                    .create().show();

        }

        if(item.getItemId()==R.id.logOut){

            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            finish();

        }

        return super.onOptionsItemSelected(item);
    }

    //reklamlarrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr
    //reklamlar için, settigse gidince çağıracak
    public void setAds(){

        AdRequest adRequest = new AdRequest.Builder().build();

        InterstitialAd.load(this,"ca-app-pub-3940256099942544/1033173712", adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        // The mInterstitialAd reference will be null until
                        // an ad is loaded.
                        mInterstitialAd = interstitialAd;

                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error
                        mInterstitialAd = null;
                    }
                });

    }
//////////////////////////////////////////////////////////////////////////////////

//MainActivity, yani sınıfın sonu.
}