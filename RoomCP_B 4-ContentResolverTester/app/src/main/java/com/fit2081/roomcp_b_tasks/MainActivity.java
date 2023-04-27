package com.fit2081.roomcp_b_tasks;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class MainActivity extends AppCompatActivity {
    TextView tV;
    Uri uri;
//    public static final String COLUMN_NAME = "taskName";
//    public static final String COLUMN_DESCRIPTION = "taskDescription";

    DatabaseReference myRef; //firebase reference

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tV = findViewById(R.id.textView_id);
        uri = Uri.parse("content://fit2081.app.Hee/items");
        Cursor result = getContentResolver().query(uri, null, null, null);
        if (result != null)
            tV.setText(result.getCount() + "");
        else
            tV.setText("Result is null");

        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Book/BookItem"); //table path name


        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                //toast message
                Toast.makeText(MainActivity.this, "Data added", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                //toast message
                Toast.makeText(MainActivity.this, "Data removed", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


//        ContentValues values= new ContentValues();
//        values.put(COLUMN_NAME,"New Task Name");
//        values.put(COLUMN_DESCRIPTION,"New Desc");
//        Uri uri2= getContentResolver().insert(uri,values);
//        Toast.makeText(this,uri2.toString(),Toast.LENGTH_LONG).show();

    }

    //get book from database using content resolver
    public void getBook(View v){
        Cursor result = getContentResolver().query(uri, null, null, null);
        if (result != null)
            tV.setText(result.getCount() + "");
        else
            tV.setText("Result is null");
    }

    //add book to database using content resolver
    public void addBook(View v) {
        ContentValues values = new ContentValues();
//        values.put("bookID", "New Book ID");  //ID is auto generated, not needed. Not in the bookItem constructor too
        values.put("bookTitle", "New Book Name");
        values.put("bookISBN", "New ISBN");
        values.put("bookAuthor", "New Author");
        values.put("bookDescription", "New Description");
        values.put("bookPrice", 69);
        Uri uri2 = getContentResolver().insert(uri, values);
        Toast.makeText(this, uri2.toString(), Toast.LENGTH_SHORT).show();



        BookItem bookItem = new BookItem("New Book Name", "New ISBN", "New Author", "New Description", 69);
        //add to firebase
        myRef.push().setValue(bookItem);

    }

    //delete all book in database using content resolver
    public void deleteAll(View v){
        int result = getContentResolver().delete(uri, null, null);
        Toast.makeText(this, result + " rows deleted", Toast.LENGTH_SHORT).show();

        myRef.removeValue();
    }
}