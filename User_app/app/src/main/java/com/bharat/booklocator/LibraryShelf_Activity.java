package com.bharat.booklocator;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;

public class LibraryShelf_Activity extends AppCompatActivity {
    Button rackNo14;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library_shelf);
        rackNo14= findViewById(R.id.rack14);
        String rackNo = getIntent().getStringExtra("key");
        int color = Color.RED;
        rackNo14.setBackgroundColor(color);

    }
}