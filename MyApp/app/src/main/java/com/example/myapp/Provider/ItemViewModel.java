package com.example.myapp.Provider;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class ItemViewModel extends AndroidViewModel {

    private ItemRepository mRepository;
    private LiveData<List<BookItem>> mAllItems;

    public ItemViewModel(@NonNull Application application) {
        super(application);
        mRepository = new ItemRepository(application);
        mAllItems = mRepository.getAllItems();
    }

    public LiveData<List<BookItem>> getAllItems() {
        return mAllItems;
    }

    public void insert(BookItem item) {
        mRepository.insert(item);
    }

    public void deleteAll(){
        mRepository.deleteAll();
    }

    public void deleteLast(){mRepository.deleteLast();
    }

    public void delete50(){mRepository.delete50();
    }

}
