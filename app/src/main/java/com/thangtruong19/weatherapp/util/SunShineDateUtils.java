package com.thangtruong19.weatherapp.util;

import android.content.Context;
import android.text.format.DateUtils;

import com.thangtruong19.weatherapp.R;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

/**
 * Created by User on 02/05/2019.
 */

public class SunShineDateUtils {
    public static final long SECOND_IN_MILLIIS = 1000;
    public static final long MINUTE_IN_MILLIIS = 60 * SECOND_IN_MILLIIS;
    public static final long HOUR_IN_MILLIIS = 60 * MINUTE_IN_MILLIIS;
    public static final long DAY_IN_MILLIIS = 24 * HOUR_IN_MILLIIS;

    /**
     * @param date : A date in miliseconds in local time
     * @return The number of days in UTC time from the epoch
     */
    public static long getDayNumber(long date){
        TimeZone timeZone = TimeZone.getDefault();
        long gmtOffset = timeZone.getOffset(date);
        return (date + gmtOffset) / DAY_IN_MILLIIS;
    }
    /**
     * @param date The UTC date to normalized
     * @return The UTC date at midnight
     */
    public static long normalizedDate(long date){
        long retValNew = date / DAY_IN_MILLIIS * DAY_IN_MILLIIS;
        return retValNew;
    }
    /**
     * @param utcDate The UTC datetime to convert to local datetime (in milliseconds)
     * @return The local date (in milliseconds)
     */
    public static long getLocalDateFromUTC(long utcDate){
        TimeZone timeZone = TimeZone.getDefault();
        long gmtOffset = timeZone.getOffset(utcDate);
        return utcDate - gmtOffset;
    }
    public static long getUTCFromLocalDate(long localDate){
        TimeZone timeZone = TimeZone.getDefault();
        long gmtOffset = timeZone.getOffset(localDate);
        return localDate + gmtOffset;
    }

    /**
     *
     * @param context Context to use for resource localization
     * @param dateInMillis the date in miliseconds (local time)
     * @return String represent day of the week
     */
    public static String getDayName(Context context,long dateInMillis){
        long dayNumber = getDayNumber(dateInMillis);
        long currentDateNumber = getDayNumber(System.currentTimeMillis());

        if(dayNumber == currentDateNumber){
            return context.getString(R.string.today);
        }else if(dayNumber == currentDateNumber+1){
            return context.getString(R.string.tomorrow);
        }else {
            SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE");
            return dayFormat.format(dateInMillis);
        }
    }

    /**
     *
     * @param context Used by DateUtils to format the date in current locale
     * @param timeInMillis Time in milliseconds since the epoch (local time)
     * @return The formatted date string
     */
    public static String getReadableDateString(Context context,long timeInMillis){
        int flags = DateUtils.FORMAT_SHOW_DATE
                | DateUtils.FORMAT_NO_YEAR
                | DateUtils.FORMAT_SHOW_WEEKDAY;

        return  DateUtils.formatDateTime(context,timeInMillis,flags);
    }

    /**
     *
     * @param context Context to use for resource localization
     * @param dateInMillis The date in milliseconds
     * @param showFullDate Used to show a fuller-version of the date, which always contains either
     *                     the day of the week, today, or tomorrow, in addition to the date.
     * @return A user-friendly representation of the date such as "Today, June 8", "Tomorrow",
     * or "Friday"
     */
    public static String getFriendlyDateString(Context context,long dateInMillis,boolean showFullDate){
        long localDate = getLocalDateFromUTC(dateInMillis);
        long dayNumber = getDayNumber(localDate);
        long currentDayNumber = getDayNumber(System.currentTimeMillis());

        if(dayNumber == currentDayNumber || showFullDate){
            String dayName = getDayName(context,localDate);
            String readableDate = getReadableDateString(context,localDate);
            if(dayNumber - currentDayNumber < 2){
                String localizedDayName = new SimpleDateFormat("EEEE").format(localDate);
                return readableDate.replace(localizedDayName,dayName);
            }else{
                return readableDate;
            }
        }else {
            int flags = DateUtils.FORMAT_SHOW_DATE
                    | DateUtils.FORMAT_NO_YEAR
                    | DateUtils.FORMAT_ABBREV_ALL
                    | DateUtils.FORMAT_SHOW_WEEKDAY;

            return DateUtils.formatDateTime(context, localDate, flags);
        }
    }
}
