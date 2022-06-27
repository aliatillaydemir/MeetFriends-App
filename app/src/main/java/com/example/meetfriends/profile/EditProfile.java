package com.example.meetfriends.profile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.meetfriends.R;
import com.example.meetfriends.databinding.ActivityEditProfileBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class EditProfile extends AppCompatActivity {

    private ActivityEditProfileBinding binding;

    private FirebaseAuth auth;
    private FirebaseFirestore firebaseFirestore;

    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditProfileBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        auth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        userId = auth.getCurrentUser().getUid();

      DocumentReference documentReference = firebaseFirestore.collection("Profile").document(userId);
        Map<String,Object> user = new HashMap<>();


        binding.kaydetButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                user.put("Location",binding.textLocation.getText().toString());
                user.put("Birth",binding.textBirthday.getText().toString());
                user.put("Name",binding.textAd.getText().toString());
                user.put("Phone",binding.textTel.getText().toString());
                user.put("Mail",binding.textEmail.getText().toString());




                documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(EditProfile.this, "Success", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(EditProfile.this, "Fail", Toast.LENGTH_SHORT).show();
                    }
                });

                startActivity(new Intent(getApplicationContext(),ProfileActivity.class));
            }
        });





    }
}