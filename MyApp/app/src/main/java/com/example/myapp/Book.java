package com.example.myapp;

public class Book {

    public String bookIdStr, bookTitleStr, bookISBNStr, bookAuthorStr, bookDescriptionStr, bookPriceStrVar;

    public Book(String bookIdStr, String bookTitleStr, String bookISBNStr, String bookAuthorStr, String bookDescriptionStr, String bookPriceStrVar) {
        this.bookIdStr = bookIdStr;
        this.bookTitleStr = bookTitleStr;
        this.bookISBNStr = bookISBNStr;
        this.bookAuthorStr = bookAuthorStr;
        this.bookDescriptionStr = bookDescriptionStr;
        this.bookPriceStrVar = bookPriceStrVar;
    }

    public String toString() {
        return this.bookTitleStr + " | " + this.bookPriceStrVar;
    }

}
