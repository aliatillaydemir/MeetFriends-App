package com.example.meetfriends.loginRegister;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.meetfriends.other.MainActivity;
import com.example.meetfriends.R;
import com.facebook.CallbackManager;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    Button logbut, creAccountButton, forgetBut;
    EditText username, password;
    FirebaseAuth fireAuth;
    AlertDialog.Builder reset;
    LayoutInflater inflater;
    LoginButton btnFace;
    CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        fireAuth = FirebaseAuth.getInstance();


        reset = new AlertDialog.Builder(this);
        inflater = this.getLayoutInflater();


        creAccountButton=findViewById(R.id.CreateBut);
        creAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextActivity();
            }
        });

        username = findViewById(R.id.log_email);
        password = findViewById(R.id.log_pass);
        logbut = findViewById(R.id.Log_button);
        forgetBut = findViewById(R.id.forgetButton);
        btnFace = findViewById(R.id.login_button); //facebook için


        forgetBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                View view = inflater.inflate(R.layout.reset_popup,null);

                reset.setTitle("Reset Forgot Password")
                        .setMessage("Enter ur email to get pasword reset link")
                        .setPositiveButton("Reset", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            EditText email = view.findViewById(R.id.reset_email);
                            if(email.getText().toString().isEmpty())
                            {
                                email.setError("email Required");
                                return;
                            }

                        fireAuth.sendPasswordResetEmail(email.getText().toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {

                                Toast.makeText(LoginActivity.this, "Reset Email Sent", Toast.LENGTH_SHORT).show();

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                                Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        });

                            }
                        }).setNegativeButton("Cancel",null)
                        .setView(view)
                        .create().show();
            }
        });


        logbut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (username.getText().toString().isEmpty())
                {
                    username.setError("Email is Missing");
                    return;
                }

                if(password.getText().toString().isEmpty())
                {
                    password.setError("Password is Missing");
                    return;
                }

                fireAuth.signInWithEmailAndPassword(username.getText().toString(), password.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {

                        startActivity(new Intent(getApplicationContext(), MainActivity.class));

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });

            }
        });

///////////////////////////////////////////////////////////////////////////////
  /*      //facebook ile login yapma
        btnFace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);

            }
        });
*/
/*
        callbackManager = CallbackManager.Factory.create();


        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile"));
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        handleFacebookAccessToken(loginResult.getAccessToken());
                    }

                    @Override
                    public void onCancel() {
                        // App code
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                    }
                });
///////////////////////////////////////////////////facebook için
*/

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Pass the activity result back to the Facebook SDK
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
/*
//facebook için  ////////////////////////////////////////////////////////////
    private void handleFacebookAccessToken(AccessToken token) {


        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        fireAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information

                            FirebaseUser user = fireAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.

                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }

                    private void updateUI(FirebaseUser user) {
                        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                        startActivity(intent);
                    }
                });
    }
//////////////////////////////////////////////////////////////////////////////
*/

    //eğer bu cihazda hesabımız varsa sürekli login ekranı çıkmaz, hatırlar. hicbir uygulamada durmadan şifre-email girmeyiz. banka hesabı değgilse tabi :))
    @Override
    protected void onStart() {
        super.onStart();

        if(FirebaseAuth.getInstance().getCurrentUser() != null)
        {
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }

    }



    private void nextActivity() {

    startActivity(new Intent(this,RegisterActivity.class));
      //  Intent send = new Intent(LoginActivity.this,RegisterActivity.class);
      // startActivity(send);
    }


}