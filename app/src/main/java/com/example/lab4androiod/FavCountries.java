package com.example.lab4androiod;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.Arrays;

public class FavCountries extends AppCompatActivity implements DatabaseManager.DataBaseListener, CountriesAdapter.ItemListener{
    CountriesAdapter ctadapter;
    ArrayList<Country>ctlist = new ArrayList<>(0);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fav_countries);
        this.setTitle("Favourite Countries");
        RecyclerView favCountryList = findViewById(R.id.favCountryList);
        ctadapter = new CountriesAdapter(this,ctlist);
        favCountryList.setAdapter(ctadapter);
        ctadapter.listener = this;
        favCountryList.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    protected void onResume() {
        super.onResume();
        ((CurrencyApp) getApplication()).databaseManager.getDB(this);
        ((CurrencyApp) getApplication()).databaseManager.getAllCountries();
        ((CurrencyApp) getApplication()).databaseManager.listener = this;

    }
    @Override
    public void insertCountryDone() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = new MenuInflater(this);
        menuInflater.inflate(R.menu.add_menu,menu);
               return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.addButton:
                Intent i = new Intent(this,MainActivity.class);
                startActivity(i);

                break;
        };
           return true;
    }

    @Override
    public void gettingCountriesDone(Country[] list) {
        ctlist = new ArrayList<Country>(Arrays.asList(list));
        ctadapter.list = ctlist;
        ctadapter.notifyDataSetChanged();
    }



    @Override
    public void onClicked(int post) {
        Intent i = new Intent(this,CountryDetail.class);
        i.putExtra("country", ctlist.get(post));
        startActivity(i);

    }


}