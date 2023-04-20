package com.example.myapp.Provider;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class ItemRepository {
    private ItemDao mItemDao;
    private LiveData<List<BookItem>> mAllItems;

    ItemRepository(Application application) {
        ItemDatabase db = ItemDatabase.getDatabase(application);
        mItemDao = db.itemDao();
        mAllItems = mItemDao.getAllItem();
    }

    LiveData<List<BookItem>> getAllItems() {
        return mAllItems;
    }

    void insert(BookItem item) {
        ItemDatabase.databaseWriteExecutor.execute(() -> mItemDao.addItem(item));
    }

    void deleteAll(){
        ItemDatabase.databaseWriteExecutor.execute(()->{
            mItemDao.deleteAllItems();
        });
    }

    void deleteLast(){
        ItemDatabase.databaseWriteExecutor.execute(()->{
            mItemDao.deleteLastItem();
        });
    }

    void delete50(){
        ItemDatabase.databaseWriteExecutor.execute(()->{
            mItemDao.delete50();
        });
    }
}
