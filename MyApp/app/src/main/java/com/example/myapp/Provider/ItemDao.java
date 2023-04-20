package com.example.myapp.Provider;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.myapp.Provider.BookItem;

import java.util.List;

@Dao
public interface ItemDao {
    @Query("select * from items")
    LiveData<List<BookItem>> getAllItem();

    @Query("select * from items where bookTitle=:name")
    List<BookItem> getItem(String name);

    @Insert
    void addItem(BookItem item);

    @Query("delete from items where bookTitle= :name")
    void deleteItem(String name);

    @Query("delete FROM items")
    void deleteAllItems();
}
