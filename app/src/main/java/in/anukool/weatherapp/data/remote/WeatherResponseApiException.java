package in.anukool.weatherapp.data.remote;

/**
 * Created by Anukool Srivastav on 15/04/18.
 */
public class WeatherResponseApiException extends Exception {
    public WeatherResponseApiException(String detailMessage) {
        super(detailMessage);
    }
}
