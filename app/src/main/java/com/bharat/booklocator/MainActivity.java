package com.bharat.booklocator;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

import com.google.android.gms.common.api.ApiException;

import com.google.android.gms.dynamic.IFragmentWrapper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class MainActivity extends AppCompatActivity {
    private static final int RC_SIGN_IN =100;

    TextView userName,passwordView,forgotPassword ,signup;

    EditText Email;
    View  view,view1,view3;

    Button loginbtn;
    ImageView  googlelogo;
    ProgressDialog progressDialog;
    TextInputEditText passwordText;


    FirebaseAuth mAuth;
    FirebaseUser mUser;

    GoogleSignInOptions gso;
    GoogleSignInClient gsc;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        progressDialog = new ProgressDialog(this);

        userName = findViewById(R.id.userName);
        passwordView = findViewById(R.id.passwordView);
        forgotPassword = findViewById(R.id.forgotPassword);
        signup = findViewById(R.id.signup);
        Email = findViewById(R.id.Email);
        view = findViewById(R.id.view);
        view1 = findViewById(R.id.view1);
        view3 = findViewById(R.id.view3);
        passwordText = ( TextInputEditText)findViewById(R.id.passwordText);
        loginbtn = findViewById(R.id.loingbtn);

        googlelogo = findViewById(R.id.googlelogo);

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail().build();
        gsc = GoogleSignIn.getClient(this,gso);



        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if (account!=null){
            Intent intent = new Intent(MainActivity.this,UserInterface.class);
        }

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // get Email and password from user
                String email = Email.getText().toString().trim();
                String password = passwordText.getText().toString().trim();
                if (email.isEmpty() || !email.contains("@ggi.ac.in")) {
                    Email.setError("Enter valid Email Address");
                    Toast.makeText(MainActivity.this, "Please Enter Your College E-mail Id ", Toast.LENGTH_SHORT).show();
                    return;
                } else if (password.isEmpty() || password.length() < 8) {
                    Toast.makeText(MainActivity.this, "Please Enter 8 Character Password", Toast.LENGTH_SHORT).show();
                    return;
                }
                progressDialog.setMessage("Please wait...");
                progressDialog.show();
                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.cancel();
                        if (task.isSuccessful()) {
                            Intent intent = new Intent(MainActivity.this, UserInterface.class);
                            startActivity(intent);

                            Toast.makeText(MainActivity.this, "Login successfully", Toast.LENGTH_SHORT).show();

                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.cancel();
                        Toast.makeText(MainActivity.this, "Oop! Wrong Password", Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,SignUp_Activity.class);
                startActivity(intent);
            }
        });

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String forgot = forgotPassword.getText().toString().trim();
                mAuth.sendPasswordResetEmail(String.valueOf(Email)).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(MainActivity.this, "Email sent", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });


            }
        });

        googlelogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signin();

            }
        });
    }
    private void signin() {
        Intent intent = gsc.getSignInIntent();
         startActivityForResult(intent,RC_SIGN_IN);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
        try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
               progressDialog.setMessage("please wait...");
              progressDialog.show();
                AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
                FirebaseAuth.getInstance().signInWithCredential(credential)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(MainActivity.this, " Login Successfully ", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(MainActivity.this, UserInterface.class);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(MainActivity.this, "Oops! Something Went Wrong", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            } catch (ApiException e) {
                e.printStackTrace();

            }
        }
    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//        if (user!=null){
//            Intent intent = new Intent(this,UserInterface.class);
//            startActivity(intent);
//        }
//
//    }

}













