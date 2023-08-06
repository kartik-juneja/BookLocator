package com.bharat.booklocator;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;


public class SignUp_Activity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 0;
    TextView userName,passwordView,account1, oR;
    EditText Name, EmailAddress1;
    View view,view1,view3;
    Button Signupbtn;
    TextInputEditText passwordText;


    FirebaseAuth mAuth;
    FirebaseUser mUser;
    FirebaseFirestore firebaseFirestore;
    ProgressDialog progressDialog;
    ImageView googlebtn;
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;

    private DatabaseReference reference, deRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        firebaseFirestore = FirebaseFirestore.getInstance();
        progressDialog = new ProgressDialog(this);

        userName = findViewById(R.id.userName);
        oR = findViewById(R.id.OR);
        googlebtn =findViewById(R.id.googlelbtn);
        passwordView = findViewById(R.id.passwordView);
        account1 = findViewById(R.id.account1);
        Name = findViewById(R.id.Name);
        EmailAddress1 = findViewById(R.id.EmailAddress1);
        passwordText = (TextInputEditText) findViewById(R.id.passwordText);
        view = findViewById(R.id.view);
        view1 = findViewById(R.id.view1);
        view3 = findViewById(R.id.view3);
        Signupbtn = findViewById(R.id.Signupbtn);

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        gsc = GoogleSignIn.getClient(this,gso);


        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if (account!=null){
            Intent intent = new Intent(SignUp_Activity.this,UserInterface.class);
        }

        Signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = Name.getText().toString();
                String Email = EmailAddress1.getText().toString().trim();
                String password = passwordText.getText().toString().trim();
                if (name.isEmpty()){
                    Name.setError("Enter Your Full Name");
                    return;
                }
               else if (!Email.contains("@ggi.ac.in")) {
                    EmailAddress1.setError("Enter Your College Email Id");
                    return;
               } else if (password.isEmpty() || password.length() < 8) {
                    passwordText.setError("Enter 8 Character Password");
                    return;
               }
//             This Method Used to Store Date In RealTime DataBase
                progressDialog.setMessage("Please Wait.....");
                progressDialog.show();
                reference = FirebaseDatabase.getInstance().getReference().child("UserSignUPData");
                deRef = reference.child("UserData");
                final String UniqueKey = deRef.push().getKey();
                UserData userData = new UserData(name,Email,password);

                assert UniqueKey != null;
                deRef.child(UniqueKey).setValue(userData).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        progressDialog.cancel();


                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.cancel();

                    }
                });


                mAuth.createUserWithEmailAndPassword(Email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Intent intent = new Intent(SignUp_Activity.this,UserInterface.class);
                            startActivity(intent);
                            Toast.makeText(SignUp_Activity.this, "SignUp Successfully", Toast.LENGTH_SHORT).show();
                            progressDialog.cancel();
                            firebaseFirestore.collection("User").document(Objects.requireNonNull(FirebaseAuth.getInstance().getUid()))
                                    .set(new UserData(name,Email,password));
                        }else {
                            Toast.makeText(SignUp_Activity.this, "Error occur", Toast.LENGTH_SHORT).show();
                        }

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.cancel();
                        Toast.makeText(SignUp_Activity.this, "Oops! Something Went Wrong", Toast.LENGTH_SHORT).show();


                    }
                });
            }
        });

        account1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUp_Activity.this,MainActivity.class);
                startActivity(intent);
            }
        });

        googlebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                googleSinUp();
            }
        });


    }
// this method is used to Login With Google

    private void googleSinUp() {
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

                    progressDialog.setMessage("Please wait.....");
                    progressDialog.show();

                    AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
                    FirebaseAuth.getInstance().signInWithCredential(credential)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(SignUp_Activity.this, " Login Successfully ", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(SignUp_Activity.this, UserInterface.class);
                                        startActivity(intent);

                                    } else {
                                        Toast.makeText(SignUp_Activity.this, "Oops! Something Went Wrong", Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });
                } catch (ApiException e) {
                    e.printStackTrace();
                }
            }

        }

}