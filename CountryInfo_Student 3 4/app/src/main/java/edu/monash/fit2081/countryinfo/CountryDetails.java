package edu.monash.fit2081.countryinfo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;
import android.os.Looper;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.net.ssl.HttpsURLConnection;

public class CountryDetails extends AppCompatActivity {

    private TextView name;
    private TextView capital;
    private TextView code;
    private TextView population;
    private TextView area;

    private TextView currency;

    private TextView language;

    private String languages;
    private String currencies;

    private ImageView imageFlag;

    private TextView region;
    CountryInfo countryInfo;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_country_details);

        getSupportActionBar().setTitle(R.string.title_activity_country_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final String selectedCountry = getIntent().getStringExtra("country");

        name = findViewById(R.id.country_name);
        capital = findViewById(R.id.capital);
        code = findViewById(R.id.country_code);
        population = findViewById(R.id.population);
        area = findViewById(R.id.area);
        currency = findViewById(R.id.currency);
        language = findViewById(R.id.language);
        imageFlag = findViewById(R.id.imageFlag);
        region = findViewById(R.id.region_tv);



        ExecutorService executor = Executors.newSingleThreadExecutor();
        //Executor handler = ContextCompat.getMainExecutor(this);
        Handler uiHandler=new Handler(Looper.getMainLooper());



        executor.execute(() -> {
            //Background work here
            countryInfo = new CountryInfo();

            //w9 task3: change button text to WIKI + country name
            Button btnWiki = findViewById(R.id.btnWiki);
            btnWiki.setText("WIKI " + selectedCountry);

            try {
                // Create URL
                URL webServiceEndPoint = new URL("https://restcountries.com/v2/name/" + selectedCountry); //

                // Create connection
                HttpsURLConnection myConnection = (HttpsURLConnection) webServiceEndPoint.openConnection();

                if (myConnection.getResponseCode() == 200) {
                    //JSON data has arrived successfully, now we need to open a stream to it and get a reader
                    InputStream responseBody = myConnection.getInputStream();
                    InputStreamReader responseBodyReader = new InputStreamReader(responseBody, "UTF-8");

                    //now use a JSON parser to decode data
                    JsonReader jsonReader = new JsonReader(responseBodyReader);
                    jsonReader.beginArray(); //consume arrays's opening JSON brace
                    String keyName;
                    // countryInfo = new CountryInfo(); //nested class (see below) to carry Country Data around in
                    boolean countryFound = false;
                    while (jsonReader.hasNext() && !countryFound) { //process array of objects
                        jsonReader.beginObject(); //consume object's opening JSON brace
                        while (jsonReader.hasNext()) {// process key/value pairs inside the current object
                            keyName = jsonReader.nextName();
                            if (keyName.equals("name")) {
                                countryInfo.setName(jsonReader.nextString());
                                if (countryInfo.getName().equalsIgnoreCase(selectedCountry)) {
                                    countryFound = true;
                                }
                            } else if (keyName.equals("alpha3Code")) {
                                countryInfo.setAlpha3Code(jsonReader.nextString());
                            }
                            else if (keyName.equals("alpha2Code")) {
                                countryInfo.setAlpha2Code(jsonReader.nextString());
                            }
                            else if (keyName.equals("capital")) {
                                countryInfo.setCapital(jsonReader.nextString());
                            } else if (keyName.equals("population")) {
                                countryInfo.setPopulation(jsonReader.nextInt());
                            } else if (keyName.equals("area")) {
                                countryInfo.setArea(jsonReader.nextDouble());
                            }
                            else if (keyName.equals("region")) {    //w9 extra task
                                countryInfo.setRegion(jsonReader.nextString());
                            }
                            else if (keyName.equals("currencies")){ //w9 task 1
                                jsonReader.beginArray();
                                {
                                    while (jsonReader.hasNext()) {
                                        jsonReader.beginObject();
                                        while (jsonReader.hasNext()) {
                                            keyName = jsonReader.nextName();
                                            if (keyName.equals("name")) {
                                                //add comma at the end of each language unless it is the last one
                                                if (currencies == null) { //first entry
                                                    currencies = jsonReader.nextString();
                                                } else {
                                                    currencies = currencies + "," + jsonReader.nextString();
                                                }
                                            }
                                            else {
                                                jsonReader.skipValue();
                                            }
                                        }
                                        jsonReader.endObject();
                                    }
                                    jsonReader.endArray();
                                    countryInfo.setCurrencies(currencies);
                                }
                            }
                            else if (keyName.equals("languages")){ //w9 task 1
                                jsonReader.beginArray();
                                {
                                    while (jsonReader.hasNext()) {
                                        jsonReader.beginObject();
                                        while (jsonReader.hasNext()) {
                                            keyName = jsonReader.nextName();
                                            if (keyName.equals("name")) {
                                                //add comma at the end of each language unless it is the last one
                                                if (languages == null) { //first entry
                                                    languages = jsonReader.nextString();
                                                } else {
                                                    languages = languages + "," + jsonReader.nextString();
                                                }
                                            }
                                            else {
                                                jsonReader.skipValue();
                                            }
                                        }
                                        jsonReader.endObject();
                                    }
                                    jsonReader.endArray();
                                    countryInfo.setLanguages(languages);
                                }
                            }
                            else {
                                jsonReader.skipValue();
                            }
                        }
                        jsonReader.endObject();
                    }
                    jsonReader.endArray();

                    //w9 task2: get the country flag
                    String request = "https://flagcdn.com/160x120/" + countryInfo.getAlpha2Code().toLowerCase() + ".png";
                    java.net.URL url = new java.net.URL(request);  //create a URL object of what server to contact
                    HttpURLConnection connection = (HttpURLConnection) url
                            .openConnection();  //open the connection to the above URL
                    connection.setDoInput(true);    //get the input stream of the connection
                    connection.connect();   //connect to the web page
                    InputStream input = connection.getInputStream(); //Download the flag image

                    Bitmap myBitmap = BitmapFactory.decodeStream(input);    //Convert InputStream to Bitmap


                    uiHandler.post(()->{
                        name.setText(countryInfo.getName());
                        capital.setText(countryInfo.getCapital());
                        code.setText(countryInfo.getAlpha3Code());
                        population.setText(Integer.toString(countryInfo.getPopulation()));
                        area.setText(Double.toString(countryInfo.getArea()));
                        language.setText(countryInfo.getLanguages());
                        currency.setText(countryInfo.getCurrencies());
                        imageFlag.setImageBitmap(myBitmap);
                        region.setText(countryInfo.getRegion());
                    });


                } else {
                    Log.i("INFO", "Error:  No response");
                }

                // All your networking logic should be here
            } catch (Exception e) {
                Log.i("INFO", "Error " + e.toString());
            }

        });


    }

    public void countryWiki(View v){
        Intent intent = new Intent(this, WebWiki.class);
        intent.putExtra("COUNTRY_NAME", countryInfo.getName());
        startActivity(intent);

    }


    private class CountryInfo {
        private String name;
        private String alpha3Code;
        private String capital;
        private int population;
        private double area;

        private String currencies;
        private String languages;
        private String alpha2Code;

        private String region;


        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAlpha3Code() {
            return alpha3Code;
        }

        public void setAlpha3Code(String alpha3Code) {
            this.alpha3Code = alpha3Code;
        }

        public String getCapital() {
            return capital;
        }

        public void setCapital(String capital) {
            this.capital = capital;
        }

        public int getPopulation() {
            return population;
        }

        public void setPopulation(int population) {
            this.population = population;
        }

        public double getArea() {
            return area;
        }

        public void setArea(double area) {
            this.area = area;
        }

        public String getCurrencies() {
            return currencies;
        }

        public void setCurrencies(String currencies) {
            this.currencies = currencies;
        }

        public String getLanguages() {
            return languages;
        }

        public void setLanguages(String languages) {
            this.languages = languages;
        }

        public String getAlpha2Code() {
            return alpha2Code;
        }

        public void setAlpha2Code(String alpha2Code) {
            this.alpha2Code = alpha2Code;
        }

        public String getRegion() {
            return region;
        }

        public void setRegion(String region) {
            this.region = region;
        }

    }
}
