package com.bharat.booklocator;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class profileEdit_Activity extends AppCompatActivity {
    private static final int REQ = 1000;
    private static final String TAG = "x";


    FirebaseFirestore firebaseFirestore;

    CardView cardView;
    EditText Branch, semester,rollNo;
    CircleImageView userImage2;
    ImageView BackArrow;
    Button saveBtn;
    View view1,view2,view3;
    ProgressDialog pd;
    private DatabaseReference reference,deRef;
    FirebaseDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);
        pd= new ProgressDialog(this);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("UserPersonalData");
        reference = FirebaseDatabase.getInstance().getReference();
        firebaseFirestore = FirebaseFirestore.getInstance();


//        myRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                String branch = dataSnapshot.getValue(String.class);
//                String semester = dataSnapshot.getValue(String.class);
//                String RollNo = dataSnapshot.getValue(String.class);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Log.w(TAG, "Failed to read value.", error.toException());
//
//            }
//
//        });
        cardView =findViewById(R.id.CardView);
        view1 =findViewById(R.id.view1);
        view2 =findViewById(R.id.view2);
        view3 =findViewById(R.id.view3);
        userImage2 =findViewById(R.id.userImage2);
        Branch =findViewById(R.id.Branch);
        semester = findViewById(R.id.semester);
        rollNo = findViewById(R.id.rollNo);
        saveBtn = findViewById(R.id.saveBtn);
        BackArrow = findViewById(R.id.BackArrow);

        BackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(profileEdit_Activity.this,UserInterface.class);
                startActivity(intent);
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //  Add personal Data from UserSide
                String branch = Branch.getText().toString();
                String Semester = semester.getText().toString();
                String RollNo = rollNo.getText().toString();
                if (branch.isEmpty()){
                    Branch.setError("Enter Your Branch Name");
                    return;
                }else if (Semester.isEmpty()){
                    semester.setError("Enter Your Semester Name");
                    return;
                }else if (RollNo.isEmpty()){
                    rollNo.setError("Enter Your RollNo");
                    return;
                }
                pd.setMessage("Please Wait Your Book Data is Uploading....");
                pd.show();
                deRef = reference.child("UserPersonalData");


                final String UniqueKey = deRef.push().getKey();
                UserPersonalData userPersonalData = new UserPersonalData(branch, Semester, RollNo);

                assert UniqueKey != null;
                deRef.child(UniqueKey).setValue(userPersonalData).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        pd.cancel();
                        Toast.makeText(profileEdit_Activity.this, "Your Data Successfully Added", Toast.LENGTH_SHORT).show();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        pd.cancel();
                        Toast.makeText(profileEdit_Activity.this, "Oop! Something Went Wrong", Toast.LENGTH_SHORT).show();

                    }
                });

                // this method is used to add data in fireStore

//                firebaseFirestore.collection("UserPersonal").document(Objects.requireNonNull(FirebaseAuth.getInstance().getUid()))
//                        .set(new UserPersonalData (branch,Semester,RollNo));



            }
        });
        userImage2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  = new Intent(profileEdit_Activity.this,UserImage_Activity.class);
                startActivity(intent);
            }
        });

    }


}


