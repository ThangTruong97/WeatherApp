package com.thangtruong19.weatherapp.util;

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by User on 01/05/2019.
 */

public class NetworkUtil {
   private static final String DYNAMIC_WEATHER_URL = "https://andfun-weather.udacity.com/weather";
   private static final String STATIC_WEATHER_URL = "https://andfun-weather.udacity.com/staticweather";
   private static final String FORECAST_BASE_URL = STATIC_WEATHER_URL;
   private static final String format = "json";
   private static final String units = "metric";
   private static final int numDays = 14;
   private static final String QUERY_PARAM = "q";
   private static final String LAT_PARAM = "lat";
   private static final String LON_PARAM = "lon";
   private static final String FORMAT_PARAM = "mode";
   private static final String UNITS_PARAM = "units";
   private static final String DAYS_PARAM = "cnt";
    public static URL buildUrl(String locationQuery){
        Uri uri = Uri.parse(FORECAST_BASE_URL).buildUpon()
                .appendQueryParameter(QUERY_PARAM,locationQuery)
                .appendQueryParameter(FORMAT_PARAM,format)
                .appendQueryParameter(UNITS_PARAM,units)
                .appendQueryParameter(DAYS_PARAM,Integer.toString(numDays)).build();
        URL url = null;
        try {
            url = new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Log.d("NetworkUtil","URL: "+url.toString());
        return url;
    }
    /*public static URL buildUrlFromCoordinate(int lat,int lon){

    }*/
    public static String getResponseFromURL(URL url){
        HttpURLConnection connection=null;
        try {
            connection = (HttpURLConnection)url.openConnection();
            InputStream stream = connection.getInputStream();
            Scanner scanner = new Scanner(stream);
            scanner.useDelimiter("\\A");
            boolean hasInput = scanner.hasNext();
            if(hasInput){
                return scanner.next();
            }else {
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            connection.disconnect();
        }
        return null;
    }
}
