package com.example.fruitapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdaptor extends RecyclerView.Adapter<MyAdaptor.MyViewHolder>{

    ArrayList<Fruit> data = new ArrayList<>();

    //The constructor is required to receive the data source
    public MyAdaptor(ArrayList<Fruit> data) {
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
        myViewHolder.itemName.setText(data.get(pos).getFruitName());
        myViewHolder.itemFamily.setText(data.get(pos).getFruitFamily());
        myViewHolder.itemSugar.setText(data.get(pos).getFruitSugar());
        myViewHolder.itemCalories.setText(data.get(pos).getFruitCalories());
        myViewHolder.itemCarbohydrate.setText(data.get(pos).getFruitCarbohydrate());

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView itemName;
        public TextView itemFamily;
        public TextView itemSugar;
        public TextView itemCalories;
        public TextView itemCarbohydrate;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.item_name);
            itemFamily = itemView.findViewById(R.id.item_family);
            itemSugar = itemView.findViewById(R.id.item_sugar);
            itemCalories = itemView.findViewById(R.id.item_calories);
            itemCarbohydrate = itemView.findViewById(R.id.item_carbohydrate);

        }
    }
}
