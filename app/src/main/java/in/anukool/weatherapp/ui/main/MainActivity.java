package in.anukool.weatherapp.ui.main;

import android.Manifest;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;

import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.Status;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import in.anukool.weatherapp.R;
import in.anukool.weatherapp.data.model.ApixuForecastResponse;
import in.anukool.weatherapp.ui.base.BaseActivity;
import in.anukool.weatherapp.util.LoadingImageView;
import in.anukool.weatherapp.util.LogUtil;
import in.anukool.weatherapp.util.StringFormatterUtil;

public class MainActivity extends BaseActivity implements MainMvpView, OnWeatherItemClickListener {

    private static final String TAG = "MainActivity";

    private static final int CHECK_LOCATION_SETTINGS_REQUEST_CODE = 1;
    private static final int FINE_LOCATION_PERMISSION_REQUEST_CODE = 2;

    @Inject
    MainPresenter mMainPresenter;

    @BindView(R.id.loading_status)
    LoadingImageView loadingSpinner;
    @BindView(R.id.text_place)
    TextView mTextPlace;
    @BindView(R.id.text_temperature)
    TextView mTextTemperature;

    @BindView(R.id.weather_forecast_rv)
    RecyclerView mRecyclerView;

    @BindView(R.id.weather_ll)
    LinearLayout mWeatherLL;

    @BindView(R.id.error_ll)
    LinearLayout errorLL;

    private Snackbar mWarningSnackbar;

    private boolean mIsLocationSettingsStatusForResultCalled = false;

    private ForecastWeatherAdapter mWeatherListAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupRecyclerView();

        getActivityComponent().inject(this);

        mMainPresenter.attachView(this);

        mMainPresenter.startLocationService();
    }

    private void setupRecyclerView() {
        // Setup adapter
        mWeatherListAdapter = new ForecastWeatherAdapter(new ArrayList<>());

        // Setup recycler view
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mWeatherListAdapter);
        mWeatherListAdapter.setWeatherCardListener(this);

        loadingSpinner.setVisibility(View.VISIBLE);
        errorLL.setVisibility(View.GONE);
        mWeatherLL.setVisibility(View.GONE);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == FINE_LOCATION_PERMISSION_REQUEST_CODE &&
                grantResults.length > 0) {
            mMainPresenter.handleFineLocationPermissionResult(grantResults[0]);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CHECK_LOCATION_SETTINGS_REQUEST_CODE) {
            mIsLocationSettingsStatusForResultCalled = false;
            mMainPresenter.handleLocationSettingsDialogResult(resultCode);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        mMainPresenter.stopLocationService();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMainPresenter.detachView();
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.retry_button)
    public void onRetry() {
        errorLL.setVisibility(View.GONE);
        mWeatherLL.setVisibility(View.GONE);
        loadingSpinner.setVisibility(View.VISIBLE);


        mMainPresenter.startLocationService();
    }


    /* MVP Methods */

    @Override
    public boolean hasFineLocationPermission() {
        return ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void setRefreshingIndicator(boolean state) {
        loadingSpinner.setVisibility(state ? View.VISIBLE : View.GONE);
        mWeatherLL.setVisibility(state ? View.GONE : View.VISIBLE);
        errorLL.setVisibility(View.GONE);
    }

    @Override
    public void showWeather(ApixuForecastResponse weatherResponse) {

        mTextPlace.setText(StringFormatterUtil.getCityName(weatherResponse));
        mTextTemperature.setText(StringFormatterUtil.getTemperature(weatherResponse));

        List<ApixuForecastResponse.ForecastDay> forecastDayList = weatherResponse.forecast.daily;
        // remove the current day as its already been show on top
        mWeatherListAdapter.replaceData(forecastDayList.subList(1, forecastDayList.size()));


        // slide animation
        slideToTop(mRecyclerView);
    }

    public static void slideToTop(View view) {

        TranslateAnimation animate = new TranslateAnimation(0, 0, view.getHeight(), 0);
        animate.setDuration(500);
        view.setVisibility(View.VISIBLE);
        view.startAnimation(animate);

    }

    @Override
    public void showError(String errorMessage) {
        LogUtil.e(TAG, errorMessage);
        errorLL.setVisibility(View.VISIBLE);
        loadingSpinner.setVisibility(View.GONE);
        mWeatherLL.setVisibility(View.GONE);
    }

    @Override
    public void showApiError(String apiError) {
        errorLL.setVisibility(View.VISIBLE);
        loadingSpinner.setVisibility(View.GONE);
        mWeatherLL.setVisibility(View.GONE);
    }

    @Override
    public void compatRequestFineLocationPermission() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                FINE_LOCATION_PERMISSION_REQUEST_CODE);
    }

    @Override
    public void showNoFineLocationPermissionWarning() {
        mWarningSnackbar = Snackbar.make(findViewById(R.id.coordinatorLayout),
                R.string.warning_no_fine_location_permission,
                Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.settings, v -> goToAppSettings());
        mWarningSnackbar.show();
    }

    private void goToAppSettings() {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                .addCategory(Intent.CATEGORY_DEFAULT)
                .setData(Uri.parse("package:" + getPackageName()));
        startActivity(intent);
    }

    @Override
    public void dismissWarning() {
        if (mWarningSnackbar != null && mWarningSnackbar.isShown()) {
            mWarningSnackbar.dismiss();
        }
    }

    @Override
    public void onUserResolvableLocationSettings(Status status) {
        try {
            status.startResolutionForResult(this, CHECK_LOCATION_SETTINGS_REQUEST_CODE);
            mIsLocationSettingsStatusForResultCalled = true;
        } catch (IntentSender.SendIntentException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void showLocationSettingsWarning() {
        mWarningSnackbar = Snackbar.make(findViewById(R.id.coordinatorLayout),
                R.string.warning_change_location_settings,
                Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.change_settings, v -> mMainPresenter.checkLocationSettings());
        mWarningSnackbar.show();
    }

    @Override
    public void onGmsConnectionResultResolutionRequired(ConnectionResult connectionResult) {
        try {
            connectionResult.startResolutionForResult(this, -1);
        } catch (IntentSender.SendIntentException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onGmsConnectionResultNoResolution(int errorCode) {
        GoogleApiAvailability.getInstance().getErrorDialog(this, errorCode, 0).show();
    }

    @Override
    public boolean isLocationSettingsStatusDialogCalled() {
        return mIsLocationSettingsStatusForResultCalled;
    }

    @Override
    public void printSomething(String print) {
        System.out.print(print);

    }

    @Override
    public void onWeatherCardClicked(ApixuForecastResponse.ForecastDay day) {
        Toast.makeText(this, "Selected Day : " +
                StringFormatterUtil.getDayFromDate(day.date), Toast.LENGTH_LONG).show();
    }
}