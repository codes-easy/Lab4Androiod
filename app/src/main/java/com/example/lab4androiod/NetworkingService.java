package com.example.lab4androiod;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class NetworkingService {


    interface NetworkingListener {
        void jsonAvailability (String json);
        void getFlagReceived (Bitmap image);

    }


    NetworkingListener listener;


    URLConnection urlConnection;
    //bring all countries name and all exchange rates
      Handler handler = new Handler(Looper.getMainLooper());
    String countryURLString = "https://restcountries.com/v2/name/";

    //String exchangeApiKey = "dc4d36a6da646496400f96a8";
    String exchangeAPIUrl = "https://v6.exchangerate-api.com/v6/dc4d36a6da646496400f96a8/pair/CAD/";

    void setExchangeAPIUrl (String targetCurrency){
        String exchangeAPIConnection =  exchangeAPIUrl + targetCurrency;  // concatinating exchange rates
        getAPIresponse(exchangeAPIConnection);
    }
    void setCountryAPIurl (String apiParameter){
        String countryAPIConnection =  countryURLString + apiParameter;  // concatinating country names
        getAPIresponse(countryAPIConnection);
        }

    void getAPIresponse (String apiUrl) {
        // String countryAPIConnection = countryURLString + countryName; // concatinating country names
        CurrencyApp.executorService.execute(new Runnable() { //this will continously fetch country names - background thread - first usage
            @Override
            public void run() {
                HttpURLConnection urlConnection = null;
                try {
                    int streamRead = 0;
                    URL url = new URL(apiUrl);
                    urlConnection = (HttpURLConnection) url.openConnection();
                    InputStream IStream = new BufferedInputStream(urlConnection.getInputStream());
                    // Bitmap imageData = BitmapFactory.decodeStream(IStream);
                    StringBuffer countryBuffer = new StringBuffer();
                    while ((streamRead = IStream.read()) != -1) {
                        countryBuffer.append((char) streamRead);
                    }
                    Log.d("country", countryBuffer.toString());
                    // the json content is ready to returned back
                    handler.post(new Runnable() { //passing object between threads - second usage
                        @Override
                        public void run() {

                            listener.jsonAvailability(countryBuffer.toString()); //passing buffer to main thread
                        }
                    });
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    urlConnection.disconnect();
                }

            }
        });
    }
        void getFlagimage(String flagPath){


            String FlagUrl=flagPath.substring(0,flagPath.lastIndexOf('/'))+ "/w320/" + flagPath.substring(flagPath.lastIndexOf('/')+1,flagPath.lastIndexOf('.'))+".png";

            CurrencyApp.executorService.execute(new Runnable() {
                @Override
                public void run() {
                    HttpURLConnection urlConnection = null;
                    try {
                        int streamRead = 0;
                        URL url = new URL(FlagUrl);
                        urlConnection = (HttpURLConnection) url.openConnection();
                        InputStream in = url.openStream();
                        Bitmap flagimage = BitmapFactory.decodeStream(in);

                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                listener.getFlagReceived(flagimage);
                            }
                        });

                    }  catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    finally {
                        urlConnection.disconnect();
                    }

                }
            });

    }
}
