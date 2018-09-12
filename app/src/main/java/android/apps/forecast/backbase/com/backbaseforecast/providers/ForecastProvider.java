package android.apps.forecast.backbase.com.backbaseforecast.providers;

import android.apps.forecast.backbase.com.backbaseforecast.base.Keys;
import android.apps.forecast.backbase.com.backbaseforecast.helpers.Callback;
import android.apps.forecast.backbase.com.backbaseforecast.helpers.NetworkUtils;
import android.apps.forecast.backbase.com.backbaseforecast.helpers.UserDefaults;
import android.apps.forecast.backbase.com.backbaseforecast.models.ForecastModel;
import android.apps.forecast.backbase.com.backbaseforecast.models.LocationModel;
import android.apps.forecast.backbase.com.backbaseforecast.models.WeatherModel;
import android.content.Context;
import android.location.Location;
import android.os.AsyncTask;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ForecastProvider {

    private Context context;
    Gson gson = new Gson();
    public ForecastProvider(Context context){
        this.context = context;
    }

    public void getTodaysForecast(double latitude, double longitude, final Callback callback) {
        final String url = String.format("%s&lat=%s&lon=%s&units=%s", Keys.TODAYS_FORECAST_URL,latitude,longitude, UserDefaults.getUnit(context));

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    String result = NetworkUtils.getResponseFromHttpUrl(new URL(url));
                    WeatherModel weatherModel = gson.fromJson(result, WeatherModel.class);
                    callback.onSuccess(weatherModel);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    public void getFiveDaysForecast(double latitude, double longitude, final Callback callback) {
        final String url = String.format("%s&lat=%s&lon=%s&units=%s", Keys.FIVE_DAYS_FORECAST_URL,latitude,longitude, UserDefaults.getUnit(context));

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    String result = NetworkUtils.getResponseFromHttpUrl(new URL(url));
                    ForecastModel forecastModel = gson.fromJson(result, ForecastModel.class);
                    callback.onSuccess(forecastModel);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public List<LocationModel> getKnownLocations() {
        List<LocationModel> locations = new ArrayList<>();
        locations.add(new LocationModel("Amsterdam", "amsterdam", 52.3546274, 4.8285839));
        locations.add(new LocationModel("İstanbul", "istanbul", 41.0087192, 28.9759469));
        locations.add(new LocationModel("San Francisco", "sanfrancisco", 37.7576948, -122.4726193));
        locations.add(new LocationModel("Barcelona", "barcelona", 41.3947688, 2.0787284));
        locations.add(new LocationModel("Moscow", "moscow", 55.5807482, 36.8251543));
        locations.add(new LocationModel("Paris", "paris", 48.8588377, 2.2770207));
        locations.add(new LocationModel("Hong Kong", "hongkong", 22.3526738, 113.9876165));
        return locations;
    }
}
