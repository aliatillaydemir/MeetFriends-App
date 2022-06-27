

package com.example.meetfriends.other;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.meetfriends.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Map;


public class MapActivity extends AppCompatActivity{

    SupportMapFragment supportMapFragment;
    FusedLocationProviderClient client;

    Button saveLoc, konumBut;

    private FirebaseFirestore firebaseFirestore;


   // private FirebaseDatabase db = FirebaseDatabase.getInstance();
  //  private DatabaseReference rf = db.getReference().child("Lokasyon");

    //ArrayList<Double> liste = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        saveLoc = findViewById(R.id.save_loc);
        konumBut = findViewById(R.id.buttonShow);
        //photoBut = findViewById(R.id.gotophoto);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//geri butonu.
        //manifestten map clasının parentını main clası olarak ayarladık ki geri gittiğimizde maine dönsün.
        // bu method parenta dönmemizi sağlayan bir yapı.

        supportMapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.google_map);


        client = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(MapActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            getCurrentLocation();
        } else {
            ActivityCompat.requestPermissions(MapActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        }


        firebaseFirestore = FirebaseFirestore.getInstance();

    }

    private void getCurrentLocation() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Task<Location> task = client.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {

                if (location != null) {
                    supportMapFragment.getMapAsync(new OnMapReadyCallback() {
                        @Override
                        public void onMapReady(@NonNull GoogleMap googleMap) {

                            GoogleMap mMap = googleMap;

                            LatLng latlng = new LatLng(location.getLatitude()
                                    ,location.getLongitude());

                            MarkerOptions options = new MarkerOptions().position(latlng)
                                    .title("I am here");

                            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latlng,10));

                            googleMap.addMarker(options);

                            saveLoc.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                   // rf.push().setValue(latlng);
                                    //lokasyonu kaydete tıklarsa locasyonu firebase kaydetsin.
                                    //Toast.makeText(MapActivity.this, "location saved", Toast.LENGTH_SHORT).show();
                                    //startActivity(new Intent(getApplicationContext(), UploadActivity.class));

                                    Intent intent = new Intent(getApplicationContext(),UploadActivity.class);
                                    intent.putExtra("Pos", latlng);
                                    startActivity(intent);


                                }
                            });

                            konumBut.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    firebaseFirestore.collection("Bilgiler").addSnapshotListener(new EventListener<QuerySnapshot>() {
                                        @Override
                                        public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                                            if(error != null){
                                                Toast.makeText(MapActivity.this, error.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                                            }

                                            if(value != null){
                                                for(DocumentSnapshot snap: value.getDocuments()){
                                                    Map<String,Object> data = snap.getData();

                                                    Double latt = Double.parseDouble((String)(data.get("Latitude")));
                                                    Double longg = Double.parseDouble((String)(data.get("Longitude")));

                                                   // mMap.addMarker(new MarkerOptions().position(new LatLng(latt,longg))).setIcon(BitmapDescriptorFactory.fromResource(R.drawable.marker));

                                                    if(!(Boolean) data.get("isDone")){
                                                        mMap.addMarker(new MarkerOptions().position(new LatLng(latt,longg))).
                                                                setIcon(BitmapDescriptorFactory.fromResource(R.drawable.peopleicon)); }

                                                    else { mMap.addMarker(new MarkerOptions().position(new LatLng(latt,longg))).
                                                            setIcon(BitmapDescriptorFactory.fromResource(R.drawable.saveflag)); }
                                                }
                                            }

                                        }
                                    });

                                    Toast.makeText(MapActivity.this, "lokasyonlar gösteriliyor...", Toast.LENGTH_SHORT).show();
                                }
                            });




                            // MarkerOptions option = new MarkerOptions().position(new LatLng(40,32))
                            //       .title("I am here");
                            // googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latlng,10));
                            // googleMap.addMarker(option);




                            //Burası itibarıyla markera bastığımızda firestora yüklenen veriler vs. kullanılacak
                            //bizi farklı sayfaya değil de küçük bir popup penceresine yönlendirsin. depo edilen fotoğrafı göstersin
                            //ve sen de foto çek diye seçenek olsun.
                            googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                                @Override
                                public boolean onMarkerClick(@NonNull Marker marker) {
                                    // startActivity(new Intent(getApplicationContext(),MainActivity.class));
                                    return false;
                                }
                            });

                        }
                    });
                }

            }
        });


    }




    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode==44){
            if(grantResults.length > 0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                getCurrentLocation();
            }
        }

    }




}

