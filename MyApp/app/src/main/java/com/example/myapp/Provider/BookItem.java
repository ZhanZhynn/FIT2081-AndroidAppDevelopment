package com.example.myapp.Provider;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "items")
public class BookItem {
    //BookItem class for database use

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "bookID")
    int bookId;

    @ColumnInfo(name = "bookTitle")
    String bookTitleStr;

    @ColumnInfo(name = "bookISBN")
    String bookISBNStr;

    @ColumnInfo(name = "bookAuthor")
    String bookAuthorStr;

    @ColumnInfo(name = "bookDescription")
    String bookDescriptionStr;

    @ColumnInfo(name = "bookPrice")
    double bookPrice;



    public BookItem(String bookTitleStr, String bookISBNStr, String bookAuthorStr, String bookDescriptionStr, double bookPrice) {
        //id is auto generated, so don't need add
        this.bookTitleStr = bookTitleStr;
        this.bookISBNStr = bookISBNStr;
        this.bookAuthorStr = bookAuthorStr;
        this.bookDescriptionStr = bookDescriptionStr;
        this.bookPrice = bookPrice;
    }

    public String toString() {
        return this.bookTitleStr + " | " + this.bookPrice;
    }

    //getter for all the attributes
    public int getID(){
        return bookId;
    }

    public String getTitle(){
        return bookTitleStr;
    }

    public String getISBN(){
        return bookISBNStr;
    }

    public String getAuthor(){
        return bookAuthorStr;
    }

    public String getDesc(){
        return bookDescriptionStr;
    }

    public double getPrice(){
        return bookPrice;
    }


}
