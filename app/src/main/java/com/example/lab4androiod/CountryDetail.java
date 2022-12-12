package com.example.lab4androiod;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;

public class CountryDetail extends AppCompatActivity  implements NetworkingService.NetworkingListener {
    TextView languageTV;
    TextView currencynameTV;
    TextView exchangerateTV;
//  TextView populationTV;
//  TextView capitalcityTV;
    ImageView flagIV;
    CountriesAdapter countriesAdapter;
    int index;
    Country ct;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country_detail);
        ((CurrencyApp)getApplication()).networkingService.listener=this;
        languageTV = findViewById(R.id.language);
        currencynameTV = findViewById(R.id.currencyname);
        exchangerateTV = findViewById(R.id.exchangerate);
//        populationTV = findViewById(R.id.population);
//        capitalcityTV = findViewById(R.id.capitalcity);
        flagIV = findViewById(R.id.flagimage);

        if (getIntent().hasExtra("country")) {
             ct = getIntent().getParcelableExtra("country");
            this.setTitle(ct.country);

            languageTV.setText("Language - " + ct.getLanguage());
            currencynameTV.setText("Currency - " +ct.getCurrency());
//            populationTV.setText("Population - " + ct.getpopulation());
//           capitalcityTV.setText("Capital City - " + ct.getcapital());

            ((CurrencyApp)getApplication()).networkingService.setExchangeAPIUrl(ct.getCurrency());

            //https://flagcdn.com/at.svg

        }

    }

    @Override
    public void jsonAvailability(String json) {
        String exchange_rate = JsonParcer.JSONToString( json);
        exchangerateTV.setText("1 CAD = "  +exchange_rate+ ct.getCurrency() );
        ((CurrencyApp)getApplication()).networkingService.getFlagimage(ct.getFlag());
    }
public void getFlagReceived(Bitmap image){

    flagIV.setImageBitmap(image);

}

}