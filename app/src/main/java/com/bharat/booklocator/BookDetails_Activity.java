package com.bharat.booklocator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class BookDetails_Activity extends AppCompatActivity {
    Button  shelf1,shelf2,shelf3,shelf4,shelf5,shelf6,Shelf7,shelf8,shelf9,shelf10 ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);
        shelf1 = findViewById(R.id.shelf1);
        shelf2 = findViewById(R.id.shelf2);
        shelf3 = findViewById(R.id.shelf3);
        shelf4 = findViewById(R.id.shelf4);
        shelf5 = findViewById(R.id.shelf5);
        shelf6 = findViewById(R.id.shelf6);
        Shelf7 = findViewById(R.id.shelf7);
        shelf8 = findViewById(R.id.shelf8);
        shelf9 = findViewById(R.id.shelf9);
        shelf10 = findViewById(R.id.shelf10);

        String ShelfNo = getIntent().getStringExtra("key");
        int color = Color.YELLOW;
        shelf1.setBackgroundColor(color);
        shelf1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("BookShelf");
                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String rackNo = dataSnapshot.child("rackNo").getValue(String.class);
                        Intent intent = new Intent(BookDetails_Activity.this,LibraryShelf_Activity.class);
                        intent.putExtra("rackNo", "rackNo");
                        startActivity(intent);
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


            }
        });








    }
}