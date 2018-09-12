package android.apps.forecast.backbase.com.backbaseforecast.adapters;

import android.view.View;


public interface ForecastItemClickListener {
    void onClick(View view, int position, boolean isLongClick);
}
