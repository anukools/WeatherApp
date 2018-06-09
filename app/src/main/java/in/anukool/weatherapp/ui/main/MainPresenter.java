package in.anukool.weatherapp.ui.main;


import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Location;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;

import java.security.PublicKey;

import javax.inject.Inject;

import in.anukool.weatherapp.data.DataManager;
import in.anukool.weatherapp.data.local.gms.ApiClientConnectionFailedException;
import in.anukool.weatherapp.data.local.gms.GmsLocationHelper;
import in.anukool.weatherapp.data.model.ApixuForecastResponse;
import in.anukool.weatherapp.data.remote.WeatherResponseApiException;
import in.anukool.weatherapp.ui.base.BasePresenter;
import in.anukool.weatherapp.util.LogUtil;
import in.anukool.weatherapp.util.RxUtil;
import rx.subscriptions.CompositeSubscription;

/** * Created by Anukool Srivastav on 15/04/18. */

public class MainPresenter extends BasePresenter<MainMvpView> {

    private final DataManager mDataManager;
    private CompositeSubscription mSubscriptions;
    private CompositeSubscription mLocationSubscriptions;
    private Location mLatestLocation;

    private static final String TAG = "MainPresenter";

    @Inject
    public MainPresenter(DataManager dataManager) {
        mDataManager = dataManager;
        mSubscriptions = new CompositeSubscription();
        mLocationSubscriptions = new CompositeSubscription();
    }

    @Override
    public void detachView() {
        super.detachView();
        mSubscriptions.unsubscribe();
        mLocationSubscriptions.unsubscribe();
    }

    public void startLocationService() {
        getMvpView().dismissWarning();
        if (getMvpView().hasFineLocationPermission()) {
            checkLocationSettings();
        } else {
            getMvpView().compatRequestFineLocationPermission();
        }
    }

    public void handleFineLocationPermissionResult(int grantResult) {
        if (grantResult == PackageManager.PERMISSION_GRANTED) {
            checkLocationSettings();
        } else {
            getMvpView().showNoFineLocationPermissionWarning();
        }
    }

    public void checkLocationSettings() {
        if (!getMvpView().isLocationSettingsStatusDialogCalled()) {
            mLocationSubscriptions.add(mDataManager.checkLocationSettings(GmsLocationHelper.APP_LOCATION_REQUEST)
                    .subscribe(this::handleLocationSettings,
                            this::handleGmsError));
        }
    }

    private void handleLocationSettings(LocationSettingsResult locationSettingsResult) {
        final Status status = locationSettingsResult.getStatus();
        switch (status.getStatusCode()) {
            case LocationSettingsStatusCodes.SUCCESS:
                subscribeToLocationChanges();
                break;
            case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                getMvpView().onUserResolvableLocationSettings(status);
                break;
            case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                LogUtil.i(TAG, "Location settings are inadequate, and cannot be fixed here. Dialog " +
                        "not created.");
                break;
        }
    }

    public void handleLocationSettingsDialogResult(int resultCode) {
        if (resultCode == Activity.RESULT_OK) {
            subscribeToLocationChanges();
        } else {
            getMvpView().showLocationSettingsWarning();
        }
    }

    public void handleGmsError(Throwable throwable) {
        if (throwable instanceof ApiClientConnectionFailedException) {
            ConnectionResult connectionResult =
                    ((ApiClientConnectionFailedException) throwable).getConnectionResult();
            if (connectionResult.hasResolution()) {
                getMvpView().onGmsConnectionResultResolutionRequired(connectionResult);
            } else {
                getMvpView().onGmsConnectionResultNoResolution(connectionResult.getErrorCode());
            }
        }
    }

    private void subscribeToLocationChanges() {
        mLocationSubscriptions.add(
                mDataManager.getDeviceLocation(GmsLocationHelper.APP_LOCATION_REQUEST)
                        .subscribe(
                                location -> {
                                        mLatestLocation = location;
                                        loadGPSBasedForecast();

                                },
                                this::handleGmsError));
    }

    public void stopLocationService() {
        mLocationSubscriptions.clear();
    }

    public void loadGPSBasedForecast() {
        getMvpView().setRefreshingIndicator(true);
            mSubscriptions.add(mDataManager.getCurrentWeatherWithObservable(
                    mLatestLocation.getLatitude(),
                    mLatestLocation.getLongitude())
                    .compose(RxUtil.applySchedulers())
                    .subscribe(
                            this::displayWeatherResponse,
                            this::handleError));
    }



    private void displayWeatherResponse(ApixuForecastResponse weatherResponse) {
        getMvpView().showWeather(weatherResponse);
        getMvpView().setRefreshingIndicator(false);
    }

    public void handleError(Throwable throwable) {
        if (throwable instanceof WeatherResponseApiException) {
            getMvpView().showApiError(throwable.getMessage());
        } else {
            getMvpView().showError(throwable.getMessage());
        }
    }


    public void doSomething(){
        String s = mDataManager.getSomething();
        getMvpView().printSomething(s);
    }
}
