package com.thangtruong19.weatherapp.data;

import android.content.Context;

/**
 * Created by User on 02/05/2019.
 */

public class SunshineReference {
    private static final String PREF_CITY_NAME = "city_name";
    private static final String PREF_COORD_LAT = "coord_lat";
    private static final String PREF_COORD_LONG = "coord_long";
    private static final String DEFAULT_WEATHER_LOCATION = "94043,USA";
    private static final double[] DEFAULT_WEATHER_COORDINATES = {37.4284, 122.0724};
    private static final String DEFAULT_MAP_LOCATION =
            "1600 Amphitheatre Parkway, Mountain View, CA 94043";

    public static String getPreferencedWeatherLocation(Context context){
        return DEFAULT_WEATHER_LOCATION;
    }
    public static boolean isMetric(Context context) {
        /** This will be implemented in a future lesson **/
        return true;
    }

}
