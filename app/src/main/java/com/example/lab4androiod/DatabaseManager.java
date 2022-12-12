package com.example.lab4androiod;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import androidx.room.Room;

import java.util.ArrayList;

public class DatabaseManager {



    interface DataBaseListener{
        void insertCountryDone();
        void gettingCountriesDone(Country [] list);

    }

    DataBaseListener listener;

    CountryDatabase countryDB;
    Handler dbHandler = new Handler(Looper.getMainLooper());

    CountryDatabase getDB(Context context){
        if (countryDB == null)
            countryDB = Room.databaseBuilder(context,CountryDatabase.class,"country_db").build();

        return countryDB;
    }

    void insertNewCountryAsync(Country cy){

        CurrencyApp.executorService.execute(new Runnable() {
            @Override
            public void run() {
                countryDB.getDao().addNewCountry(cy);
                dbHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        // notify main thread
                        listener.insertCountryDone();
                    }
                });
            }
        });
    }
    public void deleteFromFavourites(Country cy) {
        CurrencyApp.executorService.execute(new Runnable(){
            @Override
            public void run() {
                countryDB.getDao().deleteCountry(cy);
            }
        });
    }


    void getAllCountries(){
        CurrencyApp.executorService.execute(new Runnable() {
            @Override
            public void run() {
                Country[] list = countryDB.getDao().getAllCountires();
                dbHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        // notify main thread
                        listener.gettingCountriesDone(list);
                    }
                });
            }
        });
    }


    public interface MyAutoMigration {
    }
}
