package com.pavelsemak.weatherapp.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.pavelsemak.weatherapp.R;
import com.pavelsemak.weatherapp.mapper.ResourceMapper;
import com.pavelsemak.weatherapp.model.WeatherItemDay;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DailyAdapter extends RecyclerView.Adapter<DailyAdapter.ViewHolder> {
    private List<WeatherItemDay> daily = new ArrayList<>();

    @Override
    public DailyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_daily, parent, false);
        return new DailyAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final DailyAdapter.ViewHolder holder, int position) {
        WeatherItemDay dayItem = daily.get(position);

        holder.day.setText(dayItem.getDayOfWeek());
        holder.max.setText(String.valueOf(dayItem.getTemperatureMax()));
        holder.min.setText(String.valueOf(dayItem.getTemperatureMin()));
        holder.icon.setImageResource(ResourceMapper.getResource(dayItem.getIcon()));
    }

    @Override
    public int getItemCount() {
        return daily.size();
    }

    public void setData(List<WeatherItemDay> daily) {
        this.daily = daily;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.daily_day)
        TextView day;
        @BindView(R.id.daily_icon)
        ImageView icon;
        @BindView(R.id.daily_max)
        TextView max;
        @BindView(R.id.daily_min)
        TextView min;
        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
