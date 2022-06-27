package com.example.meetfriends.other;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.meetfriends.R;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

public class UploadActivity extends AppCompatActivity {

    Uri imageData; //her yerde bu uri kullanmak içni global bir değişken
    ActivityResultLauncher<Intent> activityResultLauncher; //bu da galeri intenti için
    ActivityResultLauncher<String> permissionLauncher; // bu izin istemek için

    ImageView image;
    EditText textDesc;
    TextView textViewTarih;
    Button uploadBut,btnTarih;
    LatLng latlng;

    public String tarih;
    private  Calendar calendar ;
    private String adres;
    private int yil ;
    private int ay;
    private int gun ;


    private FirebaseStorage firebaseStorage;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);



        registerLauncher();

        image = findViewById(R.id.imagePhoto);
        textDesc = findViewById(R.id.textDesc);
        uploadBut = findViewById(R.id.uploadButton);

        uploadBut.setEnabled(true); // YENİ EKLENEN

        //yeni
        btnTarih = findViewById(R.id.btnTarih);
        textViewTarih = findViewById(R.id.textViewTarih);

        // yenniiiiii
        // konumu alma

        latlng = new LatLng(0,0);
        Intent i =getIntent();
        latlng = getIntent().getExtras().getParcelable("Pos");





        calendar = Calendar.getInstance();
        yil = calendar.get(Calendar.YEAR);
        ay = calendar.get(Calendar.MONTH)+1;
        gun = calendar.get(Calendar.DATE);
        tarih = gun + " "+  ay +" " + yil ;
        textViewTarih.setText(tarih);

        btnTarih.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                handleDateButton();
            }
        });

        //yeni




        firebaseStorage = FirebaseStorage.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        storageReference = firebaseStorage.getReference();

        //resme tıklandığında resim...
        imageFun();

        //uploada tıklandığında veriler firestore'a kaydedilecek
        uploadFun();







    }



    private void handleDateButton(){

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                if(month+1<10)
                    tarih = dayOfMonth + " "+  0+(month+1) +" " + year ;
                else
                    tarih = dayOfMonth + " "+  (month+1) +" " + year ;

                textViewTarih.setText(tarih);
            }
        }, yil, ay-1, gun);
        datePickerDialog.show();
    }




    private void uploadFun() {

        uploadBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!textDesc.getText().toString().isEmpty())
                    uploadBut.setEnabled(false); // yeniiiiiiiiiiiiiiiii save butonunu devre dısı bırakır


                if(imageData != null){ //kullanıcı resmi seçti mi

                    UUID uuid = UUID.randomUUID();
                    String imageName = "image/" + uuid + ".jpg";

                    storageReference.child(imageName).putFile(imageData).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            StorageReference newRef = firebaseStorage.getReference(imageName);
                            newRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {

                                    String url = uri.toString();

                                    String aciklama = textDesc.getText().toString();

                                    if(textDesc.getText().toString().isEmpty())
                                    {
                                        textDesc.setError("this cannot be empty");
                                        Toast.makeText(UploadActivity.this, "please check the description", Toast.LENGTH_SHORT).show();
                                        return;
                                    }

                                    FirebaseUser user = firebaseAuth.getCurrentUser();
                                    String email = user.getEmail();




                                    double lat = latlng.latitude;    //konum için double bu.
                                    double lng = latlng.longitude;

                                    String lt = Double.toString(lat);  //bunlar veri tabanı için. string olmalı
                                    String ln = Double.toString(lng);  //bunlar veri tabanı için stirng.

                                    String adres = getAddress(lat,lng);

                                    HashMap<String,Object> data = new HashMap<>();

                                    data.put("Url",url);
                                    data.put("Adres",adres);
                                    //   data.put("Alan",alan);
                                    data.put("Aciklama", aciklama);
                                    data.put("Date", FieldValue.serverTimestamp());
                                    data.put("Mail",email);
                                    data.put("kurucuId",user.getUid());
                                    data.put("eventDate",tarih);
                                    data.put("isDone",false);
                                    data.put("Latitude",lt);
                                    data.put("Longitude",ln);
                                    String uniqueID = UUID.randomUUID().toString();
                                    data.put("EtkinlikID",uniqueID);

                                    FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
                                    CollectionReference usersRef = rootRef.collection("Bilgiler");
                                    usersRef.document(uniqueID).set(data).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {

                                            Toast.makeText(UploadActivity.this,"Etkinlik Oluşturuldu",Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(UploadActivity.this, MainActivity.class);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                            startActivity(intent);
                                            finish();

                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(UploadActivity.this,e.getLocalizedMessage(),Toast.LENGTH_SHORT).show();

                                        }
                                    });

/*
                                    firebaseFirestore.collection("Bilgiler").add(data).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                        @Override
                                        public void onSuccess(DocumentReference documentReference) {
                                            Toast.makeText(UploadActivity.this,"Etkinlik Oluşturuldu",Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(UploadActivity.this, MainActivity.class);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                            startActivity(intent);
                                            finish();

                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(UploadActivity.this,e.getLocalizedMessage(),Toast.LENGTH_SHORT).show();

                                        }
                                    });
*/
                                    ///////////////////////////////////////////////////
                                }
                            });

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(UploadActivity.this,e.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else{
                    Toast.makeText(UploadActivity.this,"Resim Ekleyiniz",Toast.LENGTH_SHORT).show(); // yeniiiiiiiii
                    uploadBut.setEnabled(true); // yeniiiiiiiiiiiiiiiii save butonunu
                }

            }
        });

    }




    private void imageFun() {

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(ContextCompat.checkSelfPermission(UploadActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){

                    if(ActivityCompat.shouldShowRequestPermissionRationale(UploadActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)){
                        Snackbar.make(view,"need permission",Snackbar.LENGTH_INDEFINITE).setAction("Give permission", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE);

                            }
                        }).show();
                    } else{
                        //yine izin iste
                        permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE);

                    }
                } else{
                    Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    activityResultLauncher.launch(gallery);
                }

            }
        });

    }

    private void registerLauncher(){ //activityregisterlauncher için method
        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if(result.getResultCode() == Activity.RESULT_OK){
                    Intent intentFromResult = result.getData();
                    if(intentFromResult != null){
                        imageData = intentFromResult.getData(); //bu uri'ı isteyecek firabase
                        image.setImageURI(imageData);
                    }
                }
            }
        });


        permissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), new ActivityResultCallback<Boolean>() {
            @Override
            public void onActivityResult(Boolean result) {

                if(result){

                    Intent gallery = new  Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    activityResultLauncher.launch(gallery);


                }else{
                    Toast.makeText(UploadActivity.this,"need Permission", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }

//pop up'ta adres gözüksün diye
    public String getAddress(double lat, double lng) {



        Geocoder geocoder = new Geocoder(UploadActivity.this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(lat, lng, 1);
            Address obj = addresses.get(0);
            String add = obj.getAddressLine(0);
            return add;


        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        return "Adres Bulunamadı";
    }



}