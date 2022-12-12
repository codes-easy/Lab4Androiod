package com.example.lab4androiod;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface CountryDao {


    @Insert
    void addNewCountry(Country C);

    @Query("select * from Country")
    Country[] getAllCountires();

    @Delete
    void deleteCountry (Country dc);

//    @Query("delete from Country")
//    void deleteCountries ();


}
