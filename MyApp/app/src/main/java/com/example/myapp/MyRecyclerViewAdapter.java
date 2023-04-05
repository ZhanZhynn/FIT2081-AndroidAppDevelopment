package com.example.myapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.MyViewHolder> {

    ArrayList<Book> data = new ArrayList<>();

    //The constructor is required to receive the data source
    public MyRecyclerViewAdapter(ArrayList<Book> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_view, viewGroup, false);
        MyViewHolder myViewHolder = new MyViewHolder(v);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int pos) {
        myViewHolder.tv_bookID.setText(data.get(pos).getID());
        myViewHolder.tv_bookTitle.setText(data.get(pos).getTitle());
        myViewHolder.tv_author.setText(data.get(pos).getAuthor());
        myViewHolder.tv_isbn.setText(data.get(pos).getISBN());
        myViewHolder.tv_price.setText(data.get(pos).getPrice());
        myViewHolder.tv_bookDesc.setText(data.get(pos).getDesc());

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView tv_bookID;
        public TextView tv_bookTitle;
        public TextView tv_author;
        public TextView tv_isbn;
        public TextView tv_price;
        public TextView tv_bookDesc;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_bookID = itemView.findViewById(R.id.cv_bookID);
            tv_bookTitle = itemView.findViewById(R.id.cv_bookTitle);
            tv_author = itemView.findViewById(R.id.cv_author);
            tv_isbn = itemView.findViewById(R.id.cv_isbn);
            tv_price = itemView.findViewById(R.id.cv_price);
            tv_bookDesc = itemView.findViewById(R.id.cv_bookDesc);
        }
    }

}
