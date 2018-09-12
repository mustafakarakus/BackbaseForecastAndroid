package android.apps.forecast.backbase.com.backbaseforecast.adapters;

import android.apps.forecast.backbase.com.backbaseforecast.R;
import android.apps.forecast.backbase.com.backbaseforecast.models.ForecastModel;
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

public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.ViewHolder> {

    ForecastModel forecastModel;
    Context context;

    public ForecastAdapter(Context context, ForecastModel forecastModel) {
        super();
        this.context = context;
        this.forecastModel = forecastModel;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.forecast_item, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        java.util.List<List> forecastList = forecastModel.getList();
        List forecastData = forecastList.get(i);
        String windUnit="m/s";
        String temperatureUnit = "C";
        try {
            Date forecastDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(forecastData.getDtTxt());
            String dateDescription =  new SimpleDateFormat("EEE HH:mm").format(forecastDate);
            viewHolder.lblDateDescription.setText(dateDescription);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        final int resourceId = context.getResources().getIdentifier(String.format("w%s", forecastData.getWeather().get(0).getIcon()), "drawable", context.getPackageName());
        viewHolder.imgWeather.setImageResource(resourceId);
        viewHolder.lblDescription.setText(forecastData.getWeather().get(0).getDescription());
        viewHolder.lblTemperature.setText(String.format("%.0f",forecastData.getMain().getTemp()));
        viewHolder.lblTemperatureUnit.setText(temperatureUnit);
        viewHolder.lblInformation.setText(String.format("W: %s %s, H: %s%%",forecastData.getWind().getSpeed(),windUnit,forecastData.getMain().getHumidity()));

        viewHolder.setClickListener(new ForecastItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return forecastModel.getList().size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        public ImageView imgWeather;
        public TextView lblDateDescription;
        public TextView lblTemperature;
        public TextView lblTemperatureUnit;
        public TextView lblDescription;
        public TextView lblInformation;
        private ForecastItemClickListener clickListener;

        public ViewHolder(View itemView) {
            super(itemView);
            lblDateDescription = itemView.findViewById(R.id.lblDateDescription);
            imgWeather = itemView.findViewById(R.id.imgWeather);
            lblTemperature = itemView.findViewById(R.id.lblTemperature);
            lblTemperatureUnit = itemView.findViewById(R.id.lblTemperatureUnit);
            lblDescription = itemView.findViewById(R.id.lblDescription);
            lblInformation = itemView.findViewById(R.id.lblInformation);

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