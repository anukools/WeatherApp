package in.anukool.weatherapp.injection.component;

import dagger.Component;
import in.anukool.weatherapp.injection.ActivityScope;
import in.anukool.weatherapp.injection.module.ActivityModule;
import in.anukool.weatherapp.ui.base.BaseActivity;
import in.anukool.weatherapp.ui.main.MainActivity;

/**
 * Created by Anukool Srivastav on 15/04/18.
 */
@ActivityScope
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(BaseActivity baseActivity);

    void inject(MainActivity mainActivity);
}
