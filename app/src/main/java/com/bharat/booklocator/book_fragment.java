package com.bharat.booklocator;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Objects;

public class book_fragment extends Fragment {
    CardView CardView;
    TextView bookRequest;
    EditText branch, semester, bookTitle, authorName, publisher;
    Button Submitbtn;
    View view1, view2, view3, view4, view5;



    private DatabaseReference reference, deRef;
    FirebaseFirestore firebaseFirestore;
    ProgressDialog progressDialog;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private String Branch ,  Semester, BookTitle, AuthorName,Publisher;
    private Object BookRequestData;


    public book_fragment() {

    }

    public static book_fragment newInstance(String param1, String param2) {
        book_fragment fragment = new book_fragment();
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
        View view = inflater.inflate(R.layout.fragment_book_fragment, container, false);

        reference = FirebaseDatabase.getInstance().getReference().child("StudentBookData");

        firebaseFirestore = FirebaseFirestore.getInstance();
        progressDialog = new ProgressDialog(getActivity());


        CardView = view.findViewById(R.id.CardView);
        bookRequest = view.findViewById(R.id.bookRequest);


        branch = view.findViewById(R.id.branch);
        semester = view.findViewById(R.id.semester);
        bookTitle = view.findViewById(R.id.bookTitle);
        authorName = view.findViewById(R.id.authorName);
        publisher = view.findViewById(R.id.publisher);

        view1 = view.findViewById(R.id.view1);
        view2 = view.findViewById(R.id.view2);
        view3 = view.findViewById(R.id.view3);
        view4 = view.findViewById(R.id.view4);
        view5 = view.findViewById(R.id.view5);

        Submitbtn = view.findViewById(R.id.Submitbtn);

        Submitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                CheckValidation();

            }
        });

        return view;
    }

    private void CheckValidation() {
        // getting data from edittext fields.

        String Branch = branch.getText().toString();
        String Semester = semester.getText().toString();
        String BookTitle = bookTitle.getText().toString();
        String AuthorName = authorName.getText().toString();
        String Publisher = publisher.getText().toString();

        // validating the text fields if empty or not.

        if (TextUtils.isEmpty(Branch)) {
            branch.setError("Please Enter Your Branch Name");
            return;

        } else if (TextUtils.isEmpty(Semester)) {
            semester.setError("Please Enter Your Semester Name");
            return;
        } else if (TextUtils.isEmpty(BookTitle)) {
            bookTitle.setError("Enter Book Name");
            return;
        } else if (TextUtils.isEmpty(AuthorName)) {
            authorName.setError("Please Enter Book Author Name ");
            return;
        } else if (TextUtils.isEmpty(Publisher)) {
            publisher.setError("Please Enter Book Publisher Name ");
            return;
        }

        progressDialog.setMessage("Please Wait Your Book Data is Uploading....");
        progressDialog.show();
        deRef = reference.child("BookData");
        final String UniqueKey = deRef.push().getKey();
        BookRequestData bookRequestData = new BookRequestData(Branch, Semester, BookTitle, AuthorName, Publisher);

        assert UniqueKey != null;
        deRef.child(UniqueKey).setValue(bookRequestData).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                progressDialog.cancel();
                Toast.makeText(getActivity(), "Your Book Data Successfully Uploaded ", Toast.LENGTH_SHORT).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.cancel();
                Toast.makeText(getActivity(), "Oop! Something Went Wrong", Toast.LENGTH_SHORT).show();

            }
        });
    }

}