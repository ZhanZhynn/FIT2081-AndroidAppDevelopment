package com.example.myapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText bookId, bookTitle, bookISBN, bookAuthor, bookDescription, bookPrice;


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
}