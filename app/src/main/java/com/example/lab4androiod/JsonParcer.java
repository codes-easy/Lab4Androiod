package com.example.lab4androiod;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonParcer {

    static ArrayList <Country> JSONToArrayList (String jsonString) {
        ArrayList<Country> jsonArrayCountrylist = new ArrayList<>(0);

        try {
            JSONArray jsonArray = new JSONArray(jsonString);

            for (int i = 0; i < jsonArray.length(); i ++){

                Country country = new Country();

               JSONObject obj=  jsonArray.getJSONObject(i);
               String countryName = obj.getString("name");

               JSONArray currencies = obj.getJSONArray("currencies");
               String strCurrency=  currencies.getJSONObject(0).getString("code");

               JSONArray languages = obj.getJSONArray("languages");
               String strlanguage= "";
               for (int j =0 ;j< languages.length();j++) {
                    strlanguage = strlanguage  + languages.getJSONObject(j).getString("name") + "\n";

               }
               String strFlag = obj.getString("flag");
//               String strcapital = obj.getString("capital");
//               int strpopulation = obj.getString("population");

               //fill  country object
               country.setCurrency(strCurrency);
               country.setCountry(countryName);
               country.setLanguage(strlanguage);
               country.setFlag(strFlag);
//               country.setcapital(strcapital);
//               country.setpopulation(strpopulation);

               jsonArrayCountrylist.add(country);
            }
        } catch (JSONException e) {
            Log.d("Currency_app",  e.getMessage());
            e.printStackTrace();
        }


        return jsonArrayCountrylist;
    }


    static String  JSONToString (String jsonString)  {
        String Currency = null;
        try {
            JSONObject obj=  new JSONObject(jsonString);
             Currency = obj.getString("conversion_rate");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  Currency;
    }

}
