package com.example.myapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText bookId, bookTitle, bookISBN, bookAuthor, bookDescription, bookPrice;
    String bookIdStr, bookTitleStr, bookISBNStr, bookAuthorStr, bookDescriptionStr, bookPriceStrVar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bookId = findViewById(R.id.bookID);
        bookISBN = findViewById(R.id.bookISBN);
        bookTitle = findViewById(R.id.bookTitle);
        bookAuthor = findViewById(R.id.bookAuthor);
        bookDescription = findViewById(R.id.bookDescription);
        bookPrice = findViewById(R.id.bookPrice);
    }

    public void showToast(View view){
        Double bookPriceDbl = Double.parseDouble(bookPrice.getText().toString());
        String bookPriceStr = String.format("%.2f", bookPriceDbl);
        String toastString = "Successfully added book '" + bookTitle.getText().toString() + "' at price (RM" + bookPriceStr + ").";
        Toast myToast = Toast.makeText(this, toastString, Toast.LENGTH_LONG);
        myToast.show();
//        clearInput(view);

        //save last inserted book with shared preference
//        SharedPreferences sharedPreferences = getSharedPreferences("book", MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putString("bookId", bookId.getText().toString());
//        editor.putString("bookTitle", bookTitle.getText().toString());
//        editor.putString("bookISBN", bookISBN.getText().toString());
//        editor.putString("bookAuthor", bookAuthor.getText().toString());
//        editor.putString("bookDescription", bookDescription.getText().toString());
//        editor.putString("bookPrice", bookPrice.getText().toString());
//        editor.commit();

        //save last inserted book
        bookIdStr = bookId.getText().toString();
        bookTitleStr = bookTitle.getText().toString();
        bookISBNStr = bookISBN.getText().toString();
        bookAuthorStr = bookAuthor.getText().toString();
        bookDescriptionStr = bookDescription.getText().toString();
        bookPriceStrVar = bookPriceStr;
    }

    public void clearInput(View view){
        //clear the input field
        bookId.setText("");
//        bookId.getText().clear();
        bookTitle.setText("");
        bookISBN.setText("");
        bookAuthor.setText("");
        bookDescription.setText("");
        bookPrice.setText("");
    }

    public void doublePrice(View view){
        Double bookPriceDbl = Double.parseDouble(bookPrice.getText().toString());
        bookPriceDbl = bookPriceDbl * 2;
        String bookPriceStr = String.format("%.2f", bookPriceDbl);
        bookPrice.setText(bookPriceStr);
    }

    //W3 Task 1: The activity must save and restore the view data of the book title and ISBN when you change its orientation.
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        //save only the view data of the book title and ISBN
//        super.onSaveInstanceState(outState);
        outState.putString("bookTitle", bookTitle.getText().toString());
        outState.putString("bookISBN", bookISBN.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
//        super.onRestoreInstanceState(savedInstanceState);
        //restore on the view data of the book title and ISBN
        bookTitle.setText(savedInstanceState.getString("bookTitle"));
        bookISBN.setText(savedInstanceState.getString("bookISBN"));
    }

    //Task 2: The app must remember the last book added to the system. In other words, opening the app should load the attributes of the previous book added.
    @Override
    protected void onStop() {
        super.onStop();
        //save the last book added to the system
        SharedPreferences sharedPreferences = getSharedPreferences("book", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("bookId", bookId.getText().toString());
        editor.putString("bookTitle", bookTitle.getText().toString());
        editor.putString("bookISBN", bookISBN.getText().toString());
        editor.putString("bookAuthor", bookAuthor.getText().toString());
        editor.putString("bookDescription", bookDescription.getText().toString());
        editor.putString("bookPrice", bookPrice.getText().toString());
        editor.commit();
    }

    @Override
    protected void onStart() {
        super.onStart();
        //load the last book added to the system
        SharedPreferences sharedPreferences = getSharedPreferences("book", MODE_PRIVATE);
        bookId.setText(sharedPreferences.getString("bookId", ""));
        bookTitle.setText(sharedPreferences.getString("bookTitle", ""));
        bookISBN.setText(sharedPreferences.getString("bookISBN", ""));
        bookAuthor.setText(sharedPreferences.getString("bookAuthor", ""));
        bookDescription.setText(sharedPreferences.getString("bookDescription", ""));
        bookPrice.setText(sharedPreferences.getString("bookPrice", ""));

        //save last inserted book
        bookIdStr = bookId.getText().toString();
        bookTitleStr = bookTitle.getText().toString();
        bookISBNStr = bookISBN.getText().toString();
        bookAuthorStr = bookAuthor.getText().toString();
        bookDescriptionStr = bookDescription.getText().toString();
        bookPriceStrVar = bookPrice.getText().toString();


    }

    //Task 3: Add a new button that is responsible for reloading the save attributes.
    public void reload(View view){
        //load the last book added to the system
//        SharedPreferences sharedPreferences = getSharedPreferences("book", MODE_PRIVATE);
//        bookId.setText(sharedPreferences.getString("bookId", ""));
//        bookTitle.setText(sharedPreferences.getString("bookTitle", ""));
//        bookISBN.setText(sharedPreferences.getString("bookISBN", ""));
//        bookAuthor.setText(sharedPreferences.getString("bookAuthor", ""));
//        bookDescription.setText(sharedPreferences.getString("bookDescription", ""));
//        bookPrice.setText(sharedPreferences.getString("bookPrice", ""));

        bookId.setText(bookIdStr);
        bookTitle.setText(bookTitleStr);
        bookISBN.setText(bookISBNStr);
        bookAuthor.setText(bookAuthorStr);
        bookDescription.setText(bookDescriptionStr);
        bookPrice.setText(bookPriceStrVar);
    }
}