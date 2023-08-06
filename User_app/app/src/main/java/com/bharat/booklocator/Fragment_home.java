package com.bharat.booklocator;

import android.app.Activity;
import android.appwidget.AppWidgetHost;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class Fragment_home extends Fragment implements SelecetListener{
      private  SearchView searchview;
      private RecyclerView recycleView;
    private String attributeValue;

      ArrayList<searchList>list;
    ArrayList<searchList>updatedList;

      DatabaseReference databaseReference,myRef;
      UserSearchDataAdpater userSearchDataAdpater;


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public Fragment_home() {

    }

    public static Fragment_home newInstance(String param1, String param2) {
        Fragment_home fragment = new Fragment_home();
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
        View view=  inflater.inflate(R.layout.fragment_home, container, false);

        recycleView = view.findViewById(R.id.recycleView);
        searchview = view.findViewById(R.id.searchview);

        databaseReference = FirebaseDatabase.getInstance().getReference("BookShelf");
        list = new ArrayList<>();
        updatedList = new ArrayList<>();

        recycleView.setLayoutManager(new LinearLayoutManager(getContext()));
        userSearchDataAdpater = new UserSearchDataAdpater(getContext(),list,this);
        recycleView.setAdapter(userSearchDataAdpater);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    String BookName = dataSnapshot.child("titleBook").getValue(String.class);
                    String AuthorName = dataSnapshot.child("authorBook").getValue(String.class);
                    String publisher = dataSnapshot.child("publisher").getValue(String.class);
                    String barcode = dataSnapshot.child("barcode").getValue(String.class);
//                    String  ShelfNo = dataSnapshot.child("ShelNo").getValue(String.class);

                    searchList bharat = new searchList(BookName, AuthorName, publisher, barcode);
                    list.add(bharat);
                }
                userSearchDataAdpater.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        searchview.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
               return false;

            }
        });

        return view;
    }

    @Override
    public void onItemClicked(searchList searchList) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("BookShelf");

        if (myRef != null) {
            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    String ShelfNo = dataSnapshot.child("ShelfNo").getValue(String.class);
                    Intent intent = new Intent(getActivity(), BookDetails_Activity.class);
                intent.putExtra("key", "ShelNo");
                startActivity(intent);

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // ...
                }
            });
        }



    }


}
