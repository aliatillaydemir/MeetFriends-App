package com.example.meetfriends.loginRegister;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.meetfriends.other.MainActivity;
import com.example.meetfriends.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ResetPassActivity extends AppCompatActivity {

    Button savebut;
    EditText pass, confpass;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_pass);


        pass = findViewById(R.id.passw);
        confpass = findViewById(R.id.confpassw);

        user = FirebaseAuth.getInstance().getCurrentUser();


        savebut = findViewById(R.id.SaveButton);
        savebut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pass.getText().toString().isEmpty())
                {
                    pass.setError("Required password");
                    return;
                }
                if(confpass.getText().toString().isEmpty())
                {
                    confpass.setError("Required password");
                }

                if(!pass.getText().toString().equals(confpass.getText().toString()))
                {
                    confpass.setError("passwords don't match");
                    return;
                }

                user.updatePassword(pass.getText().toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {

                        Toast.makeText(ResetPassActivity.this, "password changed", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Toast.makeText(ResetPassActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });

            }
        });

    }
}