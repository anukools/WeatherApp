package in.anukool.weatherapp;

import android.app.Application;
import android.content.Context;

import in.anukool.weatherapp.injection.component.ApplicationComponent;
import in.anukool.weatherapp.injection.component.DaggerApplicationComponent;
import in.anukool.weatherapp.injection.module.ApplicationModule;

/**
 * Created by Anukool Srivastav on 15/04/18.
 */
public class WeatherApplication extends Application {

    ApplicationComponent mApplicationComponent;

    public ApplicationComponent getComponent() {
        if (mApplicationComponent == null) {
            mApplicationComponent = DaggerApplicationComponent.builder()
                    .applicationModule(new ApplicationModule(this))
                    .build();
        }
        return mApplicationComponent;
    }

    public static WeatherApplication get(Context context) {
        return (WeatherApplication) context.getApplicationContext();
    }
}

