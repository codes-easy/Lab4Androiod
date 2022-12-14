package com.example.lab4androiod;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

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

        //handle swipe left or right in recycler view:
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callBackMethod); // creating new itemtouchhelper class
        itemTouchHelper.attachToRecyclerView(favCountryList); // use itemtouchhelper to recycle view

    }

    ItemTouchHelper.SimpleCallback callBackMethod = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView,
                              @NonNull RecyclerView.ViewHolder viewHolder,
                              @NonNull RecyclerView.ViewHolder target) {
            //Toast.makeText(FavCountries.this, "Item Moveing", Toast.LENGTH_SHORT).show();
            return false;
        }

       //on swipe left countries will be removed from db and alert message pop up for user to select
        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            int position = viewHolder.getAdapterPosition();
            String cname=ctadapter.list.get(position).getCountry();
            AlertDialog.Builder builder = new AlertDialog.Builder(FavCountries.this);
            builder.setMessage("Are you sure you want to remove "+ cname+" from the Favourites??");

            builder.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    ((CurrencyApp) getApplication()).databaseManager.deleteFromFavourites(ctadapter.list.get(position));
                    ctadapter.list.remove(position); // remove from DB
                    ctadapter.notifyDataSetChanged();
                }
            });
            builder.setNegativeButton("No",new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    ctadapter.notifyDataSetChanged();
                }
            });
            builder.create().show();
        }
    };
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
