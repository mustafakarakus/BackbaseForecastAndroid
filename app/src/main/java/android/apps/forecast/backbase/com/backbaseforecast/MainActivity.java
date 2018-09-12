package android.apps.forecast.backbase.com.backbaseforecast;

import android.apps.forecast.backbase.com.backbaseforecast.adapters.BookmarkAdapter;
import android.apps.forecast.backbase.com.backbaseforecast.adapters.ForecastAdapter;
import android.apps.forecast.backbase.com.backbaseforecast.adapters.ForecastItemClickListener;
import android.apps.forecast.backbase.com.backbaseforecast.adapters.KnownLocationsAdapter;
import android.apps.forecast.backbase.com.backbaseforecast.base.Keys;
import android.apps.forecast.backbase.com.backbaseforecast.helpers.Callback;
import android.apps.forecast.backbase.com.backbaseforecast.helpers.NetworkUtils;
import android.apps.forecast.backbase.com.backbaseforecast.helpers.UserDefaults;
import android.apps.forecast.backbase.com.backbaseforecast.models.BookmarkModel;
import android.apps.forecast.backbase.com.backbaseforecast.models.ForecastModel;
import android.apps.forecast.backbase.com.backbaseforecast.models.LocationModel;
import android.apps.forecast.backbase.com.backbaseforecast.models.MarkerModel;
import android.apps.forecast.backbase.com.backbaseforecast.models.WeatherModel;
import android.apps.forecast.backbase.com.backbaseforecast.providers.ForecastProvider;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Vibrator;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.w3c.dom.Text;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMapLongClickListener, GoogleMap.OnMarkerClickListener {
    private GoogleMap gmap;
    private MapView mapView;
    private static final String MAP_VIEW_BUNDLE_KEY = "AIzaSyCGS1tKoC1THduifYp33sibu_YXeRYdOL4";
    Map<Marker, MarkerModel> markerMap = new HashMap<>();
    ForecastProvider provider;
    private ImageButton btnHelp, btnSettings, btnBookmarks, btnLocations;
    BottomSheetDialog bottomSheet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init(savedInstanceState);
      /*  provider.getTodaysForecast(39.9111667, 32.91429, new Callback() {
            @Override
            public void onSuccess(Object result) {
                Object a = result;
            }
        });*/
       /* provider.getFiveDaysForecast(39.9111667, 32.91429, new Callback() {
            @Override
            public void onSuccess(Object result) {
                Object a = result;
            }
        });*/

    }

    private void init(Bundle savedInstanceState) {
        bottomSheet = new BottomSheetDialog(MainActivity.this);
        provider = new ForecastProvider(getApplicationContext());
        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAP_VIEW_BUNDLE_KEY);
        }
        mapView = findViewById(R.id.mapView);
        mapView.getMapAsync(this);
        mapView.onCreate(mapViewBundle);

        btnHelp = findViewById(R.id.btnHelp);
        btnHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View helpLayout = getLayoutInflater().inflate(R.layout.help_layout, null);
                ImageButton btnClose = helpLayout.findViewById(R.id.btnClose);
                WebView webView = helpLayout.findViewById(R.id.webView);
                WebSettings settings = webView.getSettings();
                settings.setLoadsImagesAutomatically(true);
                settings.setJavaScriptEnabled(true);
                settings.setLoadWithOverviewMode(true);
                settings.setUseWideViewPort(true);
                webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
                webView.loadUrl(Keys.HELP_URL);
                webView.setWebViewClient(new WebViewClient(){
                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                        view.loadUrl(url);
                        return true;
                    }
                    @Override
                    public void onPageFinished(WebView view, final String url) {
                        view.scrollTo(0, 500);
                    }
                });
                btnClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bottomSheet.dismiss();
                    }
                });
                bottomSheet.setCancelable(false);
                bottomSheet.setContentView(helpLayout);
                bottomSheet.show();
            }
        });
        btnSettings = findViewById(R.id.btnSettings);
        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final View settingsLayout = getLayoutInflater().inflate(R.layout.settings_layout, null);
                Button btnResetBookmarks = settingsLayout.findViewById(R.id.btnResetBookmarks);
                ImageButton btnClose = settingsLayout.findViewById(R.id.btnClose);
                final Switch swChangeUnit = settingsLayout.findViewById(R.id.swChangeUnit);
                btnClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bottomSheet.dismiss();
                    }
                });
                btnResetBookmarks.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new AlertDialog.Builder(MainActivity.this)
                                .setTitle(getResources().getString(R.string.areYouSure))
                                .setMessage(getResources().getString(R.string.resetBookmarks))
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        UserDefaults.setBookmarks(getApplicationContext(), new ArrayList<BookmarkModel>());
                                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.bookmarksResetSuccessfully), Toast.LENGTH_LONG).show();
                                    }
                                })
                                .setNegativeButton(android.R.string.no, null).show();
                    }
                });
                swChangeUnit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String unit =  swChangeUnit.isChecked() ? getResources().getString(R.string.metric) : getResources().getString(R.string.imperial);
                        UserDefaults.setUnit(getApplicationContext(), unit);
                    }
                });
                swChangeUnit.setChecked(UserDefaults.getUnit(getApplicationContext()).equals(getResources().getString(R.string.metric)));
                bottomSheet.setContentView(settingsLayout);
                bottomSheet.show();
            }
        });
        btnBookmarks = findViewById(R.id.btnBookmarks);
        btnBookmarks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View bookmarksLayout = getLayoutInflater().inflate(R.layout.bookmark_layout, null);
                ListView lstBookmarks = bookmarksLayout.findViewById(R.id.lstBookmarks);
                TextView emptyView = bookmarksLayout.findViewById(R.id.txtEmptyList);
                ImageButton btnClose = bookmarksLayout.findViewById(R.id.btnClose);
                btnClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bottomSheet.dismiss();
                    }
                });
                lstBookmarks.setEmptyView(emptyView);
                final List<BookmarkModel> bookmarks = UserDefaults.getBookmarks(getApplicationContext());
                BookmarkAdapter adapter = new BookmarkAdapter(getApplicationContext(), bookmarks);
                lstBookmarks.setAdapter(adapter);
                bottomSheet.setContentView(bookmarksLayout);
                bottomSheet.show();

                lstBookmarks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        BookmarkModel bookmark = bookmarks.get(position);
                        getForecastAndShow(bookmark.getLatitude(), bookmark.getLongitude(), "",true,null);
                    }
                });

            }
        });
        btnLocations = findViewById(R.id.btnLocations);
        btnLocations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View locationsLayout = getLayoutInflater().inflate(R.layout.known_locations_layout, null);
                ImageButton btnClose = locationsLayout.findViewById(R.id.btnClose);
                RecyclerView lstLocations = locationsLayout.findViewById(R.id.lstLocations);
                lstLocations.setHasFixedSize(true);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);
                lstLocations.setLayoutManager(mLayoutManager);
                final List<LocationModel> knownLocations = provider.getKnownLocations();
                KnownLocationsAdapter adapter = new KnownLocationsAdapter(MainActivity.this, knownLocations, new ForecastItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        LocationModel selectedLocation = knownLocations.get(position);
                        getForecastAndShow(selectedLocation.getLatitude(),selectedLocation.getLongitude(),selectedLocation.getName(),false,null);
                        LatLng ny = new LatLng(selectedLocation.getLatitude(), selectedLocation.getLongitude());
                        gmap.animateCamera(CameraUpdateFactory.newLatLngZoom(ny,12.0f));
                    }
                });
                lstLocations.setAdapter(adapter);
                btnClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bottomSheet.dismiss();
                    }
                });
                bottomSheet.setContentView(locationsLayout);
                bottomSheet.show();
            }
        });
    }

    private void getForecastAndShow(final double latitude, final double longitude, final String title, final Boolean showRemove, final Callback callback) {
        provider.getTodaysForecast(latitude, longitude, new Callback() {
            @Override
            public void onSuccess(Object result) {
                if (result != null) {
                    final WeatherModel weatherModel = (WeatherModel) result;
                    provider.getFiveDaysForecast(latitude, longitude, new Callback() {
                        @Override
                        public void onSuccess(final Object forecastResult) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    ForecastModel forecastModel = null;
                                    if (forecastResult != null)
                                        forecastModel = (ForecastModel) forecastResult;

                                    showWeatherDetail(weatherModel, forecastModel,title,showRemove);
                                    if (callback != null)
                                        callback.onSuccess(weatherModel);
                                }
                            });
                        }
                    });
                }
            }
        });
    }

    private void refreshExistingBookmarkPins() {
        List<BookmarkModel> bookmarks = UserDefaults.getBookmarks(getApplicationContext());
        if (bookmarks != null) {
            gmap.clear();
            markerMap = new HashMap<>();
            for (BookmarkModel bookmark : bookmarks) {
                final BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.mipmap.pin);
                final Marker marker = gmap.addMarker(new MarkerOptions()
                        .position(new LatLng(bookmark.getLatitude(), bookmark.getLongitude()))
                        .icon(icon));
                markerMap.put(marker, new MarkerModel(bookmark.getId()));
            }
        }
    }

    private void showWeatherDetail(final WeatherModel weatherModel, ForecastModel forecastModel,String title, Boolean showRemove) {
        final View bookmarksLayout = getLayoutInflater().inflate(R.layout.weather_detail_layout, null);
        TextView txtTemperature = bookmarksLayout.findViewById(R.id.txtTemperature);
        TextView txtMaxTemperature = bookmarksLayout.findViewById(R.id.txtMaxTemperature);
        TextView txtMinTemperature = bookmarksLayout.findViewById(R.id.txtMinTemperature);
        TextView txtTemperatureUnit = bookmarksLayout.findViewById(R.id.txtTemperatureUnit);
        TextView txtName = bookmarksLayout.findViewById(R.id.txtName);
        TextView txtDescription = bookmarksLayout.findViewById(R.id.txtDescription);
        TextView txtInformation = bookmarksLayout.findViewById(R.id.txtInformation);
        ImageButton btnRemoveBookmark = bookmarksLayout.findViewById(R.id.btnRemoveBookmark);
        if(showRemove){
            btnRemoveBookmark.setVisibility(View.VISIBLE);
        }else{
            btnRemoveBookmark.setVisibility(View.GONE);
        }
        RecyclerView lstFiveDaysForecast = bookmarksLayout.findViewById(R.id.lstFiveDaysForecast);
        lstFiveDaysForecast.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        lstFiveDaysForecast.setLayoutManager(mLayoutManager);
        ForecastAdapter adapter = new ForecastAdapter(MainActivity.this, forecastModel);
        lstFiveDaysForecast.setAdapter(adapter);
        btnRemoveBookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle(getResources().getString(R.string.areYouSure))
                        .setMessage(getResources().getString(R.string.areYouSureRemoveBookmark))
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                UserDefaults.removeBookmark(weatherModel.getId(), getApplicationContext());
                                bottomSheet.dismiss();
                                refreshExistingBookmarkPins();
                                Toast.makeText(getApplicationContext(), getResources().getString(R.string.bookmarkDeletedSuccessfully), Toast.LENGTH_LONG).show();
                            }
                        })
                        .setNegativeButton(android.R.string.no, null).show();
            }
        });
        ImageButton btnClose = bookmarksLayout.findViewById(R.id.btnClose);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheet.dismiss();
            }
        });
        ImageView imgWeather = bookmarksLayout.findViewById(R.id.imgWeather);
        String windUnit=UserDefaults.getUnit(getApplicationContext()).equals("metric") ?  "m/s" : "mi/h";
        String temperatureUnit = UserDefaults.getUnit(getApplicationContext()).equals("metric") ?  "C" : "F";
        // TODO refactor units.
        txtTemperature.setText(String.format("%.0f", weatherModel.getMain().getTemp()));
        txtTemperatureUnit.setText(temperatureUnit);
        txtMinTemperature.setText(String.format("min: %.0f", weatherModel.getMain().getTempMin()));
        txtMaxTemperature.setText(String.format("max: %.0f", weatherModel.getMain().getTempMax()));
        String name =  weatherModel.getName();
        if(title.length()>0){
            name = title;
        }
        txtName.setText(name);
        txtDescription.setText(weatherModel.getWeather().get(0).getDescription());
        txtInformation.setText(String.format("wind: %s %s humidity: %s%%", weatherModel.getWind().getSpeed().toString(), windUnit, weatherModel.getMain().getHumidity().toString()));

        final int resourceId = getResources().getIdentifier(String.format("w%s", weatherModel.getWeather().get(0).getIcon()), "drawable", getPackageName());
        imgWeather.setImageResource(resourceId);
        bottomSheet.setContentView(bookmarksLayout);
        bottomSheet.show();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Bundle mapViewBundle = outState.getBundle(MAP_VIEW_BUNDLE_KEY);
        if (mapViewBundle == null) {
            mapViewBundle = new Bundle();
            outState.putBundle(MAP_VIEW_BUNDLE_KEY, mapViewBundle);
        }

        mapView.onSaveInstanceState(mapViewBundle);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    protected void onPause() {
        mapView.onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        mapView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    @Override
    public void onMapReady(GoogleMap googleMap) {
        gmap = googleMap;
        refreshExistingBookmarkPins();
        gmap.setOnMapLongClickListener(this);
        gmap.setOnMarkerClickListener(this);
        gmap.setMinZoomPreference(6);
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(getApplicationContext(),
                    android.Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                gmap.setMyLocationEnabled(true);
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
        } else {
            gmap.setMyLocationEnabled(true);
        }
        LatLng ny = new LatLng(38.95682, 35.174414);
        gmap.moveCamera(CameraUpdateFactory.newLatLng(ny));

    }

    @Override
    public void onMapLongClick(final LatLng latLng) {
        getForecastAndShow(latLng.latitude, latLng.longitude, "",true,new Callback() {
            @Override
            public void onSuccess(Object result) {
                if (result != null) {
                    final WeatherModel weatherModel = (WeatherModel) result;
                    List<BookmarkModel> bookmarks = UserDefaults.getBookmarks(getApplicationContext());
                    bookmarks.add(new BookmarkModel(weatherModel.getId(), weatherModel.getName(), latLng.latitude, latLng.longitude));
                    UserDefaults.setBookmarks(getApplicationContext(), bookmarks);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            final BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.mipmap.pin);
                            final Marker marker = gmap.addMarker(new MarkerOptions()
                                    .position(latLng)
                                    .title(latLng.toString())
                                    .icon(icon));
                            markerMap.put(marker, new MarkerModel(weatherModel.getId()));
                            Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                            v.vibrate(300);
                        }
                    });

                }
            }
        });
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        getForecastAndShow(marker.getPosition().latitude, marker.getPosition().longitude,"",true, null);
        return true;
    }

}
