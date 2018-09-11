package android.apps.forecast.backbase.com.backbaseforecast.base;

/**
 * Created by Admin on 11.09.2018.
 */

public class Keys {
    public static String API_KEY = "c6e381d8c7ff98f0fee43775817cf6ad";
    public static String BASE_URL = "http://api.openweathermap.org/data/2.5";
    public static String TODAYS_FORECAST_URL = String.format("%s/weather?appid=%s", BASE_URL, API_KEY);
    public static String FIVE_DAYS_FORECAST_URL = String.format("%s/forecast?appid=%s", BASE_URL, API_KEY);
    public static String HELP_URL = "https://github.com/mustafakarakus/backbase/blob/master/README.md";
}
