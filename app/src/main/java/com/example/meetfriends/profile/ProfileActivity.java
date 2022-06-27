package com.example.meetfriends.profile;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.meetfriends.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

public class ProfileActivity extends AppCompatActivity {

    private AdView mAdView;

    TextView textname;
    TextView texthome;
    TextView textbirth;
    TextView textgender;
    TextView textphone;
    TextView textmail;
    ImageView ppImage;
    Button editbt;
    ImageView saveIcon;
    Uri selected;
    private FirebaseDatabase firebaseDb;
    private DatabaseReference dbRef;
    private StorageReference storageReference; //storage'a depolamak için
    private FirebaseAuth mAuth;

    private FirebaseFirestore firebaseFirestore;
    private String userId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        ///////// reklamlaarrrrrrrrr
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    ///////////////////////////////


        textname = findViewById(R.id.textname);
        texthome = findViewById(R.id.textLokasyon);
        textbirth = findViewById(R.id.textbirthdey);
        textgender = findViewById(R.id.textcinsiyet);
        textphone = findViewById(R.id.texttelefon);
        textmail = findViewById(R.id.textmailimiz);

        ppImage = findViewById(R.id.profile_imageView);
        editbt = findViewById(R.id.editButton);
        saveIcon = findViewById(R.id.profileSaveBut);

        firebaseDb = FirebaseDatabase.getInstance();
        dbRef = firebaseDb.getReference();
        storageReference = FirebaseStorage.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        userId = mAuth.getCurrentUser().getUid();



        DocumentReference documentReference = firebaseFirestore.collection("Profile").document(userId);
  /*
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        texthome.setText(document.getString("Location"));
                        textbirth.setText(document.getString("Birth"));
                        textgender.setText(document.getString("Name"));
                        textphone.setText(document.getString("Phone"));
                        textmail.setText(document.getString("Mail"));

                    } else {

                      //  Log.d(TAG, "No such document");
                    }
                } else {
                    //Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
*/


        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                texthome.setText(value.getString("Location"));
                textbirth.setText(value.getString("Birth"));
                textgender.setText(value.getString("Name"));
                textphone.setText(value.getString("Phone"));
                textmail.setText(value.getString("Mail"));
            }
        });





/*
        firebaseFirestore.collection("Profile").whereEqualTo("Userid",userId).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {


                if(error != null){
                    Toast.makeText(getApplicationContext(), error.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                }

                if(value != null){
                    for(DocumentSnapshot snap: value.getDocuments()){
                        Map<String,Object> data = snap.getData();

                        texthome.setText((String)data.get("Location"));
                        textbirth.setText((String)data.get("Birth"));
                        textgender.setText((String)data.get("Name"));
                        textphone.setText((String)data.get("Phone"));
                        textmail.setText((String)data.get("Mail"));

                    }
               // document("SF").set(data);
                }

            }
        });

*/





        editbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),EditProfile.class));
            }
        });


        saveIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final UUID uuid = UUID.randomUUID(); //değiştirilemez, eşsiz, rastgele sayı
                String imageName = "pp/"+uuid+".jpg";
                StorageReference ref = storageReference.child(imageName);

                ref.putFile(selected).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        StorageReference profRef = FirebaseStorage.getInstance().getReference("pp/"+uuid+".jpg");
                        profRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {

                                //String edittext ... diğerlerini de burada tanımlayabiliriz.

                                String downloadUrl = uri.toString();

                                UUID uuid1 = UUID.randomUUID();
                                String uuidStr = uuid1.toString();

                                FirebaseUser user = mAuth.getCurrentUser();
                                String userMail = user.getEmail();

                                dbRef.child("Profile").child(uuidStr).child("userImageUrl").setValue(downloadUrl);
                                dbRef.child("Profile").child(uuidStr).child("userEmail").setValue(userMail);

                                Toast.makeText(getApplicationContext(),"Uploaded",Toast.LENGTH_SHORT).show();

                            }
                        });

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(),e.getLocalizedMessage().toString(),Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });


        ppImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){ //izin yoksa

                    ActivityCompat.requestPermissions(ProfileActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);

                }else{  //galeriye gitmek için izin varsa

                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent,2);

                }


            }
        });


        getData();
//onCreate sonu
    }


    public void getData() {

        DatabaseReference newRef = firebaseDb.getReference("Profile");
        newRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot ds : snapshot.getChildren()){

                    HashMap<String,String> hashMap = (HashMap<String,String>) ds.getValue();

                    String userName = hashMap.get("userEmail");

                    if(userName.matches(mAuth.getCurrentUser().getEmail().toString())){

                        String userIm = hashMap.get("userImageUrl");

                        if(userIm != null){
                            Picasso.get().load(userIm).into(ppImage);
                        }


                    }
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==1){
            if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent,2);
            }
        }


        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if(requestCode == 2 && resultCode==RESULT_OK && data !=null){

            selected = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(),selected);
                ppImage.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }




}

