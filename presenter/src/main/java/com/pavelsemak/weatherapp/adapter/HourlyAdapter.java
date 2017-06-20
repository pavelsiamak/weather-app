package com.pavelsemak.weatherapp.adapter;

import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.pavelsemak.weatherapp.R;
import com.pavelsemak.weatherapp.mapper.ResourceMapper;
import com.pavelsemak.weatherapp.model.WeatherItemHour;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HourlyAdapter extends RecyclerView.Adapter<HourlyAdapter.ViewHolder> {
    private List<WeatherItemHour> hourly = new ArrayList<>();

    private Resources resources;

    public HourlyAdapter(Resources resources) {
        this.resources = resources;
    }

    @Override
    public HourlyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hour, parent, false);
        return new HourlyAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final HourlyAdapter.ViewHolder holder, int position) {
        WeatherItemHour hourItem = hourly.get(position);

        if (position == 0) {
            holder.time.setText(R.string.now);
        } else {
            holder.time.setText(hourItem.getDisplayTime());
        }
        if (hourItem.isSunrise()) {
            holder.temperature.setText(R.string.sunrise);
            holder.humidity.setVisibility(View.INVISIBLE);
        } else if (hourItem.isSunset()) {
            holder.temperature.setText(R.string.sunset);
            holder.humidity.setVisibility(View.INVISIBLE);
        } else {
            holder.temperature.setText(String.valueOf(hourItem.getTemperature()));
            holder.humidity.setVisibility(View.VISIBLE);
            holder.humidity.setText(String.format(resources.getString(R.string.percent), hourItem.getHumidity()));
        }
        holder.icon.setImageResource(ResourceMapper.getResource(hourItem.getIcon()));
    }

    @Override
    public int getItemCount() {
        return hourly.size();
    }

    public void setData(List<WeatherItemHour> hourly) {
        this.hourly = hourly;
        notifyDataSetChanged();
    }

    public List<WeatherItemHour> getData() {
        return hourly;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.hourly_time)
        TextView time;
        @BindView(R.id.hourly_humidity)
        TextView humidity;
        @BindView(R.id.hourly_icon)
        ImageView icon;
        @BindView(R.id.hourly_temperature)
        TextView temperature;
        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
