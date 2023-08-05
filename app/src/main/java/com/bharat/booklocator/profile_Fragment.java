package com.bharat.booklocator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
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
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class profile_Fragment extends Fragment {
    CircleImageView userImage;
   TextView YourName,YourEmail,YourRollNo,YourSemester;
   FloatingActionButton floatingActionButton;

   FirebaseFirestore firebaseFirestore;


    Button logoutBtn;
    FirebaseAuth mAuth,FAuth;
    FirebaseUser mUser;
    String userId;

    GoogleSignInOptions gso;
    GoogleSignInClient gsc;


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;
    private Object UserData;
    private Object UserPersonalData;

    public profile_Fragment() {
    }

    public static profile_Fragment newInstance(String param1, String param2) {
        profile_Fragment fragment = new profile_Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
       View view= inflater.inflate(R.layout.fragment_profile_, container, false);

       YourName = view.findViewById(R.id.YourName);
       YourEmail = view.findViewById(R.id.YourEmail);
       YourRollNo = view.findViewById(R.id.YourRollNO);
       YourSemester = view.findViewById(R.id.YourSemester);
       floatingActionButton =view.findViewById(R.id.floatingactionbutton);
        logoutBtn = view.findViewById(R.id.logoutBtn);
        userImage = view.findViewById(R.id.userImage);

       FAuth = FirebaseAuth.getInstance();
       firebaseFirestore = FirebaseFirestore.getInstance();
       userId = FAuth.getCurrentUser().getUid();


        // Name and Email get from Firebase

       DocumentReference documentReference = firebaseFirestore.collection("User").document(userId);
       documentReference.addSnapshotListener( new EventListener<DocumentSnapshot>() {
           @Override
           public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
               YourName.setText(documentSnapshot.getString("name"));
               YourEmail.setText(documentSnapshot.getString("email"));

//               YourRollNo.setText(documentSnapshot.getString("rollNo"));
//               YourSemester.setText(documentSnapshot.getString("semester"));

//               if (YourName==YourRollNo && YourEmail==YourName){

//               }

           }
       });

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        gsc = GoogleSignIn.getClient(getContext(),gso);
       logoutBtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               logout();
           }
       });
       floatingActionButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent intent = new Intent(getActivity(), profileEdit_Activity.class);
               intent.putExtra("key", "value");
               startActivity(intent);

           }
       });
       return view;
    }

    private void logout() {
        gsc.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                Intent intent = new Intent(getActivity(),MainActivity.class);
                intent.putExtra("key","value");
                startActivity(intent);
                Toast.makeText(getActivity(), "Successfully Logout ", Toast.LENGTH_SHORT).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(), "Oop! Not Logout", Toast.LENGTH_SHORT).show();

            }
        });
    }
}