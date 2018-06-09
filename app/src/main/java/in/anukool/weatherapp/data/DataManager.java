package in.anukool.weatherapp.data;

import android.location.Location;

import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationSettingsResult;

import javax.inject.Inject;
import javax.inject.Singleton;


import in.anukool.weatherapp.data.local.gms.GmsLocationHelper;
import in.anukool.weatherapp.data.model.ApixuForecastResponse;
import in.anukool.weatherapp.data.remote.ApixuWeatherService;
import in.anukool.weatherapp.data.remote.WeatherResponseApiException;
import rx.Observable;

/**
 * Created by Anukool Srivastav on 15/04/18.
 */
@Singleton
public class DataManager {


    @Inject ApixuWeatherService mApixuWeatherService;
    @Inject GmsLocationHelper mGmsLocationHelper;
    private int forecastDays = 5;

    @Inject
    public DataManager(ApixuWeatherService apixuWeatherService, GmsLocationHelper gmsLocationHelper) {
        mApixuWeatherService = apixuWeatherService;
        mGmsLocationHelper = gmsLocationHelper;
    }

    public Observable<ApixuForecastResponse> getCurrentWeatherWithObservable(double latitude, double longitude) {
        return mApixuWeatherService.getForecast(latitude + "," + longitude, forecastDays)
                .flatMap(this::handleWeatherResponse);
    }

    public String getSomething(){
        return "SomeThing";
    }

    private Observable<ApixuForecastResponse> handleWeatherResponse(ApixuForecastResponse weatherResponse) {
        if (weatherResponse != null) {
            return Observable.just(weatherResponse);
        } else {
            return Observable.error(new WeatherResponseApiException("Sorry! Something went wrong."));
        }
    }

    public Observable<Location> getDeviceLocation(LocationRequest locationRequest) {
        return mGmsLocationHelper.getDeviceLocation(locationRequest);
    }

    public Observable<LocationSettingsResult> checkLocationSettings(LocationRequest locationRequest) {
        return mGmsLocationHelper.checkLocationSettings(locationRequest);
    }

}
