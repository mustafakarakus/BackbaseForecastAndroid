package android.apps.forecast.backbase.com.backbaseforecast.base;


public class Keys {
    public static final String API_KEY = "c6e381d8c7ff98f0fee43775817cf6ad";
    public static final String BASE_URL = "http://api.openweathermap.org/data/2.5";
    public static final String TODAYS_FORECAST_URL = String.format("%s/weather?appid=%s", BASE_URL, API_KEY);
    public static final String FIVE_DAYS_FORECAST_URL = String.format("%s/forecast?appid=%s", BASE_URL, API_KEY);
    public static final String HELP_URL = "https://github.com/mustafakarakus/backbase/blob/master/README.md";

    public static final String BOOKMARK_LIST_KEY = "_UserBookmarks";
    public static final String SHARED_PREFERENCES = "BackbasePreferences" ;

}
