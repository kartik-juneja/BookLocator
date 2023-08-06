package com.bharat.booklocator;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
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

import java.text.BreakIterator;
import java.util.ArrayList;

public class IssueBook_fragment extends Fragment {

   private  RecyclerView recycleView;
   ArrayList<IssueBookData> list;
   ArrayList<IssueBookData> NewList;

    FirebaseFirestore firebaseFirestore;
    String userId;
    FirebaseAuth FAuth;
    String profileName;


    DatabaseReference databaseReference;
    IssueBookAdpater issueBookAdpater;


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;
    private BreakIterator YourEmail;

    public IssueBook_fragment() {

    }
    public static IssueBook_fragment newInstance(String param1, String param2) {
        IssueBook_fragment fragment = new IssueBook_fragment();
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
        View view = inflater.inflate(R.layout.fragment_issue_book_fragment, container, false);

        recycleView = view.findViewById(R.id.recycleView);

        databaseReference = FirebaseDatabase.getInstance().getReference("IssueBook");
        list = new ArrayList<>();
        NewList = new ArrayList<>();
        FAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        userId = FAuth.getCurrentUser().getUid();

//
//        DocumentReference documentReference = firebaseFirestore.collection("User").document(userId);
//
//
//        documentReference.addSnapshotListener( new EventListener<DocumentSnapshot>() {
//            @Override
//            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
//                profileName = documentSnapshot.getString("name");
//                YourEmail.setText(documentSnapshot.getString("email"));
//
//
//
//            }
//        });

        recycleView.setLayoutManager(new LinearLayoutManager(getContext()));
        issueBookAdpater = new IssueBookAdpater(getContext(),list);
        recycleView.setAdapter(issueBookAdpater);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    String BookName= dataSnapshot.child("BookName").getValue(String.class);
                    String IssueDate = dataSnapshot.child("IssueDate").getValue(String.class);
                    String ReturnDate = dataSnapshot.child("ReturnDate").getValue(String.class);
                    String StudentName = dataSnapshot.child("StudentName").getValue(String.class);
                    String EmailAddress = dataSnapshot.child("EmailAddress").getValue(String.class);
                    String AuthorName = dataSnapshot.child("BookAuthor").getValue(String.class);
//                    issueName = "Bharat chand ";
//                    if(issueName == profileName) {
                        IssueBookData issueBookData = new IssueBookData(StudentName,EmailAddress,BookName,AuthorName,IssueDate,ReturnDate);
                        list.add(issueBookData);
//                    }



                }
                issueBookAdpater.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        return view;
    }

}