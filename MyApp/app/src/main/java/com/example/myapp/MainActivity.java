package com.example.myapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.myapp.Provider.BookItem;
import com.example.myapp.Provider.ItemViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class MainActivity extends AppCompatActivity {

    EditText bookId, bookTitle, bookISBN, bookAuthor, bookDescription, bookPrice;
    String bookIdStr, bookTitleStr, bookISBNStr, bookAuthorStr, bookDescriptionStr, bookPriceStrVar;

    ArrayList<Book> bookList = new ArrayList<Book>();
    DrawerLayout drawerLayout;



//    ArrayAdapter adapter;
    private ListView myListView;

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    MyRecyclerViewAdapter recyclerViewAdapter;

    private ItemViewModel mItemViewModel;

    MyRecyclerViewAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer);

        //W6 recycler view
//        recyclerView = findViewById(R.id.rv);

//        layoutManager = new LinearLayoutManager(this);  //A RecyclerView.LayoutManager implementation which provides similar functionality to ListView.
//        recyclerView.setLayoutManager(layoutManager);   // Also StaggeredGridLayoutManager and GridLayoutManager or a custom Layout manager
//        adapter = new MyRecyclerViewAdapter();
//        recyclerView.setAdapter(adapter);


        //Database W7
        adapter = new MyRecyclerViewAdapter();
        mItemViewModel = new ViewModelProvider(this).get(ItemViewModel.class);
        mItemViewModel.getAllItems().observe(this, newData -> {
            adapter.setBookData(newData);
            adapter.notifyDataSetChanged();
//            myText.setText(newData.size() + "");
        });

        //fragment layout
        getSupportFragmentManager().beginTransaction().replace(R.id.frame1,new RecyclerViewFragment()).commit();


        //W5 FAB
        FloatingActionButton fab = findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showToast();
                    }
                });





        //W5 toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //W5 toggle for nav bar
        drawerLayout = findViewById(R.id.drawer_nav);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        //W5 navigation view drawer
        NavigationView navigationView = findViewById(R.id.drawer__nav_view);
        navigationView.setNavigationItemSelectedListener(new MyNavigationListener());


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

    //w5 option menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.option_menu_clear) {
            clearInput();

        } else if (id == R.id.option_menu_load) {
            reload();
        } else if (id == R.id.option_menu_total){
            //show toast for total number of books in bookList
            Toast myToast = Toast.makeText(this, "Total number of books: " + bookList.size(), Toast.LENGTH_SHORT);
            myToast.show();
        }
        // tell the OS
        return true;
    }

    //W5 Drawer Buttons
    class MyNavigationListener implements NavigationView.OnNavigationItemSelectedListener {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            // get the id of the selected item
            int id = item.getItemId();

            if (id == R.id.drawer_menu_add) {
                showToast();
            } else if (id == R.id.drawer_menu_remove_last) {
                removeLastBook();
            } else if (id == R.id.drawer_menu_remove_all){
                removeAllBook();
            }
            else if (id == R.id.drawer_menu_showall){
                showDBList();
            }
            else if (id == R.id.drawer_menu_remove50){
                remove50();
            }
            else if (id==R.id.drawer_menu_close){
                //close application
                finish();
            }

            // close the drawer
            drawerLayout.closeDrawers();
            // tell the OS
            return true;
        }
    }

    public void showDBList()
    {
        Intent i = new Intent(this, Main2Activity.class);
        startActivity(i);
    }

    //Receive SMS
    BroadcastReceiver myReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //Retrieve SMS
            String msg = intent.getStringExtra("message");
            Toast myToast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
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

    public void removeLastBook(){
        //remove last book from arraylist
//        bookList.remove(bookList.size()-1);
//        //show toast
//        Toast myToast = Toast.makeText(this, "Successfully removed last book.", Toast.LENGTH_SHORT);
//        myToast.show();
//        recyclerViewAdapter.notifyDataSetChanged();

        //remove last book from database
        mItemViewModel.deleteLast();

    }

    public void removeAllBook(){
        //remove all book from arraylist
//        bookList.removeAll(bookList);
//        //show toast
//        Toast myToast = Toast.makeText(this, "Successfully removed all books.", Toast.LENGTH_SHORT);
//        myToast.show();
//        recyclerViewAdapter.notifyDataSetChanged();

        //remove all book from database
        mItemViewModel.deleteAll();
    }

    public void remove50(){
        //remove book with price more than 50
        mItemViewModel.delete50();
    }

    public void showToast(){
        Double bookPriceDbl = 0.0;
        String bookPriceStr = "";

        //if no price inserted, set default price to empty string
        if (!bookPrice.getText().toString().isEmpty()) {
            bookPriceDbl = Double.parseDouble(bookPrice.getText().toString());
            bookPriceStr = String.format("%.2f", bookPriceDbl);
        }
        String toastString = "Successfully added book '" + bookTitle.getText().toString() + "' at price (RM" + bookPriceStr + ").";
        Toast myToast = Toast.makeText(this, toastString, Toast.LENGTH_SHORT);
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
        bookPriceDbl = Double.parseDouble(bookPrice.getText().toString());

        //insert book into array list
//        Book book = new Book(bookIdStr, bookTitleStr, bookISBNStr, bookAuthorStr, bookDescriptionStr, bookPriceStrVar);
//        bookList.add(book);
        BookItem bookItem = new BookItem(bookTitleStr, bookISBNStr, bookAuthorStr, bookDescriptionStr, bookPriceDbl);
        mItemViewModel.insert(bookItem);
    }

    public void clearInput(){
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
    public void reload(){
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