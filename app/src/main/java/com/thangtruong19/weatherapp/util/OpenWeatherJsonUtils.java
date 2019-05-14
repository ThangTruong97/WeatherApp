package com.thangtruong19.weatherapp.util;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;

/**
 * Created by User on 02/05/2019.
 */

public class OpenWeatherJsonUtils {
    public static String[] getSimpleWeatherStingsFromJson(Context context,String jsonResponse){
        final String OWM_LIST = "list";
        final String OWM_TEMPATURE = "temp";
        final String OWM_MAX = "max";
        final String OWM_MIN = "min";
        final String OWM_WEATHER = "weather";
        final String OWM_DESCRIPTION = "main";
        final String OWM_MESSAGE_CODE = "cod";

        String[] parsedWeatherData = null;

        try {
            JSONObject forecastJson = new JSONObject(jsonResponse);
            if(forecastJson.has(OWM_MESSAGE_CODE)){
                int errorCode = forecastJson.getInt(OWM_MESSAGE_CODE);
                switch (errorCode){
                    case HttpURLConnection.HTTP_OK:
                        break;
                    case HttpURLConnection.HTTP_NOT_FOUND:
                        return null;
                    default:
                        return null;
                }
            }
            JSONArray weatherArray = forecastJson.getJSONArray(OWM_LIST);
            parsedWeatherData =new String[weatherArray.length()];

            long localeDate = System.currentTimeMillis();
            long utcDate = SunShineDateUtils.getUTCFromLocalDate(localeDate);
            long startDate = SunShineDateUtils.normalizedDate(utcDate);

            for(int i=0; i<weatherArray.length();i++){
                String date;
                String highAndLow;

                long dateTimeMillis;
                double high;
                double low;
                String description;

                JSONObject dayForecast = weatherArray.getJSONObject(i);

                dateTimeMillis = startDate + SunShineDateUtils.DAY_IN_MILLIIS * i;
                date = SunShineDateUtils.getFriendlyDateString(context,dateTimeMillis,false);

                JSONObject weatherObject = dayForecast.getJSONArray(OWM_WEATHER).getJSONObject(0);
                description = weatherObject.getString(OWM_DESCRIPTION);

                JSONObject temperatureObject = dayForecast.getJSONObject(OWM_TEMPATURE);
                high = temperatureObject.getDouble(OWM_MAX);
                low = temperatureObject.getDouble(OWM_MIN);
                highAndLow = SunshineWeatherUtil.formatHighLows(context,high,low);

                parsedWeatherData[i] = date + " - "+ description+" - "+highAndLow;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return parsedWeatherData;
    }
}
