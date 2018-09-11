package android.apps.forecast.backbase.com.backbaseforecast.providers;

import android.apps.forecast.backbase.com.backbaseforecast.base.Keys;
import android.apps.forecast.backbase.com.backbaseforecast.helpers.Callback;
import android.apps.forecast.backbase.com.backbaseforecast.helpers.NetworkUtils;
import android.apps.forecast.backbase.com.backbaseforecast.models.ForecastModel;
import android.apps.forecast.backbase.com.backbaseforecast.models.WeatherModel;
import android.os.AsyncTask;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.URL;


/**
 * Created by Admin on 11.09.2018.
 */

public class ForecastProvider {

    public void getTodaysForecast(double latitude, double longitude, final Callback callback) {
        final String url = String.format("%s&lat=%s&lon=%s", Keys.TODAYS_FORECAST_URL,latitude,longitude);

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    String result =   NetworkUtils.getResponseFromHttpUrl(new URL(url));
                    Gson gson = new Gson();
                    WeatherModel weatherModel = gson.fromJson(result, WeatherModel.class);
                    callback.onSuccess(weatherModel);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    public void getFiveDaysForecast(double latitude, double longitude, final Callback callback) {
        final String url = String.format("%s&lat=%s&lon=%s", Keys.FIVE_DAYS_FORECAST_URL,latitude,longitude);

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    String result =   NetworkUtils.getResponseFromHttpUrl(new URL(url));
                    Gson gson = new Gson();
                    ForecastModel forecastModel = gson.fromJson(result, ForecastModel.class);
                    callback.onSuccess(forecastModel);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
