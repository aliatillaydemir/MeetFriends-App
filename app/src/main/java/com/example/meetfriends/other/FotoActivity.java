package com.example.meetfriends.other;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.meetfriends.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FotoActivity extends AppCompatActivity {

    public static final int CAMERA_REQUEST_CODE = 102;
    public static final int CAMERA_PERM_CODE = 101;
    public static final int GALLERY_REQUEST_CODE = 105;


    ImageView Image;
    Button cameraBtn, galleryBtn;
    String currentPhotoPath;
    EditText editText1, editText2;
    Button uploadbut;


    StorageReference storageReference;
    private FirebaseAuth auth;
    private FirebaseFirestore firebaseFirestore;

    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

    ActivityResultLauncher<Intent> startForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {


        @Override
        public void onActivityResult(ActivityResult result) {

            if (result != null && result.getResultCode() == RESULT_OK) {
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    // Create the File where the photo should go
                    File photoFile = null;
                    try {
                        photoFile = createImageFile();
                    } catch (IOException ex) {
                        // Error occurred while creating the File

                    }
                    // Continue only if the File was successfully created
                    if (photoFile != null) {
                        Uri photoURI = FileProvider.getUriForFile(getApplicationContext(),
                                "com.example.android.fileprovider",
                                photoFile);
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                        //startActivityForResult(takePictureIntent, CAMERA_REQUEST_CODE);
                        startForResult.launch(takePictureIntent);
                    }
                }

            }

            if(result.getResultCode() == CAMERA_REQUEST_CODE){
                if(result.getResultCode() == Activity.RESULT_OK){
                    File f = new File(currentPhotoPath);
                    // Image.setImageURI(Uri.fromFile(f)); //picasso kullanarak firebaseden cektigimiz icin buraya ihtiyacımız kalmadı.
                    Log.d("tag", "Url of the image ->"+Uri.fromFile(f));

                    Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                    Uri contentUri = Uri.fromFile(f);
                    mediaScanIntent.setData(contentUri);
                    sendBroadcast(mediaScanIntent);

                    uploadImageToFirebase(f.getName(),contentUri);

                }
            }

            if(result.getResultCode() == GALLERY_REQUEST_CODE){
                if(result.getResultCode() == Activity.RESULT_OK){
                    // Uri contentUri = data.getData();
                    Uri contentUri = result.getData().getData();
                    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                    String imageFileName = "JPEG_" + timeStamp + "."+getFileExt(contentUri);
                    Log.d("tag", "Gallery image Uri ->"+imageFileName);
                    // Image.setImageURI(contentUri); //picasso kullanarak firebaseden cektigimiz icin buraya ihtiyacımız kalmadı. burası tel galerisinden cekiyor.

                    uploadImageToFirebase(imageFileName,contentUri);

                }
            }
        }

    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foto);

        Image = findViewById(R.id.imagething);
        cameraBtn = findViewById(R.id.CameraButton);
        galleryBtn = findViewById(R.id.GalleryButton);
        editText1 = findViewById(R.id.descText1);
        editText2 = findViewById(R.id.descText2);
        uploadbut = findViewById(R.id.uploadbut);


        storageReference = FirebaseStorage.getInstance().getReference();
        auth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        cameraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CameraFunc();

            }
        });

        /*
        galleryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(gallery, GALLERY_REQUEST_CODE);
            }
        });
*/




    }

    private void CameraFunc() {

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERM_CODE);

        }else{
            startForResult.launch(takePictureIntent);
        }

    }


/*
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == CAMERA_REQUEST_CODE){
          if(resultCode == Activity.RESULT_OK){
              File f = new File(currentPhotoPath);
             // Image.setImageURI(Uri.fromFile(f)); //picasso kullanarak firebaseden cektigimiz icin buraya ihtiyacımız kalmadı.
              Log.d("tag", "Url of the image ->"+Uri.fromFile(f));

              Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
              Uri contentUri = Uri.fromFile(f);
              mediaScanIntent.setData(contentUri);
              this.sendBroadcast(mediaScanIntent);

              uploadImageToFirebase(f.getName(),contentUri);

          }
        }

        if(requestCode == GALLERY_REQUEST_CODE){
            if(resultCode == Activity.RESULT_OK){
                Uri contentUri = data.getData();
                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                String imageFileName = "JPEG_" + timeStamp + "."+getFileExt(contentUri);
                Log.d("tag", "Gallery image Uri ->"+imageFileName);
               // Image.setImageURI(contentUri); //picasso kullanarak firebaseden cektigimiz icin buraya ihtiyacımız kalmadı. burası tel galerisinden cekiyor.

                uploadImageToFirebase(imageFileName,contentUri);

            }
        }


    }

    */

    private void uploadImageToFirebase(String name, Uri contentUri) {

        StorageReference image = storageReference.child("images/"+name); //images/image.jpg
        image.putFile(contentUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                image.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {

                        Picasso.get().load(uri).into(Image);

                        //firestore'a veri kaydetme




                    }
                });
                Toast.makeText(FotoActivity.this, "image is uploaded.", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Toast.makeText(FotoActivity.this, "upload failed", Toast.LENGTH_SHORT).show();

            }
        });

    }

    private String getFileExt(Uri contentUri) {

        ContentResolver cont = getContentResolver();
        MimeTypeMap mim = MimeTypeMap.getSingleton();
        return mim.getExtensionFromMimeType(cont.getType(contentUri));
    }


    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        //File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File storageDir=Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);//resim galeriye kaydedilecek!

        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }


    /*
        private void dispatchTakePictureIntent() {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            // Ensure that there's a camera activity to handle the intent
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                // Create the File where the photo should go
                File photoFile = null;
                try {
                    photoFile = createImageFile();
                } catch (IOException ex) {
                    // Error occurred while creating the File

                }
                // Continue only if the File was successfully created
                if (photoFile != null) {
                    Uri photoURI = FileProvider.getUriForFile(this,
                            "com.example.android.fileprovider",
                            photoFile);
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                    //startActivityForResult(takePictureIntent, CAMERA_REQUEST_CODE);
                    startForResult.launch(takePictureIntent);
                }
            }
        }
    */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == CAMERA_PERM_CODE){
            if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                startForResult.launch(takePictureIntent);
            }else{
                Toast.makeText(this, "camera permission is require", Toast.LENGTH_SHORT).show();
            }
        }

    }
}