package android.apps.forecast.backbase.com.backbaseforecast.adapters;

import android.apps.forecast.backbase.com.backbaseforecast.R;
import android.apps.forecast.backbase.com.backbaseforecast.helpers.UserDefaults;
import android.apps.forecast.backbase.com.backbaseforecast.models.ForecastModel;
import android.apps.forecast.backbase.com.backbaseforecast.models.LocationModel;
import android.apps.forecast.backbase.com.backbaseforecast.models.apiObjects.List;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class KnownLocationsAdapter extends RecyclerView.Adapter<KnownLocationsAdapter.ViewHolder> {

    java.util.List<LocationModel> locations;
    Context context;
    ForecastItemClickListener forecastItemClickListener;
    public KnownLocationsAdapter(Context context, java.util.List<LocationModel> locations,ForecastItemClickListener forecastItemClickListener) {
        super();
        this.context = context;
        this.locations = locations;
        this.forecastItemClickListener = forecastItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.location_item, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        LocationModel locationData = locations.get(i);

        final int resourceId = context.getResources().getIdentifier(locationData.getImageName(), "drawable", context.getPackageName());
        viewHolder.imgLocation.setImageResource(resourceId);
        viewHolder.lblLocation.setText(locationData.getName());
        viewHolder.setClickListener(forecastItemClickListener);
    }

    @Override
    public int getItemCount() {
        return locations.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        public ImageView imgLocation;
        public TextView lblLocation;
        private ForecastItemClickListener clickListener;

        public ViewHolder(View itemView) {
            super(itemView);
            lblLocation = itemView.findViewById(R.id.lblLocation);
            imgLocation = itemView.findViewById(R.id.imgLocation);

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        public void setClickListener(ForecastItemClickListener itemClickListener) {
            this.clickListener = itemClickListener;
        }

        @Override
        public void onClick(View view) {
            clickListener.onClick(view, getPosition(), false);
        }

        @Override
        public boolean onLongClick(View view) {
            clickListener.onClick(view, getPosition(), true);
            return true;
        }
    }

}