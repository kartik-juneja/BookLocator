package com.bharat.booklocator;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class UserSearchDataAdpater extends RecyclerView.Adapter<UserSearchDataAdpater.MyViewHolder> {
    Context context;
    ArrayList<searchList> list;
    ArrayList<searchList>newArrayListFull;

    private SelecetListener listener;

    public UserSearchDataAdpater(Context context, ArrayList<searchList> list,SelecetListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
        newArrayListFull = new ArrayList<>(list);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.searchhome,parent,false);
        return new MyViewHolder(view);

    }

    @SuppressLint("NewApi")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {

        searchList searchList = list.get(position);
        holder.BookName.setText(searchList.getBookName());
        holder.authorName.setText(searchList.getAuthorName());
        holder.publisher.setText(searchList.getPublisher());
        holder.BookCode.setText(searchList.getBookCode());
        holder.cardView.setCardBackgroundColor(holder.itemView.getResources().getColor(getRandomColor(),null));
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClicked(list.get(position));
//
            }
        });

    }

    private int getRandomColor() {
        List<Integer>colorCode = new ArrayList<>();
//        colorCode.add(R.color.teal_200);
        colorCode.add(R.color.yellow);
//        colorCode.add(R.color.purple_200);
//        colorCode.add(R.color.teal_500);
//        colorCode.add(R.color.pink);
//        colorCode.add(R.color.red2);

        Random random= new Random();
        int number = random.nextInt(colorCode.size());
        return colorCode.get(number);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public  class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView BookName, authorName, publisher, BookCode;
        private CardView cardView;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            BookName = itemView.findViewById(R.id.BookName);
            authorName = itemView.findViewById(R.id.authorName);
            publisher = itemView.findViewById(R.id.publisher);
            BookCode = itemView.findViewById(R.id.BookCode);
            cardView = itemView.findViewById(R.id.CardViewText);

        }
    }
}




