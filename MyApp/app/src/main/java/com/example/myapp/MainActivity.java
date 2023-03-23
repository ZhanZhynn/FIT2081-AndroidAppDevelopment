package com.example.myapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.StringTokenizer;

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

        /* Request permissions to access SMS */
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS, Manifest.permission.RECEIVE_SMS, Manifest.permission.READ_SMS}, 0);

        IntentFilter intentFilter = new IntentFilter("SMS_RECEIVED_ACTION");
        SMSReceiver smsReceiver = new SMSReceiver();
        registerReceiver(myReceiver, intentFilter);

    }

    //Receive SMS
    BroadcastReceiver myReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //Retrieve SMS
            String msg = intent.getStringExtra("message");
            Toast myToast = Toast.makeText(context, msg, Toast.LENGTH_LONG);
            myToast.show();

            /*
             * String Tokenizer is used to parse the incoming message
             * */
            //12|H|123|JK|Fantasy|45|True
            StringTokenizer sT = new StringTokenizer(msg, "|");
            String bookIDMsg = sT.nextToken();
            String bookTitleMsg = sT.nextToken();
            String bookISBNMsg = sT.nextToken();
            String bookAuthorMsg = sT.nextToken();
            String bookDescriptionMsg = sT.nextToken();
            String bookPriceMsg = sT.nextToken();
            String smsBooleanStr = sT.nextToken();

            Boolean smsBoolean = Boolean.parseBoolean(smsBooleanStr);
            Double bookPriceDbl = Double.parseDouble(bookPriceMsg);

            if (smsBoolean == true){
                bookPriceDbl = bookPriceDbl + 100;
            }else{
                bookPriceDbl = bookPriceDbl + 5;
            }


            bookId.setText(bookIDMsg);
//        bookId.getText().clear();
            bookTitle.setText(bookTitleMsg);
            bookISBN.setText(bookISBNMsg);
            bookAuthor.setText(bookAuthorMsg);
            bookDescription.setText(bookDescriptionMsg);
            bookPrice.setText(bookPriceDbl.toString());
        }
    };

    public void showToast(View view){
        Double bookPriceDbl;
        String bookPriceStr = "";

        //if no price inserted, set default price to empty string
        if (!bookPrice.getText().toString().isEmpty()) {
            bookPriceDbl = Double.parseDouble(bookPrice.getText().toString());
            bookPriceStr = String.format("%.2f", bookPriceDbl);
        }
        String toastString = "Successfully added book '" + bookTitle.getText().toString() + "' at price (RM" + bookPriceStr + ").";
        Toast myToast = Toast.makeText(this, toastString, Toast.LENGTH_LONG);
        myToast.show();
//        clearInput(view);

        //save last inserted book to persistent data with shared preference
        SharedPreferences sharedPreferences = getSharedPreferences("book", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("bookId", bookId.getText().toString());
        editor.putString("bookTitle", bookTitle.getText().toString());
        editor.putString("bookISBN", bookISBN.getText().toString());
        editor.putString("bookAuthor", bookAuthor.getText().toString());
        editor.putString("bookDescription", bookDescription.getText().toString());
        editor.putString("bookPrice", bookPriceStr);
        editor.commit();

        //save last inserted book to instance variable
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
        bookId.setText("");
        bookAuthor.setText("");
        bookDescription.setText("");
        bookPrice.setText("");
    }

    //Task 2: The app must remember the last book added to the system. In other words, opening the app should load the attributes of the previous book added.
    @Override
    protected void onStop() {
        super.onStop();
        //save the last book added to the system
//        SharedPreferences sharedPreferences = getSharedPreferences("book", MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putString("bookId", bookId.getText().toString());
//        editor.putString("bookTitle", bookTitle.getText().toString());
//        editor.putString("bookISBN", bookISBN.getText().toString());
//        editor.putString("bookAuthor", bookAuthor.getText().toString());
//        editor.putString("bookDescription", bookDescription.getText().toString());
//        editor.putString("bookPrice", bookPrice.getText().toString());
//        editor.commit();
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

        //save last inserted book to instance variable
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
        SharedPreferences sharedPreferences = getSharedPreferences("book", MODE_PRIVATE);
        bookId.setText(sharedPreferences.getString("bookId", ""));
        bookTitle.setText(sharedPreferences.getString("bookTitle", ""));
        bookISBN.setText(sharedPreferences.getString("bookISBN", ""));
        bookAuthor.setText(sharedPreferences.getString("bookAuthor", ""));
        bookDescription.setText(sharedPreferences.getString("bookDescription", ""));
        bookPrice.setText(sharedPreferences.getString("bookPrice", ""));

//        bookId.setText(bookIdStr);
//        bookTitle.setText(bookTitleStr);
//        bookISBN.setText(bookISBNStr);
//        bookAuthor.setText(bookAuthorStr);
//        bookDescription.setText(bookDescriptionStr);
//        bookPrice.setText(bookPriceStrVar);
    }

    //W3 extra task: set isbn to 00112233 in shared preferences
//    public void setISBN(View view){
//        SharedPreferences sharedPreferences = getSharedPreferences("book", MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putString("bookISBN", "00112233");
//        editor.commit();
//    }


}