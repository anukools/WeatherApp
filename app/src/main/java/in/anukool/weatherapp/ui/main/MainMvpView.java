package in.anukool.weatherapp.ui.main;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Status;

import in.anukool.weatherapp.data.model.ApixuForecastResponse;
import in.anukool.weatherapp.ui.base.MvpView;

/**
 * Created by Anukool Srivastav on 15/04/18.
 */


public interface MainMvpView extends MvpView {

    boolean hasFineLocationPermission();

    void setRefreshingIndicator(boolean state);

    void showWeather(ApixuForecastResponse weatherResponse);

    void showError(String locationString);

    void showApiError(String apiError);

    void compatRequestFineLocationPermission();

    void showNoFineLocationPermissionWarning();

    void dismissWarning();

    void onUserResolvableLocationSettings(Status status);

    void showLocationSettingsWarning();

    void onGmsConnectionResultResolutionRequired(ConnectionResult connectionResult);

    void onGmsConnectionResultNoResolution(int errorCode);

    boolean isLocationSettingsStatusDialogCalled();

    void printSomething(String print);
}
