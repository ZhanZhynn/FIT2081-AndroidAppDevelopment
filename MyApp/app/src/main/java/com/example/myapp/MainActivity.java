package com.example.myapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextView bookId, bookTitle, bookISBN, bookAuthor, bookDescription, bookPrice;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bookId = findViewById(R.id.bookID);
        bookTitle = findViewById(R.id.bookTitle);
        bookISBN = findViewById(R.id.bookISBN);
        bookAuthor = findViewById(R.id.bookAuthor);
        bookDescription = findViewById(R.id.bookDescription);
        bookPrice = findViewById(R.id.bookPrice);
    }

    public void showToast(View view){
        String toastString = "Successfully added '" + bookTitle.getText().toString() + " (RM" + bookPrice.getText().toString() + ")'.";
        Toast myToast = Toast.makeText(this, toastString, Toast.LENGTH_LONG);
        myToast.show();
        clearInput(view);
    }

    public void clearInput(View view){
        //clear the input field
        bookId.setText("");
        bookTitle.setText("");
        bookISBN.setText("");
        bookAuthor.setText("");
        bookDescription.setText("");
        bookPrice.setText("");

    }
}