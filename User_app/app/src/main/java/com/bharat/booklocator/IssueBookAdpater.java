package com.bharat.booklocator;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class IssueBookAdpater extends RecyclerView.Adapter<IssueBookAdpater.MyViewHolder> {
    Context context;
    ArrayList<IssueBookData> list;

    public IssueBookAdpater(Context context, ArrayList<IssueBookData> list) {

        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.issuebookdata,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IssueBookAdpater.MyViewHolder holder, int position) {

        IssueBookData issueBookData = list.get(position);
        holder.Name.setText(issueBookData.getStudentName());
        holder.EmailAddress2.setText(issueBookData.getEmailAddress());
        holder.AuthorName.setText(issueBookData.getAuthorName());
        holder.BookName.setText(issueBookData.getBookName());
        holder.IssueDate.setText(issueBookData.getIssueDate());
        holder.ReturnDate.setText(issueBookData.getReturnDate());

    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public static  class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView  Name,EmailAddress2,AuthorName,BookName,IssueDate,ReturnDate;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            Name = itemView.findViewById(R.id.Name);
            EmailAddress2 = itemView.findViewById(R.id.EmailAddress2);
            AuthorName = itemView.findViewById(R.id.AuthorName);
            BookName= itemView.findViewById(R.id.BookName);
            IssueDate = itemView.findViewById(R.id.IssueDate);
            ReturnDate = itemView.findViewById(R.id.ReturnDate);
        }
    }
}
