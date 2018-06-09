package in.anukool.weatherapp.injection.module;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import in.anukool.weatherapp.data.remote.ApixuWeatherService;
import in.anukool.weatherapp.injection.AppContext;

/**
 * Created by Anukool Srivastav on 15/04/18.
 */

@Module
public class ApplicationModule {

    private final Application mApplication;

    public ApplicationModule(Application application) {
        mApplication = application;
    }

    @Provides
    Application providesApplication() {
        return mApplication;
    }

    @Provides
    @AppContext
    Context providesContext() {
        return mApplication;
    }

    @Provides
    @Singleton
    ApixuWeatherService provideNetworkService() {
        return ApixuWeatherService.Factory.createWeatherService();
    }
}
