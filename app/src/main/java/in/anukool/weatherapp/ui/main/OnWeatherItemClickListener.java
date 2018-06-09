package in.anukool.weatherapp.ui.main;

import in.anukool.weatherapp.data.model.ApixuForecastResponse;

/**
 * Created by Anukool Srivastav on 16/05/18.
 */
public interface OnWeatherItemClickListener {
    void onWeatherCardClicked(ApixuForecastResponse.ForecastDay forecastDay);
}
