package in.anukool.weatherapp.injection.component;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Component;
import in.anukool.weatherapp.data.DataManager;
import in.anukool.weatherapp.injection.AppContext;
import in.anukool.weatherapp.injection.module.ApplicationModule;


/**
 * Created by Anukool Srivastav on 15/04/18.
 */
@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    @AppContext
    Context context();

    Application application();

    DataManager dataManager();
}
