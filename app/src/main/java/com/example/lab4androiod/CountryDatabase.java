package com.example.lab4androiod;


import androidx.room.Database;
import androidx.room.RoomDatabase;


@Database(version = 1,entities = {Country.class})




public abstract class CountryDatabase extends RoomDatabase {

        public abstract CountryDao getDao();

    }


