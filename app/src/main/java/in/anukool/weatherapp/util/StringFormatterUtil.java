package in.anukool.weatherapp.util;


import java.text.SimpleDateFormat;
import java.util.Date;

import in.anukool.weatherapp.data.model.ApixuForecastResponse;

/**
 * Created by Anukool Srivastav on 15/04/18.
 */
public final class StringFormatterUtil {

    private StringFormatterUtil() {
        throw new AssertionError();
    }

    public static String getCityName(ApixuForecastResponse weatherResponse) {
        return weatherResponse.place.getName();
    }

    public static String getTemperature(ApixuForecastResponse weatherResponse) {

        if (weatherResponse.current != null) {
            return getTemperatureString(weatherResponse.current.tempC);
        }
        return null;
    }

    public static String getTemperatureString(double temperature) {
        return String.valueOf(Math.round(temperature)).concat("°");
    }

    public static String getTemperatureStringwithC(double temperature) {
        return String.valueOf(Math.round(temperature)).concat("°C");
    }

    public static String getDayFromDate(String forecastDate){

//        return android.text.format.DateFormat.format("EEEE", forecastDate).toString();

        try {
            SimpleDateFormat inFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = inFormat.parse(forecastDate);
            SimpleDateFormat outFormat = new SimpleDateFormat("EEEE");
            String dayOfWeek = outFormat.format(date);
            return dayOfWeek;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;

    }
}
