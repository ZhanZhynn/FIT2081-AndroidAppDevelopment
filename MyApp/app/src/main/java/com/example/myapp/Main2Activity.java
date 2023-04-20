package com.example.myapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.myapp.Provider.BookItem;
import com.example.myapp.Provider.ItemViewModel;

import java.util.ArrayList;

//need to add to android manifest
public class Main2Activity extends AppCompatActivity {

//    RecyclerView recyclerView;
//    RecyclerView.LayoutManager layoutManager;
//    MyRecyclerViewAdapter adapter;

//    ArrayList<Item> data;

//    private ItemViewModel mItemViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

//        recyclerView =  findViewById(R.id.rv2);
//
//        layoutManager = new LinearLayoutManager(this);  //A RecyclerView.LayoutManager implementation which provides similar functionality to ListView.
//        recyclerView.setLayoutManager(layoutManager);   // Also StaggeredGridLayoutManager and GridLayoutManager or a custom Layout manager
//
//
//        adapter = new MyRecyclerViewAdapter();
//        recyclerView.setAdapter(adapter);
//
//        mItemViewModel = new ViewModelProvider(this).get(ItemViewModel.class);
//        mItemViewModel.getAllItems().observe(this, newData -> {
//            adapter.setData(newData);
//            adapter.notifyDataSetChanged();
//        });
        getSupportFragmentManager().beginTransaction().replace(R.id.frame2,new RecyclerViewFragment()).commit();
    }
}