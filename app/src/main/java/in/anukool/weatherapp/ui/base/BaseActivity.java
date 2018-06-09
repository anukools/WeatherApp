package in.anukool.weatherapp.ui.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import in.anukool.weatherapp.WeatherApplication;
import in.anukool.weatherapp.injection.component.ActivityComponent;
import in.anukool.weatherapp.injection.component.DaggerActivityComponent;
import in.anukool.weatherapp.injection.module.ActivityModule;

/** * Created by Anukool Srivastav on 15/04/18. */
public abstract class BaseActivity extends AppCompatActivity {

    private ActivityComponent mActivityComponent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivityComponent().inject(this);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
    }

    public ActivityComponent getActivityComponent() {
        if (mActivityComponent == null) {
            mActivityComponent = DaggerActivityComponent.builder()
                    .activityModule(new ActivityModule(this))
                    .applicationComponent(WeatherApplication.get(this).getComponent())
                    .build();
        }
        return mActivityComponent;
    }

}
