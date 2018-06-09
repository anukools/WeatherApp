package in.anukool.weatherapp.data.remote;

import in.anukool.weatherapp.Constants;
import in.anukool.weatherapp.data.model.ApixuForecastResponse;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;


import retrofit2.Retrofit;

import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Anukool Srivastav on 15/04/18.
 */
public interface ApixuWeatherService {

    @GET("forecast.json")
    Observable<ApixuForecastResponse> getForecast(@Query("q") String query, @Query("days") int days);

    class Factory {

        public static ApixuWeatherService createWeatherService() {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.API_BASE_URL)
                    .client(createOkHTTPClient(Constants.API_KEY))
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
            return retrofit.create(ApixuWeatherService.class);
        }

        private static OkHttpClient createOkHTTPClient(String key) {
            return new OkHttpClient.Builder()
                    .addInterceptor((chain) -> {
                        Request request = chain.request();
                        HttpUrl url = request.url().newBuilder().addQueryParameter("key", key).build();
                        request = request.newBuilder().url(url).build();
                        return chain.proceed(request);
                    })
                    .build();
        }
    }
}
