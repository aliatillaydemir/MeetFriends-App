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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    EditText registerEmail, registerPass, registerConfPass;
    Button registerButton,login;
    FirebaseAuth fireAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);



        registerEmail=findViewById(R.id.MailText);
        registerPass=findViewById(R.id.PassText);
        registerConfPass=findViewById(R.id.PassText2);
        registerButton=findViewById(R.id.RegistBut);
        login=findViewById(R.id.log);

        fireAuth = FirebaseAuth.getInstance();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = registerEmail.getText().toString();
                String password = registerPass.getText().toString();
                String confPass = registerConfPass.getText().toString();


                if(email.isEmpty()){
                    registerEmail.setError("mail is required");
                    return;
                }

                if(password.isEmpty()){
                    registerPass.setError("password is required");
                    return;
                }

                if(confPass.isEmpty()){
                    registerConfPass.setError("confirmation password is required");
                    return;
                }

                if(!password.equals(confPass))
                {
                    registerConfPass.setError("Passwords don't match...");
                    return;
                }

                Toast.makeText(RegisterActivity.this, "data saved", Toast.LENGTH_SHORT).show();

                fireAuth.createUserWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {

                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(RegisterActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

    }



}