package com.example.lab4androiod;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
implements NetworkingService.NetworkingListener, CountriesAdapter.ItemListener,DatabaseManager.DataBaseListener{
    RecyclerView countryList;
    CountriesAdapter countriesAdapter;
ArrayList<Country> listCountries = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ((CurrencyApp)getApplication()).networkingService.listener=this;
        countryList = findViewById(R.id.countries_list);
        countriesAdapter = new CountriesAdapter(this, listCountries);
        this.setTitle("Search for the Country");
        countriesAdapter.listener = this;
        ((CurrencyApp)getApplication()).databaseManager.listener=this;
        ((CurrencyApp)getApplication()).databaseManager.getDB(this);
        countryList.setAdapter(countriesAdapter);
        countryList.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.country_search_menu, menu);
        MenuItem searchViewMenu = menu.findItem(R.id.country_searchview);
        SearchView searchView = (SearchView) searchViewMenu.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d("Currency app submit",query);
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.length() >= 3){
                   ((CurrencyApp)getApplication()).networkingService.setCountryAPIurl(newText);
                    //search for a country
                }
                else {
                    countriesAdapter.list = new ArrayList<>(0);
                    countriesAdapter.notifyDataSetChanged();
                }
                return false;
            }
        });

        return true;
    }

    @Override
    public void jsonAvailability(String json) { //reading and retreving json as object
//creating array of countries via json for recyclerview--arraylist of countries obj
        listCountries = JsonParcer.JSONToArrayList( json);
        countriesAdapter.list = listCountries;
        countriesAdapter.notifyDataSetChanged();

    }

    @Override
    public void getFlagReceived(Bitmap image) {

    }


    @Override
    public void onClicked(int post) {
//        Intent i = new Intent(this, CountryDetail.class);
//        i.putExtra("country", listCountries.get(post));
//        startActivity(i);
        showAlert(listCountries.get(post));

    }
    void showAlert(Country country){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to save "+ country.country+" to the Favourites??");
        builder.setNegativeButton("No",null);
        builder.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                ((CurrencyApp)getApplication()).databaseManager.insertNewCountryAsync(country);
            }
        });
        builder.create().show();

    }

    @Override
    public void insertCountryDone() { finish();}

    @Override
    public void gettingCountriesDone(Country[] list) {

    }



}