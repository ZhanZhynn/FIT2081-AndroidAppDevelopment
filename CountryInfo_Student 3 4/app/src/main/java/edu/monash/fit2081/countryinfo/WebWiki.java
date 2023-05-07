package edu.monash.fit2081.countryinfo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebWiki extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_wiki);

        //set a home button
        getSupportActionBar().setTitle("WIKI");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String wikiUrl = "https://en.wikipedia.org/wiki/" + getIntent().getStringExtra("COUNTRY_NAME");

        WebView webView = findViewById(R.id.webView);
        webView.setWebViewClient(new WebViewClient());  //this line is needed to open the link in the app
        webView.loadUrl(wikiUrl);   //load the url



    }
}
